package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CovidFileReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.PropertyFileReader;
import edu.upenn.cit594.util.*;

import java.io.IOException;
import java.util.*;

public class Processor {
    protected List<Covid> covid;
    protected List<Population> population;
    protected List<Property> property;
    protected CovidFileReader covidReader;
    protected PopulationFileReader populationReader;
    protected PropertyFileReader propertyReader;
    protected AverageCalculator marketValueCalculator = new AverageMarketValueCalculator();
    protected AverageCalculator livableAreaCalculator = new AverageLivableAreaCalculator();

    public Processor(CovidFileReader covidReader, PopulationFileReader populationReader, PropertyFileReader propertyReader) throws IOException {
        this.covidReader = covidReader;
        covid = covidReader.readCovid();
        this.populationReader = populationReader;
        population = populationReader.readPopulation();
        this.propertyReader = propertyReader;
        property = propertyReader.readProperty();
    }

    public int calculateAllPopulation(){
        /*
        Calculates total population
         */
        int totalPopulation = 0;
        for (Population p: population){
            totalPopulation += p.getPopulation();
        }
        return totalPopulation;
    }

    private Map<Integer, Integer> initializePopulationMap(){
        /*
        Creates a population map to enable quick lookups of population values given zip codes. Memoization.
         */
        Map<Integer, Integer> populationMap = new HashMap<>();
        for (Population p : population){
            populationMap.put(p.getZipcode(), p.getPopulation());
        }
        return populationMap;
    }

    public List<ZipCodeTallies> calculateVaccinatedPerPopulation(String date, String type){
        List<ZipCodeTallies> zipTalliesList = new ArrayList<>();
        Map<Integer, Integer> populationMap = initializePopulationMap();
        boolean isPartial = "partial".equalsIgnoreCase(type);

        for (Covid c : covid){
            // if date not matching, skip.
            if (!c.getEtlTimestamp().substring(0,10).equals(date)) continue;

            // if date matched, proceed
            int zip = c.getZipcode();
            if (!populationMap.containsKey(zip)) continue;

            // if population has this zip, proceed
            int populationValue = populationMap.get(zip);
            if (populationValue == 0) continue;

            int vaccinated = isPartial ? c.getPartiallyVaccinated() : c.getFullyVaccinated();
            if (vaccinated == 0) continue;

            double ratio = (double) vaccinated / populationValue;
            ratio = Math.round(ratio * 10000)/10000.0;
            zipTalliesList.add(new ZipCodeTallies(zip, ratio));
        }
        zipTalliesList.sort(Comparator.comparing(ZipCodeTallies::getZipcode));
        return zipTalliesList;
    }

    public int calculateAverageMarketValue(int zipcode){
        return marketValueCalculator.calculateAverage(zipcode, property);
    }

    public int calculateAverageLivableArea(int zipcode){
        return livableAreaCalculator.calculateAverage(zipcode, property);
    }

}

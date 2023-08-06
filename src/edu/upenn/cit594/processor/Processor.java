package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CovidFileReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.PropertyFileReader;
import edu.upenn.cit594.util.*;

import java.io.IOException;
import java.util.*;

public class Processor {
    private List<Covid> covid;
    private List<Population> population;
    private List<Property> property;
    private CovidFileReader covidReader;
    private PopulationFileReader populationReader;
    private PropertyFileReader propertyReader;
    Map<Integer, Integer> populationMap;
    protected AverageCalculator marketValueCalculator = new AverageMarketValueCalculator();
    protected AverageCalculator livableAreaCalculator = new AverageLivableAreaCalculator();


    public Processor(CovidFileReader covidReader, PopulationFileReader populationReader, PropertyFileReader propertyReader) throws IOException {
        this.covidReader = covidReader;
        if (covidReader != null){
            covid = covidReader.readCovid();
        }

        this.populationReader = populationReader;
        if (populationReader != null){
            population = populationReader.readPopulation();
        }

        this.propertyReader = propertyReader;
        if (propertyReader != null){
            property = propertyReader.readProperty();
        }


    }

    public int calculateAllPopulation(){
        /*
        Calculates total population
         */
        if (population == null){
            System.err.println("Error: population is null");
            System.exit(1);
        }
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
        if (populationMap == null){
            populationMap = new HashMap<>();
            for (Population p : population){
                populationMap.put(p.getZipcode(), p.getPopulation());
            }
        }
        return populationMap;
    }

    public List<Tallies> calculateVaccinatedPerPopulation(String date, String type){
        if (covid == null || population == null){
            System.err.println("Error: covid or population is null.");
            System.exit(1);
        }
        initializePopulationMap();
        List<Tallies> zipTalliesList = new ArrayList<>();
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

            // if population is not 0, proceed
            int vaccinated = isPartial ? c.getPartiallyVaccinated() : c.getFullyVaccinated();
            if (vaccinated == 0) continue;

            // if vaccination is not 0, proceed
            double ratio = (double) vaccinated / populationValue;
            ratio = Math.round(ratio * 10000)/10000.0;
            zipTalliesList.add(new Tallies(zip, ratio));
        }
        zipTalliesList.sort(Comparator.comparing(Tallies::getKey));
        return zipTalliesList;
    }

    public int calculateAverageMarketValue(int zipcode){
        if (property == null){
            System.err.println("Error: property is null.");
            System.exit(1);
        }
        return marketValueCalculator.calculateAverage(zipcode, property);
    }

    public int calculateAverageLivableArea(int zipcode){
        if (property == null){
            System.err.println("Error: property is null.");
            System.exit(1);
        }
        return livableAreaCalculator.calculateAverage(zipcode, property);
    }

    public int calculateTotalMarketValuePerCapita(int zipcode){
        if (population == null || property == null){
            System.err.println("Error: population or property is null.");
            System.exit(1);
        }
        initializePopulationMap();
        if (!populationMap.containsKey(zipcode)){
            return 0;
        }
        int populationValue = populationMap.get(zipcode);
        if (populationValue == 0) return 0;
        int total = 0;
        for (Property p : property){
            if (zipcode == p.getZipcode() && isNumeric(p.getMarketValue())){
                total += Integer.parseInt(p.getMarketValue());
            }
        }
        return (int)(total/populationValue);
    }

    private boolean isNumeric(String str){
        if (str == null){
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public List<Tallies> listVacRateByValuePerCapital(){
        /*
        List average market value per capital and their corresponding vaccination rate at the zipcode level, in ascending order of average market value
         */
        return null;
    }

}

package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Set<String> availableDatasets = new HashSet<>();
        Map<String, String> seenArgs = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");

        for (String arg : args){
            Matcher matcher = pattern.matcher(arg);
            if (matcher.matches()){
                String name = matcher.group("name");
                name = name.toLowerCase();
                String value = matcher.group("value");

                if (!Arrays.asList("covid", "population", "property", "log").contains(name)){
                    System.err.println("Error: Invalid argument name: " + name);
                    System.exit(1);
                }

                if (seenArgs.containsKey(name)){
                    System.err.println("Error: Argument name used more than one: " + name);
                    System.exit(1);
                }



                if (name.equals("log")){
                    try {
                        Logger.getInstance().setDestination(value);
                    } catch(IOException e){
                        System.err.println("Error: unable to initialize logger with file: " + value);
                        System.exit(1);
                    }
                }

                if (name.equals("covid")){
                    if (!value.toLowerCase().endsWith(".csv") && !value.toLowerCase().endsWith(".json")){
                        System.err.println("Error: unknown file format for Covid data file: " + value);
                        System.exit(1);
                    }
                }

                // see if the file can be open

                seenArgs.put(name, value);
                availableDatasets.add(name);
                sb.append(arg).append(' ');
            } else{
                // if any argument does not match
                System.err.println("Error: argument does not the form '--name=value': " + arg);
                System.exit(1);
            }
        }
        // Logger should have been set here
        Logger logger = Logger.getInstance();
        logger.log(sb.toString());
        // Should FileReader has a Logger field? so that it can log info when file is open?


        if (seenArgs.containsKey("covid") && seenArgs.containsKey("population") && seenArgs.containsKey("property")){
            CovidFileReader covidReader = Main.getCovidReader(seenArgs);

            String populationFile = seenArgs.get("population");
            PopulationFileReader populationReader = new PopulationFileReader(populationFile);

            String propertyFile = seenArgs.get("property");
            PropertyFileReader propertyReader = new PropertyFileReader(propertyFile);

            Processor processor = new Processor(covidReader,populationReader,propertyReader);
        }  else if (seenArgs.containsKey("covid") && seenArgs.containsKey("population")){
            CovidFileReader covidReader = Main.getCovidReader(seenArgs);

            String populationFile = seenArgs.get("population");
            PopulationFileReader populationReader = new PopulationFileReader(populationFile);

            Processor processor = new Processor(covidReader,populationReader);
        } else if (seenArgs.containsKey("covid") && seenArgs.containsKey("property")){
            CovidFileReader covidReader = Main.getCovidReader(seenArgs);

            String propertyFile = seenArgs.get("property");
            PropertyFileReader propertyReader = new PropertyFileReader(propertyFile);

            Processor processor = new Processor(covidReader,propertyReader);
        } else if (seenArgs.containsKey("population") && seenArgs.containsKey("property")){
            String populationFile = seenArgs.get("population");
            PopulationFileReader populationReader = new PopulationFileReader(populationFile);

            String propertyFile = seenArgs.get("property");
            PropertyFileReader propertyReader = new PropertyFileReader(propertyFile);

            Processor processor = new Processor(populationReader,propertyReader);
        } else if (seenArgs.containsKey("covid")){
            CovidFileReader covidReader = Main.getCovidReader(seenArgs);
            Processor processor = new Processor(covidReader);
        } else if (seenArgs.containsKey("population") ){
            String populationFile = seenArgs.get("population");
            PopulationFileReader populationReader = new PopulationFileReader(populationFile);

            Processor processor = new Processor(populationReader);
        } else{
            // only property
            String propertyFile = seenArgs.get("property");
            PropertyFileReader propertyReader = new PropertyFileReader(propertyFile);

            Processor processor = new Processor(propertyReader);
        }


    }

    private static CovidFileReader getCovidReader(Map<String, String> seenArgs){
        String covidFile = seenArgs.get("covid");
        CovidFileReader covidReader;
        if (covidFile.toLowerCase().endsWith(".json")){
            covidReader = new CovidJsonReader(covidFile);
        } else {
            covidReader = new CovidCsvReader(covidFile);
        }
        return covidReader;
    }
}

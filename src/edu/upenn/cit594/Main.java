package edu.upenn.cit594;

import edu.upenn.cit594.datamanagement.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.ui.UI;
import edu.upenn.cit594.util.CSVFormatException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, CSVFormatException, ParseException {
        Set<String> availableDatasets = new HashSet<>();
        Map<String, String> seenArgs = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        Logger logger = Logger.getInstance();

        Pattern pattern = Pattern.compile("^--(?<name>.+?)=(?<value>.+)$");

        for (String arg : args){
            Matcher matcher = pattern.matcher(arg);
            if (matcher.matches()){
                String name = matcher.group("name");
                name = name.toLowerCase();
                String value = matcher.group("value");

                if (!Arrays.asList("covid", "population", "properties", "log").contains(name)){
                    System.err.println("Error: Invalid argument name: " + name);
                    throw new IllegalArgumentException();
                }

                if (seenArgs.containsKey(name)){
                    System.err.println("Error: Argument name used more than one: " + name);
                    throw new IllegalArgumentException();
                }

                if (name.equals("log")){
                    try {
                        logger.setDestination(value);
                    } catch(IOException e){
                        System.err.println("Error: unable to initialize logger with file: " + value);
                        throw new IllegalArgumentException();
                    }
                }

                if (name.equals("covid")){
                    if (!value.toLowerCase().endsWith(".csv") && !value.toLowerCase().endsWith(".json")){
                        System.err.println("Error: unknown file format for Covid data file: " + value);
                        throw new IllegalArgumentException();
                    }
                }

                // see if the file can be open - this will be handled in Reader

                seenArgs.put(name, value);
                availableDatasets.add(name);
                sb.append(arg).append(' ');
            } else{
                // if any argument does not match
                System.err.println("Error: argument does not the form '--name=value': " + arg);
                throw new IllegalArgumentException();
            }
        }

        logger.log(sb.toString());
        // Should FileReader has a Logger field? so that it can log info when file is open? YES

        CovidFileReader covidReader = null;
        PopulationFileReader populationReader = null;
        PropertyFileReader propertyReader = null;

        if (seenArgs.containsKey("covid")){
            String covidFile = seenArgs.get("covid");
            if (covidFile.toLowerCase().endsWith(".json")){
                covidReader = new CovidJsonReader(covidFile);
            } else {
                covidReader = new CovidCsvReader(covidFile);
            }
        }
        if (seenArgs.containsKey("population")){
            String populationFile = seenArgs.get("population");
            populationReader = new PopulationFileReader(populationFile);
        }
        if (seenArgs.containsKey("properties")){
            String propertyFile = seenArgs.get("properties");
            propertyReader = new PropertyFileReader(propertyFile);
        }

        Processor processor = new Processor(covidReader, populationReader, propertyReader);

        UI ui = new UI(processor, availableDatasets);
        ui.start();
        logger.close();
    }

}

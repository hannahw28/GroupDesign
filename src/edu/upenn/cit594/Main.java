package edu.upenn.cit594;

import edu.upenn.cit594.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Set<String> availableDatasets = new HashSet<>();
        Map<String, String> seenArgs = new HashMap<>();

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
            }
        }
    }
}

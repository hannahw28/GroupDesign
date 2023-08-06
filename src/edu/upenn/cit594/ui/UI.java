package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.util.Tallies;

import java.util.*;

public class UI {

    protected Processor processor;
    private List<Integer> availableActions;
    protected Logger logger;

    public UI(Processor processor, Set<String> availableDatasets){
        this.processor = processor;
        this.logger = Logger.getInstance();
        availableActions = new ArrayList<>();
        availableActions.add(0);
        availableActions.add(1);
        if (availableDatasets.contains("population")){
            availableActions.add(2);
        }
        if (availableDatasets.contains("covid") && availableDatasets.contains("population")){
            availableActions.add(3);
        }
        if (availableDatasets.contains("property")){
            availableActions.add(4);
            availableActions.add(5);
        }
        if (availableDatasets.contains("property") && availableDatasets.contains("population")) {
            availableActions.add(6);
        }
        if (availableDatasets.contains("covid") && availableDatasets.contains("property") && availableDatasets.contains("population")) {
            availableActions.add(7);
        }
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit){
            displayOptions();
            int action = -1;

            while (true){
                System.out.println("> ");
                System.out.flush();
                String line = scanner.nextLine();
                logger.log(line);
                try{
                    action = Integer.parseInt(line);
                    if (action < 0 || action > 7){
                        throw new InputMismatchException();
                    }
                    break;
                } catch(InputMismatchException | NumberFormatException e){
                    System.out.println("Invalid input, please enter a number between 0 and 7.");
                }
            }
            switch(action){
                case 0:
                    exit = true;
                    break;
                case 1:
                    // show the available actions
                    System.out.println("BEGIN OUTPUT");
                    for (Integer availableAction: availableActions){
                        System.out.println(availableAction);
                    }
                    System.out.println("END OUTPUT");
                    break;
                case 2:
                    // show the total population for all ZIP Codes.
                    System.out.println("BEGIN OUTPUT");
                    System.out.println(processor.calculateAllPopulation());
                    System.out.println("END OUTPUT");
                    break;
                case 3:
                    // show the total vaccinations per capita for each ZIP Code
                    System.out.println("Please enter 'partial' or 'full': ");
                    System.out.println("> ");
                    System.out.flush();

                    String type = scanner.nextLine();
                    logger.log(type);
                    if (!type.equals("partial") && !type.equals("full")){
                        System.err.println("Error: Invalid input. Returning to main menu.");
                        break;
                    }

                    System.out.println("Please enter a date in the format 'YYYY-MM-DD: ");
                    System.out.println("> ");
                    System.out.flush();
                    String date = scanner.nextLine();
                    logger.log(date);
                    if (!date.matches("\\d{4}-\\d{2}-\\d{2}")){
                        System.err.println("Error: Invalid date format. Returning to main menu");
                        break;
                    }

                    List<Tallies> result = processor.calculateVaccinatedPerPopulation(date, type);
                    System.out.println("BEGIN OUTPUT");
                    if (result.isEmpty()){
                        System.out.println(0);
                    } else{
                        for (Tallies tally: result){
                            System.out.println(tally.getKey() + " " + tally.getValue());
                        }
                    }
                    System.out.println("END OUTPUT");
                    break;
                case 4:
                    // show the average market value for properties in a specified ZIP Code
                    System.out.println("Please enter a 5-digit zip code: ");
                    System.out.println("> ");
                    System.out.flush();

                    String zipcode = scanner.nextLine();
                    logger.log(zipcode);
                    if (zipcode.length() != 5 || !zipcode.matches("\\d+")){
                        System.err.println("Error: Invalid zipcode. It must be a 5-digit number.");
                    } else{
                        int zip = Integer.parseInt(zipcode);
                        System.out.println("BEGIN OUTPUT");
                        System.out.println(processor.calculateAverageMarketValue(zip));
                        System.out.println("END OUTPUT");
                    }
                    break;
                case 5:
                    // show the average total livable area for properties in a specified ZIP Code
                    System.out.println("Please enter a 5-digit zip code: ");
                    System.out.println("> ");
                    System.out.flush();

                    String zipcode2 = scanner.nextLine();
                    logger.log(zipcode2);
                    if (zipcode2.length() != 5 || !zipcode2.matches("\\d+")){
                        System.err.println("Error: Invalid zipcode. It must be a 5-digit number.");
                    } else{
                        int zip2 = Integer.parseInt(zipcode2);
                        System.out.println("BEGIN OUTPUT");
                        System.out.println(processor.calculateAverageLivableArea(zip2));
                        System.out.println("END OUTPUT");
                    }
                    break;
                case 6:
                    // show the total market value of properties, per capita, for a specified ZIP Code
                    System.out.println("Please enter a 5-digit zip code: ");
                    System.out.println("> ");
                    System.out.flush();

                    String zipcode3 = scanner.nextLine();
                    logger.log(zipcode3);
                    if (zipcode3.length() != 5 || !zipcode3.matches("\\d+")){
                        System.err.println("Error: Invalid zipcode. It must be a 5-digit number.");
                    } else{
                        int zip3 = Integer.parseInt(zipcode3);
                        System.out.println("BEGIN OUTPUT");
                        System.out.println(processor.calculateTotalMarketValuePerCapita(zip3));
                        System.out.println("END OUTPUT");
                    }
                    break;
                case 7:
                    // show the results of your custom feature.
                    break;
            }
        }
        scanner.close();
        System.out.println("Program exited.");
    }

    private void displayOptions(){
        System.out.println("0. Exit the program.");
        System.out.println("1. Show the available actions.");
        System.out.println("2. Show the total population for all ZIP Codes");
        System.out.println("3. Show the total vaccinations per capita for each ZIP Code for the specified date.");
        System.out.println("4. Show the average market value for properties in a specified ZIP Code.");
        System.out.println("5. Show the average total livable area for properties in a specified ZIP Code.");
        System.out.println("6. Show the total market value of properties, per capita, for a specified ZIP Code.");
        System.out.println("7. Show the results of your custom feature.");
        System.out.println("Please select an option (type the number):");
    }

}

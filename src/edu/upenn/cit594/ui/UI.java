package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.Processor;

import java.util.*;

public class UI {

    protected Processor processor;
    private List<Integer> availableActions;

    public UI(Processor processor, Set<String> availableDatasets){
        this.processor = processor;
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
                    break;
                case 3:
                    // show the total vaccinations per capita for each ZIP Code
                    break;
                case 4:
                    // show the average market value for properties in a specified ZIP Code
                    break;
                case 5:
                    // show the average total livable area for properties in a specified ZIP Code
                    break;
                case 6:
                    // show the total market value of properties, per capita, for a specified ZIP Code
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

package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CSVFormatException;
import edu.upenn.cit594.util.CSVReader;
import edu.upenn.cit594.util.CharacterReader;
import edu.upenn.cit594.util.Population;

public class PopulationFileReader {
	private String fileName;
	private Logger logger;

	public PopulationFileReader(String fileName) {
		this.fileName = fileName;
		logger = Logger.getInstance();
	}

	/**
	 * Read population data files
	 * 
	 * @return list of population data
	 */
	public List<Population> readPopulation() {

		// initialize arraylist to return
		List<Population> allPopulation = new ArrayList<>();

		// initialize CSV Reader
		CharacterReader reader;
		try {
			reader = new CharacterReader(fileName);
			// logging
			logger.log(fileName);

			var csvReader = new CSVReader(reader);

			// initialize index numbers
			int zipcodePos = 0;
			int populationPos = 0;

			String[] row;

			// get index
			try {
				row = csvReader.readRow();

				for (int i = 0; i < row.length; i++) {

					switch (row[i]) {
					case "zip_code":
						zipcodePos = i;
						break;
					case "population":
						populationPos = i;
						break;
					}
				}

				// read data
				while ((row = csvReader.readRow()) != null) {
					// initialize instance for population constructors
					int zipCode;
					int populationVal;

					// check if the zipcode is less than 5 digits
					if (row[zipcodePos].length() == 5) {
						// check if the first 5 digits are ints
						try {
							zipCode = Integer.parseInt(row[zipcodePos].substring(0, 5));
						} catch (NumberFormatException e) {
							continue;
						}

					} else {
						continue;
					}

					// check if the population is integers
					try {
						populationVal = Integer.parseInt(row[populationPos]);
					} catch (NumberFormatException e) {
						continue;
					}

					// create population entry and add to population list
					Population population = new Population(zipCode, populationVal);
					allPopulation.add(population);
				}
			} catch (CSVFormatException e) {
				e.printStackTrace();
				System.exit(1);

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// return property list
		return allPopulation;

	}
}

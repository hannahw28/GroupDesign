package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CSVFormatException;
import edu.upenn.cit594.util.CSVReader;
import edu.upenn.cit594.util.CharacterReader;
import edu.upenn.cit594.util.Property;

public class PropertyFileReader {

	private String fileName;
	private Logger logger;

	public PropertyFileReader(String fileName) {
		this.fileName = fileName;
		logger = Logger.getInstance();
	}

	/**
	 * Read property data
	 * 
	 * @return list of properties
	 * @throws IOException
	 * @throws CSVFormatException
	 */
	public List<Property> readProperty() throws IOException, CSVFormatException {
		// initialize arraylist to return
		List<Property> allProperty = new ArrayList<>();

		// initialize CSV Reader
		CharacterReader reader;

		reader = new CharacterReader(fileName);
		// logging
		logger.log(fileName);

		var csvReader = new CSVReader(reader);

		// initialize index numbers
		int zipcodePos = 0;
		int valuePos = 0;
		int areaPos = 0;

		String[] row;

		// get index

		row = csvReader.readRow();

		for (int i = 0; i < row.length; i++) {

			switch (row[i]) {
			case "zip_code":
				zipcodePos = i;
				break;
			case "total_livable_area":
				areaPos = i;
				break;
			case "market_value":
				valuePos = i;
				break;
			}
		}

		// read data
		while ((row = csvReader.readRow()) != null) {
			// initialize instance for property constructors
			int zipCode;
			String marketValue;
			String totalLivableArea;

			// check if the zipcode is less than 5 digits
			if (row[zipcodePos].length() > 4) {
				// check if the first 5 digits are ints
				try {
					zipCode = Integer.parseInt(row[zipcodePos].substring(0, 5));
				} catch (NumberFormatException e) {
					continue;
				}

			} else {
				continue;
			}

			marketValue = row[valuePos];
			totalLivableArea = row[areaPos];

			// create property entry and add to property list
			Property property = new Property(marketValue, totalLivableArea, zipCode);
			allProperty.add(property);
		}

		// return property list
		return allProperty;

	}

}

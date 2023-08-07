package edu.upenn.cit594.datamanagement;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.CSVFormatException;
import edu.upenn.cit594.util.CSVReader;
import edu.upenn.cit594.util.CharacterReader;

public class CovidCsvReader extends CovidFileReader {

	private String fileName;
	private Logger logger;

	public CovidCsvReader(String fileName) {
		this.fileName = fileName;

		// initialize logger
		logger = Logger.getInstance();
	}

	/**
	 * Reads Covid data
	 * 
	 * @return list of Covid datas
	 * @throws IOException
	 */
	@Override
	public List<Covid> readCovid() {
		// initialize arraylist to return
		List<Covid> covid = new ArrayList<>();

		// initialize CSV Reader
		CharacterReader reader;
		try {
			reader = new CharacterReader(fileName);
			// logging
			logger.log(fileName);

			var csvReader = new CSVReader(reader);

			// initialize index numbers
			int zipcodePos = 0;
			int partialPos = 0;
			int fullyPos = 0;
			int timePos = 0;

			String[] row;

			// get index
			try {
				row = csvReader.readRow();

				for (int i = 0; i < row.length; i++) {

					switch (row[i]) {
					case "zip_code":
						zipcodePos = i;
						break;
					case "partially_vaccinated":
						partialPos = i;
						break;
					case "fully_vaccinated":
						fullyPos = i;
						break;
					case "etl_timestamp":
						timePos = i;
						break;
					}
				}
				// initialize format for timestamp
				String regex = "\\d{4}[-]\\d{2}[-]\\d{2}\\s+\\d{2}[:]\\d{2}[:]\\d{2}";
				Pattern p = Pattern.compile(regex);

				// read data
				while ((row = csvReader.readRow()) != null) {
					// initialize instance for covid constructors
					int zipCode;
					int partial;
					int fully;
					String timeStamp;

					// check if the zipcode is 5 digits
					if (row[zipcodePos].length() == 5) {
						zipCode = Integer.parseInt(row[zipcodePos]);
					} else {
						continue;
					}

					// check if the timestamp is in correct format
					Matcher m = p.matcher(row[timePos]);
					if (m.find()) {
						timeStamp = row[timePos];
					} else {
						continue;
					}

					// check if partial vaccined is missing
					if (row[partialPos].equals("")) {
						partial = 0;
					} else {
						partial = Integer.parseInt(row[partialPos]);
					}

					// check if fully vaccined is missing
					if (row[fullyPos].equals("")) {
						fully = 0;
					} else {
						fully = Integer.parseInt(row[fullyPos]);
					}

					// create covid entry and add to covid list
					Covid covidEntry = new Covid(zipCode, partial, fully, timeStamp);
					covid.add(covidEntry);
				}
			} catch (CSVFormatException e) {
				e.printStackTrace();
				System.exit(1);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// return covid list
		return covid;

	}
	
	
	
}

package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.upenn.cit594.util.Covid;

public class CovidCsvReader extends CovidFileReader{

	private String fileName;
	
	public CovidCsvReader(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Reads Covid data
	 * @return list of Covid datas
	 * @throws IOException
	 */
	@Override
	public List<Covid> readCovid() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		
		List<Covid> covid = new ArrayList<>();
		
		String line;

		line = reader.readLine();

		String[] parts = line.split(",");
		
		String regex = "\\d{4}[-]\\d{2}[-]\\d{2}\\s+\\d{2}[:]\\d{2}[:]\\d{2}";
		
		int zipcodePos = 0;
		int partialPos = 0;
		int fullyPos = 0;
		int timePos = 0;
		
		for(int i = 0; i < parts.length; i++) {
			
			switch(parts[i]) {
			case "\"zip_code\"":
				zipcodePos = i;
				break;
			case "\"partially_vaccinated\"":
				partialPos = i;
			case "\"fully_vaccinated\"":
				fullyPos = i;
			case "\"etl_timestamp\"":
				timePos = i;
			}	
		}
		
		
		while((line = reader.readLine()) != null) {
			parts = line.split(",");
			
			int zipCode;
			int partial;
			int fully;
			String timeStamp;
			
			if(parts[zipcodePos].length() == 5) {
				zipCode = Integer.parseInt(parts[zipcodePos]) ;
			}else {
				zipCode = 0;
			}
			
			if(parts[partialPos].equals("")) {
				partial = 0;
			}else {
				partial = Integer.parseInt(parts[partialPos]);
			}
			
			if(parts[fullyPos].equals("")) {
				fully = 0;
			}else {
				fully = Integer.parseInt(parts[fullyPos]);
			}
			
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(parts[timePos]);
			if(m.find()) {
				timeStamp = parts[timePos];
			}else {
				timeStamp = "";
			}
			
			
			Covid covidEntry = new Covid(zipCode, partial, fully, timeStamp);
			covid.add(covidEntry);
		}
		
		reader.close();
		
		return covid;
		
		
	}
}

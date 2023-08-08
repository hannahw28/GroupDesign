package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.util.Covid;

public class CovidJsonReader extends CovidFileReader {
	private String fileName;
	private Logger logger;

	public CovidJsonReader(String fileName) {
		this.fileName = fileName;
		logger = Logger.getInstance();
	}

	@Override
	public List<Covid> readCovid() throws FileNotFoundException, IOException, ParseException {
		// initialize covid list
		List<Covid> allCovid = new ArrayList<>();

		// initialize format for timestamp
		String regex = "\\d{4}[-]\\d{2}[-]\\d{2}\\s+\\d{2}[:]\\d{2}[:]\\d{2}";
		Pattern p = Pattern.compile(regex);

		Object obj = new JSONParser().parse(new FileReader(fileName));
		logger.log(fileName);
		JSONArray ja = (JSONArray) obj;
		for (Object jobj : ja) {
			JSONObject jo = (JSONObject) jobj;

			// initialize vaccined data
			int zipcode;
			int partiallyVaccined = 0;
			int fullyVaccined = 0;
			String timeStamp;

			zipcode = (int) ((long) jo.get("zip_code"));

			// check if the vaccines is missing
			if (jo.get("partially_vaccinated") != null) {
				partiallyVaccined = (int) ((long) jo.get("partially_vaccinated"));
			}

			// check if the vaccines is missing
			if (jo.get("fully_vaccinated") != null) {
				fullyVaccined = (int) ((long) jo.get("fully_vaccinated"));
			}

			// check if the zipcode is 5 digits
			if ((String.valueOf(zipcode)).length() != 5) {
				continue;
			}

			// check if the timestamp is in the right format
			Matcher m = p.matcher((String) jo.get("etl_timestamp"));

			if (m.find()) {
				timeStamp = (String) jo.get("etl_timestamp");
			} else {
				continue;
			}

			Covid covid = new Covid(zipcode, partiallyVaccined, fullyVaccined, timeStamp);
			allCovid.add(covid);

		}

		return allCovid;
	}
}

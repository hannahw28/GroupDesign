package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import edu.upenn.cit594.util.CSVFormatException;
import edu.upenn.cit594.util.Covid;

public abstract class CovidFileReader {
	/**
	 * Abstract method for reading Covid datas
	 * @return list of Covid datas
	 * @throws IOException 
	 * @throws CSVFormatException 
	 * @throws ParseException 
	 */
	public List<Covid> readCovid() throws IOException, CSVFormatException, ParseException {
		return null;
	}
}

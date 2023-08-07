package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.List;

import edu.upenn.cit594.util.Covid;

public abstract class CovidFileReader {
	/**
	 * Abstract method for reading Covid datas
	 * @return list of Covid datas
	 */
	public List<Covid> readCovid() {
		return null;
	}
}

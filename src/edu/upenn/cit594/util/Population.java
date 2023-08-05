package edu.upenn.cit594.util;

public class Population {
	//instances
	private int zip_code;
	private int population;
	
	//constructors
	public Population() {};
	public Population(int zip_code, int population) {
		this.zip_code = zip_code;
		this.population = population;
	}
	
	//getters and setters
	/**
	 * @return the zip_code
	 */
	public int getZip_code() {
		return zip_code;
	}
	/**
	 * @param zip_code the zip_code to set
	 */
	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}
	/**
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}
	/**
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}
	
	
}

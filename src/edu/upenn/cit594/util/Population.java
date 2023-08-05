package edu.upenn.cit594.util;

public class Population {
	//instances
	private int zipcode;
	private int population;
	
	//constructors
	public Population() {};
	public Population(int zipcode, int population) {
		this.zipcode = zipcode;
		this.population = population;
	}
	
	//getters and setters
	/**
	 * @return the zip_code
	 */
	public int getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zip_code to set
	 */
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
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

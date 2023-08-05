package edu.upenn.cit594.util;

public class Property {
	// instances
	private int market_value;
	private int total_livable_area;
	private int zip_code;

	// constructor
	public Property() {
	};

	public Property(int market_value, int total_livable_area, int zip_code) {
		this.market_value = market_value;
		this.total_livable_area = total_livable_area;
		this.zip_code = zip_code;
	}

	// getters and setters
	/**
	 * @return the market_value
	 */
	public int getMarket_value() {
		return market_value;
	}

	/**
	 * @param market_value the market_value to set
	 */
	public void setMarket_value(int market_value) {
		this.market_value = market_value;
	}

	/**
	 * @return the total_livable_area
	 */
	public int getTotal_livable_area() {
		return total_livable_area;
	}

	/**
	 * @param total_livable_area the total_livable_area to set
	 */
	public void setTotal_livable_area(int total_livable_area) {
		this.total_livable_area = total_livable_area;
	}

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

}

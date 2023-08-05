package edu.upenn.cit594.util;

public class Property {
	// instances
	private String marketValue;
	private String totalLivableArea;
	private int zipcode;

	// constructor
	public Property() {
	};

	public Property(String marketValue, String totalLivableArea, int zipcode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipcode = zipcode;
	}

	// getters and setters
	/**
	 * @return the market_value
	 */
	public String getMarketValue() {
		return marketValue;
	}

	/**
	 * @param marketValue the market_value to set
	 */
	public void setMarket_value(String marketValue) {
		this.marketValue = marketValue;
	}

	/**
	 * @return the total_livable_area
	 */
	public String getTotalLivableArea() {
		return totalLivableArea;
	}

	/**
	 * @param totalLivableArea the total_livable_area to set
	 */
	public void setTotalLivableArea(String totalLivableArea) {
		this.totalLivableArea = totalLivableArea;
	}

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

}

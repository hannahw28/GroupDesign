package edu.upenn.cit594.util;

public class Covid {
	private int zipcode;
	private int partiallyVaccinated;
	private int fullyVaccinated;
	private String etlTimestamp;
	
	public Covid() {};
	public Covid(int zipcode, int partiallyVaccinated, int fullyVaccinated, String etlTimestamp) {
		this.zipcode = zipcode;
		this.partiallyVaccinated = partiallyVaccinated;
		this.fullyVaccinated = fullyVaccinated;
		this.etlTimestamp = etlTimestamp;
		
	}
	/**
	 * @return the zipcode
	 */
	public int getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the partiallyVaccinated
	 */
	public int getPartiallyVaccinated() {
		return partiallyVaccinated;
	}
	/**
	 * @param partiallyVaccinated the partiallyVaccinated to set
	 */
	public void setPartiallyVaccinated(int partiallyVaccinated) {
		this.partiallyVaccinated = partiallyVaccinated;
	}
	/**
	 * @return the fullyVaccinated
	 */
	public int getFullyVaccinated() {
		return fullyVaccinated;
	}
	/**
	 * @param fullyVaccinated the fullyVaccinated to set
	 */
	public void setFullyVaccinated(int fullyVaccinated) {
		this.fullyVaccinated = fullyVaccinated;
	}
	/**
	 * @return the etlTimestamp
	 */
	public String getEtlTimestamp() {
		return etlTimestamp;
	}
	/**
	 * @param etlTimestamp the etlTimestamp to set
	 */
	public void setEtlTimestamp(String etlTimestamp) {
		this.etlTimestamp = etlTimestamp;
	}
	
}

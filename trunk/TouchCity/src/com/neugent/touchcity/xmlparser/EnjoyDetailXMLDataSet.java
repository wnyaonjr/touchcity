package com.neugent.touchcity.xmlparser;

public class EnjoyDetailXMLDataSet {
	
	
	private String companyName;
	private String inSection;
	private String inCity;
	private String inAddress;
	private String inPhone;
	private String inHours;
	private String fileName;
	private double latitude;
	private double longitude;
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the inSection
	 */
	public String getInSection() {
		return inSection;
	}
	/**
	 * @param inSection the inSection to set
	 */
	public void setInSection(String inSection) {
		this.inSection = inSection;
	}
	/**
	 * @return the inCity
	 */
	public String getInCity() {
		return inCity;
	}
	/**
	 * @param inCity the inCity to set
	 */
	public void setInCity(String inCity) {
		this.inCity = inCity;
	}
	/**
	 * @return the inAddress
	 */
	public String getInAddress() {
		return inAddress;
	}
	/**
	 * @param inAddress the inAddress to set
	 */
	public void setInAddress(String inAddress) {
		this.inAddress = inAddress;
	}
	/**
	 * @return the inPhone
	 */
	public String getInPhone() {
		return inPhone;
	}
	/**
	 * @param inPhone the inPhone to set
	 */
	public void setInPhone(String inPhone) {
		this.inPhone = inPhone;
	}
	/**
	 * @return the inHours
	 */
	public String getInHours() {
		return inHours;
	}
	/**
	 * @param inHours the inHours to set
	 */
	public void setInHours(String inHours) {
		this.inHours = inHours;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EnjoyDetailXMLDataSet [companyName=" + companyName
				+ ", fileName=" + fileName + ", inAddress=" + inAddress
				+ ", inCity=" + inCity + ", inHours=" + inHours + ", inPhone="
				+ inPhone + ", inSection=" + inSection + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}
	
	
}

package com.neugent.touchcity.xmlparser;

public class XMLDataSet {
	private String sampleAttribute = null;
	private String myTagString = null;
	private int theNumber = 0;
	/**
	 * @return the sampleAttribute
	 */
	public String getSampleAttribute() {
		return sampleAttribute;
	}
	/**
	 * @param sampleAttribute the sampleAttribute to set
	 */
	public void setSampleAttribute(String sampleAttribute) {
		this.sampleAttribute = sampleAttribute;
	}
	/**
	 * @return the myTagString
	 */
	public String getMyTagString() {
		return myTagString;
	}
	/**
	 * @param myTagString the myTagString to set
	 */
	public void setMyTagString(String myTagString) {
		this.myTagString = myTagString;
	}
	/**
	 * @return the theNumber
	 */
	public int getTheNumber() {
		return theNumber;
	}
	/**
	 * @param theNumber the theNumber to set
	 */
	public void setTheNumber(int theNumber) {
		this.theNumber = theNumber;
	}
	
	public String toString(){
		return "SampleAttribute = "+this.sampleAttribute+" MyTag = "+this.myTagString+" TheNumber = "+this.theNumber;
	}
	
	

}

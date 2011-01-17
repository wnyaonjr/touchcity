package com.neugent.touchcity.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import android.util.Log;

public class EnjoyDetailXMLHandler extends DefaultHandler{

	

	private boolean isInPList = false;
	private boolean isInArray = false;
	private boolean isInDict = false;
	private boolean isInKey = false;
	private boolean isInString = false;
	private EnjoyDetailXMLDataSet parsedXMLDataSet = new EnjoyDetailXMLDataSet();
	private String tempKeyValue;
	
	
	public EnjoyDetailXMLHandler() {
	}


	/**
	 * @return the isInPList
	 */
	public boolean isInPList() {
		return isInPList;
	}

	/**
	 * @param isInPList the isInPList to set
	 */
	public void setInPList(boolean isInPList) {
		this.isInPList = isInPList;
	}

	/**
	 * @return the isInArray
	 */
	public boolean isInArray() {
		return isInArray;
	}

	/**
	 * @param isInArray the isInArray to set
	 */
	public void setInArray(boolean isInArray) {
		this.isInArray = isInArray;
	}

	/**
	 * @return the isInDict
	 */
	public boolean isInDict() {
		return isInDict;
	}

	/**
	 * @param isInDict the isInDict to set
	 */
	public void setInDict(boolean isInDict) {
		this.isInDict = isInDict;
	}

	/**
	 * @return the isInKey
	 */
	public boolean isInKey() {
		return isInKey;
	}

	/**
	 * @param isInKey the isInKey to set
	 */
	public void setInKey(boolean isInKey) {
		this.isInKey = isInKey;
	}

	/**
	 * @return the isInString
	 */
	public boolean isInString() {
		return isInString;
	}

	/**
	 * @param isInString the isInString to set
	 */
	public void setInString(boolean isInString) {
		this.isInString = isInString;
	}

	/**
	 * @return the parsedXMLDataSet
	 */
	public EnjoyDetailXMLDataSet getParsedXMLDataSet() {
		return parsedXMLDataSet;
	}

	/**
	 * @param parsedXMLDataSet the parsedXMLDataSet to set
	 */
	public void setParsedXMLDataSet(EnjoyDetailXMLDataSet parsedXMLDataSet) {
		this.parsedXMLDataSet = parsedXMLDataSet;
	}


	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("plist"))
			isInPList = true;
		else if(localName.equals("array")){
			isInArray = true;
			//parsedXMLList = new ArrayList<EnjoyDetailXMLDataSet>();
		}
			
		else if(localName.equals("dict")){
			isInDict = true;
			//parsedXMLDataSet = new EnjoyDetailXMLDataSet();
		}
			
		else if(localName.equals("key"))
			isInKey = true;
		else if(localName.equals("string"))
			isInString = true;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equals("plist"))
			isInPList = false;
		else if(localName.equals("array"))
			isInArray = false;
			
		else if(localName.equals("dict")){
			isInDict = false;
			XMLDBHelper.createEnjoyDetailRow(parsedXMLDataSet);
		}
			
		else if(localName.equals("key"))
			isInKey = false;
		else if(localName.equals("string"))
			isInString = false;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(isInKey)
			tempKeyValue = new String(ch, start, length);
		else if(isInString){
			String stringValue = new String(ch, start, length);
			if(tempKeyValue.equals("companyName"))
				parsedXMLDataSet.setCompanyName(stringValue);
			else if(tempKeyValue.equals("inSection"))
				parsedXMLDataSet.setInSection(stringValue);
			else if(tempKeyValue.equals("inCity"))
				parsedXMLDataSet.setInCity(stringValue);
			else if(tempKeyValue.equals("inAddress"))
				parsedXMLDataSet.setInAddress(stringValue);
			else if(tempKeyValue.equals("inPhone"))
				parsedXMLDataSet.setInPhone(stringValue);
			else if(tempKeyValue.equals("inHours"))
				parsedXMLDataSet.setInHours(stringValue);
			else if(tempKeyValue.equals("fileName"))
				parsedXMLDataSet.setFileName(stringValue);
			else if(tempKeyValue.equals("lat"))
				parsedXMLDataSet.setLatitude(Double.parseDouble(stringValue));
			else if(tempKeyValue.equals("long"))
				parsedXMLDataSet.setLongitude(Double.parseDouble(stringValue));
		}
	}
}

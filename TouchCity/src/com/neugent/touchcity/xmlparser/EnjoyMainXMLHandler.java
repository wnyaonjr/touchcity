package com.neugent.touchcity.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class EnjoyMainXMLHandler extends DefaultHandler{
	
	private boolean isInPList = false;
	private boolean isInArray = false;
	private boolean isInDict = false;
	private boolean isInKey = false;
	private boolean isInString = false;
	
	private String tempKeyValue;
	
	private EnjoyMainXMLDataSet parsedXMLDataSet = new EnjoyMainXMLDataSet();
	private Handler handler;
	
	public EnjoyMainXMLHandler(){}
	public EnjoyMainXMLHandler(Handler handler){
		this.handler = handler;
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
	 * @return the parsedXMLDataSet
	 */
	public EnjoyMainXMLDataSet getParsedXMLDataSet() {
		return parsedXMLDataSet;
	}

	/**
	 * @param parsedXMLDataSet the parsedXMLDataSet to set
	 */
	public void setParsedXMLDataSet(EnjoyMainXMLDataSet parsedXMLDataSet) {
		this.parsedXMLDataSet = parsedXMLDataSet;
	}

	
	@Override
	public void setDocumentLocator(Locator locator) {
		super.setDocumentLocator(locator);
	}
	
	@Override
	public void endDocument() throws SAXException {
		Message msg = handler.obtainMessage();
		Bundle b = new Bundle();
		b.putString("message", "done");
		msg.setData(b);
		handler.sendMessage(msg);
		
		super.endDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(localName.equals("plist"))
			isInPList = true;
		else if(localName.equals("array"))
			isInArray = true;
		else if(localName.equals("dict"))
			isInDict = true;
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
			//Log.i("Value", parsedXMLDataSet.toString());
			XMLDBHelper.createEnjoyMainRow(parsedXMLDataSet);
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
			
			if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[0]))
				parsedXMLDataSet.setCompanyName(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[1]))
				parsedXMLDataSet.setInSection(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[2]))
				parsedXMLDataSet.setInCategory(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[3]))
				parsedXMLDataSet.setInCity(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[4]))
				parsedXMLDataSet.setFileName(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[5]))
				parsedXMLDataSet.setHoliday(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[6]))
				parsedXMLDataSet.setHasVisa(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[7]))
				parsedXMLDataSet.setHasDelivery(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[8]))
				parsedXMLDataSet.setHasAmex(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[9]))
				parsedXMLDataSet.setHasVIP(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[10]))
				parsedXMLDataSet.setHasParking(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[11]))
				parsedXMLDataSet.setHasOnlineMenu(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[12]))
				parsedXMLDataSet.setHasChildSeat(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[13]))
				parsedXMLDataSet.setHasInternational(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[14]))
				parsedXMLDataSet.setHasJCB(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[15]))
				parsedXMLDataSet.setHasMasterCard(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[16]))
				parsedXMLDataSet.setHasWifi(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[17]))
				parsedXMLDataSet.setHasDiners(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[18]))
				parsedXMLDataSet.setCardOffer(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[19]))
				parsedXMLDataSet.setVoucherOffer(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[20]))
				parsedXMLDataSet.setVoucherValue(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[21]))
				parsedXMLDataSet.setTagLine1(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[22]))
				parsedXMLDataSet.setTagLine2(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[23]))
				parsedXMLDataSet.setCoDescription(stringValue);
			else if(tempKeyValue.equals(EnjoyMainXMLDataSet.COLUMN_NAMES[24]))
				parsedXMLDataSet.setAddressKey(stringValue);
		}
	}
}

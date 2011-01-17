package com.neugent.touchcity.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandler extends DefaultHandler{

	@SuppressWarnings("unused")
	private boolean isInOuterTag = false;
	
	@SuppressWarnings("unused")
	private boolean isInInnerTag = false;
	private boolean isInMyTag = false;
	private XMLDataSet parsedXMLDataSet = new XMLDataSet();
	
	public XMLDataSet getParsedData(){
		return this.parsedXMLDataSet;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		this.parsedXMLDataSet = new XMLDataSet();
		//super.startDocument();
	}
	
	@Override
	public void endDocument() throws SAXException {
		//super.endDocument();
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		if(localName.equals("outertag"))
			this.isInOuterTag = true;
		else if(localName.equals("innertag")){
			this.isInInnerTag = true;
			String attrValue = attributes.getValue("sampleattribute");
			this.parsedXMLDataSet.setSampleAttribute(attrValue);
		}
		else if(localName.equals("mytag"))
			this.isInMyTag = true;
		else if(localName.equals("tagwithnumber")){
			String attrValue = attributes.getValue("thenumber");
			this.parsedXMLDataSet.setTheNumber(Integer.parseInt(attrValue));
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equals("outertag"))
			this.isInOuterTag = false;
		else if(localName.equals("innertag"))
			this.isInInnerTag = false;
		else if(localName.equals("mytag"))
			this.isInMyTag = false;
		else if(localName.equals("tagwithnumber")){
			
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		//Log.i("String value", new String(ch, start, length));
		
		if(isInMyTag)
			parsedXMLDataSet.setMyTagString(new String(ch, start, length));
		//super.characters(ch, start, length);
	}
	
}

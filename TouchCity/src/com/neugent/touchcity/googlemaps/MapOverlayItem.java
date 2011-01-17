package com.neugent.touchcity.googlemaps;


import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapOverlayItem extends OverlayItem{

	private int _id;
	private int inSection;
	private String inAddress;
	
	
	public MapOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}


	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	
	/**
	 * @return the inSection
	 */
	public int getInSection() {
		return inSection;
	}


	/**
	 * @param inSection the inSection to set
	 */
	public void setInSection(int inSection) {
		this.inSection = inSection;
	}


	/**
	 * @param inAddress the inAddress to set
	 */
	public void setInAddress(String inAddress) {
		this.inAddress = inAddress;
	}


	/**
	 * @return the inAddress
	 */
	public String getInAddress() {
		return inAddress;
	}
	

}

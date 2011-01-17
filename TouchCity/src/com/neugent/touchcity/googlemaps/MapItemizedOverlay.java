package com.neugent.touchcity.googlemaps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.neugent.touchcity.R;
import com.neugent.touchcity.list.CompanyList;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem>{

	private List<MapOverlayItem> items;
	private Context context;
	public static String section[] = {"Dining", "Health and Beauty", "Live and Learn",
										"Nightlife", "Shopping", "Travel"};
	private LinearLayout initialDetails;
	private TextView companyName;
	private TextView companyAddress;

	private Bitmap categoryMarkers[];
	private int index;
	
	public static int X_OFFSET = 10;
	public static int Y_OFFSET = 10;
	
	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(defaultMarker);
		this.context = context; 
		items = new ArrayList<MapOverlayItem>();
		initCategoryMarkers();
		
	}
	

	private void initCategoryMarkers() {
		Resources resources = context.getResources();
		categoryMarkers = new Bitmap[section.length];
		categoryMarkers[0] = BitmapFactory.decodeResource(resources, R.drawable.marker_dining);
		categoryMarkers[1] = BitmapFactory.decodeResource(resources, R.drawable.marker_beauty);
		categoryMarkers[2] = BitmapFactory.decodeResource(resources, R.drawable.marker_learn);
		categoryMarkers[3] = BitmapFactory.decodeResource(resources, R.drawable.marker_nightlife);
		categoryMarkers[4] = BitmapFactory.decodeResource(resources, R.drawable.marker_shopping);
		categoryMarkers[5] = BitmapFactory.decodeResource(resources, R.drawable.marker_travel);
	}

	@Override
	protected OverlayItem createItem(int index) {
		return (OverlayItem)items.get(index);

	}

	@Override
	public int size() {
		return items.size();
	}

	
	
	public void addItem(MapOverlayItem item) {
		items.add(item);
	}
	
	public void clearItems(){
		items.clear();
	}
	
	
	
	@Override
	protected boolean onTap(int index) {
		this.index = index;
		
		initialDetails.setVisibility(View.VISIBLE);
		String companyNameValue = items.get(index).getTitle();
		String companyAddressValue = items.get(index).getInAddress();
		
		companyName.setText(companyNameValue.toCharArray(), 0, companyNameValue.length());
		companyAddress.setText(companyAddressValue.toCharArray(), 0, companyAddressValue.length());
		
    	
		return true;
	}

	public void populateOverlay() {
		super.populate();
	}
	
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();
	      
	      for (int index=0; index < size(); index++) {

		  	    // Get the current location
		  	    int latitude = items.get(index).getPoint().getLatitudeE6();
		  	    int longitude = items.get(index).getPoint().getLongitudeE6(); 
		  	    GeoPoint geoPoint = new GeoPoint(latitude,longitude);
		
		  	    // Convert the location to screen pixels 
		  	    Point screenPts = new Point();
		  	    projection.toPixels(geoPoint, screenPts);
		  	  
		  	 //---add the marker---
		      canvas.drawBitmap(categoryMarkers[items.get(index).getInSection()], screenPts.x+X_OFFSET, screenPts.y-Y_OFFSET, null);
	      }
		//boundCenterBottom(marker);
	}

	/**
	 * @param initialDetails the initialDetails to set
	 */
	public void setInitialDetails(LinearLayout initialDetails) {
		this.initialDetails = initialDetails;
		
		initListener();
	}

	private void initListener() {
		this.initialDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				Intent intent =  new Intent(context,com.neugent.touchcity.detail.Detail.class);
				Bundle b = new Bundle();
		        b.putInt("idnum", items.get(index).get_id());
		    	b.putString("name", ""+items.get(index).getTitle());
		    	intent.putExtras(b);
		    	context.startActivity(intent);
				
			}
		});
		
	}


	/**
	 * @return the initialDetails
	 */
	public LinearLayout getInitialDetails() {
		return initialDetails;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(TextView companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyName
	 */
	public TextView getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(TextView companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
	 * @return the companyAddress
	 */
	public TextView getCompanyAddress() {
		return companyAddress;
	}
	
	
	
}

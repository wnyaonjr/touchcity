package com.neugent.touchcity.googlemaps;

/*
 * 
 * wrong position of company with id=14
 */


import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;


import com.neugent.touchcity.xmlparser.XMLDBHelper;
import com.neugent.touchcity.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.Overlay;
//import com.google.android.maps.MapView;
import com.google.android.maps.MapView;


public class MapsActivity extends MapActivity implements LocationListener{
    /** Called when the activity is first created. */
	
//	public static final double LONGITUDE = 121.080548;
//	public static final double LATITUDE = 14.607412;
	
	private MapView mapView; 
	private static MapController mc;
	private MapOverlay myLocationOverlay = null;
	//private GeoPoint myLocation = new GeoPoint((int)(LATITUDE* 1E6), (int)(LONGITUDE* 1E6));
	private GeoPoint myLocation = null;
	private Context context;
	private MapItemizedOverlay overlay;
    
    private static final double MAX_DISTANCE = 2.9;
    private String categoryString = "";
	private LinearLayout initialDetails;
	private TextView companyName;
	private TextView companyAddress;
	
	protected LocationManager locationMgr;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;
        
        if (isOnline()) {
        	setContentView(R.layout.map_layout);
            
            mapView = (MapView) findViewById(R.id.mapView);
            mapView.setBuiltInZoomControls(true);
            mc = mapView.getController();
            
            overlay = new MapItemizedOverlay(getResources().getDrawable(R.drawable.androidmarker), this);
            
            locationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
            
            //initMyLocation();//TODO 
            initComponents();
		}else{
			setContentView(R.layout.map_layout_empty);
			Toast.makeText(context, "No network access.", Toast.LENGTH_LONG).show();
		}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	locationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, this);
    	locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0, this);
    }
    
    @Override
	protected void onPause() {
		super.onPause();
		try {
			locationMgr.removeUpdates(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
    
    
    
	
    
    private void initMyLocation() {
    	 
    	/*mc.setZoom(14);
		mc.animateTo(myLocation);
		mc.setCenter(myLocation);*/
		
		mapView.invalidate();
		initOverlays(categoryString);
		
    	/*

    	myLocationOverlay = new MyLocationOverlayWithRangeMarker(context, mapView);
    	myLocationOverlay.enableMyLocation();
    	myLocationOverlay.runOnFirstFix(this);
    	mapView.getOverlays().add(myLocationOverlay);
    	 * 
    	 */
    	
	}

	private void initComponents() {
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(0);
				finish();
			}
		});
		
		Button shoppingButton = (Button) findViewById(R.id.shopping_button);
		shoppingButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				
				initOverlays("Shopping");
			}
		});
		
		Button nightLifeButton = (Button) findViewById(R.id.nightlife_button);
		nightLifeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				initOverlays("Nightlife");
			}
		});
		
		Button diningButton = (Button) findViewById(R.id.dining_button);
		diningButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				initOverlays("Dining");
			}
		});
		
		Button learnButton = (Button) findViewById(R.id.learn_button);
		learnButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				initOverlays("Live and Learn");
			}
		});
		
		Button beautyButton = (Button) findViewById(R.id.beauty_button);
		beautyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				initOverlays("Health and Beauty");
			}
		});
		
		Button travelButton = (Button) findViewById(R.id.travel_button);
		travelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				initOverlays("Travel");
			}
		});
		
		Button allButton = (Button) findViewById(R.id.all_button);
		allButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				initialDetails.setVisibility(View.INVISIBLE);
				initOverlays("");
			}
		});
		
		Button listButton = (Button) findViewById(R.id.list_button);
		listButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (myLocation != null){
				
				
					initialDetails.setVisibility(View.INVISIBLE);
					Intent intent =  new Intent(context,com.neugent.touchcity.list.CompanyList.class);
					Bundle b = new Bundle();
					b.putString("message", "fromMap");
			    	//b.putString("loc", LATITUDE+","+LONGITUDE);
					b.putString("loc", myLocation.getLatitudeE6()/1E6+","+myLocation.getLongitudeE6()/1E6);
					
			    	b.putString("category", (categoryString==null)?"":categoryString);
			    	intent.putExtras(b);
		    	context.startActivity(intent);
				}
			}
		});
		
		initialDetails = (LinearLayout) findViewById(R.id.initial_details);
		companyName = (TextView) findViewById(R.id.company_name);
		companyAddress = (TextView) findViewById(R.id.company_address);
		
		overlay.setInitialDetails(initialDetails);
		overlay.setCompanyName(companyName);
		overlay.setCompanyAddress(companyAddress);
		
		
	}

	private void initOverlays(String newCategoryString){
			int counter;
			
		if (myLocation != null){
			if(((categoryString.equals(""))  ||  !newCategoryString.equals(categoryString))){
				overlay.clearItems();
				categoryString = newCategoryString;
				List<Overlay> listOfOverlays = mapView.getOverlays();
		    	listOfOverlays.clear();
		    	
		    	    	
		    	
		    	//listOfOverlays.clear();
		    	XMLDBHelper.open();
		        Cursor companiesCursor = XMLDBHelper.fetchAllCompany(categoryString);
		        startManagingCursor(companiesCursor);
		        
		        double locationLatitude = myLocation.getLatitudeE6()/1E6;
		        double locationLongitude = myLocation.getLongitudeE6()/1E6;
		        
		        if(companiesCursor.moveToFirst()){
		        	do {
		        		int id = companiesCursor.getInt(0);
		        		String companyName = companiesCursor.getString(1);
		        		String inSection = companiesCursor.getString(2);
		        		String inAddress = companiesCursor.getString(4);
		        		double latitude = companiesCursor.getDouble(8);
		        		double longitude = companiesCursor.getDouble(9);
		        		
		        		
		        		
		        		GeoPoint p = new GeoPoint((int)(latitude* 1E6), (int)(longitude* 1E6));
		        		
		        		if (Distance.calculateDistance(locationLatitude, locationLongitude, latitude, longitude, Distance.KILOMETERS) <= MAX_DISTANCE) {
		        			//System.out.println(id+" "+Distance.calculateDistance(location.getLatitude(), location.getLongitude(), latitude, longitude, Distance.KILOMETERS));
		        			MapOverlayItem mapOverLay = new MapOverlayItem(p, companyName, null);
		            		
		            		mapOverLay.set_id(id);
		            		
		            		for(counter=0; counter<MapItemizedOverlay.section.length; counter++){
		            			if(inSection.equals(MapItemizedOverlay.section[counter]))
		            				break;
		            		}
		            		mapOverLay.setInSection(counter);
		            		mapOverLay.setInAddress(inAddress);
		            		
		            		overlay.addItem(mapOverLay);	
						}
		        		

					}while(companiesCursor.moveToNext());
		        	overlay.populateOverlay();
		    		listOfOverlays.add(overlay);
		    		mapView.invalidate();
		    		
		    		listOfOverlays.add(myLocationOverlay);
		        }
		        XMLDBHelper.close();
			}
			
			
		}
		
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public static MapController getInstanceOfMapController(){
		return mc;		
	}
	

	/*
	@Override
	public void run() {
		
		

		while (activityIsRunning) {
			try {
				Thread.sleep(THREAD_SLEEP);
			} catch (Exception e) {
			}
			Location newLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location == null || location.distanceTo(newLocation) >= 0) {
				GeoPoint geoPoint = new GeoPoint((int) (newLocation.getLatitude() * 1E6),(int) (newLocation.getLongitude() * 1E6));
				if(location == null){
					location = newLocation;
					
					mapView.post(new Runnable() {
						@Override
						public void run() {
							initOverlays("");
						}
					});
					
					mc.setZoom(14);
					mc.animateTo(geoPoint);
					mc.setCenter(geoPoint);
				}
				
				location = newLocation;
				myLocationOverlay.onLocationChanged(location);
				
			}
		}
		 
	}
	*/

	
	public boolean isOnline() {
		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getActiveNetworkInfo().isConnectedOrConnecting();
		} catch (Exception e) {
			return false;
		}
		 
		 
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev){
		mapView.invalidate();
		if(mapView.getZoomLevel() < 14){
			
			MapItemizedOverlay.X_OFFSET = 0;
			MapItemizedOverlay.Y_OFFSET = 0;
			MapOverlay.X_OFFSET = 0;
		}
		else{
			MapItemizedOverlay.X_OFFSET = 10;
			MapItemizedOverlay.Y_OFFSET = 10;
			MapOverlay.X_OFFSET = 15;
		}
		try {
			return super.dispatchTouchEvent(ev);
		} catch (Exception e) {
			return false;
		}
		
	}


	@Override
	public void onLocationChanged(Location location) {
		//myLocation = location;
		
		if (myLocation == null) {
			
			myLocation = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));
			mc.setZoom(14);
			mc.animateTo(myLocation);
			mc.setCenter(myLocation);
			myLocationOverlay = new MapOverlay(this, myLocation);
			mapView.getOverlays().add(myLocationOverlay);
			
		}
		else
			myLocation = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));
				
		initMyLocation();
		
		

	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}
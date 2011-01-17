package com.neugent.touchcity.list;

import java.util.ArrayList;

import com.neugent.touchcity.R;
import com.neugent.touchcity.googlemaps.Distance;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.neugent.touchcity.xmlparser.XMLDBHelper;


public class CompanyList extends Activity {
     	    
	   XMLDBHelper xmldbHelper;
       ArrayList<String> name = new ArrayList<String>();
       ArrayList<String> addr = new ArrayList<String>(); 
       ArrayList<String> phone = new ArrayList<String>();
       ArrayList<Integer> idnum = new ArrayList<Integer>(); 
       
       String location, sender, categoryFromSender;
       TextView loc, noData;
       GridView gv;
       Cursor companiesCursor;
       Button dining_btn, travel_btn, shopping_btn, learn_btn, nightlife_btn, beauty_btn, all_btn, search_btn;
       
       private static final double MAX_DISTANCE = 2.9;
       private static final String[] CATEGORIES = { "Dining", "Travel", "Shopping", "Live and Learn", "Nightlife", "Health and Beauty"};

       public void onCreate(Bundle icicle) {
	    	
	        super.onCreate(icicle);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        Bundle b = getIntent().getExtras();
	        location = b.getString("loc");
	        sender = b.getString("message");
	        categoryFromSender = b.getString("category");

	        determineLocation();
	        
	        setContentView(R.layout.list_item);
	        
	        noData = (TextView) findViewById(R.id.no_data);
	        loc = (TextView) findViewById(R.id.loc);
	        loc.setText(location);
	               
	        gv = (GridView) findViewById(R.id.gv);
	        gv.setAdapter(new CompanyListAdapter(this, name, addr, phone));
	        
	        search_btn = (Button) findViewById(R.id.search_icon_btn);
	        
	        if(sender.equals("fromMap")) {
	        	loc.setText("Current Location");
	        	determineCategory(categoryFromSender);
	        	search_btn.setVisibility(View.INVISIBLE);
	        } else search_btn.setVisibility(View.VISIBLE);
	        
	        //added by winnie
	        Button homeButton = (Button) findViewById(R.id.home_button);
			homeButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent =  new Intent(CompanyList.this,com.neugent.touchcity.home.TouchCity.class);
		        	startActivity(intent);
				}
			});
			
	        dining_btn = (Button) this.findViewById(R.id.dining_btn);
	        travel_btn = (Button) this.findViewById(R.id.travel_btn);
	        shopping_btn = (Button) this.findViewById(R.id.shopping_btn);
	        learn_btn = (Button) this.findViewById(R.id.learn_btn);
	        beauty_btn = (Button) this.findViewById(R.id.beauty_btn);
	        nightlife_btn = (Button) this.findViewById(R.id.nightlife_btn);
	        all_btn = (Button) this.findViewById(R.id.all_btn);
	        
	        
	        gv.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        		Intent intent =  new Intent(CompanyList.this,com.neugent.touchcity.detail.Detail.class);
	        		Bundle b = new Bundle();
	            	b.putInt("idnum", idnum.get(position));
	            	b.putString("name", name.get(position));	
	            	intent.putExtras(b);       	
	            	startActivityForResult(intent, 0); 
	            }

	  	  	});
	        
	        search_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	Bundle b = new Bundle();
	            	Intent i = new Intent(CompanyList.this,com.neugent.touchcity.search.Search.class);    
	            	b.putString("loc", location);
	            	b.putInt("arg", 2);
	            	i.putExtras(b);
	            	startActivity(i);
	            }
  	  		});
	        
	        dining_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            		determineCategory(CATEGORIES[0]);
	            }
	  	  	});
	        
	        travel_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
            		determineCategory(CATEGORIES[1]);
	            }

	  	  	});
	        
	        shopping_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
            		determineCategory(CATEGORIES[2]);
	            }

	  	  	});
	        
	        learn_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
            		determineCategory(CATEGORIES[3]);
	            }

	  	  	});
	        
	        nightlife_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
            		determineCategory(CATEGORIES[4]);
	            }

	  	  	});
	        
	        beauty_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
            		determineCategory(CATEGORIES[5]);
	            }

	  	  	});
	        
	        all_btn.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
            		determineCategory("");
	            }

	  	  	});
	        
	    }
	   
       
	    public void displayCompanyList(Cursor cursor) {

        	name.clear();
        	phone.clear();
    		addr.clear();
    		idnum.clear();
    		
	        if(cursor.moveToFirst()){
	        	do {
	        		String companyName = cursor.getString(1);
	        		String compAdd = cursor.getString(4);
	        		String phoneNumber = cursor.getString(5);
	        		int idNum = cursor.getInt(0);
	        		
	        		idnum.add(idNum);
	        		name.add(companyName);
	        		addr.add(compAdd);
	        		phone.add(phoneNumber);
				} while(cursor.moveToNext());
	        }
	    }
	    
	    public void determineLocation() {
	    	
	    	XMLDBHelper.open();
	    	
	        if(!location.equals("Manila") && !location.equals("Cebu") && !location.equals("Boracay")) {
	        	String[] position = location.split(",");
	        	
	        	companiesCursor = XMLDBHelper.fetchAllCompanyRows();
	        	startManagingCursor(companiesCursor);

	        	name.clear();
	        	phone.clear();
	    		addr.clear();
	    		idnum.clear();
	    		
		        if(companiesCursor.moveToFirst()){
		        	do {
		        		int id = companiesCursor.getInt(0);
		        		String companyName = companiesCursor.getString(1);
		        		String compAdd = companiesCursor.getString(4);
		        		String phoneNumber = companiesCursor.getString(5);
		        		double latitude = companiesCursor.getDouble(8);
		        		double longitude = companiesCursor.getDouble(9);     		
		        		if (Distance.calculateDistance(Double.parseDouble(position[0]), Double.parseDouble(position[1]), latitude, longitude, Distance.KILOMETERS) <= MAX_DISTANCE) {
			        		name.add(companyName);
			        		addr.add(compAdd);
			        		phone.add(phoneNumber);
			        		idnum.add(id);
						}
		        		
					}while(companiesCursor.moveToNext());
		        }
	        }
	        else {
		        companiesCursor = XMLDBHelper.fetchCompanyByLocation(location);
	        	startManagingCursor(companiesCursor);
		        displayCompanyList(companiesCursor);
	        }
		        
		        XMLDBHelper.close();
	    }
	    
	    public void determineCategory(String category) {
	    	
	        XMLDBHelper.open();
	        noData.setVisibility(View.GONE);
	        if(!location.equals("Manila") && !location.equals("Cebu") && !location.equals("Boracay")) {
	        	String[] position = location.split(",");
	        	
	        	companiesCursor = XMLDBHelper.fetchAllCompany(category);
	        	startManagingCursor(companiesCursor);

	        	name.clear();
	        	phone.clear();
	    		addr.clear();
	    		idnum.clear();
	    		
		        if(companiesCursor.moveToFirst()){
		        	do {
		        		int id = companiesCursor.getInt(0);
		        		String companyName = companiesCursor.getString(1);
		        		String compAdd = companiesCursor.getString(4);
		        		String phoneNumber = companiesCursor.getString(5);
		        		double latitude = companiesCursor.getDouble(8);
		        		double longitude = companiesCursor.getDouble(9);     		
		        		if (Distance.calculateDistance(Double.parseDouble(position[0]), Double.parseDouble(position[1]), latitude, longitude, Distance.KILOMETERS) <= MAX_DISTANCE) {
			        		name.add(companyName);
			        		addr.add(compAdd);
			        		phone.add(phoneNumber);
			        		idnum.add(id);
						}
		        		
					}while(companiesCursor.moveToNext());
		        }
		        
	        	if(name.isEmpty()) noData.setVisibility(View.VISIBLE);
		        gv.setAdapter(new CompanyListAdapter(CompanyList.this, name, addr, phone));
	        }
	        else {
	        	companiesCursor = XMLDBHelper.fetchCompanyByCategories(location, category);
	        	startManagingCursor(companiesCursor);
	        	displayCompanyList(companiesCursor);
		        
	        	if(name.isEmpty()) noData.setVisibility(View.VISIBLE);
	        	gv.setAdapter(new CompanyListAdapter(CompanyList.this, name, addr, phone));
	        }

	        	if(!location.equals("Manila") && !location.equals("Cebu") && !location.equals("Boracay")) {
	        			loc.setText("Current Location");
	        			if (category.equals("")) loc.setText("Current Location");
		        		else loc.setText("Current Location" + " - " + category);
	        	} else {
	        		if (category.equals("")) loc.setText(location);
	        		else loc.setText(location + " - " + category);
	        	}
	        	
		        XMLDBHelper.close();
	    }
}
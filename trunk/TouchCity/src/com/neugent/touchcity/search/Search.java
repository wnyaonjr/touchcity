package com.neugent.touchcity.search;




import java.util.ArrayList;

import com.neugent.touchcity.detail.Music;
import com.neugent.touchcity.home.SearchByLocation;
import com.neugent.touchcity.list.CompanyList;
import com.neugent.touchcity.list.CompanyListAdapter;
import com.neugent.touchcity.xmlparser.XMLDBHelper;
import com.neugent.touchcity.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Search extends Activity {
	
	
	private ContentResolver mContent;
	
	Cursor searchCursor;
	ArrayList<Integer> id = new ArrayList<Integer>();
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> addr = new ArrayList<String>(); 
   ArrayList<String> phone = new ArrayList<String>(); 
    
    String searchView;
    String location;
    int i;
	CharSequence constraint;
	  GridView gv;


	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);
        
        Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(Search.this,com.neugent.touchcity.home.TouchCity.class);
	        	startActivity(intent);
			}
		});
        
        
        Bundle b = getIntent().getExtras();
        i = b.getInt("arg");
        location = b.getString("loc");
        
        XMLDBHelper.open();
        
       if(i==1){
        searchCursor = XMLDBHelper.fetchAllCompanyRows();
        
       
       }
       else if(i==2){
        searchCursor = XMLDBHelper.fetchCompanyByLocation(location);

        
       }
       startManagingCursor(searchCursor);
       displayCompanyList(searchCursor);

       
        final AutoCompleteTextView actv = (AutoCompleteTextView)findViewById(R.id.actv);
        actv.setThreshold(0);
       
        SearchAdapter myCursorAdapter = new SearchAdapter(this, searchCursor, 0);
        actv.setAdapter(myCursorAdapter);
       
        
        actv.setOnItemClickListener(new
        		 AdapterView.OnItemClickListener(){
        		                public void onItemClick(AdapterView<?> av, View v, int position, long l)
        		 {
        		            
        		                	//XMLDBHelper.open();
        		                	if (i==1){
        		                	searchCursor = XMLDBHelper.fetchCompanyBySearchList(searchView);
        		                	}
        		                	else if (i==2){
        		                	searchCursor = XMLDBHelper.fetchCompanyBySearchListByLoc(searchView, location);
        		                	}
        		                		
        		                	startManagingCursor(searchCursor);
        		                	displayCompanyList(searchCursor);
        		              
        			            	gv.setAdapter(new CompanyListAdapter(Search.this, name, addr, phone));
        			            	
//        			            	XMLDBHelper.close();

        		                }
        		        });
        	
        gv = (GridView) findViewById(R.id.gv);
        
        
        
       gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long l) {
            	
        		Intent info2 =  new Intent(Search.this,com.neugent.touchcity.detail.Detail.class);
    			
        		Bundle b = new Bundle();
            	b.putString("name", name.get(position));
            	b.putInt("idnum", id.get(position));
            	info2.putExtras(b);
            	
        		startActivity(info2); 
    			
    			
            }

  	  	});
       
  
       
    }
    
  
    @Override
    protected void onDestroy() {
    	XMLDBHelper.close();
    	super.onDestroy();
    }
    
    

    public void displayCompanyList(Cursor cursor) {

    	id.clear();
    	name.clear();
    	addr.clear();
    	phone.clear();
    	
        if(cursor.moveToFirst()){
        	do {
        		int companyId = cursor.getInt(0);
        		String companyName = cursor.getString(1);
        		String compAdd = cursor.getString(4);
        		String phoneNumber = cursor.getString(5);
        		
        		id.add(companyId);
        		name.add(companyName);
        		addr.add(compAdd);
        		phone.add(phoneNumber + "\n");
			} while(cursor.moveToNext());
        }
     
    }
    
   
   
    public class SearchAdapter extends CursorAdapter implements Filterable{
    	private int columnIndex;


    	
		public SearchAdapter(Context context, Cursor c, int col) {
			super(context, c);
			this.columnIndex = col;
		}
	

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            
           final TextView view = (TextView) inflater.inflate(
                    android.R.layout.simple_spinner_item, parent, false);
          
            
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {        	
            ((TextView) view).setText(cursor.getString(columnIndex+1));
        }
        
        @Override
        public String convertToString(Cursor cursor) {
        
        	searchView = cursor.getString(columnIndex+1);
            return searchView;
        }
        
        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        	if(getFilterQueryProvider() != null)
        		return getFilterQueryProvider().runQuery(constraint);
        	
        	if (constraint != null) {
           	 
            	Cursor searchCur = null;
            	if (i==1){
            	searchCur = XMLDBHelper.fetchCompanyBySearchAllMain(constraint);
            	}
            	else if (i==2){
                searchCur = XMLDBHelper.fetchCompanyBySearchByLocMain(constraint,location);
                }
            	
            	startManagingCursor(searchCur);
            	
//            	new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						new DatabaseCloserThread();
//					}
//				}).start();
            	
            	 return searchCur;
            	 
            	
            }
            else {
            	return null;
            }

        }
        
    }
    
    private class DatabaseCloserThread implements Runnable{    	
		@Override
		public void run() {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			try {
				XMLDBHelper.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}}

    
    @Override
    protected void onPause() {
    	XMLDBHelper.close();
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	try {
    		XMLDBHelper.open();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	super.onResume();
    }
    
}
    
    
    




    

	

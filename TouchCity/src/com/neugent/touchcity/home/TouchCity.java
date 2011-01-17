
package com.neugent.touchcity.home;

import com.neugent.touchcity.R;
import com.neugent.touchcity.xmlparser.XMLDBHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


public class TouchCity extends Activity {
	private static final String[] HOMEPAGE = { "Nearby Establishments", "View by Location", "Search" };
	private ProgressDialog progressDialog;
	private Context context;
	Button b1, b2, b3;
        
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  this.context = this;
	  progressDialog = ProgressDialog.show(this, "Working", "Loading Data...");
      new Thread(new Runnable() {
			@Override
			public void run() {
				new XMLDBHelper(context, threadHandler);
			}
		}).start();
      
      this.setContentView(R.layout.home_layout);
      b1 = (Button) this.findViewById(R.id.button1);
      b2 = (Button) this.findViewById(R.id.button2);
      b3 = (Button) this.findViewById(R.id.button3);
      
      
      b1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  Intent intent =  new Intent(TouchCity.this,com.neugent.touchcity.googlemaps.MapsActivity.class);
        	  startActivity(intent);
        	  
          }
      });                

      b2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  Intent intent =  new Intent(TouchCity.this,com.neugent.touchcity.home.SearchByLocation.class);
        	  startActivity(intent);
          }
      }); 
      
      b3.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	 Intent intent =  new Intent(TouchCity.this,com.neugent.touchcity.search.Search.class);
     		 Bundle b = new Bundle();
     		 b.putString("loc", null);
     		 b.putInt("arg", 1); 
     		 intent.putExtras(b);
     		startActivity(intent);
          }
      }); 
      
      /*	  
	  setListAdapter(new ArrayAdapter<String>(this,
	          android.R.layout.simple_list_item_1, HOMEPAGE));
	  getListView().setTextFilterEnabled(true);*/
	}

	
	private Handler threadHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg) {
    		String message = msg.getData().getString("message");
    		progressDialog.dismiss();
    			
    	};
    };   
    
}

package com.neugent.touchcity.home;

import com.neugent.touchcity.R;
import com.neugent.touchcity.detail.Detail;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;


public class SearchByLocation extends Activity {
	private static final String[] LOCATION = { "Manila", "Cebu", "Boracay" };
	Button b1, b2, b3;
        
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
      
      this.setContentView(R.layout.choose_by_location);
      
      Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(SearchByLocation.this,com.neugent.touchcity.home.TouchCity.class);
	        	startActivity(intent);
			}
		});
      
      b1 = (Button) this.findViewById(R.id.button1);
      b2 = (Button) this.findViewById(R.id.button2);
      b3 = (Button) this.findViewById(R.id.button3);
      
      
      b1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  layoutOverlay(LOCATION[0]);
          }
      });                

      b2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  layoutOverlay(LOCATION[1]);
          }
      }); 
      
      b3.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
        	  //layoutOverlay("14.607412,121.080548");
        	  layoutOverlay(LOCATION[2]);
          }
      }); 
	}
	
	public void layoutOverlay(String location) {
		
    	Bundle b = new Bundle();
    	Intent intent = new Intent(SearchByLocation.this,com.neugent.touchcity.list.CompanyList.class);    	
    	b.putString("loc", location);
    	b.putString("message", "fromLocation");
    	b.putString("category", "");
    	intent.putExtras(b);
    	startActivity(intent);
	}
}
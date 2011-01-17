package com.neugent.touchcity.detail;

import com.neugent.touchcity.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Video extends Activity {
	
	private static final String PACKAGE_NAME = "android.resource://com.neugent.touchcity/";
	private String nameCompany = "";
	private Uri uri;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.videoview);
        
        String abaca = "abacá";
        String suite = "a.venue hotel suites";
        String ek = "enchanted kingdom";
        
        TextView Name_text = (TextView)this.findViewById(R.id.nameCom);
        
        Bundle b = getIntent().getExtras();
        nameCompany = b.getString("name_vid");
        
        Name_text.setText(nameCompany);
        //Toast.makeText(this, nameCompany , Toast.LENGTH_LONG).show();
        
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        VideoView videoHolder = (VideoView)this.findViewById(R.id.myvideo);
	    videoHolder.setMediaController(new MediaController(this));
	   
	    
	   /* if(nameCompany.equalsIgnoreCase(abaca)){
	    	uri = Uri.parse(PACKAGE_NAME + R.raw.abacavid);
	    }else if(nameCompany.equalsIgnoreCase(suite)) {
	    	uri = Uri.parse(PACKAGE_NAME + R.raw.avenuevid);
	    }else if(nameCompany.equalsIgnoreCase(ek)){
	    	uri = Uri.parse(PACKAGE_NAME + R.raw.ekvid);
	    }
	    
	    videoHolder.setVideoURI(uri);
	    videoHolder.requestFocus();
	    videoHolder.start();*/
	   
	    Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(Video.this,com.neugent.touchcity.home.TouchCity.class);
	        	startActivity(intent);
			}
		});
	    
	    
    }
}

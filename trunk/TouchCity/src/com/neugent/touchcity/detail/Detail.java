package com.neugent.touchcity.detail;

import com.neugent.touchcity.R;
import com.neugent.touchcity.list.CompanyList;
import com.neugent.touchcity.xmlparser.XMLDBHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Detail extends Activity {
	private static final String PACKAGE_NAME = "android.resource://com.neugent.touchcity.detail/";
	String name;
	int id_num;
	
	String abaca = "abacá";
    String suite = "a.venue hotel suites";
    String ek = "enchanted kingdom";
    String abe = "Abe Trinoma";
    
	String name_vid = "";
	Cursor myCursorMain;
	Cursor myCursorDetails;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.details_front);
        
        Button audio = (Button)findViewById(R.id.audio);
        Button video = (Button)findViewById(R.id.video);
        Button photo = (Button)findViewById(R.id.photo);
        
        TextView companyName = (TextView)this.findViewById(R.id.com_name);
        TextView companyAdd = (TextView)this.findViewById(R.id.com_add);
        TextView companyPhone = (TextView)this.findViewById(R.id.com_fon);
        TextView companyHours = (TextView)this.findViewById(R.id.com_hour);
        String company_name = "";
        String company_add = ""	;
        String company_phone = "";
        String company_hours = "";
        	
        TextView companyTag1 = (TextView)this.findViewById(R.id.tag1);
        TextView companyDes = (TextView)this.findViewById(R.id.com_des);
        String company_des = "";
        String company_tag1 = "";
        
        Bundle b = getIntent().getExtras();
        id_num = b.getInt("idnum", 0);
        name = b.getString("name");
        
        name_vid = name;
        
        XMLDBHelper.open();
        //xmldbHelper1.open();
        
       // System.out.println(id_num);
        
        myCursorMain  = XMLDBHelper.mainDetailQuery(id_num);
        startManagingCursor(myCursorMain);
        
        if(myCursorMain.moveToFirst()){
        	company_name= myCursorMain.getString(1);
        	company_add=myCursorMain.getString(4);
        	company_phone=myCursorMain.getString(5);
        	company_hours=myCursorMain.getString(6);
        }
        
        companyName.setText(company_name);
        companyAdd.setText(company_add);
        companyPhone.setText(company_phone);
        companyHours.setText(company_hours);
        //myCursorMain.close();
        XMLDBHelper.close();
        
        XMLDBHelper.open();
        myCursorDetails = XMLDBHelper.DetailQuery(name);
        startManagingCursor(myCursorDetails);
        
        
        String hasVisa = "";
        String hasVip = "";
        String hasPark = "";
        String hasChild = "";
        String hasMaster = "";
        String hasWifi = "";
        String hasDiner = "";
        String hasCard = "";
        String hasVoucher="";
        String hasMenu ="";
        String hasDelivery = "";
        
        if(myCursorDetails.moveToFirst()){
        	company_tag1 = "";
        	company_tag1 = myCursorDetails.getString(23);
        	company_des = myCursorDetails.getString(24);
        	
        	hasVisa = myCursorDetails.getString(7);
        	hasDelivery = myCursorDetails.getString(8);
            hasVip = myCursorDetails.getString(10);
            hasPark = myCursorDetails.getString(11);
            hasMenu = myCursorDetails.getString(12);
            hasChild = myCursorDetails.getString(13);
            hasMaster = myCursorDetails.getString(16);
            hasWifi = myCursorDetails.getString(17);
            hasDiner = myCursorDetails.getString(18);
            hasCard = myCursorDetails.getString(19);
            hasVoucher = myCursorDetails.getString(21);
            
        	//Toast.makeText(this, hasDelivery, Toast.LENGTH_LONG).show();
        
        }
        
        ImageView visa = (ImageView)findViewById(R.id.hasVisa); 
        ImageView delivery = (ImageView)findViewById(R.id.hasDelivery);
        ImageView vip = (ImageView)findViewById(R.id.hasVip); 
        ImageView park = (ImageView)findViewById(R.id.hasPark);
        ImageView menu = (ImageView)findViewById(R.id.hasMenu);
        ImageView child = (ImageView)findViewById(R.id.hasChild);
        ImageView master = (ImageView)findViewById(R.id.hasMaster);
        ImageView wifi = (ImageView)findViewById(R.id.hasWifi);
        ImageView diner = (ImageView)findViewById(R.id.hasDiner);
        ImageView card = (ImageView)findViewById(R.id.hasCard);
        ImageView voucher = (ImageView)findViewById(R.id.hasVoucher);
        
        if(hasVisa.equalsIgnoreCase("0")){
        	visa.setVisibility(View.GONE);
        }
        
        if(hasDelivery.equalsIgnoreCase("0")){
        	delivery.setVisibility(View.GONE);
        }
        
        if(hasVip.equalsIgnoreCase("0")){
        	vip.setVisibility(View.GONE);
        }
        
        if(hasPark.equalsIgnoreCase("0")){
        	park.setVisibility(View.GONE);
        }
        
        if(hasMenu.equalsIgnoreCase("0")){
        	menu.setVisibility(View.GONE);
        }
        
        if(hasChild.equalsIgnoreCase("0")){
        	child.setVisibility(View.GONE);
        }
        
        if(hasMaster.equalsIgnoreCase("0")){
        	master.setVisibility(View.GONE);
        }
        
        if(hasWifi.equalsIgnoreCase("0")){
        	wifi.setVisibility(View.GONE);
        }
        
        if(hasDiner.equalsIgnoreCase("0")){
        	diner.setVisibility(View.GONE);
        }
        
        if(hasCard.equalsIgnoreCase("0")){
        	card.setVisibility(View.GONE);
        }
        
        if(hasVoucher.equalsIgnoreCase("0")){
        	voucher.setVisibility(View.GONE);
        }
        companyTag1.setText(company_tag1);
        companyDes.setText(company_des);
        //System.out.println("error" + company_name);      
        //myCursorDetails.close();
        XMLDBHelper.close();
        //Toast.makeText(this, "" +id_num, Toast.LENGTH_LONG).show();
        photo.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				viewPhoto();
			}
        });
        
        audio.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				playMusic();
			}
        });
        
        video.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				playVideo();
			}
        });
        
        
        Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(Detail.this,com.neugent.touchcity.home.TouchCity.class);
	        	startActivity(intent);
			}
		});
        
        
    }
    
    
    void playMusic(){
//          Intent music = new Intent(Detail.this, Music.class);
//          
//          	if(name_vid.equalsIgnoreCase(ek)){
//		          Bundle b = new Bundle();
//		  		  b.putString("name_vid", name_vid);
//		  		  music.putExtras(b);
//		          startActivity(music);
//          	}else{
          		Toast.makeText(this, "No Available Audio", Toast.LENGTH_SHORT).show();
          		
//          	}
    }
    
    void playVideo(){
//    		Intent i = new Intent(Detail.this, Video.class);
//    		
//    		if(name_vid.equalsIgnoreCase(abaca) || name_vid.equalsIgnoreCase(suite)
//    				|| name_vid.equalsIgnoreCase(ek)){
//    			Bundle b = new Bundle();
//    			b.putString("name_vid", name_vid);
//    			i.putExtras(b);
//    			startActivity(i);
//    		}else{
    			Toast.makeText(this, "No Available Video", Toast.LENGTH_SHORT).show();
//    		}
    }
    
    void viewPhoto(){
    	Intent photo_v = new Intent(Detail.this, Photo.class);
    	
    	if(name_vid.equalsIgnoreCase(abaca) || name_vid.equalsIgnoreCase(suite)
				|| name_vid.equalsIgnoreCase(ek) || name_vid.equalsIgnoreCase(abe)){
			Bundle b = new Bundle();
			b.putString("name_vid", name_vid);
			photo_v.putExtras(b);
			startActivity(photo_v);
		}else{
			Toast.makeText(this, "No Available photo", Toast.LENGTH_SHORT).show();
		}
    	
    }
       
}


package com.neugent.touchcity.detail;

import com.neugent.touchcity.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class Music extends Activity{
	
	private MediaPlayer mediaplayer;
	
	private static final String PACKAGE_NAME = "android.resource://com.neugent.touchcity/";
	private VideoView videoHolder;
	//private String nameCompany = "";
	AssetFileDescriptor fileDesc;
	//private static final String PACKAGE_NAME = "android.resource://com.neugent.touchcity.detail/";
	//static final String AUDIO_PATH = "/sdcard/";
	
	Button playPauseButton;

	private String nameCompany;
	
	public static int STATE = 0;
	public static final int PLAYING = 0;
	public static final int PAUSED = 1;
	public static final int STOP = 2;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.music_player);
        
        TextView Name_text = (TextView)this.findViewById(R.id.title);
        
        Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(Music.this,com.neugent.touchcity.home.TouchCity.class);
	        	startActivity(intent);
			}
		});
        
        Bundle b = getIntent().getExtras();
        nameCompany = b.getString("name_vid");
        //nameCompany = "all flip-flops";
        
        Name_text.setText(nameCompany);
        
        if(nameCompany.equalsIgnoreCase("enchanted kingdom")){
        	
	        try {
				createMediaPlayer();
		        initComponents();
			} catch (Exception e) {
				System.out.println(""+e.getLocalizedMessage());			
			}
		
        }
                 
	}//oncreate
	
	
	
	private void initComponents() {
		videoHolder = (VideoView)this.findViewById(R.id.myvideo);		
		//videoHolder.setVideoURI(Uri.parse(PACKAGE_NAME + R.raw.visualization));
		videoHolder.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				videoHolder.start();				
			}
		});
		
		
		videoHolder.start();
		
		playPauseButton = (Button)findViewById(R.id.play_pause);

	      playPauseButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {
					switch (STATE) {
						case PLAYING:
							mediaplayer.pause();
							videoHolder.pause();
							playPauseButton.setBackgroundResource(R.drawable.play_img);
							STATE = PAUSED;
							break;
						case STOP:
							mediaplayer.start();
							videoHolder.start();
							playPauseButton.setBackgroundResource(R.drawable.pause_img);
							STATE = PLAYING;
							break;
						case PAUSED:
							mediaplayer.start();
							videoHolder.start();
							playPauseButton.setBackgroundResource(R.drawable.pause_img);
							STATE = PLAYING;
							break;
						
							
						default:
							break;
					}
				}
	        });
	        
	      /*
	       * 
	       
	        pausePlayerBtn.setOnClickListener(new OnClickListener(){

				public void onClick(View view) {
						mediaplayer.pause();
						videoHolder.pause();
				}
	        });
	       */ 
	      Button resetPlayerButton = (Button)findViewById(R.id.reset);
	      resetPlayerButton.setOnClickListener(new OnClickListener(){
				public void onClick(View view) {
					switch (STATE) {
						case PLAYING:
							videoHolder.seekTo(0);
							mediaplayer.seekTo(0);
							videoHolder.start();
							mediaplayer.start();
							break;
						case PAUSED:
							videoHolder.seekTo(0);
							videoHolder.start();
							videoHolder.pause();
							
							mediaplayer.seekTo(0);
							mediaplayer.start();
							mediaplayer.pause();
						case STOP:
							videoHolder.seekTo(0);
							videoHolder.start();
							videoHolder.pause();
							
							mediaplayer.seekTo(0);
							mediaplayer.start();
							mediaplayer.pause();
							
							break;
						default:
							break;
					}
					
				}
	        });
	      
	      Button stopButton = (Button) findViewById(R.id.stop);
	      stopButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				playPauseButton.setBackgroundResource(R.drawable.play_img);
				videoHolder.seekTo(0);
				videoHolder.start();
				videoHolder.pause();
				
				mediaplayer.seekTo(0);
				mediaplayer.start();
				mediaplayer.pause();
				
				STATE = STOP;
			}
		});

	}



	private void createMediaPlayer() throws Exception{        
        
		/*mediaplayer = MediaPlayer.create(this, R.raw.enchanted);
		mediaplayer.start();*/
	}
	
	protected void onDestroy(){
		super.onDestroy();
		killMediaPlayer();
	}
	
	private void killMediaPlayer(){
		if(mediaplayer != null){
			try{
				mediaplayer.release();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
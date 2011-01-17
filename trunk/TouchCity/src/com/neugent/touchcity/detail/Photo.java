package com.neugent.touchcity.detail;

import com.neugent.touchcity.R;

import java.io.FileInputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

public class Photo extends Activity{
	CoverFlow coverFlow;
	ImageAdapter coverImageAdapter;
	
	private String nameCompany = "";
    private String abaca = "abacá";
    private String suite = "a.venue hotel suites";
    private String abe = "Abe Trinoma";
    private String ek = "Enchanted Kingdom";
	
	Bitmap[] images; 
	private Integer[] mImageIds;
	
	private Integer[] abakada = {R.drawable.abacathumb1,R.drawable.abacathumb2,R.drawable.abacathumb3,
								R.drawable.gardenpool1,R.drawable.gardenpool2,
								R.drawable.master1,R.drawable.master2,
								R.drawable.restothumb1,R.drawable.restothumb2,R.drawable.restothumb3};
	
    private Integer[] ABE = {R.drawable.abe1,R.drawable.abe2,R.drawable.abe3};
    private Integer[] AVENUE = {R.drawable.avenue1,R.drawable.avenue2,R.drawable.avenue3,R.drawable.avenue4};
    private Integer[] ENCHANTED = {R.drawable.ek1,R.drawable.ek2,R.drawable.ek3,R.drawable.ek4,R.drawable.ek5};
    
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cover); 
		
		TextView Name_text = (TextView)this.findViewById(R.id.title);
       
        Bundle b = getIntent().getExtras();
        nameCompany = b.getString("name_vid");
        
        Name_text.setText(nameCompany);
        
        if(nameCompany.equalsIgnoreCase(abe)){
        	images = new Bitmap[3];
        	mImageIds = ABE;
        }else if(nameCompany.equalsIgnoreCase(suite)){
        	images = new Bitmap[4];
        	mImageIds = AVENUE;
        }else if(nameCompany.equalsIgnoreCase(ek)){
        	images = new Bitmap[5];
        	mImageIds = ENCHANTED;
        }else if(nameCompany.equalsIgnoreCase(abaca)){
        	images = new Bitmap[12];
        	mImageIds = abakada;
        }
		
		createThumb();
		coverImageAdapter =  new ImageAdapter(this);    
		coverFlow = new CoverFlow(this);
		coverFlow = (CoverFlow) findViewById(R.id.coverflow);     
		coverFlow.setAdapter(new ImageAdapter(this));		
		coverImageAdapter.createReflectedImages();     
		coverFlow.setAdapter(coverImageAdapter);
	
		coverFlow.setSpacing(0);
		coverFlow.setSelection(2, true);
		

		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(Photo.this,com.neugent.touchcity.home.TouchCity.class);
	        	startActivity(intent);
			}
		});
	
	}
		
	@Override
	protected void onDestroy() { 
    	deleteThumb();
    	System.gc();
    	super.onDestroy();
    }
	
	private void createThumb() {
		int index = 0;
		for (int imageId : mImageIds) {
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 15;
    		bfo.inPreferredConfig = Bitmap.Config.ARGB_8888;
    		
			images[index] = BitmapFactory.decodeResource(getResources(), imageId, bfo);
			index++;
		}
	}
	
	private void deleteThumb() {
		for (int index = 0; index < mImageIds.length; index++) {
			images[index] = null;			
		}
		images = null;
	}
	
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		
		private ImageView[] mImages;     
		public ImageAdapter(Context c) {
			mContext = c;
			mImages = new ImageView[mImageIds.length];
		}
		
		
		public boolean createReflectedImages(){
			final int reflectionGap = 2;         
	        int index = 0;
	        
	        for (int imageId : mImageIds) {
	        	Bitmap originalImage = BitmapFactory.decodeResource(getResources(), imageId);
	        	  //Bitmap originalImage = BitmapFactory.decode//images[index];
	        	  
	        	  int width = originalImage.getWidth();
	        	  int height = originalImage.getHeight();       	  
	        	  
	        	  //This will not scale but will flip on the Y axis
	        	  Matrix matrix = new Matrix();
	        	  matrix.preScale(1, -1);
	           
	        	  //Create a Bitmap with the flip matrix applied to it. We only want the bottom half of the image
	        	  Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);
	                          
	        	  //Create a new bitmap with same width but taller to fit reflection
	        	  Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Config.ARGB_8888);
	         
	        	  //Create a new Canvas with the bitmap that's big enough for the image plus gap plus reflection
	        	  Canvas canvas = new Canvas(bitmapWithReflection);
	        	  
	        	  //Draw in the original image
	        	  canvas.drawBitmap(originalImage, 0, 0, null);
	        	  
	        	  //Draw in the gap
	        	  Paint deafaultPaint = new Paint();
	        	  canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
	        	  
	        	  //Draw in the reflection
	        	  canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);
	          
	        	  //Create a shader that is a linear gradient that covers the reflection
	        	  Paint paint = new Paint(); 
	        	  LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, 
	        			  					bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP); 
	        	 
	        	  //Set the paint to use this shader (linear gradient)
	        	  paint.setShader(shader); 
	        	  
	        	  //Set the Transfer mode to be porter duff and destination in
	        	  paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
	        	 
	        	  //Draw a rectangle using the paint with our linear gradient
	        	  canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint); 
	          
	        	  ImageView imageView = new ImageView(mContext);
	        	  imageView.setImageBitmap(bitmapWithReflection);
	        	  imageView.setLayoutParams(new CoverFlow.LayoutParams(200, 350));
	        	  imageView.setScaleType(ScaleType.MATRIX);
	        	  mImages[index++] = imageView;
	        	  
	        	  
	        	  System.gc();
	        	  
	          }
	       return true;
		}
		
		
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageIds.length;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		      return mImages[position];
		}
		
		public float getScale(boolean focused, int offset) { 
	        /* Formula: 1 / (2 ^ offset) */ 
	          return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
	      } 
		
	}//////baseadapter
	
	
}///
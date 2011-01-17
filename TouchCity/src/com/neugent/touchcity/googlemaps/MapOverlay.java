package com.neugent.touchcity.googlemaps;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.view.MotionEvent;
import android.widget.Toast;

import com.neugent.touchcity.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MapOverlay extends Overlay{
	public static final int CIRCLERADIUS = 3000;
	private static final int ALPHA = 50;
	
	private Context context;
	private GeoPoint location;
	private Bitmap marker;
	private Paint circle;
	
	public static int X_OFFSET = 15;
	public static int Y_OFFSET = 15;
	
	public MapOverlay(Context context, GeoPoint location){
		this.context = context;
		this.location = location;
		initMarker();
		
	}
	private void initMarker() {
		marker = BitmapFactory.decodeResource(context.getResources(), R.drawable.current_location);
		
		circle = new Paint();
		circle.setColor(Color.BLUE);
		circle.setAlpha(ALPHA);
		circle.setAntiAlias(true);
		
	}
	@Override
    public boolean draw(Canvas canvas, MapView mapView, 
    boolean shadow, long when) 
    {
        super.draw(canvas, mapView, shadow);                   
        
        //access cursor, translate, draw
        //---translate the GeoPoint to screen pixels---
        try {
	  	  
	  	 //---add the marker---
       	 	Projection projection = mapView.getProjection();
       	 	Point point = new Point();
			projection.toPixels(location, point);
			
            float radius = projection.metersToEquatorPixels(CIRCLERADIUS);
            //draw the radius
            canvas.drawCircle(point.x, point.y, radius, circle);
            
            
    		
	  	    // Convert the location to screen pixels 
	  	    Point screenPts = new Point();
	  	    projection.toPixels(location, screenPts);
            
            //---add the marker---
            canvas.drawBitmap(marker, screenPts.x+X_OFFSET, screenPts.y-Y_OFFSET, null);
            
		} catch (Exception e) {
			return false;
		}
                 
		return true;
    }
	
	void setLocation(GeoPoint myLocation){
		this.location = myLocation;
	}
	 
}

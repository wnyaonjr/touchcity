package com.neugent.touchcity.googlemaps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Projection;
import com.neugent.touchcity.R;

public class MyLocationOverlayWithRangeMarker extends MyLocationOverlay{

	public static final int CIRCLERADIUS = 3000;
	private static final int ALPHA = 50;
	
	public MyLocationOverlayWithRangeMarker(Context context, MapView mapView) {
		super(context, mapView);
	}
	
	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {
		
		try {
			// Transfrom geoposition to Point on canvas
			Projection projection = mapView.getProjection();
			Point point = new Point();
			projection.toPixels(myLocation, point);
			float radius = projection.metersToEquatorPixels(CIRCLERADIUS);
	
			// the circle to mark the spot
			Paint circle = new Paint();
			circle.setColor(Color.BLUE);
			circle.setAlpha(ALPHA);
			circle.setAntiAlias(true);
			
			//canvas.drawRoundRect(rect, 2, 2, background);
			canvas.drawCircle(point.x, point.y, radius, circle);
			//canvas.drawText("My Location", point.x + 3 * CIRCLERADIUS, point.y + 3* CIRCLERADIUS, text);

			double maxLat = (myLocation.getLatitudeE6()/1E6) + radius;
			double minLat = (myLocation.getLatitudeE6()/1E6) - radius;
			double maxLong = (myLocation.getLongitudeE6()/1E6) + radius;
			double minLong = (myLocation.getLongitudeE6()/1E6) - radius;
			
			
			//Log.i("Values: ", "Latitude Range:"+minLat+"-"+maxLat+" "+"Longitude Range:"+minLong+"-"+maxLong);

			
			super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
		} catch (Exception e) {
			super.drawMyLocation(canvas, mapView, lastFix, myLocation, when);
		}
	}

}

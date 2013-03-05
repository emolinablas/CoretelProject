package com.researchmobile.coretel.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class LinePathOverlay extends Overlay {
	 private GeoPoint GeoPointInicio;
	 private GeoPoint GeoPointFin; 
	 private Projection projection; 
	 private String HexColor = "#eda62b";
	 
	 public LinePathOverlay(GeoPoint geoPointInicio,GeoPoint geoPointFin, String color){
	  if (color.length() > 0)
	   HexColor = "#" + color;
	  this.GeoPointInicio = geoPointInicio;
	  this.GeoPointFin = geoPointFin;
	    }  
	 
	    public LinePathOverlay(GeoPoint geoPointInicio,GeoPoint geoPointFin){
	     this.GeoPointInicio = geoPointInicio;
	     this.GeoPointFin = geoPointFin;
	    }   

	    public void draw(Canvas canvas, MapView mapview, boolean shadow){
	        super.draw(canvas, mapview, shadow);
	        
	        
	        Paint   mPaint = new Paint();
	        mPaint.setDither(true);
	        
	        mPaint.setColor(Color.parseColor(HexColor));
	        
	        
	        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	        mPaint.setStrokeJoin(Paint.Join.ROUND);
	        mPaint.setStrokeCap(Paint.Cap.ROUND);
	        mPaint.setStrokeWidth(4);

	        Point p1 = new Point();
	        Point p2 = new Point();
	        
	 projection = mapview.getProjection(); 
	        projection.toPixels(this.GeoPointInicio, p1);
	        projection.toPixels(this.GeoPointFin, p2);
	        
	        Path  path = new Path();
	        path.moveTo(p2.x, p2.y);
	        path.lineTo(p1.x,p1.y);

	        canvas.drawPath(path, mPaint);
	    }

	}

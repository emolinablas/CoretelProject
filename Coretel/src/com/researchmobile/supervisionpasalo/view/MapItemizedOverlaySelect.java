package com.researchmobile.supervisionpasalo.view;

import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapItemizedOverlaySelect extends Overlay {

	 private int LONGPRESS_TIME = 500;
	 private long timeInitPress = 0;
	 private long timeFinishPress = 0;
	 
	 private int latitud = 0;
	 private int longitud = 0;

	 private GeoPoint lastMapCenter;
	 
	 private boolean isLongPress(){
	  return ((timeInitPress >= 0) && ((timeFinishPress - timeInitPress) > LONGPRESS_TIME));   
	 }
	 
	 public interface OnSelectPOIListener{void onSelectPOI(int Latitud, int Longitud);}
	 private OnSelectPOIListener listenerSelectPOI; 
	 public void setOnSelectPOIListener(OnSelectPOIListener l){listenerSelectPOI = l;}
	}


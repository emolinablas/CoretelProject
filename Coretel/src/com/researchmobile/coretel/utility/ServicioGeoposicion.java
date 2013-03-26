package com.researchmobile.coretel.utility;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServicioGeoposicion extends Service {
	 private Timer mTimer = null; 

	 
	 @Override
	 public void onCreate(){
	  super.onCreate();
	  this.mTimer = new Timer();
	  this.mTimer.scheduleAtFixedRate(
	    new TimerTask(){
	     @Override
	     public void run() {
	      ejecutarTarea();
	     }      
	    }
	    , 0, 1000 * 60);
	 }
	 
	 private void ejecutarTarea(){
	  Thread t = new Thread(new Runnable() {
	   public void run() {
	   System.out.println("se envi— la geoposici—n"); 
	    
	   }
	  });  
	  t.start();
	 }

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	}
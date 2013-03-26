package com.researchmobile.coretel.utility;

import java.util.Timer;
import java.util.TimerTask;

import com.researchmobile.coretel.entity.User;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServicioGeoposicion extends Service {
	 private Timer mTimer = null; 

	 @Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	 
	 @Override
	 public void onCreate(){
	  super.onCreate();
	  this.mTimer = new Timer();
	  this.mTimer.scheduleAtFixedRate(
	    new TimerTask(){
	     @Override
	     public void run() {
	    	 Log.v("pio", "antes de llegar a la tarea");
	      ejecutarTarea();
	     }      
	    }
	    , 0, 1000 * 1);
	 }
	 
	 private void ejecutarTarea(){
		  Thread t = new Thread(new Runnable() {
			   public void run() {
				   
				   User.setLatitudActual("1");
				   User.setLongitudActual("1");
				
				   // ACA ENVIABA LA NOTIFICACIÓN EL SERVICIO SUPUESTAMENTE.... 
				   // AHORA ACÁ ES DONDE DEBE CAPTURAR LA GEOPOSICIÓN Y ACTUALIZAR VARIBLES GLOBALES.
				  // Utiliza el GetApplicationContext() así como se ve aquí abajo
				/*   
			    NotifyManager notify = new NotifyManager();
			    notify.playNotification(getApplicationContext(),
			      Mapa.class, "Tienes una notificación"
			      , "Notificación", R.drawable.arrow_right); */
			    
			   }
			  });  
			  t.start();
	 }
	

	}
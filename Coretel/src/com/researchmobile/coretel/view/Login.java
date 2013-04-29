package com.researchmobile.coretel.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import oauth.signpost.OAuth;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.ChatUtility;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.utility.RMFile;
import com.researchmobile.coretel.ws.RequestDB;
import com.researchmobile.coretel.ws.RequestWS;

public class Login extends Activity implements OnClickListener, OnKeyListener{
    /** Called when the activity is first created. */
     private static final String LOGTAG = "MYLOG";
     private static final String LLENAR_CAMPOS = "Llene todos los campos";
     private EditText usuarioEditText;
     private EditText claveEditText;
     private Button entrarButton;
     private Button registrarButton;
     private Button salirButton;
     // Botones para conexion con redes sociales
     private boolean twitter_active = false;
     private LoginButton authButton;
     private Button twLogin;
     
     private TextView statusConexion;
     
     private static CommonsHttpOAuthProvider provider =
    	        new CommonsHttpOAuthProvider(
    	        "https://api.twitter.com/oauth/request_token",
    	        "https://api.twitter.com/oauth/access_token",
    	        "https://api.twitter.com/oauth/authorize");
    	 
    	private static CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer("vYXZb4ScOp6NnV90m9PygQ","vPPm08LFDlEOzZBANEmxCDYzH6DG8vsYMZAKV9CzQDc");
    	
     // ----- Variables para guardar las llaves de twitter
    	private static String ACCESS_KEY = null;
    	private static String ACCESS_SECRET = null;
    	private OnClickListener twitter_auth, twitter_clearauth;
    //  -----
     private TextView recuperarTextView;
     private Mensaje mensaje;
     private User user;
     private CatalogoComunidad catalogoComunidad;
     private CatalogoAnotacion catalogoAnotacion;
     private ProgressDialog pd = null;
     private RequestWS requestWS;
     private RespuestaWS respuesta;
     private RequestWS requestWSGeo;
     private RespuestaWS respuesta2Geo;
     private AlertDialog.Builder dialogActiveGPS = null;
     private RMFile rmFile = new RMFile();
     private Session.StatusCallback statusCallback = new SessionStatusCallback();
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        User.setLatitudActual("0");
        User.setLongitudActual("0");
      //Intent service = new Intent(this, ServicioGeoposicion.class);
	  //startService(service);
        try{
        	System.out.println(Login.this);
            setUser(new User());
            setMensaje(new Mensaje());
            setRespuesta(new RespuestaWS());
            setUsuarioEditText((EditText)findViewById(R.id.login_usuario_edittext));
            setClaveEditText((EditText)findViewById(R.id.login_clave_edittext));
            setEntrarButton((Button)findViewById(R.id.login_entrar_button));
            setRegistrarButton((Button)findViewById(R.id.login_registrar_button));
            // --- Set de botones de redes sociales
            setAuthButton((LoginButton)findViewById(R.id.facebook_button));
            authButton.setReadPermissions(Arrays.asList("email,basic_info,friends_online_presence"));
            setTwLogin((Button)findViewById(R.id.twitter_button));
            setStatusConexion((TextView)findViewById(R.id.status_conexion));
            // ---
            setRecuperarTextView((TextView)findViewById(R.id.login_recuperar_textview));
            setSalirButton((Button)findViewById(R.id.loginpasalo_salir_button));
            getSalirButton().setOnClickListener(this);
            getRecuperarTextView().setOnClickListener(this);
            getRegistrarButton().setOnClickListener(this);
            getClaveEditText().setOnKeyListener(this);
            getUsuarioEditText().setOnKeyListener(this);
            getEntrarButton().setOnClickListener(this);
            getAuthButton().setOnClickListener(this);
            getUsuarioEditText().setText("");
            getClaveEditText().setText("");
            setRequestWS(new RequestWS());
            verificaGps();
        }catch(Exception exception){
             Log.i(LOGTAG, exception.getLocalizedMessage());
        }
        
        // --- VERIFICACION DE ACCESO A TWITER
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String stored_keys = prefs.getString("KEY", "");
        String stored_secret = prefs.getString("SECRET", "");
         
        if (!stored_keys.equals("") && !stored_secret.equals("")) {
            twitter_active = true;
        }
        
        //--- Primer Listener para autorizacion
        twitter_auth = new OnClickListener() {
            @Override
            public void onClick(View v) {
                statusConexion.setText("Twitter status: iniciando sesi—n");
         
                try {
                	System.out.println("Antes de hacer la llamada a twiter");
                    String authUrl = provider.retrieveRequestToken(consumer, "pasalo://twitter");
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
                    System.out.println("DESPUES DEL URI");
                } catch (OAuthMessageSignerException e) {
                	System.out.println("1");
                    e.printStackTrace();
                } catch (OAuthNotAuthorizedException e) {
                	System.out.println("2");
                    e.printStackTrace();
                } catch (OAuthExpectationFailedException e) {
                	System.out.println("3");
                    e.printStackTrace();
                } catch (OAuthCommunicationException e) {
                	System.out.println("4");
                    e.printStackTrace();
                }
         
            }
        };
        
        // --- Segundo Listener para LogOut
        
        twitter_clearauth = new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("KEY", null);
                editor.putString("SECRET", null);
                editor.commit();
                twLogin.setText("Autorizar twitter");
                statusConexion.setText("Twitter status: sesi—n no iniciada ");
                twLogin.setOnClickListener(twitter_auth);
         
            }
        };
        
        // -- Verificacion de estado para saber si esta activa la sesion de Twitter
        if (twitter_active) {
            statusConexion.setText("Twitter status: sesi—n iniciada ");
            twLogin.setText("Deautorizar twitter");
            twLogin.setOnClickListener(twitter_clearauth);
        } else {
            twLogin.setText("Autorizar twitter");
            twLogin.setOnClickListener(twitter_auth);
        }
        
        // ---
        
        // --- VERIFICACION DE ACCESO A FACEBOOK
        
        com.facebook.Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }
        updateView();
        // ---
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Uri uri = this.getIntent().getData();  
    	if (uri != null && uri.toString().startsWith("pasalo://twitter")) {
    	    String verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
    	    try {    	    	
    	    	provider.retrieveAccessToken(consumer,verifier);
    			ACCESS_KEY = consumer.getToken();
    			ACCESS_SECRET = consumer.getTokenSecret();
    			
    			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    		    SharedPreferences.Editor editor = prefs.edit();    		    
    		    editor.putString("KEY", ACCESS_KEY);
    		    editor.putString("SECRET", ACCESS_SECRET);
    		    editor.commit();
    		    
    			TextView txtTwStatus = (TextView) this.findViewById(R.id.status_conexion);
	            txtTwStatus.setText("Twitter status: sesi—n iniciada ");
	            
				twLogin.setText("Deautorizar twitter");
	            twLogin.setOnClickListener(twitter_clearauth);

			} catch (OAuthMessageSignerException e) {
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				e.printStackTrace();
			}
    	    
    	} 
    	   	
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }
    
    private void onClickLogout() {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }
    
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this)
                .setPermissions(Arrays.asList("email,basic_info,friends_online_presence"))
                .setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }
    
    private void updateView() {
        Session session = Session.getActiveSession();
        if (session.isOpened()) {
            //textInstructionsOrLink.setText(URL_PREFIX_FRIENDS + session.getAccessToken());
            authButton.setText("Salir");
            authButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogout(); }
            });
        } else {
           // textInstructionsOrLink.setText(R.string.instructions);
            authButton.setText("Salir");
            authButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) { onClickLogin(); }
            });
        }
    }
    
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
        	System.out.println("Ingreso a CALL");
                // Respond to session state changes, ex: updating the view
        	  if (session.isOpened()) {
        		  System.out.println("Ingreso a IsOpened");
		          // make request to the /me API
		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		        	 
		            // callback after Graph API response with user object
		       

					@Override
					public void onCompleted(GraphUser user, Response response) {
						// TODO Auto-generated method stub
						 if (user != null) {
				              //  TextView welcome = (TextView) findViewById(R.id.user_facebook);
				              // welcome.setText("Hello " + user.getName() + "!" + user.getUsername());
				               JSONObject jsonObject = user.getInnerJSONObject();
				               System.out.println("OBJETOJSON : "+jsonObject.toString());
				               
				               
				              }
					}
					
		          });
		        }
        } }

     @Override
	public void onClick(View view) {
		
			if (view == getEntrarButton()) {
				new LoginAsync().execute("");
			}else if (view == getRegistrarButton()){
				registrar();
			}else if (view == getRecuperarTextView()){
				recuperarPassword();
			}else if (view == getSalirButton()){
				salir();
			}
		
	}
     
     public void salir(){
    	 Intent intent = new Intent(Login.this, Principal.class);
    	 startActivity(intent);
     }
     
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
        	    salir();
             return true;
         }
         
         return super.onKeyDown(keyCode, event);
     }

     private void recuperarPassword(){
    	 Intent intent = new Intent(Login.this, Recuperar.class);
    	 startActivity(intent);
     }
     
     private void registrar(){
    	 Intent intent = new Intent(Login.this, Registrar.class);
    	 startActivity(intent);
     }
     
     private void Entrar() {
          Requerido();
     }
     
    
     private boolean Requerido() {
    	 ConnectState connectState = new ConnectState();
    	 if(connectState.isConnectedToInternet(this)){
          if (CamposLlenos()){
               RequestWS request = new RequestWS();
               setRespuesta(request.Login(this, getUser()));
               if (getRespuesta().isResultado()){
            	   verificaModoTutorial();
//            	   createDirIfNotExists();
             	   rmFile.downloadImage(User.getAvatar());
//            	   CargarAnotaciones();
//            	   RegistrarChat();
                    return true;
               }else{
            	   return false;
               }
          }
          return false;
    	 }else{
    		 Toast.makeText(this, "No posee conexion a internet, intente mas tarde", Toast.LENGTH_SHORT).show();
    		 return false;
    	 }
     }
     
     public void verificaModoTutorial(){
    	 RequestDB rqDb = new RequestDB();
    	 rqDb.verificaModoTutorial(this);
     }

    
	public static boolean createDirIfNotExists() {
    	    boolean ret = true;
    	    File file = new File(Environment.getExternalStorageDirectory(), "/pasalo/" + User.getAvatar());
    	    if (!file.exists()) {
    	    	Log.e("TT", "Problem creating Image folder");
    	    	if (!file.mkdirs()) {
    	    		Log.e("TT", "Problem creating Image folder");
    	        	File avatarDirectory = new File("/sdcard/pasalo/img/avatars/");
    	        	avatarDirectory.mkdirs();
    	        	try{
    	        		Log.e("TT", "Problem creating Image folder");
    	        		URL url = new URL("http://23.23.1.2/img/avatars/avatar.png");
    	        		Log.e("TT", "Problem creating Image folder");
        				URLConnection conexion = url.openConnection();
        				conexion.connect();
        				InputStream input = new BufferedInputStream(url.openStream());
        				OutputStream output = new FileOutputStream("/sdcard/pasalo/" + User.getAvatar());
        				Log.e("TT", "Problem creating Image folder");
        				byte datos[] = new byte[1024];
        				int cuenta;
        				long total = 0;
        				
        				while ((cuenta = input.read(datos)) != -1){
        					Log.e("TT", "Problem creating Image folder");
        					total += cuenta;
        					output.write(datos, 0, cuenta);
        				}
        	            Log.e("TT", "Problem creating Image folder");
    	        	}catch(Exception exception){
    	        		
    	        	}
    	        	ret = false;
    	        }
    	    }
    	    return ret;
    	}
     private void RegistrarChat() {
    	 ChatUtility registerChar = new ChatUtility();
    	 registerChar.ChatId(Login.this);
	}

	private void CargarAnotaciones() {
		//Cargar Anotaciones(idcomunidad, idtipoanotacion)
		RequestWS request = new RequestWS();
		setCatalogoAnotacion(request.CargarAnotaciones());
		System.out.println(getCatalogoAnotacion().getRespuesta().getMensaje());
	}

	private boolean CamposLlenos() {
          String usernameString = getUsuarioEditText().getText().toString();
          String passwordString = getClaveEditText().getText().toString();
          if (getUsuarioEditText().getText().toString().equalsIgnoreCase("") || getClaveEditText().getText().toString().equalsIgnoreCase("")){
               getRespuesta().setResultado(false);
               getRespuesta().setMensaje("debe llenar los campos");
               return false;
          }else{
               getUser().setPassword(passwordString);
               getUser().setUsername(usernameString);
               return true;
          }
     }

     @Override
     public boolean onKey(View view, int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
         {
               if (view == getUsuarioEditText()){
                   getClaveEditText().requestFocus();
               }else if (view == getClaveEditText()){
            	   new LoginAsync().execute("");
               }
             return true;
         }
         if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
         {
             //TODO: When the enter key is pressed
             return true;
         }
         return false;

     }
     
     //Clase para ejecutar en Background
     class LoginAsync extends AsyncTask<String, Integer, Integer>{

    	 //Metodo que prepara lo que usara en background, Prepara el progress
 		@Override
 		protected void onPreExecute(){
 			pd = ProgressDialog.show(Login.this, "VERIFICANDO DATOS","ESPERE UN MOMENTO");
 			pd.setCancelable(false);
 		}
 		
 		//Metodo con las instrucciones que se realizan en background
 		@Override
 		protected Integer doInBackground(String... urlString) {
 			try{
 				Entrar();
 			}catch(Exception exception){
 				
 			}
 			return null;
 		}
 		//Metodo con las instrucciones al finalizar lo ejectuado en background
 		protected void onPostExecute(Integer resultado){
 			pd.dismiss();
 			if (getRespuesta() != null){
 				Toast.makeText(getBaseContext(), getRespuesta().getMensaje(), Toast.LENGTH_SHORT).show();
 				if(getRespuesta().isResultado()){
 					
 					try{
 					servicioGeoposicion(); // Activa el servicio que actualiza las variables de geoposici—n
 					creaServicio(); // Activa el servicio que envia la geoposici—n
 					Log.v("pio", "Se ejecuto el servicio");
 					}catch(Exception e){
 						Log.v("pio", "ocurrio un error al activar el serviio");
 					}
 	 				Intent intent = new Intent(Login.this, Mapa.class);
 	 	            startActivity(intent);
 	 			}
 			}
 			
 			
 		}

		private void LimpiaCampos() {
			getClaveEditText().setText("");
			getUsuarioEditText().setText("");
		}
     }
     
     private void creaServicio(){
			//Intent service = new Intent(this, ServicioGeoposicion.class);
			//	startService(service);
    	 User.setCompartirGeoposicion(true);
    	 HandlerThread hilo;
    	 hilo=new HandlerThread("hilo_geoposicion");
         hilo.start();
         new Handler(hilo.getLooper()).post(
             new Runnable() {
                 @Override
                 public void run()
                 {
                     while(User.isCompartirGeoposicion())
                     {
                       
                      try
                      {
                         //Aqui lo que quieres hacer
                    	  Log.v("pio", "Se ejecuto el hilo");
                    	 // Toast.makeText(getApplicationContext(), "se envio la geoposici—n", Toast.LENGTH_SHORT);
                    	  RequestWS request2 = new RequestWS();
                          setRespuesta2Geo(request2.EnviarGeoposicion(User.getUserId(), User.getLatitudActual(), User.getLongitudActual()));
                          Log.v("geo", "se envio la siguiente geoposicion " + User.getLatitudActual() + " " + User.getLongitudActual());
                    	  
                    	 /*
                    	  NotifyManager notify = new NotifyManager();
          			    notify.playNotification(getApplicationContext(),
          			      Mapa.class, "Se envio tu geoposicion"
          			      , "Notificaci—n", R.drawable.arrow_right); 
          			    */
                         Thread.sleep(1000 * 60);
                      }catch (Exception e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                         try {
                             //Por si ocurre algun problema para que no se ejecute sin parar y se sobrecarga
                             Thread.sleep(1000 *60);
                         } catch (InterruptedException e1) {
                             // TODO Auto-generated catch block
                             e1.printStackTrace();
                         }
                     }
                      
                     }
                 }
             });
     }
     
     private void servicioGeoposicion(){
			
 	 HandlerThread hilo2;
 	 hilo2=new HandlerThread("hilo_geoposicion2");
      hilo2.start();
      new Handler(hilo2.getLooper()).post(
          new Runnable() {
              @Override
              public void run()
              {
                  while(User.isCompartirGeoposicion())
                  { Log.v("geo", "se ingreso al hilo de geoposicion a espera de un dato.");
                    
                   try
                   {
						LocationManager locationService = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
						LocationListener myLocationListener = new LocationListener() {
							
							@Override
							public void onStatusChanged(String provider, int status, Bundle extras) {
								Log.v("geo", "capturando geoposicion 1");
								
							}
							
							@Override
							public void onProviderEnabled(String provider) {
								Log.v("geo", "capturando geoposicion 2");
								
							}
							
							@Override
							public void onProviderDisabled(String provider) {
								Log.v("geo", "capturando geoposicion 3");
								
							}
							
							@Override
							public void onLocationChanged(Location location) {
								Log.v("geo", "capturando geoposicion");
								User.setLatitudActual(String.valueOf(location.getLatitude()));
					 			User.setLongitudActual(String.valueOf(location.getLongitude()));
					 		}
						};
						locationService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,myLocationListener);
						Log.v("geo", "iniciando gps.");
						
						
						
						
						
						
						
						
                	   
//                      //Aqui lo que quieres hacer
//                	 //Obtenemos una referencia al LocationManager
//                   LocationManager	locationManager = 
//                   		(LocationManager)getSystemService(Context.LOCATION_SERVICE);
//                   	//locationManager(LocationManager)getSystemService(name)
//                   	
//                   	//Obtenemos la última posición conocida
//                   	Location location = 
//                   		locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                   	
//                   	//Mostramos la última posición conocida
//                   	//muestraPosicion(location);
//                   	
//                   	//Nos registramos para recibir actualizaciones de la posición
//                   	LocationListener locationListener = new LocationListener() {
//               	    	public void onLocationChanged(Location location) {
//               	    		//muestraPosicion(location);
//               	    		
//               	    		User.setLatitudActual(String.valueOf(location.getLatitude()));
//               	    		User.setLongitudActual(String.valueOf(location.getLongitude()));
//               	    		Log.v("geo", "Se sete— esta posici—n" + String.valueOf(location.getLatitude()) + " " + String.valueOf(location.getLongitude()));
//               	    	}
//               	    	public void onProviderDisabled(String provider){
//               	    		//lblEstado.setText("Provider OFF");
//               	    		//locationManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 0, LocationListener2);
//               	    	}
//               	    	public void onProviderEnabled(String provider){
//               	    		//lblEstado.setText("Provider ON");
//               	    	}
//               	    	public void onStatusChanged(String provider, int status, Bundle extras){
//               	    		Log.i("LocAndroid", "Provider Status: " + status);
//               	    		//lblEstado.setText("Provider Status: " + status);
//               	    	}
//                   	};
//                   	
//                   	locationManager.requestLocationUpdates(
//                   			LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
//                 	 
                      Thread.sleep(1000 * 60);
                   }catch (Exception e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                      try {
                          //Por si ocurre algun problema para que no se ejecute sin parar y se sobrecarga
                          Thread.sleep(1000 *60);
                      } catch (InterruptedException e1) {
                          // TODO Auto-generated catch block
                          e1.printStackTrace();
                      }
                  }
                   
                  }
              }
          });
  }
     
     public class MyLocationListener implements LocationListener {
 		public void onLocationChanged(Location location) {
 			User.setLatitudActual(String.valueOf(location.getLatitude()));
 			User.setLongitudActual(String.valueOf(location.getLongitude()));
 			
 			LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
 			LocationListener locationListener = new MyLocationListener();
 			locationManager.removeUpdates(locationListener);
 		}

 		public void onProviderDisabled(String provider) {
 			//Toast.makeText(getApplicationContext(), "Gps Desactivado", Toast.LENGTH_SHORT).show();
 			
 		}

 		public void onProviderEnabled(String provider) {
 			//Toast.makeText(getApplicationContext(), "Gps Activo", Toast.LENGTH_SHORT).show();
 		}

 		public void onStatusChanged(String provider, int status, Bundle extras) {
 		}
 	}
     
     private void verificaGps(){
    	 /**
          * Crate Dialog
          */
         dialogActiveGPS = new AlertDialog.Builder(this);
         AlertDialog.Builder dialogActiveGPS = new AlertDialog.Builder(this);
         dialogActiveGPS.setMessage("¿Desea activarlo ahora?");
         dialogActiveGPS.setCancelable(false);
         dialogActiveGPS.setPositiveButton("Si", new DialogInterface.OnClickListener() {
 			
 			@Override
 			public void onClick(DialogInterface dialog, int which) {
 				Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
 			    startActivity(myIntent);
 			}
 		});
         
         dialogActiveGPS.setNegativeButton("No", new DialogInterface.OnClickListener() {
 			
 			@Override
 			public void onClick(DialogInterface dialog, int which) {
// 				finish();
 				
 			}
 		});
         
   /*      LocationManager locationService = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
         LocationListener myLocationListener = new MyLocationListener2();
 		locationService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, myLocationListener);
 		if(!locationService.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
 		{
 			AlertDialog alert = dialogActiveGPS.create();
 	        alert.setTitle("NECESITA ACTIVAR GPS");
 	        alert.show();
 		}
 		*/
     }
     
  /*   public class MyLocationListener2 implements LocationListener {
  		public void onLocationChanged(Location location) {
  			LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
  			LocationListener locationListener = new MyLocationListener2();
  			locationManager.removeUpdates(locationListener);
  		}
  		
  		public void onProviderDisabled(String provider) {
 			//Toast.makeText(getApplicationContext(), "Gps Desactivado", Toast.LENGTH_SHORT).show();
 			
 		}

 		public void onProviderEnabled(String provider) {
 			//Toast.makeText(getApplicationContext(), "Gps Activo", Toast.LENGTH_SHORT).show();
 		}

 		public void onStatusChanged(String provider, int status, Bundle extras) {
 		}
  	 }*/

     public EditText getUsuarioEditText() {
          return usuarioEditText;
     }

     public void setUsuarioEditText(EditText usuarioEditText) {
          this.usuarioEditText = usuarioEditText;
     }

     public EditText getClaveEditText() {
          return claveEditText;
     }

     public void setClaveEditText(EditText claveEditText) {
          this.claveEditText = claveEditText;
     }

     public Button getEntrarButton() {
          return entrarButton;
     }

     public void setEntrarButton(Button entrarButton) {
          this.entrarButton = entrarButton;
     }

     public Mensaje getMensaje() {
          return mensaje;
     }

     public void setMensaje(Mensaje mensaje) {
          this.mensaje = mensaje;
     }

     public User getUser() {
          return user;
     }

     public void setUser(User user) {
          this.user = user;
     }

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}

	public CatalogoAnotacion getCatalogoAnotacion() {
		return catalogoAnotacion;
	}

	public void setCatalogoAnotacion(CatalogoAnotacion catalogoAnotacion) {
		this.catalogoAnotacion = catalogoAnotacion;
	}

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public Button getRegistrarButton() {
		return registrarButton;
	}

	public void setRegistrarButton(Button registrarButton) {
		this.registrarButton = registrarButton;
	}

	public TextView getRecuperarTextView() {
		return recuperarTextView;
	}

	public void setRecuperarTextView(TextView recuperarTextView) {
		this.recuperarTextView = recuperarTextView;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public Button getSalirButton() {
		return salirButton;
	}

	public void setSalirButton(Button salirButton) {
		this.salirButton = salirButton;
	}

	public RequestWS getRequestWSGeo() {
		return requestWSGeo;
	}

	public void setRequestWSGeo(RequestWS requestWSGeo) {
		this.requestWSGeo = requestWSGeo;
	}

	public RespuestaWS getRespuesta2Geo() {
		return respuesta2Geo;
	}

	public void setRespuesta2Geo(RespuestaWS respuesta2Geo) {
		this.respuesta2Geo = respuesta2Geo;
	}

	public LoginButton getAuthButton() {
		return authButton;
	}

	public void setAuthButton(LoginButton authButton) {
		this.authButton = authButton;
	}

	public Button getTwLogin() {
		return twLogin;
	}

	public void setTwLogin(Button twLogin) {
		this.twLogin = twLogin;
	}


	public TextView getStatusConexion() {
		return statusConexion;
	}


	public void setStatusConexion(TextView statusConexion) {
		this.statusConexion = statusConexion;
	}
	
}
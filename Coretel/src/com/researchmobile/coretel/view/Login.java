package com.researchmobile.coretel.view;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.ChatUtility;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class Login extends Activity implements OnClickListener, OnKeyListener{
    /** Called when the activity is first created. */
     private static final String LOGTAG = "MYLOG";
     private static final String LLENAR_CAMPOS = "Llene todos los campos";
     private EditText usuarioEditText;
     private EditText claveEditText;
     private Button entrarButton;
     private Button registrarButton;
     private TextView recuperarTextView;
     private Mensaje mensaje;
     private User user;
     private CatalogoComunidad catalogoComunidad;
     private CatalogoAnotacion catalogoAnotacion;
     private ProgressDialog pd = null;
     private boolean logeado;
     private RequestWS requestWS;
     private RespuestaWS respuesta;
     private AlertDialog.Builder dialogActiveGPS = null;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        try{
        	System.out.println(Login.this);
            setUser(new User());
            setMensaje(new Mensaje());
            setRespuesta(new RespuestaWS());
            setUsuarioEditText((EditText)findViewById(R.id.login_usuario_edittext));
            setClaveEditText((EditText)findViewById(R.id.login_clave_edittext));
            setEntrarButton((Button)findViewById(R.id.login_entrar_button));
            setRegistrarButton((Button)findViewById(R.id.login_registrar_button));
            setRecuperarTextView((TextView)findViewById(R.id.login_recuperar_textview));
            getRecuperarTextView().setOnClickListener(this);
            getRegistrarButton().setOnClickListener(this);
            getClaveEditText().setOnKeyListener(this);
            getUsuarioEditText().setOnKeyListener(this);
            getEntrarButton().setOnClickListener(this);
            getUsuarioEditText().setText("");
            getClaveEditText().setText("");
            setRequestWS(new RequestWS());
            verificaGps();
        }catch(Exception exception){
             Log.i(LOGTAG, exception.getLocalizedMessage());
        }
    }

     @Override
	public void onClick(View view) {
		try {
			if (view == getEntrarButton()) {
				new LoginAsync().execute("");
			}else if (view == getRegistrarButton()){
				registrar();
			}else if (view == getRecuperarTextView()){
				recuperarPassword();
			}
		} catch (Exception exception) {
			Log.i(LOGTAG, exception.getLocalizedMessage());
		}
	}
     
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
        	 moveTaskToBack( true);     
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
          System.out.println(isLogeado());
     }

     private boolean Requerido() {
          if (CamposLlenos()){
               RequestWS request = new RequestWS();
               setRespuesta(request.Login(getUser()));
               if (getRespuesta().isResultado()){
            	   setLogeado(true);
            	   createDirIfNotExists();
//            	   CargarAnotaciones();
//            	   RegistrarChat();
                    return true;
               }else{
            	   return false;
               }
          }
          return false;
     }

     @SuppressLint("SdCardPath")
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
               getMensaje().LoginCamposVacios(this, LLENAR_CAMPOS);
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
 			if(isLogeado()){
 				Intent intent = new Intent(Login.this, MapWuil.class);
// 	            intent.putExtra("anotaciones", getCatalogoAnotacion());
 	            startActivity(intent);
 			}else{
 				getMensaje().VerMensaje(Login.this, "Usuario no existe");
 	 			LimpiaCampos();
 			}
 			
 		}

		private void LimpiaCampos() {
			getClaveEditText().setText("");
			getUsuarioEditText().setText("");
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
 				Intent myIntent = new Intent( Settings.ACTION_SECURITY_SETTINGS );
 			    startActivity(myIntent);
 			}
 		});
         
         dialogActiveGPS.setNegativeButton("No", new DialogInterface.OnClickListener() {
 			
 			@Override
 			public void onClick(DialogInterface dialog, int which) {
// 				finish();
 				
 			}
 		});
         
         LocationManager locationService = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
         LocationListener myLocationListener = new MyLocationListener();
 		locationService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, myLocationListener);
 		if(!locationService.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ))
 		{
 			AlertDialog alert = dialogActiveGPS.create();
 	        alert.setTitle("NECESITA ACTIVAR GPS");
 	        alert.show();
 		}
     }
     
     public class MyLocationListener implements LocationListener {
 		public void onLocationChanged(Location location) {
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

	public boolean isLogeado() {
		return logeado;
	}

	public void setLogeado(boolean logeado) {
		this.logeado = logeado;
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
	
}
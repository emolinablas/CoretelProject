package alvarado.wuil;

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
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.entity.ChatUtility;
import com.researchmobile.coretel.entity.TipoAnotacion;
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
     private Mensaje mensaje;
     private User user;
     private CatalogoComunidad catalogoComunidad;
     private CatalogoAnotacion catalogoAnotacion;
     private ProgressDialog pd = null;
     private boolean logeado;
     private RequestWS requestWS;
     private AlertDialog.Builder dialogActiveGPS = null;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        try{
        	System.out.println(Login.this);
            setUser(new User());
            setMensaje(new Mensaje());
            setUsuarioEditText((EditText)findViewById(R.id.login_usuario_edittext));
            setClaveEditText((EditText)findViewById(R.id.login_clave_edittext));
            setEntrarButton((Button)findViewById(R.id.login_entrar_button));
            setRegistrarButton((Button)findViewById(R.id.login_registrar_button));
            getRegistrarButton().setOnClickListener(this);
            getClaveEditText().setOnKeyListener(this);
            getUsuarioEditText().setOnKeyListener(this);
            getEntrarButton().setOnClickListener(this);
            getUsuarioEditText().setText("walvarado");
            getClaveEditText().setText("123");
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
			}
		} catch (Exception exception) {
			Log.i(LOGTAG, exception.getLocalizedMessage());
		}
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
               if (request.Login(getUser())){
            	   setLogeado(true);
            	   CargarAnotaciones();
            	   RegistrarChat();
                    return true;
               }else{
            	   return false;
               }
          }
          return false;
     }

     private void RegistrarChat() {
    	 ChatUtility registerChar = new ChatUtility();
    	 registerChar.ChatId(Login.this);
	}

	private void CargarAnotaciones() {
		
		//Cargar Comunidades(idusuario)
		CatalogoComunidad comunidades = new CatalogoComunidad();
		comunidades = getRequestWS().CargarComunidades(User.getUserId());
		Log.e("TT", "Login, comunidades cargadas = " + comunidades.getComunidad().length);
		
		//Cargar Tipo de anotaciones por comunidades(idcomunidad)
		CatalogoTipoAnotacion tipoAnotaciones = new CatalogoTipoAnotacion();
		int tamanoComunidades = comunidades.getComunidad().length;
		
		int contar = 0;
		for (int i = 0; i < tamanoComunidades; i++){
			tipoAnotaciones = getRequestWS().BuscarTiposEventos(comunidades.getComunidad()[i].getId());
			contar = contar + tipoAnotaciones.getTipoAnotacion().length;
		}
		
		CatalogoTipoAnotacion tiposFinal = new CatalogoTipoAnotacion();
		TipoAnotacion[] total = new TipoAnotacion[contar];
		int contarTotal = 0;
		for (int i = 0; i < tamanoComunidades; i++){
			tipoAnotaciones = getRequestWS().BuscarTiposEventos(comunidades.getComunidad()[i].getId());
			for (int j = 0; j < tipoAnotaciones.getTipoAnotacion().length; j++){
				total[contarTotal] = tipoAnotaciones.getTipoAnotacion()[j];
				contarTotal++;
			}
		}
		tiposFinal.setTipoAnotacion(total);
		
		//Cargar Anotaciones(idcomunidad, idtipoanotacion)
		RequestWS request = new RequestWS();
		setCatalogoAnotacion(request.CargarAnotaciones(total));
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
 	            intent.putExtra("anotaciones", getCatalogoAnotacion());
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
         dialogActiveGPS.setMessage("�Desea activarlo ahora?");
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
 				finish();
 				
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
	
}
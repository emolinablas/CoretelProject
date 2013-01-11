package alvarado.wuil;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gcm.GCMRegistrar;
import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.ChatUtility;
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
     private Button salirButton;
     private Mensaje mensaje;
     private User user;
     private CatalogoComunidad catalogoComunidad;
     private CatalogoAnotacion catalogoAnotacion;
     private ProgressDialog pd = null;
     private boolean logeado;
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
            setSalirButton((Button)findViewById(R.id.login_salir_button));
            getClaveEditText().setOnKeyListener(this);
            getUsuarioEditText().setOnKeyListener(this);
            getEntrarButton().setOnClickListener(this);
            getSalirButton().setOnClickListener(this);
            getUsuarioEditText().setText("emejia");
            getClaveEditText().setText("tpr9yg");
        }catch(Exception exception){
             Log.i(LOGTAG, exception.getLocalizedMessage());
        }
    }

     @Override
     public void onClick(View view) {
          try{
               if (view == getEntrarButton()){
                    new LoginAsync().execute("");
                   
               }else if (view == getSalirButton()){
                   
               }
          }catch(Exception exception){
               Log.i(LOGTAG, exception.getLocalizedMessage());
          }
         
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

	private void CargarComunidades() {
    	RequestWS request = new RequestWS();
		setCatalogoComunidad(request.CargarComunidad("21"));
		//CargarAnotaciones();
	}

	private void CargarAnotaciones() {
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

     public Button getSalirButton() {
          return salirButton;
     }

     public void setSalirButton(Button salirButton) {
          this.salirButton = salirButton;
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
	
	
     
     
}
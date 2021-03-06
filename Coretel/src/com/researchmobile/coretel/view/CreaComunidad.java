package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.tutorial.pasalo.Comunidades_tutorial_2;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class CreaComunidad extends Activity implements OnClickListener, OnKeyListener{
	
	private EditText nombreEditText;
	private EditText descripcionEditText;
	private Button guardarButton;
	private Mensaje mensaje;

	private RespuestaWS respuesta;
	private Button cancelarButton;
	private ToggleButton esPublica;
	
	private ProgressDialog pd = null;
	
	public void onCreate(Bundle b){
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.creacomunidad);
		setMensaje(new Mensaje());
		
		setRespuesta(new RespuestaWS());
		setNombreEditText((EditText)findViewById(R.id.creacomunidad_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.creacomunidad_descripcion_edittext));
		//setTipoSpinner((Spinner)findViewById(R.id.creacomunidad_tipo_spinner));
		setGuardarButton((Button)findViewById(R.id.creacomunidad_guardar_button));
		setCancelarButton((Button)findViewById(R.id.creacomunidad_cancelar_button));
		setEsPublica((ToggleButton)findViewById(R.id.esPublica_crear_toggleButton));
		getCancelarButton().setOnClickListener(this);
		getGuardarButton().setOnClickListener( this);
		getNombreEditText().setOnKeyListener(this);
		getDescripcionEditText().setOnKeyListener(this);
		if(!User.isModoTutorial()){
			Intent intent = new Intent(CreaComunidad.this, Comunidades_tutorial_2.class);
			startActivity(intent);
		}
		//fillDataSpinner();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		 if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
         {
             if (v == getNombreEditText()){
            	 getDescripcionEditText().requestFocus();
             }else if (v == getDescripcionEditText()){
            	Guardar(); 
             }
         }
         if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
         {
             //TODO: When the enter key is pressed
             return true;
         }
         return false;
	}

	@Override
	public void onClick(View v) {
		ConnectState conect = new ConnectState();
		if (v == getGuardarButton()){
			if(conect.isConnectedToInternet(this))
			{
			new crearAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}else if (v == getCancelarButton()){
			finish();
		}
		
	}

	private void Guardar() {
		String reasignable="1";
		String espublica="0";
		
		if(getEsPublica().isChecked()){
			espublica="1";
		}
		
		String nombre = getNombreEditText().getText().toString();
		String descripcion = getDescripcionEditText().getText().toString();
		//String tipo = String.valueOf((getTipoSpinner().getSelectedItemPosition() + 1));
		ConnectState connect = new ConnectState();
		if (connect.isConnectedToInternet(this)){
			RequestWS request = new RequestWS();
			setRespuesta(request.CreaComunidad(nombre, descripcion, espublica, reasignable, espublica));
		}else{
			getRespuesta().setResultado(false);
			getRespuesta().setMensaje("No cuenta con conexion a internet, intente mas tarde!");
		}
	}
	
	// Clase para ejecutar en Background
    class crearAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(CreaComunidad.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	Guardar();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (getRespuesta() != null){
    				getMensaje().VerMensaje(CreaComunidad.this, getRespuesta().getMensaje());
    				if (getRespuesta().isResultado()){
    					Intent intent = new Intent(CreaComunidad.this, Comunidades.class);
    					startActivity(intent);
    				}
    			}
         }
    }


	public EditText getNombreEditText() {
		return nombreEditText;
	}

	public void setNombreEditText(EditText nombreEditText) {
		this.nombreEditText = nombreEditText;
	}

	public EditText getDescripcionEditText() {
		return descripcionEditText;
	}

	public void setDescripcionEditText(EditText descripcionEditText) {
		this.descripcionEditText = descripcionEditText;
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}


	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public Button getCancelarButton() {
		return cancelarButton;
	}

	public void setCancelarButton(Button cancelarButton) {
		this.cancelarButton = cancelarButton;
	}

	public ToggleButton getEsPublica() {
		return esPublica;
	}

	public void setEsPublica(ToggleButton esPublica) {
		this.esPublica = esPublica;
	}


	
}

package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class EditarComunidad extends Activity implements OnClickListener{
	private Button cancelarButton;
	private Button aplicarButton;
	private EditText nombreEditText;
	private EditText descripcionEditText;
	private ToggleButton esPublica;
	private DetalleComunidad detalleComunidad;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta = new RespuestaWS();
	private RequestWS request = new RequestWS();
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editarcomunidad);
		Bundle bundle = getIntent().getExtras();
		setDetalleComunidad((DetalleComunidad)bundle.get("comunidad"));
		
		setCancelarButton((Button)findViewById(R.id.editarcomunidad_cancelar_button));
		setAplicarButton((Button)findViewById(R.id.editarcomunidad_guardar_button));
		setNombreEditText((EditText)findViewById(R.id.editarcomunidad_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.editarcomunidad_descripcion_edittext));
		setEsPublica((ToggleButton)findViewById(R.id.editar_es_publica_toggleButton));
		
		if(getDetalleComunidad().getEspublica().equalsIgnoreCase("1")){
			getEsPublica().setChecked(true);
		}else{
			getEsPublica().setChecked(false);
		}
		
		getNombreEditText().setText(getDetalleComunidad().getNombre());
		getDescripcionEditText().setText(getDetalleComunidad().getDescripcion());
		getCancelarButton().setOnClickListener(this);
		getAplicarButton().setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == getCancelarButton()){
			finish();
		}else if (view == getAplicarButton()){
			ConnectState conect = new ConnectState();
			
			if(conect.isConnectedToInternet(this)){
			new editarAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intente mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	// Clase para ejecutar en Background
	class editarAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(EditarComunidad.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				editarComunidad();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			if (respuesta != null){
				Toast.makeText(getBaseContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
				if (respuesta.isResultado()){
					Intent intent = new Intent(EditarComunidad.this, Comunidades.class);
					startActivity(intent);
				}
			}

		}
	}
	
	public void editarComunidad(){
		System.out.println("inicia el proceso de edicion de comunidad");
		String nombre = getNombreEditText().getText().toString();
		System.out.println("inicia el proceso de edicion de comunidad 2");
		String descripcion = getDescripcionEditText().getText().toString();
		System.out.println("inicia el proceso de edicion de comunidad 3");
		String espublica = "";
		String esreasignable = "1";
		if(getEsPublica().isChecked()){
			espublica = "1";
		}else{
			espublica = "0";
		}
		
		if (nombre.equalsIgnoreCase("") || descripcion.equalsIgnoreCase("")){
			respuesta.setResultado(false);
			respuesta.setMensaje("debe llenar todos los campos");
		}else{
			try{
				System.out.println("antes de enviar la edicion ");
			respuesta = request.editarComunidad(getDetalleComunidad(), nombre, descripcion, espublica, esreasignable);
			System.out.println("despues de enviar la edicion");
			}catch(Exception e){
				Log.v("pio", "no su puede editar la comunidad ocurrio un problema");
			}
		}
		
	}

	public Button getCancelarButton() {
		return cancelarButton;
	}

	public void setCancelarButton(Button cancelarButton) {
		this.cancelarButton = cancelarButton;
	}

	public Button getAplicarButton() {
		return aplicarButton;
	}

	public void setAplicarButton(Button aplicarButton) {
		this.aplicarButton = aplicarButton;
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

	public DetalleComunidad getDetalleComunidad() {
		return detalleComunidad;
	}

	public void setDetalleComunidad(DetalleComunidad detalleComunidad) {
		this.detalleComunidad = detalleComunidad;
	}

	public ToggleButton getEsPublica() {
		return esPublica;
	}

	public void setEsPublica(ToggleButton esPublica) {
		this.esPublica = esPublica;
	}

}

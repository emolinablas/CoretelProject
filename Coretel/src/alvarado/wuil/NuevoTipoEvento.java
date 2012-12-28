package alvarado.wuil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class NuevoTipoEvento extends Activity implements OnClickListener, OnKeyListener{
	private EditText nombreEditText;
	private EditText descripcionEditText;
	private Spinner tipoSpinner;
	private Button guardarButton;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta;
	private Mensaje mensaje;
	private String idComunidad;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevotipoevento);
		Bundle bundle = (Bundle)getIntent().getExtras();
		setIdComunidad((String)bundle.getString("idComunidad"));
		setMensaje(new Mensaje());
		setNombreEditText((EditText)findViewById(R.id.nuevotipoevento_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.nuevotipoevento_descripcion_edittext));
		setTipoSpinner((Spinner)findViewById(R.id.nuevotipoevento_tipo_spinner));
		setGuardarButton((Button)findViewById(R.id.nuevotipoevento_guardar_button));
		getGuardarButton().setOnClickListener(this);
		getNombreEditText().setOnKeyListener(this);
		getDescripcionEditText().setOnKeyListener(this);
		fillDataSpinner();
	}

	private void fillDataSpinner() {
		final String[] datos = new String[]{"Administrador"};
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.item_spinner, R.id.item_spinner_textview, datos);
		getTipoSpinner().setAdapter(adaptador);
	}

	// Clase para ejecutar en Background
	class NuevoEventoAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(NuevoTipoEvento.this, "GUARDANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				setRespuesta(EnviarDatos());
			} catch (Exception exception) {

			}
			return null;
		}

		private RespuestaWS EnviarDatos() {
			ConnectState connect = new ConnectState();
			String nombre = getNombreEditText().getText().toString();
			String descripcion = getDescripcionEditText().getText().toString();
			String tipo = String.valueOf(getTipoSpinner().getSelectedItemPosition());
			if (connect.isConnectedToInternet(NuevoTipoEvento.this)){
				RequestWS request = new RequestWS();
				setRespuesta(request.NuevoTipoEvento(getIdComunidad(), nombre, descripcion, tipo));
				if (getRespuesta() != null){
					if (getRespuesta().isResultado()){
						getMensaje().VerMensaje(NuevoTipoEvento.this,getRespuesta().getMensaje());
					}
				}
			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			if (getRespuesta() != null){
				if (getRespuesta().isResultado()){
					getMensaje().VerMensaje(NuevoTipoEvento.this, getRespuesta().getMensaje());
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v == getGuardarButton()){
			new NuevoEventoAsync().execute("");
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
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

	public Spinner getTipoSpinner() {
		return tipoSpinner;
	}

	public void setTipoSpinner(Spinner tipoSpinner) {
		this.tipoSpinner = tipoSpinner;
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public String getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	
	

}

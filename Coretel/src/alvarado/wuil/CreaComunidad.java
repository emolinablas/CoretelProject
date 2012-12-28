package alvarado.wuil;

import android.app.Activity;
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

public class CreaComunidad extends Activity implements OnClickListener, OnKeyListener{
	
	private EditText nombreEditText;
	private EditText descripcionEditText;
	private Button guardarButton;
	private Mensaje mensaje;
	private Spinner tipoSpinner;
	private RespuestaWS respuesta;
	
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.creacomunidad);
		setMensaje(new Mensaje());
		
		setNombreEditText((EditText)findViewById(R.id.creacomunidad_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.creacomunidad_descripcion_edittext));
		setTipoSpinner((Spinner)findViewById(R.id.creacomunidad_tipo_spinner));
		setGuardarButton((Button)findViewById(R.id.creacomunidad_guardar_button));
		getGuardarButton().setOnClickListener( this);
		getNombreEditText().setOnKeyListener(this);
		getDescripcionEditText().setOnKeyListener(this);
		fillDataSpinner();
	}

	private void fillDataSpinner() {
		String[] datos = new String[]{"Publica","Privada"};
		 
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
		getTipoSpinner().setAdapter(adaptador);
		
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
		if (v == getGuardarButton()){
			System.out.println("boton guardar");
			Guardar();
		}
		
	}

	private void Guardar() {
		String nombre = getNombreEditText().getText().toString();
		String descripcion = getDescripcionEditText().getText().toString();
		String tipo = String.valueOf((getTipoSpinner().getSelectedItemPosition() + 1));
		ConnectState connect = new ConnectState();
		if (connect.isConnectedToInternet(this)){
			RequestWS request = new RequestWS();
			setRespuesta(request.CreaComunidad(nombre, descripcion, tipo));
			System.out.println("CreaComunidad - mensaje = " + respuesta.getMensaje());
			if (getRespuesta() != null){
				if (getRespuesta().isResultado()){
					getMensaje().VerMensaje(this, respuesta.getMensaje());
					finish();
				}else{
					getMensaje().VerMensaje(this, respuesta.getMensaje());
				}
			}
		}else{
			getMensaje().SinConexion(this);
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

	public Spinner getTipoSpinner() {
		return tipoSpinner;
	}

	public void setTipoSpinner(Spinner tipoSpinner) {
		this.tipoSpinner = tipoSpinner;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}
}

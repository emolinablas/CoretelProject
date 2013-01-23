package alvarado.wuil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.ImageAdapter;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class NuevoTipoEvento extends Activity implements OnClickListener, OnKeyListener{
	private EditText nombreEditText;
	private EditText descripcionEditText;
	private Spinner tipoSpinner;
	private Button guardarButton;
	private Button iconoButton;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta;
	private Mensaje mensaje;
	private String idComunidad;
	int seleccionado = 0;
	private ImageView iconoEvento;
	
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
		setIconoButton((Button)findViewById(R.id.nuevotipoevento_icono_button));
		setIconoEvento((ImageView)findViewById(R.id.nuevotipoevento_icono_imageview));
		getIconoButton().setOnClickListener(this);
		getGuardarButton().setOnClickListener(this);
		getNombreEditText().setOnKeyListener(this);
		getDescripcionEditText().setOnKeyListener(this);
		fillDataSpinner();
	}
	
	public void mDialog(){
		LayoutInflater factory = LayoutInflater.from(NuevoTipoEvento.this);
        
        final View textEntryView = factory.inflate(R.layout.items_icon , null);
        final GridView gv = (GridView)textEntryView.findViewById(R.id.grid);
        gv.setAdapter(new ImageAdapter(this, 100));
        final AlertDialog.Builder alert = new AlertDialog.Builder(NuevoTipoEvento.this );
        alert.setTitle( "¿Cliente Cerrado?");
        alert.setView(textEntryView);
        alert.setCancelable(true);
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	seleccionado = (int)parent.getAdapter().getItemId(position);
                gv.setAdapter(new ImageAdapter(NuevoTipoEvento.this, position));
            }
        });
        alert.setPositiveButton( "   OK   " ,
                new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface arg0, int arg1) {
                           verSeleccion(seleccionado);
                     }
               });

        alert.show();

	}
	
	public void verSeleccion(int imagen){
		getIconoEvento().setImageResource(imagen);
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
		}else if (v == getIconoButton()){
			mDialog();
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

	public ImageView getIconoEvento() {
		return iconoEvento;
	}

	public void setIconoEvento(ImageView iconoEvento) {
		this.iconoEvento = iconoEvento;
	}

	public Button getIconoButton() {
		return iconoButton;
	}

	public void setIconoButton(Button iconoButton) {
		this.iconoButton = iconoButton;
	}
	
	

}

package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.utility.ImageAdapter;
import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.ws.RequestWS;


public class EditarTipoEvento extends Activity implements OnClickListener, OnKeyListener{

	private EditText nombreEditText;
	private EditText descripcionEditText;
	private Button guardarButton;
	private Button iconoButton;
	private ImageView iconoEvento;
	private String urlSeleccionado = "";
	int seleccionado = 0;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta;
	private TipoAnotacion tipoAnotacion;
	
	private String nombre;
	private String descripcion;
	private String comunidad;
	private String idAnotacion;
	private String urlIcono;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editar_tipoevento);
		
		Bundle bundle = (Bundle)getIntent().getExtras();
		
		setTipoAnotacion((TipoAnotacion)bundle.get("tipoAnotacion"));
		urlSeleccionado = getTipoAnotacion().getIcono();
		setNombreEditText((EditText)findViewById(R.id.editartipoevento_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.editartipoevento_descripcion_edittext));
		setGuardarButton((Button)findViewById(R.id.editartipoevento_guardar_button));
		setIconoButton((Button)findViewById(R.id.editartipoevento_icono_button));
		setIconoEvento((ImageView)findViewById(R.id.editartipoevento_icono_imageview));
		getNombreEditText().setOnClickListener(this);
		getDescripcionEditText().setOnKeyListener(this);
		getGuardarButton().setOnClickListener(this);
		getIconoButton().setOnClickListener(this);
		
		setRespuesta(new RespuestaWS());
		
		getNombreEditText().setText(getTipoAnotacion().getNombre());
		getDescripcionEditText().setText(getTipoAnotacion().getDescripcion());
	}
	
	@Override
	public void onClick(View view) {
		if (view == getIconoButton()){
			mDialog();
		}else if(view == getGuardarButton()){
			new guardarAsync().execute("");
		}
	}
	
	// Clase para ejecutar en Background
	class guardarAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(EditarTipoEvento.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				guardarDatos();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			if (getRespuesta() != null){
				Toast.makeText(getBaseContext(), getRespuesta().getMensaje(), Toast.LENGTH_SHORT).show();
				if(getRespuesta().isResultado()){
					finish();
				}
			}
		}
	}

	public void guardarDatos(){
		String incidente = "1";
		String nom = getNombreEditText().getText().toString();
		String des = getDescripcionEditText().getText().toString();
		String icono = "";
		RequestWS request = new RequestWS();
		setRespuesta(request.EditarTipoEvento(getTipoAnotacion().getComunidad(), nom, des, incidente, urlSeleccionado, getTipoAnotacion().getId()));
	}
	
	
	public void mDialog(){
		LayoutInflater factory = LayoutInflater.from(EditarTipoEvento.this);
        
        final View textEntryView = factory.inflate(R.layout.items_icon , null);
        final GridView gv = (GridView)textEntryView.findViewById(R.id.grid);
        gv.setAdapter(new ImageAdapter(this, 100));
        final AlertDialog.Builder alert = new AlertDialog.Builder(EditarTipoEvento.this );
        alert.setTitle( "ICONO");
        alert.setView(textEntryView);
        alert.setCancelable(true);
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	ImageAdapter img = (ImageAdapter)parent.getAdapter();
            	urlSeleccionado = img.nombre(position);
            	seleccionado = (int)parent.getAdapter().getItemId(position);
            	Log.e("pio", "icono = " + urlSeleccionado + " " + seleccionado);
                gv.setAdapter(new ImageAdapter(EditarTipoEvento.this, position));
            }
        });
        alert.setPositiveButton( "   OK   " ,
                new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface arg0, int arg1) {
                           verSeleccion(urlSeleccionado);
                     }
               });

        alert.show();

	}
	
	public void verSeleccion(String imagen){
		TokenizerUtility tokenizer = new TokenizerUtility();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		getIconoEvento().setImageDrawable(tokenizer.iconoResource(EditarTipoEvento.this, "0=+=1=+=2=+=3=+=4=+=" + imagen));
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

	public Button getIconoButton() {
		return iconoButton;
	}

	public void setIconoButton(Button iconoButton) {
		this.iconoButton = iconoButton;
	}

	public ImageView getIconoEvento() {
		return iconoEvento;
	}

	public void setIconoEvento(ImageView iconoEvento) {
		this.iconoEvento = iconoEvento;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getComunidad() {
		return comunidad;
	}

	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}

	public String getIdAnotacion() {
		return idAnotacion;
	}

	public void setIdAnotacion(String idAnotacion) {
		this.idAnotacion = idAnotacion;
	}

	public String getUrlIcono() {
		return urlIcono;
	}

	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}

	public TipoAnotacion getTipoAnotacion() {
		return tipoAnotacion;
	}

	public void setTipoAnotacion(TipoAnotacion tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}
	
	
}
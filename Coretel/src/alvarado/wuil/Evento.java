package alvarado.wuil;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Fecha;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class Evento extends Activity implements OnClickListener, OnKeyListener{
	final static int CAMERA_RESULT = 0;
	
	private Button cameraButton;
	private Button borrarButton;
	private Button guardarButton;
	private ImageView fotoEvento;
	private String pathFoto;
	private String latitud;
	private String longitud;
	private String titulo;
	private String descripcion;
	private Mensaje mensaje;
	private Fecha fecha;
	
	private EditText tituloEditText;
	private TextView fechaTextView;
	private TextView latitudTextView;
	private TextView longitudTextView;
	private Spinner tipoEventoSpinnet;
	private EditText descripcionEditText;
	private Spinner comunidadSpinner;
	private CatalogoComunidad catalogoComunidad;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evento);
		new ComunidadesAsync().execute("");
		Bundle bundle = (Bundle)getIntent().getExtras();
		setLatitud(bundle.getString("latitud"));
		setLongitud(bundle.getString("longitud"));
		setMensaje(new Mensaje());
		getMensaje().VerMensaje(this, getLatitud());
		setFecha(new Fecha());
		
		setCameraButton((Button)findViewById(R.id.evento_capturar_button));
		setBorrarButton((Button)findViewById(R.id.evento_borrar_button));
		setGuardarButton((Button)findViewById(R.id.evento_save_button));
		setFotoEvento((ImageView)findViewById(R.id.evento_foto_imageview));
		getCameraButton().setOnClickListener(this);
		getBorrarButton().setOnClickListener(this);
		getGuardarButton().setOnClickListener(this);
		
		setTituloEditText((EditText)findViewById(R.id.evento_titulo_edittext));
		setFechaTextView((TextView)findViewById(R.id.evento_fecha_textview));
		setLatitudTextView((TextView)findViewById(R.id.evento_latitud_textview));
		setLongitudTextView((TextView)findViewById(R.id.evento_longitud_textview));
		setTipoEventoSpinnet((Spinner)findViewById(R.id.evento_tipo_spinner));
		setComunidadSpinner((Spinner)findViewById(R.id.evento_comunidad_spinner));
		
		getFechaTextView().setText(getFecha().FechaHoy());
		getLatitudTextView().setText(getLatitud());
		getLongitudTextView().setText(getLongitud());
		
	}
	
	private void CargarDatosSpinner() {
		TipoAnotacion();
		Comunidades();
	}

	private void Comunidades() {
		final String[] comunidades = new String[]{"comunidad 1"};
		ArrayAdapter<String> adaptador =  new ArrayAdapter<String>(this, R.layout.item_spinner, R.id.item_spinner_textview, comunidades);
		getComunidadSpinner().setAdapter(adaptador);
	}

	private void TipoAnotacion() {
		final String[] tipos = new String[]{"Accidente de Moto","Lluvia","Camino en mal estado","Policia","Disparos","Camara","Semaforo","Tunel","Volcan en erupcion","Tornado","Senal Wifi"};
		ArrayAdapter<String> adaptador =  new ArrayAdapter<String>(this, R.layout.item_spinner, R.id.item_spinner_textview, tipos);
		getTipoEventoSpinnet().setAdapter(adaptador);

	}
	
	 // Clase para ejecutar en Background
    class ComunidadesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	CargarDatos();
                	CargarTipoAnotacion();

               } catch (Exception exception) {

               }
                return null ;
         }

          private void CargarTipoAnotacion() {
			// TODO Auto-generated method stub
			
		}

		private void CargarDatos() {
        	  RequestWS request = new RequestWS();
      		  setCatalogoComunidad(request.CargarComunidad("1"));
			
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
         protected void onPostExecute(Integer resultado) {
        	 CargarDatosSpinner();
         }
    }


	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

	@Override
	public void onClick(View view) {
		if (view == getCameraButton()){
			ActivarCamara();
		}else if (view == getBorrarButton()){
			
		}else if (view == getGuardarButton()){
			try{
				ConnectState connect = new ConnectState();
				RequestWS request = new RequestWS();
				if (connect.isConnectedToInternet(this)){
					String titulo = getTituloEditText().getText().toString();
				    String idUsuario = User.getUserId();
				    String comunidad = "16";
				    String tipoAnotacion = "1";
				    String descripcion = getTipoEventoSpinnet().getSelectedItem().toString();
				    String imagen = "";
					RespuestaWS respuesta = new RespuestaWS();
					respuesta = request.MandarEvento(titulo, getLatitud(), getLongitud(), idUsuario, comunidad, tipoAnotacion, descripcion, getPathFoto());
					if (respuesta.isResultado()){
						getMensaje().VerMensaje(this, respuesta.getMensaje());
						finish();
					}
				}
			}catch(Exception exception){
				
			}
		}
		
	}

	private void ActivarCamara() {
		//Activar la camara
	  	Intent cIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		//startActivityForResult(cIntent, CAMERA_RESULT); 
		System.out.println("activar camara");
		//asignar nombre y direccion a la imagen
		setPathFoto("/foto1-evento.jpg");
		String path = "/mifoto.jpg";
		System.out.println("crear nombre archivo " + getPathFoto());
		//crear nuevo archivo (imagen)
		File f = new File(Environment.getExternalStorageDirectory() + getPathFoto());
		Uri uri = Uri.fromFile(f);
		System.out.println("crear archivo " + f);
		cIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		
		startActivityForResult(cIntent, CAMERA_RESULT);
			
		verImagen();
	}

	private void verImagen() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
        Bitmap bm = BitmapFactory.decodeFile("sdcard" + getPathFoto(), options);
        getFotoEvento().setImageBitmap(bm);
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		 
             if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
             {
                 if (v == getTituloEditText()){
                	 getDescripcionEditText().requestFocus();//TODO: When the enter key is released
                 }else if(v == getDescripcionEditText()){
                	 
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

	public Button getCameraButton() {
		return cameraButton;
	}

	public void setCameraButton(Button cameraButton) {
		this.cameraButton = cameraButton;
	}

	public Button getBorrarButton() {
		return borrarButton;
	}

	public void setBorrarButton(Button borrarButton) {
		this.borrarButton = borrarButton;
	}

	public ImageView getFotoEvento() {
		return fotoEvento;
	}

	public void setFotoEvento(ImageView fotoEvento) {
		this.fotoEvento = fotoEvento;
	}

	public String getPathFoto() {
		return pathFoto;
	}

	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public EditText getTituloEditText() {
		return tituloEditText;
	}

	public void setTituloEditText(EditText tituloEditText) {
		this.tituloEditText = tituloEditText;
	}

	public TextView getFechaTextView() {
		return fechaTextView;
	}

	public void setFechaTextView(TextView fechaTextView) {
		this.fechaTextView = fechaTextView;
	}

	public TextView getLatitudTextView() {
		return latitudTextView;
	}

	public void setLatitudTextView(TextView latitudTextView) {
		this.latitudTextView = latitudTextView;
	}

	public TextView getLongitudTextView() {
		return longitudTextView;
	}

	public void setLongitudTextView(TextView longitudTextView) {
		this.longitudTextView = longitudTextView;
	}

	public Spinner getTipoEventoSpinnet() {
		return tipoEventoSpinnet;
	}

	public void setTipoEventoSpinnet(Spinner tipoEventoSpinnet) {
		this.tipoEventoSpinnet = tipoEventoSpinnet;
	}

	public EditText getDescripcionEditText() {
		return descripcionEditText;
	}

	public void setDescripcionEditText(EditText descripcionEditText) {
		this.descripcionEditText = descripcionEditText;
	}

	public Spinner getComunidadSpinner() {
		return comunidadSpinner;
	}

	public void setComunidadSpinner(Spinner comunidadSpinner) {
		this.comunidadSpinner = comunidadSpinner;
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}
	
	
	
}

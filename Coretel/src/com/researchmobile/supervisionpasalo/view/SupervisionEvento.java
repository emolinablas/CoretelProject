package com.researchmobile.supervisionpasalo.view;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.researchmobile.coretel.supervision.entity.UserAsignacion;
import com.researchmobile.coretel.supervision.utility.TokenizerUtilitySupervision;
import com.researchmobile.coretel.supervision.ws.RequestWSAsignacion;
import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.view.R;
import com.researchmobile.supervisionpasalo.tutorial.Asignaciones_Tutorial_1;
import com.researchmobile.supervisionpasalo.tutorial.SupervisionEvento_Tutorial_2;

public class SupervisionEvento extends Activity implements OnClickListener {

	private TextView fechaTextView;
	private TextView ubicacionTextView;
	private TextView tipoTextView;
	private TextView descripcionTextView;
	private TextView comunidadTextView;
	private ImageView asignacionfotoImageView;
	private Button responderButton;
	private Button regresarButton;
	private String descripcion;
	private String titulo;
	private TokenizerUtility tokenizer = new TokenizerUtility();
	private ProgressDialog pd = null;
	private RequestWSAsignacion requestWS = new RequestWSAsignacion();
	
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.supervision_evento);
			Bundle bundle = getIntent().getExtras();
			setTitulo(bundle.getString("titulo"));
			setDescripcion(bundle.getString("descripcion"));
			String latitud = bundle.getString("latitud");
			String longitud = bundle.getString("longitud");
			Log.v("pio", "descripcion = " + descripcion);
			Log.v("pio", "titulo = " + getTitulo());
			/*
			String tipo = bundle.getString("tipo");
			String comunidad = bundle.getString("comunidad");
			*/
			setFechaTextView((TextView)findViewById(R.id.evento_fechaTextView));
			setUbicacionTextView((TextView)findViewById(R.id.evento_ubicacionTextView));
			setTipoTextView((TextView)findViewById(R.id.evento_tipoTextView));
			setDescripcionTextView((TextView)findViewById(R.id.evento_descripcionTextView));
			setComunidadTextView((TextView)findViewById(R.id.evento_comunidadTextView));
			setResponderButton((Button)findViewById(R.id.evento_responderbutton));
			setRegresarButton((Button)findViewById(R.id.evento_regresarButton));
			setAsignacionfotoImageView((ImageView)findViewById(R.id.supervisionevento_fotoImageView));
			
			getResponderButton().setOnClickListener(this);
			getRegresarButton().setOnClickListener(this);
			
			getUbicacionTextView().setText(latitud + " , " + longitud);
			/*
			getTipoTextView().setText(tipo);
			getComunidadTextView().setText(comunidad);
			*/
			
			
			//utilizando tokenizer
			getFechaTextView().setText(tokenizer.fechaRegistro(getDescripcion()));
			getDescripcionTextView().setText(tokenizer.descripcion(getDescripcion()));
			MostrarImagen();
			
			new marcarAsignacionAsync().execute("");
			//MODO TUTORIAL
			UserAsignacion.setModotutorialsupervision(true);
			if(UserAsignacion.isModotutorialsupervision()){
				Intent intent = new Intent(SupervisionEvento.this, SupervisionEvento_Tutorial_2.class);
				startActivity(intent);
			}
		}
		
			public void onClick(View view){
				if(view ==getResponderButton())
				{
					Responder();
				}else if(view == getRegresarButton()){
					Retorno();
				}
			}
			
			// Clase para ejecutar en Background
		       class marcarAsignacionAsync extends AsyncTask<String, Integer, Integer> {

		             // Metodo que prepara lo que usara en background, Prepara el progress
		             @Override
		             protected void onPreExecute() {
		                   pd = ProgressDialog. show(SupervisionEvento.this, "VERIFICANDO DATOS",
		                               "ESPERE UN MOMENTO");
		                   pd.setCancelable( false);
		            }

		             // Metodo con las instrucciones que se realizan en background
		             @Override
		             protected Integer doInBackground(String... urlString) {
		                   try {
		                	   marcaAsignacion();
		                  } catch (Exception exception) {

		                  }
		                   return null ;
		            }

		             // Metodo con las instrucciones al finalizar lo ejectuado en background
		             protected void onPostExecute(Integer resultado) {
		                   pd.dismiss();

		            }
		}
		       
		       public void marcaAsignacion(){
			    	TokenizerUtilitySupervision tokenizer = new TokenizerUtilitySupervision(); 
			    	requestWS.marcarAsignacion(tokenizer.idAnotacion(getTitulo()));
			    }
		
	//metodo para cargar imagenes
			private void MostrarImagen() {
				System.out.println("Variable descripcion" + descripcion);
				String url = "http://23.23.1.2/WS/" + tokenizer.imagen(descripcion);
				System.out.println("Recibe imagen");
				Log.e("TT", "url imagen = " + url);
				new descargaImagenes().execute(url);
			getAsignacionfotoImageView().setVisibility(View.VISIBLE);
		}

	//descargaImagenes
			class descargaImagenes extends AsyncTask<String, Integer, Integer>{

				ProgressBar miBarra;
				
				@Override
				protected void onPreExecute(){
					miBarra = (ProgressBar)findViewById(R.id.barraProgreso);
					
				}
				@Override
				protected Integer doInBackground(String... urlString) {
					try {
						URL url = new URL(urlString[0]);
						URLConnection conexion = url.openConnection();
						conexion.connect();
						
						int tamano = conexion.getContentLength();
						Log.e("TT", "resultado busqueda imagen = " + tamano);
						InputStream input = new BufferedInputStream(url.openStream());
						OutputStream output = new FileOutputStream("/sdcard/temp.jpg");
						
						byte datos[] = new byte[1024];
						int cuenta;
						long total = 0;
						
						while ((cuenta = input.read(datos)) != -1){
							total += cuenta;
							output.write(datos, 0, cuenta);
							
							this.publishProgress((int)total*100/tamano);
						}
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
				
				@Override
				protected void onProgressUpdate(Integer...progreso){
					int porcentaje = progreso[0];
					miBarra.setProgress(porcentaje);
				}
				
				protected void onPostExecute(Integer resultado){
					getAsignacionfotoImageView().setImageBitmap(BitmapFactory.decodeFile("/sdcard/temp.jpg"));
				}
			}
			
			
		
		private void Responder() {
			Intent intent = new Intent(SupervisionEvento.this, SupervisionRespuesta.class);
			intent.putExtra("descripcion", getDescripcion());
			intent.putExtra("titulo", getTitulo());
			startActivity(intent);
			}
		
		private void Retorno(){
			float latitud = (float) 14.627853;
			float longitud = (float) -90.517584;
			Intent intent = new Intent(SupervisionEvento.this, MapaSupervision.class);
			intent.putExtra("latitud", latitud);
			intent.putExtra("longitud", longitud);
			intent.putExtra("cargarPuntos", false);
		}

		public TextView getFechaTextView() {
			return fechaTextView;
		}

		public void setFechaTextView(TextView fechaTextView) {
			this.fechaTextView = fechaTextView;
		}

		public TextView getUbicacionTextView() {
			return ubicacionTextView;
		}

		public void setUbicacionTextView(TextView ubicacionTextView) {
			this.ubicacionTextView = ubicacionTextView;
		}

		public TextView getTipoTextView() {
			return tipoTextView;
		}

		public void setTipoTextView(TextView tipoTextView) {
			this.tipoTextView = tipoTextView;
		}

		public TextView getDescripcionTextView() {
			return descripcionTextView;
		}

		public void setDescripcionTextView(TextView descripcionTextView) {
			this.descripcionTextView = descripcionTextView;
		}

		public TextView getComunidadTextView() {
			return comunidadTextView;
		}

		public void setComunidadTextView(TextView comunidadTextView) {
			this.comunidadTextView = comunidadTextView;
		}

		public Button getResponderButton() {
			return responderButton;
		}
		
		

		public ImageView getAsignacionfotoImageView() {
			return asignacionfotoImageView;
		}

		public void setAsignacionfotoImageView(ImageView asignacionfotoImageView) {
			this.asignacionfotoImageView = asignacionfotoImageView;
		}

		public void setResponderButton(Button responderButton) {
			this.responderButton = responderButton;
		}

		public Button getRegresarButton() {
			return regresarButton;
		}

		public void setRegresarButton(Button regresarButton) {
			this.regresarButton = regresarButton;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
	
}

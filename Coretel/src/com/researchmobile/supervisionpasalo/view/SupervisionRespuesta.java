package com.researchmobile.supervisionpasalo.view;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.supervision.entity.CatalogoSupervisor;
import com.researchmobile.coretel.supervision.entity.RespuestaWS;
import com.researchmobile.coretel.supervision.entity.Supervisor;
import com.researchmobile.coretel.supervision.entity.UserAsignacion;
import com.researchmobile.coretel.supervision.utility.AdapterListaFotos;
import com.researchmobile.coretel.supervision.utility.TokenizerUtilitySupervision;
import com.researchmobile.coretel.supervision.ws.RequestWS;
import com.researchmobile.coretel.supervision.ws.RequestWSAsignacion;
import com.researchmobile.coretel.utility.RMFile;
import com.researchmobile.coretel.view.R;

public class SupervisionRespuesta extends Activity implements OnClickListener {
	
	final static int CAMERA_RESULT = 0;
	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 2;

	private TextView ordenTextView;
	private EditText respuestaEditText;
	private TextView estadoTextView;
	private Button capturarButton;
	private Button guardarButton;
	private Button regresarButton;
	private String descripcion;
	private String titulo;
	private String pathFoto;
	private Button estadoButton;
	private String estado;
	private int cuentaFotos = 0;
	private ListView listaFotosListView;
	private ArrayList<HashMap<String, Object>> arrayListFotos = new ArrayList<HashMap<String, Object>>();
	private ProgressDialog pd = null;
	private RequestWS requestWS = new RequestWS();
	private RespuestaWS respuesta;
	private RequestWSAsignacion requestWSAsignacion;
	private String idReasignado;
	TokenizerUtilitySupervision tokenizer = new TokenizerUtilitySupervision();
	private CatalogoSupervisor catalogoSupervisor;

		protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.supervision_respuesta);
			setRequestWSAsignacion(new RequestWSAsignacion());
			
			Bundle bundle = getIntent().getExtras();
			setDescripcion(bundle.getString("descripcion"));
			setTitulo(bundle.getString("titulo"));
			
			setEstado("2");
			/*
			//MODO TUTORIAL
			UserAsignacion.setModotutorialsupervision(true);
			if(UserAsignacion.isModotutorialsupervision()){
				Intent intent = new Intent(SupervisionRespuesta.this, SupervisionRespuesta_Tutorial_3.class);
				startActivity(intent);
			}
			*/
			setRespuesta(new RespuestaWS());
			
			setRespuestaEditText((EditText)findViewById(R.id.supervisionrespuesta_respuestaEditText));
			getRespuestaEditText().setText(tokenizer.asignacionDescripcion(getTitulo()));
			//getRespuestaEditText().setText(AnotacionAsignacion.getAsignacionDescripcion());
			setOrdenTextView((TextView)findViewById(R.id.supervisionrespuesta_ordenTextView));
			//getOrdenTextView().setText(AnotacionAsignacion.getOrden());
			getOrdenTextView().setText(tokenizer.orden(getTitulo()));
			
			
			setCatalogoSupervisor(new CatalogoSupervisor());
			
			setEstadoTextView((TextView)findViewById(R.id.supervisionrespuesta_estado_textview));
			getEstadoTextView().setText(tokenizer.nombreEstado(getTitulo()));
			setListaFotosListView((ListView)findViewById(R.id.supervisionrespuesta_fotos_listview));
			
			setCapturarButton((Button)findViewById(R.id.supervisionrespuesta_capturar_button));
			setGuardarButton((Button)findViewById(R.id.supervisionrespuesta_guardarbutton));
			setEstadoButton((Button)findViewById(R.id.supervision_respuesta_estado));
			setRegresarButton((Button)findViewById(R.id.supervisionrespuesta_regresarbutton));
			
			getGuardarButton().setOnClickListener(this);
			getEstadoButton().setOnClickListener(this);
			getCapturarButton().setOnClickListener(this);
			getRegresarButton().setOnClickListener(this);
			
			
		}
		
		private void setRequestWSAsignacion(
				RequestWSAsignacion requestWSAsignacion2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onClick(View view) {
			if (view == getCapturarButton()){
				if (cuentaFotos < 8){
					dialogFotos(this);
				}else{
					Toast.makeText(getBaseContext(), "Ya cumplio el limite de fotos", Toast.LENGTH_SHORT).show();
				}
			}else if (view == getGuardarButton()){
				new guardarAsync().execute("");
			}else if (view == getEstadoButton()){
				dialogEstados(this);
			}else if (view == getRegresarButton()){
				finish();
			}
		}
		
		// Clase para ejecutar en Background
	       class guardarAsync extends AsyncTask<String, Integer, Integer> {

	             // Metodo que prepara lo que usara en background, Prepara el progress
	             @Override
	             protected void onPreExecute() {
	                   pd = ProgressDialog. show(SupervisionRespuesta.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
	                   pd.setCancelable( false);
	            }

	             // Metodo con las instrucciones que se realizan en background
	             @Override
	             protected Integer doInBackground(String... urlString) {
	                   try {
	                	   Log.v("piio", "enviarRespeusta 0");
	                	   enviarRespuesta();
	                  } catch (Exception exception) {

	                  }
	                   return null ;
	            }

	             // Metodo con las instrucciones al finalizar lo ejectuado en background
	             protected void onPostExecute(Integer resultado) {
	                   pd.dismiss();
	                   if (getRespuesta() != null){
	                	   Toast.makeText(getBaseContext(), getRespuesta().getMensaje(), Toast.LENGTH_SHORT).show();
	                	   if (getRespuesta().isResultado()){
	                		   Intent intent = new Intent(SupervisionRespuesta.this, MapaSupervision.class);
	                		   startActivity(intent);
	                	   }
	                   }

	            }
	}

		public void enviarRespuesta(){
			TokenizerUtilitySupervision tokenizer = new TokenizerUtilitySupervision();
			String id = tokenizer.idAnotacion(getTitulo());
			String respuesta = getRespuestaEditText().getText().toString();
			if (arrayListFotos.size() < 1){
				getRespuesta().setResultado(false);
				getRespuesta().setMensaje("Debe agregar por lo menos una foto");
			}else{
				String respuestaTemp = getRespuestaEditText().toString();
				if (respuestaTemp.equalsIgnoreCase("")){
					getRespuesta().setResultado(false);
					getRespuesta().setMensaje("Escriba una respuesta");
				}else{
					setRespuesta(requestWS.enviarRespuesta(id, respuesta, getEstado(), getIdReasignado()));
					if (getRespuesta().isResultado()){
						enviarFotos();
					}
				}
			}
		}
		
		public void enviarFotos(){
			int tamano = arrayListFotos.size();
			RequestWSAsignacion req = new RequestWSAsignacion();
			for (int i = 0; i < tamano; i++){
				HashMap<String, Object> map = arrayListFotos.get(i);
				String idAsig = (String)map.get("asignacion");
				String foto = (String)map.get("path");
				String imagen = fotoReducida(foto);
				req.mandarFotoRespuesta(imagen, idAsig);
				Log.v("pio", "envio de foto " + i);
			}
		}
		
		private String fotoReducida(String path){
			
			String fotonueva = "/captura.jpg";
			BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 0;
	        Bitmap bm = BitmapFactory.decodeFile(path);
//	        }else{
//	        	bm = BitmapFactory.decodeFile("sdcard/" + getPathFoto());
//	        }
	        
	        File file = new File("sdcard" + fotonueva);
	        try {
	        file.createNewFile();
	        FileOutputStream out = new FileOutputStream(file);
	        bm.compress(Bitmap.CompressFormat.JPEG, 20, out);//Convertimos la imagen a JPEG
	        return fotonueva;
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return null;
	        }
		}
		public void dialogFotos (final Context activity) {

	        final Dialog myDialog = new Dialog(activity);
	        myDialog.setContentView(R.layout.dialog_fotos);
	        myDialog.setTitle( "Opciones" );
	        myDialog.setCancelable( false );
	       
	        Button cerrar = (Button) myDialog.findViewById(R.id.dialog_fotos_cerrar);
	        cerrar.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });

	        Button album = (Button) myDialog.findViewById(R.id.dialog_fotos_album);
	        album.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	mostrarGaleria();
//	            	galeria = true;
	                myDialog.dismiss();
	            }
	        });

	        Button camara= (Button) myDialog.findViewById(R.id.dialog_fotos_tomar);
	        camara.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	
	            	ActivarCamara();
//	            	galeria = false;
	                myDialog.dismiss();
	            }
	        });


	        myDialog.show();

	    }
		
		private void mostrarGaleria(){
			try{
				int code = TAKE_PICTURE;
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				code = SELECT_PICTURE;
				startActivityForResult(intent, code);
			}catch(Exception exception){
				
			}
		}
		
		private void ActivarCamara() {
			//Activar la camara
		  	Intent cIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			//startActivityForResult(cIntent, CAMERA_RESULT); 
			//asignar nombre y direccion a la imagen
			setPathFoto("sdcard/asingacion_" + String.valueOf(cuentaFotos + 1) + ".jpg");
			//crear nuevo archivo (imagen)
			File f = new File(getPathFoto());
			Uri uri = Uri.fromFile(f);
			cIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(cIntent, CAMERA_RESULT);
			
//			verImagen();
//			origenAlbum = false;
			
		}
		
		
		/**
	     * Funci—n que se ejecuta cuando concluye el intent en el que se solicita una imagen
	     * ya sea de la c‡mara o de la galer’a
	     */
	    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	/**
	    	 * Se revisa si la imagen viene de la c‡mara (TAKE_PICTURE) o de la galer’a (SELECT_PICTURE)
	    	 */
	    	if (requestCode == SELECT_PICTURE){
	    		if (data != null){
	    			
	    			Uri selectedImage = data.getData();
	    			RMFile rmFile = new RMFile();
	    			setPathFoto(rmFile.convertMediaUriToPath(this, selectedImage));
	    			
	    			
//	    			origenAlbum = true;
//	    			setPathFoto(data.getData().getPath());
	    			HashMap<String, Object> map = new HashMap<String, Object>();
	    			map.put("asignacion", "1");
	    			map.put("path", getPathFoto());
	    			String p = getPathFoto();
	    			BitmapFactory.Options options = new BitmapFactory.Options();
	    			options.inSampleSize = 0;
	    			Log.e("Log", "ver foto = sdcard" + p);
	    	        Bitmap bm = BitmapFactory.decodeFile(p, options);
	    	        
	    	        map.put("imagen", bm);
	    	        arrayListFotos.add(map);
	    			cuentaFotos++;
	    			Log.v("pio", "numero de foto = " + cuentaFotos);
	    			llenaListaFotos();
//	    			verImagenGaleria();
	    		}
	    	}else if (requestCode == CAMERA_RESULT){
	    		Log.v("pio", "resultCode = " + resultCode);
	    		if (resultCode != 0){
	    			HashMap<String, Object> map = new HashMap<String, Object>();
	    			map.put("asignacion", "1");
	    			map.put("path", getPathFoto());
	    			
	    			BitmapFactory.Options options = new BitmapFactory.Options();
	    			options.inSampleSize = 0;
	    			Log.e("Log", "ver foto = " + getPathFoto());
	    	        Bitmap bm = BitmapFactory.decodeFile(getPathFoto(), options);
	    	        
	    	        map.put("imagen", bm);
	    	        arrayListFotos.add(map);
	    			cuentaFotos++;
	    			Log.v("pio", "numero de foto = " + cuentaFotos);
	    			llenaListaFotos();
	    		}
	    	}
	    }
	    
	    public void llenaListaFotos(){
	    	AdapterListaFotos adapter = new AdapterListaFotos(this, arrayListFotos);
	    	getListaFotosListView().setAdapter(adapter);
	    	setListViewHeightBasedOnChildren(getListaFotosListView());
	    }
	    
	    	    
//	     Metodo para controlar ScrollView con ListView
	       public static void setListViewHeightBasedOnChildren(ListView listView) {
	          ListAdapter listAdapter = listView.getAdapter();
	          if (listAdapter == null) {
	              return ;
	          }

	          int totalHeight = 0;
	          int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
	                  MeasureSpec. AT_MOST );
	          for (int i = 0; i < listAdapter.getCount(); i++) {
	              View listItem = listAdapter.getView(i, null , listView);
	              listItem.measure(desiredWidth, MeasureSpec. UNSPECIFIED );
	              totalHeight += listItem.getMeasuredHeight();
	          }

	          ViewGroup.LayoutParams params = listView.getLayoutParams();
	          params. height = totalHeight
	                  + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	          listView.setLayoutParams(params);
	          listView.requestLayout();
	      }

			public void dialogSupervisores (final Context activity) {

		        final Dialog myDialog = new Dialog(activity);
		        myDialog.setContentView(R.layout.respuestas_supervisores);
		        myDialog.setTitle( "Opciones");
		        myDialog.setCancelable( false);
		       
		        ListView lista = (ListView) myDialog.findViewById(R.id.lista_supervisores);
		        lista.setAdapter(new ArrayAdapter<Supervisor>(this, android.R.layout.simple_list_item_1, getCatalogoSupervisor().getSupervisor()));
		        lista.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
						Supervisor sup = (Supervisor)adapter.getItemAtPosition(position);
						setIdReasignado(sup.getId());
						myDialog.dismiss();
					}
		        	
		        	
				});

		        Button cerrar = (Button) myDialog.findViewById(R.id.cerrar_supervisores);
		        cerrar.setOnClickListener( new OnClickListener() {
		            public void onClick(View v) {
		                myDialog.dismiss();
		            }
		        });

		        myDialog.show();

		    }
		
		public void dialogEstados (final Context activity) {

	        final Dialog myDialog = new Dialog(activity);
	        myDialog.setContentView(R.layout.respuestas_supervision);
	        myDialog.setTitle( "Opciones");
	        myDialog.setCancelable( false);
	       
	        Button corregido = (Button) myDialog.findViewById(R.id.respuestas_corregido);
	        corregido.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	setEstado("2");
	            	setIdReasignado(UserAsignacion.getUserId());
	            	getEstadoTextView().setText("Corregido");
	                myDialog.dismiss();
	            }
	        });
	        
	        Button irreparable = (Button) myDialog.findViewById(R.id.respuestas_irreparable);
	        irreparable.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	setEstado("3");
	            	setIdReasignado(UserAsignacion.getUserId());
	            	getEstadoTextView().setText("Irreparable");
	                myDialog.dismiss();
	            }
	        });
	        
	        Button corregidoReasignado = (Button) myDialog.findViewById(R.id.respuestas_corregido_re);
	        corregidoReasignado.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	setEstado("4");
	            	getEstadoTextView().setText("Corregido Reasignado");
	            	new reasignarAsync().execute("");
	            	myDialog.dismiss();
	              
	            }
	        });
	        
	        Button irreparableReasignado = (Button) myDialog.findViewById(R.id.respuestas_irreparable_re);
	        irreparableReasignado.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	setEstado("5");
	            	getEstadoTextView().setText("Irreparable Reasignado");
	            	new reasignarAsync().execute("");
	                myDialog.dismiss();
	
	            }

	        });
            
	        Button cerrar = (Button) myDialog.findViewById(R.id.respuestas_cerrar);
	        cerrar.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });

	        myDialog.show();

	    }

		// Clase para ejecutar en Background
	       class reasignarAsync extends AsyncTask<String, Integer, Integer> {

	             // Metodo que prepara lo que usara en background, Prepara el progress
	             @Override
	             protected void onPreExecute() {
	                   pd = ProgressDialog. show(SupervisionRespuesta.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
	                   pd.setCancelable( false);
	            }

	             // Metodo con las instrucciones que se realizan en background
	             @Override
	             protected Integer doInBackground(String... urlString) {
	                   try {
	                	   buscarUsuarios();
	                  } catch (Exception exception) {

	                  }
	                   return null ;
	            }

	             // Metodo con las instrucciones al finalizar lo ejectuado en background
	             protected void onPostExecute(Integer resultado) {
	                   pd.dismiss();
	                   dialogSupervisores(SupervisionRespuesta.this);

	            }
	}


	    public void buscarUsuarios(){
	    	RequestWSAsignacion req = new RequestWSAsignacion();
	    	setCatalogoSupervisor(req.buscaSupervisores());
	    }
		public Button getGuardarButton() {
			return guardarButton;
		}

		public void setGuardarButton(Button guardarButton) {
			this.guardarButton = guardarButton;
		}



		public EditText getRespuestaEditText() {
			return respuestaEditText;
		}

		public void setRespuestaEditText(EditText respuestaEditText) {
			this.respuestaEditText = respuestaEditText;
		}

		public Button getCapturarButton() {
			return capturarButton;
		}

		public void setCapturarButton(Button capturarButton) {
			this.capturarButton = capturarButton;
		}

		public String getDescripcion() {
			return descripcion;
		}



		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Button getEstadoButton() {
			return estadoButton;
		}

		public void setEstadoButton(Button estadoButton) {
			this.estadoButton = estadoButton;
		}

		public String getPathFoto() {
			return pathFoto;
		}

		public void setPathFoto(String pathFoto) {
			this.pathFoto = pathFoto;
		}

		public ListView getListaFotosListView() {
			return listaFotosListView;
		}

		public void setListaFotosListView(ListView listaFotosListView) {
			this.listaFotosListView = listaFotosListView;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public RespuestaWS getRespuesta() {
			return respuesta;
		}

		public void setRespuesta(RespuestaWS respuesta) {
			this.respuesta = respuesta;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public TextView getEstadoTextView() {
			return estadoTextView;
		}

		public void setEstadoTextView(TextView estadoTextView) {
			this.estadoTextView = estadoTextView;
		}

		public CatalogoSupervisor getCatalogoSupervisor() {
			return catalogoSupervisor;
		}

		public void setCatalogoSupervisor(CatalogoSupervisor catalogoSupervisor) {
			this.catalogoSupervisor = catalogoSupervisor;
		}

		public String getIdReasignado() {
			return idReasignado;
		}

		public void setIdReasignado(String idReasignado) {
			this.idReasignado = idReasignado;
		}

		public TextView getOrdenTextView() {
			return ordenTextView;
		}

		public void setOrdenTextView(TextView ordenTextView) {
			this.ordenTextView = ordenTextView;
		}

		public Button getRegresarButton() {
			return regresarButton;
		}

		public void setRegresarButton(Button regresarButton) {
			this.regresarButton = regresarButton;
		}
		
}

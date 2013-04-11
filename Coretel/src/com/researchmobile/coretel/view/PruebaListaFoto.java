package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.Miembro;
import com.researchmobile.coretel.utility.RMFile;
import com.researchmobile.coretel.ws.RequestWS;

public class PruebaListaFoto extends Activity implements OnItemClickListener{
	private static RMFile rmFile = new RMFile();
	private static CatalogoMiembro catalogoMiembro;
	private TextView comunidad;
	private boolean esDuenno; // variable de verificaci—n si es due–o de la comunidad (EM)
	private String idComunidad;
	private ProgressDialog pd = null;
	private ListView listaMiembros;
	private RequestWS requestWS;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	 		setContentView(R.layout.miembros);
	 		
	 		Bundle bundle = getIntent().getExtras();
	 		setIdComunidad(bundle.getString("idComunidad"));
	 		setComunidad((TextView)findViewById(R.id.miembros_comunidad));
	 		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
	 		setRequestWS(new RequestWS());
	 		setEsDuenno((boolean)bundle.getBoolean("esDuenno"));
	 		
	 		new miembrosAsync().execute("");
	 		setListaMiembros((ListView)findViewById(R.id.miembros_lista_listview));
	 	}
	 
	// Clase para ejecutar en Background
		class miembrosAsync extends AsyncTask<String, Integer, Integer> {

			// Metodo que prepara lo que usara en background, Prepara el progress
			@Override
			protected void onPreExecute() {
				pd = ProgressDialog.show(PruebaListaFoto.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
				pd.setCancelable(false);
			}

			// Metodo con las instrucciones que se realizan en background
			@Override
			protected Integer doInBackground(String... urlString) {
				try {
					cargarDatos();

				} catch (Exception exception) {

				}
				return null;
			}

			// Metodo con las instrucciones al finalizar lo ejectuado en background
			protected void onPostExecute(Integer resultado) {
				pd.dismiss();
				llenarLista();

			}
		}
		
		public void llenarLista(){
			getListaMiembros().setAdapter(new miAdapter(this));
			getListaMiembros().setOnItemClickListener(this);
			String tituloComunidad = "";
	 		if (getCatalogoMiembro().getMiembro()[0].getNombreComunidad() != null){
	 			tituloComunidad = getCatalogoMiembro().getMiembro()[0].getNombreComunidad();
	 		}
	 		getComunidad().setText(tituloComunidad);
		}

		public void cargarDatos() {
			setCatalogoMiembro(getRequestWS().CatalogoMiembro(getIdComunidad()));
		}
	 
	 @Override
		public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
			Miembro miembro = getCatalogoMiembro().getMiembro()[position];
			Intent intent = new Intent(PruebaListaFoto.this, DetalleMiembro.class);
			intent.putExtra("idComunidad", getIdComunidad());
			intent.putExtra("miembro", miembro);
			if(isEsDuenno()){
			intent.putExtra("esDuenno", true);
			}else{
				intent.putExtra("esDuenno", false);
			}
			startActivity(intent);
		}
	 
	    private static class miAdapter extends BaseAdapter {
	 		private LayoutInflater mInflater;
	 		static int tamano = getCatalogoMiembro().getMiembro().length;
	 		
//	 		private static final String [][] m = new String[][tamano];
	 		public miAdapter(Context context) {
	 			mInflater = LayoutInflater.from(context);
	 		}
	 
			public View getView(int position, View convertView, ViewGroup parent) {
				
				rmFile.downloadImage(getCatalogoMiembro().getMiembro()[position].getAvatar());
				
	 			TextView text;
	 			TextView text2;
	 			ImageView img1;
	 
				if (convertView == null) {
	 				convertView = mInflater.inflate(R.layout.lista_miembros, null);
	 			} 
	 
				text = (TextView) convertView.findViewById(R.id.lista_miembros_id);
	 			text2 = (TextView) convertView.findViewById(R.id.lista_miembros_nombre);
	 			img1 = (ImageView) convertView.findViewById(R.id.lista_miembros_avatar);
	 			text.setText(getCatalogoMiembro().getMiembro()[position].getId());
	 			text2.setText(getCatalogoMiembro().getMiembro()[position].getNombreUsuario());
	 			Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + getCatalogoMiembro().getMiembro()[position].getAvatar());
				img1.setImageBitmap(image);
				return convertView;
			}
	 
			public int getCount() {
				return getCatalogoMiembro().getMiembro().length;
			}
	 
			public Object getItem(int position) {
				return position;
			}
	 
			public long getItemId(int position) {
				return position;
			}
		}

		public static CatalogoMiembro getCatalogoMiembro() {
			return catalogoMiembro;
		}

		public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
			this.catalogoMiembro = catalogoMiembro;
		}

		public TextView getComunidad() {
			return comunidad;
		}

		public void setComunidad(TextView comunidad) {
			this.comunidad = comunidad;
		}

		public boolean isEsDuenno() {
			return esDuenno;
		}

		public void setEsDuenno(boolean esDuenno) {
			this.esDuenno = esDuenno;
		}

		public String getIdComunidad() {
			return idComunidad;
		}

		public void setIdComunidad(String idComunidad) {
			this.idComunidad = idComunidad;
		}

		public ListView getListaMiembros() {
			return listaMiembros;
		}

		public void setListaMiembros(ListView listaMiembros) {
			this.listaMiembros = listaMiembros;
		}

		public RequestWS getRequestWS() {
			return requestWS;
		}

		public void setRequestWS(RequestWS requestWS) {
			this.requestWS = requestWS;
		}

		
	    
	}

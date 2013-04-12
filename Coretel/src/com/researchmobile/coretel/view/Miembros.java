package com.researchmobile.coretel.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.Miembro;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.RMFile;
import com.researchmobile.coretel.ws.RequestWS;

public class Miembros extends Activity implements OnClickListener, OnItemClickListener{
	private ListView miembrosListView;
	private CatalogoMiembro catalogoMiembro;
	private ProgressDialog pd = null;
	private RMFile rmFile = new RMFile();
	private String idMiembro;
	private RequestWS requestWS;
	private String idComunidad;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.miembros);
		
		setRequestWS(new RequestWS());
		Bundle bundle = (Bundle)getIntent().getExtras();
		setIdComunidad(bundle.getString("idComunidad"));
//		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
		setMiembrosListView((ListView)findViewById(R.id.miembros_lista_listview));
		getMiembrosListView().setOnItemClickListener(this);
		
		new miembrosAsync().execute("");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> a, View arg1, int position, long arg3) {
		Miembro miembro = new Miembro();
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap) a.getItemAtPosition(position);
		setIdMiembro(map.get("id"));
		miembro = buscaMiembro();
        Intent intent = new Intent(Miembros.this, DetalleMiembro.class);
        intent.putExtra("miembro", miembro);
        startActivity(intent);
		
	}
	
	public Miembro buscaMiembro(){
		Miembro miembro = new Miembro();
		int tamano = getCatalogoMiembro().getMiembro().length;
		for (int i = 0; i < tamano; i++){
			if (getCatalogoMiembro().getMiembro()[i].getId().equalsIgnoreCase(getIdMiembro())){
				miembro = getCatalogoMiembro().getMiembro()[i];
				return miembro;
			}
		}
		return miembro;
	}
	
	// Clase para ejecutar en Background
	class miembrosAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Miembros.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
		int tamano = getCatalogoMiembro().getMiembro().length;
		ArrayList<HashMap<String, Bitmap>> mylist = new ArrayList<HashMap<String, Bitmap>>();
		if (tamano > 0) {
			for (int i = 0; i < tamano; i++) {
				rmFile.downloadImage("http://23.23.1.2/" + getCatalogoMiembro().getMiembro()[i].getAvatar());
				Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", getCatalogoMiembro().getMiembro()[i].getId());
//				map.put("avatar", image);
				map.put("nombre", getCatalogoMiembro().getMiembro()[i].getNombreUsuario());
//				mylist.add(map);
			}
		}

		final SimpleAdapter mSchedule = new SimpleAdapter(this, mylist,
				R.layout.lista_miembros, new String[] { "id", "avatar", "nombre" },
				new int[] { R.id.lista_miembros_id, R.id.lista_miembros_avatar, R.id.lista_miembros_nombre });

		getMiembrosListView().setAdapter(mSchedule);
		getMiembrosListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	public void cargarDatos() {
		ConnectState conect = new ConnectState();
		if(conect.isConnectedToInternet(this)){
		setCatalogoMiembro(getRequestWS().CatalogoMiembro(getIdComunidad()));
		}else{
			Toast.makeText(this, "No posee conexion a internet, intente de nuevo mas tarde", Toast.LENGTH_SHORT).show();
		}
	}

	public ListView getMiembrosListView() {
		return miembrosListView;
	}

	public void setMiembrosListView(ListView miembrosListView) {
		this.miembrosListView = miembrosListView;
	}

	public CatalogoMiembro getCatalogoMiembro() {
		return catalogoMiembro;
	}

	public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
		this.catalogoMiembro = catalogoMiembro;
	}

	public String getIdMiembro() {
		return idMiembro;
	}

	public void setIdMiembro(String idMiembro) {
		this.idMiembro = idMiembro;
	}

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public String getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	

}

package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.tutorial.pasalo.TipoEvento_tutorial_1;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.MyAdapterTiposEventos;
import com.researchmobile.coretel.ws.RequestWS;

public class OpcionTipoEvento extends Activity implements OnClickListener{
	
	private Button agregarButton;
	private Button atrasButton;
	private ListView tiposListView;
	private String idComunidad;
	private CatalogoTipoAnotacion catalogoTipoAnotacion;
	private TipoAnotacion tipoAnotacion;
    private boolean esDuenno;
    private ProgressDialog pd = null;
    private ConnectState connectState;
   
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.opciontipoevento);
		setConnectState(new ConnectState());
		
		Bundle bundle = (Bundle)getIntent().getExtras();
//		setCatalogoTipoAnotacion((CatalogoTipoAnotacion)bundle.get("catalogoTipoAnotacion"));
		setIdComunidad((String)bundle.getString("idComunidad"));
		setEsDuenno((boolean)bundle.getBoolean("esDuenno"));
		setAgregarButton((Button)findViewById(R.id.opciontipoevento_agregar_button));
		setAtrasButton((Button)findViewById(R.id.opciontipoevento_atras_button));
		getAtrasButton().setOnClickListener(this);
		getAgregarButton().setOnClickListener(this);
		setTiposListView((ListView)findViewById(R.id.opciontipoevento_lista_listview));
		
		new tiposAsync().execute("");
		
		if(isEsDuenno()){
			Log.v("pio", "El usuario no es dueño de la comunidad, crear un nuevo tipo");
		}else{
			getAgregarButton().setVisibility(View.GONE);
		}
		
		
		
		if(!User.isModoTutorial()){
			Intent intent = new Intent(OpcionTipoEvento.this, TipoEvento_tutorial_1.class);
			startActivity(intent);
		}
			    
			    getTiposListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    	
		    	TipoAnotacion tipo = (TipoAnotacion)a.getItemAtPosition(position);
		    	Intent intent = new Intent(OpcionTipoEvento.this, Descevento.class);
		    	intent.putExtra("anotacion", tipo);
		    	intent.putExtra("esDuenno", isEsDuenno());
		    	intent.putExtra("idComunidad", getIdComunidad());
		    	intent.putExtra("activity", OpcionTipoEvento.class);
		    	startActivity(intent);
		    }

			private String buscaId(String seleccion) {
				int tamano = getCatalogoTipoAnotacion().getTipoAnotacion().length;
				String id = null;
				for (int i = 0; i < tamano; i++){
					if (getCatalogoTipoAnotacion().getTipoAnotacion()[i].getNombre().equalsIgnoreCase(seleccion)){
						setTipoAnotacion((TipoAnotacion)getCatalogoTipoAnotacion().getTipoAnotacion()[i]);
					}
				}
				return null;
			}
		});
	}
	
	// Clase para ejecutar en Background
	class tiposAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(OpcionTipoEvento.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				buscarTipos();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			llenaLista();
		}
	}
	
	public void llenaLista(){
//		if (ListaTipos() != null){
			MyAdapterTiposEventos adapterTipos = new MyAdapterTiposEventos(this, getCatalogoTipoAnotacion().getTipoAnotacion());
			getTiposListView().setAdapter(adapterTipos);
//		}
	}
	
	public void buscarTipos(){
		if(getConnectState().isConnectedToInternet(this)){
		RequestWS request = new RequestWS();
		setCatalogoTipoAnotacion(request.BuscarTiposEventos(getIdComunidad()));
		}else{
			Toast.makeText(this, "No posee conexion a internet, intente mas tarde!", Toast.LENGTH_SHORT).show();
		}
	}

	
	private String[] ListaTipos() {
		if (getCatalogoTipoAnotacion().getTipoAnotacion() != null){
			int tamano = getCatalogoTipoAnotacion().getTipoAnotacion().length;
			String tipos[] = new String[tamano];
			for (int i = 0; i < tamano; i++){
				tipos[i] = getCatalogoTipoAnotacion().getTipoAnotacion()[i].getNombre();
			}
			return tipos;
		}
		return null;
	}
	@Override
	public void onClick(View view) {
		if (view == getAgregarButton()){
			Intent intent = new Intent(OpcionTipoEvento.this, NuevoTipoEvento.class);
			intent.putExtra("idComunidad", getIdComunidad());
			intent.putExtra("activity", OpcionTipoEvento.class);
			startActivity(intent);
		}else if (view == getAtrasButton()){
			Intent intent = new Intent(OpcionTipoEvento.this, OpcionComunidades.class);
			intent.putExtra("deOpcion", true);
			startActivity(intent);
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(OpcionTipoEvento.this,
					OpcionComunidades.class);
			intent.putExtra("deOpcion", true);
			startActivity(intent);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public Button getAgregarButton() {
		return agregarButton;
	}
	public void setAgregarButton(Button agregarButton) {
		this.agregarButton = agregarButton;
	}
	public ListView getTiposListView() {
		return tiposListView;
	}
	public void setTiposListView(ListView tiposListView) {
		this.tiposListView = tiposListView;
	}
	public String getIdComunidad() {
		return idComunidad;
	}
	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	public CatalogoTipoAnotacion getCatalogoTipoAnotacion() {
		return catalogoTipoAnotacion;
	}
	public void setCatalogoTipoAnotacion(CatalogoTipoAnotacion catalogoTipoAnotacion) {
		this.catalogoTipoAnotacion = catalogoTipoAnotacion;
	}
	public TipoAnotacion getTipoAnotacion() {
		return tipoAnotacion;
	}
	public void setTipoAnotacion(TipoAnotacion tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}
	public boolean isEsDuenno() {
		return esDuenno;
	}
	public void setEsDuenno(boolean esDuenno) {
		this.esDuenno = esDuenno;
	}

	public ConnectState getConnectState() {
		return connectState;
	}

	public void setConnectState(ConnectState connectState) {
		this.connectState = connectState;
	}

	public Button getAtrasButton() {
		return atrasButton;
	}

	public void setAtrasButton(Button atrasButton) {
		this.atrasButton = atrasButton;
	}
	
}
package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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


public class TipoEvento extends Activity implements OnClickListener{
	private Button agregarButton;
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
		setContentView(R.layout.tipoevento);
		setConnectState(new ConnectState());
		Bundle bundle = (Bundle)getIntent().getExtras();
//		setCatalogoTipoAnotacion((CatalogoTipoAnotacion)bundle.get("catalogoTipoAnotacion"));
		setIdComunidad((String)bundle.getString("idComunidad"));
		setEsDuenno((boolean)bundle.getBoolean("esDuenno"));
		setAgregarButton((Button)findViewById(R.id.tipoevento_agregar_button));
		getAgregarButton().setOnClickListener(this);
		setTiposListView((ListView)findViewById(R.id.tipoevento_lista_listview));
		
		new tiposAsync().execute("");
		
		if(isEsDuenno()){
			Log.v("pio", "El usuario no es dueño de la comunidad, crear un nuevo tipo");
		}else{
			getAgregarButton().setVisibility(View.GONE);
		}
		
		if(!User.isModoTutorial()){
			Intent intent = new Intent(TipoEvento.this, TipoEvento_tutorial_1.class);
			startActivity(intent);
		}
			    
			    getTiposListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    	
		    	TipoAnotacion tipo = (TipoAnotacion)a.getItemAtPosition(position);
		    	Intent intent = new Intent(TipoEvento.this, Descevento.class);
		    	intent.putExtra("anotacion", tipo);
		    	intent.putExtra("activity", TipoEvento.class);
				intent.putExtra("esDuenno", esDuenno);
				intent.putExtra("idComunidad", getIdComunidad());
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
			pd = ProgressDialog.show(TipoEvento.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
		RequestWS request = new RequestWS();
		if(getConnectState().isConnectedToInternet(this)){
		setCatalogoTipoAnotacion(request.BuscarTiposEventos(getIdComunidad()));
		}else{
			Toast.makeText(this, "No posee conexion a internet, intente de nuevo!", Toast.LENGTH_SHORT).show();
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
			Intent intent = new Intent(TipoEvento.this, NuevoTipoEvento.class);
			intent.putExtra("idComunidad", getIdComunidad());
			intent.putExtra("activity", TipoEvento.class);
			intent.putExtra("esDuenno", esDuenno);
			startActivity(intent);
		}
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
	
}
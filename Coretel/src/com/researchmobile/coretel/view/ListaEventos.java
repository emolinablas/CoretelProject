package com.researchmobile.coretel.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.researchmobile.coretel.entity.Anotacion;
import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.MyAdapterEventos;
import com.researchmobile.coretel.ws.RequestWS;

public class ListaEventos extends Activity implements OnItemClickListener{
	
	private ListView eventosListView;
	private RequestWS request;
	private RespuestaWS respuesta;
	private SimpleAdapter simpleAdapter;
	private ProgressDialog pd = null;
	private CatalogoAnotacion catalogoAnotacion;
	private String idComunidad;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listaeventos);
        
        try{
        	Bundle bundle = getIntent().getExtras();
            setIdComunidad(bundle.getString("idComunidad"));
        }catch(Exception exception){
        	System.out.println("no llegaron datos");
        }
        
        setEventosListView((ListView)findViewById(R.id.listaeventos_eventos_listview));
        setRequest(new RequestWS());
        setRespuesta(new RespuestaWS());
        setCatalogoAnotacion(new CatalogoAnotacion());
        
        new eventosAsync().execute("");
    }
	
	private void buscaEventos(){
		RequestWS request = new RequestWS();
		setCatalogoAnotacion(request.CargarAnotacionesComunidad(getIdComunidad()));
		
	}
	 // Clase para ejecutar en Background
    class eventosAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(ListaEventos.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
          }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	buscaEventos();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (getCatalogoAnotacion().getAnotacion() != null){
                	if (getCatalogoAnotacion().getAnotacion().length > 0){
                		llenarLista();
                    }
                }
         }
    }
    
    public void llenarLista(){
    	
    	MyAdapterEventos adapterEventos = new MyAdapterEventos(this, getCatalogoAnotacion().getAnotacion());
    	getEventosListView().setAdapter(adapterEventos);
    	
//    	setSimpleAdapter(new SimpleAdapter(this, 
//        		myList(), 
//        		R.layout.lista_evento,
//                new String[] {"id","fecha", "activo", "tipo", "descripcion"}, 
//                new int[] {R.id.listaevento_id_textview, R.id.listaevento_fecha_textview, R.id.listaevento_activo_textview, R.id.listaevento_tipo_textview, R.id.listaevento_descripcion_textview}));
//        getEventosListView().setAdapter(getSimpleAdapter());
        getEventosListView().setOnItemClickListener(this);
    }


	
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long id) {
		@SuppressWarnings("unchecked")
		
		Anotacion anotacion = (Anotacion)adapter.getItemAtPosition(position);
		Intent intent = new Intent(ListaEventos.this, DetalleEvento.class);
		intent.putExtra("id", anotacion.getIdAnotacion());
		intent.putExtra("fecha", anotacion.getFecha_registro());
		intent.putExtra("activo", anotacion.getActivo());
		intent.putExtra("tipo", anotacion.getNombreTipoAnotacion());
		intent.putExtra("descripcion", anotacion.getDescripcion());
		intent.putExtra("idComunidad", getIdComunidad());
		startActivity(intent);
	}
	
	private ArrayList<HashMap<String, String>> myList(){
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		
		if (getCatalogoAnotacion().getAnotacion().length > 0){
			for (int i = 0; i < getCatalogoAnotacion().getAnotacion().length; i++){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", getCatalogoAnotacion().getAnotacion()[i].getIdAnotacion());
		        map.put("fecha", getCatalogoAnotacion().getAnotacion()[i].getFecha_registro());
		        map.put("activo", String.valueOf(getCatalogoAnotacion().getAnotacion()[i].getActivo()));
		        map.put("tipo", getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion());
		        map.put("descripcion", getCatalogoAnotacion().getAnotacion()[i].getDescripcion());
		        mylist.add(map);
			}
		}
		return mylist;
	}

	public ListView getEventosListView() {
		return eventosListView;
	}

	public void setEventosListView(ListView eventosListView) {
		this.eventosListView = eventosListView;
	}

	public RequestWS getRequest() {
		return request;
	}

	public void setRequest(RequestWS request) {
		this.request = request;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public SimpleAdapter getSimpleAdapter() {
		return simpleAdapter;
	}

	public void setSimpleAdapter(SimpleAdapter simpleAdapter) {
		this.simpleAdapter = simpleAdapter;
	}

	public CatalogoAnotacion getCatalogoAnotacion() {
		return catalogoAnotacion;
	}

	public void setCatalogoAnotacion(CatalogoAnotacion catalogoAnotacion) {
		this.catalogoAnotacion = catalogoAnotacion;
	}

	public String getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	
}

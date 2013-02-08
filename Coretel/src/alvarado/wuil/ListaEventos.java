package alvarado.wuil;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.entity.User;
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
		//Cargar Tipo de anotaciones por comunidades(idcomunidad)
		CatalogoTipoAnotacion tipoAnotaciones = new CatalogoTipoAnotacion();
		tipoAnotaciones = getRequest().BuscarTiposEventos(getIdComunidad());
		
		//Cargar Anotaciones(idcomunidad, idtipoanotacion)
		RequestWS request = new RequestWS();
		setCatalogoAnotacion(request.CargarAnotaciones(tipoAnotaciones.getTipoAnotacion()));
		System.out.println(getCatalogoAnotacion().getRespuesta().getMensaje());
		
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
                if (getCatalogoAnotacion().getAnotacion().length > 0){
                	llenarLista();
                }
         }
    }
    
    public void llenarLista(){
    	setSimpleAdapter(new SimpleAdapter(this, 
        		myList(), 
        		R.layout.lista_dos_campos,
                new String[] {"titulo","description"}, 
                new int[] {R.id.lista_titulo_textview, R.id.lista_descripcion_textview}));
        getEventosListView().setAdapter(getSimpleAdapter());
        getEventosListView().setOnItemClickListener(this);
    }


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(ListaEventos.this, DetalleEvento.class); 
		startActivity(intent);
		
	}
	
	private ArrayList<HashMap<String, String>> myList(){
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
		
		if (getCatalogoAnotacion().getAnotacion().length > 0){
			for (int i = 0; i < getCatalogoAnotacion().getAnotacion().length; i++){
				HashMap<String, String> map = new HashMap<String, String>();
		        map.put("titulo", getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion());
		        map.put("description", getCatalogoAnotacion().getAnotacion()[i].getDescripcion());
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

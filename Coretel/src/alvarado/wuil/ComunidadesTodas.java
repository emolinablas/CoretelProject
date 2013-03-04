package alvarado.wuil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class ComunidadesTodas extends Activity implements OnClickListener{
	private ListView comunidadesListView;
	private CatalogoComunidad catalogo;
	private CatalogoMiembro catalogoMiembro;
	private DetalleComunidad detalleComunidad;
	private ProgressDialog pd = null;
	private String select;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comunidadestodas);
		
//		Bundle bundle = (Bundle)getIntent().getExtras();
//		setCatalogo((CatalogoComunidad)bundle.get("catalogo"));
		setCatalogo(new CatalogoComunidad());
		
		new buscaComunidadesAsync().execute("");
		
		setComunidadesListView((ListView)findViewById(R.id.comunidadestodas_lista_listview));
		
	}
	
	// Clase para ejecutar en Background
    class buscaComunidadesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(ComunidadesTodas.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	buscaComunidades();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                resultadoComunidades();
         }
    }
    
    private void resultadoComunidades(){
    	if (getCatalogo() != null){
    		Log.e("pio", "comunidades = " + getCatalogo().getComunidad().length);
    		if (getCatalogo().getRespuestaWS().isResultado()){
    			getComunidadesListView().setAdapter(new ArrayAdapter<String>(this, 
    					R.layout.lista_solicitar_comunidad,
    					R.id.lista_solicitar_textview,
    					ListaComunidades()));
    					getComunidadesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    				    
    				    getComunidadesListView().setOnItemClickListener(new OnItemClickListener() {
    			    @Override
    			    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
    			    	setSelect(a.getItemAtPosition(position).toString());
    			    	new MiembrosAsync().execute("");
    			    }
    			});
    		}
    	}
    }
    private void buscaComunidades(){
    	Log.e("pio", "buscar comunidades");
    	RequestWS request = new RequestWS();
    	setCatalogo(request.CargarComunidadesTodas(User.getUserId()));
    }
	
	// Clase para ejecutar en Background
    class MiembrosAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(ComunidadesTodas.this, "VERIFICANDO DATOS",
                            "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	CargarDatos(getSelect());

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                Intent intent = new Intent(ComunidadesTodas.this, Comunidad.class);
		        intent.putExtra("catalogoMiembro", getCatalogoMiembro());
		        intent.putExtra("detallecomunidad", getDetalleComunidad());
		        startActivity(intent);
         }
   }

	
	private void CargarDatos(String select) {
		String idComunidad = "";
		int tamano = getCatalogo().getComunidad().length;
		for (int i = 0; i < tamano; i++){
			if (getCatalogo().getComunidad()[i].getNombre().equalsIgnoreCase(select)){
				idComunidad = getCatalogo().getComunidad()[i].getId();
			}
		}
		
		try{
			ConnectState con = new ConnectState();
			if (con.isConnectedToInternet(this)){
				RequestWS request = new RequestWS();
				setDetalleComunidad(request.DetalleComunidad(idComunidad));
				if(getDetalleComunidad().getRespuestaWS().isResultado()){
					setCatalogoMiembro(request.CatalogoMiembro(getDetalleComunidad().getId()));
					if (getCatalogoMiembro().getRespuestaWS().isResultado()){
						
					}
				}
			}
		}catch(Exception exception){
			
		}
	}
	
	private String[] ListaComunidades() {
		int tamano = getCatalogo().getComunidad().length;
		System.out.println(tamano);
		String[] comunidades = new String[tamano];
		for (int i = 0; i < tamano; i++){
			comunidades[i] = getCatalogo().getComunidad()[i].getNombre();
		}
		return comunidades;
	}


	@Override
	public void onClick(View view) {
		
		
	}


	private void AgregarComunidad() {
		Intent intent = new Intent(ComunidadesTodas.this, CreaComunidad.class);
		startActivity(intent);
		
	}

	public ListView getComunidadesListView() {
		return comunidadesListView;
	}


	public void setComunidadesListView(ListView comunidadesListView) {
		this.comunidadesListView = comunidadesListView;
	}


	public CatalogoComunidad getCatalogo() {
		return catalogo;
	}


	public void setCatalogo(CatalogoComunidad catalogo) {
		this.catalogo = catalogo;
	}

	public CatalogoMiembro getCatalogoMiembro() {
		return catalogoMiembro;
	}

	public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
		this.catalogoMiembro = catalogoMiembro;
	}

	public DetalleComunidad getDetalleComunidad() {
		return detalleComunidad;
	}

	public void setDetalleComunidad(DetalleComunidad detalleComunidad) {
		this.detalleComunidad = detalleComunidad;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
}

package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class ComunidadesTodas extends Activity implements OnClickListener, TextWatcher{
	private ListView comunidadesListView;
	private CatalogoComunidad catalogo;
	private CatalogoMiembro catalogoMiembro;
	private DetalleComunidad detalleComunidad;
	private ProgressDialog pd = null;
	private String select;
	private RespuestaWS respuesta;
	private Button atrasButton;
	private ArrayAdapter<String> adapter;
	private EditText buscarEditText;
	private Button borrarButton;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comunidadestodas);
		
//		Bundle bundle = (Bundle)getIntent().getExtras();
//		setCatalogo((CatalogoComunidad)bundle.get("catalogo"));
		setCatalogo(new CatalogoComunidad());
		setRespuesta(new RespuestaWS());
		setAtrasButton((Button)findViewById(R.id.comunidadtodas_atras_button));
		setBorrarButton((Button)findViewById(R.id.comunidadtodas_borrar_button));
		getBorrarButton().setOnClickListener(this);
		getAtrasButton().setOnClickListener(this);
		setBuscarEditText((EditText)findViewById(R.id.comunidadtodas_buscar_edittext));
		getBuscarEditText().addTextChangedListener(this);
		
		new buscaComunidadesAsync().execute("");
		
		setComunidadesListView((ListView)findViewById(R.id.comunidadestodas_lista_listview));
		
	}
	
	@Override
	public void onClick(View view) {
	
		if (view == getAtrasButton()){
			finish();
		}else if (view == getBorrarButton()){
			getBuscarEditText().setText("");
		}
		
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
    			
    			setAdapter(new ArrayAdapter<String>(this, 
    					R.layout.lista_solicitar_comunidad,
    					R.id.lista_solicitar_textview,
    					ListaComunidades()));
    					getComunidadesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    			
    			
    			getComunidadesListView().setAdapter(getAdapter());
    				    
    				    getComunidadesListView().setOnItemClickListener(new OnItemClickListener() {
    			    @Override
    			    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
    			    	setSelect(a.getItemAtPosition(position).toString());
    			    	new SolicitarAsync().execute("");
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
    class SolicitarAsync extends AsyncTask<String, Integer, Integer> {

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
                	enviarSolicitud(getSelect());

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (getRespuesta() != null){
                	Toast.makeText(getBaseContext(), getRespuesta().getMensaje(), Toast.LENGTH_SHORT).show();
                }
                
         }
   }

	
	private void enviarSolicitud(String select) {
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
				setRespuesta(request.solicitarComunidad(idComunidad));
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
	public RespuestaWS getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}
	public Button getAtrasButton() {
		return atrasButton;
	}
	public void setAtrasButton(Button atrasButton) {
		this.atrasButton = atrasButton;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		getAdapter().getFilter().filter(s);
        getAdapter().notifyDataSetChanged();

		
	}

	public ArrayAdapter<String> getAdapter() {
		return adapter;
	}

	public void setAdapter(ArrayAdapter<String> adapter) {
		this.adapter = adapter;
	}

	public EditText getBuscarEditText() {
		return buscarEditText;
	}

	public void setBuscarEditText(EditText buscarEditText) {
		this.buscarEditText = buscarEditText;
	}

	public Button getBorrarButton() {
		return borrarButton;
	}

	public void setBorrarButton(Button borrarButton) {
		this.borrarButton = borrarButton;
	}
	
	
}

package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.tutorial.pasalo.Comunidades_tutorial_1;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.MyAdapterComunidad;
import com.researchmobile.coretel.utility.MyAdapterMenu;
import com.researchmobile.coretel.ws.RequestWS;

public class OpcionComunidades extends Activity implements OnClickListener, OnItemClickListener{
	private ListView comunidadesListView;
	private DetalleComunidad detalleComunidad;
	private ProgressDialog pd = null;
	private String select;
	private RequestWS request;
	private CatalogoComunidad catalogoComunidad;
	private CatalogoTipoAnotacion catalogoTipoAnotacion;
	private Button agregarButton;
	private boolean opcionComunidad;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.opcioncomunidades);
		
		setAgregarButton((Button)findViewById(R.id.comunidades_opciones_agregar_button));
		getAgregarButton().setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		setOpcionComunidad(bundle.getBoolean("deOpcion"));
		
		if (isOpcionComunidad()){
			Log.v("pio", "de comunidad");
		}else{
			Log.v("pio", "de tipo evento");
		}
		setCatalogoComunidad(new CatalogoComunidad());
		setRequest(new RequestWS());
		
		new buscaComunidadesAsync().execute("");
		setComunidadesListView((ListView)findViewById(R.id.comunidades_lista_listview));
	}
	
@Override
public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
	DetalleComunidad comunidad = new DetalleComunidad();
	comunidad = (DetalleComunidad)adapterView.getItemAtPosition(position);
	if (isOpcionComunidad()){
		iniciarComunidad(comunidad);
		
	}else{
		Intent intent = new Intent(OpcionComunidades.this, OpcionTipoEvento.class);
		intent.putExtra("idComunidad", comunidad.getId());
		Log.v("pio", "usuario = " + User.getUserId() + " comunidad = " + comunidad.getIdDuenno());
		if (User.getUserId().equalsIgnoreCase(comunidad.getIdDuenno())){
			intent.putExtra("esDuenno", true);
		}
	    startActivity(intent);
	}
}

public void iniciarComunidad(DetalleComunidad comunidad){
	RequestWS request = new RequestWS();
	DetalleComunidad com = request.DetalleComunidad(comunidad.getId());
	Intent intent = new Intent(OpcionComunidades.this, Comunidad.class);
	intent.putExtra("detallecomunidad", com);
	startActivity(intent);
}

// Clase para ejecutar en Background
    class buscaComunidadesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(OpcionComunidades.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
    
    class comunidadesAsync extends AsyncTask<String, Integer, Integer> {

        // Metodo que prepara lo que usara en background, Prepara el progress
        @Override
        protected void onPreExecute() {
              pd = ProgressDialog. show(OpcionComunidades.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
              pd.setCancelable( false);
       }

        // Metodo con las instrucciones que se realizan en background
        @Override
        protected Integer doInBackground(String... urlString) {
              try {
              	buscarComunidades();

             } catch (Exception exception) {

             }
              return null ;
       }
        
        private void buscarComunidades(){
    		setRequest(new RequestWS());
    		setCatalogoComunidad(getRequest().CargarComunidades(User.getUserId()));
    	}

        // Metodo con las instrucciones al finalizar lo ejectuado en background
        protected void onPostExecute(Integer resultado) {
              pd.dismiss();
              try{
              	if (getCatalogoComunidad().getComunidad() != null && getCatalogoComunidad().getComunidad().length > 0){
                  	dialogComunidades();
                  }else{
                  	Toast.makeText(getBaseContext(), "no se encontraron comunidades", Toast.LENGTH_SHORT).show();
                  }
              }catch(Exception exception){
              	
              }
       }
 }
    
private void dialogComunidades(){
		
		LayoutInflater factory = LayoutInflater.from(OpcionComunidades.this);
        
        final View textEntryView = factory.inflate(R.layout.dialog_comunidades , null);
       
        final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_comunidades_spinner);
        ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, android.R.layout.simple_spinner_item, getCatalogoComunidad().getComunidad());
        comunidadesSpinner.setAdapter(adaptador);
        
        final AlertDialog.Builder alert = new AlertDialog.Builder(OpcionComunidades.this );

       alert.setTitle( "Elija una comunidad");
       alert.setView(textEntryView);
       alert.setPositiveButton( "   OK   " ,
                    new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface arg0, int arg1) {
                        	  DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
                        	  Intent intentChat = new Intent(OpcionComunidades.this, GroupChat.class);
                        	  intentChat.putExtra("comunidad", comunidad.getId());
                        	  startActivity(intentChat);
                               
                         }
                   });
       alert.setNegativeButton( "CANCELAR" ,
               new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface arg0, int arg1) {
                   	}
              });
       alert.show();
	}
    
    private void resultadoComunidades(){
    	if (getCatalogoComunidad() != null){
    		if (getCatalogoComunidad().getRespuestaWS().isResultado()){
    			
    			MyAdapterComunidad adapterComunidad = new MyAdapterComunidad(this, getCatalogoComunidad().getComunidad());
    			getComunidadesListView().setAdapter(adapterComunidad);
    			
    			getComunidadesListView().setOnItemClickListener(this);
    		}
    	}
    }
    private void buscaComunidades(){
    	Log.e("pio", "buscar comunidades");
    	RequestWS request = new RequestWS();
    	setCatalogoComunidad(request.CargarComunidades(User.getUserId()));
    	
    }
	
	// Clase para ejecutar en Background
    class TipoEventoAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(OpcionComunidades.this, "VERIFICANDO DATOS","ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	IniciaTipos();

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                //VERIFICAR
                if (getCatalogoTipoAnotacion().getRespuestaWS() != null){
                	Toast.makeText(getBaseContext(), getCatalogoTipoAnotacion().getRespuestaWS().getMensaje(), Toast.LENGTH_SHORT).show();
                	if (getCatalogoTipoAnotacion().getRespuestaWS().isResultado()){
                		Intent intent = new Intent(OpcionComunidades.this, OpcionTipoEvento.class);
                		intent.putExtra("tipos", getCatalogoTipoAnotacion());
        		        startActivity(intent);
                    }
                }
         }
   }
    
    protected void IniciaTipos() {
		ConnectState connect = new ConnectState();
		if (connect.isConnectedToInternet(OpcionComunidades.this)){
			RequestWS request = new RequestWS();
			setCatalogoTipoAnotacion(request.BuscarTiposEventos(getSelect()));
		}
    }
    private void AgregarComunidad() {
		Intent intent = new Intent(OpcionComunidades.this, CreaComunidad.class);
		startActivity(intent);
	}
    
	@Override
	public void onClick(View view) {
		
		if(view == getAgregarButton()){
			AgregarComunidad();
		}
		
	}

	public ListView getComunidadesListView() {
		return comunidadesListView;
	}


	public void setComunidadesListView(ListView comunidadesListView) {
		this.comunidadesListView = comunidadesListView;
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
	public RequestWS getRequest() {
		return request;
	}

	public void setRequest(RequestWS request) {
		this.request = request;
	}

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}

	public CatalogoTipoAnotacion getCatalogoTipoAnotacion() {
		return catalogoTipoAnotacion;
	}

	public void setCatalogoTipoAnotacion(CatalogoTipoAnotacion catalogoTipoAnotacion) {
		this.catalogoTipoAnotacion = catalogoTipoAnotacion;
	}

	public Button getAgregarButton() {
		return agregarButton;
	}

	public void setAgregarButton(Button agregarButton) {
		this.agregarButton = agregarButton;
	}

	public boolean isOpcionComunidad() {
		return opcionComunidad;
	}

	public void setOpcionComunidad(boolean opcionComunidad) {
		this.opcionComunidad = opcionComunidad;
	}
	
	
}

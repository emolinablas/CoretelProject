package com.researchmobile.coretel.view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.view.MapItemizedOverlaySelect.OnSelectPOIListener;
import com.researchmobile.coretel.ws.RequestWS;

public class MapWuil extends MapActivity implements OnItemClickListener, OnClickListener{
	private static final String LOG = "CORETEL-MapWuil";
	private MapController mapController;
	private MyLocationOverlay myLocationOverlay;
	private MapView mapView;
	protected List<Overlay> mapOverlays;
	private MapItemizedOverlaySelect itemizedoverlay;
	private CatalogoAnotacion catalogoAnotacion;
	
	private Button btnSatelite = null;
	private Button btnCentrar = null;
	private Button btnAnimar = null;
	private Button btnFilter = null;
	private Button btnReload = null;
	private LinearLayout bubbleFilterLayout = null;
	//Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	private ListView lView;
	private ListView comunidadesFilter;
	private ProgressDialog pd = null;
	private CatalogoComunidad catalogoComunidad;
	private TokenizerUtility tokenizer =new TokenizerUtility();
	private RequestWS requestWS;
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
	
	final List<GeoPoint> list = new ArrayList<GeoPoint>();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        setRequestWS(new RequestWS());
        
        setBtnAnimar((Button)findViewById(R.id.BtnAnimar));
        setBtnCentrar((Button)findViewById(R.id.BtnCentrar));
        setBtnFilter((Button)findViewById(R.id.filter_button_mapa));
        setBtnReload((Button)findViewById(R.id.reload_button_mapa));
        setBtnSatelite((Button)findViewById(R.id.BtnSatelite));
        getBtnAnimar().setOnClickListener(this);
        getBtnCentrar().setOnClickListener(this);
        getBtnFilter().setOnClickListener(this);
        getBtnReload().setOnClickListener(this);
        getBtnSatelite().setOnClickListener(this);
        
        bubbleFilterLayout = (LinearLayout)findViewById(R.id.bubble_filter_layout);
    	comunidadesFilter = (ListView)findViewById(R.id.comunidades_filter);
    	lView = (ListView) findViewById(R.id.lista);
        lView.setOnItemClickListener(this);
    	comunidadesFilter.setOnItemClickListener(this);
        
        new buscaAnotacionesAsync().execute("");
    }
    
	@Override
	public void onClick(View v) {
		if (v == getBtnAnimar()){
			opcionAnimar();
		}else if (v == getBtnCentrar()){
			opcionCentrar();
		}else if (v == getBtnFilter()){
			opcionFiltrar();
		}else if (v == getBtnReload()){
			opcionReload();
		}else if (v == getBtnSatelite()){
			opcionSatelite();
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
		if (adapterView == lView){
			collapseMenu();
			opcionesMenu(arg2);
		}else if (adapterView == comunidadesFilter){
			DetalleComunidad comunidad = (DetalleComunidad) adapterView.getItemAtPosition(arg2);
			bubbleFilterLayout.setVisibility(View.GONE);
			filtrarComunidades(comunidad);
		}
	}
	
	private void filtrarComunidades(DetalleComunidad comunidad){
		mapOverlays.clear();
		int tamano = getCatalogoAnotacion().getAnotacion().length;
    	for (int i = 0; i < tamano; i++){
    		if (getCatalogoAnotacion().getAnotacion()[i].getIdcomunidad().equalsIgnoreCase(comunidad.getId())){
    			list.add(new GeoPoint((int)(getCatalogoAnotacion().getAnotacion()[i].getLatitud() *1E6), (int)(getCatalogoAnotacion().getAnotacion()[i].getLongitud() * 1E6)));
        		String titulo = getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+=" 
        			+ getCatalogoAnotacion().getAnotacion()[i].getIdAnotacion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getIdcomunidad() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getIcono();
        		String desc = getCatalogoAnotacion().getAnotacion()[i].getDescripcion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getFecha_registro() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreComunidad() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getImagen();
        		agregaPuntos(list.get(i), titulo, desc);
    		}else if (comunidad.getId().equalsIgnoreCase("100000")){
    			list.add(new GeoPoint((int)(getCatalogoAnotacion().getAnotacion()[i].getLatitud() *1E6), (int)(getCatalogoAnotacion().getAnotacion()[i].getLongitud() * 1E6)));
        		String titulo = getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+=" 
        			+ getCatalogoAnotacion().getAnotacion()[i].getIdAnotacion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getIdcomunidad() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getIcono();
        		String desc = getCatalogoAnotacion().getAnotacion()[i].getDescripcion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getFecha_registro() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreComunidad() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getImagen();
        		agregaPuntos(list.get(i), titulo, desc);
    		}
    	}
	}
	
	private void opcionSatelite() {
		try {
			if (mapView.isSatellite()) {
				mapView.setSatellite(false);
			} else {
				mapView.setSatellite(true);
			}
		} catch (Exception exception) {

		}
	}
	
	private void opcionReload() {
		try{
			mapOverlays.clear();
			new buscaAnotacionesAsync().execute("");
		}catch(Exception exception){
			
		}
	}
	
	private void opcionFiltrar() {
		try{
			if (bubbleFilterLayout.getVisibility() == View.VISIBLE) {
				bubbleFilterLayout.setVisibility(View.GONE);
			} else {
				bubbleFilterLayout.setVisibility(View.VISIBLE);
				new filtrarComunidadesAsync().execute("");

			}
		}catch(Exception exception){
			
		}
	}
	

	private void opcionCentrar() {
		try {
			GeoPoint loc = new GeoPoint(myLocationOverlay.getMyLocation()
					.getLatitudeE6(), myLocationOverlay.getMyLocation()
					.getLongitudeE6());

			list.add(loc);
			agregaPuntos(loc, "nuevo", "nuevo punto");
			mapController.animateTo(myLocationOverlay.getMyLocation());
		} catch (Exception exception) {

		}

	}
	private void opcionAnimar() {
		try {
			GeoPoint loc = new GeoPoint(myLocationOverlay.getMyLocation()
					.getLatitudeE6(), myLocationOverlay.getMyLocation()
					.getLongitudeE6());

			mapController.animateTo(loc);

			int zoomActual = mapView.getZoomLevel();
			for (int i = zoomActual; i < 10; i++) {
				mapController.zoomIn();
			}
		} catch (Exception exception) {

		}

	}
    private void inicializar(){
    	/***
         * MENU
         */
        
        String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Mi Perfil", "Chat", "Cerrar sesi�n" };

      // Set option as Multiple Choice. So that user can able to select more the one option from list
      lView.setAdapter(new ArrayAdapter<String>(this,
      android.R.layout.simple_list_item_1, lv_items));
      lView.setOnItemClickListener(this);
      animationMenu();
        
        
//        Bundle bundle = (Bundle)getIntent().getExtras();
//        setCatalogoAnotacion((CatalogoAnotacion)bundle.get("anotaciones"));
        inicializeMap();
        mapOverlays = mapView.getOverlays();
        
    	
        itemizedoverlay = new MapItemizedOverlaySelect();     
        mapOverlays.add(itemizedoverlay);    
        VerificarPuntos(list);
        itemizedoverlay.setOnSelectPOIListener(new OnSelectPOIListener() {   
        	public void onSelectPOI(int latitud, int longitud) {
        		GeoPoint loc = new GeoPoint((int)(latitud), (int)(longitud));
        		list.add(loc);
        		agregaPuntos(loc, "nuevo", "nuevo punto");
        	}
        });
    }
    


//Clase para ejecutar en Background
class buscaAnotacionesAsync extends AsyncTask<String, Integer, Integer> {

      // Metodo que prepara lo que usara en background, Prepara el progress
      @Override
      protected void onPreExecute() {
            pd = ProgressDialog. show(MapWuil.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
            pd.setCancelable( false);
     }

      // Metodo con las instrucciones que se realizan en background
      @Override
      protected Integer doInBackground(String... urlString) {
            try {
            	cargarAnotaciones();
           } catch (Exception exception) {

           }
            return null ;
     }

      // Metodo con las instrucciones al finalizar lo ejectuado en background
      protected void onPostExecute(Integer resultado) {
            pd.dismiss();
            inicializar();

     }
}

	private void cargarAnotaciones() {
		//Cargar Anotaciones(idcomunidad, idtipoanotacion)
		RequestWS request = new RequestWS();
		setCatalogoAnotacion(request.CargarAnotaciones());
	}

    
    private void animationMenu(){
    	//Initialize
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels)*0.75);
	
		headerPanel = (RelativeLayout) findViewById(R.id.header);
		headerPanelParameters = (LinearLayout.LayoutParams) headerPanel.getLayoutParams();
		headerPanelParameters.width = metrics.widthPixels;
		headerPanel.setLayoutParams(headerPanelParameters);
		
		menuPanel = (RelativeLayout) findViewById(R.id.menuPanel);
		menuPanelParameters = (FrameLayout.LayoutParams) menuPanel.getLayoutParams();
		menuPanelParameters.width = panelWidth;
		menuPanel.setLayoutParams(menuPanelParameters);
		
		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		
		//Slide the Panel	
		menuViewButton = (ImageView) findViewById(R.id.menuViewButton);
		menuViewButton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	if(!isExpanded){
		    		expandMenu();
		    	}else{
		    		collapseMenu();
		    	}         	   
		    }
		});
    }
    
    private void expandMenu(){
    	//Expand
    	isExpanded = true;
		new ExpandAnimation(slidingPanel, panelWidth,
	    Animation.RELATIVE_TO_SELF, 0.0f,
	    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
    }
    
    private void collapseMenu(){
    	//Collapse
    	isExpanded = false;
		new CollapseAnimation(slidingPanel,panelWidth,
	    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
	    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
    }
    private void VerificarPuntos(List<GeoPoint> list) {
    	if (getCatalogoAnotacion().getAnotacion() != null){
    		int tamano = getCatalogoAnotacion().getAnotacion().length;
        	for (int i = 0; i < tamano; i++){
        		list.add(new GeoPoint((int)(getCatalogoAnotacion().getAnotacion()[i].getLatitud() *1E6), (int)(getCatalogoAnotacion().getAnotacion()[i].getLongitud() * 1E6)));
        		String titulo = getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+=" 
        			+ getCatalogoAnotacion().getAnotacion()[i].getIdAnotacion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getIdcomunidad() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getIcono();
        		String desc = getCatalogoAnotacion().getAnotacion()[i].getDescripcion() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getFecha_registro() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getNombreComunidad() + "=+="
        			+ getCatalogoAnotacion().getAnotacion()[i].getImagen();
        		agregaPuntos(list.get(i), titulo, desc);
        	}
    	}
    }

	protected void agregaPuntito(int latitud, int longitud) {
    	
    	//mapOverlays = mapView.getOverlays();
    	Drawable drawable = getResources().getDrawable(R.drawable.marker);
        MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, mapView.getContext(), mapView);
    	OverlayItem overlayItem = new OverlayItem(new GeoPoint((int)(latitud * 1E6), (int)(longitud * 1E6)), "punto ", "descripcion ");     
        itemizedoverlay.addOverlay(overlayItem);
        mapOverlays.add(itemizedoverlay);
    }
    
    private void agregaPuntos(GeoPoint list, String titulo, String desc) {
    	
    	//for (int i = 0; i < list.size(); i++){
    		//Toast.makeText(getBaseContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
    	
    	
    	//imageView.setImageResource(mThumbIds[position]);
    	
    	BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Bitmap bm = BitmapFactory.decodeFile("sdcard/" + tokenizer.icono(titulo), options);
        
		Drawable drawable = tokenizer.iconoResource(this, titulo);
//    		Drawable drawable = new BitmapDrawable(bm);
        	MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, mapView.getContext(), mapView);
    		OverlayItem overlayItem = new OverlayItem(list, titulo, desc);
    		Log.e("TT", "22 titulo completo = " + overlayItem.getTitle());
            itemizedoverlay.addOverlay(overlayItem);
            mapOverlays.add(itemizedoverlay);
    		
    	//}
    }

	private void inicializeMap(){
        mapView = (MapView) findViewById(R.id.map);
        mapView.setBuiltInZoomControls(true);        
        mapController = mapView.getController();
        mapController.setZoom(20);
        int latitud = (int) (14.627853 * 1E6);
        int longitud = (int) (-90.517584 * 1E6);
        mapController.animateTo(new GeoPoint(latitud,longitud));
        centerMyPosition();
   }
    
    private void centerMyPosition(){
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
            	try{
            		mapController.animateTo(myLocationOverlay.getMyLocation());
            	}catch(Exception exception){
            		Log.e("TT", "MapWuil error buscando posicion");
            	}
            	
            }
        });
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

	public CatalogoAnotacion getCatalogoAnotacion() {
		return catalogoAnotacion;
	}

	public void setCatalogoAnotacion(CatalogoAnotacion catalogoAnotacion) {
		this.catalogoAnotacion = catalogoAnotacion;
	}
	
	private void opcionesMenu(int opcion){
		switch(opcion){
		case 0:
			new buscaAnotacionesAsync().execute("");
			break;
		case 1:
			Intent intentComunidades = new Intent(MapWuil.this, Comunidades.class);
			startActivity(intentComunidades);
			break;
		case 2:
			Intent intentInvitaciones = new Intent(MapWuil.this, Invitaciones.class);
			startActivity(intentInvitaciones);
			break;
		case 3:
			Intent intentLobby = new Intent(MapWuil.this, Lobby.class);
			startActivity(intentLobby);
			break;
		case 4:
			new comunidadesAsync().execute("");
			break;
		case 5:
			Intent intentCerrar = new Intent(MapWuil.this, Login.class);
			startActivity(intentCerrar);
			break;
        default:
            break;

		}
	}
	
	 // Clase para ejecutar en Background
    class comunidadesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(MapWuil.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
    
    // Clase para ejecutar en Background
    class filtrarComunidadesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(MapWuil.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	buscarComunidadesFilter();

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                try{
                	if (getCatalogoComunidad().getComunidad() != null && getCatalogoComunidad().getComunidad().length > 0){
                		ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(MapWuil.this, android.R.layout.simple_list_item_1, getCatalogoComunidad().getComunidad());
    					comunidadesFilter.setAdapter(adaptador);
                    }else{
                    	Toast.makeText(getBaseContext(), "no se encontraron comunidades", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception exception){
                	
                }
         }
   }


	private void buscarComunidades(){
		setRequestWS(new RequestWS());
		setCatalogoComunidad(getRequestWS().CargarComunidades(User.getUserId()));
	}
	
	private void buscarComunidadesFilter(){
		setRequestWS(new RequestWS());
		setCatalogoComunidad(getRequestWS().CargarComunidadesFilter(User.getUserId()));
	}
	private void dialogComunidades(){
		
		LayoutInflater factory = LayoutInflater.from(MapWuil.this);
        
        final View textEntryView = factory.inflate(R.layout.dialog_comunidades , null);
       
        final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_comunidades_spinner);
        ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, android.R.layout.simple_spinner_item, getCatalogoComunidad().getComunidad());
        comunidadesSpinner.setAdapter(adaptador);
        
        final AlertDialog.Builder alert = new AlertDialog.Builder(MapWuil.this );

       alert.setTitle( "Elija una comunidad");
       alert.setView(textEntryView);
       alert.setPositiveButton( "   OK   " ,
                    new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface arg0, int arg1) {
                        	  DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
                        	  Toast.makeText(getBaseContext(), comunidad.getId(), Toast.LENGTH_SHORT).show();
                        	  Intent intentChat = new Intent(MapWuil.this, GroupChat.class);
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

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	      if (keyCode == KeyEvent.KEYCODE_BACK) {
	            // Preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR     
	            return true;
	      }
	      
	      return super.onKeyDown(keyCode, event);
    }

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public Button getBtnSatelite() {
		return btnSatelite;
	}

	public void setBtnSatelite(Button btnSatelite) {
		this.btnSatelite = btnSatelite;
	}

	public Button getBtnCentrar() {
		return btnCentrar;
	}

	public void setBtnCentrar(Button btnCentrar) {
		this.btnCentrar = btnCentrar;
	}

	public Button getBtnAnimar() {
		return btnAnimar;
	}

	public void setBtnAnimar(Button btnAnimar) {
		this.btnAnimar = btnAnimar;
	}

	public Button getBtnFilter() {
		return btnFilter;
	}

	public void setBtnFilter(Button btnFilter) {
		this.btnFilter = btnFilter;
	}

	public Button getBtnReload() {
		return btnReload;
	}

	public void setBtnReload(Button btnReload) {
		this.btnReload = btnReload;
	}
	
	

}
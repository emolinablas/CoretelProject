package com.researchmobile.coretel.view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.researchmobile.coretel.supervision.entity.EventoTemporal;
import com.researchmobile.coretel.tutorial.pasalo.Mapa_tutorial_1;
import com.researchmobile.coretel.utility.MyAdapterMenu;
import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.view.MapItemizedOverlaySelect.OnSelectPOIListener;
import com.researchmobile.coretel.ws.RequestDB;
import com.researchmobile.coretel.ws.RequestWS;

public class Mapa extends MapActivity implements OnItemClickListener, OnClickListener{
	private static final String LOG = "pio";
	private MapController mapController;
	private MyLocationOverlay myLocationOverlay;
	private MapView mapView;
	protected List<Overlay> mapOverlays;
	private MapItemizedOverlaySelect itemizedoverlay;
	private CatalogoAnotacion catalogoAnotacion;
	private TextView tituloComunidad;
	private boolean itemTemporal = false;
	
	private Button btnSatelite = null;
	private Button btnCentrar = null;
	private Button btnAnimar = null;
	private Button btnFilter = null;
	private Button btnReload = null;
	private Button btnTipoComunidad = null;
	private Button btnLugares = null;
	private LinearLayout bubbleFilterLayout = null;
	private LinearLayout bubbleLugaresLayout = null;
	//Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	
	private ImageView avatarImageView;
	private TextView nombreUsuarioTextView;
	private ImageView menuViewButton;
	private ListView lView;
	private ListView comunidadesFilter;
	private ListView lugaresFilter;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapa);
        setRequestWS(new RequestWS());
        EventoTemporal.setControl(0);
        
        setNombreUsuarioTextView((TextView)findViewById(R.id.menu_title_1));
        getNombreUsuarioTextView().setText(User.getNombre());
        setBtnAnimar((Button)findViewById(R.id.BtnAnimar));
        setBtnCentrar((Button)findViewById(R.id.BtnCentrar));
        setBtnFilter((Button)findViewById(R.id.filter_button_mapa));
        setBtnReload((Button)findViewById(R.id.reload_button_mapa));
        setBtnSatelite((Button)findViewById(R.id.BtnSatelite));
        setBtnTipoComunidad((Button)findViewById(R.id.BtnTipoComunidad));
        setBtnLugares((Button)findViewById(R.id.BtnLugares));
        setAvatarImageView((ImageView)findViewById(R.id.mapa_avatar));
        getBtnAnimar().setOnClickListener(this);
        getBtnCentrar().setOnClickListener(this);
        getBtnFilter().setOnClickListener(this);
        getBtnReload().setOnClickListener(this);
        getBtnSatelite().setOnClickListener(this);
        getBtnTipoComunidad().setOnClickListener(this);
        getBtnLugares().setOnClickListener(this);
        setTituloComunidad((TextView)findViewById(R.id.mapa_titulo_comunidad));
        getTituloComunidad().setText("Todos");
        
        setMenuViewButton((ImageView) findViewById(R.id.menuViewButton));
        getMenuViewButton().setOnClickListener(this);
        bubbleFilterLayout = (LinearLayout)findViewById(R.id.bubble_filter_layout);
        bubbleLugaresLayout = (LinearLayout)findViewById(R.id.bubble_lugares_layout);
    	comunidadesFilter = (ListView)findViewById(R.id.comunidades_filter);
    	lugaresFilter = (ListView)findViewById(R.id.lugares_filter);
    	lView = (ListView) findViewById(R.id.lista);
        lView.setOnItemClickListener(this);
    	comunidadesFilter.setOnItemClickListener(this);
    	lugaresFilter.setOnItemClickListener(this);
    	
    	String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Mi Perfil", "Chat", "Cerrar sesión" };
    	String lugares_items[] = { "Hoteles", "Restaurantes", "Cafetería", "Otros"};

		// Set option as Multiple Choice. So that user can able to select more
		// the one option from list
    	MyAdapterMenu adapterMenu = new MyAdapterMenu(this, lv_items);
		lView.setAdapter(adapterMenu);
		lView.setOnItemClickListener(this);
		
		lugaresFilter.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lugares_items));
		lugaresFilter.setOnItemClickListener(this);
		
		
		animationMenu();
    	Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
		getAvatarImageView().setImageBitmap(image);
        
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
		}else if (v == getMenuViewButton()){
			startMenu();
		}else if (v == getBtnTipoComunidad()){
			dialogTiposComunidades(this);
		}else if (v == getBtnLugares()){
			opcionLugares();
		}
	}
	
	public void startMenu() {
		if (!isExpanded) {
			expandMenu();
		} else {
			collapseMenu();
		}
	}
	
	public void dialogTiposComunidades(final Context activity) {

        final Dialog myDialog = new Dialog(activity);
        myDialog.setContentView(R.layout.messagescreen);
        myDialog.setTitle("Opciones");
        myDialog.setCancelable(false);
        
        Button cerrar = (Button) myDialog.findViewById(R.id.cerrar_in);
        cerrar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        Button login = (Button) myDialog.findViewById(R.id.log_in);
        login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent intentComunidades = new Intent(Mapa.this, OpcionComunidades.class);
    			startActivity(intentComunidades);
                myDialog.dismiss();
            }
        });

        Button createAccount= (Button) myDialog.findViewById(R.id.create_account);
        createAccount.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Intent intentComunidades = new Intent(Mapa.this, OpcionComunidades.class);
    			startActivity(intentComunidades);
                myDialog.dismiss();
            }
        });


        myDialog.show();

    }
	
	public void dialogTutoriales(final Context activity) {
		if (!User.isModoTutorial()){
			final Dialog myDialog = new Dialog(activity);
	        myDialog.setContentView(R.layout.dialog_tutorial);
	        myDialog.setCancelable( false);
	       
	        Button verTutorial = (Button) myDialog.findViewById(R.id.dialog_tutorial_vertutorial);
	        verTutorial.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	            	User.setModoTutorial(true);
	            	if(User.isModoTutorial()){
	        			Intent intent = new Intent(Mapa.this, Mapa_tutorial_1.class);
	        			startActivity(intent);
	        		}
	                myDialog.dismiss();
	            }
	        });

	        Button noVerTutorial = (Button) myDialog.findViewById(R.id.dialog_tutorial_novertutorial);
	        noVerTutorial.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                User.setModoTutorial(false);  
	                myDialog.dismiss();
	            }
	        });

	        Button nuncaVerTutorial= (Button) myDialog.findViewById(R.id.dialog_tutorial_nuncaver);
	        nuncaVerTutorial.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                desactivarModoTutorialDB(Mapa.this); 
	                myDialog.dismiss();
	            }
	        });


	        myDialog.show();
		}
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int arg2, long arg3) {
		if (adapterView == lView){
			collapseMenu();
			opcionesMenu(arg2);
		}else if (adapterView == comunidadesFilter){
			DetalleComunidad comunidad = (DetalleComunidad) adapterView.getItemAtPosition(arg2);
			opcionFiltrar();
			filtrarComunidades(comunidad);
		}else if (adapterView == lugaresFilter){
			opcionLugares();
		}
	}
	
	private void filtrarComunidades(DetalleComunidad comunidad){
		getTituloComunidad().setText(comunidad.getNombre());
		mapOverlays.clear();
		if (getCatalogoAnotacion().getAnotacion() != null){
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
		}else{
			Toast.makeText(getBaseContext(), "intente nuevamentete", Toast.LENGTH_LONG).show();
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
	
	private void opcionLugares() {
		try{
			if (bubbleLugaresLayout.getVisibility() == View.VISIBLE) {
				bubbleLugaresLayout.setVisibility(View.GONE);
			} else {
				bubbleLugaresLayout.setVisibility(View.VISIBLE);
//				new filtrarComunidadesAsync().execute("");

			}
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

	private void inicializar() {
		/***
		 * MENU
		 */

		

		inicializeMap();
		mapOverlays = mapView.getOverlays();

		itemizedoverlay = new MapItemizedOverlaySelect();
		mapOverlays.add(itemizedoverlay);
		VerificarPuntos(list);
		itemizedoverlay.setOnSelectPOIListener(new OnSelectPOIListener() {
			public void onSelectPOI(int latitud, int longitud) {
				Log.v("pio", "nuevo punto nuevo punto");
				GeoPoint loc = new GeoPoint((int) (latitud), (int) (longitud));
				list.add(loc);
				agregaPuntos(loc, "nuevo", "nuevo punto");
				EventoTemporal.setNuevo(true);
			}
		});
	}
    
	// Clase para ejecutar en Background
	class buscaAnotacionesAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Mapa.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				cargarAnotaciones();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			dialogTutoriales(Mapa.this);
			inicializar();
		}
	}
	
	private void modoTutorialDB(Context context){
		RequestDB request = new RequestDB();
		User.setModoTutorial(request.modoTutorial(context));
		Log.v("pio", "modoTutorial = " + User.isModoTutorial());
	}
	
	private void desactivarModoTutorialDB(Context context){
		RequestDB request = new RequestDB();
		request.desactivaModoTutorial(context);
		User.setModoTutorial(true);
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
		Log.e("TT", "agrega puntito = " );
    	
    	//mapOverlays = mapView.getOverlays();
    	Drawable drawable = getResources().getDrawable(R.drawable.marker);
        MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, mapView.getContext(), mapView);
    	OverlayItem overlayItem = new OverlayItem(new GeoPoint((int)(latitud * 1E6), (int)(longitud * 1E6)), "punto ", "descripcion ");     
        itemizedoverlay.addOverlay(overlayItem);
        
        mapOverlays.add(itemizedoverlay);
    }
    
    private void agregaPuntos(GeoPoint list, String titulo, String desc) {
    	
    	BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Bitmap bm = BitmapFactory.decodeFile("sdcard/" + tokenizer.icono(titulo), options);
        
		Drawable drawable = tokenizer.iconoResource(this, titulo);
		int tamano = mapOverlays.size();
//    		Drawable drawable = new BitmapDrawable(bm);
        	MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, mapView.getContext(), mapView);
        	
    		OverlayItem overlayItem = new OverlayItem(list, titulo, desc);
    		Log.e("TT", "22 titulo completo = " + overlayItem.getTitle());
    		Log.e("TT", "tamaño en mapoverlays = " + tamano);
    		if (overlayItem.getTitle().equalsIgnoreCase("nuevo")){
    			if (!itemTemporal){
    				itemTemporal = true;
    				
    				EventoTemporal.setControl(1);
    				Log.v("pio", "eventotemporal = " + EventoTemporal.getControl());
    			}else{
    				EventoTemporal.setControl(1);
    				mapOverlays.remove(tamano - 1);
    			}
    		}else{
    			
    		}
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
			Intent intentComunidades = new Intent(Mapa.this, Comunidades.class);
			startActivity(intentComunidades);
			break;
		case 2:
			Intent intentInvitaciones = new Intent(Mapa.this, Invitaciones.class);
			startActivity(intentInvitaciones);
			break;
		case 3:
			Intent intentLobby = new Intent(Mapa.this, Lobby.class);
			startActivity(intentLobby);
			break;
		case 4:
			new comunidadesAsync().execute("");
			break;
		case 5:
			Intent intentCerrar = new Intent(Mapa.this, Login.class);
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
                pd = ProgressDialog. show(Mapa.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
                pd = ProgressDialog. show(Mapa.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
                		ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(Mapa.this, android.R.layout.simple_list_item_1, getCatalogoComunidad().getComunidad());
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
		
		LayoutInflater factory = LayoutInflater.from(Mapa.this);
        
        final View textEntryView = factory.inflate(R.layout.dialog_comunidades , null);
       
        final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_comunidades_spinner);
        ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, R.layout.item_spinner, R.id.item_spinner_textview, getCatalogoComunidad().getComunidad());
        comunidadesSpinner.setAdapter(adaptador);
        
        final AlertDialog.Builder alert = new AlertDialog.Builder(Mapa.this );

       alert.setTitle( "Elija una comunidad");
       alert.setView(textEntryView);
       alert.setPositiveButton( "   OK   " ,
                    new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface arg0, int arg1) {
                        	  DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
                        	  Intent intentChat = new Intent(Mapa.this, GroupChat.class);
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

	public TextView getNombreUsuarioTextView() {
		return nombreUsuarioTextView;
	}

	public void setNombreUsuarioTextView(TextView nombreUsuarioTextView) {
		this.nombreUsuarioTextView = nombreUsuarioTextView;
	}

	public ImageView getAvatarImageView() {
		return avatarImageView;
	}

	public void setAvatarImageView(ImageView avatarImageView) {
		this.avatarImageView = avatarImageView;
	}

	public ImageView getMenuViewButton() {
		return menuViewButton;
	}

	public void setMenuViewButton(ImageView menuViewButton) {
		this.menuViewButton = menuViewButton;
	}

	public Button getBtnTipoComunidad() {
		return btnTipoComunidad;
	}

	public void setBtnTipoComunidad(Button btnTipoComunidad) {
		this.btnTipoComunidad = btnTipoComunidad;
	}

	public Button getBtnLugares() {
		return btnLugares;
	}

	public void setBtnLugares(Button btnLugares) {
		this.btnLugares = btnLugares;
	}

	public TextView getTituloComunidad() {
		return tituloComunidad;
	}

	public void setTituloComunidad(TextView tituloComunidad) {
		this.tituloComunidad = tituloComunidad;
	}

	
}
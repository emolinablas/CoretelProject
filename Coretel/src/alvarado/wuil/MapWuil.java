package alvarado.wuil;

import java.util.ArrayList;
import java.util.List;

import alvarado.wuil.MapItemizedOverlaySelect.OnSelectPOIListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.researchmobile.coretel.entity.CatalogoAnotacion;

public class MapWuil extends MapActivity implements OnItemClickListener{
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
	//Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	private ListView lView;
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
	
	final List<GeoPoint> list = new ArrayList<GeoPoint>();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        
        /***
         * MENU
         */
        
        String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Loby", "Chat", "Cerrar sesión" };

      lView = (ListView) findViewById(R.id.lista);
      // Set option as Multiple Choice. So that user can able to select more the one option from list
      lView.setAdapter(new ArrayAdapter<String>(this,
      android.R.layout.simple_list_item_1, lv_items));
      lView.setOnItemClickListener(this);
      animationMenu();
        
        
        Bundle bundle = (Bundle)getIntent().getExtras();
        setCatalogoAnotacion((CatalogoAnotacion)bundle.get("anotaciones"));
        btnSatelite = (Button)findViewById(R.id.BtnSatelite);
        btnCentrar = (Button)findViewById(R.id.BtnCentrar);
        btnAnimar = (Button)findViewById(R.id.BtnAnimar);
        inicializeMap();
        mapOverlays = mapView.getOverlays();
        
    	
        itemizedoverlay = new MapItemizedOverlaySelect();     
        mapOverlays.add(itemizedoverlay);    
        VerificarPuntos(list);
        itemizedoverlay.setOnSelectPOIListener(new OnSelectPOIListener() {   
        	public void onSelectPOI(int latitud, int longitud) {
        		list.add(new GeoPoint((int)(latitud), (int)(longitud)));
        		agregaPuntos(list, "nuevo", "nuevo punto");
        	}
        });
        	
		btnSatelite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mapView.isSatellite()){
					mapView.setSatellite(false);
				}else{
					mapView.setSatellite(true);
				}
			}
		});

		btnCentrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try{
					GeoPoint loc = new GeoPoint(myLocationOverlay.getMyLocation().getLatitudeE6(), 
							myLocationOverlay.getMyLocation().getLongitudeE6());
					
					list.add(loc);
	        		agregaPuntos(list, "nuevo", "nuevo punto");
	                mapController.animateTo(myLocationOverlay.getMyLocation());
	            }catch(Exception exception){
					
				}
		}
		});

		btnAnimar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					GeoPoint loc = new GeoPoint(myLocationOverlay
							.getMyLocation().getLatitudeE6(), myLocationOverlay
							.getMyLocation().getLongitudeE6());

					mapController.animateTo(loc);

					int zoomActual = mapView.getZoomLevel();
					for (int i = zoomActual; i < 10; i++) {
						mapController.zoomIn();
					}
				} catch (Exception exception) {

				}

			}
		});
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
    	int tamano = getCatalogoAnotacion().getAnotacion().length;
    	for (int i = 0; i < tamano; i++){
    		list.add(new GeoPoint((int)(getCatalogoAnotacion().getAnotacion()[i].getLatitud() *1E6), (int)(getCatalogoAnotacion().getAnotacion()[i].getLongitud() * 1E6)));
    		String titulo = getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion() + "=+=" 
    			+ getCatalogoAnotacion().getAnotacion()[i].getIdAnotacion() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getIdcomunidad() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getUsuario_anoto() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getTipo_anotacion() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getIcono();
    		
    		String desc = getCatalogoAnotacion().getAnotacion()[i].getDescripcion() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getFecha_registro() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getNombreUsuario() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getNombreComunidad() + "=+="
    			+ getCatalogoAnotacion().getAnotacion()[i].getImagen();
    		agregaPuntos(list, titulo, desc);
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
    
    private void agregaPuntos(List<GeoPoint> list, String titulo, String desc) {
    	
    	for (int i = 0; i < list.size(); i++){
    		//Toast.makeText(getBaseContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
    		Log.e(LOG, "latitud = " + list.get(i).getLatitudeE6());
    		Log.e(LOG, "longitud = " + list.get(i).getLongitudeE6());
    		
    		Drawable drawable = getResources().getDrawable(R.drawable.marker);
        	MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, mapView.getContext(), mapView);
    		OverlayItem overlayItem = new OverlayItem(list.get(i), titulo, desc);     
            itemizedoverlay.addOverlay(overlayItem);
            mapOverlays.add(itemizedoverlay);
    		
    	}
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
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
            	mapController.animateTo(myLocationOverlay.getMyLocation());
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
			break;
		case 1:
			//Intent intentComunidades = new Intent(MapWuil.this, Comunidades.class);
			//startActivity(intentComunidades);
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
			Intent intentChat = new Intent(MapWuil.this, GroupChat.class);
			startActivity(intentChat);
			break;
		case 5:
			Intent intentCerrar = new Intent(MapWuil.this, Principal.class);
			startActivity(intentCerrar);
			break;
        default:
            break;

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		collapseMenu();
		opcionesMenu(arg2);
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	      if (keyCode == KeyEvent.KEYCODE_BACK) {
	            // Preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR     
	            return true;
	      }
	      
	      return super.onKeyDown(keyCode, event);
    }

    
    
}
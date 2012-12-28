package alvarado.wuil;

import java.util.ArrayList;
import java.util.List;

import alvarado.wuil.MapItemizedOverlaySelect.OnSelectPOIListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.researchmobile.coretel.entity.CatalogoAnotacion;

public class MapWuil extends MapActivity {
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
	private Button btnLobby = null;
	private Button btnChat = null;
	
	final List<GeoPoint> list = new ArrayList<GeoPoint>();
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Bundle bundle = (Bundle)getIntent().getExtras();
        setCatalogoAnotacion((CatalogoAnotacion)bundle.get("anotaciones"));
        btnSatelite = (Button)findViewById(R.id.BtnSatelite);
        btnCentrar = (Button)findViewById(R.id.BtnCentrar);
        btnAnimar = (Button)findViewById(R.id.BtnAnimar);
        btnLobby = (Button)findViewById(R.id.BtnLobby);
        btnChat = (Button)findViewById(R.id.BtnChat);
        inicializeMap();
        mapOverlays = mapView.getOverlays();
        
    	
        itemizedoverlay = new MapItemizedOverlaySelect();     
        mapOverlays.add(itemizedoverlay);    
        VerificarPuntos(list);
        itemizedoverlay.setOnSelectPOIListener(new OnSelectPOIListener() {   
        	public void onSelectPOI(int latitud, int longitud) {
        		list.add(new GeoPoint((int)(latitud), (int)(longitud)));
        		agregaPuntos(list, "nuevo", "nuevo punto");
        		Toast.makeText(getBaseContext(), String.valueOf(latitud) + " : " + String.valueOf(longitud), Toast.LENGTH_SHORT).show();
        		
        	}
        });
        	
		btnSatelite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mapView.isSatellite())
					mapView.setSatellite(false);
				else
					mapView.setSatellite(true);
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
				

				
				try{
					GeoPoint loc = new GeoPoint(myLocationOverlay.getMyLocation().getLatitudeE6(), 
							myLocationOverlay.getMyLocation().getLongitudeE6());

					mapController.animateTo(loc);

					int zoomActual = mapView.getZoomLevel();
					for (int i = zoomActual; i < 10; i++) {
						mapController.zoomIn();
					}
				}catch(Exception exception){
					
				}
				
			}
		});
		
		btnLobby.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MapWuil.this, Lobby.class);
				startActivity(intent);
			}
		});
		
		btnChat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MapWuil.this, Chat.class);
				startActivity(intent);
			}
		});
		
		/*btnMover.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// mapOverlays.clear();
				mapController.scrollBy(50, 0);
			}
		});
		*/

    }
    
    private void VerificarPuntos(List<GeoPoint> list) {
    	int tamano = getCatalogoAnotacion().getAnotacion().length;
    	for (int i = 0; i < tamano; i++){
    		list.add(new GeoPoint((int)(getCatalogoAnotacion().getAnotacion()[i].getLatitud() *1E6), (int)(getCatalogoAnotacion().getAnotacion()[i].getLongitud() * 1E6)));
    		String titulo = getCatalogoAnotacion().getAnotacion()[i].getIdAnotacion() + " " + getCatalogoAnotacion().getAnotacion()[i].getNombreTipoAnotacion();
    		String desc = getCatalogoAnotacion().getAnotacion()[i].getNombreComunidad();
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
    
    
}
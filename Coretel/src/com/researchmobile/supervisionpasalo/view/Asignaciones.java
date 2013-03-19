package com.researchmobile.supervisionpasalo.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.researchmobile.coretel.supervision.entity.AnotacionAsignacion;
import com.researchmobile.coretel.supervision.entity.CatalogoAsignacion;
import com.researchmobile.coretel.view.R;

public class Asignaciones extends Activity implements OnItemClickListener, TextWatcher{

	private TextView creacionTextView;
	private TextView asignacionTextView;
	private TextView resueltoTextView;
	private TextView ComunidadTextView;
	private TextView TipoTextView;
	private ListView ListadoListView;
	private SimpleAdapter simpleAdapter;
	private CatalogoAsignacion catalogoAsignacion;
	private AnotacionAsignacion anotacionAsignacion;
	private ArrayList<HashMap<String, Object>> asignacionaesList;
	
	//Declare to menu
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
	
	private ArrayList<HashMap<String, Object>> mylist = new ArrayList<HashMap<String, Object>>();
	
		protected void onCreate(Bundle savedInstanceState)
		{
			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.listas_supervision);
			Bundle bundle = getIntent().getExtras();
			setCatalogoAsignacion((CatalogoAsignacion)bundle.get("catalogo"));
			
			setCreacionTextView((TextView)findViewById(R.id.asignaciones_creacion_textview));
			setAsignacionTextView((TextView)findViewById(R.id.asignaciones_asignacion_textview));
			setResueltoTextView((TextView)findViewById(R.id.asignaciones_resuelto_textview));
			setComunidadTextView((TextView)findViewById(R.id.asignaciones_comunidad_textview));
			setTipoTextView((TextView)findViewById(R.id.asignaciones_tipo_textview));
			setListadoListView((ListView)findViewById(R.id.asignaciones_lista));
			
			/***
	         * MENU
	         */
	        
	        String lv_items[] = { "Mapa", "Asignaciones", "Cerrar sesión" };

	      lView = (ListView) findViewById(R.id.lista);
	      // Set option as Multiple Choice. So that user can able to select more the one option from list
	      lView.setAdapter(new ArrayAdapter<String>(this,
	      android.R.layout.simple_list_item_1, lv_items));
	      lView.setOnItemClickListener(this);
	      

			//ARRAY LIST
			int i;
			int tamano = getCatalogoAsignacion().getAnotacionasignacion().length;
			for(i=0; i<tamano; i++)
				{
				
					HashMap<String, Object>map = new HashMap<String,Object>();
	                map.put("creacion",getCatalogoAsignacion().getAnotacionasignacion()[i].getFormat_fecha_asignado());		
	                map.put("asignado",getCatalogoAsignacion().getAnotacionasignacion()[i].getFormat_fecha_asignado());
	                map.put("resultado",getCatalogoAsignacion().getAnotacionasignacion()[i].getFecha_registro());
	                map.put("comunidad", getCatalogoAsignacion().getAnotacionasignacion()[i].getNombreComunidad());
	                map.put("tipo", getCatalogoAsignacion().getAnotacionasignacion()[i].getNombreTipoAnotacion());
	                int estado = getCatalogoAsignacion().getAnotacionasignacion()[i].getId_estado();
	                if (estado == 1){
	                	estado = this.getResources().getIdentifier("estado_1", "drawable", this.getPackageName());
	                }else if (estado == 2){
	                	estado = this.getResources().getIdentifier("estado_2", "drawable", this.getPackageName());
	                }else if (estado == 3){
	                	estado = this.getResources().getIdentifier("estado_3_4", "drawable", this.getPackageName());
	                }else if (estado == 4){
	                	estado = this.getResources().getIdentifier("estado_3_4", "drawable", this.getPackageName());
	                }
	                map.put("estado", estado);
	                map.put("latitud", String.valueOf(getCatalogoAsignacion().getAnotacionasignacion()[i].getLatitud()));
	                map.put("longitud", String.valueOf(getCatalogoAsignacion().getAnotacionasignacion()[i].getLongitud()));
	                mylist.add(map);
				}
			setSimpleAdapter(new SimpleAdapter(this,
					mylist,
					R.layout.asignaciones_supervision,
					new String[]{"creacion","asignado","resultado","comunidad","tipo", "estado", "latitud", "longitud"},
					new int[]{R.id.asignaciones_creacion_textview,R.id.asignaciones_asignacion_textview,R.id.asignaciones_resuelto_textview,R.id.asignaciones_comunidad_textview,R.id.asignaciones_tipo_textview, R.id.asignaciones_estado_imageview, R.id.asignaciones_latitud, R.id.asignaciones_longitud}));
							
					getListadoListView().setAdapter(getSimpleAdapter());
					getListadoListView().setOnItemClickListener(this);
					
					animationMenu();
					
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

	    private void opcionesMenu(int opcion){
			switch(opcion){
			case 0:
				float latitud = (float) 14.627853;
				float longitud = (float) -90.516584;
				Intent intentMapa = new Intent(Asignaciones.this, MapaSupervision.class);
				intentMapa.putExtra("latitud", latitud);
				intentMapa.putExtra("longitud", longitud);
				break;
			case 1:
				
				break;
			case 2:
				Intent intentCerrar = new Intent(Asignaciones.this, LoginRecibelo.class);
				startActivity(intentCerrar);
				break;
	        default:
	            break;

			}
		}
	    
	    @Override
		public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
	    	if (adapter == lView){
	    		opcionesMenu(position);
				collapseMenu();
	    	}else if (adapter == getListadoListView()){
	    		HashMap<String, Object> map = (HashMap<String, Object>) adapter.getAdapter().getItem(position);
				String latitud = (String) map.get("latitud");
				String longitud = (String) map.get("longitud");
			    Intent intent = new Intent(Asignaciones.this, MapaSupervision.class);
				intent.putExtra("latitud", latitud);
				intent.putExtra("longitud", longitud);
				startActivity(intent);
	    	}
			
		}
	    
		
		public CatalogoAsignacion getCatalogoAsignacion() {
			return catalogoAsignacion;
		}


		public void setCatalogoAsignacion(CatalogoAsignacion catalogoAsignacion) {
			this.catalogoAsignacion = catalogoAsignacion;
		}


		public AnotacionAsignacion getAnotacionAsignacion() {
			return anotacionAsignacion;
		}

		public void setAnotacionAsignacion(AnotacionAsignacion anotacionAsignacion) {
			this.anotacionAsignacion = anotacionAsignacion;
		}

		public ListView getListadoListView() {
			return ListadoListView;
		}



		public void setListadoListView(ListView listadoListView) {
			ListadoListView = listadoListView;
		}



		public SimpleAdapter getSimpleAdapter() {
			return simpleAdapter;
		}



		public void setSimpleAdapter(SimpleAdapter simpleAdapter) {
			this.simpleAdapter = simpleAdapter;
		}



		public ArrayList<HashMap<String, Object>> getMylist() {
			return mylist;
		}



		public void setMylist(ArrayList<HashMap<String, Object>> mylist) {
			this.mylist = mylist;
		}



		public TextView getComunidadTextView() {
			return ComunidadTextView;
		}

		public void setComunidadTextView(TextView comunidadTextView) {
			ComunidadTextView = comunidadTextView;
		}

		public TextView getTipoTextView() {
			return TipoTextView;
		}

		public void setTipoTextView(TextView tipoTextView) {
			TipoTextView = tipoTextView;
		}




		public TextView getCreacionTextView() {
			return creacionTextView;
		}




		public void setCreacionTextView(TextView creacionTextView) {
			this.creacionTextView = creacionTextView;
		}




		public TextView getAsignacionTextView() {
			return asignacionTextView;
		}




		public void setAsignacionTextView(TextView asignacionTextView) {
			this.asignacionTextView = asignacionTextView;
		}




		public TextView getResueltoTextView() {
			return resueltoTextView;
		}




		public void setResueltoTextView(TextView resueltoTextView) {
			this.resueltoTextView = resueltoTextView;
		}




		public LinearLayout getSlidingPanel() {
			return slidingPanel;
		}




		public void setSlidingPanel(LinearLayout slidingPanel) {
			this.slidingPanel = slidingPanel;
		}




		public boolean isExpanded() {
			return isExpanded;
		}




		public void setExpanded(boolean isExpanded) {
			this.isExpanded = isExpanded;
		}




		public DisplayMetrics getMetrics() {
			return metrics;
		}




		public void setMetrics(DisplayMetrics metrics) {
			this.metrics = metrics;
		}




		public RelativeLayout getHeaderPanel() {
			return headerPanel;
		}




		public void setHeaderPanel(RelativeLayout headerPanel) {
			this.headerPanel = headerPanel;
		}




		public RelativeLayout getMenuPanel() {
			return menuPanel;
		}




		public void setMenuPanel(RelativeLayout menuPanel) {
			this.menuPanel = menuPanel;
		}




		public int getPanelWidth() {
			return panelWidth;
		}




		public void setPanelWidth(int panelWidth) {
			this.panelWidth = panelWidth;
		}




		public ImageView getMenuViewButton() {
			return menuViewButton;
		}




		public void setMenuViewButton(ImageView menuViewButton) {
			this.menuViewButton = menuViewButton;
		}




		public ListView getlView() {
			return lView;
		}
		
		public void setlView(ListView lView) {
			this.lView = lView;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			try{
				getSimpleAdapter().getFilter().filter(s);
		        getSimpleAdapter().notifyDataSetChanged();
			}catch(Exception exception){
				Log.v("pio", "error filtrando = " + exception);
			}
			
			
		}

		public ArrayList<HashMap<String, Object>> getAsignacionaesList() {
			return asignacionaesList;
		}

		public void setAsignacionaesList(
				ArrayList<HashMap<String, Object>> asignacionaesList) {
			this.asignacionaesList = asignacionaesList;
		}

		
}

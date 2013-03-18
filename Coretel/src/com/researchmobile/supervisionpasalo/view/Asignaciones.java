package com.researchmobile.supervisionpasalo.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.researchmobile.coretel.supervision.entity.AnotacionAsignacion;
import com.researchmobile.coretel.supervision.entity.CatalogoAsignacion;
import com.researchmobile.coretel.view.R;

public class Asignaciones extends Activity {

	private TextView creacionTextView;
	private TextView asignacionTextView;
	private TextView resueltoTextView;
	private TextView ComunidadTextView;
	private TextView TipoTextView;
	private ListView ListadoListView;
	private SimpleAdapter simpleAdapter;
	private CatalogoAsignacion catalogoAsignacion;
	private AnotacionAsignacion anotacionAsignacion;
	
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
	                mylist.add(map);
				}
			setSimpleAdapter(new SimpleAdapter(this,
					mylist,
					R.layout.asignaciones_supervision,
					new String[]{"creacion","asignado","resultado","comunidad","tipo", "estado"},
					new int[]{R.id.asignaciones_creacion_textview,R.id.asignaciones_asignacion_textview,R.id.asignaciones_resuelto_textview,R.id.asignaciones_comunidad_textview,R.id.asignaciones_tipo_textview, R.id.asignaciones_estado_imageview}));
							
					getListadoListView().setAdapter(getSimpleAdapter());
					
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




		

		
}

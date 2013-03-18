package com.researchmobile.supervisionpasalo.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
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
	
	private ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
	
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
					HashMap<String, String>map = new HashMap<String,String>();
	                map.put("creacion",getCatalogoAsignacion().getAnotacionasignacion()[i].getFormat_fecha_asignado());		
	                map.put("asignado",getCatalogoAsignacion().getAnotacionasignacion()[i].getFormat_fecha_asignado());
	                map.put("resultado",getCatalogoAsignacion().getAnotacionasignacion()[i].getFecha_registro());
	                map.put("comunidad", getCatalogoAsignacion().getAnotacionasignacion()[i].getNombreComunidad());
	                map.put("tipo", getCatalogoAsignacion().getAnotacionasignacion()[i].getNombreTipoAnotacion());
	                mylist.add(map);
				}
			setSimpleAdapter(new SimpleAdapter(this,
//					datos.Data(),//Data es el nombre que la clase Datos al final del public static ArrayList
					mylist,
					R.layout.asignaciones_supervision,
					new String[]{"creacion","asignado","resultado","comunidad","tipo"},
					new int[]{R.id.asignaciones_creacion_textview,R.id.asignaciones_asignacion_textview,R.id.asignaciones_resuelto_textview,R.id.asignaciones_comunidad_textview,R.id.asignaciones_tipo_textview}));
							
					getListadoListView().setAdapter(getSimpleAdapter());
					
	         /*
			map.put("Create", "02/02/2013 10:19 a.m");
			map.put("Asignate", "05/02/2013 8:00 a.m");
			map.put("Result", "06/02/2013 8:00 a.m");
			map.put("Comunity", "RSOLARES");
			map.put("Tipe", "Tipo1");
			mylist.add(map);
			map = new HashMap<String, String>();
			map.put("Create", "02/03/2013 10:19 a.m");
			map.put("Asignate", "05/03/2013 8:00 a.m");
			map.put("Result", "06/03/2013 8:00 a.m");
			map.put("Comunity", "RSOLARES");
			map.put("Tipe", "Tipo2");
			mylist.add(map);
			map = new HashMap<String, String>();
			map.put("Create", "02/04/2013 10:19 a.m");
			map.put("Asignate", "05/04/2013 8:00 a.m");
			map.put("Result", "06/04/2013 8:00 a.m");
			map.put("Comunity", "RSOLARES");
			map.put("Tipe", "Tipo3");
			mylist.add(map);
			*/
			
			//LISTADO
					
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



		public ArrayList<HashMap<String, String>> getMylist() {
			return mylist;
		}



		public void setMylist(ArrayList<HashMap<String, String>> mylist) {
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

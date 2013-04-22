package com.researchmobile.coretel.supervision.utility;

import java.util.ArrayList;
import java.util.HashMap;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class MyAdapterAsignaciones extends BaseAdapter implements Filterable{
	protected Activity activity;
	protected ArrayList<HashMap<String, String>> data;
	protected ArrayList<HashMap<String, String>> fitems;
	//protected Drawable estadoDrawable;
	private Filter filter;
	private TokenizerUtilitySupervision tokenizer = new TokenizerUtilitySupervision();
	
	
	
	public MyAdapterAsignaciones(Activity activity, ArrayList<HashMap<String, String>> data) {
		this.activity = activity;
		this.data = data;
	}
	
	public String toString(){
		return "nada";
	}
	
	@Override
	public int getCount() {
		return data.size();
	}
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	@Override
	public long getItemId(int position) {
		long id = position;
		return id;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, String> item;
		item = data.get(position);
		
		View vi=convertView;
		if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.asignaciones_supervision, null);
        }
		ImageView icono = (ImageView) vi.findViewById(R.id.icono_asignacion);
		TextView creacion = (TextView) vi.findViewById(R.id.asignaciones_creacion_textview);
		TextView asignacion = (TextView) vi.findViewById(R.id.asignaciones_asignacion_textview);
		TextView resuelto = (TextView) vi.findViewById(R.id.asignaciones_resuelto_textview);
		TextView comunidad = (TextView) vi.findViewById(R.id.asignaciones_comunidad_textview);
		TextView tipo = (TextView) vi.findViewById(R.id.asignaciones_tipo_textview);
		TextView estadoTV = (TextView) vi.findViewById(R.id.estado_asignacion);
		TextView descripcion = (TextView) vi.findViewById(R.id.descripcion_asignacion);
		LinearLayout estadoLayout = (LinearLayout) vi.findViewById(R.id.asignaciones_layout_estado);
		
		String icono2 =(String) item.get("icono");
		
		String estado = (String)item.get("estado");
		Log.v("pio", "asignaciones - estado = " + estado);
		if (estado.equalsIgnoreCase("0") || estado.equalsIgnoreCase("0")){
			//estadoDrawable = activity.getResources().getDrawable(R.drawable.estado_azul_0_1);
			estadoLayout.setBackgroundColor(Color.parseColor("#3f93ff"));
			estadoTV.setText("Pendiente");
		}else if(estado.equalsIgnoreCase("0")|| estado.equalsIgnoreCase("1")){
			estadoLayout.setBackgroundColor(Color.parseColor("#ffd62d"));
			estadoTV.setText("Pendiente");
		}
		else if (estado.equalsIgnoreCase("2")){
			//estadoDrawable = activity.getResources().getDrawable(R.drawable.estado_verde_2);
			estadoLayout.setBackgroundColor(Color.parseColor("#63c246"));
			estadoTV.setText("Coregido");
		}else if (estado.equalsIgnoreCase("3")){
			//estadoDrawable = activity.getResources().getDrawable(R.drawable.estado_rojo_3);
			estadoLayout.setBackgroundColor(Color.RED);
			estadoTV.setText("Con Problema");
		}else if (estado.equalsIgnoreCase("4")){
			//estadoDrawable = activity.getResources().getDrawable(R.drawable.estado_naranja_4_5);
			estadoLayout.setBackgroundColor(Color.parseColor("#ffa32f"));
			estadoTV.setText("Reasignado");
		}else if (estado.equalsIgnoreCase("5")){
			//estadoDrawable = activity.getResources().getDrawable(R.drawable.estado_naranja_4_5);
			estadoLayout.setBackgroundColor(Color.parseColor("#ffa32f"));
			estadoTV.setText("Reasignado");
		}
		
		//estadoImagen.setImageDrawable(estadoDrawabl
		//icono.setImageDrawable(tokenizer(activity, icono2));
		icono.setImageDrawable(tokenizer.buscaIcono(activity, icono2));
		descripcion.setText((String)item.get("descripcion"));
		creacion.setText((String)item.get("creacion"));
		asignacion.setText((String)item.get("asignado"));
		resuelto.setText((String)item.get("resultado"));
		comunidad.setText((String)item.get("comunidad"));
		tipo.setText((String)item.get("tipo"));
		
		return vi;
	}

	@Override
	public Filter getFilter() {
		if (filter == null)
	        filter = new PkmnNameFilter();

	    return filter;
	}
	
	private class PkmnNameFilter extends Filter
	{
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint)
	        {   
	            FilterResults results = new FilterResults();
	            String prefix = constraint.toString().toLowerCase();

	            if (prefix == null || prefix.length() == 0)
	            {
	                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(data);
	                results.values = list;
	                results.count = list.size();
	            }
	            else
	            {
	                final ArrayList<HashMap<String, String>> list = data;

	                int count = list.size();
	                final ArrayList<HashMap<String, String>> nlist = new ArrayList<HashMap<String, String>>(count);

	                for (int i=0; i<count; i++)
	                {
	                    final HashMap<String, String> pkmn = list.get(i);
	                    final String value = pkmn.get("asignacion").toLowerCase();

	                    if (value.startsWith(prefix))
	                    {
	                        nlist.add(pkmn);
	                    }
	                }
	                results.values = nlist;
	                results.count = nlist.size();
	            }
	            return results;
	        }

	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            fitems = (ArrayList<HashMap<String, String>>)results.values;
	            fitems.clear();
	            int count = fitems.size();
	            for (int i=0; i<count; i++)
	            {
	            	HashMap<String, String> pkmn = (HashMap<String, String>)fitems.get(i);
	                fitems.add(pkmn);
	            }

	            if (fitems.size() > 0)
	                notifyDataSetChanged();
	            else
	                notifyDataSetInvalidated();
	        }

	    }

}

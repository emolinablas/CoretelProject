package com.researchmobile.coretel.utility;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.view.Comunidad;
import com.researchmobile.coretel.view.R;

public class MyAdapterComunidad extends BaseAdapter {
	protected Activity activity;
	protected DetalleComunidad[] data;
	
	public MyAdapterComunidad(Activity activity, DetalleComunidad[] data) {
		this.activity = activity;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		
		return data.length;
	}
	@Override
	public Object getItem(int position) {
		
		return data[position];
	}
	@Override
	public long getItemId(int position) {
		long id = position;
		return id;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DetalleComunidad item;
		item = data[position];
		View vi=convertView;
		if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.lista_comunidades, null);
        }
		
		TextView tipo = (TextView) vi.findViewById(R.id.item_comunidad_tipo);
		TextView nombre = (TextView) vi.findViewById(R.id.item_comunidad_nombre);
		TextView descripcion = (TextView) vi.findViewById(R.id.item_comunidad_descripcion);
		
		nombre.setText(item.getNombre());
		descripcion.setText(item.getDescripcion());
		tipo.setText(tipoComunidad(item.getEspublica()));
		
		return vi;
	}
	
	private String tipoComunidad(String tipo){
		if (tipo.equalsIgnoreCase("1")){
			return "Publica";
		}else{
			return "Privada";
		}
	}

}

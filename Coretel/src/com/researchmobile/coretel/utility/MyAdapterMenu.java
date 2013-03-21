package com.researchmobile.coretel.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class MyAdapterMenu extends BaseAdapter {
	protected Activity activity;
	protected String[] data;
	
	public MyAdapterMenu(Activity activity, String[] data) {
		this.activity = activity;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data[position];
	}

	@Override
	public long getItemId(int position) {
		long id = position;
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String item;
		item = data[position];
		View vi=convertView;
		if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.list_menu, null);
        }
		
		TextView opcion = (TextView) vi.findViewById(R.id.list_menu_opcion);
		opcion.setText(item);
		ImageView icono = (ImageView) vi.findViewById(R.id.list_menu_icono);
		if (getItemId(position) == 0){
			icono.setImageResource(R.drawable.map_icon);
		}else if (getItemId(position) == 1){
			icono.setImageResource(R.drawable.comunidades_icon);
			icono.setMaxHeight(5);
		}else if (getItemId(position) == 2){
			icono.setImageResource(R.drawable.invitaciones_icon);
		}else if (getItemId(position) == 3){
			icono.setImageResource(R.drawable.perfil_icon);
		}else if (getItemId(position) == 4){
			icono.setImageResource(R.drawable.chat_icon);
		}else if (getItemId(position) == 5){
			icono.setImageResource(R.drawable.salir_icon);
		}
		
		return vi;
	}

}

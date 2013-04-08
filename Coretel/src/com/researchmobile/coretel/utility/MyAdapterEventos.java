package com.researchmobile.coretel.utility;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.entity.Anotacion;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.view.R;

public class MyAdapterEventos  extends BaseAdapter {
	protected Activity activity;
	protected Anotacion[] data;
	protected TokenizerUtility tokenizer = new TokenizerUtility();
	
	public MyAdapterEventos(Activity activity, Anotacion[] data) {
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
		Anotacion item;
		item = data[position];
		View vi=convertView;
		if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.lista_evento, null);
        }
		
		TextView nombre = (TextView) vi.findViewById(R.id.item_evento_nombre);
		TextView descripcion = (TextView) vi.findViewById(R.id.item_evento_descripcion);
		ImageView icono = (ImageView) vi.findViewById(R.id.item_evento_icono_imageview);
		icono.setImageDrawable(tokenizer.buscaIcono(activity, item.getIcono()));
		nombre.setText(item.getNombreTipoAnotacion());
		descripcion.setText(item.getDescripcion());
		
		return vi;
	}

}

package com.researchmobile.coretel.supervision.utility;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class AdapterListaFotos extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<HashMap<String, Object>> arrayList;
	
	public AdapterListaFotos(Activity activity, ArrayList<HashMap<String, Object>> arrayList) {
		this.activity = activity;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		long id = position;
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		HashMap<String, Object> item;
		item = arrayList.get(position);
		View vi = convertView;
		if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.item_fotos_respuesta, null);
        }
		
		TextView asignacion = (TextView) vi.findViewById(R.id.item_fotos_asignacion);
		TextView path = (TextView) vi.findViewById(R.id.item_fotos_path);
		asignacion.setText((String)item.get("asignacion"));
		path.setText((String)item.get("path"));
		
		ImageView imagen = (ImageView) vi.findViewById(R.id.item_fotos_imagen);
		imagen.setImageBitmap((Bitmap)item.get("imagen"));
		
		return vi;
	}

}

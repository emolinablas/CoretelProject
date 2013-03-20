package com.researchmobile.coretel.supervision.utility;

import java.util.ArrayList;
import java.util.HashMap;

import com.researchmobile.coretel.view.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterInvitaciones extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<HashMap<String, Object>> data;
	
	public AdapterInvitaciones(Activity activity, ArrayList<HashMap<String, Object>> data) {
		this.activity = activity;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		long id = (Long) data.get(position).get("id");
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> item;
		item = data.get(position);
		View vi=convertView;
		if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.lista_invitacion, null);
        }
		
		TextView titulo = (TextView) vi.findViewById(R.id.invitaciones_encabezado_textview);
		TextView link = (TextView) vi.findViewById(R.id.invitaciones_encabezado_textview);
		titulo.setTextColor(Color.BLACK);
		link.setTextColor(Color.DKGRAY);
		titulo.setText((String)item.get("T"));
		link.setText((String)item.get("L"));
		
		return vi;
	}

}






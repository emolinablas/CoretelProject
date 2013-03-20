package com.researchmobile.coretel.supervision.utility;

import java.util.ArrayList;
import java.util.HashMap;

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
		ImageView imagen = (ImageView) vi.findViewById(R.id.flecha);
		TextView encabezado = (TextView) vi.findViewById(R.id.invitaciones_encabezado_textview);
		TextView comunidad = (TextView) vi.findViewById(R.id.invitaciones_comunidad_textView);
		TextView estado = (TextView) vi.findViewById(R.id.invitaciones_estado_textView);
		
		encabezado.setTextColor(Color.BLACK);
		comunidad.setTextColor(Color.DKGRAY);
		estado.setTextColor(Color.DKGRAY);
		
		int tipo = (Integer) item.get("tipo");
		if (tipo == 2 || tipo == 4){
			imagen.setVisibility(View.GONE);
		}
		
		String enc = (String)item.get("encabezado");
		if (enc.equalsIgnoreCase("null")){
			encabezado.setText((String)item.get("email"));
		}else{
			encabezado.setText((String)item.get("encabezado"));
		}
		comunidad.setText((String)item.get("comunidad"));
		String est = (String)item.get("estado");
		if (est.equalsIgnoreCase("1")){
			estado.setText("Aceptada");
			vi.setBackgroundColor(Color.parseColor("#CCFFCC"));
		}else if (est.equalsIgnoreCase("2")){
			vi.setBackgroundColor(Color.parseColor("#FFCCCC"));
			estado.setText("Rechazado");
		}else {
			vi.setBackgroundColor(Color.parseColor("#99FFFF"));
			estado.setText("Pendiente");
		}
		
		
		return vi;
	}

}






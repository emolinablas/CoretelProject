package com.researchmobile.coretel.view;

import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.RMFile;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PruebaListaFoto extends Activity {
	private static RMFile rmFile = new RMFile();
	private static CatalogoMiembro catalogoMiembro;
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	 		setContentView(R.layout.miembros);
	 		Bundle bundle = getIntent().getExtras();
	 		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
	 		ListView l = (ListView) findViewById(R.id.miembros_lista_listview);
	 		l.setAdapter(new miAdapter(this));
	    }
	 
	    private static class miAdapter extends BaseAdapter {
	 		private LayoutInflater mInflater;
	 		static int tamano = getCatalogoMiembro().getMiembro().length;
//	 		private static final String [][] m = new String[][tamano];
	 		public miAdapter(Context context) {
	 			mInflater = LayoutInflater.from(context);
	 		}
	 
			public View getView(int position, View convertView, ViewGroup parent) {
				
				rmFile.downloadImage(getCatalogoMiembro().getMiembro()[position].getAvatar());
				
	 			TextView text;
	 			TextView text2;
	 			ImageView img1;
	 
				if (convertView == null) {
	 				convertView = mInflater.inflate(R.layout.lista_miembros, null);
	 			} 
	 
				text = (TextView) convertView.findViewById(R.id.lista_miembros_id);
	 			text2 = (TextView) convertView.findViewById(R.id.lista_miembros_nombre);
	 			img1 = (ImageView) convertView.findViewById(R.id.lista_miembros_avatar);
	 			text.setText(getCatalogoMiembro().getMiembro()[position].getId());
	 			text2.setText(getCatalogoMiembro().getMiembro()[position].getNombreUsuario());
	 			Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + getCatalogoMiembro().getMiembro()[position].getAvatar());
				img1.setImageBitmap(image);
				return convertView;
			}
	 
			public int getCount() {
				return getCatalogoMiembro().getMiembro().length;
			}
	 
			public Object getItem(int position) {
				return position;
			}
	 
			public long getItemId(int position) {
				return position;
			}
		}

		public static CatalogoMiembro getCatalogoMiembro() {
			return catalogoMiembro;
		}

		public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
			this.catalogoMiembro = catalogoMiembro;
		}
	    
	}

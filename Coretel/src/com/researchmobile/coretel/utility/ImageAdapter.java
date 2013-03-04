package com.researchmobile.coretel.utility;

import alvarado.wuil.R;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private int seleccion;

    public ImageAdapter(Context c) {
        mContext = c;
    }
    
    public ImageAdapter(Context c, int select) {
        mContext = c;
        seleccion = select;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }
    
    public String nombre(int position){
    	return mMarkers[position];
    }

    public long getItemId(int position) {
        return mThumbIds[position];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            if (seleccion == position){
            	imageView.setBackgroundColor(Color.CYAN);
            }
            
        } else {
            imageView = (ImageView) convertView;
            
        }
        
//      BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inSampleSize = 0;
//		Bitmap bm = BitmapFactory.decodeFile("sdcard/" + mMarkers[position], options);
//      imageView.setImageBitmap(bm);
        
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    
    // references to our images
    private String[] mMarkers = {
    		"img/markers/airshow-2.png",
    		"img/markers/anniversary.png",
    		"img/markers/anthropo.png",
    		"img/markers/aquarium.png",
    		"img/markers/art-museum-2.png",
    		"img/markers/billiard-2.png",
    		"img/markers/bowling.png",
    		"img/markers/bullfight.png",
    		"img/markers/bustour.png",
    		"img/markers/casino-2.png",
    		"img/markers/cinema.png",
    		"img/markers/circus.png",
    		"img/markers/coins.png",
    		"img/markers/comedyclub.png",
    		"img/markers/dance_class.png",
    		"img/markers/dancinghall.png",
    		"img/markers/dinopark.png",
    		"img/markers/ferriswheel.png",
    		"img/markers/festival.png",
    		"img/markers/fireworks.png",
    		"img/markers/fishing.png",
    		"img/markers/geocaching-3.png",
    		"img/markers/historical_museum.png",
    		"img/markers/hookah_final.png",
    		"img/markers/jazzclub.png.png",
    };
    private Integer[] mThumbIds = {
            R.drawable.icono0, 
            R.drawable.icono1,
            R.drawable.icono2, 
            R.drawable.icono3,
            R.drawable.icono4, 
            R.drawable.icono5,
            R.drawable.icono6,
            R.drawable.icono7, 
            R.drawable.icono8, 
            R.drawable.icono9,
            R.drawable.icono10, 
            R.drawable.icono11,
            R.drawable.icono12, 
            R.drawable.icono13,
            R.drawable.icono14, 
            R.drawable.icono15,
            R.drawable.icono16, 
            R.drawable.icono17,
            R.drawable.icono18, 
            R.drawable.icono19,
            R.drawable.icono20, 
            R.drawable.icono21,
            R.drawable.icono22,
            R.drawable.icono23,
            R.drawable.icono24,
    };

}

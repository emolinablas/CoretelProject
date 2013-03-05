package com.researchmobile.coretel.utility;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.researchmobile.coretel.view.R;

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
    		"aboriginal.png",
    		"anniversary.png",
    		"bustour.png",
    		"caraccident.png",
    		"clock.png",
    		"crimescene.png",
    		"cruiseship.png",
    		"dogs_leash.png",
    		"fire.png",
    		"flag-export.png",
    		"information.png",
    		"linedown.png",
    		"palm-tree-export.png",
    		"party-2.png",
    		"pirates.png",
    		"planecrash.png",
    		"radiation.png",
    		"regroup.png",
    		"rescue-2.png",
    		"revolt.png",
    		"shooting.png",
    		"star-3.png",
    		"tornado-2.png",
    		"walkingtour.png",
    		"world.png",
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

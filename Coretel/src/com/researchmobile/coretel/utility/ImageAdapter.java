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

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.a, R.drawable.b,
            R.drawable.c, R.drawable.d,
            R.drawable.f, R.drawable.g,
            R.drawable.h, R.drawable.i,
            R.drawable.j, R.drawable.k,
            R.drawable.l, R.drawable.m,
            R.drawable.n, R.drawable.o,
            R.drawable.a, R.drawable.b,
            R.drawable.c, R.drawable.d,
            R.drawable.f, R.drawable.g,
            R.drawable.h, R.drawable.i,
            R.drawable.j, R.drawable.k,
            R.drawable.l
    };

}

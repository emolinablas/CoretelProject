package com.researchmobile.coretel.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.researchmobile.coretel.entity.ItemChat;
import com.researchmobile.coretel.entity.User;

public class ItemChatAdapter extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<ItemChat> items;
	protected Drawable drawablePropio;
	protected Drawable drawableOtro;
	
	
	public ItemChatAdapter(Activity activity, ArrayList<ItemChat> items) {
		this.activity = activity;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		View vi=convertView;
		drawablePropio = activity.getResources().getDrawable(R.drawable.bubble_mime_green);
		drawableOtro = activity.getResources().getDrawable(R.drawable.bubble_mime_gray);
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.item_chat, null);
        }
        
        ImageView image = (ImageView) vi.findViewById(R.id.imageView1);
        LinearLayout layoutItem = (LinearLayout) vi.findViewById(R.id.item_chat_linearlayout);
        
        ItemChat item = items.get(position);
        
        int imageRight = RelativeLayout.ALIGN_PARENT_RIGHT;
        int imageLeft = RelativeLayout.ALIGN_PARENT_LEFT;
        int layoutRight = RelativeLayout.RIGHT_OF;
        int layoutLeft = RelativeLayout.LEFT_OF;
        
        RelativeLayout.LayoutParams paramsImage = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams paramsLayout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        if (User.getUsername().equalsIgnoreCase(item.getNombre())){
        	paramsImage.addRule(imageRight);
            paramsLayout.addRule(layoutLeft, image.getId());
            layoutItem.setBackgroundDrawable(drawablePropio);
        }else{
        	paramsImage.addRule(imageLeft);
            paramsLayout.addRule(layoutRight, image.getId());
            layoutItem.setBackgroundDrawable(drawableOtro);
        }
                    
        image.setLayoutParams(paramsImage);
        layoutItem.setLayoutParams(paramsLayout);
        
        TextView mensaje = (TextView) vi.findViewById(R.id.item_chat_mensaje_textview);
        mensaje.setText(item.getNombre() + ": " + item.getMensaje());
        
        /*
        TextView nombre = (TextView) vi.findViewById(R.id.item_chat_nombre);
        
        nombre.setText(item.getNombre());
        
        Color mcolor = new Color();
        
        LinearLayout layout = (LinearLayout) vi.findViewById(R.id.item_chat_layout);
        if (item.getMensaje().equalsIgnoreCase("1")){
        	nombre.setTextColor(Color.BLACK);
        }else if (item.getMensaje().equalsIgnoreCase("2")){
        	nombre.setTextColor(Color.BLUE);
        }else if (item.getMensaje().equalsIgnoreCase("3")){
        	nombre.setTextColor(Color.CYAN);
        }else if (item.getMensaje().equalsIgnoreCase("4")){
        	nombre.setTextColor(Color.DKGRAY);
        }else if (item.getMensaje().equalsIgnoreCase("5")){
        	nombre.setTextColor(Color.GRAY);
        }else if (item.getMensaje().equalsIgnoreCase("6")){
        	nombre.setTextColor(Color.rgb(179, 199, 223));
        }else if (item.getMensaje().equalsIgnoreCase("7")){
        	nombre.setTextColor(Color.MAGENTA);
        }else if (item.getMensaje().equalsIgnoreCase("8")){
        	nombre.setTextColor(Color.RED);
        }else if (item.getMensaje().equalsIgnoreCase("9")){
        	nombre.setTextColor(Color.TRANSPARENT);
        }
        */
        
        return vi;
	}
}

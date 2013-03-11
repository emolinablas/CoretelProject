package com.researchmobile.coretel.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
		Bitmap miAvatar = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
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
        
        RelativeLayout.LayoutParams paramsImage = new RelativeLayout.LayoutParams(70, 70);
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
        image.setImageBitmap(miAvatar);
        layoutItem.setLayoutParams(paramsLayout);
        
        TextView mensaje = (TextView) vi.findViewById(R.id.item_chat_mensaje_textview);
        mensaje.setText(item.getNombre() + ": " + item.getMensaje());
        
        return vi;
	}
}

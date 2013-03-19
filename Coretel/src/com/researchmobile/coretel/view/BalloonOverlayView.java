package com.researchmobile.coretel.view;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.OverlayItem;
import com.researchmobile.coretel.utility.TokenizerUtility;

public class BalloonOverlayView<Item extends OverlayItem> extends FrameLayout {

	 protected LinearLayout layout;
	 protected TextView title;
	 protected TextView snippet;
	 protected LayoutInflater layoutinflater;
	 protected View balloonview;
	 protected ImageView imgclose;
	 protected ImageView editar;
	 protected Item miItem;
	 private TokenizerUtility tokenizer = new TokenizerUtility();

	 protected int getIdView(){return R.layout.window_balloon_overlay;}
	 
	 public BalloonOverlayView(final Context context, int balloonBottomOffset) {
	  super(context);
	  
	  setPadding(10, 0, 10, balloonBottomOffset);
	  layout = new LinearLayout(context);
	  layout.setVisibility(VISIBLE);
	  layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  balloonview = layoutinflater.inflate(getIdView(), layout);
	  title = (TextView) balloonview.findViewById(R.id.balloon_item_title);
	  snippet = (TextView) balloonview.findViewById(R.id.balloon_item_snippet);
	  layout.setVisibility(GONE);
	  
	  setOnClickListener(new OnClickListener() {
	   public void onClick(View v) {layout.setVisibility(GONE);}
	  });
	  
	  title.setVisibility(GONE);
	  snippet.setVisibility(GONE);

	  editar = (ImageView) balloonview.findViewById(R.id.edit_img);
	  
	  editar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try{
					{
						layout.setVisibility(GONE);
					}
					
					Intent intent = new Intent(context, Evento.class);
					intent.putExtra("latitud", String.valueOf(getMiItem().getPoint().getLatitudeE6()));
					intent.putExtra("longitud",String.valueOf(getMiItem().getPoint().getLongitudeE6()));
					intent.putExtra("titulo", getMiItem().getTitle());
					intent.putExtra("descripcion", getMiItem().getSnippet());
					context.startActivity(intent);
				}catch(Exception exception){
					
				}
			}
		});
	  FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	  params.gravity = Gravity.NO_GRAVITY;
	  addView(layout, params);   
	 }
	 
	 public void setData(Item item, final Context mContext) {
	   
	  setMiItem(item);
	  System.out.println("EN EL ITEM");
	  System.out.println(getMiItem().getSnippet());
	  System.out.println(getMiItem().getTitle());
	  System.out.println(getMiItem().getPoint().getLatitudeE6()+"");
	  System.out.println(getMiItem().getPoint().getLongitudeE6()+"");
	  layout.setVisibility(VISIBLE);
	  if (getMiItem().getTitle().equalsIgnoreCase("nuevo")){
		  title.setVisibility(VISIBLE);
		  title.setText(tokenizer.titulo(item.getTitle()));
		  snippet.setVisibility(VISIBLE);
		  snippet.setText(tokenizer.descripcion(item.getSnippet()));
	  }else{
		  if (item.getTitle() != null && item.getTitle().length() > 0) {
			   title.setVisibility(VISIBLE);
			   title.setText(tokenizer.titulo(item.getTitle()));
			  } else {
			   title.setVisibility(GONE);
			  }
			  if (item.getSnippet() != null && item.getSnippet().length() > 0) {
			   snippet.setVisibility(VISIBLE);
			   snippet.setText(tokenizer.descripcion(item.getSnippet()));
			  } else {
			   snippet.setVisibility(GONE);
			   
			  }
	  }
	  
	  
	  
	 }

	public Item getMiItem() {
		return miItem;
	}

	public void setMiItem(Item miItem) {
		this.miItem = miItem;
	}
	 
	 

	}

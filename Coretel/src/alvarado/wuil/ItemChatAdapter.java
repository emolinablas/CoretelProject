package alvarado.wuil;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.entity.ItemChat;

public class ItemChatAdapter extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<ItemChat> items;
	
	
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
		
        if(convertView == null) {
        	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	vi = inflater.inflate(R.layout.list_item_chat_layout, null);
        }
            
        ItemChat item = items.get(position);
        
        TextView mensaje = (TextView) vi.findViewById(R.id.item_chat_mensaje);
        mensaje.setText(item.getMensaje());
        
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
        
        
        return vi;
	}
}

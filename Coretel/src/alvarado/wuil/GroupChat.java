package alvarado.wuil;

import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.codebutler.android_websockets.SocketIOClient;
import com.researchmobile.coretel.entity.ItemChat;
import com.researchmobile.coretel.entity.User;

public class GroupChat extends Activity implements OnClickListener, OnItemClickListener{
	
	private Button mandar;
	private long idChat = 0;
	private EditText mensajeEditText;
	protected static final String TAG = null;
	
	//Declare
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	private ListView lView;
	private SocketIOClient client;
	private String[] listaUsuarios;
	
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
	
	ItemChatAdapter adapter;
	private ListView listChat;
	ArrayList<ItemChat> itemsCompra;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_chat);
		
		setMandar((Button)findViewById(R.id.chat_enviar_button));
		setMensajeEditText((EditText)findViewById(R.id.chat_agregar_edittext));
		setListChat((ListView)findViewById(R.id.chat_conversacion_listview));
		setItemsCompra(new ArrayList<ItemChat>());
		setAdapter(new ItemChatAdapter(GroupChat.this, getItemsCompra()));
		getListChat().setAdapter(getAdapter());
		getMandar().setOnClickListener(this);
		
		menuDesplegable();
		
		setClient(new SocketIOClient(URI.create("http://23.23.1.2:8080"), new SocketIOClient.Handler() {
		    @Override
		    public void onConnect() {
		        Log.d(TAG, "Connected!");
		       // wuilla = 1;
		        
		       // entraralchat();
		    }

		    @Override
		    public void onDisconnect(int code, String reason) {
		        Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
		    }

		    @Override
		    public void onError(Exception error) {
		        Log.e(TAG, "Error!", error);
		    }

			@Override
			public void on(String event, JSONArray arguments) {
				// TODO Auto-generated method stub
				 Log.d(TAG, String.format("Got event %s: %s", event, arguments.toString()));
				 mensajeCapturado(event, arguments);
				 System.out.println("se tuvo un eveto");
				 
			}
		}));
		conectar();

		getClient().connect();
				
	}
	private void conectar(){
		JSONArray arguments = new JSONArray();
		JSONArray arguments2 = new JSONArray();
		try{
		
		arguments2.put("Wuilder Alvarado");
		arguments2.put("21");
		arguments2.put("1");
	    arguments.put("21");
	    
		getClient().emit("conectarComunidad", arguments2);
		}catch(Exception exception){
			
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	private void mensajeCapturado(String event, JSONArray arguments){
		if (event.equalsIgnoreCase("usuarios_online")){
			//event usuarios_online: [[{"id":61,"usuario":"Regina Goubaud","com":1},{"id":22,"usuario":"Kevin Herrarte","com":1},{"id":21,"usuario":"Wuilder Alvarado","com":1}]]
			try {
				JSONArray usuarios = arguments.getJSONArray(0);
				int tamano = usuarios.length();
				String[] lista = new String[tamano];
				for (int i = 0; i < tamano; i++){
					JSONObject usuario = usuarios.getJSONObject(i);
					lista[i] = usuario.getString("usuario");
					Log.e("CHAT", String.valueOf(i) + " " + lista[i]);
				}
				setListaUsuarios(lista);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (event.equalsIgnoreCase("saludador")){
			Log.e("CHAT", "inicia Saludador");
			//{"name":"saludador","args":["Hola nuevo usuario :)"]}
			try{
				String mensaje = arguments.toString();
				//insertaMensaje(mensaje, User.getUsername());
			}catch(Exception exception){
				
			}
		}
		
		
		
	}
	
	@Override
	public void onClick(View view) {
		if (view == getMandar()){
			String mensaje = getMensajeEditText().getText().toString();
			getMensajeEditText().setText("");
			insertaMensaje(User.getUsername(), mensaje);
			JSONArray arguments2 = new JSONArray();
			try{
			
			arguments2.put(mensaje);
			arguments2.put(User.getUsername());
			arguments2.put("xxx");
		    
			getClient().emit("ingresoMensaje", arguments2);
			llenaListaUsuarios(getListaUsuarios());
			}
			catch(Exception e){
			}
		}
		
	}
	
	private void insertaMensaje(String usuario, String mensaje){
		Log.e("CHAT", "insertando mensaje");
		ItemChat miItem = new ItemChat(idChat++, usuario, mensaje);
		Log.e("CHAT", miItem.getId() + " " + miItem.getNombre() + " " + miItem.getMensaje());
        getItemsCompra().add(miItem);
        setAdapter(new ItemChatAdapter(GroupChat.this, getItemsCompra()));
        getListChat().setAdapter(getAdapter());
	}
	private void llenaListaUsuarios(String[] lista){
		Log.e("CHAT", "llegaLista");
		lView = (ListView) findViewById(R.id.list);
		lView.setAdapter(new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, getListaUsuarios()));
	}
	
	private void menuDesplegable(){
		//Initialize
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels)*0.75);
	
		headerPanel = (RelativeLayout) findViewById(R.id.header);
		headerPanelParameters = (LinearLayout.LayoutParams) headerPanel.getLayoutParams();
		headerPanelParameters.width = metrics.widthPixels;
		headerPanel.setLayoutParams(headerPanelParameters);
		
		menuPanel = (RelativeLayout) findViewById(R.id.menuPanel);
		menuPanelParameters = (FrameLayout.LayoutParams) menuPanel.getLayoutParams();
		menuPanelParameters.width = panelWidth;
		menuPanel.setLayoutParams(menuPanelParameters);
		
		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);
		
		//Slide the Panel	
		menuViewButton = (ImageView) findViewById(R.id.menuViewButton);
		menuViewButton.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	if(!isExpanded){
		    		isExpanded = true;   		    				        		
		        	
		    		//Expand
		    		new ExpandAnimation(slidingPanel, panelWidth,
		    	    Animation.RELATIVE_TO_SELF, 0.0f,
		    	    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);		    			         	    
		    	}else{
		    		isExpanded = false;
		    		
		    		//Collapse
		    		new CollapseAnimation(slidingPanel,panelWidth,
            	    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
            	    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
		   
					
		    	}         	   
		    }
		});
	}
	public Button getMandar() {
		return mandar;
	}
	public void setMandar(Button mandar) {
		this.mandar = mandar;
	}

	public SocketIOClient getClient() {
		return client;
	}

	public void setClient(SocketIOClient client) {
		this.client = client;
	}

	public String[] getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(String[] listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ListView getListChat() {
		return listChat;
	}

	public void setListChat(ListView listChat) {
		this.listChat = listChat;
	}

	public ItemChatAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ItemChatAdapter adapter) {
		this.adapter = adapter;
	}

	public ArrayList<ItemChat> getItemsCompra() {
		return itemsCompra;
	}

	public void setItemsCompra(ArrayList<ItemChat> itemsCompra) {
		this.itemsCompra = itemsCompra;
	}

	public EditText getMensajeEditText() {
		return mensajeEditText;
	}

	public void setMensajeEditText(EditText mensajeEditText) {
		this.mensajeEditText = mensajeEditText;
	}
	
	

}
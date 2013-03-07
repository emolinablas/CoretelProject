package com.researchmobile.coretel.view;

import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
	private String comunidad;
	
//	Frames y Layout para el menu deslisante
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
	
//	Componentes para el uso del list que muestra la conversaci€n del chact
	ItemChatAdapter adapter;
	private ListView listChat;
	ArrayList<ItemChat> itemsCompra;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("ENTRANDO AL CHAT");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.group_chat);
		
		Bundle bundle = getIntent().getExtras();
		setComunidad(bundle.getString("comunidad"));
		//eliminar
//		setComunidad("34");
		
		setMandar((Button)findViewById(R.id.chat_enviar_button));
		setMensajeEditText((EditText)findViewById(R.id.chat_agregar_edittext));
		setListChat((ListView)findViewById(R.id.chat_conversacion_listview));
		setItemsCompra(new ArrayList<ItemChat>());
		setAdapter(new ItemChatAdapter(GroupChat.this, getItemsCompra()));
		getListChat().setAdapter(getAdapter());
		getMandar().setOnClickListener(this);
		
		menuDesplegable();
		
//		Inicio SocketIo
		setClient(new SocketIOClient(URI.create("http://23.23.1.2:8080"), new SocketIOClient.Handler() {
		    @Override
		    public void onConnect() {
		        Log.v("CHAT", "EL SOCKET HA CONECTADO!");

				// HASTA ESTE MOMENTO PODES COMENZAR A ENVIARLE COSAS AL SOCKET PORQUE YA ESTA CONECTADO,SE LO ENVIVAS SEGUIDO
		        //EN TAREAS PARALELAS Y NO SE PUEDE ENTONCES COMPLETABA EL CONNECT PERO NO SEGUIA
		        
		        conectar();
		        
		       
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

		getClient().connect();
		

				
	}
	private void conectar(){
		JSONArray arguments = new JSONArray();
		JSONArray arguments2 = new JSONArray();
		try{
		
		arguments2.put(User.getUsername());
		arguments2.put(User.getUserId());
		arguments2.put(getComunidad());
	    arguments.put(User.getUserId());
	    
//			arguments2.put("luis");
//			arguments2.put(1);
//			arguments2.put(34);
	    
		getClient().emit("conectarComunidad", arguments2);
		}catch(Exception exception){
			System.out.println("EXCEPCION CAPTURADO:"+exception.getMessage());
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	private void mensajeCapturado(String event, JSONArray arguments){
//		Muestra lo que recibe del servidor
		Log.e("pio", "mensaje capturado = " + arguments.toString());
		Log.e("CHAT", "Evento capturado = " + event);

		if (event.equalsIgnoreCase("usuarios_online")){
			try {
				JSONArray usuarios = arguments.getJSONArray(0);
				int tamano = usuarios.length();
				String[] lista = new String[tamano];
				for (int i = 0; i < tamano; i++){
					JSONObject usuario = usuarios.getJSONObject(i);
					lista[i] = usuario.getString("usuario");
					Log.e("CHAT", String.valueOf(i) + " " + lista[i]);
				}
//				Agrega lo que recibe del servidor a la lista en el menu desplegable
				setListaUsuarios(lista);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (event.equalsIgnoreCase("saludador")){
			Log.e("CHAT", "inicia Saludador");
			try{
 				String mensaje = arguments.getString(0);
				insertaMensaje(User.getUsername(), mensaje);
			}catch(Exception exception){
				
			}
		}
		if (event.equalsIgnoreCase("escribir_mensaje")){
			Log.e("CHAT", "escribir_mensaje");
			try{
				String mensaje = arguments.toString();
				insertaMensaje(arguments.getString(1), arguments.getString(0).toString());
			}catch(Exception exception){
				System.out.println("ERROR:"+exception.getMessage());
			}
		}
		if (event.equalsIgnoreCase("enviar_historial")){
			
			
			try {
				JSONArray jsonArray = (JSONArray) arguments.getJSONArray(0);
				int histo = jsonArray.length();
				Log.e("TT", "historial capturado numero = " + histo);
				for (int i = 0; i < histo; i++){
					JSONObject json = (JSONObject) jsonArray.get(i);
					String nombreH = json.getString("usuario");
					String mensajeH = json.getString("mensaje");
					insertaMensaje(nombreH, mensajeH);
				}
				new chatAsync().execute("");
				Log.e("TT", "historial capturado = " + jsonArray.get(0));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void onClick(View view) {
		if (view == getMandar()){
			String mensaje = getMensajeEditText().getText().toString();
			getMensajeEditText().setText("");
//			Agrega el mensaje a la lista en la conversaci€n
//			insertaMensaje(User.getUsername(), mensaje);
			JSONArray arguments2 = new JSONArray();
			try{
			
			arguments2.put(mensaje);
			arguments2.put(User.getUsername());//no el username sino el NOMBRE del don osea LUIS GONZALEZ no LUIS
			arguments2.put("img/avatars/60765793imagen.jpg");//envais el avatar que te mando en el LOGIN
			
		    
//			EnvÃa el mensaje
			getClient().emit("ingresoMensaje", arguments2);
			llenaListaUsuarios(getListaUsuarios());
			getClient().connect();
			}
			
			catch(Exception e){
			}
		}
		
	}
	
	private void insertaMensaje(String usuario, String mensaje){
		try{
			ItemChat miItem = new ItemChat(idChat++, usuario, mensaje);
			getItemsCompra().add(miItem);
	        
		}catch(Exception exception){
			Log.e("TT", "Error agregando item");
			Log.e("TT", exception.getMessage());
		}
		
	}
	
	// Clase para ejecutar en Background
    class chatAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
         protected Integer doInBackground(String... urlString) {
                try {
                	setAdapter(new ItemChatAdapter(GroupChat.this, getItemsCompra()));
        	        getListChat().setAdapter(getAdapter());
        	        
               } catch (Exception exception) {

               }
                return null ;
         }

         // Metodo con las instrucciones al finalizar lo ejectuado en background
         protected void onPostExecute(Integer resultado) {
        	 int total = getListChat().getCount();
 	        getListChat().setSelection(total);
         }

   }

	
	private void llenaListaUsuarios(String[] lista){
		Log.e("CHAT", "llenaLista");
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
	public String getComunidad() {
		return comunidad;
	}
	public void setComunidad(String comunidad) {
		this.comunidad = comunidad;
	}
	
	

}
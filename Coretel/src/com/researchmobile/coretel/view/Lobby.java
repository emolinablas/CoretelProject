package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.entity.Usuario;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class Lobby extends Activity implements OnItemClickListener{
	
	private ConnectState connectState;
	private Mensaje mensaje;
	private User user;
	private RequestWS requestWS; 
	private ListView opcionesListView;
	private CatalogoComunidad catalogo;
	private ProgressDialog pd = null;
	private Usuario usuario;
	private boolean estadoPerfil;
	private boolean estadoComunidad;
	
	/**
	 * Metodos para menu Slide
	 * prepararMenu(), animationMenu(), expandMenu(), collapseMenu(), opcionesMenu(int opcion).
	 * Verificar el uso en OnItemClickListener()
	 * Componentes para menu Slide
	 */
	private TextView nombreUsuarioTextView;
	private ListView lView;
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;	
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
	
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters ;
	LinearLayout.LayoutParams listViewParameters;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lobby_menu);
		
		setNombreUsuarioTextView((TextView)findViewById(R.id.menu_title_1));
        getNombreUsuarioTextView().setText(User.getNombre());
		setUsuario(new Usuario());
		setOpcionesListView((ListView)findViewById(R.id.lobby_opciones_listview));
		setConnectState(new ConnectState());
		setMensaje(new Mensaje());
		setRequestWS(new RequestWS());
		getOpcionesListView().setAdapter(new ArrayAdapter<String>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				ListaOpciones()));
			    getOpcionesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    
			    prepararMenu();
	
		getOpcionesListView().setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		if (adapter == lView){
			collapseMenu();
			opcionesMenu(position);
		}else if(adapter == getOpcionesListView()){
			if (position == 0){
				new PerfilAsync().execute("0");
			}
		}
	}
	
	// Clase para ejecutar en Background
	class PerfilAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Lobby.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				setEstadoPerfil(IniciaPerfil());

			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			try{
				if (isEstadoPerfil()){
					Intent intent = new Intent(Lobby.this, Perfil.class);
					intent.putExtra("usuario", getUsuario());
					startActivity(intent);
				}else{
					getMensaje().VerMensaje(Lobby.this, getUsuario().getRespuestaWS().getMensaje());
				}
			}catch(Exception exception){
				
			}
		}
	}

	protected boolean IniciaPerfil() {
		if (getConnectState().isConnectedToInternet(Lobby.this)){
			String id = getUser().getUserId();
			System.out.println(id);
			
			setUsuario(getRequestWS().CargarPerfil(id));
			if (!getUsuario().getRespuestaWS().isResultado() || getUsuario() == null){
				return false;
			}else{
				return true;
			}
		}else{
			getMensaje().SinConexion(Lobby.this);
			return false;
		}
	}
	
	private void prepararMenu(){
		/***
         * MENU
         */
		lView = (ListView) findViewById(R.id.lista);
        
        String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Mi Perfil", "Chat", "Cerrar sesión" };

      // Set option as Multiple Choice. So that user can able to select more the one option from list
      lView.setAdapter(new ArrayAdapter<String>(this,
      android.R.layout.simple_list_item_1, lv_items));
      lView.setOnItemClickListener(this);
      animationMenu();
      
    }
	
	private void animationMenu(){
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
		    		expandMenu();
		    	}else{
		    		collapseMenu();
		    	}         	   
		    }
		});
	}
	
	private void expandMenu(){
    	//Expand
    	isExpanded = true;
		new ExpandAnimation(slidingPanel, panelWidth,
	    Animation.RELATIVE_TO_SELF, 0.0f,
	    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
    }
    
    private void collapseMenu(){
    	//Collapse
    	isExpanded = false;
		new CollapseAnimation(slidingPanel,panelWidth,
	    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
	    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);
    }
    
    private void opcionesMenu(int opcion){
		switch(opcion){
		case 0:
			Intent intentMapa = new Intent(Lobby.this, MapWuil.class);
			startActivity(intentMapa);
			break;
		case 1:
			Intent intentComunidades = new Intent(Lobby.this, Comunidades.class);
			startActivity(intentComunidades);
			break;
		case 2:
			Intent intentInvitaciones = new Intent(Lobby.this, Invitaciones.class);
			startActivity(intentInvitaciones);
			break;
		case 3:
			
			break;
		case 4:
			collapseMenu();
			break;
		case 5:
			Intent intentCerrar = new Intent(Lobby.this, Login.class);
			startActivity(intentCerrar);
			break;
	    default:
	        break;

		}
	}
	
	private String[] ListaOpciones(){
		String [] opciones = {"Mi Perfil", "Avatar"};
		return opciones;
	}

	public ListView getOpcionesListView() {
		return opcionesListView;
	}

	public void setOpcionesListView(ListView opcionesListView) {
		this.opcionesListView = opcionesListView;
	}
	public ConnectState getConnectState() {
		return connectState;
	}
	public void setConnectState(ConnectState connectState) {
		this.connectState = connectState;
	}
	public Mensaje getMensaje() {
		return mensaje;
	}
	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}
	public RequestWS getRequestWS() {
		return requestWS;
	}
	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public CatalogoComunidad getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(CatalogoComunidad catalogo) {
		this.catalogo = catalogo;
	}
	public boolean isEstadoPerfil() {
		return estadoPerfil;
	}
	public void setEstadoPerfil(boolean estadoPerfil) {
		this.estadoPerfil = estadoPerfil;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public boolean isEstadoComunidad() {
		return estadoComunidad;
	}
	public void setEstadoComunidad(boolean estadoComunidad) {
		this.estadoComunidad = estadoComunidad;
	}

	public TextView getNombreUsuarioTextView() {
		return nombreUsuarioTextView;
	}

	public void setNombreUsuarioTextView(TextView nombreUsuarioTextView) {
		this.nombreUsuarioTextView = nombreUsuarioTextView;
	}
	
}

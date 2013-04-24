package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.entity.Usuario;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.utility.MyAdapterMenu;
import com.researchmobile.coretel.ws.RequestWS;
import com.slidingmenu.lib.SlidingMenu;

public class Lobby extends Activity implements OnItemClickListener, OnClickListener{
	
	private ConnectState connectState;
	private Mensaje mensaje;
	private User user;
	private RequestWS requestWS;
	private CatalogoComunidad catalogoComunidad;
	private ListView opcionesListView;
	private CatalogoComunidad catalogo;
	private ProgressDialog pd = null;
	private Usuario usuario;
	private boolean estadoPerfil;
	private boolean estadoComunidad;
	private Button logoutButton;
	private ImageView menuButton;
	
	/**
	 * Metodos para menu Slide
	 * 
	 */
	
	private SlidingMenu menu = null;
	private TextView nombreUsuarioTextView;
	private ListView lView;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lobby_menu);
		
		prepararMenu();
		setMenuButton((ImageView)findViewById(R.id.menuViewButton));
		getMenuButton().setOnClickListener(this);
		setNombreUsuarioTextView((TextView)findViewById(R.id.menu_lateral_usuario));
        getNombreUsuarioTextView().setText(User.getNombre());
		setUsuario(new Usuario());
		setOpcionesListView((ListView)findViewById(R.id.lobby_opciones_listview));
		setLogoutButton((Button)findViewById(R.id.lobby_logout));
		getLogoutButton().setOnClickListener(this);
		setConnectState(new ConnectState());
		setMensaje(new Mensaje());
		setRequestWS(new RequestWS());
		getOpcionesListView().setAdapter(new ArrayAdapter<String>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				ListaOpciones()));
			    getOpcionesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    
			    
	
		getOpcionesListView().setOnItemClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		if (view == getLogoutButton()){
			salir();
		}else if (view == getMenuButton()){
			onBackPressed();
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		if (adapter == lView){
			onBackPressed();
			opcionesMenu(position);
		}else if(adapter == getOpcionesListView()){
			if (position == 0){
				new PerfilAsync().execute("0");
			}else if (position == 1){
				cambiarAvatar();
			}
		}
	}
	
	public void salir(){
		Intent intent = new Intent(Lobby.this, Login.class);
		startActivity(intent);
	}
	
	public void cambiarAvatar(){
		Intent intent = new Intent(Lobby.this, CambiarAvatar.class);
		startActivity(intent);
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
		menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_lateral);
        menu.setSlidingEnabled(false);
        
        //Inicializamos la lista que mostrara las opciones del menu
        lView = (ListView)findViewById(R.id.menu_lateral_listview);
        String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Mi Perfil", "Chat", "Cerrar sesión" };
        
        //Inicializamos y agregamos la imagen del imageview para mostrar el avatar
        ImageView avatar = (ImageView)findViewById(R.id.menu_lateral_avatar);
        Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
		avatar.setImageBitmap(image);
        
		//llenamos la lista con las opciones
		MyAdapterMenu adapterMenu = new MyAdapterMenu(this, lv_items);
		lView.setAdapter(adapterMenu);
		lView.setOnItemClickListener(this);
      
    }
	
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			menu.showMenu();
//			super.onBackPressed();
		}
	}
	
	private void opcionesMenu(int opcion){
		switch(opcion){
		case 0:
			Intent intentMapa = new Intent(Lobby.this, Mapa.class);
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
			new comunidadesAsync().execute("");
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
		String [] opciones = {"Datos Personales\nCambia tu informacion personal", "Avatar\nPersonaliza tu avatar"};
		return opciones;
	}

	class comunidadesAsync extends AsyncTask<String, Integer, Integer> {

        // Metodo que prepara lo que usara en background, Prepara el progress
        @Override
        protected void onPreExecute() {
              pd = ProgressDialog. show(Lobby.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
              pd.setCancelable( false);
       }

        // Metodo con las instrucciones que se realizan en background
        @Override
        protected Integer doInBackground(String... urlString) {
              try {
              	buscarComunidades();

             } catch (Exception exception) {

             }
              return null ;
       }

        // Metodo con las instrucciones al finalizar lo ejectuado en background
        protected void onPostExecute(Integer resultado) {
              pd.dismiss();
              try{
              	if (getCatalogoComunidad().getComunidad() != null && getCatalogoComunidad().getComunidad().length > 0){
                  	dialogComunidades();
                  }else{
                  	Toast.makeText(getBaseContext(), "no se encontraron comunidades", Toast.LENGTH_SHORT).show();
                  }
              }catch(Exception exception){
              	
              }
       }
 }
 
 private void dialogComunidades(){
		
		LayoutInflater factory = LayoutInflater.from(Lobby.this);
      
      final View textEntryView = factory.inflate(R.layout.dialog_comunidades , null);
     
      final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_comunidades_spinner);
      ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, android.R.layout.simple_spinner_item, getCatalogoComunidad().getComunidad());
      comunidadesSpinner.setAdapter(adaptador);
      
      final AlertDialog.Builder alert = new AlertDialog.Builder(Lobby.this );

     alert.setTitle( "Elija una comunidad");
     alert.setView(textEntryView);
     alert.setPositiveButton( "   OK   " ,
                  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                      	  DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
                      	  Intent intentChat = new Intent(Lobby.this, GroupChat.class);
                      	  intentChat.putExtra("comunidad", comunidad.getId());
                      	  startActivity(intentChat);
                             
                       }
                 });
     alert.setNegativeButton( "CANCELAR" ,
             new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface arg0, int arg1) {
                 	}
            });
     alert.show();
	}
	
	
	
	
	private void buscarComunidades(){
		setRequestWS(new RequestWS());
		setCatalogoComunidad(getRequestWS().CargarComunidades(User.getUserId()));
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

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}

	public Button getLogoutButton() {
		return logoutButton;
	}

	public void setLogoutButton(Button logoutButton) {
		this.logoutButton = logoutButton;
	}

	public ImageView getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(ImageView menuButton) {
		this.menuButton = menuButton;
	}

	
	
}

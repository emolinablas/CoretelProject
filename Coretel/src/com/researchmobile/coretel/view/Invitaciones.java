package com.researchmobile.coretel.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoInvitacion;
import com.researchmobile.coretel.entity.CatalogoSolicitud;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.Invitacion;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.supervision.utility.AdapterInvitaciones;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.MyAdapterMenu;
import com.researchmobile.coretel.ws.RequestWS;
import com.slidingmenu.lib.SlidingMenu;
/**
 * 
 * @author WUIL
 * Invitacion
 * 0 = No respondido
 * 1 = Aceptado
 * 2 = Rechazado
 *
 */
public class Invitaciones extends Activity implements OnItemClickListener, OnClickListener{
	
	private ListView invitacionesListView;
	private ListView invitacionesEnviadasListView;
	private ListView solicitudesEnviadasListView;
	private ListView solicitudesRecibidasListView;
	private ProgressDialog pd = null;
	private RequestWS requestWS;
	private RespuestaWS respuestaWS;
	private CatalogoInvitacion catalogoInvitacion;
	private CatalogoInvitacion catalogoInvitacionEnviado;
	private CatalogoSolicitud catalogoSolicitudEnviada;
	private CatalogoSolicitud catalogoSolicitudRecibida;
	private CatalogoComunidad catalogoComunidad;
	private String respuesta;
	private Invitacion invitacion;
	private RespuestaWS respuestaRespondidoWS;
	private RespuestaWS respuestaInvitar;
	private Button invitarButton;
	private String invitaEmail;
	private String invitaComunidad;
	
	private SlidingMenu menu = null;
	private ImageView startMenuButton;
	
	/**
	 * Metodos para menu Slide
	 * prepararMenu(), animationMenu(), expandMenu(), collapseMenu(), opcionesMenu(int opcion).
	 * Verificar el uso en OnItemClickListener()
	 * Componentes para menu Slide
	 */
	private ImageView avatarImageView;
	private TextView nombreUsuarioTextView;
	private ListView lView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.invitaciones_menu);
        prepareMenu();
        setNombreUsuarioTextView((TextView)findViewById(R.id.menu_lateral_usuario));
        getNombreUsuarioTextView().setText(User.getNombre());
        setStartMenuButton((ImageView)findViewById(R.id.menuViewButton));
        getStartMenuButton().setOnClickListener(this);
        setCatalogoInvitacion(new CatalogoInvitacion());
        setCatalogoInvitacionEnviado(new CatalogoInvitacion());
        setCatalogoSolicitudRecibida(new CatalogoSolicitud());
        setCatalogoSolicitudEnviada(new CatalogoSolicitud());
        setRequestWS(new RequestWS());
        setRespuestaWS(new RespuestaWS());
        setCatalogoComunidad(new CatalogoComunidad());
        setRespuestaRespondidoWS(new RespuestaWS());
        setInvitacionesListView((ListView)findViewById(R.id.invitaciones_listview));
        setInvitacionesEnviadasListView((ListView)findViewById(R.id.invitaciones_enviadas_listview));
        setSolicitudesRecibidasListView((ListView)findViewById(R.id.solicitudes_recibidas_listView));
        setSolicitudesEnviadasListView((ListView)findViewById(R.id.solicitud_enviada_listView));
        setInvitarButton((Button)findViewById(R.id.invitaciones_agregar_button));
        setAvatarImageView((ImageView)findViewById(R.id.menu_lateral_avatar));
        getInvitarButton().setOnClickListener(this);
        getInvitacionesListView().setOnItemClickListener(this);
        getInvitacionesEnviadasListView().setOnItemClickListener(this);
        
        new InvitacionesAsync().execute("");
    }
	
	private void prepareMenu(){
		//Preparacion de los componentes necesarios para que funcione el menu lateral
		menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_lateral);
        
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

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		if (adapter == getInvitacionesListView()){
			@SuppressWarnings("unchecked")
			HashMap<String, Object> invitacion = (HashMap<String, Object>) adapter.getItemAtPosition(position);
			Intent intentDetalle = new Intent(this, DetalleInvitacion.class);
			intentDetalle.putExtra("invitacion",invitacion);
			startActivity(intentDetalle);
		}else if(adapter == lView){
			opcionesMenu(position);
		}else if (adapter == getSolicitudesRecibidasListView()){
			@SuppressWarnings("unchecked")
			HashMap<String, Object> solicitud = (HashMap<String, Object>) adapter.getItemAtPosition(position);
			Intent intentDetalle = new Intent(this, DetalleSolicitud.class);
			intentDetalle.putExtra("solicitud", solicitud);
			startActivity(intentDetalle);
		}
	}
	
	private void opcionesMenu(int opcion){
		switch(opcion){
		case 0:
			Intent intentMapa = new Intent(Invitaciones.this, Mapa.class);
			startActivity(intentMapa);
			break;
		case 1:
			Intent intentComunidades = new Intent(Invitaciones.this, Comunidades.class);
			startActivity(intentComunidades);
			break;
		case 2:
			new InvitacionesAsync().execute("");
			break;
		case 3:
			Intent intentLobby = new Intent(Invitaciones.this, Lobby.class);
			startActivity(intentLobby);
			break;
		case 4:
			new comunidadesAsync().execute("");
			break;
		case 5:
			Intent intentCerrar = new Intent(Invitaciones.this, Login.class);
			startActivity(intentCerrar);
			break;
	    default:
	        break;

		}
	}
	
	@Override
	public void onClick(View view) {
		if (view == getInvitarButton()){
			new invitarAsync().execute("");
		}else if (view == getStartMenuButton()){
			onBackPressed();
		}
	}
	
//	Metodo para controlar ScrollView con ListView
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }

	    int totalHeight = 0;
	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
	            MeasureSpec.AT_MOST);
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += listItem.getMeasuredHeight();
	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight
	            + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();
	}
	
	class comunidadesAsync extends AsyncTask<String, Integer, Integer> {

        // Metodo que prepara lo que usara en background, Prepara el progress
        @Override
        protected void onPreExecute() {
              pd = ProgressDialog. show(Invitaciones.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
			
			LayoutInflater factory = LayoutInflater.from(Invitaciones.this);
	        
	        final View textEntryView = factory.inflate(R.layout.dialog_comunidades , null);
	       
	        final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_comunidades_spinner);
	        ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, android.R.layout.simple_spinner_item, getCatalogoComunidad().getComunidad());
	        comunidadesSpinner.setAdapter(adaptador);
	        
	        final AlertDialog.Builder alert = new AlertDialog.Builder(Invitaciones.this );

	       alert.setTitle( "Elija una comunidad");
	       alert.setView(textEntryView);
	       alert.setPositiveButton( "   OK   " ,
	                    new DialogInterface.OnClickListener() {
	                          @Override
	                          public void onClick(DialogInterface arg0, int arg1) {
	                        	  DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
	                        	  Toast.makeText(getBaseContext(), comunidad.getId(), Toast.LENGTH_SHORT).show();
	                        	  Intent intentChat = new Intent(Invitaciones.this, GroupChat.class);
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
			ConnectState conect = new ConnectState();
			if(conect.isConnectedToInternet(this)){
			setCatalogoComunidad(getRequestWS().CargarComunidades(User.getUserId()));
			}else{
				Toast.makeText(this, "No posee conexion a internet, intente mas tarde", Toast.LENGTH_SHORT).show();
			}
		}

	public void dialogInvitar() {
		LayoutInflater factory = LayoutInflater.from(Invitaciones.this);

		final View textEntryView = factory.inflate(R.layout.dialog_invitar, null);

		final EditText emailEditText = (EditText) textEntryView.findViewById(R.id.dialog_invitar_email_edittext);
		final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_invitar_comunidad_spinner);
		ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, android.R.layout.simple_spinner_item, getCatalogoComunidad().getComunidad());
        comunidadesSpinner.setAdapter(adaptador);
        
		final AlertDialog.Builder alert = new AlertDialog.Builder(Invitaciones.this);

		alert.setTitle("ENVIAR INVITACION");
		alert.setView(textEntryView);
		alert.setPositiveButton("Invitar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						setInvitaEmail(emailEditText.getText().toString());
						DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
						setInvitaComunidad(comunidad.getId());
						new enviarInvitacionAsync().execute("");
					}
				});
		alert.setNegativeButton("Cancelar",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}
				});
		alert.show();
	}
	
	public void enviar(){
		RequestWS requestInvitar = new RequestWS();
		setRespuestaInvitar(requestInvitar.enviarInvitacion(getInvitaEmail(), getInvitaComunidad()));
	}
	
	// Clase para ejecutar en Background
    class enviarInvitacionAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Invitaciones.this, "Verificando...", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	enviar();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                Toast.makeText(getBaseContext(), getRespuestaInvitar().getMensaje(), Toast.LENGTH_SHORT).show();
                if (getRespuestaInvitar().isResultado()){
                	new InvitacionesAsync().execute("");
                }
          }
    }
	
	// Clase para ejecutar en Background
    class invitarAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Invitaciones.this, "Buscando Comunidades", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	buscaComunidades();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                dialogInvitar();
         }
    }
 
	// Clase para ejecutar en Background
     class enviarRespuestaAsync extends AsyncTask<String, Integer, Integer> {

           // Metodo que prepara lo que usara en background, Prepara el progress
           @Override
           protected void onPreExecute() {
                 pd = ProgressDialog. show(Invitaciones.this, "RESPONDIENDO", "ESPERE UN MOMENTO");
                 pd.setCancelable( false);
          }

           // Metodo con las instrucciones que se realizan en background
           @Override
           protected Integer doInBackground(String... urlString) {
                 try {
                	 setRespuestaRespondidoWS(enviarRespuesta());

                } catch (Exception exception) {

                }
                 return null ;
          }

           // Metodo con las instrucciones al finalizar lo ejectuado en background
           protected void onPostExecute(Integer resultado) {
                 pd.dismiss();
                 if (getRespuestaRespondidoWS() != null && getRespuestaRespondidoWS().isResultado()){
                	 new InvitacionesAsync().execute(""); 
                 }
          }
    }
     
		public void buscaComunidades(){
		RequestWS request = new RequestWS();
		setCatalogoComunidad(request.CargarComunidades(User.getUserId()));
     }
     public RespuestaWS enviarRespuesta(){
    	 try{
    		 
    		 return respuestaWS;
    	 }catch(Exception exception){
    		 return null;
    	 }
    	
     }
 

	// Clase para ejecutar en Background
    class InvitacionesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Invitaciones.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	buscarInvitaciones();
                	buscarSolicitudes();

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                
                try{
                	if (getCatalogoInvitacion().getInvitacion() != null && getCatalogoInvitacion().getRespuestaWS().isResultado()){
                		System.out.println("INVITACIONES RECIBIDAS");
                    	llenaLista();
                    	
                    }
                	if (getCatalogoInvitacionEnviado().getInvitacion() != null && getCatalogoInvitacionEnviado().getRespuestaWS().isResultado()){
                		System.out.println("INVITACIONES ENVIADAS");
                		llenaListaEnviados();
                	}
                	if (getCatalogoSolicitudEnviada().getSolicitud() != null && getCatalogoSolicitudEnviada().getRespuestaWS().isResultado()){
                		System.out.println("SOLICITUDES ENVIADAS");
                		llenaListaSolicitudEnviados();
                	}
                	System.out.println(getCatalogoSolicitudRecibida().getRespuestaWS().isResultado());
                	if(getCatalogoSolicitudRecibida().getSolicitud() != null && getCatalogoSolicitudRecibida().getRespuestaWS().isResultado()){
                		System.out.println("SOLICITUDES ENCONTRADAS RECIBIDAS");
                		llenaListaSolicitudRecibidos();
                	}
                }catch(Exception exception){
                	
                }
         }
   }
    
    public void llenaListaSolicitudEnviados(){
    	
    	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	int tamano = getCatalogoSolicitudEnviada().getSolicitud().length;
    	long con = 0;
    	for (int i = 0; i < tamano; i++){
    		map = new HashMap<String, Object>();
    		map.put("id", con);
    		map.put("encabezado", getCatalogoSolicitudEnviada().getSolicitud()[i].getNombreUsuario());
    		map.put("comunidad", getCatalogoSolicitudEnviada().getSolicitud()[i].getNombreComunidad());
    		map.put("estado", getCatalogoSolicitudEnviada().getSolicitud()[i].getEstado());
    		map.put("email", "");
    		map.put("tipo", 4);
    		con++;
    		list.add(map);
    	}
    	
    	AdapterInvitaciones adapter = new AdapterInvitaciones(this, list);
    	getSolicitudesEnviadasListView().setAdapter(adapter);
    	setListViewHeightBasedOnChildren(getSolicitudesEnviadasListView());
    	getSolicitudesEnviadasListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	
    }
    
    public void llenaListaSolicitudRecibidos(){
    	
    	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	int tamano = getCatalogoSolicitudRecibida().getSolicitud().length;
    	long con = 0;
    	for (int i = 0; i < tamano; i++){
    		map = new HashMap<String, Object>();
    		map.put("id", con);
    		map.put("idSolicitud", getCatalogoSolicitudRecibida().getSolicitud()[i].getId());
    		map.put("encabezado", getCatalogoSolicitudRecibida().getSolicitud()[i].getNombreUsuario());
    		map.put("comunidad", getCatalogoSolicitudRecibida().getSolicitud()[i].getNombreComunidad());
    		map.put("estado", getCatalogoSolicitudRecibida().getSolicitud()[i].getEstado());
    		map.put("solicita", getCatalogoSolicitudRecibida().getSolicitud()[i].getNombreUsuario());
    		map.put("email", "");
    		map.put("tipo", 3);
    		con++;
    		list.add(map);
    	}
    	
    	AdapterInvitaciones adapter = new AdapterInvitaciones(this, list);
    	getSolicitudesRecibidasListView().setAdapter(adapter);
    	getSolicitudesRecibidasListView().setOnItemClickListener(this);
    	setListViewHeightBasedOnChildren(getSolicitudesRecibidasListView());
    	getSolicitudesRecibidasListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
    
    public void llenaLista(){
    	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	int tamano = getCatalogoInvitacion().getInvitacion().length;
    	long con = 0;
    	for (int i = 0; i < tamano; i++){
    		map = new HashMap<String, Object>();
    		map.put("id", con);
    		map.put("idInvitacion", getCatalogoInvitacion().getInvitacion()[i].getId());
    		map.put("encabezado", getCatalogoInvitacion().getInvitacion()[i].getNombreUsuario());
    		map.put("comunidad", getCatalogoInvitacion().getInvitacion()[i].getNombreComunidad());
    		map.put("estado", getCatalogoInvitacion().getInvitacion()[i].getEstado());
    		map.put("email", getCatalogoInvitacion().getInvitacion()[i].getEmail());
    		map.put("usuario", getCatalogoInvitacion().getInvitacion()[i].getUsuarioInvita());
    		map.put("fecha", getCatalogoInvitacion().getInvitacion()[i].getFechaRegistro());
    		map.put("tipo", 1);
    		con++;
    		list.add(map);
    	}
    	
    	AdapterInvitaciones adapter = new AdapterInvitaciones(this, list);
    	getInvitacionesListView().setAdapter(adapter);
    	setListViewHeightBasedOnChildren(getInvitacionesListView());
    	getInvitacionesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
    
    public void llenaListaEnviados(){
    	
    	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	int tamano = getCatalogoInvitacionEnviado().getInvitacion().length;
    	long con = 0;
    	for (int i = 0; i < tamano; i++){
    		map = new HashMap<String, Object>();
    		map.put("id", con);
    		map.put("encabezado", getCatalogoInvitacionEnviado().getInvitacion()[i].getNombreUsuario());
    		map.put("comunidad", getCatalogoInvitacionEnviado().getInvitacion()[i].getNombreComunidad());
    		map.put("estado", getCatalogoInvitacionEnviado().getInvitacion()[i].getEstado());
    		map.put("email", getCatalogoInvitacionEnviado().getInvitacion()[i].getEmail());
    		map.put("tipo", 2);
    		con++;
    		list.add(map);
    	}
    	
    	AdapterInvitaciones adapter = new AdapterInvitaciones(this, list);
    	getInvitacionesEnviadasListView().setAdapter(adapter);
    	setListViewHeightBasedOnChildren(getInvitacionesEnviadasListView());
    	getInvitacionesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
    
    public String[] invitaciones(){
    	int tamano = getCatalogoInvitacion().getInvitacion().length;
    	String[] lista = new String[tamano];
    	for (int i = 0; i < tamano; i++){
    		lista[i] = getCatalogoInvitacion().getInvitacion()[i].getNombreComunidad();
    	}
    	return lista;
	}
    
    public void buscarInvitaciones(){
    	System.out.println("LLEGA ANTES DE SETEAR EL CATALOGO DE INVITACIONES");
    	try{
    	setCatalogoInvitacion(getRequestWS().buscarInvitaciones());
    	}catch(Exception e){
    		System.out.println("No hay invitaciones recibidas");
    	}
    	try{
    	setCatalogoInvitacionEnviado(getRequestWS().buscaInvitacionesEnviadas());
    	}catch(Exception e){
    		System.out.println("No hay invitaciones enviadas");
    	}
    	
    	
    }
    
    public void buscarSolicitudes(){
    	System.out.println("BUSCA LAS SOLICITUDES");
    	try{
    	setCatalogoSolicitudRecibida(getRequestWS().buscarSolicitudesRecibidas());
    	//System.out.println(getCatalogoSolicitudRecibida().getSolicitud()[0].getFechaEnviada());
    	}catch(Exception e){
    		System.out.println("ERROR AL BUSCAR LAS SOLICITUDES RECIBIDAS Y SETEARLAS");
    	}
    	//System.out.println("Solicitud recibida con exito " + getCatalogoSolicitudRecibida().getSolicitud()[0].getId());
    	try{
    	setCatalogoSolicitudEnviada(getRequestWS().buscarSolicitudesEnviadas());
    	//System.out.println(getCatalogoSolicitudEnviada().getSolicitud()[0].getFechaEnviada());
    	}catch(Exception e){
    		System.out.println("ERROR AL BUSCAR LAS SOLICITUDES ENVIADAS Y SETEARLAS ");
    	}
    }

	public ListView getInvitacionesListView() {
		return invitacionesListView;
	}

	public void setInvitacionesListView(ListView invitacionesListView) {
		this.invitacionesListView = invitacionesListView;
	}

	public RequestWS getRequestWS() {
		return requestWS;
	}

	public void setRequestWS(RequestWS requestWS) {
		this.requestWS = requestWS;
	}

	public RespuestaWS getRespuestaWS() {
		return respuestaWS;
	}

	public void setRespuestaWS(RespuestaWS respuestaWS) {
		this.respuestaWS = respuestaWS;
	}

	public CatalogoInvitacion getCatalogoInvitacion() {
		return catalogoInvitacion;
	}

	public void setCatalogoInvitacion(CatalogoInvitacion catalogoInvitacion) {
		this.catalogoInvitacion = catalogoInvitacion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Invitacion getInvitacion() {
		return invitacion;
	}

	public void setInvitacion(Invitacion invitacion) {
		this.invitacion = invitacion;
	}

	public RespuestaWS getRespuestaRespondidoWS() {
		return respuestaRespondidoWS;
	}

	public void setRespuestaRespondidoWS(RespuestaWS respuestaRespondidoWS) {
		this.respuestaRespondidoWS = respuestaRespondidoWS;
	}

	public CatalogoInvitacion getCatalogoInvitacionEnviado() {
		return catalogoInvitacionEnviado;
	}

	public void setCatalogoInvitacionEnviado(
			CatalogoInvitacion catalogoInvitacionEnviado) {
		this.catalogoInvitacionEnviado = catalogoInvitacionEnviado;
	}

	public ListView getInvitacionesEnviadasListView() {
		return invitacionesEnviadasListView;
	}

	public void setInvitacionesEnviadasListView(
			ListView invitacionesEnviadasListView) {
		this.invitacionesEnviadasListView = invitacionesEnviadasListView;
	}

	public Button getInvitarButton() {
		return invitarButton;
	}

	public void setInvitarButton(Button invitarButton) {
		this.invitarButton = invitarButton;
	}

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}

	public RespuestaWS getRespuestaInvitar() {
		return respuestaInvitar;
	}

	public void setRespuestaInvitar(RespuestaWS respuestaInvitar) {
		this.respuestaInvitar = respuestaInvitar;
	}

	public String getInvitaEmail() {
		return invitaEmail;
	}

	public void setInvitaEmail(String invitaEmail) {
		this.invitaEmail = invitaEmail;
	}

	public String getInvitaComunidad() {
		return invitaComunidad;
	}

	public void setInvitaComunidad(String invitaComunidad) {
		this.invitaComunidad = invitaComunidad;
	}

	public TextView getNombreUsuarioTextView() {
		return nombreUsuarioTextView;
	}

	public void setNombreUsuarioTextView(TextView nombreUsuarioTextView) {
		this.nombreUsuarioTextView = nombreUsuarioTextView;
	}

	public ImageView getAvatarImageView() {
		return avatarImageView;
	}

	public void setAvatarImageView(ImageView avatarImageView) {
		this.avatarImageView = avatarImageView;
	}

	public CatalogoSolicitud getCatalogoSolicitudEnviada() {
		return catalogoSolicitudEnviada;
	}

	public void setCatalogoSolicitudEnviada(CatalogoSolicitud catalogoSolicitudEnviada) {
		this.catalogoSolicitudEnviada = catalogoSolicitudEnviada;
	}

	public CatalogoSolicitud getCatalogoSolicitudRecibida() {
		return catalogoSolicitudRecibida;
	}

	public void setCatalogoSolicitudRecibida(CatalogoSolicitud catalogoSolicitudRecibida) {
		this.catalogoSolicitudRecibida = catalogoSolicitudRecibida;
	}

	public ListView getSolicitudesEnviadasListView() {
		return solicitudesEnviadasListView;
	}

	public void setSolicitudesEnviadasListView(
			ListView solicitudesEnviadasListView) {
		this.solicitudesEnviadasListView = solicitudesEnviadasListView;
	}

	public ListView getSolicitudesRecibidasListView() {
		return solicitudesRecibidasListView;
	}

	public void setSolicitudesRecibidasListView(
			ListView solicitudesRecibidasListView) {
		this.solicitudesRecibidasListView = solicitudesRecibidasListView;
	}

	public ImageView getStartMenuButton() {
		return startMenuButton;
	}

	public void setStartMenuButton(ImageView startMenuButton) {
		this.startMenuButton = startMenuButton;
	}

	
}

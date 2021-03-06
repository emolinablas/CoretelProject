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
import android.view.KeyEvent;
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
import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.tutorial.pasalo.Comunidades_tutorial_1;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.MyAdapterComunidad;
import com.researchmobile.coretel.utility.MyAdapterMenu;
import com.researchmobile.coretel.ws.RequestWS;
import com.slidingmenu.lib.SlidingMenu;

public class Comunidades extends Activity implements OnClickListener, OnItemClickListener{
	private Button agregarButton;
	private Button explorarButton;
	private ImageView menuButton;
	private ListView comunidadesListView;
	private CatalogoComunidad catalogo;
	private CatalogoMiembro catalogoMiembro;
	private DetalleComunidad detalleComunidad;
	private ProgressDialog pd = null;
	private String select;
	private RequestWS request;
	private CatalogoComunidad catalogoComunidad;
	private boolean deOpciones;
	
	private SlidingMenu menu = null;
	private TextView nombreUsuarioTextView;
	private ListView lView;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comunidades_menu);
		prepararMenu();
		
		setMenuButton((ImageView)findViewById(R.id.menuViewButton));
		getMenuButton().setOnClickListener(this);
		setNombreUsuarioTextView((TextView)findViewById(R.id.menu_lateral_usuario));
        getNombreUsuarioTextView().setText(User.getNombre());
		setCatalogo(new CatalogoComunidad());
		setCatalogoComunidad(new CatalogoComunidad());
		setRequest(new RequestWS());
		ConnectState conect = new ConnectState();
		if(conect.isConnectedToInternet(this))
		{
		new buscaComunidadesAsync().execute("");
		}else{
			Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
		}
		
		if (!User.isModoTutorial()) {
			Intent intent = new Intent(Comunidades.this,Comunidades_tutorial_1.class);
			startActivity(intent);
		}
		
		setAgregarButton((Button)findViewById(R.id.comunidades_agregar_button));
		setExplorarButton((Button)findViewById(R.id.explorar_comunidades_button));
		getAgregarButton().setOnClickListener(this);
		getExplorarButton().setOnClickListener(this);
		setComunidadesListView((ListView)findViewById(R.id.comunidades_lista_listview));
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
        String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Mi Perfil", "Chat", "Cerrar sesi�n" };
        
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
public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
	if (adapterView == lView){
		onBackPressed();
		opcionesMenu(arg2);
	}
	
}

private void opcionesMenu(int opcion){
	switch(opcion){
	case 0:
		Intent intentMapa = new Intent(Comunidades.this, Mapa.class);
		startActivity(intentMapa);
		break;
	case 1:
		new buscaComunidadesAsync().execute("");
        
        //Si es modo tutorial muestra la ayuda
		if(!User.isModoTutorial()){
			Intent intent = new Intent(Comunidades.this, Comunidades_tutorial_1.class);
			startActivity(intent);
		}
       
		break;
	case 2:
		Intent intentInvitaciones = new Intent(Comunidades.this, Invitaciones.class);
		startActivity(intentInvitaciones);
		break;
	case 3:
		Intent intentLobby = new Intent(Comunidades.this, Lobby.class);
		startActivity(intentLobby);
		break;
	case 4:
		new comunidadesAsync().execute("");
		

		break;
	case 5:
		Intent intentCerrar = new Intent(Comunidades.this, Login.class);
		startActivity(intentCerrar);
		break;
    default:
        break;

	}
}
	
	// Clase para ejecutar en Background
    class buscaComunidadesAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Comunidades.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
                resultadoComunidades();

         }
    }
    
    class comunidadesAsync extends AsyncTask<String, Integer, Integer> {

        // Metodo que prepara lo que usara en background, Prepara el progress
        @Override
        protected void onPreExecute() {
              pd = ProgressDialog. show(Comunidades.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
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
        
        private void buscarComunidades(){
    		setRequest(new RequestWS());
    		setCatalogoComunidad(getRequest().CargarComunidades(User.getUserId()));
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
		
		LayoutInflater factory = LayoutInflater.from(Comunidades.this);
        
        final View textEntryView = factory.inflate(R.layout.dialog_comunidades , null);
       
        final Spinner comunidadesSpinner = (Spinner) textEntryView.findViewById(R.id.dialog_comunidades_spinner);
        ArrayAdapter<DetalleComunidad> adaptador = new ArrayAdapter<DetalleComunidad>(this, android.R.layout.simple_spinner_item, getCatalogoComunidad().getComunidad());
        comunidadesSpinner.setAdapter(adaptador);
        
        final AlertDialog.Builder alert = new AlertDialog.Builder(Comunidades.this );

       alert.setTitle( "Elija una comunidad");
       alert.setView(textEntryView);
       alert.setPositiveButton( "   OK   " ,
                    new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface arg0, int arg1) {
                        	  DetalleComunidad comunidad = (DetalleComunidad)comunidadesSpinner.getSelectedItem();
                        	  Intent intentChat = new Intent(Comunidades.this, GroupChat.class);
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
    
    private void resultadoComunidades(){
    	try{
    	if (getCatalogo() != null){
    		Log.e("pio", "comunidades = " + getCatalogo().getComunidad().length);
    		if (getCatalogo().getRespuestaWS().isResultado()){
    			
    			MyAdapterComunidad adapterComunidad = new MyAdapterComunidad(this, getCatalogo().getComunidad());
    			getComunidadesListView().setAdapter(adapterComunidad);
    			
    			getComunidadesListView().setOnItemClickListener(new OnItemClickListener() {
    			    @Override
    			    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
    			    	DetalleComunidad comunidad = new DetalleComunidad();
    			    	comunidad = (DetalleComunidad)a.getItemAtPosition(position);
    			    	setSelect(comunidad.getId());
    			    	new MiembrosAsync().execute("");
    			    }
    			});
    		}
    	}
    	}catch(Exception e){
    		Toast.makeText(this, "No se pudo cargar el catalogo de comunidades \n puede que no posea", Toast.LENGTH_SHORT).show();
    	}
    }
    private void buscaComunidades(){
    	Log.e("pio", "buscar comunidades");
    	RequestWS request = new RequestWS();
    	setCatalogo(request.CargarComunidades(User.getUserId()));
    	
    }
	
	// Clase para ejecutar en Background
    class MiembrosAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Comunidades.this, "VERIFICANDO DATOS",
                            "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	CargarDatos(getSelect());

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                Intent intent = new Intent(Comunidades.this, Comunidad.class);
//                intent.putExtra("idComunidad", getDetalleComunidad().getId());
//		        intent.putExtra("catalogoMiembro", getCatalogoMiembro());
		        intent.putExtra("detallecomunidad", getDetalleComunidad());
		        startActivity(intent);
         }
   }

	
	private void CargarDatos(String select) {
		
		try{
			ConnectState con = new ConnectState();
			if (con.isConnectedToInternet(this)){
				RequestWS request = new RequestWS();
				setDetalleComunidad(request.DetalleComunidad(getSelect()));
//				if(getDetalleComunidad().getRespuestaWS().isResultado()){
//					setCatalogoMiembro(request.CatalogoMiembro(getDetalleComunidad().getId()));
//					if (getCatalogoMiembro().getRespuestaWS().isResultado()){
//						
//					}
//				}
			}
		}catch(Exception exception){
			
		}
	}
	
	private String[] ListaComunidades() {
		int tamano = getCatalogo().getComunidad().length;
		System.out.println(tamano);
		String[] comunidades = new String[tamano];
		for (int i = 0; i < tamano; i++){
			comunidades[i] = getCatalogo().getComunidad()[i].getNombre();
		}
		return comunidades;
	}


	@Override
	public void onClick(View view) {
		if (view == getAgregarButton()){
			AgregarComunidad();
		}else if(view == getExplorarButton()){
			explorarComunidades();
		}else if (view == getMenuButton()){
			onBackPressed();
		}
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	      if (keyCode == KeyEvent.KEYCODE_BACK) {
	            // Preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR     
	            return true;
	      }
	      
	      return super.onKeyDown(keyCode, event);
	    }


	private void explorarComunidades(){
		Intent intent = new Intent(Comunidades.this, ComunidadesTodas.class);
		startActivity(intent);
	}

	private void AgregarComunidad() {
		Intent intent = new Intent(Comunidades.this, CreaComunidad.class);
		startActivity(intent);
		
	}

	public Button getAgregarButton() {
		return agregarButton;
	}


	public void setAgregarButton(Button agregarButton) {
		this.agregarButton = agregarButton;
	}


	public ListView getComunidadesListView() {
		return comunidadesListView;
	}


	public void setComunidadesListView(ListView comunidadesListView) {
		this.comunidadesListView = comunidadesListView;
	}


	public CatalogoComunidad getCatalogo() {
		return catalogo;
	}


	public void setCatalogo(CatalogoComunidad catalogo) {
		this.catalogo = catalogo;
	}

	public CatalogoMiembro getCatalogoMiembro() {
		return catalogoMiembro;
	}

	public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
		this.catalogoMiembro = catalogoMiembro;
	}

	public DetalleComunidad getDetalleComunidad() {
		return detalleComunidad;
	}

	public void setDetalleComunidad(DetalleComunidad detalleComunidad) {
		this.detalleComunidad = detalleComunidad;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}
	public Button getExplorarButton() {
		return explorarButton;
	}
	public void setExplorarButton(Button explorarButton) {
		this.explorarButton = explorarButton;
	}

	public TextView getNombreUsuarioTextView() {
		return nombreUsuarioTextView;
	}

	public void setNombreUsuarioTextView(TextView nombreUsuarioTextView) {
		this.nombreUsuarioTextView = nombreUsuarioTextView;
	}

	public RequestWS getRequest() {
		return request;
	}

	public void setRequest(RequestWS request) {
		this.request = request;
	}

	public CatalogoComunidad getCatalogoComunidad() {
		return catalogoComunidad;
	}

	public void setCatalogoComunidad(CatalogoComunidad catalogoComunidad) {
		this.catalogoComunidad = catalogoComunidad;
	}

	public boolean isDeOpciones() {
		return deOpciones;
	}

	public void setDeOpciones(boolean deOpciones) {
		this.deOpciones = deOpciones;
	}

	public ImageView getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(ImageView menuButton) {
		this.menuButton = menuButton;
	}

	
	
}

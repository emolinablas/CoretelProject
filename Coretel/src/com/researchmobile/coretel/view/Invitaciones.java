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
import android.view.LayoutInflater;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoInvitacion;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.Invitacion;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.ws.RequestWS;
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
	private ProgressDialog pd = null;
	private RequestWS requestWS;
	private RespuestaWS respuestaWS;
	private CatalogoInvitacion catalogoInvitacion;
	private CatalogoInvitacion catalogoInvitacionEnviado;
	private CatalogoComunidad catalogoComunidad;
	private String respuesta;
	private Invitacion invitacion;
	private RespuestaWS respuestaRespondidoWS;
	private RespuestaWS respuestaInvitar;
	private Button invitarButton;
	private String invitaEmail;
	private String invitaComunidad;
	
	/**
	 * Metodos para menu Slide
	 * prepararMenu(), animationMenu(), expandMenu(), collapseMenu(), opcionesMenu(int opcion).
	 * Verificar el uso en OnItemClickListener()
	 * Componentes para menu Slide
	 */
	private ImageView avatarImageView;
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.invitaciones_menu);
        
        setNombreUsuarioTextView((TextView)findViewById(R.id.menu_title_1));
        getNombreUsuarioTextView().setText(User.getNombre());
        
        setCatalogoInvitacion(new CatalogoInvitacion());
        setCatalogoInvitacionEnviado(new CatalogoInvitacion());
        setRequestWS(new RequestWS());
        setRespuestaWS(new RespuestaWS());
        setCatalogoComunidad(new CatalogoComunidad());
        setRespuestaRespondidoWS(new RespuestaWS());
        setInvitacionesListView((ListView)findViewById(R.id.invitaciones_listview));
        setInvitacionesEnviadasListView((ListView)findViewById(R.id.invitaciones_enviadas_listview));
        setInvitarButton((Button)findViewById(R.id.invitaciones_agregar_button));
        setAvatarImageView((ImageView)findViewById(R.id.mapa_avatar));
        getInvitarButton().setOnClickListener(this);
        getInvitacionesListView().setOnItemClickListener(this);
        getInvitacionesEnviadasListView().setOnItemClickListener(this);
        prepararMenu();
    	Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
		getAvatarImageView().setImageBitmap(image);
        new InvitacionesAsync().execute("");
    }

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		if (adapter == getInvitacionesListView()){
			setInvitacion((Invitacion)adapter.getItemAtPosition(position));
			dialogInvitacion();
		}else if(adapter == lView){
			collapseMenu();
			opcionesMenu(position);
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
			collapseMenu();
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
		}
	}
	
	private void prepararMenu(){
		/***
         * MENU
         */
		lView = (ListView) findViewById(R.id.lista);
        
        String lv_items[] = { "Mapa", "Comunidades", "Invitaciones", "Mi Perfil", "Chat", "Cerrar sesi—n" };

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
	
	private void dialogInvitacion() {
		 new AlertDialog.Builder(this)
         .setIcon(this.getResources().getDrawable(R.drawable.alert))
         .setTitle("Invitacion")
         .setMessage("¿Que desea hacer?")
         .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                  setRespuesta("1");
                  new enviarRespuestaAsync().execute("");
             }
         })
         .setNegativeButton("RECHAZAR", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int whichButton) {
                 setRespuesta("2");
                 new enviarRespuestaAsync().execute("");
             }
         })
         .show();
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
                pd = ProgressDialog. show(Invitaciones.this, "Buscando Comunidades", "ESPERE UN MOMENTO");
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
    		 RespuestaWS respuestaWS = new RespuestaWS();
    		 respuestaWS = getRequestWS().enviarRespuestaInvitacion(getInvitacion(), getRespuesta());
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

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                
                try{
                	if (getCatalogoInvitacion().getRespuestaWS().isResultado()){
                    	llenaLista();
                    }
                	if (getCatalogoInvitacionEnviado().getRespuestaWS().isResultado()){
                		llenaListaEnviados();
                	}
                }catch(Exception exception){
                	
                }
         }
   }
    
    public void llenaLista(){
    	getInvitacionesListView().setAdapter(new ArrayAdapter<Invitacion>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				getCatalogoInvitacion().getInvitacion()));
    	
    			getInvitacionesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
    
    public void llenaListaEnviados(){
    	getInvitacionesEnviadasListView().setAdapter(new ArrayAdapter<Invitacion>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				getCatalogoInvitacionEnviado().getInvitacion()));
    	
				getInvitacionesEnviadasListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
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
    	setCatalogoInvitacion(getRequestWS().buscarInvitaciones());
    	System.out.println("Invitaci—n recibida con exito " + getCatalogoInvitacion().getInvitacion()[0].getId());
    	setCatalogoInvitacionEnviado(getRequestWS().buscaInvitacionesEnviadas());
    
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

	
}

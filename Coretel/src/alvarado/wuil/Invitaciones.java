package alvarado.wuil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.CatalogoInvitacion;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.Invitacion;
import com.researchmobile.coretel.entity.Miembro;
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
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitaciones);
        
        setCatalogoInvitacion(new CatalogoInvitacion());
        setCatalogoInvitacionEnviado(new CatalogoInvitacion());
        setRequestWS(new RequestWS());
        setRespuestaWS(new RespuestaWS());
        setCatalogoComunidad(new CatalogoComunidad());
        setRespuestaRespondidoWS(new RespuestaWS());
        setInvitacionesListView((ListView)findViewById(R.id.invitaciones_listview));
        setInvitacionesEnviadasListView((ListView)findViewById(R.id.invitaciones_enviadas_listview));
        setInvitarButton((Button)findViewById(R.id.invitaciones_agregar_button));
        getInvitarButton().setOnClickListener(this);
        getInvitacionesListView().setOnItemClickListener(this);
        getInvitacionesEnviadasListView().setOnItemClickListener(this);
        
        new InvitacionesAsync().execute("");
    }

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//		if (view == getInvitacionesListView()){
			setInvitacion((Invitacion)adapter.getItemAtPosition(position));
			dialogInvitacion();
//		}
	}
	
	@Override
	public void onClick(View view) {
		if (view == getInvitarButton()){
			new invitarAsync().execute("");
		}
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

	
}

package alvarado.wuil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoComunidad;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.entity.Usuario;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class Lobby extends Activity implements OnClickListener{
	
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
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lobby);
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
			    
		getOpcionesListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		        if (position == 0){
		        	new PerfilAsync().execute("0");
		        }else if (position == 1){
		        	IniciaInvitaciones();
		        }else {
		        	Intent intent = new Intent(Lobby.this, Comunidades.class);
		        	startActivity(intent);
		        }
		    }
		});
	}
	
	// Clase para ejecutar en Background
	class PerfilAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Lobby.this, "VERIFICANDO DATOS",
					"ESPERE UN MOMENTO");
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
	
	protected boolean IniciaComunidades() {
		if (getConnectState().isConnectedToInternet(Lobby.this)){
			String id = getUser().getUserId();
			System.out.println(id);
			setCatalogo(getRequestWS().CargarComunidades(id));
			if (getCatalogo() == null){
				return false;
			}else{
				return true;
			}
		}else{
			getMensaje().SinConexion(Lobby.this);
			return false;
		}
	}
	private void VerComunidades() {
		Intent intent = new Intent(Lobby.this, Comunidades.class);
		intent.putExtra("catalogo", getCatalogo());
		startActivity(intent);
		
	}
	protected void IniciaInvitaciones() {
		Toast.makeText(getBaseContext(), "En proceso de desarrollo", Toast.LENGTH_SHORT).show();
		
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
	
	private String[] ListaOpciones(){
		String [] opciones = {"Mi Perfil", "Invitaciones Recibidas", "Comunidades"};
		return opciones;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getBaseContext(), "En Proceso de Desarrollo", Toast.LENGTH_SHORT).show();
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
	
	
}

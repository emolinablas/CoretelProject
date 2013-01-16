package alvarado.wuil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoInvitacion;
import com.researchmobile.coretel.entity.Invitacion;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.ws.RequestWS;

public class Invitaciones extends Activity implements OnItemClickListener{
	
	private ListView invitacionesListView;
	private ProgressDialog pd = null;
	private RequestWS requestWS;
	private RespuestaWS respuestaWS;
	private CatalogoInvitacion catalogoInvitacion;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitaciones);
        
        setRequestWS(new RequestWS());
        setRespuestaWS(new RespuestaWS());
        setInvitacionesListView((ListView)findViewById(R.id.invitaciones_listview));
        getInvitacionesListView().setOnItemClickListener(this);
        
        new InvitacionesAsync().execute("");
    }

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Invitacion seleccionado = (Invitacion)adapter.getItemAtPosition(position);
		
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
                if (getCatalogoInvitacion().getRespuestaWS().isResultado()){
                	llenaLista();
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
    
    public String[] invitaciones(){
    	int tamano = getCatalogoInvitacion().getInvitacion().length;
    	String[] lista = new String[tamano];
    	for (int i = 0; i < tamano; i++){
    		lista[i] = getCatalogoInvitacion().getInvitacion()[i].getNombreComunidad();
    	}
    	return lista;
	}
    
    public void buscarInvitaciones(){
    	setCatalogoInvitacion((getRequestWS().buscarInvitaciones()));
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

	
	
}

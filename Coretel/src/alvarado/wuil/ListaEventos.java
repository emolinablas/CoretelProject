package alvarado.wuil;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.researchmobile.coretel.entity.CatalogoAnotacion;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.ws.RequestWS;

public class ListaEventos extends Activity implements OnItemClickListener{
	
	private ListView eventosListView;
	private RequestWS request;
	private RespuestaWS respuesta;
	private SimpleAdapter simpleAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaeventos);
        
        setEventosListView((ListView)findViewById(R.id.listaeventos_eventos_listview));
        setRequest(new RequestWS());
        setRespuesta(new RespuestaWS());
        
        setSimpleAdapter(new SimpleAdapter(this, 
        		myList(null), 
        		R.layout.lista_dos_campos,
                new String[] {"titulo","description"}, 
                new int[] {R.id.lista_titulo_textview, R.id.lista_descripcion_textview}));
        getEventosListView().setAdapter(getSimpleAdapter());
        
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	private ArrayList<HashMap<String, String>> myList(CatalogoAnotacion catalogo){
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("titulo", "1");
        map.put("description", "MACC");
        mylist.add(map);
        map = new HashMap<String, String>();
        map.put("titulo", "2");
        map.put("description", "SE");
        mylist.add(map);
		return mylist;
	}

	public ListView getEventosListView() {
		return eventosListView;
	}

	public void setEventosListView(ListView eventosListView) {
		this.eventosListView = eventosListView;
	}

	public RequestWS getRequest() {
		return request;
	}

	public void setRequest(RequestWS request) {
		this.request = request;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public SimpleAdapter getSimpleAdapter() {
		return simpleAdapter;
	}

	public void setSimpleAdapter(SimpleAdapter simpleAdapter) {
		this.simpleAdapter = simpleAdapter;
	}
	
}

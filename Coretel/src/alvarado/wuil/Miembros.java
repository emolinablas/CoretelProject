package alvarado.wuil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.CatalogoMiembro;

public class Miembros extends Activity implements OnClickListener{
	private ListView miembrosListView;
	private CatalogoMiembro catalogoMiembro;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.miembros);
		
		Bundle bundle = (Bundle)getIntent().getExtras();
		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
		setMiembrosListView((ListView)findViewById(R.id.miembros_lista_listview));
		getMiembrosListView().setAdapter(new ArrayAdapter<String>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				ListaMiembros()));
				getMiembrosListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    
			    getMiembrosListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		        Toast.makeText(getBaseContext(), "En proceso de desarrollo", Toast.LENGTH_SHORT).show();
		    }
		});
	}

	private String[] ListaMiembros() {
		
		int tamano = getCatalogoMiembro().getMiembro().length;
		String[] miembros = new String[tamano];
		for (int i = 0; i < tamano; i++){
			miembros[i] = getCatalogoMiembro().getMiembro()[i].getNombreUsuario();
		}
		return miembros;
		
		//String[] miembros = {"jperez", "smendez", "aalfaro","mhernandez"}; 
		//return miembros;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public ListView getMiembrosListView() {
		return miembrosListView;
	}

	public void setMiembrosListView(ListView miembrosListView) {
		this.miembrosListView = miembrosListView;
	}

	public CatalogoMiembro getCatalogoMiembro() {
		return catalogoMiembro;
	}

	public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
		this.catalogoMiembro = catalogoMiembro;
	}
	
	
}

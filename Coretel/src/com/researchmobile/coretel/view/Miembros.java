package com.researchmobile.coretel.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.Miembro;

public class Miembros extends Activity implements OnClickListener{
	private ListView miembrosListView;
	private CatalogoMiembro catalogoMiembro;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.miembros);
		
		Bundle bundle = (Bundle)getIntent().getExtras();
		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
		setMiembrosListView((ListView)findViewById(R.id.miembros_lista_listview));
		getMiembrosListView().setAdapter(new ArrayAdapter<Miembro>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				getCatalogoMiembro().getMiembro()));
				getMiembrosListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    
			    getMiembrosListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		        Miembro miembro = new Miembro();
		        miembro = (Miembro)a.getItemAtPosition(position);
		        Intent intent = new Intent(Miembros.this, DetalleMiembro.class);
		        intent.putExtra("miembro", miembro);
		        startActivity(intent);
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

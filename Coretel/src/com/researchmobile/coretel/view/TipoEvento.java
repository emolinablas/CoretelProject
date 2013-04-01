package com.researchmobile.coretel.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.entity.TipoAnotacion;

public class TipoEvento extends Activity implements OnClickListener{
	private Button agregarButton;
	private ListView tiposListView;
	private String idComunidad;
	private CatalogoTipoAnotacion catalogoTipoAnotacion;
	private TipoAnotacion tipoAnotacion;
    private boolean esDuenno;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tipoevento);
		Bundle bundle = (Bundle)getIntent().getExtras();
		setCatalogoTipoAnotacion((CatalogoTipoAnotacion)bundle.get("catalogoTipoAnotacion"));
		setIdComunidad((String)bundle.getString("idComunidad"));
		setEsDuenno((boolean)bundle.getBoolean("esDuenno"));
		
		
		setAgregarButton((Button)findViewById(R.id.tipoevento_agregar_button));
		getAgregarButton().setOnClickListener(this);
		setTiposListView((ListView)findViewById(R.id.tipoevento_lista_listview));
		
		if(isEsDuenno()){
			Log.v("pio", "El usuario no es dueño de la comunidad, crear un nuevo tipo");
		}else{
			getAgregarButton().setVisibility(View.GONE);
		}
		
		if (ListaTipos() != null){
			getTiposListView().setAdapter(new ArrayAdapter<String>(this, 
					R.layout.lista_lobby,
					R.id.lista_lobby_textview,
					ListaTipos()));
					getTiposListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		}
			    
			    getTiposListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    	String seleccion = a.getItemAtPosition(position).toString();
		    	String idAnotacion = buscaId(seleccion);
		    	Intent intent = new Intent(TipoEvento.this, Descevento.class);
		    	intent.putExtra("anotacion", getTipoAnotacion());
		    	
		    	if(isEsDuenno()){  // dato diferente para saber si es due–o del evento en el descevento
					intent.putExtra("esDuenno", true);
					}else{
						intent.putExtra("esDuenno", false);
					}
		    	
		    	startActivity(intent);
		    }

			private String buscaId(String seleccion) {
				int tamano = getCatalogoTipoAnotacion().getTipoAnotacion().length;
				String id = null;
				for (int i = 0; i < tamano; i++){
					if (getCatalogoTipoAnotacion().getTipoAnotacion()[i].getNombre().equalsIgnoreCase(seleccion)){
						setTipoAnotacion((TipoAnotacion)getCatalogoTipoAnotacion().getTipoAnotacion()[i]);
					}
				}
				return null;
			}
		});
		
	}
	private String[] ListaTipos() {
		if (getCatalogoTipoAnotacion().getTipoAnotacion() != null){
			int tamano = getCatalogoTipoAnotacion().getTipoAnotacion().length;
			String tipos[] = new String[tamano];
			for (int i = 0; i < tamano; i++){
				tipos[i] = getCatalogoTipoAnotacion().getTipoAnotacion()[i].getNombre();
			}
			return tipos;
		}
		return null;
	}
	@Override
	public void onClick(View view) {
		if (view == getAgregarButton()){
			Intent intent = new Intent(TipoEvento.this, NuevoTipoEvento.class);
			intent.putExtra("idComunidad", getIdComunidad());
			startActivity(intent);
		}
	}
	public Button getAgregarButton() {
		return agregarButton;
	}
	public void setAgregarButton(Button agregarButton) {
		this.agregarButton = agregarButton;
	}
	public ListView getTiposListView() {
		return tiposListView;
	}
	public void setTiposListView(ListView tiposListView) {
		this.tiposListView = tiposListView;
	}
	public String getIdComunidad() {
		return idComunidad;
	}
	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	public CatalogoTipoAnotacion getCatalogoTipoAnotacion() {
		return catalogoTipoAnotacion;
	}
	public void setCatalogoTipoAnotacion(CatalogoTipoAnotacion catalogoTipoAnotacion) {
		this.catalogoTipoAnotacion = catalogoTipoAnotacion;
	}
	public TipoAnotacion getTipoAnotacion() {
		return tipoAnotacion;
	}
	public void setTipoAnotacion(TipoAnotacion tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}
	public boolean isEsDuenno() {
		return esDuenno;
	}
	public void setEsDuenno(boolean esDuenno) {
		this.esDuenno = esDuenno;
	}
	
}
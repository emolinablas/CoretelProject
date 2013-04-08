package com.researchmobile.coretel.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.utility.MyAdapterTiposEventos;

public class OpcionTipoEvento extends Activity implements OnClickListener{
	
	private CatalogoTipoAnotacion catalogoTipoAnotacion;
	private ListView listaTipos;
	private MyAdapterTiposEventos adapterTipoEventos;
	private Button agregarButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.opciontipoevento);
		
		Bundle bundle = getIntent().getExtras();
		setCatalogoTipoAnotacion((CatalogoTipoAnotacion)bundle.get("tipos"));
		setAgregarButton((Button)findViewById(R.id.opciontipoevento_agregar_button));
		setListaTipos((ListView)findViewById(R.id.opciontipoevento_lista_listview));
		
		getAgregarButton().setOnClickListener(this);
		setAdapterTipoEventos(new MyAdapterTiposEventos(this, getCatalogoTipoAnotacion().getTipoAnotacion()));
		getListaTipos().setAdapter(getAdapterTipoEventos());
	}
	

	@Override
	public void onClick(View view) {
		if (view == getAgregarButton()){
			Intent intent = new Intent(OpcionTipoEvento.this, NuevoTipoEvento.class);
			intent.putExtra("idComunidad", getCatalogoTipoAnotacion().getTipoAnotacion());
			startActivity(intent);
		}
		
	}

	public CatalogoTipoAnotacion getCatalogoTipoAnotacion() {
		return catalogoTipoAnotacion;
	}

	public void setCatalogoTipoAnotacion(CatalogoTipoAnotacion catalogoTipoAnotacion) {
		this.catalogoTipoAnotacion = catalogoTipoAnotacion;
	}

	public ListView getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(ListView listaTipos) {
		this.listaTipos = listaTipos;
	}

	public MyAdapterTiposEventos getAdapterTipoEventos() {
		return adapterTipoEventos;
	}

	public void setAdapterTipoEventos(MyAdapterTiposEventos adapterTipoEventos) {
		this.adapterTipoEventos = adapterTipoEventos;
	}


	public Button getAgregarButton() {
		return agregarButton;
	}


	public void setAgregarButton(Button agregarButton) {
		this.agregarButton = agregarButton;
	}
	
	
}

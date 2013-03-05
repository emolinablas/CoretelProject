package com.researchmobile.coretel.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;


public class EditarTipoEvento extends Activity implements OnClickListener, OnKeyListener{

	private EditText nombreEditText;
	private EditText descripcionEditText;
	private Button guardarButton;
	private Button iconoButton;
	private ImageView iconoEvento;
	private CheckBox incidenteCheckBox;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_tipoevento);
		
		Bundle bundle = (Bundle)getIntent().getExtras();
		setNombreEditText((EditText)findViewById(R.id.editartipoevento_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.editartipoevento_descripcion_edittext));
		setGuardarButton((Button)findViewById(R.id.editartipoevento_guardar_button));
		setIconoButton((Button)findViewById(R.id.editartipoevento_icono_button));
		setIconoEvento((ImageView)findViewById(R.id.editartipoevento_icono_imageview));
		setIncidenteCheckBox((CheckBox)findViewById(R.id.editartipoevento_incidente_checkbox));
		getNombreEditText().setOnClickListener(this);
		getDescripcionEditText().setOnKeyListener(this);
		getGuardarButton().setOnClickListener(this);
		getIconoButton().setOnClickListener(this);
		
		
	}

	public EditText getNombreEditText() {
		return nombreEditText;
	}

	public void setNombreEditText(EditText nombreEditText) {
		this.nombreEditText = nombreEditText;
	}

	public EditText getDescripcionEditText() {
		return descripcionEditText;
	}

	public void setDescripcionEditText(EditText descripcionEditText) {
		this.descripcionEditText = descripcionEditText;
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	public Button getIconoButton() {
		return iconoButton;
	}

	public void setIconoButton(Button iconoButton) {
		this.iconoButton = iconoButton;
	}

	public ImageView getIconoEvento() {
		return iconoEvento;
	}

	public void setIconoEvento(ImageView iconoEvento) {
		this.iconoEvento = iconoEvento;
	}

	public CheckBox getIncidenteCheckBox() {
		return incidenteCheckBox;
	}

	public void setIncidenteCheckBox(CheckBox incidenteCheckBox) {
		this.incidenteCheckBox = incidenteCheckBox;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}




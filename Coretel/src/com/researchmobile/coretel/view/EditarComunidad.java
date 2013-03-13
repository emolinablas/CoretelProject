package com.researchmobile.coretel.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.researchmobile.coretel.entity.DetalleComunidad;

public class EditarComunidad extends Activity implements OnClickListener{
	private Button cancelarButton;
	private Button aplicarButton;
	private EditText nombreEditText;
	private EditText descripcionEditText;
	private DetalleComunidad detalleComunidad;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editarcomunidad);
		Bundle bundle = getIntent().getExtras();
		setDetalleComunidad((DetalleComunidad)bundle.get("comunidad"));
		
		setCancelarButton((Button)findViewById(R.id.editarcomunidad_cancelar_button));
		setAplicarButton((Button)findViewById(R.id.editarcomunidad_guardar_button));
		setNombreEditText((EditText)findViewById(R.id.editarcomunidad_nombre_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.editarcomunidad_descripcion_edittext));
		
		getNombreEditText().setHint(getDetalleComunidad().getNombre());
		getDescripcionEditText().setHint(getDetalleComunidad().getDescripcion());
		getCancelarButton().setOnClickListener(this);
		getAplicarButton().setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == getCancelarButton()){
			finish();
		}else if (view == getAplicarButton()){
		
		}
		
	}
	
	public Button getCancelarButton() {
		return cancelarButton;
	}

	public void setCancelarButton(Button cancelarButton) {
		this.cancelarButton = cancelarButton;
	}

	public Button getAplicarButton() {
		return aplicarButton;
	}

	public void setAplicarButton(Button aplicarButton) {
		this.aplicarButton = aplicarButton;
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

	public DetalleComunidad getDetalleComunidad() {
		return detalleComunidad;
	}

	public void setDetalleComunidad(DetalleComunidad detalleComunidad) {
		this.detalleComunidad = detalleComunidad;
	}
	
}

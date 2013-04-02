package com.researchmobile.supervisionpasalo.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

import com.researchmobile.coretel.view.R;

public class SupervisionRespuesta_Tutorial_3 extends Activity {
	
	private LinearLayout idSupervisionRespuestaLinearLayout;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_supervisionpasalo_asignaciones_supervisionrespuesta);
	
		setIdSupervisionRespuestaLinearLayout((LinearLayout)findViewById(R.id.id_supervisionrespuesta_LinearLayout));
	}

	public LinearLayout getIdSupervisionRespuestaLinearLayout() {
		return idSupervisionRespuestaLinearLayout;
	}

	public void setIdSupervisionRespuestaLinearLayout(
			LinearLayout idSupervisionRespuestaLinearLayout) {
		this.idSupervisionRespuestaLinearLayout = idSupervisionRespuestaLinearLayout;
	}
}

package com.researchmobile.supervisionpasalo.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

import com.researchmobile.coretel.view.R;

public class Asignaciones_Tutorial_1 extends Activity{

	private LinearLayout idAsignacionesLinearLayout;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_supervisionpasalo_asignaciones_asignaciones);
	
	setIdAsignacionesLinearLayout((LinearLayout)findViewById(R.id.id_asignaciones_LinearLayout));
	}

	public LinearLayout getIdAsignacionesLinearLayout() {
		return idAsignacionesLinearLayout;
	}

	public void setIdAsignacionesLinearLayout(
			LinearLayout idAsignacionesLinearLayout) {
		this.idAsignacionesLinearLayout = idAsignacionesLinearLayout;
	}


}

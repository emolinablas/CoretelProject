package com.researchmobile.supervisionpasalo.tutorial;

import android.app.Activity;
import android.os.Bundle;

import com.researchmobile.coretel.view.R;
import android.widget.*;
public class SupervisionEvento_Tutorial_2 extends Activity {

	private LinearLayout idSupervisionEventoLinearLayout;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_supervisionpasalo_asignaciones_supervisionevento);
	
		setIdSupervisionEventoLinearLayout((LinearLayout)findViewById(R.id.id_supervisionevento_LinearLayout));
	
	}

	public LinearLayout getIdSupervisionEventoLinearLayout() {
		return idSupervisionEventoLinearLayout;
	}

	public void setIdSupervisionEventoLinearLayout(
			LinearLayout idSupervisionEventoLinearLayout) {
		this.idSupervisionEventoLinearLayout = idSupervisionEventoLinearLayout;
	}
}

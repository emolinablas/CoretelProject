package com.researchmobile.coretel.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class OpcionTipoEvento extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.opciontipoevento);
	}
	
}

package com.researchmobile.supervisionpasalo.tutorial;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class Asignaciones_Tutorial_1 extends Activity implements OnClickListener{

	 LinearLayout idAsignacionesLinearLayout;
	 TextView mensaje;
	 int pagina = 1;
	 
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_supervisionpasalo_asignaciones_asignaciones);
	
	setIdAsignacionesLinearLayout((LinearLayout)findViewById(R.id.id_asignaciones_LinearLayout));
	setMensaje((TextView)findViewById(R.id.tutorial_mensaje_asignaciones_textview));
	getIdAsignacionesLinearLayout().setOnClickListener(this);

	}

	public TextView getMensaje() {
		return mensaje;
	}

	public void setMensaje(TextView mensaje) {
		this.mensaje = mensaje;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public LinearLayout getIdAsignacionesLinearLayout() {
		return idAsignacionesLinearLayout;
	}

	public void setIdAsignacionesLinearLayout(
			LinearLayout idAsignacionesLinearLayout) {
		this.idAsignacionesLinearLayout = idAsignacionesLinearLayout;
	}

		public void onClick(View view) {
			if(view == getIdAsignacionesLinearLayout()){
				switch(pagina){
				
				case 1: idAsignacionesLinearLayout.setBackgroundResource(R.drawable.tutorial_supervisionpasalo_asignaciones_shape);
						mensaje.setText(R.string.TutorialMensaje1Asignaciones);
						pagina ++;
						break;
				case 2: finish();
						break;
				}
			}
	}


}

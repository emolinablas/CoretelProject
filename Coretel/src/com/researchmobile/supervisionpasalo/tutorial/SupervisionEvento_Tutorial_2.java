package com.researchmobile.supervisionpasalo.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;
public class SupervisionEvento_Tutorial_2 extends Activity implements OnClickListener {

	LinearLayout idSupervisionEventoLinearLayout;
	TextView mensaje;
	int pagina = 1;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_supervisionpasalo_asignaciones_supervisionevento);
	
		setIdSupervisionEventoLinearLayout((LinearLayout)findViewById(R.id.id_supervisionevento_LinearLayout));
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_supervisionevento_textview));
		getIdSupervisionEventoLinearLayout().setOnClickListener(this);
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

	public LinearLayout getIdSupervisionEventoLinearLayout() {
		return idSupervisionEventoLinearLayout;
	}

	public void setIdSupervisionEventoLinearLayout(
			LinearLayout idSupervisionEventoLinearLayout) {
		this.idSupervisionEventoLinearLayout = idSupervisionEventoLinearLayout;
	}

	@Override
	public void onClick(View view) {
		if(view == getIdSupervisionEventoLinearLayout()){
			switch(pagina){
			
			case 1:		idSupervisionEventoLinearLayout.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_left_margin);
						mensaje.setText(R.string.TutorialMensaje1SupervisionEvento);
						pagina++;
						break;
			
			case 2:		idSupervisionEventoLinearLayout.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
						mensaje.setText(R.string.TutorialMensaje2SupervisionEvento);
						pagina++;
						break;
						
			case 3:		finish();
						break;
			
		}
	}
}
}

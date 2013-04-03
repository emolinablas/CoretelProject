package com.researchmobile.supervisionpasalo.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class SupervisionRespuesta_Tutorial_3 extends Activity implements OnClickListener {
	
	LinearLayout idSupervisionRespuestaLinearLayout;
	TextView mensaje;
	int pagina = 1;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_supervisionpasalo_asignaciones_supervisionrespuesta);
	
		setIdSupervisionRespuestaLinearLayout((LinearLayout)findViewById(R.id.id_supervisionrespuesta_LinearLayout));
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_supervisionrespuesta_textview));
		getIdSupervisionRespuestaLinearLayout().setOnClickListener(this);
	}

	public TextView getMensaje() {
		return mensaje;
	}

	public void setMensaje(TextView mensaje) {
		this.mensaje = mensaje;
	}

	public LinearLayout getIdSupervisionRespuestaLinearLayout() {
		return idSupervisionRespuestaLinearLayout;
	}

	public void setIdSupervisionRespuestaLinearLayout(
			LinearLayout idSupervisionRespuestaLinearLayout) {
		this.idSupervisionRespuestaLinearLayout = idSupervisionRespuestaLinearLayout;
	}

	@Override
	public void onClick(View view) {
		if(view == getIdSupervisionRespuestaLinearLayout()){
			switch(pagina){
			case 1:		idSupervisionRespuestaLinearLayout.setBackgroundResource(R.drawable.tutorial_supervisionpasalo_supervisonrespuesta_shape2);
						mensaje.setText(R.string.TutorialMensaje1SupervisionRespuesta);
						pagina++;
						break;

			case 2:		idSupervisionRespuestaLinearLayout.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
						mensaje.setText(R.string.TutorialMensaje2SupervisionRespuesta);
						pagina++;
						break;
						
			case 3:		idSupervisionRespuestaLinearLayout.setBackgroundResource(R.drawable.tutorial_supervisionpasalo_supervisionevento_shape1);
						mensaje.setText(R.string.TutorialMensaje3SupervisionRespuesta);
						pagina++;
						break;
						
			case 4:		idSupervisionRespuestaLinearLayout.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_right_maring);
						mensaje.setText(R.string.TutorialMensaje4SupervisionRespuesta);
						pagina++;
						break;			
			
			case 5:		finish();
			break;


			}
		}
	}
}

package com.researchmobile.coretel.tutorial.pasalo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class TipoEvento_tutorial_2 extends Activity implements OnClickListener {
	
	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	int pagina = 1;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_comunidades_creacomunidad);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_comunidades));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_comunidades));
	}

	@Override
	public void onClick(View view) {
		if (view == getTutorialBackgroud()) {
			switch (pagina) {
			case 1:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
				mensaje.setText(R.string.tutorial_nuevotipoevento_cancelar);
				pagina++;
				break;
			case 2:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
				mensaje.setText(R.string.tutorial_nuevotipoevento_aplicar);
				pagina++;
				break;
			case 3:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_tipoevento_nombre);
				mensaje.setText(R.string.tutorial_nuevotipoevento_nombre);
				pagina++;
				break;
			case 4:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_tipoevento_descripcion);
				mensaje.setText(R.string.tutorial_nuevotipoevento_descripcion);
				pagina++;
				break;
			case 5:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_tipoevento_icono);
				mensaje.setText(R.string.tutorial_nuevotipoevento_icono);
				pagina++;
				break;
			case 6:
				finish();
				break;
			}
		}
		
	}

	public LinearLayout getTutorialBackgroud() {
		return tutorialBackgroud;
	}

	public void setTutorialBackgroud(LinearLayout tutorialBackgroud) {
		this.tutorialBackgroud = tutorialBackgroud;
	}

	public TextView getMensaje() {
		return mensaje;
	}

	public void setMensaje(TextView mensaje) {
		this.mensaje = mensaje;
	}
	
	
}

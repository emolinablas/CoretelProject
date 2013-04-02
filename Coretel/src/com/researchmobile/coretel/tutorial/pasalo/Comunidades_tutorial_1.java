package com.researchmobile.coretel.tutorial.pasalo;

import com.researchmobile.coretel.view.Comunidades;
import com.researchmobile.coretel.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Comunidades_tutorial_1 extends Activity implements OnClickListener {
	
	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	int pagina = 1;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_comunidades_comunidades);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_comunidades));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_comunidades));
		}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == getTutorialBackgroud()){
			switch(pagina){
		
			case 1:		tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
						mensaje.setText(R.string.tutorial_agregar_comunidad);
						pagina++;
						break;
			case 2: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
						mensaje.setText(R.string.tutorial_menu);
						pagina++;
						break;
			case 4:	//Intent intent = new Intent(Comunidades_tutorial_1.this, Comunidades.class);
						finish();
						break;
			case 3: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_right_maring);
						mensaje.setText(R.string.tutorial_explorar_comunidades);
						pagina++;
						break;
					
		}
		}
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Preventing default implementation previous to
			// android.os.Build.VERSION_CODES.ECLAIR
			return true;
		}

		return super.onKeyDown(keyCode, event);
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
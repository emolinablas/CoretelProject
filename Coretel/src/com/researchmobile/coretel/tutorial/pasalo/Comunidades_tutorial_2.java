package com.researchmobile.coretel.tutorial.pasalo;

import com.researchmobile.coretel.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Comunidades_tutorial_2  extends Activity implements OnClickListener {

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
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin1);
				mensaje.setText(R.string.tutorial_nuevacomunidad_nombrecomunidad);
				pagina++;
				break;
			case 2:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin2);
				mensaje.setText(R.string.tutorial_nuevacomunidad_descripcioncomunidad);
				pagina++;
				break;
			case 3:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin3);
				mensaje.setText(R.string.tutorial_nuevacomunidad_espublica);
				pagina++;
				break;
			case 4: 
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin4);
				mensaje.setText(R.string.tutorial_nuevacomunidad_esreasignable);
				pagina++;
				break;
			case 5:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
				mensaje.setText(R.string.tutorial_nuevacomunidad_aplicarcomunidad);
				pagina++;
				break;
			case 6:
				tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
				mensaje.setText(R.string.tutorial_nuevacomunidad_cancelarcomunidad);
				pagina++;
				break;
			case 7:
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

package com.researchmobile.coretel.tutorial.pasalo;

import com.researchmobile.coretel.view.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Mapa_tutorial_1 extends Activity implements OnClickListener {

	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	int pagina = 1;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_mapa_mapa);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_mapa));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_mapa));
		}
	
	// El siguiente metodo permite llevar el seguimiento del tutorial, seteando el gradiente para cada paso del tutorial ademas del mensaje.
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == getTutorialBackgroud()){
			switch(pagina){
		
			case 1:		tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left); //utilizo el gradiente arriba/izquierda
						mensaje.setText(R.string.tutorial_menu);
						pagina++;
						break;
			case 2: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_right); // utilizo el gradiente abajo/derecha
						mensaje.setText(R.string.tutorial_mapa_accdir);
						pagina++;
						break;
			
			case 3: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_right_maring);
						mensaje.setText(R.string.tutorial_mapa_pluscreaevento);
						pagina++;
						break;
			case 4: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_right_maring_2);
						mensaje.setText(R.string.tutorial_mapa_world);
						pagina++;
						break;
			case 5: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_left_margin);
						mensaje.setText(R.string.tutorial_mapa_cambiarvista);
						pagina++;
						break;
			case 6: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_left);
						mensaje.setText(R.string.tutorial_mapa_actualizaposicion);
						pagina++;
						break;
			case 7: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right_reload);
						mensaje.setText(R.string.tutorial_mapa_recargar);
						pagina++;
						break;
			case 8: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left_filter);
						mensaje.setText(R.string.tutorial_mapa_filtrar);
						pagina++;
						break;


			case 9:		//Intent intent = new Intent(Comunidades_tutorial_1.this, Comunidades.class);
						finish();
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

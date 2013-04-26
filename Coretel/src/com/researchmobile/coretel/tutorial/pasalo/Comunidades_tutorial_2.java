package com.researchmobile.coretel.tutorial.pasalo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class Comunidades_tutorial_2  extends Activity implements OnClickListener {

	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	Button retornoButton;
	int pagina = 0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_comunidades_creacomunidad);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_comunidades));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_comunidades));
		setRetornoButton((Button)findViewById(R.id.tutorial_button_regresar_creacomunidad));
		getRetornoButton().setOnClickListener(this);
		}
	
	private void MostrarView(){
		switch (pagina) {
		case 1:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin1);
			mensaje.setText(R.string.tutorial_nuevacomunidad_nombrecomunidad);
			break;
		case 2:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin2);
			mensaje.setText(R.string.tutorial_nuevacomunidad_descripcioncomunidad);
			break;
		case 3:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin3);
			mensaje.setText(R.string.tutorial_nuevacomunidad_espublica);
			break;
		case 4: 
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_center_margin4);
			mensaje.setText(R.string.tutorial_nuevacomunidad_esreasignable);
			break;
		case 5:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
			mensaje.setText(R.string.tutorial_nuevacomunidad_aplicarcomunidad);
			break;
		case 6:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
			mensaje.setText(R.string.tutorial_nuevacomunidad_cancelarcomunidad);
			break;
		case 7:
			finish();
			break;
		
		}
	}
	public void onClick(View view) {
		if(view == getTutorialBackgroud())
		 {
			pagina++;
			MostrarView();
			if(pagina > 0)
			getRetornoButton().setEnabled(true);				
		 }
		
		else if(pagina == 0)
			{
			getRetornoButton().setEnabled(false);
			}
		else if(view == getRetornoButton())
			{	
		pagina--;
		MostrarView();			
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
	public Button getRetornoButton() {
		return retornoButton;
	}
	public void setRetornoButton(Button retornoButton) {
		this.retornoButton = retornoButton;
	}
	
	

}

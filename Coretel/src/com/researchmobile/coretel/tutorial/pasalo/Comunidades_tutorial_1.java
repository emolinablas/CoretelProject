package com.researchmobile.coretel.tutorial.pasalo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class Comunidades_tutorial_1 extends Activity implements OnClickListener {
	
	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	Button retornoButton;
	Button salirButton;
	int pagina = 0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_comunidades_comunidades);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_comunidades));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_comunidades));
		setRetornoButton((Button)findViewById(R.id.tutorial_button_regresar));
		getRetornoButton().setOnClickListener(this);
		
		//salir button
		setSalirButton((Button)findViewById(R.id.tutorial_button_salir_comunidades));
		getSalirButton().setOnClickListener(this);
	}

	private void MostrarView(){
		switch(pagina){
		
		case 1:		tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
					mensaje.setText(R.string.tutorial_agregar_comunidad);
					break;
		case 2: 	tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
					mensaje.setText(R.string.tutorial_menu);
					break;
		case 3:		tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_bottom_right_maring);
					mensaje.setText(R.string.tutorial_explorar_comunidades);
					break;			
		case 4: 	//Intent intent = new Intent(Comunidades_tutorial_1.this, Comunidades.class);
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
		if(view == getSalirButton())
		{
			finish();
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

	public Button getRetornoButton() {
		return retornoButton;
	}

	public void setRetornoButton(Button retornoButton) {
		this.retornoButton = retornoButton;
	}

	public Button getSalirButton() {
		return salirButton;
	}

	public void setSalirButton(Button salirButton) {
		this.salirButton = salirButton;
	}
	
	
	
	

}
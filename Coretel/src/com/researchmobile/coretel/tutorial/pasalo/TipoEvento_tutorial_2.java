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

public class TipoEvento_tutorial_2 extends Activity implements OnClickListener {
	
	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	Button retornoButton;
	Button salirButton;
	int pagina = 0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_tipoevento2);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_tipoevento2));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_tipoevento2));
		setRetornoButton((Button)findViewById(R.id.tutorial_button_regresar_tipoevento2));
		getRetornoButton().setOnClickListener(this);
	
		//salir button
		setSalirButton((Button)findViewById(R.id.tutorial_button_salir_tipoevento2));
		getSalirButton().setOnClickListener(this);
	
	}

	private void MostrarView(){
		switch (pagina) {
		case 0:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_inicial);
			mensaje.setText(R.string.mensajeAyuda);
			break;
		case 1:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_left);
			mensaje.setText(R.string.tutorial_nuevotipoevento_cancelar);
			break;
		case 2:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
			mensaje.setText(R.string.tutorial_nuevotipoevento_aplicar);
			break;
		case 3:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_tipoevento_nombre);
			mensaje.setText(R.string.tutorial_nuevotipoevento_nombre);
			break;
		case 4:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_tipoevento_descripcion);
			mensaje.setText(R.string.tutorial_nuevotipoevento_descripcion);
			break;
		case 5:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_tipoevento_icono);
			mensaje.setText(R.string.tutorial_nuevotipoevento_icono);
			break;
		case 6:
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

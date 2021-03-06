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

public class TipoEvento_tutorial_1 extends Activity implements OnClickListener {
	
	LinearLayout tutorialBackgroud;
	TextView	mensaje;
	TextView indicaInicio;
	Button RetornoButton;
	Button salirButton;
	int pagina = 0;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tutorial_pasalo_tipoevento);
		setTutorialBackgroud((LinearLayout)findViewById(R.id.tutorial_layout_tipoevento));
		getTutorialBackgroud().setOnClickListener(this);
		setMensaje((TextView)findViewById(R.id.tutorial_mensaje_tipoevento));
		setIndicaInicio((TextView)findViewById(R.id.inicio_tutorial_textview));
		setRetornoButton((Button)findViewById(R.id.tutorial_button_regresar_tipoevento));
		getRetornoButton().setOnClickListener(this);
		
		//salir button
		setSalirButton((Button)findViewById(R.id.tutorial_button_salir_tipoevento1));
		getSalirButton().setOnClickListener(this);
	}
	
	private void MostrarView(){
		switch (pagina) {
		case 0:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_inicial);
			mensaje.setText(R.string.mensajeAyuda);
			break;
		case 1:
			tutorialBackgroud.setBackgroundResource(R.drawable.tutorial_pasalo_shape_top_right);
			mensaje.setText(R.string.tutorial_tipoevento_agregar);
			break;
		case 2:
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
			getIndicaInicio().setVisibility(View.INVISIBLE);
		 }
		
		else if(pagina == 0)
			{
			getRetornoButton().setEnabled(false);
			getIndicaInicio().setVisibility(View.VISIBLE);
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
		return RetornoButton;
	}

	public void setRetornoButton(Button retornoButton) {
		RetornoButton = retornoButton;
	}

	public Button getSalirButton() {
		return salirButton;
	}

	public void setSalirButton(Button salirButton) {
		this.salirButton = salirButton;
	}

	public TextView getIndicaInicio() {
		return indicaInicio;
	}

	public void setIndicaInicio(TextView indicaInicio) {
		this.indicaInicio = indicaInicio;
	}
	
	
}

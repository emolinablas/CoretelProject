package com.researchmobile.supervisionpasalo.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.view.R;

public class SupervisionEvento extends Activity {

	private TextView fechaTextView;
	private TextView ubicacionTextView;
	private TextView tipoTextView;
	private TextView descripcionTextView;
	private TextView comunidadTextView;
	private ImageView eventofotoImageView;
	private Button responderButton;
	private Button regresarButton;

		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.supervision_evento);
			Bundle bundle = getIntent().getExtras();
			
			setFechaTextView((TextView)findViewById(R.id.evento_fechaTextView));
			setUbicacionTextView((TextView)findViewById(R.id.evento_ubicacionTextView));
			setTipoTextView((TextView)findViewById(R.id.evento_tipoTextView));
			setDescripcionTextView((TextView)findViewById(R.id.evento_descripcionTextView));
			setComunidadTextView((TextView)findViewById(R.id.evento_comunidadTextView));
			setResponderButton((Button)findViewById(R.id.evento_responderbutton));
			setRegresarButton((Button)findViewById(R.id.evento_regresarButton));
			setEventoFotoImageView((ImageView)findViewById(R.id.evento_fotoImageView));
			
			
			
		}
		
		public ImageView getEventoFotoImageView(){
			return eventofotoImageView;
		}
		
		public void setEventoFotoImageView(ImageView EventoFotoImageView){
			this.eventofotoImageView = eventofotoImageView;
		}

		public TextView getFechaTextView() {
			return fechaTextView;
		}

		public void setFechaTextView(TextView fechaTextView) {
			this.fechaTextView = fechaTextView;
		}

		public TextView getUbicacionTextView() {
			return ubicacionTextView;
		}

		public void setUbicacionTextView(TextView ubicacionTextView) {
			this.ubicacionTextView = ubicacionTextView;
		}

		public TextView getTipoTextView() {
			return tipoTextView;
		}

		public void setTipoTextView(TextView tipoTextView) {
			this.tipoTextView = tipoTextView;
		}

		public TextView getDescripcionTextView() {
			return descripcionTextView;
		}

		public void setDescripcionTextView(TextView descripcionTextView) {
			this.descripcionTextView = descripcionTextView;
		}

		public TextView getComunidadTextView() {
			return comunidadTextView;
		}

		public void setComunidadTextView(TextView comunidadTextView) {
			this.comunidadTextView = comunidadTextView;
		}

		public ImageView getEventofotoImageView() {
			return eventofotoImageView;
		}

		public void setEventofotoImageView(ImageView eventofotoImageView) {
			this.eventofotoImageView = eventofotoImageView;
		}

		public Button getResponderButton() {
			return responderButton;
		}

		public void setResponderButton(Button responderButton) {
			this.responderButton = responderButton;
		}

		public Button getRegresarButton() {
			return regresarButton;
		}

		public void setRegresarButton(Button regresarButton) {
			this.regresarButton = regresarButton;
		}
	
}

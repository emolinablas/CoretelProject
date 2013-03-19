package com.researchmobile.supervisionpasalo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.view.R;

public class SupervisionEvento extends Activity implements OnClickListener {

	private TextView fechaTextView;
	private TextView ubicacionTextView;
	private TextView tipoTextView;
	private TextView descripcionTextView;
	private TextView comunidadTextView;
	private ImageView eventofotoImageView;
	private Button responderButton;
	private Button regresarButton;
	
	private TokenizerUtility tokenizer = new TokenizerUtility();
	
		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.supervision_evento);
			Bundle bundle = getIntent().getExtras();
			String fecha = bundle.getString("titulo");
			String descripcion = bundle.getString("descripcion");
			String latitud = bundle.getString("latitud");
			String longitud = bundle.getString("longitud");
			Log.v("coretel", "titulo = " + fecha);
			Log.v("coretel", "descripcion = " + descripcion);
			/*
			String tipo = bundle.getString("tipo");
			String comunidad = bundle.getString("comunidad");
			*/
			setFechaTextView((TextView)findViewById(R.id.evento_fechaTextView));
			setUbicacionTextView((TextView)findViewById(R.id.evento_ubicacionTextView));
			setTipoTextView((TextView)findViewById(R.id.evento_tipoTextView));
			setDescripcionTextView((TextView)findViewById(R.id.evento_descripcionTextView));
			setComunidadTextView((TextView)findViewById(R.id.evento_comunidadTextView));
			setResponderButton((Button)findViewById(R.id.evento_responderbutton));
			setRegresarButton((Button)findViewById(R.id.evento_regresarButton));
			setEventoFotoImageView((ImageView)findViewById(R.id.evento_fotoImageView));
			
			getResponderButton().setOnClickListener(this);
			getRegresarButton().setOnClickListener(this);
			
			getUbicacionTextView().setText(latitud + " , " + longitud);
			/*
			getTipoTextView().setText(tipo);
			getComunidadTextView().setText(comunidad);
			*/
			
			
			//utilizando tokenizer
			getFechaTextView().setText(tokenizer.fechaRegistro(descripcion));
			getDescripcionTextView().setText(tokenizer.descripcion(descripcion));
		}
		
			public void onClick(View view){
				if(view ==getResponderButton())
				{
					Responder();
				}else if(view == getRegresarButton()){
					Retorno();
				}
			}
		
		
		private void Responder() {
			Intent intent = new Intent(SupervisionEvento.this, SupervisionRespuesta.class);
			startActivity(intent);
			}
		
		private void Retorno(){
			Intent intent = new Intent(SupervisionEvento.this, MapaSupervision.class );
			startActivity(intent);
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

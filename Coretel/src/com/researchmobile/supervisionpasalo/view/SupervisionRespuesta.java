package com.researchmobile.supervisionpasalo.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.researchmobile.coretel.view.R;

public class SupervisionRespuesta extends Activity implements OnClickListener {

	private EditText respuestaEditText;
	private ImageView fotoImageView;
	private Button capturarButton;
	private Button guardarButton;
	private RadioButton pendienteRadioButton;
	private RadioButton corregidoRadioButton;
	private RadioButton irreparableRadioButton;
	private RadioButton cerrarRadioButton;

		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.supervision_respuesta);
			Bundle bundle = getIntent().getExtras();
			
			setRespuestaEditText((EditText)findViewById(R.id.supervisionrespuesta_respuestaEditText));
			setFotoImageView((ImageView)findViewById(R.id.supervisionrespuesta_foto_imageview));
			setCapturarButton((Button)findViewById(R.id.supervisionrespuesta_capturar_button));
			setGuardarButton((Button)findViewById(R.id.supervisionrespuesta_guardarbutton));
			setPendienteRadioButton((RadioButton)findViewById(R.id.supervisionrespuesta_pendiente));
			setCorregidoRadioButton((RadioButton)findViewById(R.id.supervisionrespuesta_corregido));
			setIrreparableRadioButton((RadioButton)findViewById(R.id.supervisionrespuesta_irreparable));
			setCerrarRadioButton((RadioButton)findViewById(R.id.supervisionrespuesta_cerrar));
			
			getCapturarButton().setOnClickListener(this);
		}

		
		
		public Button getGuardarButton() {
			return guardarButton;
		}

		public void setGuardarButton(Button guardarButton) {
			this.guardarButton = guardarButton;
		}



		public RadioButton getCorregidoRadioButton() {
			return corregidoRadioButton;
		}
		
		public void setCorregidoRadioButton(RadioButton corregidoRadioButton) {
			this.corregidoRadioButton = corregidoRadioButton;
		}

		public RadioButton getIrreparableRadioButton() {
			return irreparableRadioButton;
		}


		public RadioButton getPendienteRadioButton() {
			return pendienteRadioButton;
		}



		public void setPendienteRadioButton(RadioButton pendienteRadioButton) {
			this.pendienteRadioButton = pendienteRadioButton;
		}



		public void setIrreparableRadioButton(RadioButton irreparableRadioButton) {
			this.irreparableRadioButton = irreparableRadioButton;
		}


		public RadioButton getCerrarRadioButton() {
			return cerrarRadioButton;
		}



		public void setCerrarRadioButton(RadioButton cerrarRadioButton) {
			this.cerrarRadioButton = cerrarRadioButton;
		}



		public EditText getRespuestaEditText() {
			return respuestaEditText;
		}

		public void setRespuestaEditText(EditText respuestaEditText) {
			this.respuestaEditText = respuestaEditText;
		}

		public ImageView getFotoImageView() {
			return fotoImageView;
		}

		public void setFotoImageView(ImageView fotoImageView) {
			this.fotoImageView = fotoImageView;
		}

		public Button getCapturarButton() {
			return capturarButton;
		}

		public void setCapturarButton(Button capturarButton) {
			this.capturarButton = capturarButton;
		}



		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}

}

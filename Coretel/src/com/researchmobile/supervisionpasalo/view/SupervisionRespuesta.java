package com.researchmobile.supervisionpasalo.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.researchmobile.coretel.view.R;

public class SupervisionRespuesta extends Activity {

	private EditText respuestaEditText;
	private Spinner comunidadSpinner;
	private ImageView fotoImageView;
	private Button capturarButton;

		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.supervision_respuesta);
			Bundle bundle = getIntent().getExtras();
			
			setRespuestaEditText((EditText)findViewById(R.id.respuesta_respuestaEditText));
			setComunidadSpinner((Spinner)findViewById(R.id.respuesta_comunidad_spinner));
			setFotoImageView((ImageView)findViewById(R.id.respuesta_foto_imageview));
			setCapturarButton((Button)findViewById(R.id.respuesta_capturar_button));
		}

		public EditText getRespuestaEditText() {
			return respuestaEditText;
		}

		public void setRespuestaEditText(EditText respuestaEditText) {
			this.respuestaEditText = respuestaEditText;
		}

		public Spinner getComunidadSpinner() {
			return comunidadSpinner;
		}

		public void setComunidadSpinner(Spinner comunidadSpinner) {
			this.comunidadSpinner = comunidadSpinner;
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

}

package com.researchmobile.supervisionpasalo.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.researchmobile.coretel.view.Comunidades;
import com.researchmobile.coretel.view.Mapa;
import com.researchmobile.coretel.view.R;

public class SupervisionRespuesta extends Activity implements OnClickListener {

	private EditText respuestaEditText;
	private ImageView fotoImageView;
	private Button capturarButton;
	private Button guardarButton;
	private String descripcion;
	private Button estadoButton;

		protected void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.supervision_respuesta);
			Bundle bundle = getIntent().getExtras();
			setDescripcion(bundle.getString("descripcion"));
			setRespuestaEditText((EditText)findViewById(R.id.supervisionrespuesta_respuestaEditText));
			setFotoImageView((ImageView)findViewById(R.id.supervisionrespuesta_foto_imageview));
			setCapturarButton((Button)findViewById(R.id.supervisionrespuesta_capturar_button));
			setGuardarButton((Button)findViewById(R.id.supervisionrespuesta_guardarbutton));
			setEstadoButton((Button)findViewById(R.id.supervision_respuesta_estado));
			getEstadoButton().setOnClickListener(this);
			getCapturarButton().setOnClickListener(this);
		}
		
		@Override
		public void onClick(View v) {
			dialogEstados(this);
			
		}
		
		public void dialogEstados (final Context activity) {

	        final Dialog myDialog = new Dialog(activity);
	        myDialog.setContentView(R.layout.respuestas_supervision);
	        myDialog.setTitle( "Opciones");
	        myDialog.setCancelable( false);
	       
	        Button corregido = (Button) myDialog.findViewById(R.id.respuestas_corregido);
	        corregido.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });
	        
	        Button irreparable = (Button) myDialog.findViewById(R.id.respuestas_irreparable);
	        irreparable.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });
	        
	        Button corregidoReasignado = (Button) myDialog.findViewById(R.id.respuestas_corregido_re);
	        corregidoReasignado.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });
	        
	        Button irreparableReasignado = (Button) myDialog.findViewById(R.id.respuestas_irreparable_re);
	        irreparableReasignado.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });

	        Button cerrar = (Button) myDialog.findViewById(R.id.respuestas_cerrar);
	        cerrar.setOnClickListener( new OnClickListener() {
	            public void onClick(View v) {
	                myDialog.dismiss();
	            }
	        });

	        myDialog.show();

	    }


		public Button getGuardarButton() {
			return guardarButton;
		}

		public void setGuardarButton(Button guardarButton) {
			this.guardarButton = guardarButton;
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

		public String getDescripcion() {
			return descripcion;
		}



		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Button getEstadoButton() {
			return estadoButton;
		}

		public void setEstadoButton(Button estadoButton) {
			this.estadoButton = estadoButton;
		}
		
}

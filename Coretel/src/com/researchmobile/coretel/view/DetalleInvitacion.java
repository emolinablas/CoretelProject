package com.researchmobile.coretel.view;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.researchmobile.coretel.entity.Invitacion;

public class DetalleInvitacion extends Activity implements OnClickListener{
	
	private TextView estadoTextView;
	private TextView invitoTextView;
	private TextView comunidadTextView;
	private TextView fechaTextView;
	private TextView emailTextView;
	private Button invitacionesButton;
	private Invitacion invitacion;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detalleinvitacion);
		Bundle bundle = (Bundle)getIntent().getExtras();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> invitacion = (HashMap<String, Object>)bundle.get("invitacion");
//		setInvitacion((Invitacion)bundle.get("invitacion"));
		
		setEstadoTextView((TextView)findViewById(R.id.detalleinvitacion_estado_textview));
		setInvitoTextView((TextView)findViewById(R.id.detalleinvitacion_invito_textview));
		setComunidadTextView((TextView)findViewById(R.id.detalleinvitacion_comunidad_textview));
		setFechaTextView((TextView)findViewById(R.id.detalleinvitacion_fecha_textview));
		setEmailTextView((TextView)findViewById(R.id.detalleinvitacion_email_textview));
		setInvitacionesButton((Button)findViewById(R.id.detalleinvitacion_invitaciones_button));
		getInvitacionesButton().setOnClickListener(this);
		
		String estado = (String)invitacion.get("estado");
		if (estado.equalsIgnoreCase("1")){
			estado = "Aceptada";
		}else if(estado.equalsIgnoreCase("2")){
			estado = "Rechazada";
		}else if(estado.equalsIgnoreCase("3")){
			estado = "Pendiente";
		}
		estadoTextView.setText(estado);
		invitoTextView.setText((String)invitacion.get("usuario"));
		comunidadTextView.setText((String)invitacion.get("comunidad"));
		fechaTextView.setText((String)invitacion.get("fecha"));
		emailTextView.setText((String)invitacion.get("email"));
	}
	
	@Override
	public void onClick(View view) {
		if (view == getInvitacionesButton()){
			finish();
		}
		
	}

	public TextView getEstadoTextView() {
		return estadoTextView;
	}

	public void setEstadoTextView(TextView estadoTextView) {
		this.estadoTextView = estadoTextView;
	}

	public TextView getInvitoTextView() {
		return invitoTextView;
	}

	public void setInvitoTextView(TextView invitoTextView) {
		this.invitoTextView = invitoTextView;
	}

	public TextView getComunidadTextView() {
		return comunidadTextView;
	}

	public void setComunidadTextView(TextView comunidadTextView) {
		this.comunidadTextView = comunidadTextView;
	}

	public TextView getFechaTextView() {
		return fechaTextView;
	}

	public void setFechaTextView(TextView fechaTextView) {
		this.fechaTextView = fechaTextView;
	}

	public TextView getEmailTextView() {
		return emailTextView;
	}

	public void setEmailTextView(TextView emailTextView) {
		this.emailTextView = emailTextView;
	}

	public Button getInvitacionesButton() {
		return invitacionesButton;
	}

	public void setInvitacionesButton(Button invitacionesButton) {
		this.invitacionesButton = invitacionesButton;
	}

	public void setInvitacion(Invitacion invitacion) {
		this.invitacion = invitacion;
	}

	public Invitacion getInvitacion() {
		return invitacion;
	}

}

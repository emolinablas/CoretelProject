package com.researchmobile.coretel.view;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class DetalleInvitacion extends Activity implements OnClickListener{
	
	private TextView invitoTextView;
	private TextView comunidadTextView;
	private TextView fechaTextView;
	private TextView emailTextView;
	private Button invitacionesButton;
	private Button aceptarButton;
	private Button rechazarButton;
	private ProgressDialog pd = null;
	
	private String respuesta;
	private String idInvitacion;
	private RespuestaWS respuestaRespondidoWS;
	private RequestWS resquestWS;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detalleinvitacion);
		Bundle bundle = (Bundle)getIntent().getExtras();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> invitacion = (HashMap<String, Object>)bundle.get("invitacion");
		setIdInvitacion((String)invitacion.get("idInvitacion"));
		
		setResquestWS(new RequestWS());
		setInvitoTextView((TextView)findViewById(R.id.detalleinvitacion_invito_textview));
		setComunidadTextView((TextView)findViewById(R.id.detalleinvitacion_comunidad_textview));
		setFechaTextView((TextView)findViewById(R.id.detalleinvitacion_fecha_textview));
		setEmailTextView((TextView)findViewById(R.id.detalleinvitacion_email_textview));
		setInvitacionesButton((Button)findViewById(R.id.detalleinvitacion_invitaciones_button));
		setAceptarButton((Button)findViewById(R.id.detalleinvitacion_aceptar_button));
		setRechazarButton((Button)findViewById(R.id.detalleinvitacion_rechazar_button));
		getInvitacionesButton().setOnClickListener(this);
		getAceptarButton().setOnClickListener(this);
		getRechazarButton().setOnClickListener(this);
		
		String estado = (String)invitacion.get("estado");
		if (estado.equalsIgnoreCase("1")){
			estado = "Aceptada";
			getAceptarButton().setVisibility(View.GONE);
			getRechazarButton().setVisibility(View.GONE);
		}else if(estado.equalsIgnoreCase("2")){
			estado = "Rechazada";
			getAceptarButton().setVisibility(View.GONE);
			getRechazarButton().setVisibility(View.GONE);
		}else if(estado.equalsIgnoreCase("0")){
			estado = "Pendiente";
		}
		invitoTextView.setText((String)invitacion.get("usuario"));
		comunidadTextView.setText((String)invitacion.get("comunidad"));
		fechaTextView.setText((String)invitacion.get("fecha"));
		emailTextView.setText((String)invitacion.get("email"));
	}
	
	@Override
	public void onClick(View view) {
		ConnectState conect = new ConnectState();
		if (view == getInvitacionesButton()){
			finish();
		}else if (view == getAceptarButton()){
			setRespuesta("1");
			if(conect.isConnectedToInternet(this))
			{
            new enviarRespuestaAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}else if (view == getRechazarButton()){
			setRespuesta("2");
			if(conect.isConnectedToInternet(this))
			{
            new enviarRespuestaAsync().execute("");
		}else{
			Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
		}
		}
		
	}
	
	// Clase para ejecutar en Background
    class enviarRespuestaAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(DetalleInvitacion.this, "RESPONDIENDO", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
               	 setRespuestaRespondidoWS(enviarRespuesta());

               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (getRespuestaRespondidoWS() != null && getRespuestaRespondidoWS().isResultado()){
               	 Intent intent = new Intent(DetalleInvitacion.this, Invitaciones.class);
               	 startActivity(intent);
                }
         }
   }
    
    public RespuestaWS enviarRespuesta(){
   	 try{
   		 RespuestaWS respuestaWS = new RespuestaWS();
   		 respuestaWS = getResquestWS().enviarRespuestaInvitacion(getIdInvitacion(), getRespuesta());
   		 return respuestaWS;
   	 }catch(Exception exception){
   		 return null;
   	 }
   	
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

	public Button getAceptarButton() {
		return aceptarButton;
	}

	public void setAceptarButton(Button aceptarButton) {
		this.aceptarButton = aceptarButton;
	}

	public Button getRechazarButton() {
		return rechazarButton;
	}

	public void setRechazarButton(Button rechazarButton) {
		this.rechazarButton = rechazarButton;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public RespuestaWS getRespuestaRespondidoWS() {
		return respuestaRespondidoWS;
	}

	public void setRespuestaRespondidoWS(RespuestaWS respuestaRespondidoWS) {
		this.respuestaRespondidoWS = respuestaRespondidoWS;
	}

	public RequestWS getResquestWS() {
		return resquestWS;
	}

	public void setResquestWS(RequestWS resquestWS) {
		this.resquestWS = resquestWS;
	}

	public String getIdInvitacion() {
		return idInvitacion;
	}

	public void setIdInvitacion(String idInvitacion) {
		this.idInvitacion = idInvitacion;
	}
	
	
}

package com.researchmobile.coretel.view;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class DetalleSolicitud extends Activity implements OnClickListener{
	
	private TextView estadoTextView;
	private TextView solicitaTextView;
	private TextView comunidadTextView;
	private Button aceptarButton;
	private Button rechazarButton;
	private Button regresarButton;
	
	private String idSolicitud;
	private String respuesta;
	private RespuestaWS respuestaRespondidoWS;
	private RequestWS resquestWS;
	private ProgressDialog pd = null;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detallesolicitud);
		
		setResquestWS(new RequestWS());
		
		Bundle bundle = (Bundle)getIntent().getExtras();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> solicitud = (HashMap<String, Object>)bundle.get("solicitud");
		setIdSolicitud((String)solicitud.get("idSolicitud"));
		
		setEstadoTextView((TextView)findViewById(R.id.detallesolicitud_estado_textview));
		setSolicitaTextView((TextView)findViewById(R.id.detallesolicitud_solicita_textview));
		setComunidadTextView((TextView)findViewById(R.id.detallesolicitud_comunidad_textview));
		
		setAceptarButton((Button)findViewById(R.id.detallesolicitud_aceptar_button));
		setRechazarButton((Button)findViewById(R.id.detallesolicitud_rechazar_button));
		setRegresarButton((Button)findViewById(R.id.detallesolicitud_invitaciones_button));
		
		getAceptarButton().setOnClickListener(this);
		getRechazarButton().setOnClickListener(this);
		getRegresarButton().setOnClickListener(this);
		
		String estado = (String)solicitud.get("estado");
		if (estado.equalsIgnoreCase("1")){
			estado = "Aceptada";
			getAceptarButton().setVisibility(View.GONE);
			getRechazarButton().setVisibility(View.GONE);
		}else if(estado.equalsIgnoreCase("2")){
			estado = "Rechazada";
			getAceptarButton().setVisibility(View.GONE);
			getRechazarButton().setVisibility(View.GONE);
		}else if (estado.equalsIgnoreCase("0")){
			estado = "No Respondido";
		}
		getEstadoTextView().setText(estado);
		getSolicitaTextView().setText((String)solicitud.get("solicita"));
		getComunidadTextView().setText((String)solicitud.get("comunidad"));
		
		
	}
	
	@Override
	public void onClick(View view) {
		ConnectState conect = new ConnectState();
		
		if (view == getAceptarButton()){
			setRespuesta("1");
			if(conect.isConnectedToInternet(this)){
            new enviarRespuestaAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}else if (view == getRechazarButton()){
			setRespuesta("2");
			if(conect.isConnectedToInternet(this)){
            new enviarRespuestaAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}else if (view == getRegresarButton()){
			finish();
		}
		
	}
	
	// Clase para ejecutar en Background
    class enviarRespuestaAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(DetalleSolicitud.this, "RESPONDIENDO", "ESPERE UN MOMENTO");
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
               	 Intent intent = new Intent(DetalleSolicitud.this, Invitaciones.class);
               	 startActivity(intent);
                }
         }
   }
    
    public RespuestaWS enviarRespuesta(){
      	 try{
      		
      		 RespuestaWS respuestaWS = new RespuestaWS();
      		 respuestaWS = getResquestWS().enviarRespuestaSolicitud(getIdSolicitud(), getRespuesta());
      		Log.v("pio", "responder solicidud 4");
      		 return respuestaWS;
      	 }catch(Exception exception){
      		 return null;
      	 }
      	
       }

	public TextView getEstadoTextView() {
		return estadoTextView;
	}

	public void setEstadoTextView(TextView estadoTextView) {
		this.estadoTextView = estadoTextView;
	}

	public TextView getSolicitaTextView() {
		return solicitaTextView;
	}

	public void setSolicitaTextView(TextView solicitaTextView) {
		this.solicitaTextView = solicitaTextView;
	}

	public TextView getComunidadTextView() {
		return comunidadTextView;
	}

	public void setComunidadTextView(TextView comunidadTextView) {
		this.comunidadTextView = comunidadTextView;
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

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Button getRegresarButton() {
		return regresarButton;
	}

	public void setRegresarButton(Button regresarButton) {
		this.regresarButton = regresarButton;
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
	
	

}

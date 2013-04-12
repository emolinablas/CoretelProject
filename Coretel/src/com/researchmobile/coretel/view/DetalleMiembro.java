package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.Miembro;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.ws.RequestWS;

public class DetalleMiembro extends Activity implements OnClickListener{

	private TextView nombreTextView;
	private TextView telefonoTextView;
	private TextView emailTextView;
	private Miembro miembro;
	private LinearLayout borrarButton;
	private Button comunidadesButton;
	private ProgressDialog pd = null;
	private RequestWS request = new RequestWS();
	private RespuestaWS respuesta = new RespuestaWS();
	private boolean esDuenno;
	private String idComunidad;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detallemiembro);
	
		Bundle bundle = getIntent().getExtras();
		setMiembro((Miembro)bundle.get("miembro"));
		setEsDuenno((boolean)bundle.getBoolean("esDuenno"));
		setIdComunidad(bundle.getString("idComunidad"));
		
		setNombreTextView((TextView)findViewById(R.id.detallemiembros_nombre_edittext));
		setTelefonoTextView((TextView)findViewById(R.id.detallemiembro_telefono_edittext));
		setEmailTextView((TextView)findViewById(R.id.detallemiembro_email_edittext));
		setBorrarButton((LinearLayout)findViewById(R.id.detallemiembro_borrar_button));
		setComunidadesButton((Button)findViewById(R.id.detallemiembros_comunidades_button));
		getBorrarButton().setOnClickListener(this);
		getComunidadesButton().setOnClickListener(this);
		
		getNombreTextView().setText(getMiembro().getNombreUsuario());
		getTelefonoTextView().setText(getMiembro().getTelefono());
		getEmailTextView().setText(getMiembro().getEmail());
		
		if(isEsDuenno()){
			Log.v("pio", "El usuario no es dueño de la comunidad, no puede borrar el miembro");
		}else{
			getBorrarButton().setVisibility(View.GONE);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		ConnectState conect = new ConnectState();
		if (v == getComunidadesButton()){
			finish();
		}else if (v == getBorrarButton()){
			if(conect.isConnectedToInternet(this))
			{
			new eliminarAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public void dialogBorrar(){
		new AlertDialog.Builder(this)
        .setTitle("Borrar Miembro")
        .setMessage("Esta seguro de borrar el miembro de la comunidad")
        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     
                }
        })
        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     
                }
        })
        .show();
    }
	
	// Clase para ejecutar en Background
	class eliminarAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(DetalleMiembro.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				borrarMiembro();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			if (respuesta != null){
				Toast.makeText(getBaseContext(), respuesta.getMensaje(), Toast.LENGTH_SHORT).show();
				if (respuesta.isResultado()){
					Intent intent = new Intent(DetalleMiembro.this, PruebaListaFoto.class);
					intent.putExtra("idComunidad", getIdComunidad());
					startActivity(intent);
				}
			}

		}
	}
	
	public void borrarMiembro(){
		respuesta = request.eliminarMiembro(getMiembro());
	}

	public Miembro getMiembro() {
		return miembro;
	}


	public void setMiembro(Miembro miembro) {
		this.miembro = miembro;
	}

	public TextView getNombreTextView() {
		return nombreTextView;
	}

	public void setNombreTextView(TextView nombreTextView) {
		this.nombreTextView = nombreTextView;
	}

	public TextView getTelefonoTextView() {
		return telefonoTextView;
	}

	public void setTelefonoTextView(TextView telefonoTextView) {
		this.telefonoTextView = telefonoTextView;
	}

	public TextView getEmailTextView() {
		return emailTextView;
	}

	public void setEmailTextView(TextView emailTextView) {
		this.emailTextView = emailTextView;
	}

	public LinearLayout getBorrarButton() {
		return borrarButton;
	}

	public void setBorrarButton(LinearLayout borrarButton) {
		this.borrarButton = borrarButton;
	}

	public Button getComunidadesButton() {
		return comunidadesButton;
	}

	public void setComunidadesButton(Button comunidadesButton) {
		this.comunidadesButton = comunidadesButton;
	}

	public boolean isEsDuenno() {
		return esDuenno;
	}

	public void setEsDuenno(boolean esDuenno) {
		this.esDuenno = esDuenno;
	}

	public String getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}

	
}



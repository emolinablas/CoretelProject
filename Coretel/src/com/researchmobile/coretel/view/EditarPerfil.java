package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.entity.Usuario;
import com.researchmobile.coretel.ws.RequestWS;

public class EditarPerfil  extends Activity implements OnClickListener{
	
	private EditText nombreEditText;
	private EditText emailEditText;
	private EditText telefonoEditText;
	private TextView usuarioTextView;
	private Usuario usuario;
	private Button guardarButton;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta;
	private RequestWS request;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editarperfil);
		
		Bundle bundle = getIntent().getExtras();
		setUsuario((Usuario)bundle.get("usuario"));
		
		setRespuesta(new RespuestaWS());
		setRequest(new RequestWS());
		
		setNombreEditText((EditText)findViewById(R.id.editarperfil_nombre_edittext));
		setEmailEditText((EditText)findViewById(R.id.editarperfil_email_edittext));
		setTelefonoEditText((EditText)findViewById(R.id.editarperfil_telefono_edittext));
		setUsuarioTextView((TextView)findViewById(R.id.editarperfil_usuario_textview));
		setGuardarButton((Button)findViewById(R.id.editarperfil_guardar_button));
		getGuardarButton().setOnClickListener(this);
		
		getNombreEditText().setText(getUsuario().getNombre());
		getEmailEditText().setText(getUsuario().getEmail());
		getTelefonoEditText().setText(getUsuario().getTelefono());
		getUsuarioTextView().setText(User.getUsername());

	}

	@Override
	public void onClick(View view) {
		if (view == getGuardarButton()){
			dialogGuardar();
		}
		
	}
	
	private void dialogGuardar(){
		new AlertDialog.Builder(this)
        .setTitle("Guardar Cambios")
        .setMessage("Esta seguro que desea guardar los cambios?")
        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     new guardarAsync().execute("");
                }
        })
        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     
                }
        })
        .show();
	}
	
	private void enviarCambios(){
		String nombre = getNombreEditText().getText().toString();
		String email = getEmailEditText().getText().toString();
		String usuario = User.getUsername();
		String telefono = getTelefonoEditText().getText().toString();
		
		if (nombre.equalsIgnoreCase("") || email.equalsIgnoreCase("") || telefono.equalsIgnoreCase("") || usuario.equalsIgnoreCase("")){
			getRespuesta().setMensaje("Debe completar todos los campos");
			getRespuesta().setResultado(false);
		}else{
			setRespuesta(getRequest().editarPerfil(nombre, email, usuario, telefono));
		}
	}
	// Clase para ejecutar en Background
    class guardarAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(EditarPerfil.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	enviarCambios();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                Toast.makeText(getBaseContext(), getRespuesta().getMensaje(), Toast.LENGTH_SHORT).show();
                if (getRespuesta().isResultado()){
                	finish();
                }
         }
}


	public EditText getNombreEditText() {
		return nombreEditText;
	}

	public void setNombreEditText(EditText nombreEditText) {
		this.nombreEditText = nombreEditText;
	}

	public EditText getEmailEditText() {
		return emailEditText;
	}

	public void setEmailEditText(EditText emailEditText) {
		this.emailEditText = emailEditText;
	}

	public EditText getTelefonoEditText() {
		return telefonoEditText;
	}

	public void setTelefonoEditText(EditText telefonoEditText) {
		this.telefonoEditText = telefonoEditText;
	}

	public TextView getUsuarioTextView() {
		return usuarioTextView;
	}

	public void setUsuarioTextView(TextView usuarioTextView) {
		this.usuarioTextView = usuarioTextView;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public RequestWS getRequest() {
		return request;
	}

	public void setRequest(RequestWS request) {
		this.request = request;
	}
	
}

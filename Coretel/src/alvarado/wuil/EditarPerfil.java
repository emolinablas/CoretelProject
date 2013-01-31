package alvarado.wuil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.entity.Usuario;

public class EditarPerfil  extends Activity implements OnClickListener{
	
	private EditText nombreEditText;
	private EditText emailEditText;
	private EditText telefonoEditText;
	private TextView usuarioTextView;
	private Usuario usuario;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editarperfil);
		
		Bundle bundle = getIntent().getExtras();
		setUsuario((Usuario)bundle.get("usuario"));
		
		setNombreEditText((EditText)findViewById(R.id.editarperfil_nombre_edittext));
		setEmailEditText((EditText)findViewById(R.id.editarperfil_email_edittext));
		setTelefonoEditText((EditText)findViewById(R.id.editarperfil_telefono_edittext));
		setUsuarioTextView((TextView)findViewById(R.id.editarperfil_usuario_textview));
		
		getNombreEditText().setText(getUsuario().getNombre());
		getEmailEditText().setText(getUsuario().getEmail());
		getTelefonoEditText().setText(getUsuario().getTelefono());
		getUsuarioTextView().setText(User.getUsername());

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
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
	
	
}

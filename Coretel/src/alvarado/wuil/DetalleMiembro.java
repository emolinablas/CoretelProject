package alvarado.wuil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.researchmobile.coretel.entity.Miembro;
import com.researchmobile.coretel.entity.Usuario;

public class DetalleMiembro extends Activity implements OnClickListener{

	private TextView nombreTextView;
	private TextView telefonoTextView;
	private TextView emailTextView;
	private Miembro miembro;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.detallemiembro);
	
		Bundle bundle = getIntent().getExtras();
		setMiembro((Miembro)bundle.get("miembro"));
		
		setNombreTextView((TextView)findViewById(R.id.detallemiembros_nombre_edittext));
		setTelefonoTextView((TextView)findViewById(R.id.detallemiembro_telefono_edittext));
		setEmailTextView((TextView)findViewById(R.id.detallemiembro_email_edittext));
		
		getNombreTextView().setText(getMiembro().getNombreUsuario());
		getTelefonoTextView().setText(getMiembro().getTelefono());
		getEmailTextView().setText(getMiembro().getEmail());
		
	}

	
	public Miembro getMiembro() {
		return miembro;
	}


	public void setMiembro(Miembro miembro) {
		this.miembro = miembro;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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

}



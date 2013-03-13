package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.researchmobile.coretel.entity.Miembro;

public class DetalleMiembro extends Activity implements OnClickListener{

	private TextView nombreTextView;
	private TextView telefonoTextView;
	private TextView emailTextView;
	private Miembro miembro;
	private LinearLayout borrarButton;
	private Button comunidadesButton;
	
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detallemiembro);
	
		Bundle bundle = getIntent().getExtras();
		setMiembro((Miembro)bundle.get("miembro"));
		
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
		
	}
	
	@Override
	public void onClick(View v) {
		if (v == getComunidadesButton()){
			finish();
		}else if (v == getBorrarButton()){
			dialogBorrar();
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

	
}



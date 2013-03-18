package com.researchmobile.coretel.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity implements OnClickListener{
	private Button pasaloButton;
	private Button recibeloButton;
	private Button salirButton;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.principal);
		
		setPasaloButton((Button)findViewById(R.id.principal_pasalo_button));
		setRecibeloButton((Button)findViewById(R.id.principal_recibelo_button));
		setSalirButton((Button)findViewById(R.id.principal_salir_button));
		getPasaloButton().setOnClickListener(this);
		getRecibeloButton().setOnClickListener(this);
		getSalirButton().setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		if (view == getPasaloButton()){
			iniciarPasalo();
		}else if (view == getRecibeloButton()){
			iniciarRecibelo();
		}else if (view == getSalirButton()){
			salir();
		}
	}
	
	public void iniciarPasalo(){
		Intent intent = new Intent(Principal.this, Login.class);
		startActivity(intent);
	}
	
	public void iniciarRecibelo(){
		
	}
	
	public void salir(){
		moveTaskToBack( true);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	      if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	  moveTaskToBack( true);     
	          return true;
	      }
	      
	      return super.onKeyDown(keyCode, event);
    }


	public Button getPasaloButton() {
		return pasaloButton;
	}

	public void setPasaloButton(Button pasaloButton) {
		this.pasaloButton = pasaloButton;
	}

	public Button getRecibeloButton() {
		return recibeloButton;
	}

	public void setRecibeloButton(Button recibeloButton) {
		this.recibeloButton = recibeloButton;
	}

	public Button getSalirButton() {
		return salirButton;
	}

	public void setSalirButton(Button salirButton) {
		this.salirButton = salirButton;
	}
	
	

}

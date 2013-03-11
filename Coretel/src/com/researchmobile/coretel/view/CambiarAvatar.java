package com.researchmobile.coretel.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.researchmobile.coretel.entity.User;

public class CambiarAvatar extends Activity implements OnClickListener{
	private ImageView avatarImageView;
	private Button cancelarButton;
	private Button cambiarButton;
	private Button guardarButton;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cambiaravatar);
		
		setAvatarImageView((ImageView)findViewById(R.id.cambiaravatar_avatar));
		setCancelarButton((Button)findViewById(R.id.cambiaravatar_cancelar_button));
		setCambiarButton((Button)findViewById(R.id.cambiaravatar_cambiar_button));
		setGuardarButton((Button)findViewById(R.id.cambiaravatar_guardar_button));
		Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
		getAvatarImageView().setImageBitmap(image);
	}

	@Override
	public void onClick(View view) {
		if (view == getCancelarButton()){
			finish();
		}else if (view == getCambiarButton()){
			
		}else if (view == getGuardarButton()){
			
		}
		
	}

	public ImageView getAvatarImageView() {
		return avatarImageView;
	}

	public void setAvatarImageView(ImageView avatarImageView) {
		this.avatarImageView = avatarImageView;
	}

	public Button getCancelarButton() {
		return cancelarButton;
	}

	public void setCancelarButton(Button cancelarButton) {
		this.cancelarButton = cancelarButton;
	}

	public Button getCambiarButton() {
		return cambiarButton;
	}

	public void setCambiarButton(Button cambiarButton) {
		this.cambiarButton = cambiarButton;
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

}

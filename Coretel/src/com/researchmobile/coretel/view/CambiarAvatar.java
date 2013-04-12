package com.researchmobile.coretel.view;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.RMFile;
import com.researchmobile.coretel.ws.RequestWS;

public class CambiarAvatar extends Activity implements OnClickListener{
	final static int CAMERA_RESULT = 0;
	private static int TAKE_PICTURE = 1;
	private static int SELECT_PICTURE = 2;
	private ImageView avatarImageView;
	private Button cancelarButton;
	private Button cambiarButton;
	private Button guardarButton;
	private String pathFoto;
	private ProgressDialog pd = null;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cambiaravatar);
		
		setAvatarImageView((ImageView)findViewById(R.id.cambiaravatar_avatar));
		setCancelarButton((Button)findViewById(R.id.cambiaravatar_cancelar_button));
		setCambiarButton((Button)findViewById(R.id.cambiaravatar_cambiar_button));
		setGuardarButton((Button)findViewById(R.id.cambiaravatar_guardar_button));
		getCancelarButton().setOnClickListener(this);
		getCambiarButton().setOnClickListener(this);
		getGuardarButton().setOnClickListener(this);
		Bitmap image = BitmapFactory.decodeFile("sdcard/pasalo/" + User.getAvatar());
		getAvatarImageView().setImageBitmap(image);
	}

	@Override
	public void onClick(View view) {
		ConnectState conect = new ConnectState();
		if (view == getCancelarButton()){
			finish();
		}else if (view == getCambiarButton()){
			dialogFotos(this);
		}else if (view == getGuardarButton()){
			if(conect.isConnectedToInternet(this))
			{
			new guardarAsync().execute("");
			}else{
				Toast.makeText(this, "No posee conexion a internet, intentelo mas tarde!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	

	// Dialog de opciones Camara o Album
	public void dialogFotos(final Context activity) {

		final Dialog myDialog = new Dialog(activity);
		myDialog.setContentView(R.layout.dialog_fotos);
		myDialog.setTitle("Opciones");
		myDialog.setCancelable(false);

		Button cerrar = (Button) myDialog
				.findViewById(R.id.dialog_fotos_cerrar);
		cerrar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				myDialog.dismiss();
			}
		});

		Button album = (Button) myDialog.findViewById(R.id.dialog_fotos_album);
		album.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mostrarGaleria();
				// galeria = true;
				myDialog.dismiss();
			}
		});

		Button camara = (Button) myDialog.findViewById(R.id.dialog_fotos_tomar);
		camara.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				ActivarCamara();
				// galeria = false;
				myDialog.dismiss();
			}
		});

		myDialog.show();

	}

	// Metodos para usar la galería de fotos
	private void mostrarGaleria() {
		try {
			int code = TAKE_PICTURE;
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
			code = SELECT_PICTURE;
			startActivityForResult(intent, code);
		} catch (Exception exception) {

		}
	}

	private void verImagenGaleria() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Log.e("Log", "ver foto galeria = " + getPathFoto());
		Bitmap bm = BitmapFactory.decodeFile(getPathFoto(), options);
		getAvatarImageView().setImageBitmap(bm);
		// origenAlbum = true;
	}
	
	/**
     * Funci—n que se ejecuta cuando concluye el intent en el que se solicita una imagen
     * ya sea de la c‡mara o de la galer’a
     */
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	/**
    	 * Se revisa si la imagen viene de la c‡mara (TAKE_PICTURE) o de la galer’a (SELECT_PICTURE)
    	 */
    	Log.v("pio", "avatar. requestCode = " + requestCode);
    	if (requestCode == SELECT_PICTURE){
    		if (data != null){
//    			origenAlbum = true;
    			Log.v("pio", "avatar. album = " + resultCode);
    			setPathFoto(data.getData().getPath());
    			verImagenGaleria();
    		}
    	}else {
    		if (resultCode < 0){
    			Log.v("pio", "avatar. camara = " + resultCode);
    			verImagen();
    		}
    	}
    }

	// Metodos para usar la camara

	private void ActivarCamara() {
		// Activar la camara
		Intent cIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		// startActivityForResult(cIntent, CAMERA_RESULT);
		// asignar nombre y direccion a la imagen
		setPathFoto("sdcard/foto1-even.jpg");
		String path = "/mifoto.jpg";
		// crear nuevo archivo (imagen)
		File f = new File(getPathFoto());
		Uri uri = Uri.fromFile(f);
		cIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(cIntent, CAMERA_RESULT);
		
		// origenAlbum = false;
	}

	private void verImagen() {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Log.e("Log", "ver foto = sdcard" + getPathFoto());
		Bitmap bm = BitmapFactory.decodeFile(getPathFoto(), options);
		getAvatarImageView().setImageBitmap(bm);
		getAvatarImageView().setMaxHeight(70);
		// setGaleria(false);
	}
	
	// Clase para ejecutar en Background
	class guardarAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(CambiarAvatar.this, "ENVIAR", "ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				EnviarEvento();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			login();
			regresar();
		}
	}
	
	public void login(){
		RequestWS req = new RequestWS();
		User user = new User();
		req.Login(user);
		RMFile rmFile = new RMFile(); 
		rmFile.downloadImage(User.getAvatar());
	}
	
	public void regresar(){
		Intent intent = new Intent(CambiarAvatar.this, Mapa.class);
		startActivity(intent);
	}

	private void EnviarEvento() {

		try {
			ConnectState connect = new ConnectState();
			RequestWS request = new RequestWS();
			if (connect.isConnectedToInternet(this)) {
				String idUsuario = User.getUserId();
				String imagen = fotoReducida();
				RespuestaWS respuesta = new RespuestaWS();
				request.cambiarAvatar(imagen);
				if (respuesta.isResultado()) {
					
				}
			}
		} catch (Exception exception) {
			Toast.makeText(getBaseContext(), "no tiene internet",
					Toast.LENGTH_SHORT).show();
		}
	}
	

	private String fotoReducida() {

		String fotonueva = "/captura.jpg";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 0;
		Bitmap bm = BitmapFactory.decodeFile(getPathFoto());
		// }else{
		// bm = BitmapFactory.decodeFile("sdcard/" + getPathFoto());
		// }

		File file = new File("sdcard" + fotonueva);
		try {
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 20, out);// Convertimos la
																// imagen a JPEG
			return fotonueva;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

	public String getPathFoto() {
		return pathFoto;
	}

	public void setPathFoto(String pathFoto) {
		this.pathFoto = pathFoto;
	}

}

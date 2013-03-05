package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.ws.RequestWS;

public class Descevento extends Activity implements OnClickListener{
	private TipoAnotacion tipoAnotacion;
	private TextView nombreTextView;
	private TextView descripcionTextView;
	private ImageView iconoImageView;
	private Button borrarButton;
	private Button editarButton;
	private TokenizerUtility tokenizer = new TokenizerUtility();
	private ProgressDialog pd = null;
	private RespuestaWS respuestaWS;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.descevento);
		Bundle bundle = (Bundle)getIntent().getExtras();
		setTipoAnotacion((TipoAnotacion)bundle.get("anotacion"));
		
		setNombreTextView((TextView)findViewById(R.id.descevento_nombre_textview));

		setDescripcionTextView((TextView)findViewById(R.id.descevento_descripcion_textview));
		setIconoImageView((ImageView)findViewById(R.id.descevento_icono_imageview));
		setBorrarButton((Button)findViewById(R.id.descevento_borrar_button));
		getBorrarButton().setOnClickListener(this);
		setEditarButton((Button)findViewById(R.id.editar_edit_button));
		getBorrarButton().setOnClickListener(this);
		getEditarButton().setOnClickListener(this);
		
		if (getTipoAnotacion() != null){
			getNombreTextView().setText(getTipoAnotacion().getNombre());
	
			getDescripcionTextView().setText(getTipoAnotacion().getNombre());
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 0;
//			Bitmap bm = BitmapFactory.decodeFile("sdcard/" + getTipoAnotacion().getIcono(), options);
			getIconoImageView().setImageDrawable(tokenizer.iconoResource(this, "0=+=1=+=2=+=3=+=4=+=" + getTipoAnotacion().getIcono()));
//	        getIconoImageView().setImageBitmap(bm);
		}
		
	}


	public Button getEditarButton() {
		return editarButton;
	}


	public void setEditarButton(Button editarButton) {
		this.editarButton = editarButton;
	}


	public TokenizerUtility getTokenizer() {
		return tokenizer;
	}


	public void setTokenizer(TokenizerUtility tokenizer) {
		this.tokenizer = tokenizer;
	}


	public ProgressDialog getPd() {
		return pd;
	}


	public void setPd(ProgressDialog pd) {
		this.pd = pd;
	}


	@Override
	public void onClick(View v) {
		if (v == getBorrarButton()){
			dialogBorrar();
		}else if(v == getEditarButton()){
			Editarboton();
		}
				
	}
	
	private void Editarboton(){
		Intent intent = new Intent(Descevento.this, EditarTipoEvento.class); 
		startActivity(intent);
	}
	
	private void dialogBorrar(){
		new AlertDialog.Builder(this)
        .setTitle("Borrar Tipo Evento")
        .setMessage("Esta seguro que desea borrar el tipo de evento (" + getTipoAnotacion().getDescripcion() + ")..?")
        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     new borrarAsync().execute("");
                }
        })
        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     Toast.makeText(getBaseContext(), "operacion cancelada", Toast.LENGTH_SHORT).show();
                }
        })
        .show();

	}
	
	private void eliminarEvento(){
		RequestWS request = new RequestWS();
		setRespuestaWS(request.eliminaTipoEvento(User.getUserId(), getTipoAnotacion().getId()));
	}
	
	 // Clase para ejecutar en Background
    class borrarAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Descevento.this, "ELIMINAR EVENTO", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	eliminarEvento();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (getRespuestaWS() != null){
                	if (getRespuestaWS().isResultado()){
                		finish();	
                	}else{
                		Toast.makeText(getBaseContext(), getRespuestaWS().getMensaje(), Toast.LENGTH_SHORT).show();
                	}
                }else{
                	Toast.makeText(getBaseContext(), "intente nuevamente", Toast.LENGTH_SHORT).show();
                }

         }
   }



	public TipoAnotacion getTipoAnotacion() {
		return tipoAnotacion;
	}


	public void setTipoAnotacion(TipoAnotacion tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}


	public TextView getNombreTextView() {
		return nombreTextView;
	}


	public void setNombreTextView(TextView nombreTextView) {
		this.nombreTextView = nombreTextView;
	}



	public TextView getDescripcionTextView() {
		return descripcionTextView;
	}


	public void setDescripcionTextView(TextView descripcionTextView) {
		this.descripcionTextView = descripcionTextView;
	}


	public ImageView getIconoImageView() {
		return iconoImageView;
	}


	public void setIconoImageView(ImageView iconoImageView) {
		this.iconoImageView = iconoImageView;
	}


	public Button getBorrarButton() {
		return borrarButton;
	}


	public void setBorrarButton(Button borrarButton) {
		this.borrarButton = borrarButton;
	}


	public RespuestaWS getRespuestaWS() {
		return respuestaWS;
	}


	public void setRespuestaWS(RespuestaWS respuestaWS) {
		this.respuestaWS = respuestaWS;
	}
	
	
}

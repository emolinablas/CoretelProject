package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.ws.RequestWS;

public class DetalleEvento extends Activity implements OnClickListener{
	
	private TipoAnotacion tipoAnotacion;
	
	private TextView fechaTextView;
	private TextView tipoTextView;
	private TextView descripcionTextView;
	private TextView activoTextView;
	private Button regresarButton;
	private Button editarButton;
	private Button eliminarButton;
	private String id = "";
    private String fecha = "";
    private String activo = "";
    private String tipo = "";
    private String descripcion = "";
    private RespuestaWS respuestaWS = new RespuestaWS();
    private String idComunidad;
	
	private ProgressDialog pd = null;
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detalleevento);
        Bundle bundle = getIntent().getExtras();
        
        try{
         id = bundle.getString("id");
         fecha = bundle.getString("fecha");
         activo = bundle.getString("activo");
         tipo = bundle.getString("tipo");
         descripcion = bundle.getString("descripcion");
         setIdComunidad(bundle.getString("idComunidad"));
         System.out.println("se setearon los datoss " + id + " " + fecha + " " + activo  + " " + descripcion);
        }catch(Exception e){
        	System.out.println("Ocurrio un error al guardar los extras");
        }
        
        setFechaTextView((TextView)findViewById(R.id.detalleevento_fecha_textview));
        setTipoTextView((TextView)findViewById(R.id.detalleevento_tipo_textview));
        setActivoTextView((TextView)findViewById(R.id.detalleevento_activo_textview));
        setDescripcionTextView((TextView)findViewById(R.id.detalleevento_descripcion_textview));
        setRegresarButton((Button)findViewById(R.id.detalleevento_regresar_button));
        setEditarButton((Button)findViewById(R.id.detalleevento_editar_button));
        setEliminarButton((Button)findViewById(R.id.detalleevento_borrar_button));
        getEditarButton().setOnClickListener(this);
        getEliminarButton().setOnClickListener(this);
        getRegresarButton().setOnClickListener(this);
        
       getEliminarButton().setVisibility(View.VISIBLE);
       
       
        getFechaTextView().setText(fecha);
        getTipoTextView().setText(tipo);
        getDescripcionTextView().setText(descripcion);
        getActivoTextView().setText(activo);
        
    }
    
	@Override
	public void onClick(View view) {
		if (view == getRegresarButton()){
			finish();
		}else if (view == getEliminarButton()){
			borrarEvento();
		}else if (view == getEditarButton()){
			Editarboton();
			
		}
	}
	
	private void borrarEvento(){
		new AlertDialog.Builder(this)
        .setTitle("Borrar Evento")
        .setMessage("Esta seguro que desea eliminar el evento")
        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     new eliminaEventoAsync().execute("");
                }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	Toast.makeText(getBaseContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                }
        })
        .show();
	}
	
	// Clase para ejecutar en Background
	class eliminaEventoAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(DetalleEvento.this, "Eliminar Evento","ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				eliminarEvento();
			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();
			if (respuestaWS != null) {
				Toast.makeText(getBaseContext(), respuestaWS.getMensaje(),Toast.LENGTH_SHORT).show();
				if (respuestaWS.isResultado()) {
					Intent intent = new Intent(DetalleEvento.this, ListaEventos.class);
					intent.putExtra("idComunidad", getIdComunidad());
					startActivity(intent);
				}
			}
		}
	}
    
    private void eliminarEvento(){
		RequestWS request = new RequestWS();
		respuestaWS = request.eliminaEvento(id, User.getUserId());
	}
	
	private void Editarboton() {
		Intent intent = new Intent(DetalleEvento.this, EditarDetalleEvento.class);
		intent.putExtra("tipo", tipo);
		intent.putExtra("activo", activo);
		intent.putExtra("fecha", fecha);
		intent.putExtra("descripcion", descripcion);
		intent.putExtra(id, id);
		intent.putExtra("idComunidad", getIdComunidad());
		startActivity(intent);
	}

	public TextView getFechaTextView() {
		return fechaTextView;
	}
	public void setFechaTextView(TextView fechaTextView) {
		this.fechaTextView = fechaTextView;
	}
	public TextView getTipoTextView() {
		return tipoTextView;
	}
	public void setTipoTextView(TextView tipoTextView) {
		this.tipoTextView = tipoTextView;
	}
	public TextView getDescripcionTextView() {
		return descripcionTextView;
	}
	public void setDescripcionTextView(TextView descripcionTextView) {
		this.descripcionTextView = descripcionTextView;
	}
	public TextView getActivoTextView() {
		return activoTextView;
	}
	public void setActivoTextView(TextView activoTextView) {
		this.activoTextView = activoTextView;
	}
	public Button getRegresarButton() {
		return regresarButton;
	}
	public void setRegresarButton(Button regresarButton) {
		this.regresarButton = regresarButton;
	}

	public Button getEditarButton() {
		return editarButton;
	}

	public void setEditarButton(Button editarButton) {
		this.editarButton = editarButton;
	}

	public Button getEliminarButton() {
		return eliminarButton;
	}

	public void setEliminarButton(Button eliminarButton) {
		this.eliminarButton = eliminarButton;
	}

	public TipoAnotacion getTipoAnotacion() {
		return tipoAnotacion;
	}

	public void setTipoAnotacion(TipoAnotacion tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}

	public String getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}

	
	
}

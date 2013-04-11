package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.TipoAnotacion;
import com.researchmobile.coretel.utility.ImageAdapter;
import com.researchmobile.coretel.utility.TokenizerUtility;
import com.researchmobile.coretel.ws.RequestWS;

public class EditarDetalleEvento extends Activity implements OnClickListener, OnKeyListener {
	
	private Button guardarButton;
	
	private ImageView iconoEvento;
	private EditText tipoEditText;
	private EditText FechaEditText;
	private EditText ActivoEditText;
	private EditText DescripcionEditText;
	private TipoAnotacion tipoAnotacion;
	
	private String id = "";
    private String fecha = "";
    private String activo = "";
    private String tipo = "";
    private String descripcion = "";
    private String idComunidad;
	
	private String urlSeleccionado = "";
	int seleccionado = 0;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta;
	

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editardetalleevento);
		
		setGuardarButton((Button)findViewById(R.id.editardetalleevento_guardar_button));
		setTipoEditText((EditText)findViewById(R.id.editardetalleevento_tipo_edittext));
		setFechaEditText((EditText)findViewById(R.id.editardetalleevento_fecha_edittext));
		setActivoEditText((EditText)findViewById(R.id.editardetalleevento_activo_edittext));
		setDescripcionEditText((EditText)findViewById(R.id.editardetalleevento_descripcion_edittext));
		
		getGuardarButton().setOnClickListener(this);
		
		Bundle bundle = (Bundle)getIntent().getExtras();
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
		
		getTipoEditText().setText(tipo);
		getFechaEditText().setText(fecha);
		getActivoEditText().setText(activo);
		getDescripcionEditText().setText(descripcion);

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	
	public ImageView getIconoEvento() {
		return iconoEvento;
	}

	public void setIconoEvento(ImageView iconoEvento) {
		this.iconoEvento = iconoEvento;
	}

	public EditText getTipoEditText() {
		return tipoEditText;
	}

	public void setTipoEditText(EditText tipoEditText) {
		this.tipoEditText = tipoEditText;
	}

	public EditText getFechaEditText() {
		return FechaEditText;
	}

	public void setFechaEditText(EditText fechaEditText) {
		FechaEditText = fechaEditText;
	}

	public EditText getActivoEditText() {
		return ActivoEditText;
	}

	public void setActivoEditText(EditText activoEditText) {
		ActivoEditText = activoEditText;
	}

	public EditText getDescripcionEditText() {
		return DescripcionEditText;
	}

	public void setDescripcionEditText(EditText descripcionEditText) {
		DescripcionEditText = descripcionEditText;
	}

	public TipoAnotacion getTipoAnotacion() {
		return tipoAnotacion;
	}

	public void setTipoAnotacion(TipoAnotacion tipoAnotacion) {
		this.tipoAnotacion = tipoAnotacion;
	}

	public String getUrlSeleccionado() {
		return urlSeleccionado;
	}

	public void setUrlSeleccionado(String urlSeleccionado) {
		this.urlSeleccionado = urlSeleccionado;
	}

	public int getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(int seleccionado) {
		this.seleccionado = seleccionado;
	}

	public ProgressDialog getPd() {
		return pd;
	}

	public void setPd(ProgressDialog pd) {
		this.pd = pd;
	}

	public RespuestaWS getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(RespuestaWS respuesta) {
		this.respuesta = respuesta;
	}

	public String getIdComunidad() {
		return idComunidad;
	}

	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	
	


}
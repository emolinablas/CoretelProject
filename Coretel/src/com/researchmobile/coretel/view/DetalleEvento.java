package com.researchmobile.coretel.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.entity.Anotacion;

public class DetalleEvento extends Activity implements OnClickListener{
	
	private TextView fechaTextView;
	private TextView tipoTextView;
	private TextView descripcionTextView;
	private TextView activoTextView;
	private Button regresarButton;
	private Button editarButton;
	private Button eliminarButton;
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.detalleevento);
        Bundle bundle = getIntent().getExtras();
        
        String id = "";
        String fecha = "";
        String activo = "";
        String tipo = "";
        String descripcion = "";
        
        try{
         id = bundle.getString("id");
         fecha = bundle.getString("fecha");
         activo = bundle.getString("activo");
         tipo = bundle.getString("tipo");
         descripcion = bundle.getString("descripcion");
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
        
       getEditarButton().setVisibility(View.INVISIBLE);
       getEliminarButton().setVisibility(View.INVISIBLE);
        
        
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
			
		}else if (view == getEditarButton()){
			
		}
	}
	
	private void setFotoImageView(ImageView findViewById) {
		// TODO Auto-generated method stub
		
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
	
	
}

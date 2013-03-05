package com.researchmobile.coretel.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.entity.Anotacion;

public class DetalleEvento extends Activity implements OnClickListener{
	
	private TextView tituloTextView;
	private TextView fechaTextView;
	private TextView latitudTextView;
	private TextView longitudTextView;
	private TextView comunidadTextView;
	private TextView tipoTextView;
	private TextView descripcionTextView;
	private Button verButton;
	private ImageView fotoimageview;
	private Anotacion anotacion;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalleevento);
    
   
        
        Bundle bundle = getIntent().getExtras();
//        setAnotacion((Anotacion)bundle.get("anotacion"));
        
        setTituloTextView((TextView)findViewById(R.id.detalleevento_titulo_textview));
        setFechaTextView((TextView)findViewById(R.id.detalleevento_fecha_textview));
        setLatitudTextView((TextView)findViewById(R.id.detalleevento_latitud_textview));
        setLongitudTextView((TextView)findViewById(R.id.detalleevento_longitud_textview));
        setComunidadTextView((TextView)findViewById(R.id.detalleevento_comunidad_textview));
        setTipoTextView((TextView)findViewById(R.id.detalleevento_tipo_textview));
        setDescripcionTextView((TextView)findViewById(R.id.detalleevento_descripcion_textview));
        setVerButton((Button)findViewById(R.id.detalleevento_ver_button));
        setFotoImageView((ImageView)findViewById(R.id.detalleevento_foto_imageview));
        
        /*
        getTituloTextView().setText(getAnotacion().getDescripcion());
        getFechaTextView().setText(getAnotacion().getFecha_registro());
        getLatitudTextView().setText(String.valueOf(getAnotacion().getLatitud()));
        getLongitudTextView().setText(String.valueOf(getAnotacion().getLongitud()));
        getComunidadTextView().setText(getAnotacion().getIdcomunidad());
        getTipoTextView().setText(getAnotacion().getTipo_anotacion());
        getDescripcionTextView().setText(getAnotacion().getDescripcion());
        */
        getVerButton().setOnClickListener(this);
	}
	
	private void setFotoImageView(ImageView findViewById) {
		// TODO Auto-generated method stub
		
	}


	public Button getVerButton() {
		return verButton;
	}


	public void setVerButton(Button verButton) {
		this.verButton = verButton;
	}


	public ImageView getFotoimageview() {
		return fotoimageview;
	}


	public void setFotoimageview(ImageView fotoimageview) {
		this.fotoimageview = fotoimageview;
	}


	public TextView getTituloTextView() {
		return tituloTextView;
	}
	public void setTituloTextView(TextView tituloTextView) {
		this.tituloTextView = tituloTextView;
	}
	public TextView getFechaTextView() {
		return fechaTextView;
	}
	public void setFechaTextView(TextView fechaTextView) {
		this.fechaTextView = fechaTextView;
	}
	public TextView getLatitudTextView() {
		return latitudTextView;
	}
	public void setLatitudTextView(TextView latitudTextView) {
		this.latitudTextView = latitudTextView;
	}
	public TextView getLongitudTextView() {
		return longitudTextView;
	}
	public void setLongitudTextView(TextView longitudTextView) {
		this.longitudTextView = longitudTextView;
	}
	public TextView getComunidadTextView() {
		return comunidadTextView;
	}
	public void setComunidadTextView(TextView comunidadTextView) {
		this.comunidadTextView = comunidadTextView;
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
	public Anotacion getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(Anotacion anotacion) {
		this.anotacion = anotacion;
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
		
}

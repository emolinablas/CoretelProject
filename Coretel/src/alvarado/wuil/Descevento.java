package alvarado.wuil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.researchmobile.coretel.entity.TipoAnotacion;

public class Descevento extends Activity implements OnClickListener{
	private TipoAnotacion tipoAnotacion;
	private TextView nombreTextView;
	private TextView activoTextView;
	private TextView incidenteTextView;
	private TextView descripcionTextView;
	private ImageView iconoImageView;
	private Button borrarButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.descevento);
		Bundle bundle = (Bundle)getIntent().getExtras();
		setTipoAnotacion((TipoAnotacion)bundle.get("anotacion"));
		
		setNombreTextView((TextView)findViewById(R.id.descevento_nombre_textview));
		setActivoTextView((TextView)findViewById(R.id.descevento_activo_textview));
		setIncidenteTextView((TextView)findViewById(R.id.descevento_incidente_textview));
		setDescripcionTextView((TextView)findViewById(R.id.descevento_descripcion_textview));
		setIconoImageView((ImageView)findViewById(R.id.descevento_icono_imageview));
		setBorrarButton((Button)findViewById(R.id.descevento_borrar_button));
		
		if (getTipoAnotacion() != null){
			getNombreTextView().setText(getTipoAnotacion().getNombre());
			getActivoTextView().setText(getTipoAnotacion().getActivo());
			getIncidenteTextView().setText(getTipoAnotacion().getIncidente());
			getDescripcionTextView().setText(getTipoAnotacion().getDescripcion());
		}
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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


	public TextView getActivoTextView() {
		return activoTextView;
	}


	public void setActivoTextView(TextView activoTextView) {
		this.activoTextView = activoTextView;
	}


	public TextView getIncidenteTextView() {
		return incidenteTextView;
	}


	public void setIncidenteTextView(TextView incidenteTextView) {
		this.incidenteTextView = incidenteTextView;
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
	
	
}

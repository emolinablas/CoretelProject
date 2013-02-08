package alvarado.wuil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class DetalleEvento extends Activity {

	private Button galleryButton;
	private Button capturarButton;
	private Button saveButton;
	private EditText tituloEditText;
	private EditText fechaEditText;
	private TextView latitudTextView;
	private TextView longitudTextView;
	private TextView comunidadTextView;
	private Spinner comunidadSpinner;
	private TextView tipoTextView;
	private Spinner tipoSpinner; 
	private EditText descripcionEditText;
	private Button verButton;
	private ImageView fotoImageView;
	private Button borrarButton;
	
	public void OnCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.detalle_evento);
	}
	
	
}

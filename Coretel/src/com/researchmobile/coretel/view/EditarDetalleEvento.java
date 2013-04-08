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
	private Button iconoButton;
	private ImageView iconoEvento;
	private EditText tipoEditText;
	private EditText Fecha;
	private EditText Activo;
	private EditText Descripcion;
	private TipoAnotacion tipoAnotacion;
	private String urlSeleccionado = "";
	int seleccionado = 0;
	private ProgressDialog pd = null;
	private RespuestaWS respuesta;
	
	private String tipo;
	private String fecha;
	private String activo;
	private String descripcion;
	private String idAnotacion;
	private String urlIcono;
	
	

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.editardetalleevento);
		
		Bundle bundle = (Bundle)getIntent().getExtras();
			
		
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


}
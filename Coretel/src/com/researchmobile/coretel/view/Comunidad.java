package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.researchmobile.coretel.entity.CatalogoMiembro;
import com.researchmobile.coretel.entity.CatalogoTipoAnotacion;
import com.researchmobile.coretel.entity.DetalleComunidad;
import com.researchmobile.coretel.entity.RespuestaWS;
import com.researchmobile.coretel.entity.User;
import com.researchmobile.coretel.utility.ConnectState;
import com.researchmobile.coretel.utility.Mensaje;
import com.researchmobile.coretel.ws.RequestWS;

public class Comunidad extends Activity implements OnClickListener{
	private TextView nombreTextView;
	private TextView descripcionTextView;
	private Button borrarButton;
	private Button comunidadesButton;
	private Button editarComunidad;
	private Button miembrosButton;
	private Button eventosButton;
	private Button tiposButton;
	private ToggleButton esPublicaToggleButton;
	private ToggleButton esReasignableToggleButton;
	private CatalogoMiembro catalogoMiembro;
	private CatalogoTipoAnotacion catalogoTipoAnotacion;
	private DetalleComunidad detalleComunidad;
	private ProgressDialog pd = null;
	private boolean estadoMiembro;
	private boolean estadoEvento;
	private boolean estadoTipoEvento;
	private int seleccion;
	private RespuestaWS respuesa;
	private Mensaje mensaje;
	private RespuestaWS respuestaEliminar = new RespuestaWS();
	private boolean esDuenno;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comunidad);
		Bundle bundle = (Bundle)getIntent().getExtras();
//		esDuenno = bundle.getBoolean("esDuenno");
//		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
		setDetalleComunidad((DetalleComunidad)bundle.get("detallecomunidad"));
		setMensaje(new Mensaje());
		setNombreTextView((TextView)findViewById(R.id.comunidad_nombre_textview));
		getNombreTextView().setText(getDetalleComunidad().getNombre());
		setDescripcionTextView((TextView)findViewById(R.id.comunidad_descripcion_textview));
		getDescripcionTextView().setText(getDetalleComunidad().getDescripcion());
		setBorrarButton((Button)findViewById(R.id.comunidad_borrar_button));
		setMiembrosButton((Button)findViewById(R.id.comunidad_miembros_button));
		setEventosButton((Button)findViewById(R.id.comunidad_eventos_button));
		setTiposButton((Button)findViewById(R.id.comunidad_tiposeventos_button));
		setComunidadesButton((Button)findViewById(R.id.comunidad_atras_button));
		setEditarComunidad((Button)findViewById(R.id.comunidad_editar_button));
		setEsPublicaToggleButton((ToggleButton)findViewById(R.id.esPublicaToggleButton));
		setEsReasignableToggleButton((ToggleButton)findViewById(R.id.esReasignableToggleButton));
		getMiembrosButton().setOnClickListener(this);
		getEventosButton().setOnClickListener(this);
		getTiposButton().setOnClickListener(this);
		getEditarComunidad().setOnClickListener(this);
		getComunidadesButton().setOnClickListener(this);
		getBorrarButton().setOnClickListener(this);
		getEsPublicaToggleButton().setEnabled(false);
		getEsReasignableToggleButton().setEnabled(false);
		
		if(getDetalleComunidad().getEspublica().equalsIgnoreCase("1")){
			getEsPublicaToggleButton().setChecked(true);
		}else{
			getEsPublicaToggleButton().setChecked(false);
		}
		
		if(getDetalleComunidad().getEsreasignable().equalsIgnoreCase("1")){
			getEsReasignableToggleButton().setChecked(true);
		}else{
		getEsReasignableToggleButton().setChecked(false);
		}
		
		//verificando si el usuario es due–o de la comunidad (EM)
		
		String usuarioActual = User.getUserId();
		if(usuarioActual.equalsIgnoreCase(getDetalleComunidad().getIdDuenno())){ 
			esDuenno = true;
		}else{
			getEditarComunidad().setVisibility(View.GONE);
			getBorrarButton().setVisibility(View.GONE);
			esDuenno= false;
		}
	}
	
	// Clase para ejecutar en Background
	class SeleccionAsync extends AsyncTask<String, Integer, Integer> {

		// Metodo que prepara lo que usara en background, Prepara el progress
		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Comunidad.this, null,
					"ESPERE UN MOMENTO");
			pd.setCancelable(false);
		}

		// Metodo con las instrucciones que se realizan en background
		@Override
		protected Integer doInBackground(String... urlString) {
			try {
				if (getSeleccion() == 0){
					IniciaTipos();
				}else if (getSeleccion() == 1){
					
				}else if (getSeleccion() == 2){
					
				}

			} catch (Exception exception) {

			}
			return null;
		}

		// Metodo con las instrucciones al finalizar lo ejectuado en background
		protected void onPostExecute(Integer resultado) {
			pd.dismiss();

		}
	}
	
	 // Clase para ejecutar en Background
    class eliminarComunidadAsync extends AsyncTask<String, Integer, Integer> {

          // Metodo que prepara lo que usara en background, Prepara el progress
          @Override
          protected void onPreExecute() {
                pd = ProgressDialog. show(Comunidad.this, "VERIFICANDO DATOS", "ESPERE UN MOMENTO");
                pd.setCancelable( false);
         }

          // Metodo con las instrucciones que se realizan en background
          @Override
          protected Integer doInBackground(String... urlString) {
                try {
                	eliminarComunidad();
               } catch (Exception exception) {

               }
                return null ;
         }

          // Metodo con las instrucciones al finalizar lo ejectuado en background
          protected void onPostExecute(Integer resultado) {
                pd.dismiss();
                if (respuestaEliminar != null){
                	if (respuestaEliminar.isResultado()){
                		Intent intent = new Intent(Comunidad.this, Comunidades.class);
                		startActivity(intent);
                	}else{
                		Toast.makeText(getBaseContext(), respuestaEliminar.getMensaje(), Toast.LENGTH_SHORT).show();
                	}
                }
         }
   }
    
//	Metodo para controlar ScrollView con ListView
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }

	    int totalHeight = 0;
	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
	            MeasureSpec.AT_MOST);
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, listView);
	        listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += listItem.getMeasuredHeight();
	    }

	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight
	            + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();
	}
	
    
    private void eliminarComunidad(){
    	RequestWS request = new RequestWS();
    	respuestaEliminar = request.eliminaComunidad(getDetalleComunidad().getId(), User.getUserId());
    }


	protected void IniciaTipos() {
		ConnectState connect = new ConnectState();
		Intent intent = new Intent(Comunidad.this, TipoEvento.class);
		intent.putExtra("idComunidad", getDetalleComunidad().getId());
		intent.putExtra("esDuenno", esDuenno);
		intent.putExtra("activity", Comunidad.class);
		startActivity(intent);
	}

	protected void IniciaEventos() {
		Intent intent = new Intent(Comunidad.this, ListaEventos.class);
		intent.putExtra("idComunidad", getDetalleComunidad().getId());
		startActivity(intent);
	}

	protected void IniciaMiembros() {
		Intent intent = new Intent(Comunidad.this, PruebaListaFoto.class);
		intent.putExtra("idComunidad", getDetalleComunidad().getId());
//		intent.putExtra("catalogoMiembro", getCatalogoMiembro());
		intent.putExtra("esDuenno", esDuenno); // enviando par‡metro si es dueño de la comunidad.
		startActivity(intent);
	}

	private String[] ListaOpciones() {
		
		String[] opciones = {"Miembros", "Eventos", "Tipos de Eventos"};
		return opciones;
	}

	@Override
	public void onClick(View view) {
		if (view == getBorrarButton()){
			eliminaDialog();
		}else if (view == getComunidadesButton()){
			finish();
		}else if (view == getEditarComunidad()){
			Intent intent = new Intent(Comunidad.this, EditarComunidad.class);
			intent.putExtra("comunidad", getDetalleComunidad());
			startActivity(intent);
		}else if (view == getMiembrosButton()){
			IniciaMiembros();
		}else if (view == getEventosButton()){
			IniciaEventos();
		}else if (view == getTiposButton()){
			IniciaTipos();
		}
	}
	
	private void eliminaDialog(){
		new AlertDialog.Builder(this)
        .setTitle("Eliminar Comunidad")
        .setMessage("Esta seguro que desea eliminar la comunidad...?")
        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     new eliminarComunidadAsync().execute("");
                }
        })
        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                     Toast.makeText(getBaseContext(), "Operacion cancelada", Toast.LENGTH_SHORT).show();
                }
        })
        .show();

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

	public Button getBorrarButton() {
		return borrarButton;
	}

	public void setBorrarButton(Button borrarButton) {
		this.borrarButton = borrarButton;
	}

	public CatalogoMiembro getCatalogoMiembro() {
		return catalogoMiembro;
	}

	public void setCatalogoMiembro(CatalogoMiembro catalogoMiembro) {
		this.catalogoMiembro = catalogoMiembro;
	}

	public boolean isEstadoMiembro() {
		return estadoMiembro;
	}

	public void setEstadoMiembro(boolean estadoMiembro) {
		this.estadoMiembro = estadoMiembro;
	}

	public boolean isEstadoEvento() {
		return estadoEvento;
	}

	public void setEstadoEvento(boolean estadoEvento) {
		this.estadoEvento = estadoEvento;
	}

	public boolean isEstadoTipoEvento() {
		return estadoTipoEvento;
	}

	public void setEstadoTipoEvento(boolean estadoTipoEvento) {
		this.estadoTipoEvento = estadoTipoEvento;
	}

	public DetalleComunidad getDetalleComunidad() {
		return detalleComunidad;
	}

	public void setDetalleComunidad(DetalleComunidad detalleComunidad) {
		this.detalleComunidad = detalleComunidad;
	}

	public int getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(int seleccion) {
		this.seleccion = seleccion;
	}

	public RespuestaWS getRespuesa() {
		return respuesa;
	}

	public void setRespuesa(RespuestaWS respuesa) {
		this.respuesa = respuesa;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public CatalogoTipoAnotacion getCatalogoTipoAnotacion() {
		return catalogoTipoAnotacion;
	}

	public void setCatalogoTipoAnotacion(CatalogoTipoAnotacion catalogoTipoAnotacion) {
		this.catalogoTipoAnotacion = catalogoTipoAnotacion;
	}


	public Button getComunidadesButton() {
		return comunidadesButton;
	}


	public void setComunidadesButton(Button comunidadesButton) {
		this.comunidadesButton = comunidadesButton;
	}


	public Button getEditarComunidad() {
		return editarComunidad;
	}


	public void setEditarComunidad(Button editarComunidad) {
		this.editarComunidad = editarComunidad;
	}


	public ToggleButton getEsPublicaToggleButton() {
		return esPublicaToggleButton;
	}


	public void setEsPublicaToggleButton(ToggleButton esPublicaToggleButton) {
		this.esPublicaToggleButton = esPublicaToggleButton;
	}


	public ToggleButton getEsReasignableToggleButton() {
		return esReasignableToggleButton;
	}


	public void setEsReasignableToggleButton(ToggleButton esReasignableToggleButton) {
		this.esReasignableToggleButton = esReasignableToggleButton;
	}


	public Button getMiembrosButton() {
		return miembrosButton;
	}


	public void setMiembrosButton(Button miembrosButton) {
		this.miembrosButton = miembrosButton;
	}


	public Button getEventosButton() {
		return eventosButton;
	}


	public void setEventosButton(Button eventosButton) {
		this.eventosButton = eventosButton;
	}


	public Button getTiposButton() {
		return tiposButton;
	}


	public void setTiposButton(Button tiposButton) {
		this.tiposButton = tiposButton;
	}

	

}

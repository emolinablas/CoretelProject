package com.researchmobile.coretel.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	private Button guardarButton;
	private Button borrarButton;
	private ListView opcionesListView;
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
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comunidad);
		Bundle bundle = (Bundle)getIntent().getExtras();
		setCatalogoMiembro((CatalogoMiembro)bundle.get("catalogoMiembro"));
		setDetalleComunidad((DetalleComunidad)bundle.get("detallecomunidad"));
		setMensaje(new Mensaje());
		setNombreTextView((TextView)findViewById(R.id.comunidad_nombre_textview));
		getNombreTextView().setText(getDetalleComunidad().getNombre());
		setDescripcionTextView((TextView)findViewById(R.id.comunidad_descripcion_textview));
		getDescripcionTextView().setText(getDetalleComunidad().getDescripcion());
		setGuardarButton((Button)findViewById(R.id.comunidades_guardar_button));
		setBorrarButton((Button)findViewById(R.id.comunidad_borrar_button));
		getGuardarButton().setOnClickListener(this);
		getBorrarButton().setOnClickListener(this);
		setOpcionesListView((ListView)findViewById(R.id.comunidad_lista_listview));
		
		getOpcionesListView().setAdapter(new ArrayAdapter<String>(this, 
				R.layout.lista_lobby,
				R.id.lista_lobby_textview,
				ListaOpciones()));
		getOpcionesListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			    
		getOpcionesListView().setOnItemClickListener(new OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
		    	if (position == 0){
		    		IniciaMiembros();
		    	}else if (position == 1){
		    		IniciaEventos();
		    	}else if (position == 2){
		    		new SeleccionAsync().execute("");
		    	}
		    }
		});
		
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
                		finish();
                	}else{
                		Toast.makeText(getBaseContext(), respuestaEliminar.getMensaje(), Toast.LENGTH_SHORT).show();
                	}
                }
         }
   }
    
    private void eliminarComunidad(){
    	RequestWS request = new RequestWS();
    	respuestaEliminar = request.eliminaComunidad(getDetalleComunidad().getId(), User.getUserId());
    }


	protected void IniciaTipos() {
		ConnectState connect = new ConnectState();
		if (connect.isConnectedToInternet(Comunidad.this)){
			RequestWS request = new RequestWS();
			setCatalogoTipoAnotacion(request.BuscarTiposEventos(getDetalleComunidad().getId()));
			
		}
		
		Intent intent = new Intent(Comunidad.this, TipoEvento.class);
		intent.putExtra("idComunidad", getDetalleComunidad().getId());
		intent.putExtra("catalogoTipoAnotacion", getCatalogoTipoAnotacion());
		startActivity(intent);
	}

	protected void IniciaEventos() {
		Intent intent = new Intent(Comunidad.this, ListaEventos.class);
		intent.putExtra("idComunidad", getDetalleComunidad().getId());
		startActivity(intent);
	}

	protected void IniciaMiembros() {
		Intent intent = new Intent(Comunidad.this, Miembros.class);
		intent.putExtra("catalogoMiembro", getCatalogoMiembro());
		startActivity(intent);
	}

	private String[] ListaOpciones() {
		
		String[] opciones = {"Miembros", "Eventos", "Tipos de Eventos"};
		return opciones;
	}

	@Override
	public void onClick(View view) {
		if (view == getGuardarButton()){
			Toast.makeText(getBaseContext(), "En proceso de desarrollo", Toast.LENGTH_SHORT).show();
		}else if (view == getBorrarButton()){
			eliminaDialog();
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
                     Toast.makeText(getBaseContext(), "Operación cancelada", Toast.LENGTH_SHORT).show();
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

	public Button getGuardarButton() {
		return guardarButton;
	}

	public void setGuardarButton(Button guardarButton) {
		this.guardarButton = guardarButton;
	}

	public Button getBorrarButton() {
		return borrarButton;
	}

	public void setBorrarButton(Button borrarButton) {
		this.borrarButton = borrarButton;
	}

	public ListView getOpcionesListView() {
		return opcionesListView;
	}

	public void setOpcionesListView(ListView opcionesListView) {
		this.opcionesListView = opcionesListView;
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
}

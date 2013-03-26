package com.researchmobile.coretel.supervision.ws;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

import com.researchmobile.coretel.supervision.entity.AnotacionAsignacion;
import com.researchmobile.coretel.supervision.entity.CatalogoAsignacion;
import com.researchmobile.coretel.supervision.entity.CatalogoSupervisor;
import com.researchmobile.coretel.supervision.entity.Multimedia;
import com.researchmobile.coretel.supervision.entity.RespuestaWS;
import com.researchmobile.coretel.supervision.entity.Supervisor;
import com.researchmobile.coretel.supervision.entity.UserAsignacion;

public class RequestWSAsignacion {
	private static final String WS_LOGIN = "ws_login.php?usuario=";
	private static final String WS_ASIGNACIONES = "ws_anotacion.php?usuario=";
	private static final String WS_MARCARASIGNACION = "ws_view_anotacion.php?id=";
	private static final String WS_SUPERVISORES = "ws_supervisor.php?padre=";
	
	private ConnectWS connectWS = new ConnectWS();
	
	public boolean LoginRecibelo(UserAsignacion user)
	{
	  JSONObject jsonObject = null;
	  String finalURL = WS_LOGIN + UserAsignacion.getUsername()+ "&clave="+ UserAsignacion.getPassword();
	  boolean respuesta = false;
	   try
	   {
		jsonObject = connectWS.Login(finalURL);
		if(jsonObject != null)
		{
		respuesta = Boolean.parseBoolean(jsonObject.getString("resultado"));
		JSONArray datosUsuario = jsonObject.getJSONArray("usuario");
		JSONObject id = (JSONObject) datosUsuario.get(0);
		UserAsignacion.setUserId(id.getString("id"));
		UserAsignacion.setEmail(id.getString("email"));
		UserAsignacion.setIdTipoUsuario(id.getString("id_tipo_usuario"));
		UserAsignacion.setActivo(id.getString("activo"));
		UserAsignacion.setIdPadre(id.getString("id_padre"));
		Log.v("pio", "id user = " + UserAsignacion.getUserId());
		System.out.println("Resultado = " + respuesta);
		return respuesta;
	   }
	   else
	   {
		   return respuesta;
	   }
	}
	 catch(JSONException e)
	 {
		 return respuesta;
	 }
}	 
	
	public CatalogoSupervisor buscaSupervisores() {
		JSONObject jsonObject = null;
		CatalogoSupervisor catalogo = new CatalogoSupervisor();
		String finalURL = WS_SUPERVISORES + UserAsignacion.getIdPadre();
		RespuestaWS respuesta = new RespuestaWS();
		try {
			jsonObject = connectWS.buscarSupervisores(finalURL);
			if (jsonObject != null) {
				
				respuesta.setResultado(jsonObject.getBoolean("resultado"));
				respuesta.setMensaje(jsonObject.getString("mensaje"));
				
				if (respuesta.isResultado()){
					JSONArray datoSupervisor = jsonObject.getJSONArray("usuario");
					int tamano = datoSupervisor.length();
					Supervisor[] listaSupervisor = new Supervisor[tamano];
					for (int i = 0; i < tamano; i++){
						JSONObject jsonTemp = datoSupervisor.getJSONObject(i);
						Supervisor sup = new Supervisor();
						sup.setId(jsonTemp.getString("id"));
						sup.setNombre(jsonTemp.getString("nombre"));
						sup.setEmail(jsonTemp.getString("email"));
						sup.setTelefono(jsonTemp.getString("telefono"));
						listaSupervisor[i] = sup;
					}
					catalogo.setSupervisor(listaSupervisor);
				}else{
					respuesta.setResultado(false);
					respuesta.setMensaje("No se encontraron supervisores");
				}
				catalogo.setRespuestaWS(respuesta);
				return catalogo;
				
			} else {
				respuesta.setResultado(false);
				respuesta.setMensaje("Problemas al cargar supervisores");
				catalogo.setRespuestaWS(respuesta);
				return catalogo;
			}
		} catch (JSONException e) {
			return catalogo;
		}
	}
	
	public RespuestaWS mandarFotoRespuesta(String imagen, String id) {
		Log.v("pio", "imagen = " + imagen);
		final List<NameValuePair> nombresArchivos = new ArrayList<NameValuePair>(2);
		nombresArchivos.add(new BasicNameValuePair("id", id));
		nombresArchivos.add(new BasicNameValuePair("Filedate",Environment.getExternalStorageDirectory() + imagen) );
		post("http://23.23.1.2/WS/ws_upload_photo.php?", nombresArchivos);
		
		
		return null;
		
	}
	
	public void post(String url, List<NameValuePair> nameValuePairs) {
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    HttpPost httpPost = new HttpPost(url);

	    try {
	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	        Log.e("LOG", "Prueba envio foto 1");

	        for(int index=0; index < nameValuePairs.size(); index++) {
	            if(nameValuePairs.get(index).getName().equalsIgnoreCase("Filedata")) {
	            	Log.e("LOG", "Prueba envio foto 2");
	                // If the key equals to "image", we use FileBody to transfer the data
	                entity.addPart(nameValuePairs.get(index).getName(), new FileBody(new File (nameValuePairs.get(index).getValue())));
	                Log.e("LOG", "Prueba envio foto 2");
	            } else {
	                // Normal string data
	                entity.addPart(nameValuePairs.get(index).getName(), new StringBody(nameValuePairs.get(index).getValue()));
	                Log.e("LOG", "Prueba envio foto 3");
	            }
	        }

	        Log.e("LOG", "Prueba envio foto 4");
	        httpPost.setEntity(entity);

	        Log.e("LOG", "Prueba envio foto 5");
	        HttpResponse response = httpClient.execute(httpPost, localContext);
	        Log.e("LOG", "Prueba envio foto 6, response = " + response.getParams());
	        Log.e("LOG", "Prueba envio foto 6, response = " + response.hashCode());
	        Log.e("LOG", "Prueba envio foto 6, response = " + response.getStatusLine());
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void marcarAsignacion(String asignacion) {
		// TODO Auto-generated method stub
		String finalURL = WS_MARCARASIGNACION + asignacion;
		Log.v("pio", "url marcar asignacion = " + finalURL);
		connectWS.marcarAsignacion(finalURL);
	}
	
			public CatalogoAsignacion CatalogoAsignacion(String id)
		{	
			JSONObject jsonObject = null;
			String finalURL = WS_ASIGNACIONES + id + "&asignados=1";
			Log.v("pio", "url asignaciones = " + finalURL);
			RespuestaWS respuesta = new RespuestaWS();
			CatalogoAsignacion catalogo = new CatalogoAsignacion();
			
			try
			{
				jsonObject = connectWS.CatalogoAsignacion(finalURL);
				 if(jsonObject != null)
				 {
					System.out.println("CatalogoAsignacion - != null");
					respuesta.setResultado(jsonObject.getBoolean("resultado"));
					respuesta.setMensaje(jsonObject.getString("mensaje"));
					catalogo.setRespuesta(respuesta);
						if(respuesta.isResultado())
						{
							System.out.println("CatalogoAsignacion - respuesta true");
							JSONArray jsonArray = jsonObject.getJSONArray("anotacion");
							
							int tamano = jsonArray.length();
							AnotacionAsignacion[] asignacion = new AnotacionAsignacion[tamano];
								for(int i=0; i<tamano; i++)
								{
									System.out.println("CatalogoAsignacion - cargando asignacion");
									JSONObject mJson = jsonArray.getJSONObject(i);
									AnotacionAsignacion asignacionTemp = new AnotacionAsignacion();
									asignacionTemp.setDescripcion(mJson.getString("descripcion"));
									asignacionTemp.setActivo(Integer.parseInt(mJson.getString("activo")));
									asignacionTemp.setId_estado(Integer.parseInt(mJson.getString("id_estado")));
									asignacionTemp.setFecha_registro(mJson.getString("fecha_registro"));
									asignacionTemp.setLatitud(Float.parseFloat(mJson.getString("latitud")));
									asignacionTemp.setLongitud(Float.parseFloat(mJson.getString("longitud")));
									asignacionTemp.setArchivo(mJson.getString("archivo"));
									asignacionTemp.setNombreComunidad(mJson.getString("nombreComunidad"));
									asignacionTemp.setNombreUsuario(mJson.getString("nombreUsuario"));
									asignacionTemp.setId(mJson.getString("id"));
									asignacionTemp.setEstadoAnotacion(mJson.getString("estadoAnotacion"));
									asignacionTemp.setNombreTipoAnotacion(mJson.getString("nombreTipoAnotacion"));
									asignacionTemp.setIcono(mJson.getString("icono"));
									asignacionTemp.setNombreSupervisor(mJson.getString("nombreSupervisor"));
									//agregado 25032013
									asignacionTemp.setArchivoNuevo(mJson.getString("archivoNuevo"));
									asignacionTemp.setFechaasignado(mJson.getString("fecha_asignado"));
									//
									asignacionTemp.setOrden(mJson.getString("orden"));
									asignacionTemp.setVisto(Integer.parseInt(mJson.getString("visto")));
									//agregado 25032013
									asignacionTemp.setFechaestado(mJson.getString("fecha_estado"));
									//
									asignacionTemp.setRespuesta(mJson.getString("respuesta"));
									asignacionTemp.setId_comunidad(Integer.parseInt(mJson.getString("id_comunidad")));
									//agregado 25032013
									asignacionTemp.setAsignacionDescripcion(mJson.getString("asignacionDescripcion"));
									//
									asignacionTemp.setFormat_fecha_asignado(mJson.getString("format_fecha_asignado"));
									asignacionTemp.setHace_tiempo(mJson.getString("hace_tiempo"));
									//aqui Multimedia el cual es un array
									
									 if(mJson.has("multimedia"))
									 {
										 JSONArray jsonArrayMultimedia = mJson.getJSONArray("multimedia");
										 System.out.println("Multimedia - Cargando campo Multimedia");
										 int tamanomultimedia = jsonArrayMultimedia.length();
										 Multimedia[] multimedia = new Multimedia[tamanomultimedia]; 
										 	for(int a=0; a<tamanomultimedia; a++)
										 	{
										 		
											  JSONObject mJsonMultimedia = jsonArrayMultimedia.getJSONObject(a);
											  Multimedia multimediaTemp = new Multimedia();
											  multimediaTemp.setId(Integer.parseInt(mJsonMultimedia.getString("id")));
											  multimediaTemp.setTipo_multimedia(Integer.parseInt(mJsonMultimedia.getString("tipo_multimedia")));
											  multimediaTemp.setDato(mJsonMultimedia.getString("dato"));
											  multimediaTemp.setAnotacion(Integer.parseInt(mJsonMultimedia.getString("anotacion")));
											  multimediaTemp.setNombreTipoMultimedia(mJsonMultimedia.getString("nombreTipoMultimedia"));
											  multimedia[a]=multimediaTemp;
											  System.out.println("Multimedia  - cargado FIN");
										 	}
									 asignacionTemp.setMultimedia(multimedia);
									 asignacion[i]=asignacionTemp; 
									 System.out.println("CatalogoAsignacion - cargado FIN");
									 
									 }
									 catalogo.setAnotacionasignacion(asignacion);
									 	
								}
								return catalogo;
									
						}
				 }
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
			
			return null;
		}

			
}

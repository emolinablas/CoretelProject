package com.researchmobile.coretel.supervision.ws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.researchmobile.coretel.supervision.entity.AnotacionAsignacion;
import com.researchmobile.coretel.supervision.entity.CatalogoAsignacion;
import com.researchmobile.coretel.supervision.entity.Multimedia;
import com.researchmobile.coretel.supervision.entity.RespuestaWS;
import com.researchmobile.coretel.supervision.entity.UserAsignacion;

public class RequestWSAsignacion {
	private static final String WS_LOGIN = "ws_login.php?usuario=";
	private static final String WS_ASIGNACIONES = "ws_anotacion.php?usuario=";
	
	private ConnectWS connectWS = new ConnectWS();
	
	public boolean LoginRecibelo(UserAsignacion user)
	{
	  JSONObject jsonObject = null;
	  String finalURL = WS_LOGIN + user.getUsername()+ "&clave="+ user.getPassword();
	  boolean respuesta = false;
	   try
	   {
		jsonObject = connectWS.Login(finalURL);
		if(jsonObject != null)
		{
		UserAsignacion usuario = new UserAsignacion();
		respuesta = Boolean.parseBoolean(jsonObject.getString("resultado"));
		JSONArray datosUsuario = jsonObject.getJSONArray("usuario");
		JSONObject id = (JSONObject) datosUsuario.get(0);
		usuario.setUserId(id.getString("id"));
		usuario.setEmail(id.getString("email"));
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

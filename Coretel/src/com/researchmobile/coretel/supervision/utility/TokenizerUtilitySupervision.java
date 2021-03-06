package com.researchmobile.coretel.supervision.utility;

import java.util.StringTokenizer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.researchmobile.coretel.view.R;

public class TokenizerUtilitySupervision {
	
	private static int TITULO = 0;
	private static int IDANOTACION = 1;
	private static int IDCOMUNIDAD = 2;
	private static int USUARIO_ANOTO = 3;
	private static int TIPO_ANOTACION = 4;
	private static int ICONO = 5;
	private static int ESTADO = 6;
	private static int ORDEN = 7;
	private static int VISTO = 8;
	private static int RESPUESTA = 9;
	private static int ASIGNACIONDESCRIPCION = 10;
	
	private static int DESCRIPCION = 0;
	private static int FECHA_REGISTRO = 1;
	private static int NOMBRE_USUARIO = 2;
	private static int NOMBRE_COMUNIDAD = 3;
	private static int IMAGEN = 4;

	private static String SEPARADOR = "=+=";
	
	public String titulo(String anotacion){
		return buscar(anotacion, TITULO);
	}
	
	public String idAnotacion(String anotacion) {
		return buscar(anotacion, IDANOTACION);
	}
	
	public String idComunidad(String anotacion) {
		return buscar(anotacion, IDCOMUNIDAD);
	}
	
	public String usuarioAnoto(String anotacion){
		return buscar(anotacion, USUARIO_ANOTO);
	}
	
	public String tipoAnotacion(String anotacion){
		return buscar(anotacion, TIPO_ANOTACION);
	}
	
	public String icono(String anotacion){
		return buscar(anotacion, ICONO);
	}
	
	public String estado(String anotacion){
		return buscar(anotacion, ESTADO);
	}
	
	public String visto(String anotacion){
		return buscar(anotacion, VISTO);
	}
	public String orden(String anotacion){
		return buscar(anotacion, ORDEN);
	}
	
	public String asignacionDescripcion(String anotacion){
		return buscar(anotacion, ASIGNACIONDESCRIPCION);
	}
	
	public String nombreEstado(String anotacion){
		String estado = buscar(anotacion, ESTADO);
		if (estado.equalsIgnoreCase("0")){
			estado = "Pendiente";
			return estado;
		}else
		
		if (estado.equalsIgnoreCase("1")){
			estado = "Pendiente";
			return estado;
		}else
		
		if (estado.equalsIgnoreCase("2")){
			estado = "Corregido";
			return estado;
		}else
		
		if (estado.equalsIgnoreCase("3")){
			estado = "Irreparable";
			return estado;
		}else
		
		if (estado.equalsIgnoreCase("4")){
			estado = "Corregido Reasignado";
			return estado;
		}else{
			estado = "Irreparable Reasignado";
			return estado;
		}
	}
	
	public String descripcion(String anotacion){
		return buscar(anotacion, DESCRIPCION);
	}
	
	public String fechaRegistro(String anotacion){
		return buscar(anotacion, FECHA_REGISTRO);
	}
	
	public String nombreUsuario(String anotacion){
		return buscar(anotacion, NOMBRE_USUARIO);
	}
	
	public String nombreComunidad(String anotacion){
		return buscar(anotacion, NOMBRE_COMUNIDAD);
	}
	
	public String imagen(String anotacion){
		return buscar(anotacion, IMAGEN);
	}
	
	public Drawable iconoAsignacion(Context context, String titulo) {
		String url = estado(titulo);
		Log.v("pio", "icono del evento = " + url);
		//pendiente_nuevo
		if (url.equalsIgnoreCase("1") && visto(titulo).equalsIgnoreCase("0")){
			return context.getResources().getDrawable(R.drawable.pinazul);
		}
		
		//pendiente_visto
		if (url.equalsIgnoreCase("1") && visto(titulo).equalsIgnoreCase("1")){
			return context.getResources().getDrawable(R.drawable.pinamarillo);
		}
		
		//corregido
		if (url.equalsIgnoreCase("2")){
			return context.getResources().getDrawable(R.drawable.pinverde);
		}
		
		//irreparable
		if (url.equalsIgnoreCase("3")){
			return context.getResources().getDrawable(R.drawable.pinrojo);
		}
		
		//corregido_reasignado
		if (url.equalsIgnoreCase("4")){
			return context.getResources().getDrawable(R.drawable.pinnaranja);
		}
		
		//irreparable_reasignado
		if(url.equalsIgnoreCase("5")){
			return context.getResources().getDrawable(R.drawable.pinnaranja);
		}
		
		return context.getResources().getDrawable(R.drawable.marker);
	}
	
	public Drawable buscaIcono(Context context, String url){
		if (nombrePin(url).equalsIgnoreCase("aboriginal.png")){
			return context.getResources().getDrawable(R.drawable.icono0);
		}
		
		if (nombrePin(url).equalsIgnoreCase("anniversary.png")){
			return context.getResources().getDrawable(R.drawable.icono1);
		}
		
		if (nombrePin(url).equalsIgnoreCase("bustour.png")){
			return context.getResources().getDrawable(R.drawable.icono2);
		}
		
		if (nombrePin(url).equalsIgnoreCase("caraccident.png")){
			return context.getResources().getDrawable(R.drawable.icono3);
		}
		
		if (nombrePin(url).equalsIgnoreCase("clock.png")){
			return context.getResources().getDrawable(R.drawable.icono4);
		}
		
		if (nombrePin(url).equalsIgnoreCase("crimescene.png")){
			return context.getResources().getDrawable(R.drawable.icono5);
		}
		
		if (nombrePin(url).equalsIgnoreCase("cruiseship.png")){
			return context.getResources().getDrawable(R.drawable.icono6);
		}
		
		if (nombrePin(url).equalsIgnoreCase("dogs_leash.png")){
			return context.getResources().getDrawable(R.drawable.icono7);
		}
		
		if (nombrePin(url).equalsIgnoreCase("fire.png")){
			return context.getResources().getDrawable(R.drawable.icono8);
		}
		
		if (nombrePin(url).equalsIgnoreCase("flag-export.png")){
			return context.getResources().getDrawable(R.drawable.icono9);
		}
		
		if (nombrePin(url).equalsIgnoreCase("information.png")){
			return context.getResources().getDrawable(R.drawable.icono10);
		}
		
		if (nombrePin(url).equalsIgnoreCase("linedown.png")){
			return context.getResources().getDrawable(R.drawable.icono11);
		}
		
		if (nombrePin(url).equalsIgnoreCase("palm-tree-export.png")){
			return context.getResources().getDrawable(R.drawable.icono12);
		}
		
		if (nombrePin(url).equalsIgnoreCase("party-2.png")){
			return context.getResources().getDrawable(R.drawable.icono13);
		}
		
		if (nombrePin(url).equalsIgnoreCase("pirates.png")){
			return context.getResources().getDrawable(R.drawable.icono14);
		}
		
		if (nombrePin(url).equalsIgnoreCase("planecrash.png")){
			return context.getResources().getDrawable(R.drawable.icono15);
		}
		
		if (nombrePin(url).equalsIgnoreCase("radiation.png")){
			return context.getResources().getDrawable(R.drawable.icono16);
		}
		
		if (nombrePin(url).equalsIgnoreCase("regroup.png")){
			return context.getResources().getDrawable(R.drawable.icono17);
		}
		
		if (nombrePin(url).equalsIgnoreCase("rescue-2.png")){
			return context.getResources().getDrawable(R.drawable.icono18);
		}
		
		if (nombrePin(url).equalsIgnoreCase("revolt.png")){
			return context.getResources().getDrawable(R.drawable.icono19);
		}
		
		if (nombrePin(url).equalsIgnoreCase("shooting.png")){
			return context.getResources().getDrawable(R.drawable.icono20);
		}
		
		if (nombrePin(url).equalsIgnoreCase("star-3.png")){
			return context.getResources().getDrawable(R.drawable.icono21);
		}
		
		if (nombrePin(url).equalsIgnoreCase("tornado-2.png")){
			return context.getResources().getDrawable(R.drawable.icono22);
		}
		
		if (nombrePin(url).equalsIgnoreCase("walkingtour.png")){
			return context.getResources().getDrawable(R.drawable.icono23);
		}
		
		if (nombrePin(url).equalsIgnoreCase("world.png")){
			return context.getResources().getDrawable(R.drawable.icono24);
		}
		
		return context.getResources().getDrawable(R.drawable.marker);
	}
	
	public Drawable iconoResource(Context context, String titulo) {
		String url = icono(titulo);
		Log.e("TT", "icono del evento = " + url);
		if (nombrePin(url).equalsIgnoreCase("aboriginal.png")){
			return context.getResources().getDrawable(R.drawable.icono0);
		}
		
		if (nombrePin(url).equalsIgnoreCase("anniversary.png")){
			return context.getResources().getDrawable(R.drawable.icono1);
		}
		
		if (nombrePin(url).equalsIgnoreCase("bustour.png")){
			return context.getResources().getDrawable(R.drawable.icono2);
		}
		
		if (nombrePin(url).equalsIgnoreCase("caraccident.png")){
			return context.getResources().getDrawable(R.drawable.icono3);
		}
		
		if (nombrePin(url).equalsIgnoreCase("clock.png")){
			return context.getResources().getDrawable(R.drawable.icono4);
		}
		
		if (nombrePin(url).equalsIgnoreCase("crimescene.png")){
			return context.getResources().getDrawable(R.drawable.icono5);
		}
		
		if (nombrePin(url).equalsIgnoreCase("cruiseship.png")){
			return context.getResources().getDrawable(R.drawable.icono6);
		}
		
		if (nombrePin(url).equalsIgnoreCase("dogs_leash.png")){
			return context.getResources().getDrawable(R.drawable.icono7);
		}
		
		if (nombrePin(url).equalsIgnoreCase("fire.png")){
			return context.getResources().getDrawable(R.drawable.icono8);
		}
		
		if (nombrePin(url).equalsIgnoreCase("flag-export.png")){
			return context.getResources().getDrawable(R.drawable.icono9);
		}
		
		if (nombrePin(url).equalsIgnoreCase("information.png")){
			return context.getResources().getDrawable(R.drawable.icono10);
		}
		
		if (nombrePin(url).equalsIgnoreCase("linedown.png")){
			return context.getResources().getDrawable(R.drawable.icono11);
		}
		
		if (nombrePin(url).equalsIgnoreCase("palm-tree-export.png")){
			return context.getResources().getDrawable(R.drawable.icono12);
		}
		
		if (nombrePin(url).equalsIgnoreCase("party-2.png")){
			return context.getResources().getDrawable(R.drawable.icono13);
		}
		
		if (nombrePin(url).equalsIgnoreCase("pirates.png")){
			return context.getResources().getDrawable(R.drawable.icono14);
		}
		
		if (nombrePin(url).equalsIgnoreCase("planecrash.png")){
			return context.getResources().getDrawable(R.drawable.icono15);
		}
		
		if (nombrePin(url).equalsIgnoreCase("radiation.png")){
			return context.getResources().getDrawable(R.drawable.icono16);
		}
		
		if (nombrePin(url).equalsIgnoreCase("regroup.png")){
			return context.getResources().getDrawable(R.drawable.icono17);
		}
		
		if (nombrePin(url).equalsIgnoreCase("rescue-2.png")){
			return context.getResources().getDrawable(R.drawable.icono18);
		}
		
		if (nombrePin(url).equalsIgnoreCase("revolt.png")){
			return context.getResources().getDrawable(R.drawable.icono19);
		}
		
		if (nombrePin(url).equalsIgnoreCase("shooting.png")){
			return context.getResources().getDrawable(R.drawable.icono20);
		}
		
		if (nombrePin(url).equalsIgnoreCase("star-3.png")){
			return context.getResources().getDrawable(R.drawable.icono21);
		}
		
		if (nombrePin(url).equalsIgnoreCase("tornado-2.png")){
			return context.getResources().getDrawable(R.drawable.icono22);
		}
		
		if (nombrePin(url).equalsIgnoreCase("walkingtour.png")){
			return context.getResources().getDrawable(R.drawable.icono23);
		}
		
		if (nombrePin(url).equalsIgnoreCase("world.png")){
			return context.getResources().getDrawable(R.drawable.icono24);
		}
		
		return context.getResources().getDrawable(R.drawable.marker);
	}
	
	private String buscar(String anotacion, int posicion){
		Log.v("pio", "tokenizer = " + anotacion);
		StringTokenizer tokenizer = new StringTokenizer(anotacion, SEPARADOR);
		int i = 0;
		if (tokenizer.countTokens() < 5){
			return "";
		}
		String[] vector = new String[tokenizer.countTokens()];
		while(tokenizer.hasMoreTokens()){
			vector[i] = isNull(tokenizer.nextToken());
			i++;
		}
		if (posicion > vector.length){
			return "";
		}
		return vector[posicion];
	}
	
	private String isNull(String posicion){
		String result = "-";
		if (posicion == null || posicion.equalsIgnoreCase("")){
			return result;
		}else{
			return posicion;
		}
	}
	
	private String nombrePin(String icono){
		
		StringTokenizer tokenizer = new StringTokenizer(icono, "/");
		int i = 0;
		String[] vector = new String[tokenizer.countTokens()];
		int tamano = vector.length;
		if (tamano > 0){
			while(tokenizer.hasMoreTokens()){
				vector[i] = tokenizer.nextToken();
				i++;
			}
			return vector[tamano - 1];
		}
		
		return "";
		
	}

}

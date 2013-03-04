package com.researchmobile.coretel.utility;

import java.util.StringTokenizer;

import alvarado.wuil.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class TokenizerUtility {
	
	private static int TITULO = 0;
	private static int IDANOTACION = 1;
	private static int IDCOMUNIDAD = 2;
	private static int USUARIO_ANOTO = 3;
	private static int TIPO_ANOTACION = 4;
	private static int ICONO = 5;
	
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
	
	public Drawable iconoResource(Context context, String titulo) {
		String url = icono(titulo);
		
		if (url.equalsIgnoreCase("img/markers/airshow-2.png")){
			return context.getResources().getDrawable(R.drawable.icono0);
		}
		
		if (url.equalsIgnoreCase("img/markers/anniversary.png")){
			return context.getResources().getDrawable(R.drawable.icono1);
		}
		
		if (url.equalsIgnoreCase("img/markers/anthropo.png")){
			return context.getResources().getDrawable(R.drawable.icono2);
		}
		
		if (url.equalsIgnoreCase("img/markers/aquarium.png")){
			return context.getResources().getDrawable(R.drawable.icono3);
		}
		
		if (url.equalsIgnoreCase("img/markers/art-museum-2.png")){
			return context.getResources().getDrawable(R.drawable.icono4);
		}
		
		if (url.equalsIgnoreCase("img/markers/billiard-2.png")){
			return context.getResources().getDrawable(R.drawable.icono5);
		}
		
		if (url.equalsIgnoreCase("img/markers/bowling.png")){
			return context.getResources().getDrawable(R.drawable.icono6);
		}
		
		if (url.equalsIgnoreCase("img/markers/bullfight.png")){
			return context.getResources().getDrawable(R.drawable.icono7);
		}
		
		if (url.equalsIgnoreCase("img/markers/bustour.png")){
			return context.getResources().getDrawable(R.drawable.icono8);
		}
		
		if (url.equalsIgnoreCase("img/markers/casino-2.png")){
			return context.getResources().getDrawable(R.drawable.icono9);
		}
		
		if (url.equalsIgnoreCase("img/markers/cinema.png")){
			return context.getResources().getDrawable(R.drawable.icono10);
		}
		
		if (url.equalsIgnoreCase("img/markers/circus.png")){
			return context.getResources().getDrawable(R.drawable.icono11);
		}
		
		if (url.equalsIgnoreCase("img/markers/coins.png")){
			return context.getResources().getDrawable(R.drawable.icono12);
		}
		
		if (url.equalsIgnoreCase("img/markers/comedyclub.png")){
			return context.getResources().getDrawable(R.drawable.icono13);
		}
		
		if (url.equalsIgnoreCase("img/markers/dance_class.png")){
			return context.getResources().getDrawable(R.drawable.icono14);
		}
		
		if (url.equalsIgnoreCase("img/markers/dancinghall.png")){
			return context.getResources().getDrawable(R.drawable.icono15);
		}
		
		if (url.equalsIgnoreCase("img/markers/dinopark.png")){
			return context.getResources().getDrawable(R.drawable.icono16);
		}
		
		if (url.equalsIgnoreCase("img/markers/ferriswheel.png")){
			return context.getResources().getDrawable(R.drawable.icono17);
		}
		
		if (url.equalsIgnoreCase("img/markers/festival.png")){
			return context.getResources().getDrawable(R.drawable.icono18);
		}
		
		if (url.equalsIgnoreCase("img/markers/fireworks.png")){
			return context.getResources().getDrawable(R.drawable.icono19);
		}
		
		if (url.equalsIgnoreCase("img/markers/fishing.png")){
			return context.getResources().getDrawable(R.drawable.icono20);
		}
		
		if (url.equalsIgnoreCase("img/markers/geocaching-3.png")){
			return context.getResources().getDrawable(R.drawable.icono21);
		}
		
		if (url.equalsIgnoreCase("img/markers/historical_museum.png")){
			return context.getResources().getDrawable(R.drawable.icono22);
		}
		
		if (url.equalsIgnoreCase("img/markers/hookah_final.png")){
			return context.getResources().getDrawable(R.drawable.icono23);
		}
		
		if (url.equalsIgnoreCase("img/markers/jazzclub.png")){
			return context.getResources().getDrawable(R.drawable.icono24);
		}
		
		return context.getResources().getDrawable(R.drawable.marker);
	}
	
	private String buscar(String anotacion, int posicion){
		Log.e("TT", "tokenizer - string = " + anotacion + " posicion = " + posicion);
		StringTokenizer tokenizer = new StringTokenizer(anotacion, SEPARADOR);
		int i = 0;
		Log.e("TT", "tamaño tokenizer = " + tokenizer.countTokens());
		if (tokenizer.countTokens() < 5){
			return "";
		}
		String[] vector = new String[tokenizer.countTokens()];
		while(tokenizer.hasMoreTokens()){
			vector[i] = tokenizer.nextToken();
			i++;
		}
		
		return vector[posicion];
	}

}

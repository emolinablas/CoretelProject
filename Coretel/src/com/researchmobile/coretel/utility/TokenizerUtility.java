package com.researchmobile.coretel.utility;

import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
	
	public String nombreCoString(String anotacion){
		return buscar(anotacion, NOMBRE_COMUNIDAD);
	}
	
	public String imagen(String anotacion){
		return buscar(anotacion, IMAGEN);
	}
	private String buscar(String anotacion, int posicion){
		Log.e("TT", "tokenizer - string = " + anotacion + " posicion = " + posicion);
		StringTokenizer tokenizer = new StringTokenizer(anotacion, SEPARADOR);
		int i = 0;
		String[] vector = new String[tokenizer.countTokens()];
		while(tokenizer.hasMoreTokens()){
			vector[i] = tokenizer.nextToken();
			i++;
		}
		return vector[posicion];
	}
	
	
}

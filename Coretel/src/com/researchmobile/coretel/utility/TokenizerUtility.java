package com.researchmobile.coretel.utility;

import java.util.StringTokenizer;

import android.content.Context;
import android.widget.Toast;

public class TokenizerUtility {
	private static int IDANOTACION = 0;
	private static int IDCOMUNIDAD = 1;
	private static int NOMBRE = 2;
	private static int COMUNIDAD = 3;
	private static String SEPARADOR = "%";
	
	public String idAnotacion(String anotacion) {
		return buscar(anotacion, IDANOTACION);
	}
	
	public String idComunidad(String anotacion) {
		return buscar(anotacion, IDCOMUNIDAD);
	}
	
	public String nombre(String anotacion) {
		return buscar(anotacion, NOMBRE);
	}
	
	public String comunidad(String anotacion) {
		return buscar(anotacion, COMUNIDAD);
	}
	
	private String buscar(String anotacion, int posicion){
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

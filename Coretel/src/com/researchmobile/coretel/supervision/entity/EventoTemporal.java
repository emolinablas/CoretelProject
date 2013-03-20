package com.researchmobile.coretel.supervision.entity;

import java.io.Serializable;

public class EventoTemporal implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static float latitud;
	private static float longitud;
	private static int control;
	private static boolean nuevo;
	public static float getLatitud() {
		return latitud;
	}
	public static void setLatitud(float latitud) {
		EventoTemporal.latitud = latitud;
	}
	public static float getLongitud() {
		return longitud;
	}
	public static void setLongitud(float longitud) {
		EventoTemporal.longitud = longitud;
	}
	public static int getControl() {
		return control;
	}
	public static void setControl(int control) {
		EventoTemporal.control = control;
	}
	public static boolean isNuevo() {
		return nuevo;
	}
	public static void setNuevo(boolean nuevo) {
		EventoTemporal.nuevo = nuevo;
	}
	

}

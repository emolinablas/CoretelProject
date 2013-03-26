package com.researchmobile.coretel.entity;

import java.io.Serializable;

public class DetalleComunidad implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	private String descripcion;
	private String activo;
	private String idDuenno;
	private String espublica;
	private String esreasignable;
	RespuestaWS respuestaWS;
	
	
	public String toString(){
		return nombre + descripcion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getActivo() {
		return activo;
	}
	public void setActivo(String activo) {
		this.activo = activo;
	}
	public RespuestaWS getRespuestaWS() {
		return respuestaWS;
	}
	public void setRespuestaWS(RespuestaWS respuestaWS) {
		this.respuestaWS = respuestaWS;
	}
	public String getIdDuenno() {
		return idDuenno;
	}
	public void setIdDuenno(String idDuenno) {
		this.idDuenno = idDuenno;
	}
	public String getEspublica() {
		return espublica;
	}
	public void setEspublica(String espublica) {
		this.espublica = espublica;
	}
	public String getEsreasignable() {
		return esreasignable;
	}
	public void setEsreasignable(String esreasignable) {
		this.esreasignable = esreasignable;
	}
	
	
}



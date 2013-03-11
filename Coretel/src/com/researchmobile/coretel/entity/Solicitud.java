package com.researchmobile.coretel.entity;

import java.io.Serializable;

public class Solicitud implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String fechaEnviada;
	private String idUsuario;
	private String idComunidad;
	private String estado;
	private String nombreUsuario;
	private String nombreComunidad;
	private String usuarioCreo;
	private String miembros;
	
	public String toString(){
		String estP = "Pendiente";
		String estA = "Aceptado";
		
			if(estado == "1"){
				return nombreUsuario + "\n" + nombreComunidad + "   " + estA;
				}else{
				return nombreUsuario + "\n" + nombreComunidad + "   " + estP;
				}
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFechaEnviada() {
		return fechaEnviada;
	}
	public void setFechaEnviada(String fechaEnviada) {
		this.fechaEnviada = fechaEnviada;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdComunidad() {
		return idComunidad;
	}
	public void setIdComunidad(String idComunidad) {
		this.idComunidad = idComunidad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombreComunidad() {
		return nombreComunidad;
	}
	public void setNombreComunidad(String nombreComunidad) {
		this.nombreComunidad = nombreComunidad;
	}
	public String getUsuarioCreo() {
		return usuarioCreo;
	}
	public void setUsuarioCreo(String usuarioCreo) {
		this.usuarioCreo = usuarioCreo;
	}
	public String getMiembros() {
		return miembros;
	}
	public void setMiembros(String miembros) {
		this.miembros = miembros;
	}
	
	
}


package com.researchmobile.coretel.supervision.entity;

import java.io.Serializable;

public class Supervisor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nombre;
	private String email;
	private String telefono;
	
	public String toString(){
		return nombre;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}

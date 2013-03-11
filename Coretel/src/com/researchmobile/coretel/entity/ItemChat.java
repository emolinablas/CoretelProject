package com.researchmobile.coretel.entity;

public class ItemChat {
	protected long id;
	protected String nombre;
	protected String mensaje;
	protected String avatar;
	
	
	public ItemChat() {
		this.nombre = "";
		this.mensaje = "";
		this.avatar = "";
	}
	
	public ItemChat(long id, String nombre, String mensaje) {
		this.id = id;
		this.nombre = nombre;
		this.mensaje = mensaje;
	}
	
	public ItemChat(long id, String nombre, String mensaje, String rutaImagen) {
		this.id = id;
		this.nombre = nombre;
		this.mensaje = mensaje;
		this.avatar = rutaImagen;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}

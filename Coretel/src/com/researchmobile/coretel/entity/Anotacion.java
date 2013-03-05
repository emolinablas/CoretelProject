package com.researchmobile.coretel.entity;

import java.io.Serializable;

public class Anotacion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String descripcion;
	private int activo;
	private int idEstado;
	private String fecha_registro;
	private float latitud;
	private float longitud;
	private String imagen;
	private String nombreComunidad;
	private String nombreUsuario;
	private String idAnotacion;
	private String estadoAnotacion;
	private String nombreTipoAnotacion;
	private String icono;
	private String visto;
	private String asignado;
	private String respuesta;
	private String nombreSupervisor;
	private String orden;
	private String idcomunidad;
	private String formatFechaAsignado;
	private String haceTiempo;
	
	public String getIdAnotacion() {
		return idAnotacion;
	}
	public void setIdAnotacion(String idAnotacion) {
		this.idAnotacion = idAnotacion;
	}
	public String getIdcomunidad() {
		return idcomunidad;
	}
	public void setIdcomunidad(String idcomunidad) {
		this.idcomunidad = idcomunidad;
	}
	public float getLatitud() {
		return latitud;
	}
	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}
	public float getLongitud() {
		return longitud;
	}
	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	public int getActivo() {
		return activo;
	}
	public void setActivo(int activo) {
		this.activo = activo;
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
	public String getNombreTipoAnotacion() {
		return nombreTipoAnotacion;
	}
	public void setNombreTipoAnotacion(String nombreTipoAnotacion) {
		this.nombreTipoAnotacion = nombreTipoAnotacion;
	}
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public String getEstadoAnotacion() {
		return estadoAnotacion;
	}
	public void setEstadoAnotacion(String estadoAnotacion) {
		this.estadoAnotacion = estadoAnotacion;
	}
	public String getVisto() {
		return visto;
	}
	public void setVisto(String visto) {
		this.visto = visto;
	}
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getNombreSupervisor() {
		return nombreSupervisor;
	}
	public void setNombreSupervisor(String nombreSupervisor) {
		this.nombreSupervisor = nombreSupervisor;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getFormatFechaAsignado() {
		return formatFechaAsignado;
	}
	public void setFormatFechaAsignado(String formatFechaAsignado) {
		this.formatFechaAsignado = formatFechaAsignado;
	}
	public String getHaceTiempo() {
		return haceTiempo;
	}
	public void setHaceTiempo(String haceTiempo) {
		this.haceTiempo = haceTiempo;
	}
}

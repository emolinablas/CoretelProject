package com.researchmobile.coretel.supervision.entity;

import java.io.Serializable;

public class AnotacionAsignacion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static String descripcion;
	private static Integer activo;
	private static Integer id_estado;
	private static String fecha_registro;
	private static float latitud;
	private static float longitud;
	private static String archivo;
	private static String nombreComunidad;
	private static String nombreUsuario;
	private static String id;
	private static String estadoAnotacion;
	private static String nombreTipoAnotacion;
	private static String icono;
	private static Integer visto;
	private static String asignado;
	private static String respuesta;
	private static String nombreSupervisor;
	private static String orden;
	private static Integer id_comunidad;
	private static String format_fecha_asignado;
	private static Multimedia[] multimedia;
	private static String hace_tiempo;
	//agregados por cambios en web service 25032013 
	private static String archivoNuevo;
	private static String fechaasignado;
	private static String fechaestado;
	private static String asignacionDescripcion;
	
	
	
	public String getArchivoNuevo() {
		return archivoNuevo;
	}
	public void setArchivoNuevo(String archivoNuevo) {
		this.archivoNuevo = archivoNuevo;
	}
	public String getFechaasignado() {
		return fechaasignado;
	}
	public void setFechaasignado(String fechaasignado) {
		this.fechaasignado = fechaasignado;
	}
	public String getFechaestado() {
		return fechaestado;
	}
	public void setFechaestado(String fechaestado) {
		this.fechaestado = fechaestado;
	}
	public static String getAsignacionDescripcion() {
		return asignacionDescripcion;
	}
	public void setAsignacionDescripcion(String asignacionDescripcion) {
		this.asignacionDescripcion = asignacionDescripcion;
	}
	public String getHace_tiempo() {
		return hace_tiempo;
	}
	public void setHace_tiempo(String hace_tiempo) {
		this.hace_tiempo = hace_tiempo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	public Integer getId_estado() {
		return id_estado;
	}
	public void setId_estado(Integer id_estado) {
		this.id_estado = id_estado;
	}
	public String getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public String getNombreComunidad() {
		return nombreComunidad;
	}
	public void setNombreComunidad(String nombreComunidad) {
		this.nombreComunidad = nombreComunidad;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEstadoAnotacion() {
		return estadoAnotacion;
	}
	public void setEstadoAnotacion(String estadoAnotacion) {
		this.estadoAnotacion = estadoAnotacion;
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
	public String getNombreSupervisor() {
		return nombreSupervisor;
	}
	public void setNombreSupervisor(String nombreSupervisor) {
		this.nombreSupervisor = nombreSupervisor;
	}
	
	
	public static String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public Integer getVisto() {
		return visto;
	}
	public void setVisto(Integer visto) {
		this.visto = visto;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public Integer getId_comunidad() {
		return id_comunidad;
	}
	public void setId_comunidad(Integer id_comunidad) {
		this.id_comunidad = id_comunidad;
	}
	public String getFormat_fecha_asignado() {
		return format_fecha_asignado;
	}
	public void setFormat_fecha_asignado(String format_fecha_asignado) {
		this.format_fecha_asignado = format_fecha_asignado;
	}
	public Multimedia[] getMultimedia() {
		return multimedia;
	}
	public void setMultimedia(Multimedia[] multimedia) {
		this.multimedia = multimedia;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
}

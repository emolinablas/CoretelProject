package com.researchmobile.coretel.entity;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static String userId;
	private static String fechaRegistro;
	private static String idTipoUsuario;
	private static String activo;
	private static String nombre;
	private static String username;
	private static String password;
	private static String email;
	private static String telefono;
	private static String avatar;
	private static String idPadre;
	private static String nivel;
	private static String superUsuario;
	private static String supervisorUsuario;
	private static boolean compartirGeoposicion;
	
	
	
	private RespuestaWS respuestaWS;
	
	public static String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public RespuestaWS getRespuestaWS() {
		return respuestaWS;
	}
	public void setRespuestaWS(RespuestaWS respuestaWS) {
		this.respuestaWS = respuestaWS;
	}
	public static String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static String getFechaRegistro() {
		return fechaRegistro;
	}
	public static void setFechaRegistro(String fechaRegistro) {
		User.fechaRegistro = fechaRegistro;
	}
	public static String getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public static void setIdTipoUsuario(String idTipoUsuario) {
		User.idTipoUsuario = idTipoUsuario;
	}
	public static String getActivo() {
		return activo;
	}
	public static void setActivo(String activo) {
		User.activo = activo;
	}
	public static String getNombre() {
		return nombre;
	}
	public static void setNombre(String nombre) {
		User.nombre = nombre;
	}
	public static String getTelefono() {
		return telefono;
	}
	public static void setTelefono(String telefono) {
		User.telefono = telefono;
	}
	public static String getAvatar() {
		return avatar;
	}
	public static void setAvatar(String avatar) {
		User.avatar = avatar;
	}
	public static String getIdPadre() {
		return idPadre;
	}
	public static void setIdPadre(String idPadre) {
		User.idPadre = idPadre;
	}
	public static String getNivel() {
		return nivel;
	}
	public static void setNivel(String nivel) {
		User.nivel = nivel;
	}
	public static String getSuperUsuario() {
		return superUsuario;
	}
	public static void setSuperUsuario(String superUsuario) {
		User.superUsuario = superUsuario;
	}
	public static String getSupervisorUsuario() {
		return supervisorUsuario;
	}
	public static void setSupervisorUsuario(String supervisorUsuario) {
		User.supervisorUsuario = supervisorUsuario;
	}
	public static boolean isCompartirGeoposicion() {
		return compartirGeoposicion;
	}
	public static void setCompartirGeoposicion(boolean compartirGeoposicion) {
		User.compartirGeoposicion = compartirGeoposicion;
	}
}

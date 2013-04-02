package com.researchmobile.coretel.supervision.entity;

import java.io.Serializable;

public class UserAsignacion implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static String userId;
	private static String idTipoUsuario;
	private static String activo;
	private static String username;
	private static String password;
	private static String email;
	private static String telefono;
	private static String avatar;
	private static String idPadre;
	private static String nivel;
	private static String superUsuario;
	private static String supervisorUsuario;
	private static boolean modotutorialsupervision;
	
	
	public static boolean isModotutorialsupervision() {
		return modotutorialsupervision;
	}
	public static void setModotutorialsupervision(boolean modotutorialsupervision) {
		UserAsignacion.modotutorialsupervision = modotutorialsupervision;
	}
	private RespuestaWS respuestaWs;
	
	public RespuestaWS getRespuestaWs() {
		return respuestaWs;
	}
	public void setRespuestaWs(RespuestaWS respuestaWs) {
		this.respuestaWs = respuestaWs;
	}
	public static String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		UserAsignacion.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		UserAsignacion.password = password;
	}
	public static String getUserId() {
		return userId;
	}
	public static void setUserId(String userId) {
		UserAsignacion.userId = userId;
	}
	
	
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		UserAsignacion.email = email;
	}
	public static String getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public static void setIdTipoUsuario(String idTipoUsuario) {
		UserAsignacion.idTipoUsuario = idTipoUsuario;
	}
	public static String getActivo() {
		return activo;
	}
	public static void setActivo(String activo) {
		UserAsignacion.activo = activo;
	}
	public static String getTelefono() {
		return telefono;
	}
	public static void setTelefono(String telefono) {
		UserAsignacion.telefono = telefono;
	}
	public static String getAvatar() {
		return avatar;
	}
	public static void setAvatar(String avatar) {
		UserAsignacion.avatar = avatar;
	}
	public static String getIdPadre() {
		return idPadre;
	}
	public static void setIdPadre(String idPadre) {
		UserAsignacion.idPadre = idPadre;
	}
	public static String getNivel() {
		return nivel;
	}
	public static void setNivel(String nivel) {
		UserAsignacion.nivel = nivel;
	}
	public static String getSuperUsuario() {
		return superUsuario;
	}
	public static void setSuperUsuario(String superUsuario) {
		UserAsignacion.superUsuario = superUsuario;
	}
	public static String getSupervisorUsuario() {
		return supervisorUsuario;
	}
	public static void setSupervisorUsuario(String supervisorUsuario) {
		UserAsignacion.supervisorUsuario = supervisorUsuario;
	}
	
	
}

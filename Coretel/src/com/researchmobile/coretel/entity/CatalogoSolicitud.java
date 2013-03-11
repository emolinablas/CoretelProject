package com.researchmobile.coretel.entity;

import java.io.Serializable;

public class CatalogoSolicitud implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Solicitud[] solicitud;
	private RespuestaWS respuestaWS;
	
	public Solicitud[] getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(Solicitud[] solicitud) {
		this.solicitud = solicitud;
	}
	public RespuestaWS getRespuestaWS() {
		return respuestaWS;
	}
	public void setRespuestaWS(RespuestaWS respuestaWS) {
		this.respuestaWS = respuestaWS;
	}

}

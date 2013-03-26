package com.researchmobile.coretel.supervision.entity;

import java.io.Serializable;

public class CatalogoSupervisor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Supervisor[] supervisor;
	private RespuestaWS respuestaWS;
	public Supervisor[] getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Supervisor[] supervisor) {
		this.supervisor = supervisor;
	}
	public RespuestaWS getRespuestaWS() {
		return respuestaWS;
	}
	public void setRespuestaWS(RespuestaWS respuestaWS) {
		this.respuestaWS = respuestaWS;
	}
	
}

package com.researchmobile.coretel.entity;

import java.io.Serializable;

public class Perfil implements Serializable {
 private static final long serialVersionUID = 1L;
 
 private int modotutorial;
 private long idDB;

public long getIdDB() {
	return idDB;
}

public void setIdDB(long idDB) {
	this.idDB = idDB;
}

public int getModotutorial() {
	return modotutorial;
}

public void setModotutorial(int modotutorial) {
	this.modotutorial = modotutorial;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}
 
}

package com.researchmobile.coretel.ws;

import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.android.dataframework.DataFramework;
import com.android.dataframework.Entity;
import com.researchmobile.coretel.entity.Perfil;
import com.researchmobile.coretel.entity.User;

public class RequestDB {
	
	public boolean modoTutorial (Context context){
		try {
			int modoTutorial = 0;
			List<Entity> categories = DataFramework.getInstance().getEntityList("perfil", User.getUserId());
			{
				Entity dato = categories.get(0);
				modoTutorial = dato.getInt("modotutorial");
				return modoTutorial > 0 ? true : false ;
			}
		} catch (Exception msj) {
			msj.printStackTrace();
			return false;
		}
	}
	
	public void desactivaModoTutorial (Context context){
		try {
			DataFramework.getInstance().open(context,"com.researchmobile.coretel.view");
			Entity dato = new Entity("perfil", "usuario = " + String.valueOf(User.getUserId()));
			dato.setValue("modotutorial", 1);
			dato.save();
		} catch (Exception msj) {

		}
	}
	

	// Insert Perfil
	public void insertaPerfil(Context context, Perfil perfil) {
		try {
			DataFramework.getInstance().open(context, "com.researchmobile.coretel.view");
			Entity ent = new Entity("perfil");
			ent.setValue("modotutorial", perfil.getModotutorial());
			ent.setValue("usuario", User.getUserId());
			ent.save();

		} catch (Exception msj) {
			msj.printStackTrace();
		}
	}
	
	public void verificaLogin(Context context){
		try{
			DataFramework.getInstance().open(context, "com.researchmobile.coretel.view");
			List<Entity> categories = DataFramework.getInstance().getEntityList("perfil", "usuario = " + User.getUserId());
			if (categories.size() > 0){
				
			}else{
				Perfil perfil = new Perfil();
				perfil.setModotutorial(0);
				insertaPerfil(context, perfil);
			}
		}catch(Exception exception){
			
		}
	}

	// Busqueda Perfil
	public Perfil perfilDB() {
		try {
			Perfil perfil = new Perfil();
			List<Entity> categories = DataFramework.getInstance().getEntityList("perfil");
			{
				Iterator<Entity> iter = categories.iterator();
				while (iter.hasNext()) {
					Entity datoperfil = (Entity) iter.next();
					perfil.setModotutorial(Integer.parseInt(datoperfil.getString("modotutorial")));

				}
				return perfil;
			}
		} catch (Exception msj) {
			msj.printStackTrace();
			return null;
		}

	}

	// UPDATE Perfil
	public void editarPerfil(Context context, Perfil seleccion) {
		try {
			DataFramework.getInstance().open(context,"com.researchmobile.coretel.view");
			Entity dato = new Entity("perfil", seleccion.getIdDB());
			dato.setValue("modotutorial", seleccion.getModotutorial());
			dato.save();
		} catch (Exception msj) {

		}
	}
}

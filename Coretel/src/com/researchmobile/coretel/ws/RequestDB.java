package com.researchmobile.coretel.ws;

import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.android.dataframework.DataFramework;
import com.android.dataframework.Entity;
import com.researchmobile.coretel.entity.Perfil;

public class RequestDB {

	//Insert Perfil
	public void insertaPerfil(Context context, Perfil perfil)
	{
	try
	 {
		DataFramework.getInstance().open(context, "com.researchmobile.coretel.entity");
		Entity ent= new Entity("perfil");
		ent.setValue("modotutorial", perfil.getModotutorial());
		ent.save();
		
	 }
		catch(Exception msj)
		{
			msj.printStackTrace();
		}
 }
	
	//Busqueda Perfil
	public Perfil perfilDB()
	{
		try
		{
			Perfil perfil = new Perfil();
			List<Entity> categories = DataFramework.getInstance().getEntityList("perfil");
			{
				Iterator<Entity> iter = categories.iterator();
				while(iter.hasNext())
				{
					Entity datoperfil = (Entity)iter.next();
					perfil.setModotutorial(Integer.parseInt(datoperfil.getString("modotutorial")));
					
				}
				return perfil;
			}
		}
		catch(Exception msj)
		{
			msj.printStackTrace();	
			return null;
		}
		
	}
	
	//UPDATE Perfil
	public void editarPerfil(Context context, Perfil seleccion)
	{
		try
		{
			DataFramework.getInstance().open(context, "com.researchmobile.coretel.entity");
			Entity dato = new Entity("perfil", seleccion.getIdDB());
			dato.setValue("modotutorial", seleccion.getModotutorial());
			dato.save();
		}
		 catch(Exception msj)
		 {
		
		 }
	}
}

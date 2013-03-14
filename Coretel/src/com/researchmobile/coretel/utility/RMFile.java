package com.researchmobile.coretel.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Environment;
import android.util.Log;

public class RMFile {
	
public void downloadImage(String  fileUrl){
        
	File file = new File("/mnt/sdcard/pasalo/" + fileUrl);
	if (!file.exists()) {
	  Log.d("test", "no existe la imagen = " + file);
	  Log.e("URL", fileUrl);
      URL myFileUrl =null;
          try {
              myFileUrl= new URL("http://23.23.1.2/" + fileUrl);
          } catch (MalformedURLException e) {
              // TODO Auto-generated catch block
          e.printStackTrace();
          }
          try {
        	  	URLConnection conexion = myFileUrl.openConnection();
				conexion.connect();
				File avatarDirectory = new File("/sdcard/pasalo/img/avatars/");
	        	avatarDirectory.mkdirs();
				int tamano = conexion.getContentLength();
				Log.e("test", "resultado busqueda imagen = " + tamano);
				InputStream input = new BufferedInputStream(myFileUrl.openStream());
				OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+ "/pasalo/" + fileUrl);
				Log.d("test", "no existe la imagen = " + fileUrl);
				byte datos[] = new byte[1024];
				int cuenta;
				long total = 0;
				
				while ((cuenta = input.read(datos)) != -1){
					total += cuenta;
					output.write(datos, 0, cuenta);
				}
        	  
        	  
        	  /*
              //***Para conectarse a internet
              HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
              conn.setDoInput(true);
              conn.connect();
              int length = conn.getContentLength();
              int[] bitmapData =new int[length];
              byte[] bitmapData2 =new byte[length];
              InputStream is = conn.getInputStream();
              //decodificar la imagen de internet en bmImg
              Bitmap bmImg = BitmapFactory.decodeStream(is);
              //***Para guardar la imagen en la SD
              String path = "/sdcard/pasalo/" + fileUrl;
              Log.d("test", "creando la imagen " + path);
              
              OutputStream output = new FileOutputStream(file);
				
				byte datos[] = new byte[1024];
				int cuenta;
				long total = 0;
				
				while ((cuenta = is.read(datos)) != -1){
					total += cuenta;
					output.write(datos, 0, cuenta);
				}
              is.close();
              //guardar la imagen en la ruta y en el formato especificado
              bmImg.compress(Bitmap.CompressFormat.JPEG, 90, output);
              output.flush();
              output.close();
              */
                 
          } catch (IOException e)
           {            
              e.printStackTrace();              
           }
      }else{
		Log.d("test", "ya existe la imagen");
	}
}
}

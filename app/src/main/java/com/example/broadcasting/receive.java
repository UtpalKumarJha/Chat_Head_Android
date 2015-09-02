
package com.example.broadcasting;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class receive extends BroadcastReceiver {

	public final String TAG	= "hello";
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		//Toast.makeText(context, "received and application started",Toast.LENGTH_SHORT).show();
		Bundle extras = intent.getExtras();
		if(extras==null)
			return;
	
		Object[] pdus = (Object[]) extras.get("pdus");
		for (int i = 0; i < pdus.length; i++)
	    {
		 SmsMessage SMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
		 String sender = SMessage.getOriginatingAddress();
		 String sms = SMessage.getMessageBody();

		 	Intent in = new Intent("SmsMessage.intent.MAIN").putExtra("sender", sender).putExtra("sms", sms);
			Toast.makeText(context, "received and application started",Toast.LENGTH_SHORT).show();
		 	in.setClass(context, chathead.class);

		 	context.startService(in);
        }
		
	}

	
	
	
}

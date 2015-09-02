package com.example.broadcasting;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;

public class floatingchat extends Activity 
{

	Button btn1,btn;
	EditText ed1,ed2,ed3;
	private Service servicing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		        setContentView(R.layout.floatinglayout);
				btn=(Button)findViewById(R.id.bt1);
				btn1=(Button)findViewById(R.id.bt2);
				ed1=(EditText)findViewById(R.id.ed1);
				ed2=(EditText)findViewById(R.id.ed2);
				ed3=(EditText)findViewById(R.id.ed3);
	}

	@Override
	protected void onResume() {
		
		servicing = new Service()
		{

			@Override
			public IBinder onBind(Intent intent) 
			{
				final String finalnumber = intent.getStringExtra("sender");
			    final String finalstring = intent.getStringExtra("sms");
			    ed1.setText(finalnumber);
			    ed2.setText(finalstring);
				return null;
			}
			
			
			
			
			
		};
		
		
		
		super.onResume();
	}
	

	@Override
	protected void onPause()
	{
	super.onPause();
	
	}
	
	
   
	
}


package com.example.broadcasting;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class floatingchat extends Activity 
{

	Button btn1,btn;
	EditText ed1,ed2,ed3;
	private Service servicing;
	String number = " ",string=" ",ans="";
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

		        btn1.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
                       finish();
					}
				});

		       btn.setOnClickListener(new View.OnClickListener()
			   {
				   @Override
				   public void onClick(View v)
				   {
					   ans = ed3.getText().toString();
					   SmsManager smsManager = SmsManager.getDefault();
					   smsManager.sendTextMessage(number, null,ans, null, null);
				   }
			   });

		 Bundle extras = getIntent().getExtras();

         if(extras!=null)
		 {
			 number = extras.getString("se");
			 string = extras.getString("ss");
			 ed1.setText(number);
			 ed2.setText(string);
		 }
	}



	@Override
	protected void onResume()
	{
		super.onResume();
	}
	

	@Override
	protected void onPause()
	{
	  super.onPause();
	}

}


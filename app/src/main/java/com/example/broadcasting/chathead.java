package com.example.broadcasting;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class chathead extends Service
{
	WindowManager wm,wmi;
	ImageView iv;
	int initialX;
	int initialY;
	float initialTouchX;
	float initialTouchY;
	Button bt;
	EditText ed;
	TextView tv;
	private BroadcastReceiver mIntentReceiver;
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	String sms="";
	String number="";
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent.getStringExtra("sms") != null)
		{
			sms = intent.getStringExtra("sms");
		}
		if(intent.getStringExtra("sender")!=null)
		{
			number = intent.getStringExtra("sender");
		}



		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		this.unregisterReceiver(this.mIntentReceiver);
		return super.onUnbind(intent);
	}

	@SuppressLint("NewApi")
	@Override
	public void onCreate()
	{

		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		iv = new ImageView(this);
		iv.setImageResource(R.drawable.fb);

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.fb);
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);

		BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
				TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setShader(shader);
		paint.setAntiAlias(true);

		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
				bitmap.getWidth() / 2, paint);

		iv.setImageBitmap(circleBitmap);

		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 0;

		wm.addView(iv, params);

		iv.setOnTouchListener(new View.OnTouchListener()
		{
			@SuppressLint("NewApi")
			@Override
			public boolean onTouch(View v, MotionEvent arg1)
			{
				switch (arg1.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						initialX = params.x;
						initialY = params.y;
						initialTouchX = arg1.getRawX();
						initialTouchY = arg1.getRawY();
						return true;
					case MotionEvent.ACTION_UP:
						params.x = initialX
								+ (int) (arg1.getRawX() - initialTouchX);
						params.y = initialY
								+ (int) (arg1.getRawY() - initialTouchY);

						if (params.y <= 700)
						{
									Intent in = new Intent("com.android.activity.SEND_DATA").putExtra("sender", number).putExtra("sms",sms);
									in.setClass(chathead.this, floatingchat.class);
									in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(in);
						}
						if (params.y < 800 && params.y > 700)
						{
							wm.removeView(iv);
						}
						if (params.x >= 240)
							params.x = 480;
						else
							params.x = 0;

						return true;
					case MotionEvent.ACTION_MOVE:
						params.x = initialX
								+ (int) (arg1.getRawX() - initialTouchX);
						params.y = initialY
								+ (int) (arg1.getRawY() - initialTouchY);
						wm.updateViewLayout(v, params);
						return true;

				}

				return false;


			}


		});

		
		
		super.onCreate();
	}


	

}

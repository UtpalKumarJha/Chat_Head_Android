package com.example.broadcasting;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader.TileMode;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class chathead_service extends Service
{
	WindowManager wm;
	ImageView iv;
	int initialX;
	int initialY;
	float initialTouchX;
	float initialTouchY;
	long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds
	long lastClickTime = 0;

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
	public boolean onUnbind(Intent intent)
	{
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
		DisplayMetrics dis = getBaseContext().getResources().getDisplayMetrics();

		final int width = dis.widthPixels;
		final int height = dis.heightPixels;


		iv.setOnTouchListener(new View.OnTouchListener() {
								  @SuppressLint("NewApi")
								  @Override
								  public boolean onTouch(View v, MotionEvent arg1) {

									  switch (arg1.getAction())
									  {
										  case MotionEvent.ACTION_DOWN:
											  initialX = params.x;
											  initialY = params.y;
											  initialTouchX = arg1.getRawX();
											  initialTouchY = arg1.getRawY();

											  long clickTime = System.currentTimeMillis();
											  if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA)
											  {
												  wm.removeView(iv);
											  }
											  lastClickTime = clickTime;
											  return true;
										  case MotionEvent.ACTION_UP:
											  params.x = initialX
													  + (int) (arg1.getRawX() - initialTouchX);
											  params.y = initialY
													  + (int) (arg1.getRawY() - initialTouchY);

											  if (params.x <= width && params.y<=height)
											  {
												  Intent in = new Intent("com.android.activity.SEND_DATA").putExtra("se", number).putExtra("ss", sms);
												  in.setClass(chathead_service.this, floatingchat_activity.class);
												  in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												  startActivity(in);
												  wm.removeView(iv);
											  }
											  else
											  {
												  wm.removeView(iv);
											  }
											  return true;
										  case MotionEvent.ACTION_MOVE:
											  params.x = initialX
													  + (int) (arg1.getRawX() - initialTouchX);
											  params.y = initialY
													  + (int) (arg1.getRawY() - initialTouchY);
											  wm.updateViewLayout(v, params);

										      if(params.x>=width || params.y>=height)
										       {
											     wm.removeView(iv);
                                               }
                                               return true;
									  }

									  return false;


								  }


							  }

		);


			super.onCreate();
		}


	}

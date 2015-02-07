package com.pankaj.pslock;


import java.util.ArrayList;
import java.util.Calendar;

import com.pankaj.pslock.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ServicesDemo extends Activity implements OnClickListener {
  public static ArrayList<String> lstLockedApps=new ArrayList<String>();
  private static final String TAG = "SimplePSAppLock";
  Button buttonStart, buttonStop;
  Button btnApps;
  Handler handler;
  
  public static PendingIntent pendingIntent;
  public static AlarmManager alarmManager;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  try
	  {	
		  SQLiteDatabase db;
			 db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
			 db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,packageName TEXT);");
					
			Cursor c=db.rawQuery("SELECT packageName FROM appLocklist",null);
			 for(c.moveToFirst(); !c.isAfterLast() ; c.moveToNext())
			    {
					 lstLockedApps.add(c.getString(c.getColumnIndex("packageName")));
			    }
			 c.close();
			 db.close();
			 
		 setContentView(R.layout.main);
    
		 //setFirstLaunchFlag();
    
		 buttonStart = (Button) findViewById(R.id.buttonStart);
		 buttonStop = (Button) findViewById(R.id.buttonStop);

		 btnApps=(Button)findViewById(R.id.btnApplist);
    
		 if(Service_Enabled())
		 {
			 buttonStart.setEnabled(false);
			 buttonStop.setEnabled(true);
		 }
		 else
		 {
			 buttonStart.setEnabled(true);
			 buttonStop.setEnabled(false);
		 }
    
		 buttonStart.setOnClickListener(this);
		 buttonStop.setOnClickListener(this);
	  }
	  catch (Exception ex) {
	    	Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
	    }		
  }

  
  private boolean Service_Enabled()
	{
		SharedPreferences sprefPin = getSharedPreferences("PS_Service", MODE_PRIVATE);		
		return sprefPin.getBoolean("PSService", false); 					
	}
  
  //for long press home button and notification window apps rstriction
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	 	 
		if(!hasFocus) 
		{	 
			Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
			sendBroadcast(closeDialog);
		}
	}
	
	//Below method used for enabling Home button key press event  
		 @Override
		    public void onAttachedToWindow() {
		        super.onAttachedToWindow();
		        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);           
		    }
		 
		 @Override
		    public boolean onKeyDown(int keyCode, KeyEvent event) {     

		        if(keyCode == KeyEvent.KEYCODE_HOME)
		        {
		        	//Leave Blank no code like back press method so by pressng home button it will not go to home
		        	//!!!~~~~IMPORTANT~~~~~!!!
		        	//This can be used for long press home button also
		        }
		        if(keyCode == KeyEvent.KEYCODE_BACK)	  	    	  
	  	        {
			     finish();
	  	        }
		        return true;
		    }
		 
  private void setFirstLaunchFlag(){
	  try
	  {
      SharedPreferences prefs = getSharedPreferences("Preferences",0);
      SharedPreferences.Editor edit = prefs.edit();
      edit.putBoolean("firstLaunch",false);
      edit.commit();
	  }
	  catch (Exception ex) {
	    	 Dialog d=new Dialog(this);
			  d.setTitle("Error!!!");
			  TextView tv=new TextView(this);
			  tv.setText(ex.toString());
			  d.setContentView(tv);
			  d.show();
	    }		
}
  
  public void OnPause()
  {
	  super.onPause();
	  Toast.makeText(this, "Tab", Toast.LENGTH_LONG).show();
	  finish();
  }
  
  
 /* public void btnAdd_click(View view)
  {
	Intent i=new Intent(this, ADD.class);
	startActivity(i);
	finish();
  }
  */
  public void onClick(View src) { 
	  final Calendar calendar = Calendar.getInstance();
	  try
	  {
    switch (src.getId()) {
    case R.id.buttonStart:
      Log.d(TAG, "onClick: starting srvice");
		/*new Thread() {
			public void run() {
				try {*/
      				buttonStart.setEnabled(false);
      				buttonStop.setEnabled(true);
      				//TODO New code
      				Intent myIntent = new Intent(ServicesDemo.this, MyReceiver.class);
      			    pendingIntent = PendingIntent.getBroadcast(ServicesDemo.this, 0, myIntent,0);
      				alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//      			    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
      			    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 3*1000, pendingIntent);
      				startService(new Intent(getBaseContext(), MyAlarmService.class));	
      				//END
      			    
//					startService(new Intent(ServicesDemo.this, MyService.class));
					try
					{
						SharedPreferences sprefPin = getSharedPreferences("PS_Service", MODE_PRIVATE);
						SharedPreferences.Editor edit = sprefPin.edit();
						edit.putBoolean("PSService",true);
						edit.commit();
					}
				    catch (Exception e) {
					e.printStackTrace();
				}
			/*}
		}.start();*/
		break;
    case R.id.buttonStop:
      //Log.d(TAG, "onClick: stopping srvice");
		/*new Thread() {
			public void run() {*/
				try {
    				buttonStart.setEnabled(true);
    				buttonStop.setEnabled(false);
    				//TODO New code
    				if(alarmManager!=null){
    					alarmManager.cancel(pendingIntent);
    				}

    				stopService(new Intent(ServicesDemo.this, MyAlarmService.class));
    				//END
//					stopService(new Intent(ServicesDemo.this, MyService.class));
					try
					{
						SharedPreferences sprefPin = getSharedPreferences("PS_Service", MODE_PRIVATE);
						SharedPreferences.Editor edit = sprefPin.edit();
						edit.putBoolean("PSService",false);
						edit.commit();
					}
				    catch (Exception e) {
					e.printStackTrace();
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*}
		}.start();*/
		break;
    }
	  }
	  catch (Exception ex) {
	    	 Dialog d=new Dialog(this);
			  d.setTitle("Error!!!");
			  TextView tv=new TextView(this);
			  tv.setText(ex.toString());
			  d.setContentView(tv);
			  d.show();
	    }		
  }
  
  public void btnApps_Click(View view)
  {
	  //Toast.makeText(getApplicationContext(), "Wait!!! Getting List of Installed Apps...", Toast.LENGTH_LONG).show();
	  
	  
	  handler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            // TODO Auto-generated method stub
	            super.handleMessage(msg);
	            Toast.makeText(getApplicationContext(), "Wait!!! Getting List of Installed Apps...", Toast.LENGTH_SHORT).show();
	        }
	    };
	  
	  new Thread() {
			public void run() {	
				handler.sendEmptyMessage(0);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent i=new Intent(getApplicationContext(),com.pankaj.pslock.TabAppList.class);
				startActivity(i);	
			}
		}.start();
		finish();
  }    
}
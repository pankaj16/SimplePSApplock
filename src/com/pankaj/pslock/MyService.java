package com.pankaj.pslock;

import java.util.List;


import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

public class MyService extends Service {
	private static String TAG = "MyService";
	MediaPlayer player;
	private static boolean boolService=true;
	public static String strLockApp="com.pankaj.pslock"; //using for Lock activity to lock
	public static String strTempApp="Service"; //Used is Lock activity for checking last recent activity  
	SQLiteDatabase db;
	Cursor cursorVar;
	ActivityManager am;
	List<ActivityManager.RunningTaskInfo> taskInfo;
	ComponentName componentInfo;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		/*try
		{
		Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onCreate");
		
		
		
		  
		player = MediaPlayer.create(this, R.raw.braincandy);
		player.setLooping(false); // Set looping
	
		
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);



	     // get the info from the currently running task
	     List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);

	     Log.d("topActivity", "CURRENT Activity ::"
	             + taskInfo.get(0).topActivity.getClassName());

	     ComponentName componentInfo = taskInfo.get(0).topActivity;
	  String strName= componentInfo.getPackageName().toString();
	   

	  
	  TAG=strName;
	
	  
	  if(strName.equalsIgnoreCase("com.pankaj.pslock.studentdb"))
	  {
		  PackageManager pm = getPackageManager();
	      Intent intent=pm.getLaunchIntentForPackage("com.pankaj.pslock");
	      startActivity(intent);
		  
	  }
		
		}
		catch (Exception ex) {
	    	 Dialog d=new Dialog(this);
			  d.setTitle("Error!!!");
			  TextView tv=new TextView(this);
			  tv.setText(ex.toString());
			  d.setContentView(tv);
			  d.show();
	    }*/		
		
         boolService=true;
		
        db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
 		//db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,packageName TEXT);");
		try
		{
			
		
		//Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onStart");
		//player.start();
		
		new Thread() {
			String strName="";
			 String strTmp;
			 String strApp="";
			 String strNum="1";
			public void run() {
				String strCheck="com.android.packageinstaller";
				try
				{
				while (boolService) 
				{
								
					 am = (ActivityManager) MyService.this
							.getSystemService(ACTIVITY_SERVICE);
				    
					// get the info from the currently running task
					 taskInfo = am
							.getRunningTasks(1);

					/* Log.d("topActivity", "CURRENT Activity ::"
					         + taskInfo.get(0).topActivity.getClassName());*/

					componentInfo = taskInfo.get(0).topActivity;
					strTmp = componentInfo.getPackageName().toString();
					
					strTempApp=strTmp; //this is used for checking in "Lock activity inside onPause() Method"  
					
					if (!strTmp.equalsIgnoreCase("com.pankaj.pslock")) {
						TAG = strTmp;
					}

					if (TAG.equalsIgnoreCase(strName))
						continue;
					else
						strName = TAG;
					
					if(strCheck.contains(strName))
					{
						Intent i = new Intent();
						i.setClass(getApplicationContext(),com.pankaj.pslock.Lock.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						strLockApp=strName;
						startActivity(i); 
						continue;
					}
					
					cursorVar=db.rawQuery("SELECT packageName FROM appLockList", null);
					
					for(cursorVar.moveToFirst(); !cursorVar.isAfterLast() ; cursorVar.moveToNext())
				    {
				      strApp=cursorVar.getString(cursorVar.getColumnIndex("packageName"));
					if (strName.equalsIgnoreCase(strApp))
					{
						/*db.execSQL("DELETE FROM appRunning WHERE appID=1;");
						sleep(500);
						db.execSQL("INSERT INTO appRunning VALUES(1,'" + strApp + "');");*/
						strLockApp=strApp;
					
						/*PackageManager pm = getPackageManager();
						Intent intent1 = pm.getLaunchIntentForPackage("com.pankaj.pslock");
						//intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent1);
						break;*/
						
						Intent i = new Intent();
						i.setClass(getApplicationContext(),com.pankaj.pslock.Lock.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i); 
					      break;
					      //finish();	  
					}//End of if					
				   } //End of for
					
					cursorVar.close();
				 }//End of While
				
				}//End of Try
				catch(Exception ex)
				{					
					ex.getStackTrace();
				}
		             
			}
		}.start();
	   }
		catch (Exception ex) 
		{
	    	 /*Dialog d=new Dialog(this);
			  d.setTitle("Error!!!");
			  TextView tv=new TextView(this);
			  tv.setText(ex.toString());
			  d.setContentView(tv);
			  d.show();*/
			ex.printStackTrace();
	    }
		
	}

	@Override
	public void onDestroy() {
		
		try
		{
		Toast.makeText(this, "Lock is now Deactivated!!!", Toast.LENGTH_SHORT).show();
		//Log.d(TAG, "onDestroy");
		//player.stop();
		boolService=false;
		Thread.interrupted();
		stopSelf();
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
	
	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Lock Activation Service Started.", Toast.LENGTH_SHORT).show();
		/*final String strName="";
		 String strTmp;*/
		/*boolService=true;
		
		db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
		try
		{
			
		
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
		//player.start();
		
		new Thread() {
			String strName="";
			 String strTmp;
			 String strApp="";
			 String strNum="1";
			public void run() {
				
				try
				{
				while (boolService) 
				{
								
					ActivityManager am = (ActivityManager) MyService.this
							.getSystemService(ACTIVITY_SERVICE);
				    
					// get the info from the currently running task
					List<ActivityManager.RunningTaskInfo> taskInfo = am
							.getRunningTasks(1);

					 Log.d("topActivity", "CURRENT Activity ::"
					         + taskInfo.get(0).topActivity.getClassName());

					ComponentName componentInfo = taskInfo.get(0).topActivity;
					strTmp = componentInfo.getPackageName().toString();

					if (!strTmp.equalsIgnoreCase("com.pankaj.pslock")) {
						TAG = strTmp;
					}

					if (TAG.equalsIgnoreCase(strName))
						continue;
					else
						strName = TAG;
					
					cursorVar=db.rawQuery("SELECT * FROM appLockList", null);
					
					for(cursorVar.moveToFirst(); !cursorVar.isAfterLast() ; cursorVar.moveToNext())
				    {
				      strApp=cursorVar.getString(cursorVar.getColumnIndex("appName"));
					if (strName.equalsIgnoreCase(strApp))
					{
						db.execSQL("DELETE FROM appRunning WHERE appID=1;");
						sleep(500);
						db.execSQL("INSERT INTO appRunning VALUES(1,'" + strApp + "');");
						strLockApp=strApp;
					
						PackageManager pm = getPackageManager();
						Intent intent1 = pm
								.getLaunchIntentForPackage("com.pankaj.pslock");
						startActivity(intent1);
						
					}//End of if					
				   } //End of for
					
					
				 }//End of While
				
				}//End of Try
				catch(Exception ex)
				{					
					ex.getStackTrace();
				}
		             
			}
		}.start();
	   }
		catch (Exception ex) 
		{
	    	 Dialog d=new Dialog(this);
			  d.setTitle("Error!!!");
			  TextView tv=new TextView(this);
			  tv.setText(ex.toString());
			  d.setContentView(tv);
			  d.show();
	    }		*/
	}
	
	public static String GetstrLockApp()
	{
		return strLockApp;
	}
	
	public static String GetstrTempApp()
	{
		return strTempApp;
	}
}

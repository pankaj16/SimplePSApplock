package com.pankaj.pslock;


import java.util.List;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
                           

public class MyAlarmService extends Service 

{
     private NotificationManager mManager;
     
    private static String TAG = "MyService";
 	MediaPlayer player;
 	private static boolean boolService=true;
 	public static String strLockApp="com.pankaj.pslock"; //using for Lock activity to lock
 	public static String strTempApp="Service"; //Used is Lock activity for checking last recent activity 
 	public static String previousApp="";
 	SQLiteDatabase db;
 	Cursor cursorVar;
 	ActivityManager am;
 	List<ActivityManager.RunningTaskInfo> taskInfo;
 	ComponentName componentInfo;
 	boolean isOwnApp = true;

     @Override
     public IBinder onBind(Intent arg0)
     {
       // TODO Auto-generated method stub
        return null;
     }

    @Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }

   @SuppressWarnings("static-access")
   @Override
   public void onStart(Intent intent, int startId)
   {
       super.onStart(intent, startId);
     
//       mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
//	   Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);
//	
//	   Notification notification = new Notification(R.drawable.ic_launcher,"This is a test message!", System.currentTimeMillis());
//	   System.out.println("Time: "+System.currentTimeMillis());
//	   intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//	   PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
//       notification.flags |= Notification.FLAG_AUTO_CANCEL;
//       notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);
//
//       mManager.notify(0, notification);
//       Toast.makeText(getApplicationContext(), "Starting service", Toast.LENGTH_LONG).show();
       
      
		//db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,packageName TEXT);");
		try
		{
			
		
		//Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onStart");
		//player.start();
		
		new Thread() {
			
			 String strTmp;
			 String strApp="";
			 String strNum="1";
			public void run() {
				String strCheck="com.android.packageinstaller";
				try
				{			
				   am = (ActivityManager) MyAlarmService.this
							.getSystemService(ACTIVITY_SERVICE);
				    
					// get the info from the currently running task
					 taskInfo = am
							.getRunningTasks(1);

					/* Log.d("topActivity", "CURRENT Activity ::"
					         + taskInfo.get(0).topActivity.getClassName());*/

					componentInfo = taskInfo.get(0).topActivity;
					strTmp = componentInfo.getPackageName().toString();
					
//					System.out.println("strTmp: "+strTmp);
					
					strTempApp=strTmp; //this is used for checking in "Lock activity inside onPause() Method"  
					
					if (!strTmp.equalsIgnoreCase("com.pankaj.pslock")) {
						TAG = strTmp;
						isOwnApp = false;
					}
					else{
						isOwnApp = true;
						return;
					}
					
//					if(previousApp.equals(strTmp)){
//						isOwnApp = true;
//						return;
//					}
//					else{
//						isOwnApp = false;
//					}
					
					
					if (TAG.equalsIgnoreCase(previousApp)){
						isOwnApp = true;
						return;						
					}
					else{
						isOwnApp = false;
						previousApp = TAG;						
					}
					
					
					
//					if(strCheck.contains(strName))
//					{
//						Intent i = new Intent();
//						i.setClass(getApplicationContext(),com.pankaj.pslock.Lock.class);
//						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						strLockApp=strName;
//						startActivity(i); 
//						onDestroy();
//						return;
//					}
					
					
					
//					System.out.println("isOwnApp"+String.valueOf(isOwnApp));
					
				
					//TODO
//					if(!isOwnApp){
//						db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
//						cursorVar=db.rawQuery("SELECT packageName FROM appLockList where packageName='"+previousApp+"'", null);
//						
//						if(cursorVar.moveToFirst()){
//							strLockApp=strApp;
//							Intent i = new Intent();
//							i.setClass(getApplicationContext(),com.pankaj.pslock.Lock.class);
//							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							startActivity(i);
//						}
//						cursorVar.close();
//						db.close();
//					}
					
					if(!isOwnApp){
						SharedPreferences sharedPreference;
	                	sharedPreference = getSharedPreferences("AppList", Context.MODE_PRIVATE);
	                	SharedPreferences.Editor editor = sharedPreference.edit();
	                	String appString = sharedPreference.getString("apps", "");
	                	appString += ";";
	                	if(appString.contains(previousApp)){	                		
	                		strLockApp=strApp;
							Intent i = new Intent();
							i.setClass(getApplicationContext(),com.pankaj.pslock.Lock.class);
							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(i);
	                	}
					}
					
					
//					for(cursorVar.moveToFirst(); !cursorVar.isAfterLast() ; cursorVar.moveToNext())
//				    {
//				      strApp=cursorVar.getString(cursorVar.getColumnIndex("packageName"));
//					if (previousApp.equalsIgnoreCase(strApp))
//					{
						/*db.execSQL("DELETE FROM appRunning WHERE appID=1;");
						sleep(500);
						db.execSQL("INSERT INTO appRunning VALUES(1,'" + strApp + "');");*/
//						strLockApp=strApp;
					
						/*PackageManager pm = getPackageManager();
						Intent intent1 = pm.getLaunchIntentForPackage("com.pankaj.pslock");
						//intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent1);
						break;*/
						
//						Intent i = new Intent();
//						i.setClass(getApplicationContext(),com.pankaj.pslock.Lock.class);
//						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						startActivity(i);
//						return;
					      //break;
					      
					     //finish();	  
//					}//End of if					
//				   } //End of for
					
					
					
					
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
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
//    	Toast.makeText(getApplicationContext(), "Stopping service", Toast.LENGTH_LONG).show();
    	System.out.println("OnDestroy()Called.....");
    	if(ServicesDemo.alarmManager!=null){
			ServicesDemo.alarmManager.cancel(ServicesDemo.pendingIntent);
		}
        super.onDestroy();
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
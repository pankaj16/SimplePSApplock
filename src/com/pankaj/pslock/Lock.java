package com.pankaj.pslock;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.os.Process;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Lock extends Activity {
	private static final Runnable Runnable = null;
	/*private ApplicationInfo appinfo = null;
	 ActivityManager.RunningAppProcessInfo runinfo=null;
	 private PackageInfo pkginfo = null; 
	 ActivityManager manager;*/
	/* ActivityManager am = null;
	 ActivityManager.RunningAppProcessInfo runinfo=null;
	 PackageManager pm;
	 private ApplicationInfo appinfo = null;
	 private PackageInfo pkginfo = null;*/
	//ActivityManager am=null; 
	SQLiteDatabase db;
	String strLockedApp;
	Boolean boolMessDestroy=true;
	String strTmp;
	int pid;
	 String packageName;
	public Context cx;
	boolean boolResult=false;
	//public sboolResulttatic boolean boolHome = true;
	//public static boolean OnResume = false;
	
	 
	
	 
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_lock);
		//boolHome=true; //for home button press	
		/*if(HideIcon_Checking())
		{
			try
			{
				 PackageManager manager  = getPackageManager();
			     ComponentName compName = new ComponentName("com.pankaj.pslock","com.pankaj.pslock.LockPSAlias");
				 manager.setComponentEnabledSetting(compName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}*/
		
		
	}

	/*private boolean HideIcon_Checking()
	{
		SharedPreferences sprefPin = getSharedPreferences("PS_Hide", MODE_PRIVATE);		
		return sprefPin.getBoolean("PSHidden", false); 					
	}*/
	
	
	 @Override
	 protected void onResume() {
	     super.onResume();
	     
	    // boolHome=true;
	     try
	        {
		 
	       /* if(firstLaunch())
	        {	        	   
	            startActivity(new Intent(this,com.pankaj.pslock.ServicesDemo.class));
	            finish();
	        }
	        else
	        {*/
	          //Do your normal stuff       	
	        	
	        	setContentView(R.layout.activity_lock);
	        	
	        	//startService(new Intent(Lock.this, MyService.class));
	        	/*requestWindowFeature(Window.FEATURE_NO_TITLE);
	 	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);*/
	       // }
	        
	       
	        
	        db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
			//db.execSQL("CREATE TABLE IF NOT EXISTS appRunning(appID INTEGER,appName TEXT);");
			db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,packageName TEXT);");
			//db.execSQL("INSERT INTO appRunning VALUES(1,'com.example');");
			
			 strLockedApp=com.pankaj.pslock.MyAlarmService.GetstrLockApp();
			 strTmp=strLockedApp;
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
	 
	 
	 
	 
	 private boolean firstLaunch(){
         SharedPreferences prefs = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
         return prefs.getBoolean("firstLaunch",true);
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
		           //The Code Want to Perform. 
		        	
		        	//Leave Blank no code like back press method so by pressng home button it will not go to home
		        	//!!!~~~~IMPORTANT~~~~~!!!
		        	//This can be used for long press home button also
		        }
		        return true;
		    }
	 
	

	 protected void onPause()
	 {
	   super.onPause();
	   
	   //Toast.makeText(this, "1 "+ com.example.MyService.strTempApp, Toast.LENGTH_LONG).show();
	   String strRunningApp=com.pankaj.pslock.MyAlarmService.GetstrTempApp();
	   
	   if(boolMessDestroy==true)
	   {	
		 
		 ActivityManager am = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
	        /*List<RunningAppProcessInfo> runappinfoList = am.getRunningAppProcesses();
	       	        
	        
	        Intent i=new Intent(Intent.ACTION_MAIN);
	        i.addCategory(Intent.CATEGORY_HOME);	
	        startActivity(i);
	        Toast.makeText(this, "1 "+ com.example.MyService.strTempApp, Toast.LENGTH_LONG).show();
	        for(RunningAppProcessInfo runappInfo : runappinfoList) 
	        {           
	            
	            String packageName = runappInfo.processName.split(":")[0];
	            
	            if(packageName.contains(strLockedApp))
	                {
	                //Toast.makeText(this, "1 "+packageName, Toast.LENGTH_LONG).show(); 
	                am.restartPackage(packageName); 
	                break;
	                }	            
	        }*/

	       // com.example.MyService.strLockApp = "com.example";	
		 am.restartPackage(strLockedApp);
	       // CheckstrTempApp(strRunningApp);
		// com.example.MyService.strLockApp = "com.example";
	   } //End of if
	   
	   
	 }
	 
	 
	 
	 
	 
	 protected void onStop()
	 {
		 super.onStop();
		
				
				
		if(boolMessDestroy==true)
		{
		String strLocal;	
		 Cursor cursorVar;
		 Boolean boolFlag=false;
		 cursorVar=db.rawQuery("SELECT packageName FROM appLockList", null);
		 
		 String strRecentApp=com.pankaj.pslock.MyAlarmService.GetstrTempApp();
			
		 ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
		 
		  if(strRecentApp.equalsIgnoreCase(strTmp))
		  {
			  am.restartPackage(strTmp);
			  //Toast.makeText(this, "Dont Mess with App... Terminating!!!", Toast.LENGTH_LONG).show();
			 
			//  am.restartPackage(strTmp);	
			  //com.example.MyService.strLockApp="com.example";
			 //killingApp();
			 // boolMessDestroy=false;
			  finish();
		  }
		  
			for(cursorVar.moveToFirst(); !cursorVar.isAfterLast() ; cursorVar.moveToNext())
		    {
		      strLocal=cursorVar.getString(cursorVar.getColumnIndex("packageName"));
			if (strLocal.contains(strRecentApp))
			{	
				//Toast.makeText(this, "equals", Toast.LENGTH_LONG).show(); 
				boolFlag=true;
			    break;  	
			}//End of if					
		   } //End of for
			
			
			if(boolFlag==true)
			{
				//Toast.makeText(this, "True", Toast.LENGTH_LONG).show(); 
				/*PackageManager pm = getPackageManager();
				Intent intent1 = pm.getLaunchIntentForPackage("com.example");
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent1);
				//finish();*/
				/*PackageManager pm = getPackageManager();
				Intent intent1 = pm.getLaunchIntentForPackage("com.pankaj.pslock");*/
				Intent intent1 =new Intent(this,Lock.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent1);
		       /* List<RunningAppProcessInfo> runappinfoList = am.getRunningAppProcesses();
		        for(RunningAppProcessInfo runappInfo : runappinfoList) 
		        {           
		            
		            String packageName = runappInfo.processName.split(":")[0];
		            
		            if(packageName.contains(com.example.MyService.GetstrTempApp()))
		                {*/
		                //Toast.makeText(this, "2 Equal"+packageName, Toast.LENGTH_LONG).show(); 
		                //am.restartPackage(packageName);
		                //am.killBackgroundProcesses(strRecentApp);
		               // break;
		               /* }	            
		        }*/
				/*Toast.makeText(this, com.example.MyService.GetstrTempApp(), Toast.LENGTH_LONG).show();
				ActivityManager am = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
				am.restartPackage(com.example.MyService.GetstrTempApp());*/
		       // com.example.MyService.strLockApp = "com.example";
		        //finish();
			}
			/*else
			{
				//Toast.makeText(this, "False", Toast.LENGTH_LONG).show();
				com.example.MyService.strLockApp = "com";
				finish();
				//KillApp();
			}*/
		  
		}
				
	 }

	
	     
	public void btnPass_click(View view)
	{
		 
		try
		{
	 EditText edtPassword=(EditText)findViewById(R.id.edtPass);	
	 
	 String strPass="";
	// String strApp="";
	 strPass=edtPassword.getText().toString();
	 
	 boolResult=Check_Pass(strPass);
	 
	 if(boolResult==true)
	 {
		 /*Cursor c=db.rawQuery("SELECT * FROM appRunning",null);
		 
		    c.moveToFirst();
		    strApp=c.getString(c.getColumnIndex("appName"));		    
	       
		    if(strApp.equalsIgnoreCase("com.example"))*/
		  //getting values for locking app
		 
		 //Toast.makeText(this, strApp, Toast.LENGTH_LONG).show();
		  if(strLockedApp.equalsIgnoreCase("com.pankaj.pslock"))
		    {
		     Intent i=new Intent(this,ServicesDemo.class);
		   //this is used when anyone try to open multiple instances of an activity
		   //False here bcoz its opening its own another activity   
		     boolMessDestroy=false; 
		     startActivity(i);
		     finish();
		    }
		  else
		    {				  
			  boolMessDestroy=false;
			  com.pankaj.pslock.MyService.strLockApp="com.pankaj.pslock"; //assigning any false value for making true condition for my service
              finish();              
		    }		 
	   }
	 else
		 Toast.makeText(this, "Wrong Password!!!", Toast.LENGTH_LONG).show();
     }
	 catch(Exception ex)
	 {
		 Dialog d=new Dialog(this);
		  d.setTitle("Error");
		  TextView tv=new TextView(this);
		  tv.setText(ex.toString());
		  d.setContentView(tv);
		  d.show();
	 }
	 
	}
	
	public boolean Check_Pass(String strPass)
	{		
		String strTmp="";
		
		SharedPreferences sprefPin = getSharedPreferences("PS_Storage", MODE_PRIVATE);
		strTmp=sprefPin.getString("PS", "333333");
		
		if(strTmp.equals(strPass))
			return true;
		else
			return false;
	}
	
	
		
		 /*Intent i = new Intent(this,ServicesDemo.class);
	      startActivity(i); 
	      finish();*/
		
		//db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
		/*db.execSQL("CREATE TABLE IF NOT EXISTS appRunning(appID INTEGER,appName TEXT);");
		db.execSQL("INSERT INTO appRunning VALUES(1,'com.example');");*/
		
		/*db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,running TEXT);");
		db.execSQL("INSERT INTO appLockList VALUES(1,'com.example',null);");
		db.execSQL("INSERT INTO appLockList VALUES(2,'com.example.studentdb',null);");*/
		
		//db.execSQL("UPDATE appLockList SET appName='com.example.studentdb' WHERE appID=2;");
		
		
		
	
	
	
	public void btnClose_Click(View view)
	{
		boolMessDestroy=false;
		
		 ActivityManager am = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
	        List<RunningAppProcessInfo> runappinfoList = am.getRunningAppProcesses();
	        	        
	        
	        Intent i=new Intent(Intent.ACTION_MAIN);
	        i.addCategory(Intent.CATEGORY_HOME);	
	       // boolHome=false; //for home button
	        startActivity(i);
	        
	        for(RunningAppProcessInfo runappInfo : runappinfoList) {           
	            
	            String packageName = runappInfo.processName.split(":")[0];
	            
	            if(packageName.contains(strLockedApp))
	                {
	                //Toast.makeText(this, "1 "+packageName, Toast.LENGTH_LONG).show(); 
	                am.restartPackage(packageName); 
	                break;
	                }
	            
	        }

		com.pankaj.pslock.MyService.strLockApp = "com.pankaj.pslock";
		//boolHome=false; //for home button
		finish();		
	}
	
	/*public void KillApp()
	{
		 ActivityManager am = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
	        List<RunningAppProcessInfo> runappinfoList = am.getRunningAppProcesses();
	        //int icount=0;
	        
	        
	        Intent i=new Intent(Intent.ACTION_MAIN);
	        i.addCategory(Intent.CATEGORY_HOME);	
	        startActivity(i);
	        
	        for(RunningAppProcessInfo runappInfo : runappinfoList) {           
	            
	            String packageName = runappInfo.processName.split(":")[0];
	            
	            if(packageName.contains(strTmp))
	                {
	                //Toast.makeText(this, "killed" + strTmp , Toast.LENGTH_LONG).show(); 
	                am.restartPackage(packageName); 
	                //break;
	                }
	            
	        }
	        //Toast.makeText(this, "kil "+strTmp , Toast.LENGTH_LONG).show();
		com.pankaj.pslock.MyService.strLockApp = "com.pankaj.pslock";
		finish();
		//boolHome=false; //for home button
		
		
	}*/

}

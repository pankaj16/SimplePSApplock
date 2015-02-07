package com.pankaj.pslock;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainPS extends Activity {

	
	String strLockedApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_ps);	
		 
		strLockedApp=com.pankaj.pslock.MyAlarmService.GetstrLockApp();	
		 
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
		 

			 public void btnPassMain_Click(View view)
			 {
				 try
				 {
				 EditText edtPassMain=(EditText)findViewById(R.id.edtPassMain);
				 String strPass=edtPassMain.getText().toString();
				 
				 if(Check_Pass(strPass))
				 {
				Intent intent = new Intent(this,ServicesDemo.class);  
					 startActivity(intent);
					 finish();
				 }
				 else
					 Toast.makeText(this, "Wrong Password!!!", Toast.LENGTH_SHORT).show();
				 }
					catch(Exception ex)
					{
						Toast.makeText(this, "btn "+ ex.toString(), Toast.LENGTH_LONG).show();
					}
			 }
			 
			 public boolean Check_Pass(String strPass)
				{		
					String strTmp;
					boolean boolFlag=false;
					try
					{
					SharedPreferences sprefPin = getSharedPreferences("PS_Storage", MODE_PRIVATE);
					strTmp=sprefPin.getString("PS", "333333");
					if(strTmp.equals(strPass))
						boolFlag=true;
					else
						boolFlag=false;
					}
					catch(Exception ex)
					{
						Toast.makeText(this, "check "+ ex.toString(), Toast.LENGTH_LONG).show();
					}
					return boolFlag;
				}
				
			 public void btnCloseMain_Click(View view)
			 {
				 ActivityManager am = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
			        List<RunningAppProcessInfo> runappinfoList = am.getRunningAppProcesses();
			        
			        
			        Intent intent=new Intent(Intent.ACTION_MAIN);
			        intent.addCategory(Intent.CATEGORY_HOME);	
			        startActivity(intent);
			        
			        for(RunningAppProcessInfo runappInfo : runappinfoList) {           
			            
			            String packageName = runappInfo.processName.split(":")[0];
			            
			            if(packageName.contains(strLockedApp))
			                {
			                am.restartPackage(packageName); 
			                break;
			                }
			            
			        }

				com.pankaj.pslock.MyService.strLockApp = "com.pankaj.pslock";
				finish();		
			 }
}

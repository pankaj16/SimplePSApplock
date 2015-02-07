package com.pankaj.pslock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.TabHost.TabSpec;

public class TabAppList extends Activity {

	TabHost tabHost;
    
    TextView data;
    ImageView image1;
    LinearLayout holdlayout;
    View l1;
    private ArrayList results;
    List<ResolveInfo> list;
    TextView result;
    String str = "";
    Drawable icon;
    ArrayList<String> lstArray = new ArrayList<String>();
//    SQLiteDatabase db;
    int i=0;
    String strTmp;
    int iWidth15,iWidth5;
    ToggleButton tg;
    Button btnHideApp,btnUnHideApp;
     
    
  
  	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_list); 
		
//		db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
//		db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,packageName TEXT);");
		
		tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();
      
        TabSpec spec1=tabHost.newTabSpec("Locked/UnlockedApps");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Locked/UnlockedApps");
      
      
        TabSpec spec2=tabHost.newTabSpec("Change Password");
        spec2.setIndicator("Change Password");
        spec2.setContent(R.id.tab2);
      
        
        TabSpec spec3=tabHost.newTabSpec("Hide");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Hide");
        
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        
        
        l1 = findViewById(R.id.Layout1);
        //cbHideIcon=(CheckBox)findViewById(R.id.cbHideicon);
         btnHideApp=(Button)findViewById(R.id.btnHideApp);
         btnUnHideApp=(Button)findViewById(R.id.btnUnHideApp);
        
        Display display = getWindowManager().getDefaultDisplay(); 
        int iScrnWidth = display.getWidth();  // deprecated
        int iScrnHeight = display.getHeight();  // deprecated
        
        iWidth15=iScrnWidth*15/100;
        iWidth5=iScrnHeight*5/100;
        
        results = new ArrayList();
        PackageManager pm = getApplicationContext().getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        list = pm.queryIntentActivities(intent,
                PackageManager.PERMISSION_GRANTED);
        
       Collections.sort(list, new ResolveInfoComparator());
        
       SharedPreferences sharedPreference;
   	sharedPreference = getSharedPreferences("AppList", Context.MODE_PRIVATE);
   	String appListString = sharedPreference.getString("apps", "");
        for (ResolveInfo rInfo : list) 
        {
            str = rInfo.activityInfo.applicationInfo.loadLabel(pm).toString()
                    + "\n";
            results.add(rInfo.activityInfo.applicationInfo.loadLabel(pm)
                    .toString());
            TextView tv=new TextView(getApplicationContext());
            tv.setText(rInfo.activityInfo.applicationInfo.packageName);
            
            if(tv.getText().toString().contains("com.pankaj.pslock"))
            	continue;
            
            lstArray.add(tv.getText().toString());
            strTmp=tv.getText().toString();
            /*if(i==0)
            	Toast.makeText(getApplicationContext(),rInfo.activityInfo.applicationInfo.packageName.toString(), Toast.LENGTH_SHORT).show();*/
            
            //Log.w("Installed Applications", rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());
            icon = rInfo.activityInfo.applicationInfo.loadIcon(pm);
            
            
            	
            holdlayout = new LinearLayout(getApplicationContext());
            holdlayout.setOrientation(LinearLayout.HORIZONTAL);
            
            data = new TextView(getApplicationContext());
            data.setId(7000+i);// For setting unique id for textview
            data.setMinWidth(iWidth15*4);
            data.setText(str);
            data.setTextSize(20);
            data.setLines(2);
            data.setMaxWidth(iWidth15*4);
            //data.setGravity(Gravity.CENTER);
            data.setPadding(0, 0, 1, 0);
            data.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            android.widget.LinearLayout.LayoutParams params1 = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(10, 0, 0, 0);
            data.setLayoutParams(params1);
           // data.setTextAppearance(this, 2);
            
            tg=new ToggleButton(getApplicationContext());
            tg.setId(i);
            i++;
            tg.setText("OFF");
            tg.bringToFront();
//            if(com.pankaj.pslock.ServicesDemo.lstLockedApps.contains(strTmp))
//            	tg.setChecked(true);
//            else
//            	tg.setChecked(false);
            if(appListString.contains(strTmp)){
            	tg.setChecked(true);
            }else{
            	tg.setChecked(false);
            }
            tg.setClickable(true);
            tg.setMaxWidth(10);
            //tg.setGravity(Gravity.RIGHT);
            android.widget.LinearLayout.LayoutParams params2 = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
            params2.setMargins(iWidth5, 0, 0, 0);
            tg.setLayoutParams(params2);
            
           
            tg.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View view){
                	SharedPreferences sharedPreference;
                	sharedPreference = getSharedPreferences("AppList", Context.MODE_PRIVATE);
                	SharedPreferences.Editor editor = sharedPreference.edit();
                	String appString;
                	String deleteApp = lstArray.get(view.getId()).toString()+";";
                	try
                	{
                   // Toast.makeText(getApplicationContext(), "Click!"+view.getId(), Toast.LENGTH_SHORT).show();
                    ToggleButton tbtn=(ToggleButton)findViewById(view.getId());
                    //int iAppId=7000+view.getId();
                    TextView txtAppName=(TextView)l1.findViewById(7000+view.getId());
                    
                    appString = sharedPreference.getString("apps", "");
                    
                    
                    if(tbtn.isChecked())
                    	{
                    	//Toast.makeText(getApplicationContext(), txtAppName.getText().toString()+" is Checked", Toast.LENGTH_SHORT).show();
                    	System.out.println(txtAppName.getText().toString()+" is Checked " + lstArray.get(view.getId()).toString());
                    	//TODO
//                    	db.execSQL("INSERT INTO appLockList VALUES("+view.getId()+",'"+ txtAppName.getText().toString() +"','"+lstArray.get(view.getId()).toString()+"');");
                    	appString += lstArray.get(view.getId()).toString()+";";
                    	editor.putString("apps", appString);
                    	editor.commit();
                    	}
                    else
                    	{
                    	//Toast.makeText(getApplicationContext(),lstArray.get(view.getId())+" is UnChecked", Toast.LENGTH_SHORT).show();
//                    	db.delete("appLockList", "packageName='"+ lstArray.get(view.getId()).toString() + "'", null);
                    	 
                    	 appString = appString.replace(deleteApp, "");
                    	 editor.putString("apps", appString);
                    	 editor.commit();                    	 
                    	}
                	}
                	catch(Exception ex)
                	{
                		ex.printStackTrace();
                		//Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();
                	}
                }
            });
            
            image1 = new ImageView(getApplicationContext());
            image1.setAdjustViewBounds(true);
            image1.setBackgroundDrawable(icon);
            
            image1.setLayoutParams(new LayoutParams(iWidth15,iWidth15));
            //image1.setPadding(0, 0, 1, 0);
            
            
            //drawline=new  DrawLine(this);
            
            //drawline.setBackgroundColor(Color.WHITE);
            ((ViewGroup) holdlayout).addView(image1);
            ((ViewGroup) holdlayout).addView(data);
            ((ViewGroup) holdlayout).addView(tg);
            ((ViewGroup) l1).addView(holdlayout);
            
            TextView txtLine = new TextView(this);
            //txtLine.setText("-");
            txtLine.setBackgroundColor(Color.WHITE);
            txtLine.setHeight(1);
            txtLine.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            
            holdlayout = new LinearLayout(getApplicationContext());
            holdlayout.setOrientation(LinearLayout.HORIZONTAL);
            ((ViewGroup) holdlayout).addView(txtLine);
            ((ViewGroup) l1).addView(holdlayout);
            
        }//End of FOR loop 
        
       com.pankaj.pslock.ServicesDemo.lstLockedApps.clear();
       if(HideIcon_Checking())
 		  btnHideApp.setEnabled(false);
 		else
 		  btnUnHideApp.setEnabled(false);
        
       /* OnCheckedChangeListener listner=null;
		cbHideIcon.setOnCheckedChangeListener(listner);*/
	}

	 public void OnPause()
	  {		 
		  super.onPause();
		  Toast.makeText(this, "Tab", Toast.LENGTH_LONG).show();
		  finish();
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
	  	      if(keyCode == KeyEvent.KEYCODE_BACK)	  	    	  
	  	        {
	  	    	Intent intent=new Intent(this,com.pankaj.pslock.ServicesDemo.class);
			    startActivity(intent);
			    finish();
	  	        }
	  	        return true;
	  	    }
	    
	  	public void onBackPressed()
		{
	  		Intent intent=new Intent(this,com.pankaj.pslock.ServicesDemo.class);
		    startActivity(intent);
		    finish();
		}
	  	
	  	 public class ResolveInfoComparator implements Comparator<ResolveInfo>
	     {
	         public int compare(ResolveInfo left, ResolveInfo right) {
	         	PackageManager pm = getApplicationContext().getPackageManager();
	         	String strLeft=left.activityInfo.applicationInfo.loadLabel(pm).toString();
	         	String strRight=right.activityInfo.applicationInfo.loadLabel(pm).toString();
	             return strLeft.compareTo(strRight);
	         }
	     }
	  	 
	  	 
	  	 
	  	 /*
	  	   !!!!!!!!!!!!!!~~~~~~~~~~~~NOTE TAB 2 CODE~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!
	  	  Below coding is for Change Password Tab Only
	  	  
	  	  */
	 
	 
	 public void btnChange_Click(View view)
		{
			String strNewPass="";
			String strRepeat="";
			boolean boolFlag=true;
			
			EditText edtOldPass=(EditText)findViewById(R.id.edtCurrentPass);
			EditText edtNewPass=(EditText)findViewById(R.id.edtNewPass);
			EditText edtRepeat=(EditText)findViewById(R.id.edtRepeat);
			
			strNewPass=edtNewPass.getText().toString();
			strRepeat=edtRepeat.getText().toString();
			
			if(strNewPass.equalsIgnoreCase(""))
			{
				Toast.makeText(this, "New Password Can't be Empty!!!", Toast.LENGTH_LONG).show();
				boolFlag=false;
			}
			else
			{
			  if(strNewPass.equals(strRepeat))
			  {
				if(Check_OldPass(edtOldPass.getText().toString()))
				{
					SharedPreferences sprefPin = getSharedPreferences("PS_Storage", MODE_PRIVATE);
					SharedPreferences.Editor edit = sprefPin.edit();
				    edit.putString("PS",strNewPass);
				    edit.commit();
				    Toast.makeText(this, "New Password Saved!!!", Toast.LENGTH_LONG).show();
				    boolFlag=false;
				}
				else
				{
					Toast.makeText(this, "Current Password is Wrong!!!", Toast.LENGTH_LONG).show();
					boolFlag=false;
				}
			  }		
		    }
			
			if(boolFlag==true)
				Toast.makeText(this, "New and Repeat Password are not same!!!", Toast.LENGTH_LONG).show();
		}
		
		public boolean Check_OldPass(String strOldPass)
		{
			String strTmp;
			SharedPreferences sprefPin = getSharedPreferences("PS_Storage", MODE_PRIVATE);
			strTmp=sprefPin.getString("PS","333333");
			if(strTmp.equals(strOldPass))
				return true;
			else
				return false;
		}
		
		public void btnCancel_Click(View view)
		{
			Intent intent=new Intent(this,com.pankaj.pslock.ServicesDemo.class);
		    startActivity(intent);
		    finish();
		}
		
		
		
		//         --------------TAB 3 CODE---------------
		
		
		private boolean HideIcon_Checking()
		{
			SharedPreferences sprefPin = getSharedPreferences("PS_Hide", MODE_PRIVATE);		
			return sprefPin.getBoolean("PSHidden", false); 					
		}
		
		public void btnHideApp_click(View view) 
		{
			try
			{
			 	SharedPreferences sprefPin = getSharedPreferences("PS_Hide", MODE_PRIVATE);
				SharedPreferences.Editor edit = sprefPin.edit();
				edit.putBoolean("PSHidden",true);
				edit.commit();
				Toast.makeText(this, "hiding Icon!!! Now use Phone dialer to open App", Toast.LENGTH_LONG).show();
				  						
				PackageManager manager  = getPackageManager();
				ComponentName compName = new ComponentName("com.pankaj.pslock","com.pankaj.pslock.LockPSAlias");
				//ComponentName compName = new ComponentName("<Package Name>", "<Package Name.Alias Name>");
				  							 
				manager.setComponentEnabledSetting(compName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				btnHideApp.setEnabled(false);
				btnUnHideApp.setEnabled(true);
			//	Toast.makeText(this, "Disabled", Toast.LENGTH_LONG).show();
			}
			catch(Exception ex)
			{
			  // Toast.makeText(this, "1 "+ex.toString(), Toast.LENGTH_LONG).show();
				ex.printStackTrace();
			}
	  }
		
		public void btnUnHideApp_click(View view)
		{
		
				 					   try
				 					   {
				 					   SharedPreferences sprefPin = getSharedPreferences("PS_Hide", MODE_PRIVATE);
				 					   SharedPreferences.Editor edit = sprefPin.edit();
				 					   edit.putBoolean("PSHidden", false);
				 					   edit.commit();
				 					   Toast.makeText(this, "Unhiding Icon!!!", Toast.LENGTH_LONG).show();
				 					   
				 					  PackageManager manager  = getPackageManager();
				  						ComponentName compName = new ComponentName("com.pankaj.pslock","com.pankaj.pslock.LockPSAlias");
				  						//ComponentName compName = new ComponentName("<Package Name>", "<Package Name.Alias Name>");
				  							 
				  						manager.setComponentEnabledSetting(compName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
				  						btnUnHideApp.setEnabled(false);
				  						btnHideApp.setEnabled(true);
				  						//Toast.makeText(this, "Disabled", Toast.LENGTH_LONG).show();
				  						
				  						}
				  						catch(Exception ex)
				  						{
				  							//Toast.makeText(this, "2 "+ex.toString(), Toast.LENGTH_LONG).show();
				  							ex.printStackTrace();
				  									
				  						}
		
			}
		
		@Override
		protected void onStop() {
//			db.close();
			super.onStop();
		}
		
}

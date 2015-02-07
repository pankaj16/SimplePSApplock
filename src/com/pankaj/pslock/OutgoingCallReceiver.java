package com.pankaj.pslock;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.sax.StartElementListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.widget.Toast;

public class OutgoingCallReceiver extends BroadcastReceiver {

	   //public static final String App_Open_Number = "123123";
       public static String phoneNumber="";
	   private static final String OUTGOING_CALL_ACTION = "android.intent.action.NEW_OUTGOING_CALL";
	   private static final String INTENT_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
	   Boolean boolPin=false;	   
	   //Context context = null;
	   private static final String TAG = "Phone call";
	   //public ITelephony telephonyService;
	   public Class c;
	   public Method m;
	   
	   @Override
	   public void onReceive(final Context context, final Intent intent) {
	      
		   try
		   {
	      if (intent.getAction().equals(OutgoingCallReceiver.OUTGOING_CALL_ACTION))
	      {
	        phoneNumber = intent.getExtras().getString(OutgoingCallReceiver.INTENT_PHONE_NUMBER);
	        	        
	        boolPin=Check_Pin(phoneNumber,context);
	        if(boolPin==true)
	        {	        
	        //	Toast.makeText(context, "True" +phoneNumber,Toast.LENGTH_LONG).show();
	          //  this.abortBroadcast();       
	        	/*if(IconHidden(context))
	        	{
	        		Toast.makeText(context, "hiddden",Toast.LENGTH_LONG).show();
	        		intent.setClassName("com.pankaj.pslock", "com.pankaj.pslock.Unhide");
	                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	                setResultData(null); 
	                context.startActivity(intent);	
	        	}
	        	else
	        	{	     */        
	        		//Toast.makeText(context, "unhidden",Toast.LENGTH_LONG).show();
	             intent.setClassName("com.pankaj.pslock", "com.pankaj.pslock.ServicesDemo");
	             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	  
	             //killCall(context);
	             setResultData(null);
	             context.startActivity(intent);
	        	//}
	         }
	       
	        }
		   }
		   catch(Exception ex)
		   {
			  // Toast.makeText(context,"Receive "+ex.toString(),Toast.LENGTH_LONG).show();
			   ex.printStackTrace();
		   }
	      
	   }
	   
	   private boolean Check_Pin(String strPin,Context context) {
	    	SharedPreferences sprefPin;
	    	String strTmp=null;
	    	boolean boolResult=false;
	    	try
	    	{
	    	 sprefPin = context.getSharedPreferences("PS_Storage", 0);    	
			 strTmp=sprefPin.getString("PS","333333");	
			
					
			  if(strPin.equals(strTmp))
				boolResult=true;		
			  else
			   boolResult=false;    	
	    	}
	    	 catch(Exception ex)
	     	{
	     		//Toast.makeText(context, ex.toString() + "Pin", Toast.LENGTH_LONG).show();
	    		 ex.printStackTrace();
	     	}
	    	return boolResult;
	    }
	   
	  /* public boolean IconHidden(Context context)
	   {
		   boolean boolFlag=false;		   
		   try
	    	{
			   SharedPreferences sprefPin= context.getSharedPreferences("PS_Hide", Context.MODE_PRIVATE);		
			   boolFlag=sprefPin.getBoolean("PSHidden", false);
	    	}
	    	catch(Exception ex)
	    	{
	    		//Toast.makeText(context, ex.toString() + "Receive", Toast.LENGTH_LONG).show();
	    		ex.printStackTrace();
	    	}  
		   return boolFlag;
	   }*/
	   
	  /* public boolean killCall(Context context) {
	        try {
	           // Get the boring old TelephonyManager
	           TelephonyManager telephonyManager =
	              (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	     
	           // Get the getITelephony() method
	           Class classTelephony = Class.forName(telephonyManager.getClass().getName());
	           Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");
	     
	           // Ignore that the method is supposed to be private
	           methodGetITelephony.setAccessible(true);
	     
	           // Invoke getITelephony() to get the ITelephony interface
	           Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);
	     
	           // Get the endCall method from ITelephony
	           Class telephonyInterfaceClass =  
	               Class.forName(telephonyInterface.getClass().getName());
	           Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
	     
	           // Invoke endCall()
	           methodEndCall.invoke(telephonyInterface);
	     
	       } catch (Exception ex) { // Many things can go wrong with reflection calls
	          //Logger.e("PhoneStateReceiver **" + ex.toString());
	          return false;
	       }
	       return true;
	    }*/
	   
	}



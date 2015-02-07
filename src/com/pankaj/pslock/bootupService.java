package com.pankaj.pslock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class bootupService extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		 if(Service_Enabled(context))
		 {
			 if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
			 {
				 Intent serviceLauncher = new Intent(context, com.pankaj.pslock.MyService.class);
				 context.startService(serviceLauncher);
			 }
		 }
	}
	
	private boolean Service_Enabled(Context context)
	{
		SharedPreferences sprefPin = context.getSharedPreferences("PS_Service", 0);	  //0 means MODE_PRIVATE	
		return sprefPin.getBoolean("PSService", false); 					
	}

}

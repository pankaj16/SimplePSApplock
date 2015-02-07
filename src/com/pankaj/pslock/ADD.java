package com.pankaj.pslock;

import com.pankaj.pslock.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ADD extends Activity {

	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		db=openOrCreateDatabase("PLock",MODE_PRIVATE,null);
		db.execSQL("CREATE TABLE IF NOT EXISTS appLockList(appID INTEGER,appName TEXT,packageName TEXT);");
		
	}
	
	public void btnAdd_click(View view)
	{
		try
		{
		
	    EditText edtApp=(EditText)findViewById(R.id.edtApps);
	   
	    db.execSQL("INSERT INTO appLockList VALUES(null,'"+ edtApp.getText().toString()+"',null);");
	   
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
	
	@Override
	protected void onStop() {
		db.close();
		super.onStop();
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}*/

}

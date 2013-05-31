package com.roro.vms;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private final static String keyFirstRun = "firstRun";
	
    private final String INTENT_ACTION = "com.android.iVMS_5060.CameraList.View";
    private final String USERNAME = "UserName";
    private final String PASSWORD= "Password";
    private final String SERVER_ADDRESS = "ServerAddress";		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		boolean firstRun = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(keyFirstRun, true);
		if (firstRun){		
			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			editor.putBoolean(keyFirstRun, false);
			editor.commit();
			
			start_config();
		}		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}
		
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.btnRemote:
			start_remote_video();
			break;
		case R.id.btnLocal:
			start_local_video();
			break;
		case R.id.btnConfig:
			start_config();
			break;
		default:
			break;
		}
	}
	
    private void start_remote_video() {
    	
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String serverAddr = sharedPreferences.getString("pref_key_remote_addr", "");
    	String username = sharedPreferences.getString("pref_key_remote_username", "");
    	String password = sharedPreferences.getString("pref_key_remote_password", "");
    	
        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION);
        Bundle data = new Bundle();
        data.putString(USERNAME, username);
        data.putString(PASSWORD, password);
        data.putString(SERVER_ADDRESS, serverAddr);
        intent.putExtras(data);
        startActivity(intent);
    }
    
    private void start_local_video() {
		Intent intent = new Intent(this, LocalVideoActivity.class);
		startActivity(intent);  	
    }
    
    private void start_config(){
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);    	
    }
}

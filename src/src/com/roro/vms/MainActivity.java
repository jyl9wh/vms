package com.roro.vms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.AlteredCharSequence;
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
	/**
	 * http://www.3gvs.net:81/service/dvrs_mcu.php?username=dx&password=12345&width=800&height=1232&netType=0&decoderCapbility=2&version=V2.00.00
	 * rtsp://210.83.80.211:7554/livestream?deviceid=dx1&channel=1&streamtype=1&netType=0&streamEncoding=1&acsip=210.83.80.211&acsport=7660&acsusr=admin&acspwd=12345&usrtype=1&naluflag=1&transport=0/track
	 * 
	 * http://192.168.1.120:81/service/jump_mcu.php?deviceid=dx11&&channel=1&&channelname=%E9%80%9A%E9%81%9301&&streamtype=0&&acsip=192.168.1.120&&acsport=7660&&acsteamip=192.168.1.120&&acsteamport=7554&&acsusr=admin&&acspwd=12345&&usrtype=1&&ptzcontrol=1&&transport=0&&netType=0&&streamEncoding=1
	 * rtsp://192.168.1.120:7554/livestream?deviceid=dx11&channel=1&streamtype=0&netType=0&streamEncoding=1&acsip=192.168.1.120&acsport=7660&acsusr=admin&acspwd=12345&usrtype=1&naluflag=1&transport=0/track
	 */
    private void start_remote_video() {
    	
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	if (sharedPreferences.getBoolean(CVal.pref_key_remote_use_vitamio, false)){
    		Intent intent = new Intent(this, RemoteVideoActivity.class);
    		startActivity(intent); 
    		return;
    	}
    	
    	String serverAddr = sharedPreferences.getString(CVal.pref_key_remote_addr, "");
    	String username = sharedPreferences.getString(CVal.pref_key_remote_username, "");
    	String password = sharedPreferences.getString(CVal.pref_key_remote_password, "");
    	
    	try{
        	
            Intent intent = new Intent();
            intent.setAction(INTENT_ACTION);
            Bundle data = new Bundle();
            data.putString(USERNAME, username);
            data.putString(PASSWORD, password);
            data.putString(SERVER_ADDRESS, serverAddr);
            intent.putExtras(data);
            startActivity(intent);   		
    	}
    	catch (ActivityNotFoundException e) {
    		AlertDialog dlg = new AlertDialog.Builder(this).setTitle("提示ʾ")
    				.setMessage("请先安装头盔监控客户端.")
    				.setPositiveButton("确定", null)
    				.create();
    		dlg.show();
		}
    	

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

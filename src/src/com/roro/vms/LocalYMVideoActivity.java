package com.roro.vms;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Player.Core.PlayerCore;

public class LocalYMVideoActivity extends Activity {
	
	//private VideoView mVideoView;
	private PlayerCore ymPlayerCore;
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.activity_local_ymvideo);
				
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String serverAddr = sharedPreferences.getString(CVal.pref_key_local_addr, "");
    	String username = sharedPreferences.getString(CVal.pref_key_local_username, "");
    	String password = sharedPreferences.getString(CVal.pref_key_local_password, "");
    	
    	String[] ipport = serverAddr.split(":");
    	String ip = (ipport != null && ipport.length > 0) ? ipport[0] : "";
    	String port = (ipport != null && ipport.length > 1) ? ipport[1] : "0";
    	
    	iv = (ImageView)findViewById(R.id.imageview);
    	
    	try {
    		ymPlayerCore = new PlayerCore(this, 4);
        	ymPlayerCore.InitParam(ip, Integer.parseInt(port), username, password, 1);        	
        	
        	iv.setVisibility(View.VISIBLE);
        	ymPlayerCore.Play(0, iv);
    	} catch (Exception e){
    		e.printStackTrace();
    		Toast.makeText(this, "不能打开视频" + e.getMessage(), Toast.LENGTH_SHORT).show();
    	}    	    	
				
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ymPlayerCore.Stop();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ymPlayerCore.Pause();
		//iv.setVisibility(View.GONE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
    	iv.setVisibility(View.VISIBLE);
    	ymPlayerCore.Resume();		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_remote_video, menu);
//		return true;
//	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}	

}

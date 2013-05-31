package com.roro.vms;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class TwoVideoActivity extends ActivityGroup {
	
	private final static String keyFirstRun = "firstRun";
	
    private final String INTENT_ACTION = "com.android.iVMS_5060.CameraList.View";
    private final String USERNAME = "UserName";
    private final String PASSWORD= "Password";
    private final String SERVER_ADDRESS = "ServerAddress";	
	
	private VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.activity_two_video);
				
		start_local_video();
		
		start_remote_video();	
		
	}
	
	private void start_local_video(){
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String serverAddr = sharedPreferences.getString("pref_key_local_addr", "");		
		
		mVideoView = (VideoView) findViewById(R.id.localVideoView);
		mVideoView.setVideoPath(serverAddr);
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		//mVideoView.setMediaController(new MediaController(this));
		mVideoView.start();			
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
	        
	        LocalActivityManager m_ActivityManager = getLocalActivityManager();
	        ViewGroup view = (ViewGroup) (m_ActivityManager.startActivity("", intent)).getDecorView(); 
	        //加入到主程序的容器中 
	        RelativeLayout layout = (RelativeLayout)findViewById(R.id.remoteVideoView); 
	        layout.addView(view, 400, 400);	        
	        
	    }	

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_remote_video, menu);
//		return true;
//	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (mVideoView != null)
			mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}	

}

package com.roro.vms;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class RemoteVideoActivity extends Activity {
	
	static final String tag = RemoteVideoActivity.class.getSimpleName();
	
	private VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.activity_remote_video);
				
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String serverAddr = sharedPreferences.getString(CVal.pref_key_remote_addr, "");
    	String username = sharedPreferences.getString(CVal.pref_key_remote_username, "");
    	String password = sharedPreferences.getString(CVal.pref_key_remote_password, "");
    	
    	String ip = null;
    	if (serverAddr.contains(":")){
    		ip = serverAddr.substring(0, serverAddr.indexOf(":"));
    	}
    	else{
    		ip = serverAddr;
    	}
    	
    	String deviceId = sharedPreferences.getString(CVal.pref_key_remote_deviceid, "dx11");
    	String channel = sharedPreferences.getString(CVal.pref_key_remote_channel, "1");
    	
    	StringBuffer rtspBuf = new StringBuffer("rtsp://").append(ip).append(":7554/livestream?");
    	rtspBuf.append("deviceid=").append(deviceId);
    	rtspBuf.append("&channel=").append(channel);
    	rtspBuf.append("&streamtype=0&netType=0&streamEncoding=1");
    	rtspBuf.append("&acsip=").append(ip).append("&acsport=7660");
    	rtspBuf.append("&acsusr=").append(username);
    	rtspBuf.append("&acspwd=").append(password);
    	rtspBuf.append("&usrtype=1&naluflag=1&transport=0/track");
    	
    	String rtsp = rtspBuf.toString();
    	
    	Log.i(tag, "play: " + rtsp);
		
		mVideoView = (VideoView) findViewById(R.id.remoteVideoView);
		mVideoView.setVideoPath(rtsp);
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		//mVideoView.setMediaController(new MediaController(this));
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("正在加载...");
	    dialog.setIndeterminate(true);
	    dialog.setCancelable(true);
	    dialog.show();
	    
	    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer arg0) {
				dialog.cancel();
				mVideoView.start();
			}
		});
	    
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
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

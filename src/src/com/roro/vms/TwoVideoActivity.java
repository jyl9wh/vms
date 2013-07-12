package com.roro.vms;

import io.vov.vitamio.widget.VideoView;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.Player.Core.PlayerCore;

public class TwoVideoActivity extends ActivityGroup {
	
	final static String tag = TwoVideoActivity.class.getSimpleName();
	
	private final static String keyFirstRun = "firstRun";
	
	private VideoView localVideoView;
	private android.widget.VideoView remoteVideoView;
	
	private final int ReqCode_Setting_First = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.activity_two_video);
		
		boolean firstRun = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(keyFirstRun, true);
		if (firstRun){		
			SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
			editor.putBoolean(keyFirstRun, false);
			editor.commit();
			
			start_config();
		}
		else{
			start_local_video();
			start_remote_video();
		}
		
	}
	
    private void start_config(){
		Intent intent = new Intent(this, SettingsActivity.class);		
		startActivityForResult(intent, ReqCode_Setting_First);
    }	
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == this.ReqCode_Setting_First){
    		start_local_video();		
    		start_remote_video();
    	}
    }
	
	private void start_local_video(){
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String serverAddr = sharedPreferences.getString(CVal.pref_key_local_addr, "");
    	String username = sharedPreferences.getString(CVal.pref_key_local_username, "");
    	String password = sharedPreferences.getString(CVal.pref_key_local_password, "");
    	
    	String[] ipport = serverAddr.split(":");
    	String ip = (ipport != null && ipport.length > 0) ? ipport[0] : "";
    	String port = (ipport != null && ipport.length > 1) ? ipport[1] : "0";

    	ImageView iv = (ImageView)findViewById(R.id.imageview);
    	
    	try {
    		PlayerCore ymPlayerCore = new PlayerCore(this, 4);
        	ymPlayerCore.InitParam(ip, Integer.parseInt(port), username, password, 1);        	
        	
        	iv.setVisibility(View.VISIBLE);
        	ymPlayerCore.Play(0, iv);
    	} catch (Exception e){
    		e.printStackTrace();
    		Toast.makeText(this, "不能打开视频" + e.getMessage(), Toast.LENGTH_SHORT).show();
    	}     	
		
	}
	
	   private void start_remote_video() {
	    	
	    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    	String serverAddr = sharedPreferences.getString("pref_key_remote_addr", "");
	    	String username = sharedPreferences.getString("pref_key_remote_username", "");
	    	String password = sharedPreferences.getString("pref_key_remote_password", "");
	    	
	    	String deviceId = sharedPreferences.getString(CVal.pref_key_remote_deviceid, "dx11");
	    	String channel = sharedPreferences.getString(CVal.pref_key_remote_channel, "1");
	    	
	    	String ip = null;
	    	if (serverAddr.contains(":")){
	    		ip = serverAddr.substring(0, serverAddr.indexOf(":"));
	    	}
	    	else{
	    		ip = serverAddr;
	    	}
	    	
	    	StringBuffer rtspBuf = new StringBuffer("rtsp://").append(ip).append(":7554/livestream?");
	    	rtspBuf.append("deviceid=").append(deviceId);
	    	rtspBuf.append("&channel=").append(channel);
	    	rtspBuf.append("&streamtype=0&netType=0&streamEncoding=1");
	    	rtspBuf.append("&acsip=").append(ip).append("&acsport=7660");
	    	rtspBuf.append("&acsusr=").append(username);
	    	rtspBuf.append("&acspwd=").append(password);
	    	rtspBuf.append("&usrtype=1&naluflag=1&transport=0/track");    	
	    	
			final Uri uri = Uri.parse(rtspBuf.toString());
			
			remoteVideoView = (android.widget.VideoView) findViewById(R.id.remoteVideoView);
			remoteVideoView.setVideoURI(uri);
			//vv.setVideoPath(path);
			
			
			remoteVideoView.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(android.media.MediaPlayer arg0) {
					Log.w(tag, "onCompletion");
					remoteVideoView.setVideoURI(uri);
					remoteVideoView.start();
				}
			});
			
			remoteVideoView.setOnErrorListener(new android.media.MediaPlayer.OnErrorListener() {
				
				@Override
				public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
					Log.w(tag, "onError");
					return false;
				}
			});
			
			
			remoteVideoView.start();
	    	
	    }	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_remote_video, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (localVideoView != null)
			localVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}	

}

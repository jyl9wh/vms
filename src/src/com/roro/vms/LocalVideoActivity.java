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

public class LocalVideoActivity extends Activity {
	
	private VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.activity_local_video);
				
    	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String serverAddr = sharedPreferences.getString(CVal.pref_key_local_addr, "");		
		
		mVideoView = (VideoView) findViewById(R.id.localVideoView);
		mVideoView.setVideoPath(serverAddr);
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

/*
 * Copyright (C) 2012 YIXIA.COM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.w_test_ivms_5060;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class MyVideoViewDemo extends Activity {

	private String path = "rtsp://192.168.1.110/mpeg4cif";
	//private String path = "rtsp://admin:123456@hcvisionguan.oicp.net:9002/mpeg4";
	private VideoView mVideoView;
	//private VideoView mVideoView1;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;

		setContentView(R.layout.myvideoview);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mVideoView.setVideoPath(path);
		mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
		//mVideoView.setMediaController(new MediaController(this));
		mVideoView.start();
		
//		mVideoView1 = (VideoView) findViewById(R.id.surface_view1);
//		mVideoView1.setVideoPath(path);
//		mVideoView1.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
//		mVideoView1.start();		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (mVideoView != null)
			mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
		super.onConfigurationChanged(newConfig);
	}
}

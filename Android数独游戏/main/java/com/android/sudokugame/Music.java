package com.android.sudokugame;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
public class Music extends Service{
	private static final int MUSIC_ID=R.raw.yran;
	private MediaPlayer myPlayer;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		myPlayer=MediaPlayer.create(getBaseContext(), MUSIC_ID);
		myPlayer.setLooping(true);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (!myPlayer.isPlaying()) {
			myPlayer.setLooping(true);
			myPlayer.start();
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (myPlayer.isPlaying()) {
			myPlayer.stop();
			myPlayer.release();
			myPlayer=null;
		}
	}
}

package com.android.sudokugame;

import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

@SuppressLint("SimpleDateFormat")
public class Game extends Activity {
	ShuDuGameSettings mySettings=new ShuDuGameSettings();
	private MyView myView;
	public static final int DIFContinue=-1;
	public static Date beginTime;
	public static Date endTime;
	public static String usedTime;
	public static String recordContinueTime;
	public static RankSql myRankSql;
	private Rank myRank;
	private String convertDifficulty;
	public static long recordTime=0;
	public static String flagContinueDifficulty;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String strString=getPreferences(MODE_PRIVATE).getString("strContinue", "0");
		if (getIntent().getExtras().getInt("difficulty")==DIFContinue) {
			myView=new MyView(this, strString);
		}else {
			myView=new MyView(this);
		}
		setContentView(myView);
	}
	@Override
	protected void onResume() {
		super.onResume();
		beginTime=new Date(System.currentTimeMillis());
	}
	@Override
	protected void onPause() {
		super.onPause();
		switch (getIntent().getExtras().getInt("difficulty")) {
			case 0:
				convertDifficulty="Easy";
				break;
			case 1:
				convertDifficulty="Medium";
				break;
			case 2:
				convertDifficulty="Hard";
				break;
			default:
				break;
		}
		getPreferences(MODE_PRIVATE).edit().putString("strContinue",
				String.valueOf(myView.str)).commit();

		if (ShuDuGame.isStartByStart) {
			ShuDuGame.recordContinueDifficulty=convertDifficulty;
		}
		if (myView.isover) {
			myRank=new Rank();
			myRank.beginTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginTime);
			if (ShuDuGame.isStartByStart) {
				myRank.gameDifficulty=convertDifficulty;
				myRank.usedTime=usedTime;
			}
			else {
				myRank.gameDifficulty=ShuDuGame.recordContinueDifficulty;
				myRank.usedTime=recordContinueTime;
			}
			myRankSql=new RankSql(Game.this);
			try {
				myRankSql.open();
			} catch (SQLDataException e) {
				e.printStackTrace();
			}
			myRankSql.insert(myRank);
			myRankSql.close();
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		stopClock();
		Game.recordTime+=GetTime.recordLongTime;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game, menu);
		menu.getItem(0).setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent settingIntent=new Intent(Game.this, ShuDuGameSettings.class);
				startActivity(settingIntent);
				return false;
			}
		});
		return true;
	}
	public static void stopClock() {
		endTime=new Date(System.currentTimeMillis());
		usedTime=GetTime.getTime(beginTime, endTime);
	}

}

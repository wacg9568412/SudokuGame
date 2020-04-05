package com.android.sudokugame;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.app.Activity;
import android.content.Intent;
public class ShuDuGameSettings extends Activity {
	public  CheckBox chbMusic=null;
	public  CheckBox chbHint=null;
	public static boolean isChbHint=true;
	public static boolean isChbMusic=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_settings);
		chbMusic=(CheckBox) findViewById(R.id.chbMusic);
		chbHint=(CheckBox) findViewById(R.id.chbHint);
		if(isChbHint){
			chbHint.setChecked(true);
		}
		else {
			chbHint.setChecked(false);
		}
		if (isChbMusic) {
			chbMusic.setChecked(true);
		}
		else {
			chbMusic.setChecked(false);
		}
		chbHint.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					isChbHint=false;
				}
				else {
					isChbHint=true;
				}
			}
		});
		chbMusic.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					isChbMusic=true;
					Intent musicIntent=new Intent(ShuDuGameSettings.this, Music.class);
					startService(musicIntent);
				}
				else {
					isChbMusic=false;
					Intent musicIntent=new Intent(ShuDuGameSettings.this, Music.class);
					stopService(musicIntent);
				}
			}
		});
	}
}

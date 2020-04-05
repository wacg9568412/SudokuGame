package com.android.sudokugame;

import java.sql.SQLDataException;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TextView;

@SuppressWarnings({ "deprecation", "unused" })
public class RecordRank extends TabActivity {

	private TextView txtEasyRank=null;
	private TextView txtMediumRank=null;
	private TextView txtHardRank=null;
	private RankSql myDbOperation=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost=getTabHost();
		LayoutInflater.from(this).inflate(R.layout.easy_rank, tabHost.getTabContentView(), true);
		LayoutInflater.from(this).inflate(R.layout.medium_rank, tabHost.getTabContentView(), true);
		LayoutInflater.from(this).inflate(R.layout.hard_rank, tabHost.getTabContentView(), true);

		tabHost.addTab(tabHost.newTabSpec("easyHost").setContent(R.id.easyHost).setIndicator("EasyRank"));
		tabHost.addTab(tabHost.newTabSpec("mediumHost").setContent(R.id.mediumHost).setIndicator("MediumRank"));
		tabHost.addTab(tabHost.newTabSpec("hardHost").setContent(R.id.hardHost).setIndicator("HardRank"));
		txtEasyRank=(TextView) findViewById(R.id.txtEasyRank);
		txtMediumRank=(TextView) findViewById(R.id.txtMediumRank);
		txtHardRank=(TextView) findViewById(R.id.txtHardRank);
		getSqlRank();
	}
	public void getSqlRank() {
		myDbOperation=new RankSql(RecordRank.this);
		try {
			myDbOperation.open();
		} catch (SQLDataException e) {
			e.printStackTrace();
		}
		String strMessage="";
		int i=1;
		//////////查询难度为Easy的记录//////////////////////
		Rank[] easyRank=myDbOperation.queryAll("Easy");
		if (easyRank!=null) {
			for (Rank rank : easyRank) {
				strMessage+="\t\t"+(i++)+"\t\t\t"+rank.toString();
			}
			i=1;
			txtEasyRank.setText(strMessage);
			strMessage="";
		}
		else {
			txtEasyRank.setText(strMessage+"\t\t空");
			strMessage="";
		}
		//////////查询难度为Medium的记录//////////////////////
		Rank[] mediumRank=myDbOperation.queryAll("Medium");
		if (mediumRank!=null) {
			for (Rank rank : mediumRank) {
				strMessage+="\t\t"+(i++)+"\t\t\t"+rank.toString();
			}
			i=1;
			txtMediumRank.setText(strMessage);
			strMessage="";
		}
		else {
			txtMediumRank.setText(strMessage+"\t\t空");
			strMessage="";
		}
		//////////查询难度为Hard的记录//////////////////////
		Rank[] hardRank=myDbOperation.queryAll("Hard");
		if (hardRank!=null) {
			for (Rank rank : hardRank) {
				strMessage+="\t\t"+(i++)+"\t\t\t"+rank.toString();
			}
			i=1;
			txtHardRank.setText(strMessage);
			strMessage="";
		}
		else {
			txtHardRank.setText(strMessage+"\t\t空");
			strMessage="";
		}
		myDbOperation.close();
	}
}

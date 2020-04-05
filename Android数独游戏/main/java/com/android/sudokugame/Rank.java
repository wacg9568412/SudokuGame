package com.android.sudokugame;

public class Rank {
	public String gameDifficulty;
	public String beginTime;
	public String usedTime;
	@Override
	public String toString() {
		String str="";
		str+=this.usedTime+"\t\t\t"+this.beginTime+"\n";
		return str;
	}
}

package com.android.sudokugame;

import java.util.Date;

public class GetTime {
	public static String time;
	public static long recordLongTime;
	public static String getTime(Date begin, Date end) {
		long diff = end.getTime() - begin.getTime();// 这样得到的差值是微秒级别
		recordLongTime=diff;
		time =transmitLongToString(diff);
		return time;
	}
	public static String transmitLongToString(long diff) {
		String str;
		long days = diff / (1000 * 60 * 60 * 24);
		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60))
				/ (1000 * 60);
		long second = (diff - days * (1000 * 60 * 60 * 24) - hours
				* (1000 * 60 * 60)-minutes*(1000*60))/ (1000);
		String strM = String.valueOf(minutes);
		String strS = String.valueOf(second);
		if (second < 10) {
			strS = "0" + String.valueOf(second);
		}
		str =strM + "分" + strS+"秒";
		return str;
	}
}

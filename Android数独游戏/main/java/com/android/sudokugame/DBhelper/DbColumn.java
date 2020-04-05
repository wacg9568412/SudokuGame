package com.android.sudokugame.DBhelper;

import android.provider.BaseColumns;

public class DbColumn implements BaseColumns{

    public static final String TABLE_NAME="xiaodiao";

//	public static final String AUTUHORITY="data.test";
//	public static final Uri CONTENT_URI=Uri.parse("content://"+AUTUHORITY+"/"+TABLE_NAME);

    //列名
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String LEVEL = "level";
    //列 索引值
//	public static final int _ID_COLUMN = 0;
//	public static final int NAME_COLUMN = 1;
//	public static final int PASSWORD_COLUMN = 2;
//	public static final int LEVEL_COLUMN = 3;
//	//查询结果
//	public static final String[] PROJECTION ={
//		_ID,//0
//		NAME,//1
//		PASSWORD,//2
//		LEVEL//3
//	};

}

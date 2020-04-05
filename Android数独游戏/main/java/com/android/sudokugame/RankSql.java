package com.android.sudokugame;
import java.sql.SQLDataException;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class RankSql {
	private static final String DB_NAME = "rank.db";
	private static final String DB_TABLE = "rankinfo";
	private static final int DB_VERSION = 1;
	public static final String DIF = "gameDifficulty";
	public static final String BEGIN = "beginTime";
	public static final String TIME = "usedTime";
	private SQLiteDatabase myDatabase;
	private final Context myContext;
	private MyDBHelper myDBHelper;
	public RankSql(Context context) {
		myContext = context;
	}
	public static class MyDBHelper extends SQLiteOpenHelper {
		public MyDBHelper(Context context, String name, CursorFactory factory,
						  int version) {
			super(context, name, factory, version);
		}
		private final String sqlCreateTable = "create table " + DB_TABLE + "( "
				+ DIF + "  text not null," + BEGIN + "  text not null," + TIME
				+ "  text not null" + ");";
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(sqlCreateTable);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists " + DB_TABLE);
			onCreate(db);
		}
	}
	public void open() throws SQLDataException {
		myDBHelper = new MyDBHelper(myContext, DB_NAME, null, DB_VERSION);
		try {
			myDatabase = myDBHelper.getWritableDatabase();
		} catch (Exception e) {
			myDatabase = myDBHelper.getReadableDatabase();
		}
	}

	public void close() {
		if (myDatabase != null) {
			myDatabase.close();
			myDatabase = null;
		}
	}

	private Rank[] convertToRank(Cursor cursor) {
		int count = cursor.getCount();
		if (count == 0 || !cursor.moveToFirst()) {
			return null;
		}
		Rank[] myRank = new Rank[count];
		for (int i = 0; i < count; i++) {
			myRank[i] = new Rank();
			myRank[i].gameDifficulty = cursor.getString(cursor.getColumnIndex(DIF));
			myRank[i].beginTime = cursor
					.getString(cursor.getColumnIndex(BEGIN));
			myRank[i].usedTime = cursor.getString(cursor.getColumnIndex(TIME));
			cursor.moveToNext();
		}
		return myRank;
	}

	public long insert(Rank myRank) {
		ContentValues myContentValues = new ContentValues();
		myContentValues.put(DIF, myRank.gameDifficulty);
		myContentValues.put(BEGIN, myRank.beginTime);
		myContentValues.put(TIME, myRank.usedTime);
		return myDatabase.insert(DB_TABLE, null, myContentValues);
	}

	public Rank[] queryAll(String difString) {
		Cursor myCursor = myDatabase.query(DB_TABLE, new String[]{DIF,BEGIN,TIME}, DIF+"="+'"'+difString+'"', null, null, null, TIME);
		if (myCursor.getCount() < 1) {
			return null;
		}
		return convertToRank(myCursor);
	}
}
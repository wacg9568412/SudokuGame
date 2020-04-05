package com.android.sudokugame.DBhelper;
import static android.provider.BaseColumns._ID;
import static com.android.sudokugame.DBhelper.DbColumn.LEVEL;
import static com.android.sudokugame.DBhelper.DbColumn.TABLE_NAME;
import static com.android.sudokugame.DBhelper.DbColumn.NAME;
import static com.android.sudokugame.DBhelper.DbColumn.PASSWORD;
import static com.android.sudokugame.DBhelper.DbColumn.LEVEL;
import static com.android.sudokugame.DBhelper.DbColumn.TABLE_NAME;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="xiaodiao.db";
    private static final int DATABASE_VERSION=1;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
                + " TEXT NOT NULL," +PASSWORD+" TEXT NOT NULL," + LEVEL
                + " TEXT NOT NULL);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insert(String name, String password,String level){
        SQLiteDatabase db= getWritableDatabase();//获取可写SQLiteDatabase对象
        //ContentValues类似map，存入的是键值对
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PASSWORD, password);
        contentValues.put(LEVEL, level);
        db.insertOrThrow(TABLE_NAME, null, contentValues);
        db.close();
    }
    public void delete(String tid){
        SQLiteDatabase db= getWritableDatabase();
        String[] args = {String.valueOf(tid)};
        Log.d("adf", tid);
        db.delete(TABLE_NAME,
                "_ID=?",
                args);
    }
    public void update(String id,String name, String password,String level){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PASSWORD, password);
        contentValues.put(LEVEL, level);
        String[] whereArgs = {String.valueOf(id)};
        db.update(TABLE_NAME, contentValues, "_ID=?", whereArgs);
        db.close();
    }
    public Cursor selectByID(String tid) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                TABLE_NAME,
                new String[]{_ID,NAME,PASSWORD,LEVEL},
                _ID+" = "+tid,
                null, null, null, _ID+" asc");
    }
    public Cursor selectByNAME(String tname) {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(
                TABLE_NAME,
                new String[]{_ID,NAME,PASSWORD,LEVEL},
                NAME+" = '"+tname+"'",
                null, null, null, _ID+" asc");
    }
    public void close() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null)
            db.close();
    }
}

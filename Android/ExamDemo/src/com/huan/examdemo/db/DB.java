package com.huan.examdemo.db;

import android.R.bool;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;

public class DB extends SQLiteOpenHelper {
	public static final String EXAM_DB_NAME = "examdemo";
	
	public static final String EXAM_TABLE_NAME = "exam";
	public static final String EXAM_COLUMN_ID = "_id";
	public static final String EXAM_COLUMN_TITLE = "title";
	public static final String EXAM_COLUMN_RADIOA = "radioA";
	public static final String EXAM_COLUMN_RADIOB = "radioB";
	public static final String EXAM_COLUMN_RADIOC = "radioC";
	public static final String EXAM_COLUMN_RADIOD = "radioD";
	public static final String EXAM_COLUMN_ANSWER = "answer";
	public static final String EXAM_COLUMN_USER_ANSWER = "user_answer";
	public static final String EXAM_COLUMN_RESULT = "result";
	
	/*
	public static final String DBDONE_TABLE_NAME = "exam_done";
	public static final String DBDONE_COLUMN_ID = "_id";
	public static final String DBDONE_COLUMN_ANSWER = "answer";
	public static final String DBDONE_COLUMN_USER_ANSWER = "user_answer";
	*/

	public DB(Context context) {
		super(context, EXAM_DB_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + EXAM_TABLE_NAME + "(" + EXAM_COLUMN_ID
				+ " INTEGER," + EXAM_COLUMN_TITLE
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_RADIOA
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_RADIOB
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_RADIOC
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_RADIOD
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_ANSWER
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_USER_ANSWER
				+ " TEXT DEFAULT \"\"," + EXAM_COLUMN_RESULT
				+ " TEXT DEFAULT \"\""
				+ ")");
		/*
		db.execSQL("CREATE TABLE " + DBDONE_TABLE_NAME + "(" + DBDONE_COLUMN_ID
				+ " INTEGER," + DBDONE_COLUMN_ANSWER
				+ " TEXT DEFAULT \"\"," + DBDONE_COLUMN_USER_ANSWER
				+ " TEXT DEFAULT \"\""
				+ ")");
		 */
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}

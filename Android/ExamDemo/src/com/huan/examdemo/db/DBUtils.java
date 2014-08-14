package com.huan.examdemo.db;


import com.huan.examdemo.data.ExamData;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils {


	public static boolean isExist(int _id, SQLiteDatabase dbRead) {
		Cursor c = dbRead.query(DB.EXAM_TABLE_NAME, null, "_id=?", new String[] { _id+"" },
				null, null, null);
		if (c.moveToNext()) {
			return true;
		}else {
			return false;	
		}
	}
	
	/*
	public static boolean AddToDataBase(ExamData examData, SQLiteDatabase dbWrite){
		ContentValues cv = new ContentValues();
		cv.put(DB.DICT_COLUMN_WORD, dictData.getWord());
		cv.put(DB.DICT_COLUMN_EXPLAINS, dictData.getExplainsString());
		if (dbWrite.insert(DB.DICT_TABLE_NAME, null, cv) != -1)
			return true;
		else
			return false;
	}
	
	public static String GetExplainsFromDB(String word, SQLiteDatabase dbRead){
		String result = "";
		Cursor c = dbRead.query(DB.DICT_TABLE_NAME, null, "word=?", new String[]{word}, null, null, null);
		c.moveToNext();
		result = c.getString(c.getColumnIndex(DB.DICT_COLUMN_EXPLAINS));
		return result;
	}
	*/
}

package com.example.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBOpenHelper extends SQLiteOpenHelper {

	public MyDBOpenHelper(Context context) {
		super(context, "mydb1.db", null, 2);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		  db.execSQL
		  ("CREATE TABLE IF NOT EXISTS article (id integer primary key autoincrement, title varchar(20), content varchar(200),pic varchar(20),type integer)");  
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL("ALTER TABLE article ADD account INTEGER NULL ");
	}

}

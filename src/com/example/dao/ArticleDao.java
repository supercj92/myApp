package com.example.dao;

import java.util.ArrayList;
import java.util.List;


import com.example.db.MyDBOpenHelper;
import com.example.entity.Article;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class ArticleDao {

	private Context context;
	MyDBOpenHelper dbOpenHelper;

	public ArticleDao(Context context) {
		this.context = context;
		dbOpenHelper = new MyDBOpenHelper(context);
	}
	
	
	public int update(int fav, int id) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put("fav", fav);
			db.update("article", values, "id=?", new String[] { ""+id });
			db.close();
			return 1;
		}
		return -1;
	}
	
	
	public ArrayList<Article> findAll( String selection, String[] selectionArgs) {
		ArrayList<Article> list = null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.query("article", null, selection, selectionArgs, null, null,
					null);
			list = new ArrayList<Article>();
			while (cursor.moveToNext()) {
				Article article = new Article();
				String title = cursor.getString(cursor.getColumnIndex("title"));
				article.setTitle(title);
				String content = cursor.getString(cursor.getColumnIndex("content"));
				article.setContent(content);
				article.setPic(cursor.getString(cursor.getColumnIndex("pic")));
				article.setFav(cursor.getInt(cursor.getColumnIndex("fav")));
				article.setId(cursor.getInt(cursor.getColumnIndex("id")));
				article.setPosition(cursor.getString(cursor.getColumnIndex("position")));
				list.add(article);
			}
			cursor.close();
			db.close();
		}
		return list;
	}
	
}

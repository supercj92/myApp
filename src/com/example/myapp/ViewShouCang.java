package com.example.myapp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.dao.ArticleDao;
import com.example.entity.Article;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class ViewShouCang extends Activity {

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ArticleDao dao=new ArticleDao(this);
		datalist=dao.findAll(" fav=?", new String[]{"1"});
		showDataList();
	}

	TextView toptv;
	ArrayList<Article> datalist;
	ListView listview;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.hotel);
		toptv=(TextView)this.findViewById(R.id.toptv);
		toptv.setText("Œ“µƒ ’≤ÿ");
		listview=(ListView)this.findViewById(R.id.hotel);
		ArticleDao dao=new ArticleDao(this);
		datalist=dao.findAll(" fav=?", new String[]{"1"});
		showDataList();
		
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Article article=datalist.get(arg2);
				Intent intent=new Intent();
				intent.setClass(ViewShouCang.this, Details.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("article", article);
				intent.putExtras(bundle);
				startActivity(intent);
//				ViewShouCang.this.finish();
			}
		});
		
	}

public void showDataList(){
		
		SimpleAdapter adapter =new SimpleAdapter(this,
				getTripList(), R.layout.itemhotel, new String[]{"pic","title",
			"content"},
			new int[]{R.id.hotelimg,R.id.title,R.id.content});
		
		
		adapter.setViewBinder(new ViewBinder(){

			@Override
			public boolean setViewValue(View arg0, Object arg1, String arg2) {
				// TODO Auto-generated method stub
				
				if(arg0 instanceof ImageView&arg1 instanceof Bitmap){
					
					ImageView img=(ImageView)arg0;
				Bitmap pic=(Bitmap)	arg1;
				img.setImageBitmap(pic);
				return true;
					
				}
				
				return false;
			}
			
			
		});
		
		
		listview.setAdapter(adapter);
		
	}
	
	public ArrayList<Map<String,Object>> getTripList(){
		
		 ArrayList<Map<String,Object>> mylist=new  ArrayList<Map<String,Object>>();
		 
		 for(int i=0;i<datalist.size();i++){
			 
			 Article article=datalist.get(i);
			 HashMap<String,Object> map=new  HashMap<String,Object>();
			
			 AssetManager assets = getAssets();
		        InputStream is = null;
		        
		        try {
		            is = assets.open("img/"+article.getPic());
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        BitmapFactory.Options options = new BitmapFactory.Options();
		        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
			 
			// Bitmap pic=BitmapFactory.decodeFile(article.getPic());
			 map.put("pic", bitmap);
			 
			 map.put("title", article.getTitle());
			 map.put("content", article.getContent());
		
			 mylist.add(map);
		 }
		 return mylist;
		
	}
	
}

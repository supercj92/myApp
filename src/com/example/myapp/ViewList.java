package com.example.myapp;

import java.io.IOException;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewList extends Activity {

	String type;
	ArrayList<Article> datalist;
	ListView listview;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.hotel);
		
		listview=(ListView)this.findViewById(R.id.hotel);
		tv=(TextView)this.findViewById(R.id.toptv);
		
		listview.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Article article=datalist.get(arg2);
				
				Intent intent=new Intent();
				
				Bundle bundle=new Bundle();
				bundle.putSerializable("article", article);
				intent.putExtras(bundle);
				intent.setClass(ViewList.this, Details.class);
				
				startActivity(intent);
			}
		});
		
		if(this.getIntent().getExtras().getString("type")!=null){
			type=this.getIntent().getExtras().getString("type");
			if(type.equals("5"))
				tv.setText("路线推荐 ");
			showDataList();
		}
	}
	protected void showDataList(){
		//得到数据
		ArticleDao dao=new ArticleDao(ViewList.this);
	    datalist=dao.findAll("type=?", new String[]{type});
		
	    //设置适配器
	    
	    SimpleAdapter sa=new SimpleAdapter(ViewList.this, transDataList(), R.layout.itemhotel,
	    		new String[]{"img","title","content"}, new int[]{R.id.hotelimg,
	    		R.id.title,R.id.content});
	    
	    sa.setViewBinder(new SimpleAdapter.ViewBinder(){

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if(view instanceof ImageView&&data instanceof Bitmap){
					ImageView iv=(ImageView)view;
					Bitmap img=(Bitmap)data;
				iv.setImageBitmap(img);
					return true;
				}
				
				return false;
			}
	    });
		listview.setAdapter(sa);
	}
//进行格式转换
	protected ArrayList<Map<String,Object>> transDataList(){
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<datalist.size();i++){
			
			HashMap<String, Object> map=new HashMap<String,Object>();
			
			Article article=datalist.get(i);
			
			map.put("title", article.getTitle());
			map.put("content", article.getContent());
			
			AssetManager mgr=getAssets();
			InputStream is=null;
			try {
				 is=mgr.open("img/"+article.getPic());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BitmapFactory.Options option=new BitmapFactory.Options();
			Bitmap img=BitmapFactory.decodeStream(is,null,option);
			map.put("img", img);
			
			list.add(map);	
		}
		return list;
	}
	
	
}

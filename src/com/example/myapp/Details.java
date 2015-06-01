package com.example.myapp;

import java.io.InputStream;

import com.example.dao.ArticleDao;
import com.example.entity.Article;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Details extends Activity {

//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		MyButton();
//	}
	ImageButton btn;
	TextView toptv;
	ImageButton btntomap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.details);
		toptv=(TextView)this.findViewById(R.id.toptv);
		toptv.setText("详情");
		btn=(ImageButton)this.findViewById(R.id.addToCart);
		btntomap=(ImageButton)findViewById(R.id.btntoMap);
		btntomap.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle =Details.this.getIntent().getExtras();
				final Article article = (Article)bundle.getSerializable("article");
				Intent intent=new Intent();
				intent.setClass(Details.this, com.example.baid.MainActivity.class);
				Bundle bundletomap=new Bundle();
				bundletomap.putString("address", article.getPosition());
				intent.putExtras(bundletomap);
				startActivity(intent);
			}
		});
		MyButton();
		getArticleDetail();
	}

	
	private void MyButton(){
		
		Bundle bundle =this.getIntent().getExtras();
		
		final Article article = (Article)bundle.getSerializable("article");
		if(article.getFav()==1)
			btn.setImageResource(R.drawable.icon_faved);
	//根据不同的标记来响应不同的事件
		btn.setOnClickListener(new OnClickListener(){
			int sign=article.getFav();
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(sign==1){
					btn.setImageResource(R.drawable.icon_fav_active);
					ArticleDao dao=new ArticleDao(Details.this);
					dao.update(0, article.getId());
					sign=0;
					Toast.makeText(Details.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
				}else if(sign==0){
					btn.setImageResource(R.drawable.icon_faved);
					ArticleDao dao=new ArticleDao(Details.this);
					dao.update(1, article.getId());
					sign=1;
					Toast.makeText(Details.this, "添加收藏成功", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	private void getArticleDetail() {
		// TODO Auto-generated method stub
		
		Bundle bundle =this.getIntent().getExtras();
		final Article article = (Article)bundle.getSerializable("article");
//		if(article.getFav()==1){
//			
//			
//		}
		
		 AssetManager assets = getAssets();
	        InputStream is = null;
	        
	        try {
	            is = assets.open("img/"+article.getPic());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
	        
	        ImageView img=(ImageView)this.findViewById(R.id.pic);
	        img.setImageBitmap(bitmap);
		
		TextView detitle = (TextView)this.findViewById(R.id.detitle);
		detitle.setText(article.getTitle());
		
//		ImageView img = (ImageView)this.findViewById(R.id.goodpic);
//		
//		try {
//			URL url = new URL(GoodsDetail.this.getResources().getString(R.string.path)+good.getDir()+good.getPic());
//			Bitmap pic= BitmapFactory.decodeStream(url.openStream());
//			img.setImageBitmap(pic);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		TextView decontent = (TextView)this.findViewById(R.id.decontent);
		decontent.setText(article.getContent());

	
	}
	
}

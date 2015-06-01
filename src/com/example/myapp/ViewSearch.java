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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSearch extends Activity {

	ImageButton btnsearch;
	TextView tv;
	WebView mwebview;
	EditText etsearch;
	ArticleDao dao;
	ArrayList<Article> arraylist=null;
	ListView listview;
//	 @Override
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			// TODO Auto-generated method stub
//	    	
//	    	if ( mwebview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK ) {    
//	            mwebview.goBack();   //����webView����һҳ��    
//	            return true;    
//	        }    
//	       
//			return super.onKeyDown(keyCode, event);
//		}
	
	
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(arraylist.size()==0){
				Toast.makeText(ViewSearch.this, "δ�ҵ����������Ľ��", 1).show();
			}else{
			showListView(arraylist,listview);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.search);
		
		tv=(TextView)this.findViewById(R.id.toptv);
		tv.setText("����");
		btnsearch=(ImageButton)this.findViewById(R.id.btnsearch);
		etsearch=(EditText)this.findViewById(R.id.etsearch);
		listview=(ListView)this.findViewById(R.id.resultlist);
		listview.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(ViewSearch.this, Details.class);
				Bundle bundle=new Bundle();
				Article article=arraylist.get(arg2);
				bundle.putSerializable("article", article);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		btnsearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		//arraylist=	dao.findAll("content like ?", new String[]{etsearch.getText().toString()});
			dao=new ArticleDao(ViewSearch.this);
			if(etsearch.getText().toString().equals("")){
				Toast.makeText(ViewSearch.this, "�����������ؼ���", 1).show();
			}else{
			getArticle(etsearch.getText().toString());
			}
			}
		});
	}
	
	public void getArticle(String keywords){
		final String words=keywords;
		
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
			arraylist= dao.findAll("content like ?", new String[]{"%"+words+"%"});
			Message msg=new Message();
			handler.sendMessage(msg);
			}
			
		}.start();
	
	
	}
	
	public void showListView(ArrayList<Article> arraylist,ListView listview){
		SimpleAdapter sa=new SimpleAdapter(this, transData(arraylist), R.layout.itemhotel, 
				new String[]{"pic","title","content"},
				new int[]{R.id.hotelimg,R.id.title,R.id.content});
		
		sa.setViewBinder(new SimpleAdapter.ViewBinder(){

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if(view instanceof ImageView&&data instanceof Bitmap){
				ImageView iv=(ImageView)view;
				iv.setImageBitmap((Bitmap)data);
				
				return true;
				}
				return false;
			}
		});
		
		listview.setAdapter(sa);
	}
	
	public ArrayList<Map<String,Object>> transData(ArrayList<Article> arraylist){
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<arraylist.size();i++){
			HashMap<String,Object> map=new HashMap<String,Object>();
			
			AssetManager asset=getAssets();
			InputStream is=null;
			
			try {
				is=asset.open("img/"+arraylist.get(i).getPic());
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BitmapFactory.Options option=new BitmapFactory.Options();
			Bitmap pic= BitmapFactory.decodeStream(is, null, option);
			
			map.put("pic", pic);
			map.put("title", arraylist.get(i).getTitle());
			map.put("content", arraylist.get(i).getContent());
			list.add(map);
		}
		return list;
	}
	
//		mwebview=(WebView)this.findViewById(R.id.myWebView1);
//		tv=(TextView)this.findViewById(R.id.toptv);
//		mwebview.getSettings().setJavaScriptEnabled(true);   //����֧��Javascript 
//        mwebview.getSettings().setBuiltInZoomControls(true);   //ҳ��������Ű�ť 
//        mwebview.requestFocus();   //��������������.��������ã����ڵ����ҳ�ı������ʱ��
//        //���ܵ�������̼�����Ӧ������һЩ�¼��� 
        
//        mwebview.setWebViewClient(new WebViewClient() {    
//            @Override  
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
//                //���õ����ҳ��������ӻ����ڵ�ǰ��webview����ת  
//                view.loadUrl(url);    
//                return true;    
//            }    
//            @Override  
////            public void onReceivedSslError(WebView view,   
////                    SslErrorHandler handler, android.net.http.SslError error) {   
////                //����webview����https����  
////                handler.proceed();  
////            }  
//            
//            public void onReceivedError(WebView view,  
//                    int errorCode, String description, String failingUrl) {  
//                //����ҳ�汨��ʱ�Ĵ���  
//                Toast.makeText(ViewSearch.this,   
//                        "Oh no! " + description, Toast.LENGTH_SHORT).show();  
//            }  
//        });  
        
        
//        mwebview.setWebChromeClient(new WebChromeClient() {    
//            public void onProgressChanged(WebView view, int progress) {    
//                //setTitle("ҳ������� ... " + progress + "%");    
//            	tv.setText("ҳ�������..."+progress+"%");
//                setProgress(progress * 100);    
//                if (progress == 100) {    
//                  //  setTitle(R.string.app_name);    
//                	tv.setText("�������");
//                }    
//            }    
//        }); 
		
//        String uri="http://www.baidu.com";
//		mwebview.loadUrl(uri);
        
//		
	

}

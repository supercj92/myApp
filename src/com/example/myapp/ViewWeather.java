package com.example.myapp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewWeather extends Activity {

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem menuitem = menu.add(0, 0, 0, "关于");
//		menuitem.setIcon(R.drawable.cart);
		
		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
			Intent intent = new Intent();
			intent.setClass(ViewWeather.this,Author.class);
			startActivity(intent);
		
		
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//getWeather();
	}
	//ImageView img;
	TextView tv;
	LinearLayout llayout;
	WebView mwebview;
	
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
	    	
	    	if ( mwebview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK ) {    
	            mwebview.goBack();   //返回webView的上一页面    
	            return true;    
	        }    
	       
			return super.onKeyDown(keyCode, event);
		}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.weather);
		//img=(ImageView)this.findViewById(R.id.ivweather);
		tv=(TextView)this.findViewById(R.id.toptv);
		llayout=(LinearLayout)this.findViewById(R.id.LinearLayout04);
		mwebview=(WebView)this.findViewById(R.id.mwebview2);
		tv.setText("天气提醒");
		
		mwebview.getSettings().setJavaScriptEnabled(true);
		mwebview.getSettings().setBuiltInZoomControls(false);
		mwebview.requestFocus();
		
		  mwebview.setWebViewClient(new WebViewClient() {    
	            @Override  
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	                //设置点击网页里面的链接还是在当前的webview里跳转  
	                view.loadUrl(url);    
	                return true;    
	            }    
	            @Override  
//	            public void onReceivedSslError(WebView view,   
//	                    SslErrorHandler handler, android.net.http.SslError error) {   
//	                //设置webview处理https请求  
//	                handler.proceed();  
//	            }  
	            
	            public void onReceivedError(WebView view,  
	                    int errorCode, String description, String failingUrl) {  
	                //加载页面报错时的处理  
	                Toast.makeText(ViewWeather.this,   
	                        "Oh no! " + description, Toast.LENGTH_SHORT).show();  
	            }  
	        });  
	        
	        
	        mwebview.setWebChromeClient(new WebChromeClient() {    
	            public void onProgressChanged(WebView view, int progress) {    
	                //setTitle("页面加载中 ... " + progress + "%");    
	            	tv.setText("页面加载中..."+progress+"%");
	                setProgress(progress * 100);    
	                if (progress == 100) {    
	                  //  setTitle(R.string.app_name);    
	                	tv.setText("加载完成");
	                }    
	            }    
	        }); 
	      getWeather();
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
//		llayout.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				getWeather();
//			}
//			
//		});
		
		
		
		
	}
	public void getWeather(){
		String uri="http://baidu.weather.com.cn/mweather/101091101.shtml";
		mwebview.loadUrl(uri);
	//	Uri uri = Uri.parse("http://baidu.weather.com.cn/mweather/101091101.shtml");        

		
		//        Intent outerIntent = new Intent(Intent.ACTION_VIEW, uri);  
//        startActivity(outerIntent);  
	}
}

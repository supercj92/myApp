package com.example.myapp;




import android.view.LayoutInflater;
import android.view.Window;
import android.app.Activity;
import android.app.ActivityGroup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ViewMain extends ActivityGroup {

	
	 private TabHost mTabHost = null; 
	protected void onCreate(Bundle savedInstanceState) {
		
		 super.onCreate(savedInstanceState);  
	        requestWindowFeature(Window.FEATURE_NO_TITLE);  
	        setContentView(R.layout.activity_main);  
	               
	        mTabHost = (TabHost) findViewById(R.id.maintabhost);  
	        mTabHost.setup();  
	          
	        // 此处是我解决异常加的一行代码,如果继承Activity的话可将此行注释  
	        mTabHost.setup(this.getLocalActivityManager());  
	        
	        mTabHost.addTab(mTabHost.newTabSpec("t3").setIndicator("主页",this.getResources()
	        		.getDrawable(R.drawable.home1))  
	                .setContent(new Intent(this,ViewHomePage.class)));  
	          
	        mTabHost.addTab(mTabHost.newTabSpec("t1").setIndicator("收藏",this.getResources()
	        		.getDrawable(R.drawable.fav1))  
	                .setContent(new Intent(this, ViewShouCang.class)));  
	      
	        mTabHost.addTab(mTabHost.newTabSpec("t6").setIndicator("地图",this.getResources()
	        		.getDrawable(R.drawable.dt))  
	                .setContent(new Intent(this, com.example.baid.MainActivity.class)));  
	       
	        mTabHost.addTab(mTabHost.newTabSpec("t4").setIndicator("搜索",this.getResources()
	        		.getDrawable(R.drawable.search1))  
	                .setContent(new Intent(this,ViewSearch.class)));
	        mTabHost.addTab(mTabHost.newTabSpec("t5").setIndicator("天气提醒",this.getResources()
	        		.getDrawable(R.drawable.cloud1)).
	        		setContent(new Intent(this,ViewWeather.class)));

	
	}
	
}

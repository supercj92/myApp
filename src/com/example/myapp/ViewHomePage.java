package com.example.myapp;


import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewHomePage extends Activity {

	private ImageButton b1,b2,b3,b4,b5,b6;
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.homepage);
		tv=(TextView)this.findViewById(R.id.toptv);
		tv.setText("Ê×Ò³");
		b1=(ImageButton)this.findViewById(R.id.btnhotel);
		b2=(ImageButton)this.findViewById(R.id.btnres);
		b3=(ImageButton)this.findViewById(R.id.btnplace);
		b4=(ImageButton)this.findViewById(R.id.btnconfig);
		b5=(ImageButton)this.findViewById(R.id.btntjxl);
		b6=(ImageButton)this.findViewById(R.id.btnlydt);
	
		//×¢²á¼àÌýÆ÷
		b1.setOnClickListener(new MyListener());
		b2.setOnClickListener(new MyListener());
		b3.setOnClickListener(new MyListener());
		b4.setOnClickListener(new MyListener());
		b5.setOnClickListener(new MyListener());
		b6.setOnClickListener(new MyListener());	
	}
	
	private class MyListener implements OnClickListener{

		String type=null;
		Intent intent =new Intent();
		Bundle bundle=new Bundle();
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){

			case R.id.btnhotel:
				intent.setClass(ViewHomePage.this,ViewHotel.class);
				break;
			case R.id.btnres:
				intent.setClass(ViewHomePage.this, ViewRes.class);
				break;
			case R.id.btnplace:
				intent.setClass(ViewHomePage.this, ViewPlace.class);
				break;
			case R.id.btnconfig:
				intent.setClass(ViewHomePage.this, ViewShopping.class);
				break;
			case R.id.btntjxl:
				intent.setClass(ViewHomePage.this, ViewList.class);
				type="5";
				break;
			case R.id.btnlydt:
				intent.setClass(ViewHomePage.this, ViewList.class);
				break;
		
		}	
			bundle.putString("type", type);
			intent.putExtras(bundle);
			startActivity(intent);
		
	}
	
}
}

 package com.example.myapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Welcome extends Activity {
	
	 public static String dbName="mydb1.db";//数据库的名字
	 private static String DATABASE_PATH="/data/data/com.example.myapp/databases/";//数据库在手机里的路径
	private TextView tvwelcome;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent();
			intent.setClass(Welcome.this, ViewMain.class);
			startActivity(intent);
			Welcome.this.finish();
			super.handleMessage(msg);
		}
		
		
	};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.welcome);
      tvwelcome=(TextView)this.findViewById(R.id.tvwelcome);
        final Window win=this.getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        boolean dbExist = checkDataBase();
        if(dbExist){
        	 welcome();
        }else{//不存在就把raw里的数据库写入手机
           
            	new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						   try {
						
							copyDataBase();
							Message msg=new Message();
							handlercopy.handleMessage(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
            	}.start();
        }
    }
    
   Handler handlercopy=new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		if(checkDataBase()){
			welcome();
		}
	}
   };
public void welcome(){
	
	new Thread(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			tvwelcome.setText("数据加载成功");
				sleep(2000);
				Message msg=new Message();
				handler.sendMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}		
	}.start();
}


public boolean checkDataBase(){
    SQLiteDatabase checkDB = null;
    try{
        String databaseFilename = DATABASE_PATH+dbName;
        checkDB =SQLiteDatabase.openDatabase(databaseFilename, null,
                SQLiteDatabase.OPEN_READONLY);
    }catch(SQLiteException e){
         
    }
    if(checkDB!=null){
        checkDB.close();
    }
    return checkDB !=null?true:false;
}

public void copyDataBase() throws IOException{
    String databaseFilenames =DATABASE_PATH+dbName;
    File dir = new File(DATABASE_PATH);
    if(!dir.exists())//判断文件夹是否存在，不存在就新建一个
        dir.mkdir();
    FileOutputStream os = null;
    try{
        os = new FileOutputStream(databaseFilenames);//得到数据库文件的写入流
    }catch(FileNotFoundException e){
        e.printStackTrace();
    }
    InputStream is =Welcome.this.getResources().openRawResource(R.raw.mydb1);//得到数据库文件的数据流
    byte[] buffer = new byte[8192];
    int count = 0;
    try{
         
        while((count=is.read(buffer))>0){
            os.write(buffer, 0, count);
            os.flush();
        }
    }catch(IOException e){
         
    }
    try{
        is.close();
        os.close();
        
    }catch(IOException e){
        e.printStackTrace();
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

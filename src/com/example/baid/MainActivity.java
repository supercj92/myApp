package com.example.baid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.myapp.R;

/**
 * ��demo����չʾ��ν��е�������������õ�ַ�������꣩����������������������������ַ��
 */
public class MainActivity extends Activity implements
		OnGetGeoCoderResultListener {
	GeoCoder mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	BaiduMap mBaiduMap = null;
	MapView mMapView = null;
	LatLng mLatLng;
	String address;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(this.getApplication());  
		setContentView(R.layout.activity_geocoder);
	Log.i("debug", "onCreate");
		
		// ��ͼ��ʼ��
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

//		//����map��ʼ��
//		mLatLng=new LatLng(39.9165020000,119.5248500000);
//		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(mLatLng));
		
		
		
		// ��ʼ������ģ�飬ע���¼�����
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		
		//intent��������
		
		
	}

	/*
	 * 
	 * intent��������
	 */
	public void intentSearch(){

	
		if(this.getIntent().getExtras()!=null){
		
			
			address=this.getIntent().getExtras().getString("address");
			Log.i("debug", ""+address);
			mSearch.geocode(new GeoCodeOption().city("�ػʵ�").address(address));
		}
	}
	
	/**
	 * ��������
	 * 
	 * @param v
	 */
	public void SearchButtonProcess(View v) {
		
			EditText editCity = (EditText) findViewById(R.id.city);
			EditText editGeoCodeKey = (EditText) findViewById(R.id.geocodekey);
			// Geo����
			mSearch.geocode(new GeoCodeOption().city(
					editCity.getText().toString()).address(
					editGeoCodeKey.getText().toString()));
		
	}

	@Override
	protected void onPause() {
		Log.i("debug", "onPause");
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i("debug", "onResume");
		mMapView.onResume();
		
		super.onResume();
		intentSearch();
	}

	@Override
	protected void onDestroy() {
		mMapView.onDestroy();
		mSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MainActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
					.show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(result.getLocation(), 18));
		Log.i("debug", "found");
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		

	}

}

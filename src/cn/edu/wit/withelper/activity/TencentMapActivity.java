package cn.edu.wit.withelper.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.MapOverlay.OnTapListener;
import cn.edu.wit.withelper.services.OverlayItemService;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

import com.tencent.tencentmap.mapsdk.map.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.ItemizedOverlay;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapController;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.OverlayItem;
import com.tencent.tencentmap.mapsdk.map.Projection;

public class TencentMapActivity extends MapActivity {

	
	MapView mMapView;
	MapController mMapController;
	View viewTip=null;
	TextView tvTapTitle = null;
	TextView tvTapSub = null ;
	
	
	
	MapOverlay mapOverlay=null;
	
	int iTipTranslateX=0;
	int iTipTranslateY=0;
	
	Runnable runAnimate = null;

	
	// 定义一个变量，来标识是否退出
    private static boolean isOpenStreetView = false;
 
    @SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
 
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isOpenStreetView = false;
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.tencentmap);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.SCHOOLMAP);
		
		LayoutInflater layoutInfla = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		viewTip=layoutInfla.inflate(R.layout.tencentmaptipview, null);
		tvTapTitle = (TextView) viewTip.findViewById(R.id.txtViewSelectTile);
		tvTapSub= (TextView) viewTip.findViewById(R.id.txtViewSelectSub);
		
		
		mMapView = (MapView) findViewById(R.id.itemizedoverlayview);
		
		//设置启用内置的缩放控件
		mMapView.setBuiltInZoomControls(true); 
		
		mMapController = mMapView.getController();

		Drawable marker = getResources().getDrawable(R.drawable.markpoint);  //得到需要标在地图上的资源
		
		this.iTipTranslateY=marker.getIntrinsicHeight();
		
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());   //为maker定义位置和边界
		
		//调用不带marker的构造函数
		mapOverlay=new MapOverlay(this);
		
		//初始化位置
		GeoPoint ge=new GeoPoint((int) (30.459074 * 1E6), (int) (114.433013 * 1E6)); 
		
		//添加点击事件监听
		mapOverlay.setOnTapListener(onTapListener);
		
        //添加ItemizedOverlay实例到mMapView
		mMapView.addOverlay(mapOverlay); 
		
		//移动到制定的初始位置
		mMapController.animateTo(ge);
		
		//设置为最大缩放级别
		mMapController.setZoom(mMapView.getMaxZoomLevel());//最大为18
//		System.out.println(mMapView.getMaxZoomLevel());
		
		//设置为卫星图
		mMapView.setSatellite(true);
		
		//获取点击处的经纬度
		mMapView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					int iClickX = (int)event.getX();
	                int iClickY = (int)event.getY();
	                GeoPoint geoPt = mMapView.getProjection().fromPixels(iClickX, iClickY);
	                
	                openStreetView(geoPt);
	                
				}
				return false;
			}
		});
		
		
		mMapView.invalidate();  //刷新地图 
//		mMapView.refreshMap();//刷新地图 
		
		TextView btnBack = (TextView) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
			}
		});
		TextView tvBackHome = (TextView) findViewById(R.id.back_home);
		tvBackHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
			}
		});
	}
	
	private GeoPoint lastPoint = null;
	private void openStreetView(GeoPoint geoPoint) {
        if (null == lastPoint 
        		|| (Math.abs(lastPoint.getLatitudeE6()-geoPoint.getLatitudeE6())>50
        		&& Math.abs(lastPoint.getLongitudeE6()-geoPoint.getLongitudeE6())>50)
        		|| !isOpenStreetView 
        		|| mMapView.getZoomLevel()<mMapView.getMaxZoomLevel()) {
            isOpenStreetView = true;
            lastPoint = geoPoint;
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 400);
        } else {
            Intent intent = new Intent(getApplicationContext(), StreetViewActivity.class);
            intent.putExtra("lat", geoPoint.getLatitudeE6());
            intent.putExtra("lon", geoPoint.getLongitudeE6());
            startActivity(intent);
            overridePendingTransition(SwitchActivityAnim.rightIn(), SwitchActivityAnim.rightOut());
        }
    }
	
	
	OnTapListener onTapListener=new OnTapListener(){

		@Override
		public void onTap(OverlayItem itemTap) {
			
			if(viewTip==null||itemTap==null)
			{
				return;
			}
			
			tvTapTitle.setText(itemTap.getTitle());
			tvTapSub.setText(itemTap.getSnippet());
			
			MapView.LayoutParams layParaOntap=new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,MapView.LayoutParams.WRAP_CONTENT,itemTap.getPoint(),iTipTranslateX,-iTipTranslateY,MapView.LayoutParams.BOTTOM_CENTER);
			
			if(mMapView.indexOfChild(viewTip)==-1) {
				mMapView.addView(viewTip,layParaOntap);
			} else {
				mMapView.updateViewLayout(viewTip,layParaOntap);
			}
		}

		@Override
		public void onEmptyTap(GeoPoint pt) {
			if(mMapView.indexOfChild(viewTip)>=0)
			{
				mMapView.removeView(viewTip);
			}
		}
	};
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
		}
		return super.onKeyDown(keyCode, event);
	}
}



class MapOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> itemList = new ArrayList<OverlayItem>();

	private OnTapListener tapListener=null;


	public MapOverlay(Drawable marker, Context context) {
		
		super(boundCenterBottom(marker));
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		itemList = OverlayItemService.getInstance(context).getOverlayItemListFromDB();
		populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	
	public MapOverlay(Context context) {
		super(context);
		itemList = OverlayItemService.getInstance(context).getOverlayItemListFromDB();
		
		populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView) {

		// Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换
		Projection projection = mapView.getProjection(); 
		
		for (int index = size() - 1; index >= 0; index--) { // 遍历GeoList
			
			OverlayItem overLayItem = getItem(index); // 得到给定索引的item

			String title = overLayItem.getTitle();
			
			// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
			Point point = projection.toPixels(overLayItem.getPoint(), null); 

			Paint paintCircle = new Paint();
			paintCircle.setColor(Color.RED);
			canvas.drawCircle(point.x, point.y, 5, paintCircle); // 画圆

			Paint paintText = new Paint();
			paintText.setColor(Color.WHITE);
			paintText.setTextSize(25);
			canvas.drawText(title, point.x - title.length()*12, point.y - 15, paintText); // 绘制文本

		}

//		super.draw(canvas, mapView);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return itemList.get(i);
	}

	@Override
	public int size() {
		return itemList.size();
	}

	@Override
	protected boolean onTap(int i) {
		OverlayItem itemSelect=itemList.get(i);
		setFocus(itemSelect);
		if(tapListener!=null)
		{
			tapListener.onTap(itemSelect);
		}
		return true;
	}
	
	
	
	@Override
	public void onEmptyTap(GeoPoint pt) {
		super.onEmptyTap(pt);
		
		if(tapListener!=null)
		{
			tapListener.onEmptyTap(pt);
		}
	}

	interface OnTapListener
	{
		void onTap(OverlayItem itemTap);
		void onEmptyTap(GeoPoint pt);
	}
	
	public void setOnTapListener(OnTapListener listnerTap)
	{
		tapListener=listnerTap;
	}
}
package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.List;

import cn.edu.wit.withelper.bean.OverlayItemUtil;

import com.tencent.tencentmap.mapsdk.map.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.OverlayItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OverlayItemService extends BaseService{

	
//private static final String TAG = "OverlayItemService";
	
	public static OverlayItemService overlayItemService = null;
	
	public static OverlayItemService getInstance(Context paramContext) {
		
		if (null == overlayItemService) {
			overlayItemService = new OverlayItemService(paramContext);
		}
		
		return overlayItemService;
	}
	
	private OverlayItemService(Context paramContext) {
		super(paramContext);
	}
	
	
	
	/**
	 * 将列表存入数据库
	 * @param borrowList
	 */
	public void saveOverlayItemList(List<OverlayItem> overlayItemList){
		
		for (OverlayItem overlayItem : overlayItemList) {
			saveOverlayItem(overlayItem);
		}
		
	}

	/**
	 * 将信息存入数据库
	 * @param overlayItem
	 */
	public void saveOverlayItem(OverlayItem overlayItem){
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//先判断 数据库中是否存在 当前用户 借阅的当前图书
		Cursor cursor = db.rawQuery("select * from "+OverlayItemUtil.TB_NAME+" where "+OverlayItemUtil.TITLE+"=?", new String[]{overlayItem.getTitle()});
		if (cursor.getCount() <= 0) {
			
			ContentValues values = new ContentValues();
			values.put(OverlayItemUtil.TITLE, overlayItem.getTitle());
			values.put(OverlayItemUtil.SNIPPET, overlayItem.getSnippet());
			values.put(OverlayItemUtil.POINT_LAT, overlayItem.getPoint().getLatitudeE6());
			values.put(OverlayItemUtil.POINT_LON, overlayItem.getPoint().getLongitudeE6());
//			values.put(OverlayItem.MARKER, overlayItem.getMarker());
			db.insert(OverlayItemUtil.TB_NAME, null, values);
		}	
		
		db.close();
	}
	
	
	/**
	 * 从数据库获取当前用户借阅图书列表
	 * @param userID
	 * @return
	 */
	public List<OverlayItem> getOverlayItemListFromDB(){
		
		List<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor cursor = db.rawQuery("select * from "+ OverlayItemUtil.TB_NAME, new String[]{});
	    
	    while (cursor.moveToNext()) {
	    	
	    	String title = cursor.getString(cursor.getColumnIndex(OverlayItemUtil.TITLE));
	    	String snippet = cursor.getString(cursor.getColumnIndex(OverlayItemUtil.SNIPPET));
	    	
	    	double latitude = cursor.getDouble(cursor.getColumnIndex(OverlayItemUtil.POINT_LAT));
	    	double longitude = cursor.getDouble(cursor.getColumnIndex(OverlayItemUtil.POINT_LON));
	    	GeoPoint point = new GeoPoint((int)(latitude*1E6),(int)(longitude*1E6));
	    	
	    	OverlayItem overlayItem = new OverlayItem(point, title, snippet);
	    	overlayItem.setDragable(false);
	    	overlayItemList.add(overlayItem);
	    	
	    	System.out.println(overlayItem);
	    }
	    db.close();
	    return overlayItemList;
	}
	
}

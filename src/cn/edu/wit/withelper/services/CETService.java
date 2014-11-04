package cn.edu.wit.withelper.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import cn.edu.wit.withelper.bean.CET;
import cn.edu.wit.withelper.util.InterfaceUtil;

public class CETService extends BaseService{

	
	private static final String TAG = "CETService";

	public CETService(Context paramContext) {
		super(paramContext);
	}
	
	public static CET getCETInfo(CET cet){
		
		
		String URL = "http://www.withelper.com/API/Android/GetCet";
		final Map<String, String> params = new HashMap<String, String>();

		params.put(CET.CETID, cet.getAdmission());
		params.put(CET.NAME, cet.getName());
		
		JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);
		
		if (jsonResult == null) {
			Log.i(TAG, "result = null");
			return null;
		} else {
			Log.i(TAG, "result = " + jsonResult.toString());
			
			return getCETByJson(jsonResult, cet);
		}
		
	}
	
	
	@SuppressWarnings("finally")
	private static CET getCETByJson(JSONObject jsonResult, CET cet){
		
		
		try {
			String errorString = jsonResult.getString("error");

			if (null != errorString && "null".equals(errorString)) {
				cet.setSchool(jsonResult.getString("school"));
				cet.setType(jsonResult.getString("type"));
				cet.setTime(jsonResult.getString("time"));
				cet.setTotalGrade(jsonResult.getString("totalGrade"));
				cet.setListenGrade(jsonResult.getString("listenGrade"));
				cet.setReadGrade(jsonResult.getString("readGrade"));
				cet.setWriteGrade(jsonResult.getString("writeGrade"));
				
			}else if("Invalid input".equals(errorString)) {
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			Log.i(TAG, "totalGrade = "+cet.getTotalGrade());
			return cet;
		}
		
	}

	
	
}

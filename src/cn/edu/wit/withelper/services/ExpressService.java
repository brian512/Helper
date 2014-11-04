package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import cn.edu.wit.withelper.bean.Express;
import cn.edu.wit.withelper.bean.ExpressContentData;
import cn.edu.wit.withelper.util.InterfaceUtil;

public class ExpressService extends BaseService{
	
	private static final String TAG = "ExpressService";

	public ExpressService(Context paramContext) {
		super(paramContext);
	}
	
	public static Express getExpressInfo(Express express){
		
		
		String URL = "http://www.withelper.com/API/Android/Express";
		final Map<String, String> params = new HashMap<String, String>();

		params.put(Express.EXPRESSID, express.getExpressID());
		params.put(Express.ORDERID, express.getOrder());

		
		JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);
		
		if (jsonResult == null) {
			Log.i(TAG, "result = null");
			return null;
		} else {
			Log.i(TAG, "result = " + jsonResult.toString());
			
			return getExpressByJson(jsonResult, express);
		}
		
	}
	
	
	@SuppressWarnings("finally")
	private static Express getExpressByJson(JSONObject jsonResult, Express express){
		
		
		try {
			String errorString = jsonResult.getString("error");

			if (null != errorString && "null".equals(errorString)) {
				express.setExpressID(jsonResult.getString("id"));
				express.setName(jsonResult.getString("name"));
				express.setOrder(jsonResult.getString("order"));
				express.setUpdateTime(jsonResult.getString("updateTime"));
				express.setMessage(jsonResult.getString("message"));
				express.setErrCode(jsonResult.getString("errCode"));
				express.setStatus(jsonResult.getString("status"));
				
				String datas = jsonResult.getString("data");
				List<ExpressContentData> contentList = new ArrayList<ExpressContentData>();
				JSONArray jsonArray = new JSONArray(datas); 
				for(int i=0; i<jsonArray.length(); i++){
					
					JSONObject contentJson = jsonArray.getJSONObject(i);
					
					ExpressContentData contentData = new ExpressContentData();
					contentData.setContent(contentJson.getString("content"));
					contentData.setTime(contentJson.getString("time"));
					
					contentList.add(contentData);
				}
				
				express.setContentList(contentList);
			}else if("Invalid expressID".equals(errorString)) {
				
			}else if("Server error".equals(errorString) || "Program failed".equals(errorString) || "Maxium access count".equals(errorString)) {
				
			}else {
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			Log.i(TAG, "content = "+express.getContentList());
			return express;
		}
		
	}


}

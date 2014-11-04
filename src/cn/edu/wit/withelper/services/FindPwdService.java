package cn.edu.wit.withelper.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import cn.edu.wit.withelper.bean.UserInfo;
import cn.edu.wit.withelper.util.InterfaceUtil;

public class FindPwdService {

	public static final String TAG = "FindPwdService";
	
	public static String getPassword(UserInfo user){
		
		final String URL = "http://www.withelper.com/API/Android/ForgetPass";
		final Map<String, String> params = new HashMap<String, String>();
		params.put(UserInfo.IDCARD, user.getIDCard());
		params.put(UserInfo.USERID, user.getUserId());
		params.put(UserInfo.USERNAME, user.getUserName());
		
		JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);
		
		Log.i(TAG, jsonResult.toString());
		
		String password = null;
		if (null != jsonResult) {
			
			try {
				String errorString = jsonResult.getString("error");
				if ("null".equals(errorString)) {
					password = jsonResult.getString("password");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return password;
	}
	
}

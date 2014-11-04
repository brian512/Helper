/**
 *SharedPreferencesUtil.java
 *2011-9-13 下午10:38:06
 *Touch Android
 *http://bbs.droidstouch.com
 */
package cn.edu.wit.withelper.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.edu.wit.withelper.bean.UserInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private static final String LOGIN_USER = "login_user";
	private static final String CHECK_USED = "check_used";

	
	
	/**
	 * 将键值对存储到配置文件
	 * @param context
	 * @param spName 文件名
	 * @param map 需要写入的map键值对
	 */
	public static void editSharedPreferences(Context context, String spName, Map<String, String> map){
		
		Set<String> keys = map.keySet();
		if (null != keys) {
			
			SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
			Editor editor = sp.edit();

			Iterator<String> iterator = keys.iterator( );
			 
			while(iterator.hasNext( )) {
			 
				String key = (String) iterator.next();
				String value = (String) map.get(key);
				
				editor.putString(key, value);
			}
			
			editor.commit();
		}
	}
	
	
	public static Map<String, String> getSharedPreferences(Context context, String spName, List<String> list){
	
		SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		
		Map<String, String> map = new HashMap<String, String>();
		
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i), sp.getString(list.get(i), ""));
		}
		return map;
	}
	
	/**
	 * 保存登录用户信息
	 * @param context
	 * @param user
	 */
	public static void saveLoginUser(Context context, UserInfo user) {

		SharedPreferences sp = context.getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);

		Editor editor = sp.edit();

		editor.putString(UserInfo.USERID, user.getUserId());
		editor.putString(UserInfo.SESSIONID, user.getSessionId());
		editor.putString(UserInfo.USERNAME, user.getUserName());

		editor.commit();

	}

	/**
	 * 获取登录用户信息
	 * @param context
	 * @return
	 */
	public static UserInfo getLoginUser(Context context) {

		SharedPreferences sp = context.getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);
		String userId = sp.getString(UserInfo.USERID, "");
		String session = sp.getString(UserInfo.SESSIONID, "");
		String userName = sp.getString(UserInfo.USERNAME, "");

		if ("".equals(userId))
			return null;

		UserInfo user = new UserInfo();
		user.setUserId(userId);
		user.setSessionId(session);
		user.setUserName(userName);
		return user;
	}
	
	/**
	 * 判断是否使用过
	 * @param context
	 * @return
	 */
	public static boolean getIsUsed(Context context){
		SharedPreferences sp = context.getSharedPreferences(CHECK_USED, Context.MODE_PRIVATE);
		String isUsed = sp.getString("isUsed", "0");//默认为没有使用过
		if ("1".equals(isUsed)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 设置是否使用过
	 * @param context
	 * @param isUsed
	 */
	public static void setIsUsed(Context context, boolean isUsed){
		SharedPreferences sp = context.getSharedPreferences(CHECK_USED, Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		editor.putString("isUsed", String.valueOf(isUsed?1:0));
		
		editor.commit();
	}

}

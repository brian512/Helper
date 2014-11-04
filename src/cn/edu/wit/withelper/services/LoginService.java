package cn.edu.wit.withelper.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;
import cn.edu.wit.withelper.bean.UserInfo;
import cn.edu.wit.withelper.util.InterfaceUtil;

public class LoginService extends BaseService {

	
	private static final String TAG = "LoginService";


	public LoginService(Context paramContext) {
		super(paramContext);
	}


	public static UserInfo login(UserInfo loginUser) {

		String URL = "http://www.withelper.com/API/Android/Login";
		final Map<String, String> params = new HashMap<String, String>();

		params.put("userID", loginUser.getUserId());
		params.put("password", loginUser.getPassword());

		JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);

		if (jsonResult == null) {
			Log.i(TAG, "result = null");
			return loginUser;
		} else {
			Log.i(TAG, "result = " + jsonResult.toString());
			return getUserByJson(jsonResult, loginUser);
		}
	}

	@SuppressWarnings("finally")
	private static UserInfo getUserByJson(
			JSONObject jsonResult,
			UserInfo loginUser
	) {

		try {
			String errorString = jsonResult.getString("error");

			if (null != errorString && "null".equals(errorString)) {
				String sessionId = jsonResult.getString("sessionid");
				String userinfo = jsonResult.getString("userinfo");
				JSONTokener tokener = new JSONTokener(userinfo);
				JSONObject infoJson = (JSONObject) tokener.nextValue();
				String name = infoJson.getString("name");
				String className = infoJson.getString("classname");
				String college = infoJson.getString("college");

				loginUser.setSessionId(sessionId);
				loginUser.setUserName(name);
				loginUser.setClassName(className);
				loginUser.setCollege(college);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			Log.i(TAG, "name = " + loginUser.getUserName());
			return loginUser;
		}
	}//end of getUserByJson()
	
	// /**
	// * 通过联网，经过教务处验证登录
	// * @param loginUser 已封装用户ID和密码
	// * @return
	// * @throws Exception
	// */
	// public static String login(UserInfo loginUser) throws Exception{
	// String userName = InternetHelper.login(loginUser);
	// return userName;
	// }

//	/**
//	 * 通过本地数据库的信息登录
//	 * @param userId 已登录的用户ID
//	 * @param password 登录使用的密码
//	 * @return 返回登录的用户姓名
//	 */
//	public UserInfo localLogin(String userId, String password) {
//		SQLiteDatabase localSQLiteDatabase = this.dbHelper
//				.getReadableDatabase();
//		String[] arrayOfString = new String[2];
//		arrayOfString[0] = userId;
//		arrayOfString[1] = Crypto.encrypt(password);
//		Cursor localCursor = localSQLiteDatabase.rawQuery(
//				"select userName from UserInfo where userId=? and password=?",
//				arrayOfString);
//
//		if (localCursor.moveToFirst()) {
//			UserInfo localUser = new UserInfo(userId, password);
//			localUser.setUserName(localCursor.getString(localCursor
//					.getColumnIndex("userName")));
//			return localUser;
//		}
//		return null;
//	}

//	/**
//	 * 从数据库取出所有登录过的用户ID
//	 * 
//	 * @return
//	 */
//	public List<String> getAllStuId() {
//		List<String> localArrayList = new ArrayList<String>();
//		Cursor localCursor = this.dbHelper.getReadableDatabase().rawQuery(
//				"select userId from user", null);
//		while (true) {
//			if (!localCursor.moveToNext())
//				return localArrayList;
//			localArrayList.add(localCursor.getString(0));
//		}
//	}
//
//	/**
//	 * 向数据库插入用户数据
//	 * 
//	 * @param userInfo
//	 */
//	public void addUser(UserInfo userInfo) {
//		SQLiteDatabase localSQLiteDatabase = this.dbHelper.getWritableDatabase();
//		ContentValues localContentValues = new ContentValues();
//		localContentValues.put(UserInfo.USERID, userInfo.getUserId());
//		localContentValues.put(UserInfo.USERNAME, userInfo.getUserName());
//		localContentValues.put(UserInfo.PASSWORD,Crypto.encrypt(userInfo.getPassword()));
//		localContentValues.put(UserInfo.SESSIONID, userInfo.getSessionId());
//		localSQLiteDatabase.insert("UserInfo", null, localContentValues);
//	}


}

package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.wit.withelper.bean.UserInfo;
import cn.edu.wit.withelper.util.Crypto;

/**
 * 用户对象数据库操作层
 */
public class UserInfoServices extends BaseService {

//	private static final String TAG = "UserInfoServices";
	
	public UserInfoServices(Context context) {
		super(context);
	}

	/**
	 * 添加用户信息
	 * @param user
	 */
	public void insertUserInfo(UserInfo user) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cursor = db.rawQuery("select * from "+UserInfo.TB_NAME+" where "+UserInfo.USERID+"=?", new String[]{user.getUserId()});
		if (cursor.getCount() <= 0) {
			ContentValues values = new ContentValues();
			values.put(UserInfo.USERID, user.getUserId());
			values.put(UserInfo.USERNAME, user.getUserName());
			values.put(UserInfo.PASSWORD, Crypto.encrypt(user.getPassword()));
			values.put(UserInfo.LIBRARYPASSWORD, user.getUserId());
			values.put(UserInfo.SESSIONID, user.getSessionId());
			values.put(UserInfo.CLASSNAME, user.getClassName());
			values.put(UserInfo.COLLEGE, user.getCollege());
			db.insert(UserInfo.TB_NAME, null, values);
			db.close();
		}else {
			updateUserInfo(user);
		}
	}

	/**
	 * 更新用户信息
	 * @param userId
	 * @param userName
	 */
	public void updateUserInfo(UserInfo user) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(UserInfo.USERNAME, user.getUserName());
		values.put(UserInfo.PASSWORD, Crypto.encrypt(user.getPassword()));
		values.put(UserInfo.LIBRARYPASSWORD, user.getLibraryPassword());
		values.put(UserInfo.SESSIONID, user.getSessionId());
		values.put(UserInfo.CLASSNAME, user.getClassName());
		values.put(UserInfo.COLLEGE, user.getCollege());
		values.put(UserInfo.NICKNAME, user.getNickName());
		values.put(UserInfo.SEX, user.getSex());
		values.put(UserInfo.ICONINDEX, user.getIconIndex());

		db.update(UserInfo.TB_NAME, values, UserInfo.USERID + "=?",new String[] { user.getUserId() });
		db.close();
	}
	
	
	/**
	 * 查询所有的用户信息
	 * @return
	 */
	public List<UserInfo> getAllLoginedUser() {

		SQLiteDatabase db = dbHelper.getReadableDatabase();

		List<UserInfo> users = null;

		//从数据库表中获取所有登录过的用户的 ID 和 password
		Cursor cursor = db.query(UserInfo.TB_NAME, new String[]{UserInfo.USERID, UserInfo.PASSWORD}, null, null, null,null, null);

		if (null != cursor && cursor.getCount() > 0) {

			users = new ArrayList<UserInfo>(cursor.getCount());

			UserInfo userInfo = null;
			while (cursor.moveToNext()) {

				userInfo = new UserInfo();
				userInfo.setUserId(cursor.getString(cursor.getColumnIndex(UserInfo.USERID)));
				userInfo.setPassword(Crypto.decrypt(cursor.getString(cursor.getColumnIndex(UserInfo.PASSWORD))));
				users.add(userInfo);
			}
		}
		cursor.close();
		db.close();
		return users;
	}
	
	
	
//	/**
//	 * 根据用户ID获取用户对象
//	 * 
//	 * @param userId
//	 * @return
//	 */
//	public UserInfo getUserInfoByUserId(String userId) {
//
//		SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//		UserInfo userInfo = null;
//
//		Cursor cursor = db.query(UserInfo.TB_NAME, new String[] { UserInfo.ID,
//				UserInfo.USER_ID, UserInfo.USER_NAME,UserInfo.IS_DEFAULT, 
//				UserInfo.SEX,UserInfo.USER_ICON },
//				UserInfo.USER_ID + "=?", new String[] { userId }, null, null,
//				null);
//
//		if (null != cursor) {
//
//			if (cursor.getCount() > 0) {
//
//				cursor.moveToFirst();
//				userInfo = new UserInfo();
//
//				Long id = cursor.getLong(cursor.getColumnIndex(UserInfo.ID));
//				String uId = cursor.getString(cursor
//						.getColumnIndex(UserInfo.USER_ID));
//				String userName = cursor.getString(cursor
//						.getColumnIndex(UserInfo.USER_NAME));
//				String sex = cursor.getString(cursor
//						.getColumnIndex(UserInfo.SEX));
//				String password = cursor.getString(cursor
//						.getColumnIndex(UserInfo.PASSWORD));
//				String isDefault = cursor.getString(cursor
//						.getColumnIndex(UserInfo.IS_DEFAULT));
//				byte[] byteIcon = cursor.getBlob(cursor
//						.getColumnIndex(UserInfo.USER_ICON));
//
//				userInfo.setId(id);
//				userInfo.setUserId(uId);
//				userInfo.setUserName(userName);
//				userInfo.setPassword(password);
//				userInfo.setIsDefault(isDefault);
//				userInfo.setSex(sex);
//
//				if (null != byteIcon) {
//
//					ByteArrayInputStream is = new ByteArrayInputStream(byteIcon);
//					Drawable userIcon = Drawable.createFromStream(is, "image");
//
//					userInfo.setUserIcon(userIcon);
//				}
//			}
//		}
//		cursor.close();
//		db.close();
//		return userInfo;
//	}
//




//	
//	public void updatePasswordByID(String userID, String oldPassword, String newPassword) {
//		
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		ContentValues values = new ContentValues(1);
//		values.put(UserInfo.PASSWORD, newPassword);
//
//		db.update(UserInfo.TB_NAME, values, UserInfo.USER_ID + "=?" + "and " + UserInfo.PASSWORD + "=?",
//				new String[] { userID , oldPassword});
//		db.close();
//	}

}

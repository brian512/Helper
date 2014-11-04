package cn.edu.wit.withelper.services;

import android.content.Context;
import cn.edu.wit.withelper.db.DBHelper;

public class BaseService {
	
	public Context context;
	public DBHelper dbHelper;

	public BaseService(Context paramContext) {
		this.context = paramContext;
		dbHelper = new DBHelper(paramContext);
	}
}

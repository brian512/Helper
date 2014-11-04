package cn.edu.wit.withelper.activity;

import android.app.ListActivity;

public abstract class BaseActivity extends ListActivity {

	/**
	 * 初始化数据
	 */
	abstract void init();

	/**
	 * 刷新UI
	 */
	public abstract void refresh(Object... obj);
	
	/**
	 * 新建任务
	 */
	abstract void newTask(Object... obj);
}

package cn.edu.wit.withelper.activity;

public interface IAssistantActivity {

	/**
	 * 初始化数据
	 */
	void init();

	/**
	 * 刷新UI
	 */
	void refresh(Object... obj);
}

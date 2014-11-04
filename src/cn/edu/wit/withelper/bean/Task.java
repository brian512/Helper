package cn.edu.wit.withelper.bean;

import java.util.Map;

public class Task {
	
	public static final int ERROR_NETEXCEPTION = 00;
	
	
	
	public static final int USER_LOGIN = 11;
	public static final int LOGIN_ERR = 12;
	public static final int LOGIN_OK = 13;
	
	
	public static final int GET_NEWSLIST = 21;
	public static final int GET_NEWSCONTENT = 22;
	public static final int REFRESH_NEWS = 23;
	public static final int GET_NEWS_OK = 24;
	public static final int GET_NEWS_ERR = 25;
	
	
	public static final int GET_GRADE = 31;
	public static final int REFRESH_GRADE = 31;
	
	public static final int GET_GRADE_OK = 32;
	public static final int GET_GRADE_ERR = 33;
	
	
	public static final int GET_COURSE = 41;
	public static final int REFRESH_COURSE = 41;
	public static final int GET_COURSE_OK = 42;
	public static final int GET_COURSE_ERR = 43;
	
	
	public static final int GET_EXPRESS = 51;
	
	
	public static final int GET_CET = 61;
	
	public static final int FINDPWD = 71;
	
	public static final int GET_BOOKBORROW = 81;
	public static final int GET_BOOKHOT = 82;
	public static final int GET_SEARCHBOOK = 83;
	public static final int GET_BOOK_WRONGPWD = 84;
	

	//任务ID
	private int taskId;

	// 任务参数
	private Map<String, Object> taskParams;

	
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	public int getTaskId() {
		return taskId;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}

	public Task(int taskId, Map<String, Object> taskParams) {
		this.taskId = taskId;
		this.taskParams = taskParams;
	}
}

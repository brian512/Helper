package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.activity.NewsContentActivity;
import cn.edu.wit.withelper.bean.Book;
import cn.edu.wit.withelper.bean.BorrowBook;
import cn.edu.wit.withelper.bean.CET;
import cn.edu.wit.withelper.bean.Express;
import cn.edu.wit.withelper.bean.Grade;
import cn.edu.wit.withelper.bean.News;
import cn.edu.wit.withelper.bean.SearchBook;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.bean.UserInfo;

public class MainService extends Service implements Runnable {

	private static final String TAG = "MainService";

	private static Queue<Task> tasks = new LinkedList<Task>();
	
	private static ArrayList<Activity> appActivities = new ArrayList<Activity>();

	/**
	 * 标志执行任务的线程是否启动
	 */
	public static boolean isRun;
	
	/**
	 * 系统当前的用户
	 */
	public static UserInfo nowUser;

	/**
	 * 标志当前网络是否可用
	 */
	public static boolean isNetAvailable = false;

	@Override
	public void onCreate() {

		//启动线程执行任务
		Thread thread = new Thread(this);
		thread.start();
		isRun = true;
		
		//启动一个新线程获取网络状态
		new Thread(new Runnable() {
			@Override
			public void run() {
				//一直获取网络状态
				isNetAvailable = NetService.getNetWorkState(MainService.this);
			}
		}).start();
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 添加一个Activity对象
	 * @param activity
	 */
	public static void addActivity(Activity activity) {

		if (!appActivities.isEmpty()) {

			for (Activity at : appActivities) {
				if (at.getClass().getName()
						.equals(activity.getClass().getName())) {
					appActivities.remove(at);
					break;
				}
			}
		}
		appActivities.add(activity);
	}

	/**
	 * 根据Activity 的Name 获取Activity对象
	 * @param name
	 * @return
	 */
	private static Activity getActivityByName(String name) {

		if (!appActivities.isEmpty()) {
			for (Activity activity : appActivities) {
				if (null != activity) {
					if (activity.getClass().getName().indexOf(name) > 0) {
						return activity;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 新建任务
	 * @param t
	 */
	public static void newTask(Task t) {
		tasks.add(t);
	}

	/**
	 * 主服务启动后，一直从任务队列中取出任务执行
	 */
	public void run() {

		while (isRun) {
			Task task = null;
			if (!tasks.isEmpty()) {
				task = tasks.poll();// 执行完任务后把改任务从任务队列中移除
				if (null != task) {
					doTask(task);
				}
			}
			
		}
	}

	// 处理任务
	private void doTask(Task t) {
		Message msg = handler.obtainMessage();
		msg.what = t.getTaskId();

		switch (t.getTaskId()) {

		case Task.USER_LOGIN: {

			UserInfo loginUser = (UserInfo) t.getTaskParams().get("loginUser");
			if (null != loginUser) {

				nowUser = LoginService.login(loginUser);

				msg.obj = nowUser;
				Log.i(TAG, "用户登录————————>" + nowUser.getUserName());

			}
			break;
		}
		case Task.FINDPWD :{
			
			UserInfo user = (UserInfo) t.getTaskParams().get("user");
			if (null != user) {
				String password = FindPwdService.getPassword(user);
				System.out.println(password);
				if (null != password) {
					msg.obj = password;
				}
			}
			
			break;
		}
//		case Task.GET_COURSE: {
//			List<Course> courseList = null;
//			try {
//				courseList = new CourseService(this, handler).getCourseList();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			msg.obj = courseList;
//			break;
//		}
		case Task.GET_GRADE: {
			List<Grade> list = null;
			try {
				list = GradeService.getInstance(this).getAllGrage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			msg.obj = list;
			break;
		}
		case Task.GET_NEWSLIST: {
			List<String> newsList = null;
			try {
				newsList = NewsService.getInstance(this).getTitles();
			} catch (Exception e) {
				e.printStackTrace();
			}
			msg.obj = newsList;
			break;
		}
		case Task.GET_NEWSCONTENT: {

			String title = (String) t.getTaskParams().get("title");

			Log.i(TAG, title);

			News news = null;
			try {
				news = NewsService.getInstance(this).getNewsByTitle(title);
			} catch (Exception e) {
				e.printStackTrace();
			}
			msg.obj = news;
			break;
		}
		case Task.GET_EXPRESS: {

			Express express = (Express) t.getTaskParams().get("express");

			express = ExpressService.getExpressInfo(express);

			msg.obj = express;
			break;
		}
		case Task.GET_CET: {

			CET cet = (CET) t.getTaskParams().get("cet");

			cet = CETService.getCETInfo(cet);

			msg.obj = cet;
			break;
		}
		case Task.GET_BOOKBORROW: {

			String password = (String) t.getTaskParams().get("password");

			List<BorrowBook> bookList = LibraryService.getInstance(this).getBorrowBook(password);

			msg.obj = bookList;
			break;
		}
		case Task.GET_BOOKHOT: {

			String classNum = (String) t.getTaskParams().get("classNum");

			List<Book> hotList = LibraryService.getInstance(this).getHotBook(classNum);

			msg.obj = hotList;
			break;
		}
		case Task.GET_SEARCHBOOK: {

			String keyWord = (String) t.getTaskParams().get("keyWord");

			List<SearchBook> searchBooks = LibraryService.getInstance(this).getSearchBookList(keyWord);

			msg.obj = searchBooks;
			break;
		}
		default:
			break;
		}// end of switch
		handler.sendMessage(msg);
	}

	
	/**
	 * 异步处理消息
	 */
	@SuppressLint("HandlerLeak")
	public static Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			
			IAssistantActivity activity = null;
			
			switch (msg.what) {
			
			case Task.ERROR_NETEXCEPTION: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) appActivities.get(appActivities.size()-1);
					System.out.println(activity.getClass().getName());
				}
				break;
			}
			case Task.USER_LOGIN: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("LoginActivity");
				}
				break;
			}
			case Task.FINDPWD: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("FindPwdActivity");
				}
				break;
			}
			case Task.GET_COURSE: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("CourseActivity");
				}
				break;
			}
			case Task.GET_GRADE: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("GradeActivity");
				}
				break;
			}
			case Task.GET_NEWSLIST: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("NewsActivity");
				}
				break;
			}
			case Task.GET_NEWSCONTENT: {
				if (null != msg.obj) {

					activity = (NewsContentActivity) getActivityByName("NewsContentActivity");

					News news = (News) msg.obj;
					activity.refresh(news);
				}
				break;
			}
			case Task.GET_EXPRESS: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("ExpressActivity");
				}
				break;
			}
			case Task.GET_CET: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("CETActivity");
				}
				break;
			}
			case Task.GET_BOOKBORROW: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("MyBorrowActivity");
				}
				break;
			}
			case Task.GET_BOOKHOT: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("HotBookActivity");
				}
				break;
			}
			case Task.GET_BOOK_WRONGPWD: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("MyBorrowActivity");
				}
				break;
			}
			case Task.GET_SEARCHBOOK: {
				if (null != msg.obj) {
					activity = (IAssistantActivity) getActivityByName("SearchBookActivity");
				}
				break;
			}
			default:
				break;
			}//end of switch
			
			activity.refresh(msg.obj);
		};
		
	};

	/**
	 * 退出系统
	 * @param context
	 */
	public static void appExit(Context context) {
		// Finish 所有的Activity
		for (Activity activity : appActivities) {
			if (!activity.isFinishing())
				activity.finish();
		}
	
		// 结束 Service
		Intent service = new Intent("cn.edu.wit.services.MainService");
		context.stopService(service);
	}

	
	
}

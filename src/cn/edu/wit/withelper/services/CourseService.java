package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.wit.withelper.bean.Course;

public class CourseService extends BaseService{

	
//	private static final String TAG = "CourseService";
//	private List<Course> courseList = null;
	
	public CourseService(Context paramContext) {
		super(paramContext);
	}


	
//	/**
//	 * 通过当前用户ID及姓名生成查询链接，并调用getCourseByHtml(html)获取课程表网页源码
//	 * @return 课程表网页源码字符串
//	 * @throws Exception
//	 */
//	public List<Course> getCourseList() throws Exception {
//		
//		courseList = getCourseListFromDB();
//		
//		if (courseList.size() <= 0) {
//			String url_course = InternetHelper.URL_BASE + "tjkbcx.aspx?xh="
//				+ MainService.nowUser.getUserId() + "&xm="
//				+ MainService.nowUser.getUserName() + "&gnmkdm=N121613";
//		
//			courseList = getCourseByHtml(InternetHelper.getCourseHtmlByURL(url_course));
//		
//			if (null != courseList) {
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						saveCourseList(courseList);
//					}
//				}).start();
//			}
//		}
//		
//		
//		return courseList;
//	}

	
	public List<Course> getCourseListFromDB() {
	    ArrayList<Course> courseList = new ArrayList<Course>();
	    SQLiteDatabase database = dbHelper.getReadableDatabase();
	    String str = "select * from " + Course.TABLE_NAME + " where userId=?";
	    String[] params = new String[1];
	    String userId = MainService.nowUser.getUserId();
	    params[0] = userId;
	    Cursor cursor = database.rawQuery(str, params);
	    
	    while (cursor.moveToNext()) {
	    	Course course = new Course(
	    			cursor.getString(cursor.getColumnIndex(Course.COURSE_ID)),
	    			cursor.getString(cursor.getColumnIndex(Course.COURSE_NAME)),
	    			cursor.getString(cursor.getColumnIndex(Course.TEATCH)),
	    			cursor.getString(cursor.getColumnIndex(Course.TIME)),
	    			cursor.getString(cursor.getColumnIndex(Course.STARTWEEK)),
	    			cursor.getString(cursor.getColumnIndex(Course.ENDWEEK)),
	    			cursor.getString(cursor.getColumnIndex(Course.CLASSROOM)),
	    			userId);
	    	courseList.add(course);
	    }
	    
	    database.close();
	    return courseList;
	}
	
	public void saveCourseList(List<Course> courseList) {
		for (Iterator<Course> iterator = courseList.iterator(); iterator.hasNext();) {
			insertCourse((Course) iterator.next());
		}
	}
	
	public long insertCourse(Course course) {
		
		SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put(Course.COURSE_NAME,course.getCourseName());
		localContentValues.put(Course.TEATCH,course.getTeacher());
		localContentValues.put(Course.TIME,course.getTime());
		localContentValues.put(Course.STARTWEEK,course.getStartWeek());
		localContentValues.put(Course.ENDWEEK,course.getEndWeek());
		localContentValues.put(Course.CLASSROOM,course.getClassRoom());
		localContentValues.put("userId", course.getUserId());
		
		long rowId = dataBase.insert(Course.TABLE_NAME, null, localContentValues);
		dataBase.close();
		return rowId;
	}



//	/**
//	 * 通过课程表网页源码字符串，获得课程信息
//	 * @param html 课程表网页源码字符串
//	 * @return
//	 */
//	private static List<Course> getCourseByHtml(String html) {
//		
//		List<Course> courseList = new ArrayList<Course>();
//		
//		Log.i(TAG, html);
//		
//		if ((html == null) || (html.equals("")))
//			return null;
//		
//		String tdReg = "<td align=\"Center\" rowspan=\"[\\d]\">[^d]*</td>";
//		Pattern patternTd = Pattern.compile(tdReg);
//	
//		Matcher matcherTd = patternTd.matcher(html);
//		String courseStr = null;
//		while (matcherTd.find()) {
//			String td = matcherTd.group(0);
//			courseStr = td.substring(td.indexOf("\">")+2,td.lastIndexOf("</td"));
//			
//			Course course = getCourseFromString(courseStr);
//			courseList.add(course);
//		}
//		
//		return courseList;
//	}
//
//
//
//	private static Course getCourseFromString(String courseStr) {
//		
//		String[] strs = courseStr.split("<br>");
//		String courseName = strs[0];
//		String classTime = strs[1];
//		String teacher = strs[2];
//		String classRoom = strs[3];
//		
//		String time = classTime.substring(classTime.indexOf('('));
//		String startWeek = classTime.substring(0, classTime.indexOf('-'));
//		String endWeek = classTime.substring(classTime.indexOf('-')+1, classTime.indexOf('('));
//		
//		Course course = new Course();
//		course.setCourseName(courseName);
//		course.setClassRoom(classRoom);
//		course.setTeacher(teacher);
//		course.setTime(time);
//		course.setStartWeek(startWeek);
//		course.setEndWeek(endWeek);
//		
//		course.setUserId(MainService.nowUser.getUserId());
//		
//		return course;
//	}
}

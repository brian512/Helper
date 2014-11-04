package cn.edu.wit.withelper.db;

import cn.edu.wit.withelper.bean.Book;
import cn.edu.wit.withelper.bean.BorrowBook;
import cn.edu.wit.withelper.bean.Course;
import cn.edu.wit.withelper.bean.Grade;
import cn.edu.wit.withelper.bean.News;
import cn.edu.wit.withelper.bean.OverlayItemUtil;
import cn.edu.wit.withelper.bean.UserInfo;

public class DBInfo {

	// 数据库名称
	public static final String DB_NAME = "wit_helper";

	// 数据库版本
	public static final int VERSION = 1;

	public static class Table_UserInfo {

		public static final String TB_NAME = UserInfo.TB_NAME;
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS  " + TB_NAME
				+ " ( "
				+ UserInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ UserInfo.USERID + " TEXT,"
				+ UserInfo.USERNAME + " TEXT,"
				+ UserInfo.NICKNAME + " TEXT,"
				+ UserInfo.PASSWORD + " TEXT,"
				+ UserInfo.LIBRARYPASSWORD + " TEXT,"
				+ UserInfo.SESSIONID + " TEXT,"
				+ UserInfo.CLASSNAME + " TEXT,"
				+ UserInfo.COLLEGE + " TEXT,"
				+ UserInfo.SEX + " TEXT,"
				+ UserInfo.ICONINDEX + " int )";
		
		public static final String DROP = "DROP TABLE " + TB_NAME;
		
	}
	
	
	public static class Table_BorrowBook {

		public static final String TB_NAME = BorrowBook.TB_NAME;
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS  " + TB_NAME
				+ " ( "
				+ BorrowBook.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ BorrowBook.BOOKNAME  + " TEXT NOT NULL,"
				+ BorrowBook.BORROW_DATE  + " TEXT,"
				+ BorrowBook.RETURN_DATE  + " TEXT,"
				+ BorrowBook.RENEW_COUNT  + " TEXT,"
				+ BorrowBook.AREA  + " TEXT,"
				+ BorrowBook.STU_ID  + " TEXT )";
		
		public static final String DROP = "DROP TABLE " + TB_NAME;
		
	}
	
	public static class Table_OverlayItem {

		public static final String TB_NAME = OverlayItemUtil.TB_NAME;
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS  " + TB_NAME
				+ " ( "
				+ OverlayItemUtil.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ OverlayItemUtil.TITLE  + " TEXT NOT NULL,"
				+ OverlayItemUtil.SNIPPET  + " TEXT,"
				+ OverlayItemUtil.POINT_LAT  + " double,"
				+ OverlayItemUtil.POINT_LON  + " double)";
		
		public static final String DROP = "DROP TABLE " + TB_NAME;
		
	}
	
	
	public static class Table_Book {

		public static final String TB_NAME = Book.TB_NAME;
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS  " + TB_NAME
				+ " ( "
				+ Book.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Book.NAME + " TEXT NOT NULL,"
				+ Book.AUTHOR + " TEXT,"
				+ Book.PRESS + " TEXT,"
				+ Book.BOOKID + " TEXT NOT NULL,"
				+ Book.COLLECTIONS + " TEXT,"
				+ Book.CLASSNUM + " TEXT,"
				+ Book.LEND_COUNT + " TEXT,"
				+ Book.LEND_RATIO + " TEXT )";
		
		public static final String DROP = "DROP TABLE " + TB_NAME;
		
	}
	
	
	public static class Table_Grade {
		public static final String TB_NAME = Grade.TABLE_NAME;
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TB_NAME 
				+ "("
				+ Grade.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ Grade.YEAR + " TEXT NOT NULL,"
				+ Grade.TERM + " TEXT NOT NULL,"
				+ Grade.POINT + " TEXT NOT NULL,"
				+ Grade.GRADE + " TEXT NOT NULL,"
				+ Grade.GENERAL_GRADE + " TEXT NOT NULL,"
				+ Grade.PAPER_GRADE + " TEXT NOT NULL,"
				+ Grade.EXP_GRADE + " TEXT NOT NULL,"
				+ Grade.MAKEUP_GRADE + " TEXT NOT NULL,"
				+ Grade.REBUILT_GRADE + " TEXT NOT NULL,"
				+ Grade.REBUILT_SIGN + " TEXT NOT NULL,"
				+ Grade.STATE + " TEXT NOT NULL,"
				+ Grade.COURSE_NAME + " TEXT NOT NULL,"
				+ Grade.NATURE + " TEXT NOT NULL,"
				+ Grade.ATTRIBUTION + " TEXT NOT NULL,"
				+ Grade.CREDIT + " TEXT NOT NULL,"
				+ Grade.COLLEGE_NAME + " TEXT NOT NULL,"
				+ "stu_id TEXT NOT NULL )";
				
		public static final String DROP = "DROP TABLE " + TB_NAME;
	}

	public static class Table_News {
		
		public static final String TB_NAME = News.TABLE_NAME;
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TB_NAME
				+ "("
				+ News.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ News.TITLE + " TEXT NOT NULL,"
				+ News.SOURCE + " TEXT,"
				+ News.TIME + " TEXT,"
				+ News.BRIEF + " TEXT,"
				+ News.CONTENT + " TEXT,"
				+ News.URL + " TEXT,"
				+ News.IMAGE + " BLOB )";
		public static final String DROP = "DROP TABLE " + TB_NAME;
		
	}
	
	
	public static class Table_Course {
		
		public static final String TB_NAME = Course.TABLE_NAME;
		
		public static final String CREATE = "CREATE TABLE IF NOT EXISTS " + TB_NAME 
				+ "("
				+ Course.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Course.COURSE_ID + " TEXT,"
				+ Course.COURSE_NAME + " TEXT,"
				+ Course.TEATCH + " TEXT,"
				+ Course.TIME + " TEXT,"
				+ Course.STARTWEEK + " TEXT,"
				+ Course.ENDWEEK + " TEXT,"
				+ Course.CLASSROOM + " TEXT,"
				+ "userId TEXT NOT NULL )";
		
		public static final String DROP = "DROP TABLE " + TB_NAME;
	}
	
}

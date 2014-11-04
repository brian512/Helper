package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.util.Log;
import cn.edu.wit.withelper.bean.Book;
import cn.edu.wit.withelper.bean.BorrowBook;
import cn.edu.wit.withelper.bean.SearchBook;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.util.InterfaceUtil;

public class LibraryService extends BaseService {

	private static final String TAG = "LibraryService";
	
	public static LibraryService libraryService = null;
	
	public static LibraryService getInstance(Context paramContext) {
		
		if (null == libraryService) {
			libraryService = new LibraryService(paramContext);
		}
		
		return libraryService;
	}
	
	private LibraryService(Context paramContext) {
		super(paramContext);
	}

	
	public List<SearchBook> getSearchBookList(String keyWord) {
		
		List<SearchBook> bookList = new ArrayList<SearchBook>();
		String URL = "http://www.withelper.com/API/Android/LibraryBookList";
		final Map<String, String> params = new HashMap<String, String>();
		params.put("str", keyWord);
		
		JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);
		
		
		if (jsonResult == null) {
			Log.i(TAG, "result = null");
		} else {
			Log.i(TAG, "result = " + jsonResult.toString());
			bookList = getSearchBooksByJson(jsonResult);
		}
		
		return bookList;
		
	}
	
	@SuppressWarnings("finally")
	private List<SearchBook> getSearchBooksByJson(JSONObject jsonResult) {
		
		List<SearchBook> bookList = null ;
		
		try {
			
			String errorString = jsonResult.getString("error");
			
			if (null != errorString && "null".equals(errorString)) {
				
				String datas = jsonResult.getString("book");
				
				if (null != datas && !"".equals(datas)) {
					
					bookList = new ArrayList<SearchBook>();
					JSONArray jsonArray = new JSONArray(datas); 
					for(int i=0; i<jsonArray.length(); i++){
						
						JSONObject bookJson = jsonArray.getJSONObject(i);
						
						SearchBook book = new SearchBook();
						book.setTitle(bookJson.getString("title"));
						book.setAuthor(bookJson.getString("author"));
						book.setPress(bookJson.getString("press"));
						book.setCallno(bookJson.getString("callno"));
						book.setType(bookJson.getString("type"));
						
						bookList.add(book);
					}
				}else {
					return null;
				}
			}else {
				return null;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			return bookList;
		}
	}

	/**
	 * 获取当前用户借阅图书列表
	 * @param password 当前用户图书馆密码
	 * @return
	 */
	public List<BorrowBook> getBorrowBook(String password) {

		List<BorrowBook> borrowBookList = getBorrowBookListFromDB(MainService.nowUser.getUserId());
		
		if (null == borrowBookList || borrowBookList.size() <= 0) {
			
			String URL = "http://www.withelper.com/API/Android/LibraryBorrow";
			final Map<String, String> params = new HashMap<String, String>();
			params.put("sessionid", MainService.nowUser.getSessionId());
			params.put("password", password);
System.out.println(password);
			JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);
			
			if (jsonResult == null) {
				Log.i(TAG, "result = null");
			} else {
				Log.i(TAG, "result = " + jsonResult.toString());
				
				borrowBookList = getBorrowBooksByJson(jsonResult);
				
				if (borrowBookList.size() > 0) {
					saveBorrowBookList(borrowBookList);
				}
			}
		}
		
		return borrowBookList;
	}

	/**
	 * 从数据库获取当前用户借阅图书列表
	 * @param userID
	 * @return
	 */
	private List<BorrowBook> getBorrowBookListFromDB(String userID){
		
		List<BorrowBook> borrowBookList = new ArrayList<BorrowBook>();
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    Cursor cursor = db.rawQuery("select * from "+ BorrowBook.TB_NAME + " where "+BorrowBook.STU_ID+"=?", new String[]{userID});
	    
	    while (cursor.moveToNext()) {
	    	
	    	BorrowBook borrowBook = new BorrowBook();
	    	borrowBook.setBookName(cursor.getString(cursor.getColumnIndex(BorrowBook.BOOKNAME)));
	    	borrowBook.setBorrow_date(cursor.getString(cursor.getColumnIndex(BorrowBook.BORROW_DATE)));
	    	borrowBook.setReturn_date(cursor.getString(cursor.getColumnIndex(BorrowBook.RETURN_DATE)));
	    	borrowBook.setRenew_count(cursor.getString(cursor.getColumnIndex(BorrowBook.RENEW_COUNT)));
	    	borrowBook.setArea(cursor.getString(cursor.getColumnIndex(BorrowBook.AREA)));
	    	
	    	borrowBookList.add(borrowBook);
	    	
	    	System.out.println(borrowBook);
	    }
	    db.close();
	    return borrowBookList;
		
	}

	/**
	 * 将借阅的图书列表存入数据库
	 * @param borrowList
	 */
	private void saveBorrowBookList(List<BorrowBook> borrowList){
		
		for (BorrowBook borrowBook : borrowList) {
			saveBorrowBook(borrowBook);
		}
		
	}

	/**
	 * 将借阅的图书信息存入数据库
	 * @param borrowBook
	 */
	private void saveBorrowBook(BorrowBook borrowBook){
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//先判断 数据库中是否存在 当前用户 借阅的当前图书
		Cursor cursor = db.rawQuery("select * from "+BorrowBook.TB_NAME+" where "+BorrowBook.STU_ID+"=? and "+BorrowBook.BOOKNAME+ "=?", new String[]{MainService.nowUser.getUserId(),borrowBook.getBookName()});
		if (cursor.getCount() <= 0) {
			
			ContentValues values = new ContentValues();
			values.put(BorrowBook.BOOKNAME, borrowBook.getBookName());
			values.put(BorrowBook.BORROW_DATE, borrowBook.getBorrow_date());
			values.put(BorrowBook.RETURN_DATE, borrowBook.getReturn_date());
			values.put(BorrowBook.RENEW_COUNT, borrowBook.getRenew_count());
			values.put(BorrowBook.AREA, borrowBook.getArea());
			
			values.put(BorrowBook.STU_ID, MainService.nowUser.getUserId());
			
			db.insert(BorrowBook.TB_NAME, null, values);
		}	
		
		db.close();
	}

	
	/**
	 * 获取热门图书列表
	 * @param classNum
	 * @return
	 */
	public List<Book> getHotBook(String classNum) {
	
		//先从数据库中获取
		List<Book> bookList = getBookListFromDB(classNum);
		
//		Log.i(TAG, classNum);
		//若数据库中午数据则从服务器更新
		if (null == bookList || bookList.size() <= 0) {
			
			String URL = "http://www.withelper.com/API/Android/LibraryHot";
			final Map<String, String> params = new HashMap<String, String>();
			params.put("classNum", classNum);
			
			JSONObject jsonResult = InterfaceUtil.getJSONObject(URL, params);
			
			
			if (jsonResult == null) {
				Log.i(TAG, "result = null");
			} else {
				Log.i(TAG, "result = " + jsonResult.toString());
				bookList = getHotBooksByJson(jsonResult, classNum);
				
				if (bookList == null) {
					return null;
				}else if (bookList.size() > 0) {
					saveBookList(bookList);
				}
			}
			
		}
		
		return bookList;
	}

	/**
	 * 从数据库中获取热门图书列表
	 * @param classNum
	 * @return
	 */
	private List<Book> getBookListFromDB(String classNum) {
		
	    List<Book> bookList = new ArrayList<Book>();
	    
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
	    
	    Cursor cursor = db.rawQuery("select * from "+ Book.TB_NAME + " where "+Book.CLASSNUM+"=?", new String[]{classNum});
	    
	    while (cursor.moveToNext()) {
	    	
	    	Book book = new Book();
	    	book.setName(cursor.getString(cursor.getColumnIndex(Book.NAME)));
	    	book.setAuthor(cursor.getString(cursor.getColumnIndex(Book.AUTHOR)));
	    	book.setPress(cursor.getString(cursor.getColumnIndex(Book.PRESS)));
	    	book.setbookID(cursor.getString(cursor.getColumnIndex(Book.BOOKID)));
	    	book.setCollections(cursor.getString(cursor.getColumnIndex(Book.COLLECTIONS)));
	    	book.setLend_count(cursor.getString(cursor.getColumnIndex(Book.LEND_COUNT)));
	    	book.setLend_ratio(cursor.getString(cursor.getColumnIndex(Book.LEND_RATIO)));
	    	book.setClassNum(cursor.getString(cursor.getColumnIndex(Book.CLASSNUM)));
	    	
	    	bookList.add(book);
	    	
//	    	System.out.println(book);
	    }
	    db.close();
	    return bookList;
	}

	/**
	 * 将图书列表存入数据库
	 * @param bookList
	 */
	private void saveBookList(List<Book> bookList){
		
		for (Book book : bookList) {
			saveBook(book);
		}
		
	}
	
	/**
	 * 将图书信息存入数据库
	 * @param book
	 */
	private void saveBook(Book book) {

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//判断数据库中是否存在该图书信息
		Cursor cursor = db.rawQuery("select * from "+Book.TB_NAME+" where "+Book.BOOKID+"=?", new String[]{book.getbookID()});
		if (cursor.getCount() <= 0) {
			ContentValues values = new ContentValues();
			values.put(Book.NAME, book.getName());
			values.put(Book.AUTHOR, book.getAuthor());
			values.put(Book.PRESS, book.getPress());
			values.put(Book.BOOKID, book.getbookID());
			values.put(Book.COLLECTIONS, book.getCollections());
			values.put(Book.LEND_COUNT, book.getLend_count());
			values.put(Book.LEND_RATIO, book.getLend_ratio());
			values.put(Book.CLASSNUM, book.getClassNum());
			
			db.insert(Book.TB_NAME, null, values);
		}	
		
		db.close();
	}
	
	
	/**
	 * 从服务器返回的json中获取图书信息
	 * @param jsonResult
	 * @param classNum 图书的分类
	 * @return
	 */
	@SuppressWarnings("finally")
	private List<Book> getHotBooksByJson(JSONObject jsonResult, String classNum) {

		List<Book> bookList = null ;
		
		try {
			
			String errorString = jsonResult.getString("error");
			
			if (null != errorString && "null".equals(errorString)) {
				
				String datas = jsonResult.getString("hot");
				
				if (null != datas && !"".equals(datas)) {
					
					bookList = new ArrayList<Book>();
					JSONArray jsonArray = new JSONArray(datas); 
					for(int i=0; i<jsonArray.length(); i++){
						
						JSONObject bookJson = jsonArray.getJSONObject(i);
						
						Book book = new Book();
						book.setName(bookJson.getString("name"));
						book.setAuthor(bookJson.getString("author"));
						book.setPress(bookJson.getString("press"));
//						book.setbookID(bookJson.getString("ID"));
						book.setCollections(bookJson.getString("collections"));
						book.setLend_count(bookJson.getString("lend_count"));
						book.setLend_ratio(bookJson.getString("lend_ratio"));
						book.setClassNum(classNum);
						
						bookList.add(book);
					}
				}else {
					return null;
				}
			}else {
				return null;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			return bookList;
		}
	}

	/**
	 * 从服务器返回的json中获取借阅图书的信息
	 * @param jsonResult
	 * @return
	 */
	@SuppressWarnings("finally")
	private List<BorrowBook> getBorrowBooksByJson(JSONObject jsonResult) {

		List<BorrowBook> borrowList = null ;
		
		try {
			String errorString = jsonResult.getString("error");

			//密码错误
			if ("Wrong password!".equals(errorString)) {
				
				Message msg = new Message();
				msg.what = Task.GET_BOOK_WRONGPWD;
				msg.obj = errorString;
				MainService.handler.sendMessage(msg);	//通知主服务用户图书馆密码错误
				
				return null ;
				
			}else if (null != errorString && "null".equals(errorString)) {
				
				String datas = jsonResult.getString("borrow");
				
				if (null != datas && !"".equals(datas)) {
					
					borrowList = new ArrayList<BorrowBook>();
					JSONArray jsonArray = new JSONArray(datas); 
					for(int i=0; i<jsonArray.length(); i++){
						
						JSONObject borrowJson = jsonArray.getJSONObject(i);
						
						BorrowBook book = new BorrowBook();
						book.setBookName(borrowJson.getString("title"));
						book.setBorrow_date(borrowJson.getString("borrow_date"));
						book.setReturn_date(borrowJson.getString("return_date"));
						book.setRenew_count(borrowJson.getString("renew_count"));
						book.setArea(borrowJson.getString("area"));
						
						borrowList.add(book);
					}
				}else {
					return null;
				}
			}else {
				return null;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			return borrowList;
		}
		
	}
	
	
}

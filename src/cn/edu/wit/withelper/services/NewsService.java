package cn.edu.wit.withelper.services;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import cn.edu.wit.withelper.bean.News;
import cn.edu.wit.withelper.util.InternetHelper;

public class NewsService extends BaseService {

//	private static final String TAG = "NewsService";
	
	private static final String NEWSLIST_URL = "http://jwc.wit.edu.cn/content.asp?m=10";
	
	private static List<News> newsList = null;
	
	public static NewsService newService = null;
	
	public static NewsService getInstance(Context paramContext) {
		
		if (null == newService) {
			newService = new NewsService(paramContext);
		}
		
		return newService;
	}
	
	private NewsService(Context paramContext) {
		super(paramContext);
	}

	
	/**
	 * 获取新闻列表
	 * @return
	 * @throws Exception
	 */
	public List<String> getTitles() throws Exception{
		
		String html = InternetHelper.getNewsHtmlByURL(NEWSLIST_URL);
		
		if (null != html) {
			List<String> titleList = new ArrayList<String>();
			newsList = getNewsByHtml(html);
			
			for (Iterator<News> iterator = newsList.iterator(); iterator.hasNext();) {
				News news = (News) iterator.next();
				titleList.add(news.getTitle());
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					saveNewsList(newsList);
				}
			}).start();
			
			return titleList;
		}
		return null;
	}
	
	
	
	/**
	 * 通过点击的新闻标题得到News实体
	 * @param title
	 * @return
	 * @throws Exception 
	 */
	public News getNewsByTitle(String title) throws Exception {
		
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.query(News.TABLE_NAME, new String[]{News.ID,News.URL,News.SOURCE,News.TIME,News.CONTENT}, News.TITLE+"=?", new String[]{title}, null, null, null);
		
		News news = new News();
		if(cursor.moveToFirst()){
			news.setId(cursor.getLong(cursor.getColumnIndex(News.ID)));
			news.setTime(cursor.getString(cursor.getColumnIndex(News.TIME)));
			news.setSource(cursor.getString(cursor.getColumnIndex(News.SOURCE)));
			news.setContent(cursor.getString(cursor.getColumnIndex(News.CONTENT)));
			news.setUrl(cursor.getString(cursor.getColumnIndex(News.URL)));
			news.setTitle(title);
		}
		
		//如果数据库中的news数据没有新闻主体部分则联网获取
		if (null == news.getContent()) {
			String url = news.getUrl();
		
			String html = InternetHelper.getNewsHtmlByURL(url);
			
			html = html.substring(html.indexOf(title)+title.length());//去头
			String regexstr = "<(?!p|/p).*?>";
			html = html.replaceAll(regexstr, "");//去HTML标签
			html = html.replace("&nbsp;", " ");//去空格
			
			//获取新闻内容
			String content = html.substring(html.indexOf("教务处")+3, html.lastIndexOf("机构设置")).trim();//去尾
			//获取发布时间
			String time = html.substring(html.indexOf("发布时间：")+5, html.indexOf("点击次数"));
			
			news.setTime(time);
			news.setContent(content);
			
			//更新数据库中的数据，主要是加入content和time
			updateNews(news);
		}
		database.close();
		return news;
	}

	
	/**
	 * 通过获取的含有新闻标题的网页源码获取新闻列表
	 * @param html
	 * @return
	 */
	private static List<News> getNewsByHtml(String html) {

		List<News> newsList = new ArrayList<News>();
		String reg = "(<a href=\"Content.asp\\?c=14&a=[^>]*&todo=show\"  target=\"_blank\">).*?(</a>)";

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(html);

		while (matcher.find()) {
			String titleStr = matcher.group(0);
			String title = titleStr.substring(titleStr.indexOf('】')+1, titleStr.indexOf("</"));
			String source = titleStr.substring(titleStr.indexOf('【')+1, titleStr.indexOf('】'));
			String url = "http://jwc.wit.edu.cn/" + titleStr.substring(titleStr.indexOf("href=\"")+"href=\"".length(), titleStr.indexOf("todo=show")+"todo=show".length());
			
			News news = new News();//封装为news对象
			news.setTitle(title);
			news.setSource(source);
			news.setUrl(url);
			
			newsList.add(news);
		}
		return newsList;
	}

	/**
	 * 将获取的新闻列表储存到数据库
	 * @param newsList
	 */
	public void saveNewsList(List<News> newsList) {
		for (Iterator<News> iterator = newsList.iterator(); iterator.hasNext();) {
			News news = (News) iterator.next();
			insertNews(news);//分条插入
		}
	}
	
	
	/**
	 * 插入一条新闻数据到数据库
	 * @param news
	 * @return
	 */
	public long insertNews(News news) {
		
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put(News.TITLE, news.getTitle());
		localContentValues.put(News.BRIEF, news.getBrief());
		localContentValues.put(News.TIME, news.getTime());
		localContentValues.put(News.SOURCE, news.getSource());
		localContentValues.put(News.CONTENT, news.getContent());
		localContentValues.put(News.URL, news.getUrl());
		localContentValues.put(News.IMAGE, Bitmap2Bytes(news.getImage()));
		
		long rowId = database.insert(News.TABLE_NAME, null, localContentValues);
		
		database.close();
		return rowId;
	}
	
	/**
	 * 更新news对象，主要是包括content和time
	 * @param news
	 * @return
	 */
	public int updateNews(News news){
		
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(News.TIME, news.getTime());
		values.put(News.SOURCE, news.getSource());
		values.put(News.CONTENT, news.getContent());
		values.put(News.URL, news.getUrl());
		values.put(News.IMAGE, Bitmap2Bytes(news.getImage()));
		
		int count = database.update(News.TABLE_NAME, values, News.TITLE+"=?", new String[]{news.getTitle()});
		
		database.close();
		return count;
	}
	
	/**
	 * 将Drawable对象装换为 byte[]数据，方便存储
	 * @param image
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Drawable image) {
		
		if (null != image) {
			Bitmap bitMap = ((BitmapDrawable) image).getBitmap();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitMap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();
		}
		return null;
	}

}

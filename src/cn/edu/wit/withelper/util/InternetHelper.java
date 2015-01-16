package cn.edu.wit.withelper.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class InternetHelper {

	private static final String TAG = "InternetHelper";
	
	
	public static final String URL_BASE = "http://61.183.207.40/zjdxgc/(kgd5dczwtsnv50yznsqeuh55)/";
	public static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)";
	public static final String HOST = "http://61.183.207.40";
	
	private static CookieStore cookie = null;

	/**
	 * 单例模式，生成HttpClient对象，并进行请求参数封装
	 * @return HttpClient对象
	 */
	public static DefaultHttpClient getClient(){
		DefaultHttpClient client = null;
		if (null == client) {
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter("http.protocol.allow-circular-redirects", Boolean.valueOf(true));
			httpParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
			HttpClientParams.setRedirecting(httpParams, true);
			
			//设置编码
			HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(httpParams, true);
			HttpProtocolParams.setUserAgent(httpParams, USER_AGENT);
			
			HttpConnectionParams.setTcpNoDelay(httpParams, true);
			//关闭旧连接检查，提升速度
			HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);
//			//从连接池中取连接的超时时间
//			ConnManagerParams.setTimeout(httpParams, 1000);
//			//连接超时
//			HttpConnectionParams.setConnectionTimeout(httpParams, 2000);
//			//请求超时
//			HttpConnectionParams.setSoTimeout(httpParams, 4000);
			
			//设置httpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			
			//使用线程安全的连接管理
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(httpParams, schReg);
			
			client = new DefaultHttpClient(conMgr, httpParams);
		}
		
		if (null != cookie) {
			client.setCookieStore(cookie);
			Log.i(TAG, cookie.toString());
		}
		
		return client;
	}
	

	/**
	 * 通过URL获取含有课表的网页<table>源码
	 * @param url_course
	 * @return
	 * @throws Exception
	 */
	public static String getCourseHtmlByURL(String url_course) throws Exception {

		DefaultHttpClient client = InternetHelper.getClient();
		
		HttpGet localHttpGet = new HttpGet(url_course);
		String referer = url_course.substring(0, url_course.indexOf("&xm="));

		localHttpGet.setHeader("Referer", referer);
		localHttpGet.setHeader("User-Agent", USER_AGENT);
		
		HttpResponse httpResponse = client.execute(localHttpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("statusCode————————————————>" + statusCode);
		
		String html = null;
		
		if (statusCode == 400) {
			HttpEntity httpEntity = httpResponse.getEntity();
			System.out.println("出错了，400，下面是得到的html代码："+ EntityUtils.toString(httpEntity, "gb2312"));
			return null;
		}else if (statusCode == 302) {	//网页跳转
			
			//从头信息中获取跳转地址
			Header[] arrayOfHeader = httpResponse.getHeaders("Location");
			String location = HOST + arrayOfHeader[0].getValue();
			System.out.println("location——————————————————————————>" + location);
			
			HttpGet httpGet = new HttpGet(location);
			httpGet.setHeader("Referer", location);
			html = EntityUtils.toString(client.execute(httpGet).getEntity(), "gb2312");
			httpGet.abort();
			
		} else if (statusCode == 200){
			html = EntityUtils.toString(httpResponse.getEntity(),"gb2312");
		}
		String courseTable = html.substring(html.indexOf("<table"),html.indexOf("</table>") + "</table>".length());

		return courseTable;
	}

	/**
	 * 通过URL获取含有新闻的网页源码
	 * @param url_news
	 * @return
	 * @throws Exception
	 */
	public static String getNewsHtmlByURL(String url_news) throws Exception {
		
		DefaultHttpClient client = InternetHelper.getClient();
		
		HttpGet localHttpGet = new HttpGet(url_news);
		String referer = url_news;

		localHttpGet.setHeader("Referer", referer);
		localHttpGet.setHeader("User-Agent", USER_AGENT);

		HttpResponse httpResponse = client.execute(localHttpGet);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("statusCode————————————————>" + statusCode);
		
		String html = null;
		
		if (statusCode == 400) {
			HttpEntity localHttpEntity = httpResponse.getEntity();
			System.out.println("出错了，400，下面是得到的html代码："+ EntityUtils.toString(localHttpEntity, "gb2312"));
			return null;
		}else if (statusCode == 302) {	//网页跳转
			
			//从头信息中获取跳转地址
			Header[] arrayOfHeader = httpResponse.getHeaders("Location");
			String location = HOST + arrayOfHeader[0].getValue();
			
			HttpGet httpGet = new HttpGet(location);
			httpGet.setHeader("Referer", location);
			html = EntityUtils.toString(client.execute(httpGet).getEntity(), "gb2312");
			httpGet.abort();
		} else if (statusCode == 200){
			html = EntityUtils.toString(httpResponse.getEntity(),"gb2312");
		}
		return html;
	}

}

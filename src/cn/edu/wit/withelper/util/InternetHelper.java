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
	
	
//	/**
//	 * 通过URL获取含有成绩的网页<table>源码
//	 * @param url_grade
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getGradeHtmlByURL(String url_grade) throws Exception {
//
//		DefaultHttpClient client = InternetHelper.getClient();
//		
//		HttpGet localHttpGet = new HttpGet(url_grade);
//		String referer_get = url_grade.substring(0, url_grade.indexOf("&xm="));
//
//		localHttpGet.setHeader("Referer", referer_get);
//		localHttpGet.setHeader("User-Agent", USER_AGENT);
//		
//		String html = EntityUtils.toString(client.execute(localHttpGet).getEntity(), "gb2312");
//		
//		String temp = html.substring(html.indexOf("name=\"__VIEWSTATE\" value=\"") + "name=\"__VIEWSTATE\" value=\"".length());
//		String __VIEWSTATE = temp.substring(0, temp.indexOf("\""));
//		
//		String referer_post = url_grade;
//		HttpPost localHttpPost = new HttpPost(url_grade);
//		localHttpPost.setHeader("Referer", referer_post);
//
//		List<BasicNameValuePair> localArrayList = new ArrayList<BasicNameValuePair>();
//		localArrayList.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
//		localArrayList.add(new BasicNameValuePair("Button1", "按学期查询"));
//		localArrayList.add(new BasicNameValuePair("ddlXN", ""));
//		localArrayList.add(new BasicNameValuePair("ddlXQ", ""));
//		localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList,"gb2312"));
//
//		HttpResponse httpResponse = client.execute(localHttpPost);
//		int statusCode = httpResponse.getStatusLine().getStatusCode();
//
//		html = null;
//		
//		if (statusCode == 400) {
//			HttpEntity httpEntity = httpResponse.getEntity();
//			System.out.println("出错了，400，下面是得到的html代码："+ EntityUtils.toString(httpEntity, "gb2312"));
//			return null;
//		}else if (statusCode == 302) {	//网页跳转
//			
//			//从头信息中获取跳转地址
//			Header[] arrayOfHeader = httpResponse.getHeaders("Location");
//			String location = HOST + arrayOfHeader[0].getValue();
//			
//			HttpGet httpGet = new HttpGet(location);
//			httpGet.setHeader("Referer", location);
//			html = EntityUtils.toString(client.execute(httpGet).getEntity(), "gb2312");
//			
//		} else if (statusCode == 200){
//			html = EntityUtils.toString(httpResponse.getEntity(),"gb2312");
//		}
//		String gradeHtml = html.substring(html.indexOf("<table"), html.indexOf("</table>") + "</table>".length());
//		
////		client.getConnectionManager().shutdown();
//		return gradeHtml;
//	}

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

//		client.getConnectionManager().shutdown();
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


//	/**
//	 * 通过已封装用户ID和密码的loginUser对象进行登录验证
//	 * @param loginUser
//	 * @return 返回用户姓名
//	 * @throws Exception
//	 */
//	public static String login(UserInfo loginUser) throws Exception {
//
//		//登录所需参数
//		final String URL_LOGIN = URL_BASE + "default2.aspx";
//		final String __VIEWSTATE = "dDwtMjEzNzcwMzMxNTs7PqAOXZm+x1OKbkY14vnLGDKojgcI";
//		final String LOGIN_TYPE = "学生";
//		
//		
//		String userId = loginUser.getUserId();
//		String password = loginUser.getPassword();
//
//		
//		DefaultHttpClient client = InternetHelper.getClient();
//		
//		HttpPost httpPost = new HttpPost(URL_LOGIN);
//		String referer = URL_LOGIN;
//		httpPost.addHeader("Referer", referer);
//		
//		//需要提交到服务器的数据
//		List<BasicNameValuePair> localArrayList = new ArrayList<BasicNameValuePair>();
//		localArrayList.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
//		localArrayList.add(new BasicNameValuePair("Button1", ""));
//		localArrayList.add(new BasicNameValuePair("RadioButtonList1",LOGIN_TYPE));
//		localArrayList.add(new BasicNameValuePair("TextBox1", userId));
//		localArrayList.add(new BasicNameValuePair("TextBox2", password));
//		httpPost.setEntity(new UrlEncodedFormEntity(localArrayList,"gb2312"));
//		
//		HttpResponse httpResponse = null;
//		// 登录请求
//		httpResponse = client.execute(httpPost);
//		
//		int statusCode = httpResponse.getStatusLine().getStatusCode();
//
//		System.out.println("statusCode————————————————>" + statusCode);
//		
//		String html = null;
//		String userName = null;//用户姓名
//		if (statusCode == 400) {
//			HttpEntity localHttpEntity = httpResponse.getEntity();
//			System.out.println("出错了，400，下面是得到的html代码："+ EntityUtils.toString(localHttpEntity, "gb2312"));
//			return null;
//			
//		} else if (statusCode == 200) {
//
//			html = EntityUtils.toString(httpResponse.getEntity(),"gb2312");
//			
//			if (html.contains("防刷")) {
//				
//				System.out.println("五秒防刷");
//
//				//重新发送请求
//				Map<String, Object> taskParams = new HashMap<String, Object>();
//				taskParams.put("loginUser", loginUser);
//				Task task = new Task(Task.USER_LOGIN, taskParams);
//				MainService.newTask(task);
//			}
//			
//			System.out.println(html);
//			
//		} else if (statusCode == 302) {	//网页跳转
//			
//			//从头信息中获取跳转地址
//			Header[] arrayOfHeader = httpResponse.getHeaders("Location");
//			String location = HOST + arrayOfHeader[0].getValue();
//			System.out.println("location——————————————————————————>" + location);
//			
//			HttpGet httpGet = new HttpGet(location);
//			httpGet.setHeader("Referer", location);
//			html = EntityUtils.toString(client.execute(httpGet).getEntity(), "gb2312");
//			cookie = client.getCookieStore();
//			System.out.println(cookie.toString());
//			httpGet.abort();//获取数据后，断开连接
//			
//		}
//		
//		if (html.contains("密码错误") || html.contains("用户名不存在") || html.contains("密码不能为空") || html.contains("用户名不能为空")) {
//			return ConstantValues.ERROR_USER;
//		}else {
//			String temp = html.substring(4 + html.indexOf("&xm="));
//			userName = temp.substring(0, temp.indexOf("&"));
//			System.out.println("姓名：" + userName);
//		}
//
//		httpPost.abort();
//		
//		return userName;
//	}

}

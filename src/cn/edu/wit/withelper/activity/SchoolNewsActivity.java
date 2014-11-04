package cn.edu.wit.withelper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import cn.edu.wit.withelper.util.MyWebViewClient;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class SchoolNewsActivity extends Activity {

private WebView webView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		webView = new WebView(this);
		
		//设置WebView属性，能够执行Javascript脚本
		WebSettings settings = webView.getSettings();
		//支持js脚步
		settings.setJavaScriptEnabled(true);
		
		settings.setAppCacheEnabled(true);// 设置启动缓存
		
		settings.setSavePassword(true);
		//支持缩放
		settings.setSupportZoom(true);
		
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//支持通过JS打开新窗口
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		String url = "http://www.withelper.cn/";
		
		webView.setWebViewClient(new MyWebViewClient());
		webView.loadUrl(url);
		
		setContentView(webView); 
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
				return true;
			}else {
				this.finish();
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

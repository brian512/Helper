package cn.edu.wit.withelper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import cn.edu.wit.withelper.util.MyWebViewClient;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class TieBaActivity extends Activity {

	private WebView webView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		webView = new WebView(this);
		
		//设置WebView属性，能够执行Javascript脚本
		WebSettings settings = webView.getSettings();
		//支持js脚步
		settings.setJavaScriptEnabled(true);
		
		settings.setAppCacheEnabled(true);// 设置启动缓存
		
		settings.setSavePassword(true);
		//支持缩放
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);

		settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		settings.setDefaultFontSize(settings.getDefaultFontSize()+4);
		
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//支持通过JS打开新窗口
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		String url = "http://tieba.baidu.com/f?kw=%BE%B2%CB%BC%BA%FE&fr=index";
		
		webView.setWebViewClient(new MyWebViewClient());
		
//		View zoom = webView.getZoomControls();
		
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


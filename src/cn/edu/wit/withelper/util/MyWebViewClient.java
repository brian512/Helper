package cn.edu.wit.withelper.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

	
	 //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。 
    @Override 
    public boolean shouldOverrideUrlLoading(WebView view, String url) { 

        view.loadUrl(url); 
        //如果不需要其他对点击链接事件的处理返回true，否则返回false 
        return true; 
    } 
    
    @Override
    public void onPageFinished(WebView view, String url) {
    	super.onPageFinished(view, url);
    }
    
    @Override
    public void onReceivedError(WebView view, int errorCode,
    		String description, String failingUrl) {
    	super.onReceivedError(view, errorCode, description, failingUrl);
    	
    	//这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
    	
    }
	
}

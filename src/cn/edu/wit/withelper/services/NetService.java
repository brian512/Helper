package cn.edu.wit.withelper.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetService {
	
	/**
	 * 判断网络是否可用
	 * @param context
	 */
	public static boolean getNetWorkState(Context context) {
		
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

//判断wifi是否可用
//	public boolean isWifiConnected(Context context) {
//		if (context != null) {
//			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
//					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//			if (mWiFiNetworkInfo != null) {
//				return mWiFiNetworkInfo.isAvailable();
//			}
//		}
//		return false;
//	}
//
//	public boolean isMobileConnected(Context context) {
//		if (context != null) {
//			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//					.getSystemService(Context.CONNECTIVITY_SERVICE);
//			NetworkInfo mMobileNetworkInfo = mConnectivityManager
//					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//			if (mMobileNetworkInfo != null) {
//				return mMobileNetworkInfo.isAvailable();
//			}
//		}
//		return false;
//	}
	
	

}

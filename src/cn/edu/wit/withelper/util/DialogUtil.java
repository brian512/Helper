package cn.edu.wit.withelper.util;

import android.app.AlertDialog;
import android.content.Context;

public class DialogUtil {
	public static void showDialog(Context paramContext, String message) {
		new AlertDialog.Builder(paramContext).setTitle("消息框")
				.setMessage(message).setCancelable(false)
				.setPositiveButton("确定", null).create().show();
	}
}

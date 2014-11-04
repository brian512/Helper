package cn.edu.wit.withelper.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class NewsActivity extends Activity implements IAssistantActivity{
	
	public static final String TAG = "NewsActivity";
	
	private List<String> titleList ;

	private ListView newsList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.listview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.SCHOOLNEWS);
		init();
		newTask();
	}

	@Override
	public void init() {
		
		MainService.addActivity(this);
		
		newsList = (ListView) findViewById(R.id.listview);
		newsList.setBackgroundColor(getResources().getColor(R.color.lightgrey));
		newsList.setCacheColorHint(Color.TRANSPARENT);//列表项的背景设置为透明
		//设置主界面
		newsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "ItemClicked");
				//获得点击的新闻标题
				String title = titleList.get(position);
				
				Intent intent = new Intent(NewsActivity.this, NewsContentActivity.class);
				intent.putExtra("title", title);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.rightIn(),SwitchActivityAnim.rightOut());
			}
		});
		
		TextView btnBack = (TextView) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
			}
		});
		TextView tvBackHome = (TextView) findViewById(R.id.back_home);
		tvBackHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {
		
		titleList = (List<String>) obj[0];
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.news_item, titleList);
		newsList.setAdapter(adapter);
	}
	
	private void newTask(){

		Task task = new Task(Task.GET_NEWSLIST, null);
		
		MainService.newTask(task);
		
	}
//	
//	private void showDialg() {
//
//		if (null == progressDialog  ) {
//			progressDialog = new ProgressDialog(this);
//		}
//		progressDialog.setMessage("正在获取信息...");
//		progressDialog.show();
//	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}

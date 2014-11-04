package cn.edu.wit.withelper.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.bean.News;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class NewsContentActivity extends Activity implements IAssistantActivity{

	private TextView tvTitle = null ;
	private TextView tvContent = null  ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		
		init();
	}
	
	@Override
	public void init() {
		
		MainService.addActivity(this);
		
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		
		newTask(title);
		
		tvContent = (TextView) findViewById(R.id.content);
		tvTitle = (TextView) findViewById(R.id.title);
		tvTitle.setText(title);
	}

	@Override
	public void refresh(Object... obj) {
		
		News news = (News) obj[0];
		
		tvContent.setText(news.getContent());

	}
	
	private void newTask(String title){
		
		Map<String, Object> taskParams = new HashMap<String, Object>();
		
		taskParams.put("title", title);

		Task task = new Task(Task.GET_NEWSCONTENT, taskParams);
		MainService.newTask(task);
		
	}
	
//	private void showDialg() {
//
//		if (null == progressDialog  ) {
//			progressDialog = new ProgressDialog(this);
//		}
//		progressDialog.setMessage("正在获取信息...");
//		progressDialog.show();
//	}
//	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.leftIn(), SwitchActivityAnim.leftOut());
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

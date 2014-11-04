package cn.edu.wit.withelper.activity.infoquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.Grade;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class GradeActivity extends Activity implements IAssistantActivity{

	private static final String TAG = "GradeActivity";
	
	private ListView listView = null ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.listview);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.GRADE);
		init();
		newTask();
	}

	@Override
	public void init() {
		
		Log.i(TAG, "init gradeactivity");
		MainService.addActivity(this);
		
		listView = (ListView) findViewById(R.id.listview);
		listView.setCacheColorHint(Color.TRANSPARENT);//列表项的背景设置为透明
		//设置主界面
		listView.setBackgroundColor(Color.LTGRAY);
	
		TextView btnBack = (TextView) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), InfoQueryActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.leftIn(), SwitchActivityAnim.leftOut());
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
		
		List<Grade> gradeList = (List<Grade>) obj[0];
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> localHashMap = new HashMap<String, Object>();
		
		localHashMap.put(Grade.YEAR, "学年");
		localHashMap.put(Grade.TERM, "学期");
		localHashMap.put(Grade.COURSE_NAME, "课程名");
		localHashMap.put(Grade.POINT, "学分");
		localHashMap.put(Grade.GRADE, "成绩");
		listItems.add(0,localHashMap);
		
		
		for (Grade localGrade : gradeList) {
			
			localHashMap = new HashMap<String, Object>();
			localHashMap.put(Grade.YEAR, localGrade.getYear());
			localHashMap.put(Grade.TERM, localGrade.getTerm());
			localHashMap.put(Grade.COURSE_NAME, localGrade.getName());
			localHashMap.put(Grade.POINT, localGrade.getPoint());
			localHashMap.put(Grade.GRADE, localGrade.getGrade());
//			listItems.add(localHashMap);
			listItems.add(1, localHashMap);
		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.info_grade_item,
				new String[] { Grade.YEAR, Grade.TERM,Grade.COURSE_NAME, Grade.POINT, Grade.GRADE }, 
				new int[] {R.id.year, R.id.term, R.id.name, R.id.point,R.id.grade });
		listView.setAdapter(simpleAdapter);
		
	}
	
	
	private void newTask(){
		
		Map<String, Object> taskParams = new HashMap<String, Object>();
		
		taskParams.put("", "");
		
		Task task = new Task(Task.GET_GRADE, taskParams);
		
		MainService.newTask(task);
		
	}
//	
//	private void showDialg() {
//
//		if (null == progressDialog ) {
//			progressDialog = new ProgressDialog(this);
//		}
//		progressDialog.setMessage("正在获取成绩信息...");
//		progressDialog.show();
//	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			Intent intent = new Intent(getApplicationContext(), InfoQueryActivity.class);
			startActivity(intent);
			overridePendingTransition(SwitchActivityAnim.leftIn(), SwitchActivityAnim.leftOut());
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

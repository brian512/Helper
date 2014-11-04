package cn.edu.wit.withelper.activity.infoquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.CET;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SharedPreferencesUtil;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class CETActivity extends Activity implements IAssistantActivity{

	private EditText etCetID = null;
	private EditText etName = null;
	private TextView tvResult = null;
	private Button btSubmit = null;
	
	private static final String spName = "cet";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.info_cet);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.CET);
		
		init();
	}

	@Override
	public void init() {
		MainService.addActivity(this);
		
		
		etCetID = (EditText) findViewById(R.id.cetID);
//		etCetID.setBackgroundColor(Color.TRANSPARENT);
		etName = (EditText) findViewById(R.id.name);
		btSubmit = (Button) findViewById(R.id.submit);
		tvResult = (TextView) findViewById(R.id.tvResult);
		
		List<String> list = new ArrayList<String>();
		list.add(CET.CETID);
		list.add(CET.NAME);
		Map<String, String> map = SharedPreferencesUtil.getSharedPreferences(CETActivity.this, spName, list);
		etCetID.setText(map.get(CET.CETID));
		etName.setText(map.get(CET.NAME));
		
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
		
		btSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String cetID = etCetID.getText().toString();
				String name = etName.getText().toString();
				CET cet = new CET();
				cet.setAdmission(cetID);
				cet.setName(name);

				Map<String, String> map = new HashMap<String, String>();
				map.put(CET.CETID, cetID);
				map.put(CET.NAME, name);
				SharedPreferencesUtil.editSharedPreferences(CETActivity.this, spName, map);
				
				newTask(cet);
			}
		});
		
		
	}

	private void newTask(CET cet) {

		if(!MainService.isNetAvailable){
			
			Toast.makeText(CETActivity.this, "网络不可用！", Toast.LENGTH_LONG).show();
			
		}else {
			
			Map<String, Object> taskParams = new HashMap<String, Object>();
			
			taskParams.put("cet", cet);
	
			Task task = new Task(Task.GET_CET, taskParams);
			
			//将任务加入主服务线程
			MainService.newTask(task);
		}
	}
	
	@Override
	public void refresh(Object... obj) {
		
		CET cet = (CET) obj[0];
		
		String totalGrade = cet.getTotalGrade();
		if (null != totalGrade) {
			String strResult = "";
			strResult += "学校："+cet.getSchool()
					+"\n类型："+cet.getType()
					+"\n时间："+cet.getTime()
					+"\n总分："+cet.getTotalGrade()
					+"\n听力："+cet.getListenGrade()
					+"\n阅读："+cet.getReadGrade()
					+"\n写作："+cet.getWriteGrade();
			tvResult.setText(strResult);
		}else {
			tvResult.setText("查询失败");
		}
	}
	
	
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

package cn.edu.wit.withelper.activity;

import java.util.HashMap;
import java.util.Map;

import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.bean.UserInfo;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.SwitchActivityAnim;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindPwdActivity extends Activity implements IAssistantActivity{

	private Button btn_submit = null;
	private EditText et_userName = null;
	private EditText et_userID = null;
	private EditText et_userIDCard = null;
	private EditText et_password = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpassword);
		
		init();
	}
	
	
	
	
	@Override
	public void init() {

//		if (!MainService.isRun) {
//			Intent service = new Intent(this, MainService.class);
//			startService(service);
//		}
		
		
		MainService.addActivity(this);
		
		
		et_userID = (EditText) findViewById(R.id.et_userid);
		et_userName = (EditText) findViewById(R.id.et_username);
		et_userIDCard = (EditText) findViewById(R.id.et_idcard);
		et_password = (EditText) findViewById(R.id.password);
		
		et_userID.setText("1005100109");
		et_userName.setText("华满满");
		et_userIDCard.setText("421381199205128134");
		
		
		btn_submit = (Button) findViewById(R.id.btn_findpwd);
		btn_submit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				
				System.out.println("findpwd clicked");
				
				String userID = et_userID.getText().toString();
				String userName = et_userName.getText().toString();
				String idCard = et_userIDCard.getText().toString();
				if (!"".equals(userID) && !"".equals(userName) && !"".equals(idCard)) {
					UserInfo user = new UserInfo();
					user.setUserId(userID);
					user.setUserName(userName);
					user.setIDCard(idCard);
					
					newTask(user);
				}
			}
		});
	}

	private void newTask(UserInfo user){
		System.out.println(MainService.isRun);
		if(!MainService.isNetAvailable){
			
			Toast.makeText(FindPwdActivity.this, "网络不可用！", Toast.LENGTH_LONG).show();
			
		}else {
			
			Map<String, Object> taskParams = new HashMap<String, Object>();
			
			taskParams.put("user", user);
			
			Task task = new Task(Task.FINDPWD, taskParams);
			
			//将任务加入主服务线程
			MainService.newTask(task);
		}
	}
	

	@Override
	public void refresh(Object... obj) {
		String password = (String) obj[0];
		if (null != password && !"".equals(password)) {
			et_password.setText(password);
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.leftIn(), SwitchActivityAnim.leftOut());
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

package cn.edu.wit.withelper.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.bean.UserInfo;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.services.UserInfoServices;
import cn.edu.wit.withelper.util.ClearEditText;
import cn.edu.wit.withelper.util.SharedPreferencesUtil;
import cn.edu.wit.withelper.util.SwitchActivityAnim;



public class LoginActivity extends Activity implements IAssistantActivity {

	public static final String TAG = "LoginActivity";

	private ProgressDialog progressDialog = null;
	
	private Button btn_Login;
	private ClearEditText etUserId;
	private ClearEditText etPassword;
	private CheckBox cbIsRemember;
	private CheckBox cbIsAuto;
	
	private ImageButton ib_spinner;
	protected View listView;
	private PopupWindow pop;
	private List<String> userIDs ;
	private List<UserInfo> loginedUsers ;
	private MyAdapter adapter;
	
	private Animation shake ;
	
	private UserInfoServices userInfoServices = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		init();
	}

	@Override
	public void init() {

		if(!MainService.isRun){
			Intent service = new Intent(this, MainService.class);
			startService(service);
		}
		
		userInfoServices = new UserInfoServices(LoginActivity.this);
		//获取所用用户
		initData();
		
		initView();
		
		// 把自己添加到Activity集合里面
		MainService.addActivity(this);
		
	}
	
	/**
	 * 初始化界面
	 */
	private void initView() {

		shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
		etUserId = (ClearEditText) findViewById(R.id.userid);
		etPassword = (ClearEditText) findViewById(R.id.password);
		cbIsRemember = (CheckBox) findViewById(R.id.isremember);
		cbIsAuto = (CheckBox) findViewById(R.id.isAuto);
		
		

		etUserId.setText("1005100109");
		etPassword.setText("1005100109");
		
		TextView tvGetPassword = (TextView) findViewById(R.id.tvGetPassword);
		//"找回密码"下划线
		tvGetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvGetPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this, FindPwdActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.rightIn(), SwitchActivityAnim.rightOut());
			}
		});
		
		//登录按钮
		btn_Login = (Button) findViewById(R.id.login_ok);
		btn_Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String userId = etUserId.getText().toString().trim();
				String password = etPassword.getText().toString().trim();

				if (!"".equals(userId) && !"".equals(password)) {
					Log.i(TAG, "click login");
					UserInfo loginUser = new UserInfo();
					loginUser = new UserInfo();
					loginUser.setUserId(userId);
					loginUser.setPassword(password);
					// 调用函数创建新任务
					newTask(loginUser);
				} else {
					if ("".equals(userId)) {
						etUserId.startAnimation(shake);
						etUserId.setHint("学号不能为空");
						etUserId.setHintTextColor(Color.RED);
					} else {
						etPassword.startAnimation(shake);
						etPassword.setHint("密码不能为空");
						etPassword.setHintTextColor(Color.RED);
					}
				}
			}
		});
		
		
		ib_spinner = (ImageButton) findViewById(R.id.ib_spinner);

		ib_spinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 弹出下拉列表
				ListView listView = new ListView(getApplicationContext());
				listView.setCacheColorHint(0x00000000);// 滑动时 不变色
				listView.setVerticalScrollBarEnabled(false);
				listView.setBackgroundColor(getResources().getColor(R.color.white));
				//设置透明度
				listView.getBackground().setAlpha(230);
				listView.setAdapter(new MyAdapter());

				pop = new PopupWindow(listView, etUserId.getWidth()+ib_spinner.getWidth(),
						LayoutParams.WRAP_CONTENT, true);

				// pop隐藏
				pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
				pop.setOutsideTouchable(true);
				pop.setFocusable(true);
//				pop.setAnimationStyle(R.style.PopupAnimation);
				pop.showAsDropDown(etUserId, 0, -8);
				pop.update();
			}
		});
		
	}

	private void newTask(UserInfo loginUser) {

		
		if(!MainService.isNetAvailable){
			
			Toast.makeText(LoginActivity.this, "网络不可用！", Toast.LENGTH_LONG).show();
			
		}else {
			
			Map<String, Object> taskParams = new HashMap<String, Object>();
			
			taskParams.put("loginUser", loginUser);
			
			Task task = new Task(Task.USER_LOGIN, taskParams);
			
			//将任务加入主服务线程
			MainService.newTask(task);
			
			showDialg();
			
		}
	}

	/**
	 * 更新登录界面，或登录成功后跳转，或显示错误信息
	 */
	@Override
	public void refresh(Object... obj) {
		
		progressDialog.dismiss();
		
		if (null != obj[0]) {
			
			if (obj[0] instanceof Exception) {
				Exception exception = (Exception) obj[0];
				System.out.println(exception.getMessage());
				Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			UserInfo user = (UserInfo)obj[0];
			
			if (null == user.getUserName() || "".equals(user.getUserName())) {	//用户名密码错误
				Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
				Toast.makeText(this, "请核查用户名密码以及网络连接是否可用", Toast.LENGTH_LONG).show();
			}else {	//登录成功，跳转到主界面
				
				Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
				Toast.makeText(LoginActivity.this, "欢迎 "+user.getUserName()+"童鞋", Toast.LENGTH_SHORT).show();
				
				int isRemember = cbIsRemember.isChecked() ? 1 :0 ;
				int isAuto = cbIsAuto.isChecked() ? 1 :0 ;
				// 跳转
				Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.fadeIn(), SwitchActivityAnim.bloomOut());
				
				
				
				if (1 == isAuto) {	//自动登录则存入数据库，且写到配置文件
					SharedPreferencesUtil.saveLoginUser(LoginActivity.this, user);
					userInfoServices.insertUserInfo(user);
				}else if (1 == isRemember) {	//记住密码就写入数据库
					userInfoServices.insertUserInfo(user);
				}
				this.finish();
			}
			
			
		}else {
			Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
		}
		
	}
	
	private void initData(){
		
		userIDs = new ArrayList<String>();
		
		loginedUsers = userInfoServices.getAllLoginedUser();
		String users = "";
		
		if (null != loginedUsers && loginedUsers.size() > 0) {
			for (UserInfo user : loginedUsers) {
				userIDs.add(user.getUserId());
				users += user.getUserId()+"\t  密码："+user.getPassword()+"\n";
			}
			System.out.println(users);
		}
	}
	
	
	
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return userIDs.size();
		}

		@Override
		public Object getItem(int position) {
			return userIDs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater
					.from(getApplicationContext());
			View view = inflater.inflate(R.layout.item_userids, parent, false);

			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			ImageButton delete = (ImageButton) view.findViewById(R.id.delete);

			tv_name.setText(userIDs.get(position));

			tv_name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					etUserId.setText(userIDs.get(position));
					
					etPassword.setText(loginedUsers.get(position).getPassword());
					
					pop.dismiss();
					
					
				}
			});

			delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					userIDs.remove(position);

					adapter.notifyDataSetChanged();
				}
			});

			return view;
		}

	}
	
	
	private void showDialg() {

		if (null == progressDialog  ) {
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setMessage("正在获取信息...");
		progressDialog.show();
	}
	
}

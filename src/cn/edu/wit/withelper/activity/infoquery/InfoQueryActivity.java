package cn.edu.wit.withelper.activity.infoquery;

import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class InfoQueryActivity extends Activity{

	private Button btnCet = null ;
	private Button btnExpress = null ;
	private Button btnGrade = null ;
	private Button btnOfficePhone = null ;
	private Button btnSchoolBus = null ;
	
	private TextView btnBack = null;
	private TextView btnBackHome = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.info_query);   //软件activity的布局
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar为自己标题栏的布局
		//设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.INFOQUERY);
		
		
		btnBack = (TextView) findViewById(R.id.back);
		btnBackHome = (TextView) findViewById(R.id.back_home);
		
		btnCet = (Button) findViewById(R.id.query_cet);
		btnExpress = (Button) findViewById(R.id.query_express);
		btnGrade = (Button) findViewById(R.id.query_grade);
		btnOfficePhone = (Button) findViewById(R.id.query_officenum);
		btnSchoolBus = (Button) findViewById(R.id.query_schoolbus);
		
		MyClickListener listener = new MyClickListener();
		
		btnBack.setOnClickListener(listener);
		btnBackHome.setOnClickListener(listener);
		
		btnCet.setOnClickListener(listener);
		btnExpress.setOnClickListener(listener);
		btnGrade.setOnClickListener(listener);
		btnOfficePhone.setOnClickListener(listener);
		btnSchoolBus.setOnClickListener(listener);
		
		
		
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(intent);
			overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private class MyClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			Intent intent = new Intent();
			
			int viewID = v.getId();
			
			if (viewID == R.id.back) {
				intent.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
				return;
			}else if(viewID == R.id.back_home){
				intent.setClass(getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
			}
			
			switch (viewID) {
			case R.id.query_cet:
				intent.setClass(getApplicationContext(), CETActivity.class);
				break;
			case R.id.query_express:
				intent.setClass(getApplicationContext(), ExpressActivity.class);
				break;
			case R.id.query_grade:
				intent.setClass(getApplicationContext(), GradeActivity.class);
				break;
			case R.id.query_officenum:
				intent.setClass(getApplicationContext(), CETActivity.class);
				break;
			case R.id.query_schoolbus:
				intent.setClass(getApplicationContext(), CETActivity.class);
				break;
			}
			
			startActivity(intent);
			overridePendingTransition(SwitchActivityAnim.upIn(), SwitchActivityAnim.upOut());
		}
		
	}
}

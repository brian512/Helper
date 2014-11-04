package cn.edu.wit.withelper.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.infoquery.ExpressActivity;
import cn.edu.wit.withelper.activity.infoquery.InfoQueryActivity;
import cn.edu.wit.withelper.activity.library.LibraryActivity;
import cn.edu.wit.withelper.activity.order.StoreListActivity;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.CircleImageView;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.MenuSlidingDrawer;
import cn.edu.wit.withelper.util.SharedPreferencesUtil;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class HomeActivity extends Activity implements IAssistantActivity{
	
	private static final String TAG = "HomeActivity";

	private GridView myGridView = null;
	private TextView tvUserName = null;
	private CircleImageView user_Icon = null;
	private MenuSlidingDrawer sliding = null;
	private SimpleAdapter simpleAdapter ;
	
	private Button btn_exit = null;
	private Button btn_logout = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.setContentView(R.layout.home);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_main); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText("微高校-WIT");
		init();

	}

	@Override
	public void init()  {

		tvUserName = (TextView) findViewById(R.id.userName);
		user_Icon = (CircleImageView) findViewById(R.id.user_icon);
		
		if (null == MainService.nowUser) {
			MainService.nowUser = SharedPreferencesUtil.getLoginUser(HomeActivity.this);
		}
		
//		tvUserName.setText(MainService.nowUser.getUserName());
		tvUserName.setText("张三");
		
		if (-1 < MainService.nowUser.getIconIndex()) {
			user_Icon.setImageDrawable(getResources().getDrawable(MainService.nowUser.getIconIndex()));
		}else {
			user_Icon.setImageDrawable(getResources().getDrawable(R.drawable.portrait_1));
		}
		
		
		sliding = (MenuSlidingDrawer) findViewById(R.id.slidingdrawer);;
		
		
		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		
		btn_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(SwitchActivityAnim.leftIn(), SwitchActivityAnim.leftOut());
				HomeActivity.this.finish();
				MainService.nowUser = null ;
			}
		});
		
		//退出程序
		btn_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainService.appExit(HomeActivity.this);
				HomeActivity.this.finish();
			}
		});
		
		
		this.myGridView = (GridView) findViewById(R.id.myGridView);
		
		int screenWidth  = getWindowManager().getDefaultDisplay().getWidth();
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		int letf = (int) (screenWidth*0.06);
		int top = (int) (screenHeight*0.06);
//		int right = (int) (screenWidth*0.06);
		int bottom = (int) (screenHeight*0.02);
		lp.width = (int)screenWidth;
//		lp.width = (int)(screenWidth * 0.9);
		lp.height = (int)(screenHeight * 0.6);
		lp.setMargins(0, top, 0, bottom);
//		myGridView.setLayoutParams(lp);
		
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("myimgView", R.drawable.main_infoquery);
		map1.put("mytxt", ConstantValues.INFOQUERY);
		list.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("myimgView", R.drawable.main_dingcan);
		map2.put("mytxt", ConstantValues.DINGCAN);
		list.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("myimgView", R.drawable.main_schoolnews);
		map3.put("mytxt", ConstantValues.SCHOOLNEWS);
		list.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("myimgView", R.drawable.main_library);
		map4.put("mytxt", ConstantValues.LIBRARY);
		list.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("myimgView", R.drawable.main_schoolmap);
		map5.put("mytxt", ConstantValues.SCHOOLMAP);
		list.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("myimgView", R.drawable.main_tieba);
		map6.put("mytxt", ConstantValues.TIEBA);
		list.add(map6);

		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("myimgView", R.drawable.main_schoolnews);
		map7.put("mytxt", ConstantValues.FEEDBACK);
		list.add(map7);

		HashMap<String, Object> map8 = new HashMap<String, Object>();
		map8.put("myimgView", R.drawable.main_more);
		map8.put("mytxt", ConstantValues.MORE);
		list.add(map8);
		
		HashMap<String, Object> map9 = new HashMap<String, Object>();
		map9.put("myimgView", R.drawable.main_aboutus);
		map9.put("mytxt", ConstantValues.ABOUTUS);
		list.add(map9);

		simpleAdapter = new SimpleAdapter(this, list,
				R.layout.home_grid_layout, new String[] { "myimgView", "mytxt" },
				new int[] { R.id.myimgView, R.id.mytxt });

		myGridView.setAdapter(simpleAdapter);
		myGridView.setOnItemClickListener(new ItemClickListener());
		
		myGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		
		sliding.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() { }
		});
		sliding.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() { }
		});

	}

	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(index);
			String txt = (String) item.get("mytxt");
			
			Intent intent = null;
			if (ConstantValues.INFOQUERY.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						InfoQueryActivity.class);
			} else if (ConstantValues.DINGCAN.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						StoreListActivity.class);
			} else if (ConstantValues.SCHOOLNEWS.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						NewsActivity.class);
			} else if (ConstantValues.LIBRARY.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						LibraryActivity.class);
			} else if (ConstantValues.SCHOOLMAP.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						TencentMapActivity.class);
			} else if (ConstantValues.TIEBA.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						TieBaActivity.class);
			} else if (ConstantValues.ABOUTUS.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						TieBaActivity.class);
			} else if (ConstantValues.FEEDBACK.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						SchoolNewsActivity.class);
			} else if (ConstantValues.MORE.equals(txt)) {
				intent = new Intent(HomeActivity.this,
						ExpressActivity.class);
			}
			Log.i(TAG, view.getClass().getName());
			startActivity(intent);
			overridePendingTransition(SwitchActivityAnim.upIn(), SwitchActivityAnim.upOut());
		}
		
	}

	@Override
	public void refresh(Object... params) {
		//更新主界面
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:{
			
			if (sliding.isOpened()) {
				sliding.animateClose();
				return true;
			}else {
				moveTaskToBack(false);
				return false;
			}
		}
		case KeyEvent.KEYCODE_MENU:{
			sliding.animateToggle();
			break;
		}
		default:
			break;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
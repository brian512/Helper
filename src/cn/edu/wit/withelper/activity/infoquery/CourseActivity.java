package cn.edu.wit.withelper.activity.infoquery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class CourseActivity extends Activity implements IAssistantActivity{

	private View view1, view2, view3,view4,view5,view6,view7;
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private List<View> viewList;
	private List<String> titleList;
	private TextView date ;
	private Button btnSwitchView ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_course);
		
		init();
		newTask();
	}


	@Override
	public void init() {
		
		MainService.addActivity(this);
		initView();
	}


	@Override
	public void refresh(Object... obj) {
		
		if (obj[0] instanceof List<?>) {
//			List<Course> courseList = (List<Course>) obj[0];
			
		}
	}
	
	
	private void newTask(){
		
		Map<String, Object> taskParams = new HashMap<String, Object>();
		
		taskParams.put("", "");
		
		Task task = new Task(Task.GET_COURSE, taskParams);
		
		MainService.newTask(task);
	}
	
	
	private void initView() {
		
		date = (TextView) findViewById(R.id.date);
		Date today = new Date();
		String strDate = (today.getMonth()+1)+"月"+today.getDate()+"日";
		date.setText(strDate);
		
		
		btnSwitchView = (Button) findViewById(R.id.switchView);
//		btnToday = (Button) findViewById(R.id.btn_today);
		btnSwitchView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("单日".equals(btnSwitchView.getText())) {
					btnSwitchView.setText("整周");
				}else {
					btnSwitchView.setText("单日");
				}
				
			}
		});
		
		
		
		
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(android.R.color.darker_gray)); 
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setBackgroundColor(Color.BLUE);
		pagerTabStrip.setTextSpacing(50);


		getLayoutInflater();
		LayoutInflater lf = LayoutInflater.from(this);
		view1 = lf.inflate(R.layout.info_coursetable, null);
		view2 = lf.inflate(R.layout.info_coursetable, null);
		view3 = lf.inflate(R.layout.info_coursetable, null);
		view4 = lf.inflate(R.layout.info_coursetable, null);
		view5 = lf.inflate(R.layout.info_coursetable, null);
		view6 = lf.inflate(R.layout.info_coursetable, null);
		view7 = lf.inflate(R.layout.info_coursetable, null);

		viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);
		viewList.add(view4);
		viewList.add(view5);
		viewList.add(view6);
		viewList.add(view7);

		titleList = new ArrayList<String>();// 每个页面的Title数据
		titleList.add("一");
		titleList.add("二");
		titleList.add("三");
		titleList.add("四");
		titleList.add("五");
		titleList.add("六");
		titleList.add("日");
		
		PagerAdapter pagerAdapter = new PagerAdapter() {

			int size = viewList.size();
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				
				View view = viewList.get(position%size);
				container.addView(view, 0);
				return view;
			}
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(viewList.get(position%size)); 
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
			@Override
			public int getCount() {
				return Integer.MAX_VALUE;
			}
			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}
			@Override
			public CharSequence getPageTitle(int position) {
				return "星期"+titleList.get(position%size);
			}
		};
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(viewList.size()*100+today.getDay()-1);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.downIn(), SwitchActivityAnim.downOut());
		}
		return super.onKeyDown(keyCode, event);
	}
	
}

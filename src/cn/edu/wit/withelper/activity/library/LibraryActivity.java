package cn.edu.wit.withelper.activity.library;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.EmptyActivity;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class LibraryActivity extends Activity {
	List<View> listViews;

	Context context = null;

	LocalActivityManager manager = null;

	TabHost tabHost = null;

	private ViewPager pager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.viewpaper);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.LIBRARY);

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
		
		context = LibraryActivity.this;

		pager = (ViewPager) findViewById(R.id.viewpager);

		// 定放一个放view的list，用于存放viewPager用到的view
		listViews = new ArrayList<View>();

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		Intent i1 = new Intent(context, SearchBookActivity.class);
		listViews.add(getView("A", i1));
		Intent i2 = new Intent(context, MyBorrowActivity.class);
		listViews.add(getView("B", i2));
		Intent i3 = new Intent(context, HotBookActivity.class);
		listViews.add(getView("C", i3));

		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setup(manager);

		// 这儿主要是自定义一下tabhost中的tab的样式
		RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.library_tabhost, null);
		TextView tvTab1 = (TextView) tabIndicator1.findViewById(R.id.tv_title);
		tvTab1.setText("图书查询");

		RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.library_tabhost, null);
		TextView tvTab2 = (TextView) tabIndicator2.findViewById(R.id.tv_title);
		tvTab2.setText("我的借书");

		RelativeLayout tabIndicator3 = (RelativeLayout) LayoutInflater.from(
				this).inflate(R.layout.library_tabhost, null);
		TextView tvTab3 = (TextView) tabIndicator3.findViewById(R.id.tv_title);
		tvTab3.setText("热门图书");

		Intent intent = new Intent(context, EmptyActivity.class);
		// 注意这儿Intent中的activity不能是自身,所以我弄了个空的acitivity
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1)
				.setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2)
				.setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("C").setIndicator(tabIndicator3)
				.setContent(intent));

		pager.setAdapter(new MyPageAdapter(listViews));
		//初始化位置 在中间
		pager.setCurrentItem(1);
		tabHost.setCurrentTab(1);
		
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// 当viewPager发生改变时，同时改变tabhost上面的currentTab
				tabHost.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});

		// 点击tabhost中的tab时，要切换下面的viewPager
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				if ("A".equals(tabId)) {
					pager.setCurrentItem(0);
				}
				if ("B".equals(tabId)) {

					pager.setCurrentItem(1);
				}
				if ("C".equals(tabId)) {
					pager.setCurrentItem(2);
				}
				
				System.out.println(listViews.get(pager.getCurrentItem()));
			}
		});

	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	private class MyPageAdapter extends PagerAdapter {

		private List<View> list;

		private MyPageAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(View view, int position, Object arg2) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View view, int position) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

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

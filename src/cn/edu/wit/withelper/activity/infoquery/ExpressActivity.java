package cn.edu.wit.withelper.activity.infoquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.Express;
import cn.edu.wit.withelper.bean.ExpressContentData;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.ClearEditText;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.MyAutoCompleteTextView;
import cn.edu.wit.withelper.util.SharedPreferencesUtil;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class ExpressActivity extends Activity implements IAssistantActivity{

	private MyAutoCompleteTextView etExpressName = null;
	private ClearEditText etOrderID = null;
	private TextView tvResult = null;
	private Button btSubmit = null;
	
	protected View listView;
	private PopupWindow pop;
	private List<String> expressNames ;
	
	private MyAdapter adapter;
	
	private static final String[] EXPRESS ={
		 "申通快递","圆通快递","中通快递","顺丰快递",
		 "韵达快递","汇通快递","天天快递"
	};
	
	
	
	private static final String spName = "express";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.info_express);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.EXPRES);
		
		init();
	}

	@Override
	public void init() {
		
		MainService.addActivity(this);
		
		expressNames = Express.expressNames;
		
		etExpressName = (MyAutoCompleteTextView) findViewById(R.id.expressName);
		etOrderID = (ClearEditText) findViewById(R.id.orderID);
		btSubmit = (Button) findViewById(R.id.submit);
		tvResult = (TextView) findViewById(R.id.tvResult);
		
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,EXPRESS);
		etExpressName.setAdapter(adapter);
		
		
		
		List<String> list = new ArrayList<String>();
		list.add(Express.EXPRESSNAME);
		list.add(Express.ORDERID);
		Map<String, String> map = SharedPreferencesUtil.getSharedPreferences(ExpressActivity.this, spName, list);
		etExpressName.setText(map.get(Express.EXPRESSNAME));
		etOrderID.setText(map.get(Express.ORDERID));
		
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
				String expressName = etExpressName.getText().toString();
				String expressID = Express.expressCode.get(expressName);
				String orderID = etOrderID.getText().toString();
				
				Express express = new Express();
				express.setExpressID(expressID);
				express.setName(expressName);
				express.setOrder(orderID);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put(Express.EXPRESSNAME, expressName);
				map.put(Express.ORDERID, orderID);
				SharedPreferencesUtil.editSharedPreferences(ExpressActivity.this, spName, map);
				
				newTask(express);
			}
		});
		
	}

	private void newTask(Express express) {

		if(!MainService.isNetAvailable){
			
			Toast.makeText(ExpressActivity.this, "网络不可用！", Toast.LENGTH_LONG).show();
			
		}else {
			
			Map<String, Object> taskParams = new HashMap<String, Object>();
			
			taskParams.put("express", express);

			Task task = new Task(Task.GET_EXPRESS, taskParams);
			
			//将任务加入主服务线程
			MainService.newTask(task);
		}
	}
	
	@Override
	public void refresh(Object... obj) {
		
		Express express = (Express) obj[0];
		
		List<ExpressContentData> contentDatas = express.getContentList();
		if (null != contentDatas) {
			String strResult = "";
			for (int i = 0; i < contentDatas.size(); i++) {
				strResult += "time:"+contentDatas.get(i).getTime()+"\ncontent:"+contentDatas.get(i).getContent()+"\n\n";
			}
			tvResult.setText(strResult);
		}else {
			tvResult.setText("查询失败");
		}
	}
	
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return expressNames.size();
		}

		@Override
		public Object getItem(int position) {
			return expressNames.get(position);
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

			tv_name.setText(expressNames.get(position));

			tv_name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					etExpressName.setText(expressNames.get(position));
					
					pop.dismiss();
					
					
				}
			});

			delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					expressNames.remove(position);

					adapter.notifyDataSetChanged();
				}
			});

			return view;
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

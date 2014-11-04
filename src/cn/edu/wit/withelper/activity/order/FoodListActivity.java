package cn.edu.wit.withelper.activity.order;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class FoodListActivity extends Activity {

	private MyExpandableListAdapter adapter;
	private ExpandableListView exList;
	private ArrayList<OrderData> orderDatas;
	
	private TextView tvStoreLocation = null;
	private Button btnSubmitOrder = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.ep_foodlist);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar为自己标题栏的布局
		//设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
        
		TextView btnBack = (TextView) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), StoreListActivity.class);
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
		
		
		String title = getIntent().getStringExtra("title");
		String location = getIntent().getStringExtra("location");
		tvMainTitle.setText(title);
		
		tvStoreLocation = (TextView) findViewById(R.id.store_location);
		tvStoreLocation.setText(location);
		
		btnSubmitOrder = (Button) findViewById(R.id.bt_submit);
		btnSubmitOrder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				MyDialog dialog = new MyDialog(FoodListActivity.this, R.style.MyDialog, orderDatas);
				dialog.show();
			}
		});
		
		exList = (ExpandableListView) findViewById(R.id.exlist);
		orderDatas = OrderData.getOrderDatas();
		adapter = new MyExpandableListAdapter(this, orderDatas, exList);
		exList.setAdapter(adapter);
		
		// 取消系统自带的父列表的图标
		exList.setGroupIndicator(null);
		
		exList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				CheckedTextView checkbox = (CheckedTextView) v.findViewById(R.id.list_item_text_child);
				checkbox.toggle();

				// find parent view by tag
				View parentView = exList.findViewWithTag(orderDatas.get(groupPosition).name);
				if (parentView != null) {
					OrderData orderData = orderDatas.get(groupPosition);
					if (checkbox.isChecked()) {
						// 加入已选列表
						orderData.selection.add(checkbox.getText().toString());
					} else {
						// 从已选列表中删除
						orderData.selection.remove(checkbox.getText().toString());
					}

				}
				return true;
			}
		});

		/**
		 * 点击父列表中的项
		 */
		exList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// 让点击项弹回最顶部
				if (!exList.expandGroup(groupPosition)) {// 当点击的节点和打开的节点是同一个节点时，关闭此节点
					exList.collapseGroup(groupPosition);
				}else {
					exList.setSelectedGroup(groupPosition);
				}
				
				return true;
			}
		});

		
		exList.expandGroup(0);
		
	}//end of onCreate()

	
	private class MyDialog extends Dialog{
		
	    private Button btnCancel = null;
	    private Button btnConfirm = null;
	    private TextView tvOrderContent = null;
	    
//	    private List<OrderData> orderDatas = null;
	    
	    
	    public MyDialog(Context context) {
	        super(context);
	    }
	    public MyDialog(Context context, int theme, List<OrderData> orderDatas){
	        super(context, theme);
//	        this.orderDatas = orderDatas;
	    }

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.setContentView(R.layout.ep_order_confirm_dialog);
	        
	        btnCancel = (Button) findViewById(R.id.dialog_button_cancel);
	        btnConfirm = (Button) findViewById(R.id.dialog_button_ok);
	        tvOrderContent = (TextView) findViewById(R.id.order_content);
	        if(orderDatas != null){
	        	String str = "";
	        	for (OrderData orderData : orderDatas) {
	        		int size = orderData.selection.size();
	        		if(size > 0){
	        			for (int i = 0; i < size; i++) {
	        				str += orderData.selection.get(i)+"\n";
						}
	        		}
				}
	        	tvOrderContent.setText(str);
	        }
	        
	        btnConfirm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
					Toast.makeText(getApplicationContext(), "订餐成功", Toast.LENGTH_LONG).show();
				}
			});
	        btnCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
	    }
	    
	    
	}
	
	
	class MyExpandableListAdapter extends BaseExpandableListAdapter {

		private LayoutInflater inflater;
		private ArrayList<OrderData> mParent;

		public MyExpandableListAdapter(Context context,
				ArrayList<OrderData> parent, 
				ExpandableListView accordion) {
			super();
			mParent = parent;
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View view, ViewGroup viewGroup) {
		
			if (view == null) {
				view = inflater.inflate(R.layout.ep_list_item_parent, null);
			}
			// set category name as tag so view can be found view later
			view.setTag(getGroup(groupPosition).toString());
		
			TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);
		
			// "i" is the position of the parent/group in the list
			textView.setText(getGroup(groupPosition).toString());
		
//			TextView sub = (TextView) view.findViewById(R.id.list_item_text_subscriptions);
		
//			if (mParent.get(groupPosition).selection.size() > 0) {
//				sub.setText(mParent.get(groupPosition).selection.toString());
//			} else {
//				sub.setText("");
//			}
		
			return view;
		}

		@Override
		// in this method you must set the text to see the children on the list
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ep_list_item_child, null);
			}
		
			CheckedTextView textView = (CheckedTextView) convertView.findViewById(R.id.list_item_text_child);
		
			textView.setText(mParent.get(groupPosition).children.get(childPosition).name);
		
			// set checked if parent category selection contains child category
			if (mParent.get(groupPosition).selection.contains(textView.getText().toString())) {
				textView.setChecked(true);
			} else {
				textView.setChecked(false);
			}
		
			// return the entire view
			return convertView;
		}

		@Override
		public int getGroupCount() {
			return mParent.size();
		}
		@Override
		public int getChildrenCount(int i) {
			return mParent.get(i).children.size();
		}
		@Override
		public Object getGroup(int i) {
			return mParent.get(i).name;
		}
		@Override
		public Object getChild(int i, int i1) {
			return mParent.get(i).children.get(i1);
		}
		@Override
		public long getGroupId(int i) {
			return i;
		}
		@Override
		public long getChildId(int i, int i1) {
			return i1;
		}
		@Override
		public boolean hasStableIds() {
			return true;
		}
		@Override
		public boolean isChildSelectable(int i, int i1) {
			return true;
		}
		@Override
		public void onGroupExpanded(int groupPosition) {
			super.onGroupExpanded(groupPosition);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.leftIn(),
					SwitchActivityAnim.leftOut());
		}
		return super.onKeyDown(keyCode, event);
	}

}
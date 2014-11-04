package cn.edu.wit.withelper.activity.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.wit.withelper.util.RefreshListView.RefreshListener;

import cn.edu.wit.withelper.util.RefreshListView;

import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.Book;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class HotBookActivity extends Activity implements IAssistantActivity{

	private RefreshListView refreshListView = null ;
	private List<Book> hotBooks;
	private static final int REFRESH_OK = 0;
	private MessageAdapter messageAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refresh_listview);
		
		init();
	}
	
	@Override
	public void init() {
		
		MainService.addActivity(HotBookActivity.this);
		
		refreshListView = (RefreshListView) findViewById(R.id.pull_refresh_listview);
		
		newTask("B");
	}
	
	private void newTask(String classNum){
		
		Map<String, Object> taskParams = new HashMap<String, Object>();
		
		taskParams.put("classNum", classNum);
System.out.println(classNum);
		Task task = new Task(Task.GET_BOOKHOT, taskParams);
		
		//将任务加入主服务线程
		MainService.newTask(task);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {

		if (obj[0] instanceof List) {
			hotBooks = (List<Book>) obj[0];
		}else {
			hotBooks = new ArrayList<Book>();
		}
		
		messageAdapter = new MessageAdapter();
		refreshListView.setOnRefreshListener(new Refresh());
		refreshListView.setAdapter(messageAdapter);

	}
	
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_OK:
				for (int j = 0; j < 2; j++) {
//					messages.add();
					System.out.println("add new data");
				}
				messageAdapter.notifyDataSetChanged();
				refreshListView.currentHeaderState = 3;
				refreshListView.refreshViewByRefreshingState();
				break;
			default:
				break;
			}
		}

	};
	
	
	
	class Refresh implements RefreshListener {
		@Override
		public void refreshing() {
			new Thread() {

				@Override
				public void run() {
					try {
						sleep(5000);
						handler.sendEmptyMessage(REFRESH_OK);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}.start();
		}
	}

	class MessageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return hotBooks.size();
		}

		@Override
		public Object getItem(int position) {
			return hotBooks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public class ViewHolder {
			public Button messagePButton;
			public TextView messageTextTV;
			public TextView messageTimeTV;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.refresh_list_item, null);
				holder = new ViewHolder();
				holder.messageTextTV = (TextView) convertView.findViewById(R.id.message_tv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.messageTextTV.setText(hotBooks.get(position).toString());

			return convertView;
		}
	}

	
}

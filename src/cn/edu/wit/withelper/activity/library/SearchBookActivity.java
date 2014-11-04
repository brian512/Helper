package cn.edu.wit.withelper.activity.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.SearchBook;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchBookActivity extends Activity implements IAssistantActivity {

	private TextView tvResult = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library_search);
		
		init();
	}
	
	
	@Override
	public void init() {
		
		MainService.addActivity(this);
		
		tvResult = (TextView) findViewById(R.id.tv_result);
		final EditText etKeyword = (EditText) findViewById(R.id.et_searchkey);
		Button btnSubmit = (Button) findViewById(R.id.btn_submit);
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				String keyWord = etKeyword.getText().toString();
				newTask(keyWord);
				System.out.println(keyWord);
			}

		});
	}

	private void newTask(String keyWord) {
		
		if(!MainService.isNetAvailable){
			
			Toast.makeText(getApplicationContext(), "网络不可用！", Toast.LENGTH_LONG).show();
			
		}else {
			
			Map<String, Object> taskParams = new HashMap<String, Object>();
			
			taskParams.put("keyWord", keyWord);

			Task task = new Task(Task.GET_SEARCHBOOK, taskParams);
			
			//将任务加入主服务线程
			MainService.newTask(task);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {
		
		if (obj[0] instanceof List) {
			List<SearchBook> searchBooks = (List<SearchBook>) obj[0];
			String str = "";
			for (SearchBook searchBook : searchBooks) {
				str += searchBook.toString();
			}
			
			tvResult.setText(str);
		}
		
	}


}

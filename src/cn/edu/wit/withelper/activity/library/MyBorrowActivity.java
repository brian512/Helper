package cn.edu.wit.withelper.activity.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.IAssistantActivity;
import cn.edu.wit.withelper.bean.BorrowBook;
import cn.edu.wit.withelper.bean.Task;
import cn.edu.wit.withelper.services.MainService;

public class MyBorrowActivity extends Activity implements IAssistantActivity{

	private TextView textView = null ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		init();
	}

	
	@Override
	public void init() {
		
		textView = (TextView) findViewById(R.id.tvResult);
		
		MainService.addActivity(MyBorrowActivity.this);
		
		newTask(MainService.nowUser.getUserId());
		
	}

	
	private void newTask(String password){
		
		Map<String, Object> taskParams = new HashMap<String, Object>();
		
		taskParams.put("password", password);
		
		Task task = new Task(Task.GET_BOOKBORROW, taskParams);
		
		//将任务加入主服务线程
		MainService.newTask(task);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... obj) {
		
		if (obj[0] instanceof List<?>) {
			
			List<BorrowBook> books = (List<BorrowBook>) obj[0];
			if (books.size() <= 0) {
				textView.setText("暂无借书");
			} else {
				String str = "";
				for (BorrowBook book : books) {
					str += book.toString();
				}
				
				textView.setText(str);
			}
			
		}else if(obj[0] instanceof String){//密码错误
			inputTitleDialog();
		}
	
			
	}
	
	private void inputTitleDialog() {

		 //创建view从当前activity获取loginactivity
		  LayoutInflater  inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		  View view = inflater.inflate(R.layout.library_login, null);
		  
		  final EditText et_userID=(EditText)view.findViewById(R.id.txt_userID);
		  final EditText et_password = (EditText)view.findViewById(R.id.txt_password);
		  et_userID.setText(MainService.nowUser.getUserId());
		  et_password.setText(""); //为了测试方便所以在这里初始化弹出框是填上账号密码
		  AlertDialog.Builder ad =new AlertDialog.Builder(MyBorrowActivity.this);
		  ad.setView(view);
		  ad.setTitle("图书馆登录");
		  final AlertDialog selfdialog = ad.create();
		  
		  selfdialog.setButton("确定", new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
			   //获取输入框的信息
			   newTask(et_password.getText().toString().trim());
		   }
		  });
		  selfdialog.setButton2("取消", new DialogInterface.OnClickListener() {
		   
		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		   		selfdialog.cancel();
		   }
		  });
		  selfdialog.show();
    }
}

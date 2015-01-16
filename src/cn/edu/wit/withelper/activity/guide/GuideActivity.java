package cn.edu.wit.withelper.activity.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.LoginActivity;
import cn.edu.wit.withelper.util.SharedPreferencesUtil;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

public class GuideActivity extends Activity implements OnViewChangeListener{
	
	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;//标记的点
	private int count;
	private int currentItem;
	private Button startBtn;//最后一页的按钮
	private LinearLayout pointLLayout;//一排点的布局
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initView();
    }
    
	private void initView() {
		mScrollLayout  = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(onClick);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for(int i = 0; i< count;i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;//默认进入第一个页面
		imgs[currentItem].setEnabled(false);//与其他页面的标记区分开
		mScrollLayout.SetOnViewChangeListener(this);
	}
	
	//最后一页的按钮监听事件，动画跳转到登陆页面
	private View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startBtn:
				Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
				GuideActivity.this.startActivity(intent);
				GuideActivity.this.finish();
				overridePendingTransition(SwitchActivityAnim.rightIn(),SwitchActivityAnim.rightOut());
				//标记为使用过
				SharedPreferencesUtil.setIsUsed(GuideActivity.this, true);
				break;
			}
		}
	};

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}
	
	//更新界面，标记当前页
	private void setcurrentPoint(int position) {
		if(position < 0 || position > count -1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}
}


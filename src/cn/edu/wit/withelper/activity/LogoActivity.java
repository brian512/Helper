package cn.edu.wit.withelper.activity;

import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.guide.GuideActivity;
import cn.edu.wit.withelper.bean.UserInfo;
import cn.edu.wit.withelper.services.MainService;
import cn.edu.wit.withelper.util.SharedPreferencesUtil;
import cn.edu.wit.withelper.util.SwitchActivityAnim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity {
	
	Intent intent = null;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//设置窗口为无标题栏，全屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//标记为全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.logo);

		//设置动画
		AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
		animation.setDuration(2000);//2S渐现

		ImageView img_logo = (ImageView) this.findViewById(R.id.img_logo);
		img_logo.setAnimation(animation);//将logo设置为动画显示

		
		UserInfo loginUser = SharedPreferencesUtil.getLoginUser(LogoActivity.this);
		boolean isUsed = SharedPreferencesUtil.getIsUsed(LogoActivity.this);
		
		//判断是否使用过
		if(isUsed){
			//判断是否有自动登录用户
			if(null == loginUser){//无自动登录用户则进入登录界面
				intent = new Intent(LogoActivity.this,LoginActivity.class);
			}else {//有自动登录用户则进入主界面
				intent = new Intent(LogoActivity.this,HomeActivity.class);
			}
		}else {
			intent = new Intent(LogoActivity.this,GuideActivity.class);
		}
//		intent = new Intent(LogoActivity.this, LoginActivity.class);
		
		
		//动画监听
		animation.setAnimationListener(new AnimationListener() {

			//动画结束时跳转
			public void onAnimationEnd(Animation animation) {

				startActivity(intent);
				LogoActivity.this.finish();
				overridePendingTransition(SwitchActivityAnim.rightIn(),SwitchActivityAnim.rightOut());
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}
			@Override
			public void onAnimationStart(Animation arg0) {
				startMainService();
			}
		});
		
	}
	
	// 启动主服务
	private void startMainService(){
		Intent service = new Intent(this, MainService.class);
		startService(service);
	}
	
}
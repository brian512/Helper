
package cn.edu.wit.withelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

import com.tencent.tencentmap.streetviewsdk.StreetViewListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewShow;
import com.tencent.tencentmap.streetviewsdk.map.basemap.GeoPoint;
import com.tencent.tencentmap.streetviewsdk.overlay.ItemizedOverlay;

public class StreetViewActivity extends Activity implements StreetViewListener {
    
    /**
     * View Container
     */
    private ViewGroup mContainer;
    
    /**
     * 街景View
     */
    private View mStreetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
		// 显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		mContainer = new LinearLayout(StreetViewActivity.this);
		setContentView(mContainer);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar); // titlebar为自己标题栏的布局
		// 设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.SCHOOLVIEW);
        
		TextView btnBack = (TextView) findViewById(R.id.back);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TencentMapActivity.class);
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
		
        Intent intent = getIntent();
        int lat = intent.getIntExtra("lat", (int)(30.463175 * 1E6));
        int lon = intent.getIntExtra("lon", (int)(114.43649 * 1E6));
        // 使用经纬度获取街景
         GeoPoint center = new GeoPoint(lat,lon);
         
         StreetViewShow.getInstance().showStreetView(this, center, 300, this,-170, 0);
         // 使用svid获取街景
//        StreetViewShow.getInstance().showStreetView(this, "10011026130910162137500", this, -170, 0);
    }

    @Override
    protected void onDestroy() {
    	StreetViewShow.getInstance().destory();
        super.onDestroy();
    }

    @Override
    public void onViewReturn(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mStreetView = v;
                mContainer.addView(mStreetView);
                Log.d("street", StreetViewShow.getInstance().getStreetStatus().toString());
            }
        });
    }

    // 网络错误处理
    @Override
    public void onNetError() {
    	Toast.makeText(StreetViewActivity.this, "请检查网络是否可用", Toast.LENGTH_LONG).show();
    }

    // 解析数据错误处理
    @Override
    public void onDataError() {
    	Toast.makeText(StreetViewActivity.this, "数据出错，请返回重试！", Toast.LENGTH_LONG).show();
    }

    
    @Override
    public ItemizedOverlay getOverlay() {
        return null;
    }


	@Override
	public void onLoaded() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mStreetView.setVisibility(View.VISIBLE);
            }
        });
	}

    @Override
    public void onAuthFail() {
        // 验证失败
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			overridePendingTransition(SwitchActivityAnim.leftIn(), SwitchActivityAnim.leftOut());
		}
		return super.onKeyDown(keyCode, event);
	}
}


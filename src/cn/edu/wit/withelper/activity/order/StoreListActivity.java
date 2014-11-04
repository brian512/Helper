package cn.edu.wit.withelper.activity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.edu.wit.withelper.R;
import cn.edu.wit.withelper.activity.HomeActivity;
import cn.edu.wit.withelper.util.ConstantValues;
import cn.edu.wit.withelper.util.SwitchActivityAnim;

import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;


public class StoreListActivity extends FragmentActivity
{

    private int mDragStartMode = DragSortController.ON_LONG_PRESS;
    private boolean mDragEnabled = true;

    private String mTag = "dslvTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		//显示标题，以下三行代码顺序不可变
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);   
		setContentView(R.layout.dslv_storelist_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);  //titlebar为自己标题栏的布局
		//设置标题
		TextView tvMainTitle = (TextView) findViewById(R.id.main_title);
		tvMainTitle.setText(ConstantValues.DINGCAN);
        
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
		
		
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.test_bed, getNewDslvFragment(), mTag).commit();
        }
    }

    private Fragment getNewDslvFragment() {
        DSLVFragment f = DSLVFragment.newInstance(this);
        f.dragStartMode = mDragStartMode;
        f.dragEnabled = mDragEnabled;
        return f;
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
    

class DSLVFragment extends ListFragment {

	static StoreListActivity thisActivity = null;
	SimpleAdapter adapter = null ;

    private List<HashMap<String, Object>> listItem ;

    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                    	listItem.add(to, listItem.remove(from));
                    	adapter.notifyDataSetChanged();
                    	for (HashMap<String, Object> item : listItem) {
							System.out.println(item.get("text"));
						}
                    }
                }
            };

    private DragSortListView mDslv;
    private DragSortController mController;

    public int dragStartMode = DragSortController.ON_DOWN;
    public boolean dragEnabled = true;

    public static DSLVFragment newInstance(StoreListActivity activity) {
        DSLVFragment f = new DSLVFragment();

        thisActivity = activity;
        return f;
    }


    public DragSortController getController() {
        return mController;
    }

    /**
     * 初始化列表数据
     */
    private void initData(){
    	
        listItem = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map1 = new HashMap<String, Object>();
    	map1.put("drag_handle", R.drawable.store01);
    	map1.put("store_name_", "廖记棒棒鸡");
    	map1.put("store_location_", "廖记棒棒鸡地址");
    	listItem.add(map1);
    	HashMap<String, Object> map2 = new HashMap<String, Object>();
    	map2.put("drag_handle", R.drawable.store02);
    	map2.put("store_name_", "东北菜饺子馆");
    	map2.put("store_location_", "东北菜饺子馆地址");
    	listItem.add(map2);
    	HashMap<String, Object> map3 = new HashMap<String, Object>();
    	map3.put("drag_handle", R.drawable.store03);
    	map3.put("store_name_", "黄焖鸡米饭");
    	map3.put("store_location_", "黄焖鸡米饭地址");
    	listItem.add(map3);
    	HashMap<String, Object> map4 = new HashMap<String, Object>();
    	map4.put("drag_handle", R.drawable.store04);
    	map4.put("store_name_", "小胡鸭");
    	map4.put("store_location_", "小胡鸭地址");
    	listItem.add(map4);
    	
    }
    
    
    /**
     * Called in onCreateView. Override this to provide a custom
     * DragSortController.
     */
    public DragSortController buildController(DragSortListView dslv) {
        
        DragSortController controller = new DragSortController(dslv);
        controller.setDragHandleId(R.id.drag_handle);
        controller.setDragInitMode(dragStartMode);
        return controller;
    }


    /** Called when the activity is first created. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mDslv = (DragSortListView) inflater.inflate(R.layout.dslv_fragment_main, container, false);

        mController = buildController(mDslv);
        mDslv.setFloatViewManager(mController);
        mDslv.setOnTouchListener(mController);
        mDslv.setDragEnabled(dragEnabled);

        return mDslv;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDslv = (DragSortListView) getListView(); 

        mDslv.setDropListener(onDrop);

        initData();
        
        adapter = new SimpleAdapter(
        		getActivity(), 
        		listItem, 
        		R.layout.dslv_item, 
        		new String[]{"drag_handle","store_name_","store_location_"}, 
        		new int[]{R.id.drag_handle,R.id.store_name_,R.id.store_location_});
        
        setListAdapter(adapter);
        
        
		ListView lv = getListView();
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {

				Intent intent = new Intent(DSLVFragment.thisActivity,
						FoodListActivity.class);

				intent.putExtra("title", listItem.get(index).get("store_name_").toString());
				intent.putExtra("location",listItem.get(index).get("store_location_").toString());

				startActivity(intent);
				thisActivity.overridePendingTransition(
						SwitchActivityAnim.rightIn(),
						SwitchActivityAnim.rightOut());
			}
		});
    }
}
package cn.edu.wit.withelper.db;


import cn.edu.wit.withelper.bean.OverlayItemUtil;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作帮助类
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		// /第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
		super(context, DBInfo.DB_NAME, null, DBInfo.VERSION);
	}

	
	// 用于初次使用软件时生成数据库表
	// 当调用SQLiteOpenHelper的getWritableDatabase()或者getReadableDatabase()方法获取用于操作数据库的SQLiteDatabase实例的时候，
	// 如果数据库不存在，Android系统会自动生成一个数据库，接着调用onCreate()方法，
	// onCreate()方法在初次生成数据库时才会被调用，
	// 在onCreate()方法里可以生成数据库表结构及添加一些应用使用到的初始化数据
	public void onCreate(SQLiteDatabase db) {

		System.out.println("创建数据库表：userinfo news grade course");
		db.execSQL(DBInfo.Table_UserInfo.CREATE);
		db.execSQL(DBInfo.Table_News.CREATE);
		db.execSQL(DBInfo.Table_Grade.CREATE);
		db.execSQL(DBInfo.Table_Course.CREATE);
		db.execSQL(DBInfo.Table_Book.CREATE);
		db.execSQL(DBInfo.Table_BorrowBook.CREATE);
		db.execSQL(DBInfo.Table_OverlayItem.CREATE);
		

db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('一号实验楼', null, '30.46151000', '114.43290700');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('一号教学楼', null, '30.46134400', '114.43449400');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('三号教学楼', null, '30.46295300', '114.43428000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('东校门', null, '30.46317500', '114.43649000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('中快餐厅', null, '30.46212100', '114.43041800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('二号实验楼', null, '30.46212100', '114.43243500');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('二号教学楼', null, '30.46215800', '114.43438700');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('体育馆', null, '30.46210200', '114.43524500');");
//db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('勤工助学中心', null, '30.45901300', '114.43275600');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('北极星工作室', null, '30.45888400', '114.43325000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('北校门', null, '30.46293400', '114.42625500');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('四号教学楼', null, '30.46434000', '114.43196200');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('图书馆', null, '30.46065900', '114.43329300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('多功能报告厅', null, '30.46424800', '114.43108300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('大学生活动中心', null, '30.45925400', '114.43344300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('大礼堂', null, '30.45997500', '114.43196200');");
//db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('小天地', null, '30.45573900', '114.43204800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('排球场', null, '30.46067800', '114.43569600');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('教辅一栋', null, '30.46236100', '114.42913000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('教辅七栋', null, '30.46415500', '114.42898000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('教辅三栋', null, '30.46269400', '114.42861500');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('教辅二栋', null, '30.46276800', '114.42915200');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('教辅五栋', null, '30.46321200', '114.42906600');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('教辅八栋', null, '30.46324900', '114.42812200');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('文科楼', null, '30.46276800', '114.43222000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('景臣时代', null, '30.45869900', '114.43387200');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('机电院大楼', null, '30.46345200', '114.43282100');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('校医院', null, '30.46082600', '114.43091100');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('校园卡服务中心', null, '30.45936500', '114.43397900');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑一栋', '12345678', '30.45686800', '114.43153300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑一食堂', null, '30.45749600', '114.43187700');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑七栋', null, '30.45625700', '114.43312100');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑三栋', null, '30.45618300', '114.43136200');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑九栋', null, '30.45561000', '114.43292800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑二栋', '12345678', '30.45647900', '114.43146900');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑二食堂', null, '30.45573900', '114.43204800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑五栋', null, '30.45546200', '114.43125400');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑八栋', null, '30.45594300', '114.43301400');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑六栋', null, '30.45672000', '114.43312100');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑十栋', null, '30.45525800', '114.43292800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑四栋', null, '30.45583200', '114.43129700');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('泰塑物业', null, '30.45729300', '114.43153300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('洗浴中心', null, '30.46206500', '114.42990300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('电气院大楼', null, '30.46358200', '114.43410800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('知博广场', null, '30.46352600', '114.43335700');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('研究生楼', null, '30.46371100', '114.42900100');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('粉红城堡', null, '30.46282300', '114.42795000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('精工实习分厂', null, '30.46213900', '114.42949500');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('精工实习厂', null, '30.46256400', '114.43001000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('网球场', null, '30.46014100', '114.43621100');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('艺术院大楼', null, '30.46339700', '114.43226300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('西南校门', null, '30.45903200', '114.43230600');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('计算机大楼', null, '30.46469100', '114.43425800');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('零点咖啡', null, '30.45905000', '114.43314300');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('静思湖', null, '30.45995600', '114.43428000');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('风行超市', null, '30.46293400', '114.42863700');");
db.execSQL("INSERT INTO `"+OverlayItemUtil.TB_NAME+"` (`title`, `snippet`, `point_lat`, `point_lon`) VALUES ('鹏翔花园', null, '30.45812500', '114.43224100');");



	}

	// onUpgrade()方法在数据库的版本发生变化时会被调用，一般在软件升级时才需改变版本号
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// db.execSQL("DROP TABLE IF EXISTS Customer");
		// onCreate(db);

		// 上面两句在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建。
		// 一般在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要考虑用户存放于数据库中的数据不会丢失

	}

}

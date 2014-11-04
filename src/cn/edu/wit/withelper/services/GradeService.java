package cn.edu.wit.withelper.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.edu.wit.withelper.bean.Grade;
import cn.edu.wit.withelper.util.InterfaceUtil;

public class GradeService extends BaseService{

	private static final String TAG = "GradeService";
	
	private List<Grade> grades = null;
	
	public static GradeService gradeService = null;
	
	public static GradeService getInstance(Context paramContext) {
		
		if (null == gradeService) {
			gradeService = new GradeService(paramContext);
		}
		
		return gradeService;
	}
	
	private GradeService(Context paramContext) {
		super(paramContext);
	}

	/**
	 * 通过当前用户ID及姓名生成查询链接，并调用getGrageByHtml(html)获取成绩网页源码
	 * @return
	 * @throws Exception
	 */
	public List<Grade> getAllGrage() throws Exception {
		
		grades = getGradeList();
		if (grades.size() <= 0) {
			String URL = "http://www.withelper.com/API/Android/GetGrade";
			final Map<String, String> params = new HashMap<String, String>();
			params.put("sessionid", MainService.nowUser.getSessionId());
			
			grades = getGradeListByJson(InterfaceUtil.getJSONObject(URL, params));
			if (null != grades) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						saveGrades(grades);
					}
				}).start();
			}
		}
		
		return grades;
	}

	@SuppressWarnings("finally")
	private List<Grade> getGradeListByJson(JSONObject jsonResult) {
		
		List<Grade> gradeList = null;
		try {
			String errorString = jsonResult.getString("error");
			if (null != errorString && "null".equals(errorString)) {

				gradeList = new ArrayList<Grade>();
				String gradeinfo = jsonResult.getString("grade");
				JSONArray jsonArray = new JSONArray(gradeinfo); 
				for(int i=0; i < jsonArray.length(); i++){
					JSONObject gradeJson = jsonArray.getJSONObject(i);
					Grade grade = new Grade();
					grade.setYear(gradeJson.getString(Grade.YEAR));
					grade.setTerm(gradeJson.getString(Grade.TERM));
					grade.setPoint(gradeJson.getString(Grade.POINT));
					grade.setGrade(gradeJson.getString(Grade.GRADE));
					grade.setGeneral_grade(gradeJson.getString(Grade.GENERAL_GRADE));
					grade.setPaper_grade(gradeJson.getString(Grade.PAPER_GRADE));
					grade.setExperiment_grade(gradeJson.getString(Grade.EXP_GRADE));
					grade.setMakeup_grade(gradeJson.getString(Grade.MAKEUP_GRADE));
					grade.setRebuilt_grade(gradeJson.getString(Grade.REBUILT_GRADE));
					grade.setRebuilt_sign(gradeJson.getString(Grade.REBUILT_SIGN));
					grade.setState(gradeJson.getString(Grade.STATE));
					grade.setName(gradeJson.getString(Grade.COURSE_NAME));
					grade.setNature(gradeJson.getString(Grade.NATURE));
					grade.setAttribution(gradeJson.getString(Grade.ATTRIBUTION));
					grade.setCredit(gradeJson.getString(Grade.CREDIT));
					grade.setCollege_name(gradeJson.getString(Grade.COLLEGE_NAME));
					
					gradeList.add(grade);
				}
			}else if("Empty".equals(errorString)){
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			return gradeList;
		}
	}

	public List<Grade> getGradeList() {
	    ArrayList<Grade> gradeList = new ArrayList<Grade>();
	    SQLiteDatabase database = dbHelper.getReadableDatabase();
	    String sql = "select * from "+ Grade.TABLE_NAME + " where stu_id=?";
	    
	    Log.i(TAG, sql);
	    
	    String[] params = new String[1];
	    String userId = MainService.nowUser.getUserId();
	    params[0] = userId;
	    Cursor cursor = database.rawQuery(sql, params);
	    
	    while (cursor.moveToNext()) {
	    	Grade grade = new Grade();
	    	grade.setYear(cursor.getString(cursor.getColumnIndex(Grade.YEAR)));
	    	grade.setTerm(cursor.getString(cursor.getColumnIndex(Grade.TERM)));
	    	grade.setPoint(cursor.getString(cursor.getColumnIndex(Grade.POINT)));
	    	grade.setGrade(cursor.getString(cursor.getColumnIndex(Grade.GRADE)));
	    	grade.setGeneral_grade(cursor.getString(cursor.getColumnIndex(Grade.GENERAL_GRADE)));
	    	grade.setPaper_grade(cursor.getString(cursor.getColumnIndex(Grade.PAPER_GRADE)));
	    	grade.setExperiment_grade(cursor.getString(cursor.getColumnIndex(Grade.EXP_GRADE)));
	    	grade.setMakeup_grade(cursor.getString(cursor.getColumnIndex(Grade.MAKEUP_GRADE)));
	    	grade.setRebuilt_grade(cursor.getString(cursor.getColumnIndex(Grade.REBUILT_GRADE)));
	    	grade.setRebuilt_sign(cursor.getString(cursor.getColumnIndex(Grade.REBUILT_SIGN)));
	    	grade.setState(cursor.getString(cursor.getColumnIndex(Grade.STATE)));
	    	grade.setName(cursor.getString(cursor.getColumnIndex(Grade.COURSE_NAME)));
	    	grade.setNature(cursor.getString(cursor.getColumnIndex(Grade.NATURE)));
	    	grade.setAttribution(cursor.getString(cursor.getColumnIndex(Grade.ATTRIBUTION)));
	    	grade.setCredit(cursor.getString(cursor.getColumnIndex(Grade.CREDIT)));
	    	grade.setCollege_name(cursor.getString(cursor.getColumnIndex(Grade.COLLEGE_NAME)));
	    	gradeList.add(grade);
	    }
	    database.close();
	    return gradeList;
	}
	
	

	public void saveGrades(List<Grade> gradeList) {
		for (Grade grade : gradeList ) {
			insertGrade(grade);
		}
	}
	
	public long insertGrade(Grade grade) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		
		String sql = "select * from "+Grade.TABLE_NAME+" where "+Grade.COURSE_NAME+"=? and stu_id=?";
		
		Cursor cursor = database.rawQuery(sql, new String[]{grade.getName(),MainService.nowUser.getUserId()});
		long rowId = -1;
		
		if (cursor.getCount() <= 0) {
			ContentValues localContentValues = new ContentValues();
			localContentValues.put(Grade.YEAR,grade.getYear());
			localContentValues.put(Grade.TERM, grade.getTerm());
			localContentValues.put(Grade.POINT, grade.getPoint());
			localContentValues.put(Grade.GRADE,grade.getGrade());
			localContentValues.put(Grade.GENERAL_GRADE, grade.getGeneral_grade());
			localContentValues.put(Grade.PAPER_GRADE, grade.getPaper_grade());
			localContentValues.put(Grade.EXP_GRADE, grade.getExperiment_grade());
			localContentValues.put(Grade.MAKEUP_GRADE,grade.getMakeup_grade());
			localContentValues.put(Grade.REBUILT_GRADE, grade.getRebuilt_grade());
			localContentValues.put(Grade.REBUILT_SIGN, grade.getRebuilt_sign());
			localContentValues.put(Grade.STATE, grade.getState());
			localContentValues.put(Grade.COURSE_NAME, grade.getName());
			localContentValues.put(Grade.NATURE, grade.getNature());
			localContentValues.put(Grade.ATTRIBUTION, grade.getAttribution());
			localContentValues.put(Grade.CREDIT, grade.getCredit());
			localContentValues.put(Grade.COLLEGE_NAME, grade.getCollege_name());
			localContentValues.put("stu_id", MainService.nowUser.getUserId());
			
			rowId = database.insert(Grade.TABLE_NAME, null, localContentValues);
		}
		database.close();
		return rowId;
	}
	
}

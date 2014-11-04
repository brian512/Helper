package cn.edu.wit.withelper.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Express {

	private String expressID ;
	private String name ;
	private String order ;
	private String num ;
	private String updateTime ;
	private String message ;
	private String errCode ;
	private String status ;
	private List<ExpressContentData> contentList = null ;
	
	public static String EXPRESSNAME = "expressName" ;
	public static String EXPRESSID = "expressID" ;
	public static String ORDERID = "orderID" ;
	
	public static Map<String, String> expressCode = null;
	public static List<String> expressNames ;
	
	static{
		expressCode = new HashMap<String, String>();
		expressCode.put("顺丰快递", "shunfeng");
		expressCode.put("申通快递", "shentong");
		expressCode.put("圆通快递", "yuantong");
		expressCode.put("韵达快递", "yunda");
		expressCode.put("中通快递", "zhongtong");
		expressCode.put("天天快递", "tiantian");
		expressCode.put("汇通快递", "huitong");
		expressCode.put("EMS快递", "ems");
		expressCode.put("国通快递", "guotong");
		expressCode.put("如风达快递", "rufengda");
		expressCode.put("宅急送快递", "zjs");
		
		expressNames = new ArrayList<String>();
		expressNames.add("顺丰快递");
		expressNames.add("申通快递");
		expressNames.add("圆通快递");
		expressNames.add("韵达快递");
		expressNames.add("中通快递");
		expressNames.add("天天快递");
		expressNames.add("汇通快递");
		expressNames.add("EMS快递");
		expressNames.add("国通快递");
		expressNames.add("如风达快递");
		expressNames.add("宅急送快递");
	}
	
	
	public Express() {
		
		
	}
	
	public String getExpressID() {
		return expressID;
	}
	public void setExpressID(String expressID) {
		this.expressID = expressID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ExpressContentData> getContentList() {
		return contentList;
	}
	public void setContentList(List<ExpressContentData> contentList) {
		this.contentList = contentList;
	}
	
	
}

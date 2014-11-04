package cn.edu.wit.withelper.bean;

public class BorrowBook {

	public static final String TB_NAME = "tb_borrowbook";
	public static final String ID = "_id";
	public static final String BOOKNAME = "bookName";
	public static final String BORROW_DATE = "borrow_date";
	public static final String RETURN_DATE = "return_date";
	public static final String RENEW_COUNT = "renew_count";
	public static final String AREA = "area";
	public static final String STU_ID = "stu_id";
	
	
	private String bookName ;
	private String borrow_date ;
	private String return_date ;
	private String renew_count ;
	private String area ;
	
	@Override
	public String toString() {
		return "书名："+bookName+"\n应还日期："+return_date+"\n\n";
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBorrow_date() {
		return borrow_date;
	}
	public void setBorrow_date(String borrow_date) {
		this.borrow_date = borrow_date;
	}
	public String getReturn_date() {
		return return_date;
	}
	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}
	public String getRenew_count() {
		return renew_count;
	}
	public void setRenew_count(String renew_count) {
		this.renew_count = renew_count;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
	
}

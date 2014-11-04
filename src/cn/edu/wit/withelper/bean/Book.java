package cn.edu.wit.withelper.bean;

public class Book {

	public static final String TB_NAME = "tb_book";
	public static final String ID = "_id";
	public static final String NAME = "name";
	public static final String AUTHOR = "author";
	public static final String PRESS = "press";
	public static final String BOOKID = "bookID";
	public static final String COLLECTIONS = "collections";
	public static final String LEND_COUNT = "lend_count";
	public static final String LEND_RATIO = "lend_ratio";
	public static final String CLASSNUM = "classNum";
	
	private String name ;
	private String author ;
	private String press ;
	private String bookID ;
	private String collections ;
	private String lend_count ;
	private String lend_ratio ;
	private String classNum ;
	
	@Override
	public String toString() {
		return "书名："+name+"\n作者："+author+"\n出版社："+press+"\n索书号："+bookID;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getbookID() {
		return bookID;
	}
	public void setbookID(String bookID) {
		this.bookID = bookID;
	}
	public String getCollections() {
		return collections;
	}
	public void setCollections(String collections) {
		this.collections = collections;
	}
	public String getLend_count() {
		return lend_count;
	}
	public void setLend_count(String lend_count) {
		this.lend_count = lend_count;
	}
	public String getLend_ratio() {
		return lend_ratio;
	}
	public void setLend_ratio(String lend_ratio) {
		this.lend_ratio = lend_ratio;
	}


	public String getClassNum() {
		return classNum;
	}


	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	
	
	
}

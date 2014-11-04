package cn.edu.wit.withelper.bean;

public class SearchBook {

	private String title = null;
	private String marc_no = null;
	private String author = null;
	private String press = null;
	private String callno = null;
	private String type = null;
	
	
	@Override
	public String toString() {
		return "标题："+title+"\n作者："+author+"\n出版社："+press+"\n\n";
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMarc_no() {
		return marc_no;
	}
	public void setMarc_no(String marc_no) {
		this.marc_no = marc_no;
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
	public String getCallno() {
		return callno;
	}
	public void setCallno(String callno) {
		this.callno = callno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}

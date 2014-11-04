package cn.edu.wit.withelper.bean;

import java.io.Serializable;

import android.graphics.drawable.Drawable;


public class News implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String brief ;
	private String time;
	private String source ;
	private String content ;
	private String url ;
	private Drawable image;
	
	public static final String TABLE_NAME = "tb_news";
	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String BRIEF = "brief";
	public static final String TIME = "time";
	public static final String SOURCE = "source";
	public static final String CONTENT = "content";
	public static final String URL = "url";
	public static final String IMAGE = "image";
	
	public News(){}
	
	public News(String title, String brief, String source, String time, String content){
		this.title = title;
		this.brief = brief;
		this.source = source;
		this.time = time;
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "标题："+ title + 
				"\n时间"+time + 
				"\n来源:"+source+
				"\n简介："+brief + 
				"\n内容:"+ content;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Drawable getImage() {
		return image;
	}
	public void setImage(Drawable image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}

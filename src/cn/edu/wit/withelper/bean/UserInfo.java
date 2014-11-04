package cn.edu.wit.withelper.bean;

public class UserInfo {

	private Long id;
	private String userId;
	private String userName;
	private String nickName ;
	private String className ;
	private String college ;
	private String sessionId;
	private String password;
	private String libraryPassword;
	private String sex;
	private String IDCard ;
	private int iconIndex = -1;

	public static final String TB_NAME = "tb_user";

	public static final String ID = "_id";
	public static final String USERID = "userID";
	public static final String USERNAME = "name";
	public static final String NICKNAME = "nickName";
	public static final String CLASSNAME = "className";
	public static final String COLLEGE = "college";
	public static final String SESSIONID = "sessionID";
	public static final String PASSWORD = "password";
	public static final String LIBRARYPASSWORD = "libraryPassword";
	public static final String SEX = "sex";
	public static final String ICONINDEX = "iconIndex";
	
	
	public static final String IDCARD = "idcard";
	
	public UserInfo(){}
	
	public  UserInfo(String userId, String sessionId) {
		this.userId = userId;
		this.sessionId = sessionId;
	}
	
	public  UserInfo(String userId, String sessionId,String password) {
		this.userId = userId;
		this.sessionId = sessionId;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "学号："+userId + 
				"\n姓名："+ userName + 
				"\n班级：" + className;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getIconIndex() {
		return iconIndex;
	}

	public void setIconIndex(int iconIndex) {
		this.iconIndex = iconIndex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}

	public String getLibraryPassword() {
		return libraryPassword;
	}

	public void setLibraryPassword(String libraryPassword) {
		this.libraryPassword = libraryPassword;
	}

}

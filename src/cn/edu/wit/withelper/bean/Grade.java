package cn.edu.wit.withelper.bean;

public class Grade {
	
	public static final String TABLE_NAME = "tb_grade";
	public static final String ID = "_id";
	public static final String YEAR = "year";
	public static final String TERM = "term";
	public static final String POINT = "point";
	public static final String GRADE = "grade";
	public static final String GENERAL_GRADE = "general_grade";
	public static final String PAPER_GRADE = "paper_grade";
	public static final String EXP_GRADE = "experiment_grade";
	public static final String MAKEUP_GRADE = "makeup_grade";
	public static final String REBUILT_GRADE = "rebuilt_grade";
	public static final String REBUILT_SIGN = "rebuilt_sign";
	public static final String STATE = "state";
	public static final String COURSE_NAME = "name";
	public static final String NATURE = "nature";
	public static final String ATTRIBUTION = "attribution";
	public static final String CREDIT = "credit";
	public static final String COLLEGE_NAME = "college_name";

	private String year;
	private String term;
	private String point;
	private String grade;
	private String general_grade;
	private String paper_grade;
	private String experiment_grade;
	private String makeup_grade;
	private String rebuilt_grade;
	private String rebuilt_sign;
	private String state;
	private String name;
	private String nature;
	private String attribution;
	private String credit;
	private String college_name;

	public Grade() {
	}

	public Grade(String paramString1, String paramString2, String paramString3,
			String paramString4, String paramString5, String paramString6,
			String paramString7, String paramString8, String paramString9,
			String paramString10, String paramString11, String paramString12,
			String paramString13, String paramString14, String paramString15,
			String paramString16) {
		this.year = paramString1;
		this.term = paramString2;
		this.point = paramString3;
		this.grade = paramString4;
		this.general_grade = paramString5;
		this.paper_grade = paramString6;
		this.experiment_grade = paramString7;
		this.makeup_grade = paramString8;
		this.rebuilt_grade = paramString9;
		this.rebuilt_sign = paramString10;
		this.state = paramString11;
		this.name = paramString12;
		this.nature = paramString13;
		this.attribution = paramString14;
		this.credit = paramString15;
		this.college_name = paramString16;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGeneral_grade() {
		return general_grade;
	}

	public void setGeneral_grade(String general_grade) {
		this.general_grade = general_grade;
	}

	public String getPaper_grade() {
		return paper_grade;
	}

	public void setPaper_grade(String paper_grade) {
		this.paper_grade = paper_grade;
	}

	public String getExperiment_grade() {
		return experiment_grade;
	}

	public void setExperiment_grade(String experiment_grade) {
		this.experiment_grade = experiment_grade;
	}

	public String getMakeup_grade() {
		return makeup_grade;
	}

	public void setMakeup_grade(String makeup_grade) {
		this.makeup_grade = makeup_grade;
	}

	public String getRebuilt_grade() {
		return rebuilt_grade;
	}

	public void setRebuilt_grade(String rebuilt_grade) {
		this.rebuilt_grade = rebuilt_grade;
	}

	public String getRebuilt_sign() {
		return rebuilt_sign;
	}

	public void setRebuilt_sign(String rebuilt_sign) {
		this.rebuilt_sign = rebuilt_sign;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	
}

/*
 * Location: D:\Java\apk2java\appData\classes-dex2jar.jar Qualified Name:
 * com.wang.domain.Grade JD-Core Version: 0.5.4
 */
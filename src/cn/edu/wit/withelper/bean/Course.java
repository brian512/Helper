package cn.edu.wit.withelper.bean;

public class Course {

	private String courseId ;
	private String courseName ;
	private String teacher ;
	private String time ;
	private String startWeek ;
	private String endWeek ;
	private String classRoom ;
	private String userId ;
	
	public static final String TABLE_NAME = "tb_course";
	public static final String ID = "_id";
	public static final String COURSE_ID = "courseid";
	public static final String COURSE_NAME = "coursename";
	public static final String TEATCH = "teacher";
	public static final String TIME = "time";
	public static final String STARTWEEK = "startweek";
	public static final String ENDWEEK = "endweek";
	public static final String CLASSROOM = "classroom";
	
	public Course(){}
	
	public Course(String courseId, String courseName, String teacher, String time, String startWeek, String endWeek, String classRoom, String userId){
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacher = teacher;
		this.time = time;
		this.startWeek = startWeek;
		this.endWeek = endWeek;
		this.classRoom = classRoom;
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return courseName + "  " + teacher 
				+ "\n" + startWeek + "-" + endWeek 
				+ "  " + classRoom;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}

	public String getEndWeek() {
		return endWeek;
	}

	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}

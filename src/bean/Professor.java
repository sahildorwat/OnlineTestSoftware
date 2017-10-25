package bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

import queries.QueriesRunner;

public class Professor {
	Integer id;
	String name;
	String user_id;
	String password;
	List<Course> courses;
	public Professor(){
		this.id=null;
		this.name=null;
		this.user_id=null;
		this.password=null;
		this.courses=new ArrayList<Course>();
	}
	
	static Scanner sc=new Scanner(System.in);
	static QueriesRunner qr = QueriesRunner.getInstance();
	public  void loginAsProfessor(ResultSet rs){
		try {
			this.id=rs.getInt("id");
			this.name=rs.getString("name");
			this.user_id=rs.getString("user_id");
			this.password=rs.getString("password");
			showOptions();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void showOptions(){
		System.out.println("1.View Profile");
		System.out.println("2.View/Add​ ​Courses");
		System.out.println("3.Enroll/Drop A Student");
		System.out.println("4.View Report");
		System.out.println("5.Setup TA");
		System.out.println("6.View/Add​ ​Exercises");
		System.out.println("7.Search/Add​ ​questions​ ​to​ ​Question​ ​Bank");
		System.out.println("8.Add/Remove Questions from Exercises");
		Integer option =sc.nextInt();
		
		while(true){
			if(option==1){
				viewProfile();
				break;
			}else if(option==2) {
				viewAddCourses();
				break;
			}else if(option==3){
				break;
			}else if(option==4){
				break;
			}else if(option==5){
				break;
			}else if(option==6){
				break;
			}else if(option==7){
				break;
			}else if(option==8){
				break;
			}
		}
	}
	
	public void viewAddCourses(){
		System.out.println("1.Search by course");
		System.out.println("2.Add Courses");
		Integer option =sc.nextInt();
		if(option==1){
			searchByCourse();
		}else{
			
		}
	}
	//select * from courses,exercise_mapping,exercises where courses.id = exercise_mapping.course_id and exercise_mapping.exercise_id = exercises.id
	public void searchByCourse(){
		System.out.println("Enter the course to be searched:(Course_id)");
		String option =sc.next();
		ResultSet ws= qr.selectQueries("select c.id as id,c.name as c_name,e.name as e_name from courses c,exercise_mapping em,exercises e where c.id = em.course_id and em.exercise_id = e.id and c.id='"+option+"'");
		try {

			while(ws.next()){
//				Course temp=new Course();
				String id=ws.getString("id");
				System.out.println(id);
				String name=ws.getString("c_name");
				System.out.println(name);
				String ename=ws.getString("e_name");
				System.out.println(ename);
//				this.courses.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCourse(){
		
		
		
	}
	public void viewProfile(){
		System.out.println(this);
		getCoursesForProfessor();
		
	}
	public void getCoursesForProfessor(){
		ResultSet ss= qr.selectQueries("select t.course_id as course_id,c.name as name,t.start_date as start_date,t.end_date as end_date from teaches t,courses c where t.course_id=c.id and professor_id="+id);
		System.out.println();
		try {
			
			while(ss.next()){
				Course temp=new Course();
//				Integer profesor_id=ss.getInt("professor_id");
//				System.out.print(profesor_id);
				
				temp.id=ss.getString("course_id");
				temp.name=ss.getString("name");
//				System.out.println(ss.getTimestamp("start_date"));
				temp.start_date=(Date)ss.getTimestamp("start_date");
//				System.out.println(start_date);
				temp.end_date=(Date)ss.getTimestamp("end_date");
				System.out.println(temp);
				this.courses.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", name=" + name + ", user_id="
				+ user_id + ", password=" + password + "]";
	}
	
}

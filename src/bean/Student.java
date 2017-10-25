package bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import connection.GdConnection;
import queries.QueriesRunner;

public class Student {
	String lvl;
	Integer id;
	String name;
	String user_id;
	String password;
	
	static QueriesRunner qr = QueriesRunner.getInstance();
	static Scanner sc = new Scanner(System.in);
	
	public void loginAsStudent(ResultSet rs){
		try {
			this.id=rs.getInt("id");
			this.lvl=rs.getString("lvl");
			this.name=rs.getString("name");
			this.user_id=rs.getString("user_id");
			this.password=rs.getString("password");
			
			while(true) {
				System.out.println("Welcome " + this.name);
				System.out.println();
				System.out.println("1. View/Edit Profile");
				System.out.println("2. View Courses");
				System.out.println("3. Logout");
				System.out.print("Enter Choice: ");
				
				String option = sc.next();
				System.out.println();
				
				switch(option) {
					case "1":	this.viewProfile(); break;
					case "2":	this.viewCourses(); break;
					case "3":	return;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void viewProfile() {
		String option = "", last_name = "";
		String[] full_name = this.name.split("\\s", 2);
		System.out.println("Press 0 to Go Back");
		System.out.println("1. First Name: " + full_name[0]);
		if(full_name.length == 2) {
			last_name = full_name[1];
		}
		System.out.println("2. Last Name: " + last_name);
		System.out.println("3. Student ID: " + this.user_id);
		if(option == "0") {
			
		}
	}
	
	public void viewCourses() {
		ResultSet rs = null;
		rs = qr.selectQueries("select * from courses c, enrollment e where c.id=e.course_id and e.student_id="+this.id);
		try {
			int no = 0;
			System.out.println("List of Current Courses: ");
			while(rs.next()) {
				no++;
				System.out.println(no + ". " + rs.getString("id") + " - " + rs.getString("name"));
			}
			GdConnection.close(rs);
			System.out.println("Please Provide Course ID: ");
			System.out.println("Press 0 to Go Back to Previous Menu");
			String option = sc.next();
			
			if(option == "0")
				return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	@Override
	public String toString() {
		return "Student [lvl=" + lvl + ", id=" + id + ", name=" + name
				+ ", user_id=" + user_id + ", password=" + password + "]";
	}
	
	
	 
}

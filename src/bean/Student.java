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
				

				int option = sc.nextInt();
				System.out.println();

				
				switch(option) {
					case 1:	this.viewProfile(); break;
					case 2:	this.viewCourses(); break;
					case 3:	return;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void viewProfile() {
		String option, last_name = "";
		ResultSet rs = null;
		String[] full_name = this.name.split("\\s", 2);
		System.out.println();
		System.out.println("Press 0 to Go Back");
		System.out.println("1. First Name: " + full_name[0]);
		if(full_name.length == 2) {
			last_name = full_name[1];
		}
		System.out.println("2. Last Name: " + last_name);
		System.out.println("3. Student ID: " + this.user_id);
		System.out.print("Enter Choice: ");
		int op = sc.nextInt();
		if(op == 1) {
			System.out.println("Enter new First Name: ");
			option = sc.next();
			rs = qr.selectQueries("update students set name='" + option + " " + last_name + "' where id=" + this.id);
			try {
				if(rs.next()) {//since its a update query
					this.name = option + " " + last_name;
					viewProfile();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(op == 2) {
			System.out.println("Enter new Last Name: ");
			option = sc.next();
			rs = qr.selectQueries("update students set name='" + full_name[0] + " " + option + "' where id=" + this.id);
			try {
				if(rs.next()) {//since its a update query
					this.name = full_name[0] + " " + option;
					viewProfile();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(op == 3) {
			System.out.println("Can't edit Student ID");
			viewProfile();
		} else if(op == 0) {
			return;
		} else {
			System.out.println("Illegal operation");
			viewProfile();
		}
	}
	
	public void viewCourses() {
		ResultSet rs = null;
		Exercise ex = new Exercise();
		while(true) {
			rs = qr.selectQueries("select * from courses c, enrollment e where c.id=e.course_id and e.student_id="+this.id);
			try {
				int no = 0;
				System.out.println();	
				System.out.println("List of Current Courses: ");
				while(rs.next()) {
					no++;
					System.out.println(no + ". " + rs.getString("id") + " - " + rs.getString("name"));
				}
				
				if(no == 0) {
					System.out.println("Not enrolled in any course!");
					return;
				}
				
				System.out.println("Please Provide Course ID (eg. CSCxxx): ");
				System.out.println("Press 0 to Go Back to Previous Menu");
				String option = sc.next();
				
				if(option.equals("0"))
					return;
				else {
					option = option.toUpperCase();
					rs = qr.selectQueries("select * from exercise_mapping where course_id='" + option + "'");
					if(rs.next()) {
						ex.showHomeworkMenu(rs, id);
					} else {
						rs = qr.selectQueries("select * from courses where id='" + option + "'");
						if(rs.next()) {
							System.out.println("No homework exists for " + option);
						} else {
							System.out.println("Invalid Course ID, Check formatting!");
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GdConnection.close(rs);
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

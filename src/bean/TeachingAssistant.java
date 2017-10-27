package bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import queries.QueriesRunner;

public class TeachingAssistant extends Student{
	static QueriesRunner qr = QueriesRunner.getInstance();
	static Scanner sc = new Scanner(System.in);
	
	public void loginAsTeachingAssistant(ResultSet rs){
		try {
			this.id=rs.getInt("id");
			this.lvl=rs.getString("lvl");
			this.name=rs.getString("name");
			this.user_id=rs.getString("user_id");
			this.password=rs.getString("password");
			while(true) {
				System.out.println("Welcome " + this.name);
				System.out.println();
				System.out.println("1. View Profile");
				System.out.println("2. View Courses");
				System.out.println("3. Enroll/Drop a student");
				System.out.println("4. Logout");
				System.out.print("Enter Choice: ");				

				int option = sc.nextInt();
				System.out.println();
				
				while(true){
					if(option==1){
						viewProfile();
						break;
					}else if(option==2) {
						viewCourses();
						break;
					}else if(option==3) {
						enrollordrop();
						break;
					}else if(option==4){
						return;
					}
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
	
	public void enrollordrop(){
		int flag=0;
		while(true){
			System.out.println("1.Enroll a student");
			System.out.println("2.Drop a student");
			System.out.println("3.Exit");
			Integer option=sc.nextInt();
			switch(option){
				case 1:try {
					enrollStudent();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}break;
				case 2:dropStudent();break;
				case 3:flag=1;break;
			}
			if(flag==1) break;
		}
	}
	
	public void enrollStudent() throws SQLException{
		System.out.println("Please provide course_id:");
		String course_id=sc.next();
		Student stud = new Student();
		System.out.println("1.New Student\n2.Available Student");
		Integer option=sc.nextInt();
		if(option==2){
			System.out.println("Please enter id of the student:");
			Integer id=sc.nextInt();
			qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_id+"',"+id+")");
		}else{
			System.out.println("Please enter level, name,user_id, password");
			stud.lvl=sc.next();
			stud.name=sc.next();
			stud.user_id=sc.next();
			stud.password=sc.next();
			ResultSet ws= qr.selectQueries("select max(id) as id from students");
			if(ws.next()){
				stud.id=ws.getInt("id")+1;
				qr.updateQueries("INSERT INTO STUDENTS VALUES("+stud.id+",'"+stud.lvl+"','"+stud.name+"','"+stud.user_id+"','"+stud.password+"')");
				qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_id+"',"+stud.id+")");
			}
		}
	}
	public void dropStudent(){
		System.out.println("Please provide the id of student to be deleted:");
		Integer id=sc.nextInt();
		qr.updateQueries("DELETE FROM STUDENTS WHERE ID="+id+"");
	}
	
	public void viewReport() {
		
	}
	
	public void viewCourseMenu(String id, String name) {
		System.out.println();
		System.out.println("Course: " + id + " - " + name);
		System.out.println("Start Date: ");
		System.out.println("End Date: ");
		System.out.println("End Date: ");
		System.out.println("1. View Report: ");
		System.out.println("2. View Homeworks: ");
		System.out.println("Press 0 to Go Back to Previous Menu");
		System.out.print("Enter choice: ");
		String option = sc.next();
		
		if(option.equals("0"))
			return;
	}
	
	public void viewCourses() {
		ResultSet rs = null;
		while(true) {
			rs = qr.selectQueries("select c.course_id, cr.name from courses_to_ta c, courses cr where cr.id=c.course_id and ta_id="+this.id);
			try {
				int no = 0;
				System.out.println();
				System.out.println("List of Courses as TA: ");
				while(rs.next()) {
					no++;
					System.out.println(no + ". " + rs.getString("course_id") + " - " + rs.getString("name"));
				}
				System.out.println("Please Provide Course ID (eg. CSCxxx): ");
				System.out.println("Press 0 to Go Back to Previous Menu");
				String option = sc.next();
				
				if(option.equals("0"))
					return;
				else {
					viewCourseMenu(rs.getString("course_id"), rs.getString("name"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

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
		
	}
	
	public void viewReport() {
		
	}
	
	public void viewCourses() {
		System.out.println();
		System.out.println("Courses as TA");
		
	}
}

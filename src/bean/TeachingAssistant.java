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

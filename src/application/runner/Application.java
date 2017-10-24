package application.runner;
import java.sql.ResultSet;
import java.sql.SQLException;
import  java.util.Scanner;

import connection.GdConnection;

import bean.Professor;
import bean.Student;
import bean.TeachingAssistant;


import queries.QueriesRunner;
public class Application {
	static QueriesRunner qr = QueriesRunner.getInstance();
	static Scanner sc=new Scanner(System.in);
	public static void clearScreen() {  
//		System.out.print("\033[H\033[2J");  
		System.out.flush();

	}
	public static void login() throws SQLException{
		ResultSet rs = null;
		ResultSet ss = null;
		ResultSet ws = null;
		System.out.println("Enter Username");
		String user_id=sc.next();
		System.out.println("Enter password");
		String password=sc.next();
		Student stud =new Student();
		TeachingAssistant ta =new TeachingAssistant();
		Professor prof=new Professor();
		
		rs= qr.selectQueries("select * from students where user_id='"+user_id+"' and password='"+password+"'");
		System.out.println(rs);
		if(!rs.next()){
			
			ss=qr.selectQueries("select * from professors where user_id='"+user_id+"' and password='"+password+"'");
			if(!ss.next()){
				clearScreen(); 
				System.out.println("Incorrect Username ,password");
				mainpage();
			}else{
				System.out.println("Welcome Prof:"+rs);
				prof.loginAsProfessor(ss);
				GdConnection.close(ss);
				
				System.out.println(prof);
			}
			
		}else{
			int id=100;
			try {
				id = rs.getInt("id");
				GdConnection.close(rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ws=qr.selectQueries("select * from courses_to_ta where ta_id='"+id+"'");
			if(ws.next()){
//				GdConnection.close(ws);
				stud.loginAsStudent(ws);
				GdConnection.close(ws);
			}else{
				System.out.println("1.login as Student ");
				System.out.println("2.login as TA");
				int option=sc.nextInt();
				if(option == 1){
					stud.loginAsStudent(rs);
				}else{
					ta.loginAsTeachingAssistant(rs);
				}
			}
			
		}
		
	}
	public static void mainpage() throws SQLException{
		while(true){
			System.out.println("StartMenu:");
			System.out.println("1. Login");
			System.out.println("2. Exit");
			System.out.println("Enter input:");

			int scan=sc.nextInt();
			switch(scan){
			case 1: 
				clearScreen();
				System.out.println("Login");
				login();

			case 2:
				return;
			}
		}
	}
	public static void main(String[] args) throws SQLException {
		mainpage();
		qr.closeConnection();
	}

}


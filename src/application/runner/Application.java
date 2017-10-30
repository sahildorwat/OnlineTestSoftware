package application.runner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import  java.util.Scanner;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import connection.GdConnection;
import bean.Professor;
import bean.Student;
import bean.TeachingAssistant;


import queries.QueriesRunner;
public class Application extends JFrame{
	/**
	 * 
	 */
	private JPanel content_pane = null;
	final JTextField user_tf = new JTextField(10);
	final JTextField pwd_tf = new JTextField(10);
	JLabel error_label = new JLabel("");
	private static final long serialVersionUID = 1L;
	static QueriesRunner qr = QueriesRunner.getInstance();
	static Scanner sc=new Scanner(System.in);
	public static void clearScreen() {  
//		System.out.print("\033[H\033[2J");  
		System.out.flush();

	}
	public void login() throws SQLException, ParseException{
		ResultSet rs = null;
		ResultSet ss = null;
		ResultSet ws = null;
		
		System.out.println("Enter Username");
		//String user_id=sc.next();
		String user_id = user_tf.getText();
		System.out.println("Enter password");
		//String password=sc.next();
		String password = pwd_tf.getText();
		
		Student stud =new Student();
		TeachingAssistant ta =new TeachingAssistant();
		Professor prof=new Professor();
		
		//rs = qr.selectQueries("select * from students where user_id='"+user_id+"' and password='"+password+"'");
		ss = qr.selectQueries("select * from professors where user_id='"+user_id+"' and password='"+password+"'");
		if(ss.next()){
			//professor login
			System.out.println("Welcome Prof:"+ ss);
			prof.setVisible(true);
			dispose();
			prof.loginAsProfessor(ss);
			System.out.println(prof);
			GdConnection.close(ss);
		}
		else{
			//check for TA
			ws=qr.selectQueries("select * from students s, courses_to_ta c where s.id = c.ta_id and user_id='"+user_id
					+"' and password='"+password+"'");
			if(ws.next()) {
				// if TA logs in
				System.out.println("1.login as Student ");
				System.out.println("2.login as TA");
				int option=sc.nextInt();
				if(option == 1){
					stud.setVisible(true);
					dispose();
					stud.loginAsStudent(ws);
					System.out.println(stud);
				}else if(option == 2){
					ta.setVisible(true);
					dispose();
					ta.loginAsTeachingAssistant(ws);
					System.out.println(ta);
				}
				GdConnection.close(ws);
			} else {
				//Check for Student
				rs = qr.selectQueries("select * from students where user_id='"+user_id+"' and password='"+password+"'");
				if(rs.next()) {
					//Student logs in
					stud.loginAsStudent(rs);
					System.out.println(stud);
				} else {
					clearScreen();
					System.out.println("Incorrect Username, Password");
					mainpage();
				}
			}
			GdConnection.close(rs);
		}
		
	}
	public void mainpage() throws SQLException, ParseException{
		
		if(!(content_pane == null))
		{
			user_tf.setText("");
			pwd_tf.setText("");
			error_label.setText("Invalid login details");
			content_pane.removeAll();
		}
		
		setLocation(800, 600);
		setPreferredSize(new Dimension(1024,768));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		content_pane = new JPanel(new GridLayout(4,1));
		JLabel lbl= new JLabel("StartMenu : ");
		
		JLabel username_label = new JLabel("Username : ");
		JLabel pwd_label = new JLabel("Password : ");
			
		JButton btn = new JButton("LOGIN");
		btn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			clearScreen();
			System.out.println("Login");
			try {
				login();
			} catch (SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;
		
		JButton btn2 = new JButton("EXIT");
		btn2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			System.out.println("Exit");
			qr.closeConnection();
			dispose();
			return;
		}});;
		
		
		content_pane.add(lbl);
		content_pane.add(error_label);
		content_pane.add(username_label);
		content_pane.add(user_tf);
		content_pane.add(pwd_label);
		content_pane.add(pwd_tf);
		content_pane.add(btn);
		content_pane.add(btn2);
		getContentPane().add(content_pane);
		pack();
		/*while(true){
			
			
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
*/	}
	public static void main(String[] args) throws SQLException, ParseException {
		Application app = new Application();
		app.setVisible(true);
		app.mainpage();
		//qr.closeConnection();
	}

}


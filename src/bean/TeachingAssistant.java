package bean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import queries.QueriesRunner;

public class TeachingAssistant extends Student{
	String lvl;
	Integer id;
	String name;
	String user_id;
	String password;
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
		System.out.println("Please provide course_id:");
		String course_id=sc.next();
		int flag=0;
		ResultSet ws= qr.selectQueries("select e.scoring_policy_id as sp_id,e.id as e_id,e.name as name from exercises e,exercise_mapping em where e.id=em.exercise_id and em.course_id='"+course_id+"'");
		ArrayList<Exercise> listExercise=new ArrayList<Exercise>();
		try {
			while(ws.next()){
				Exercise exer=new Exercise();
				exer.scoring_policy_id=ws.getInt("sp_id");
				exer.id=ws.getInt("e_id");
				exer.name=ws.getString("name");
				listExercise.add(exer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Exercise exer :listExercise)
			{
			ResultSet rs= qr.selectQueries("select e.student_id,s.name as name from enrollment e,students s where e.student_id=s.id and course_id='"+course_id+"'");
			ArrayList<Student> listStudent=new ArrayList<Student>();
			try {
				while(rs.next()){
					Student stud=new Student();
					stud.id=rs.getInt("student_id");
					stud.name=rs.getString("name");
					listStudent.add(stud);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag=1;
			for(Student stud :listStudent)
			{
				if(exer.scoring_policy_id==1){
					ResultSet ss= qr.selectQueries("select score from attempts where id=(select max(attempt_id) from student_attempts_exercises where student_id='"+stud.id+"'and exercise_id="+exer.id+")");
					try {
						if(ss.next()){
							Integer max_score =ss.getInt("score");
							System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"  "+max_score);
						}
						else{
							System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"   0");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(exer.scoring_policy_id==2){
					ResultSet qz=qr.selectQueries("select max(score) as score from student_attempts_exercises s, attempts a where s.student_id ="+stud.id+" and s.exercise_id ="+exer.id+" and s.attempt_id = a.id group by s.exercise_id, s.student_id");
					try {
						if(qz.next()){
							Integer max_score =qz.getInt("score");
							System.out.print(stud.id+"  "+stud.name+"  "+exer.name+"  "+max_score);
						}
						else{
							System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"   0");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					ResultSet qw=qr.selectQueries("select avg(score) as score from student_attempts_exercises s, attempts a where s.student_id ="+stud.id+" and s.exercise_id ="+exer.id+" and s.attempt_id = a.id group by s.exercise_id, s.student_id");
					try {
						if(qw.next()){
							Integer max_score =qw.getInt("score");
							System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"  "+max_score);
						}
						else{
							System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"   0");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}if(flag==0){
			System.out.println("This Course has no exercises. Please Enter different course");
			viewReport();
		}
	
	}
	
	public void viewHomeworks() {
		// need to remove part of taking input
		System.out.println("Please provide course_id:");
		String course_id=sc.next();
		int flag=0;
		ResultSet ws= qr.selectQueries("select * from exercises e, exercise_mapping em where e.id = em.exercise_id and em.course_id = '" + course_id + "'");
		try {
			while(ws.next()){
				flag=1;
				Exercise ex =new Exercise();
				ex.id=ws.getInt("id");
				ex.name=ws.getString("name");
				ex.start_time=ws.getDate("start_time");
				ex.end_time=ws.getDate("end_time");
				ex.total_questions=ws.getInt("TOTAL_QUESTIONS");
				ex.penalty_per_question=(float) ws.getInt("PENALTY_PER_QUESTION");
				ex.points_per_question=(float) ws.getInt("POINTS_PER_QUESTION");
				ex.scoring_policy_id=ws.getInt("SCORING_POLICY_ID");
				ex.num_of_retries=ws.getInt("NUM_OF_RETRIES");
				System.out.println(ex);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==0){
			System.out.println("This Course has no homeworks.");
		}
	}

	public void viewCourseMenu(String id) {
		ResultSet rs = null;
		rs = qr.selectQueries("select * from teaches t, courses c where c.id=t.course_id and c.id='"+id+"'");
		try {
			if(rs.next()) {
				System.out.println();
				System.out.println("Course: " + id + " - " + rs.getString("name"));
				System.out.println("Professor: " + rs.getInt("professor_id"));
				System.out.println("Start Date: " + (Date)rs.getTimestamp("start_date"));
				System.out.println("End Date: " + (Date)rs.getTimestamp("end_date"));
				System.out.println("1. View Report ");
				System.out.println("2. View Homeworks ");
				System.out.println("Press 0 to Go Back to Previous Menu");
				System.out.print("Enter choice: ");
				String option = sc.next();
				
				switch(option) {
					case "0": return;
					case "1": viewReport(); break;
					case "2": viewHomeworks(); break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void viewCourses() {
		ResultSet rs = null;
		while(true) {
			rs = qr.selectQueries("select c.course_id, cr.name from courses_to_ta c, courses cr where cr.id=c.course_id and c.ta_id="+this.id);
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
					option = option.toUpperCase();
					rs = qr.selectQueries("select c.id from courses c, courses_to_ta cr where c.id=cr.course_id and cr.ta_id=" + this.id +" and c.id='" + option + "'");
					if(rs.next()) {
						viewCourseMenu(rs.getString("id"));
					} else {
						System.out.println("Invalid Course ID");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
}

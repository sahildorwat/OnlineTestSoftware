package bean;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

import javax.swing.*;

import queries.QueriesRunner;

public class Professor extends JFrame{
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
	
	private JPanel content_pane = null;
	private JPanel output_panel = null;
	private JPanel main_panel = new JPanel();
	private JPanel options = new JPanel(new GridLayout(4,1));
	private JPanel answers = new JPanel(new GridLayout(8,1));
	
	JTextField course_number; 
	JLabel error_label;
	JTextField course_text;
	JTextField course_name_text;
	JTextField prof_id_text;
	JTextField start_date_text;
	JTextField end_date_text;
	JTextField topic_id_text;
	JTextField topic_name_text;
	JTextField id_text;
	JTextField level_text;
	JTextField name_text;
	JTextField user_id_text;
	JTextField pwd_text;
	JTextField enroll_text;
	JTextField drop_text;
	JTextField stud_id;
	JTextField stud_level;
	JTextField stud_name;
	JTextField stud_id1;
	JTextField pwd_id;
	
	private static final long serialVersionUID = 1L;
	static Scanner sc=new Scanner(System.in);
	static QueriesRunner qr = QueriesRunner.getInstance();
	public  void loginAsProfessor(ResultSet rs) throws ParseException{
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
	public void showOptions() throws ParseException, SQLException{
		if(!(content_pane==null))
		{
			content_pane.removeAll();
		}
		
		setLocation(800, 600);
		setPreferredSize(new Dimension(1024,768));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));

		content_pane = new JPanel(new GridLayout(4,1));
		output_panel = new JPanel(new GridLayout(4,1));
				
		JButton btn1 = new JButton("1.View Profile");
		JButton btn2 = new JButton("2.View/Add Courses");
		JButton btn3 = new JButton("3.Enroll/Drop A Student");
		JButton btn4 = new JButton("4.View Report");
		JButton btn5 = new JButton("5.Setup TA");
		JButton btn6 = new JButton("6.View/Add Exercises");
		JButton btn7 = new JButton("7.Search/Add Questions");
		JButton btn8 = new JButton("8.Add/Remove Questions from Exercises");
		
		btn1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			viewProfile();
		}});;

		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			try {
				viewAddCourses();
			} catch (ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;

		btn3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try {
				enrolDropStudent();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;

		btn4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			try {
				viewReport();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;

		btn5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			setUpTa();
		}});;

		btn6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			try {
				viewAddExercises();
			} catch (SQLException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;

		btn7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			try {
				searchAddQuestions();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;
								
		btn8.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			try {
				addRemoveQuestionsFromExercises();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});;						
		
		content_pane.add(btn1);
		content_pane.add(btn2);
		content_pane.add(btn3);
		content_pane.add(btn4);
		content_pane.add(btn5);
		content_pane.add(btn6);
		content_pane.add(btn7);
		content_pane.add(btn8);
		//content_pane.add(output_panel);
		main_panel.add(content_pane);
		main_panel.add(output_panel);
		//getContentPane().add(content_pane);
		getContentPane().add(main_panel);
		pack();

		/*
		System.out.println("1.View Profile");
		System.out.println("2.View/Add Courses");
		System.out.println("3.Enroll/Drop A Student");
		System.out.println("4.View Report");
		System.out.println("5.Setup TA");
		System.out.println("6.View/Add Exercises");
		System.out.println("7.Search/Add Questions");
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
				enrolDropStudent();
				break;
			}else if(option==4){
				viewReport();
				break;
			}else if(option==5){
				setUpTa();
				break;
			}else if(option==6){
				viewAddExercises();
				break;
			}else if(option==7){
				searchAddQuestions();
				break;
			}else if(option==8){
				addRemoveQuestionsFromExercises();
				break;
			}
		}
		*/
	}
	public void addRemoveQuestionsFromExercises() throws SQLException {
		while(true){
			System.out.println("1.Add Questions from Exercise");
			System.out.println("2.Remove Questions from Exercise");
			System.out.println(("3.Exit"));
			Integer option = sc.nextInt();
			int flag=0;
			switch(option){
				case 1:addQuestionsToExercises();break;
				case 2:removeQuestionsFromExercises();break;
				case 3:flag=1;break;
			}
			if(flag==1) break;
		}
	}
	
	public void addQuestionsToExercises() throws SQLException {
		System.out.println("Please Enter Exercise Id");
		Integer id = sc.nextInt();
		ResultSet ws= qr.selectQueries("select * from exercises where id="+id);
		if(ws.next());
		String ex_type = ws.getString("homework_type");
		if(ex_type.equalsIgnoreCase("Adaptive")){
			addAdaptiveQuestions(id);
			return;
		}addQuestionsStandard(id);
			
		
	}
	public void addQuestionsStandard(Integer ex_id) throws SQLException{
//		ResultSet ws= qr.selectQueries("select * from topics");
//		while(ws.next()){
//			Integer topic_id = ws.getInt("id");
//			String name = ws.getString("name");
//			
//			System.out.println("topic_id = "+topic_id);
//			System.out.println("name = "+ name);
//		} 	
//		//System.out.println("Please enter topic id of Standard Exam");
//		Integer topic = sc.nextInt();
		
		
		ResultSet ws= qr.selectQueries("select max(id) as id from exercise_questions");
		int eq_id = -1;
		if(ws.next())
			eq_id = ws.getInt("id") + 1;
		else
			eq_id = 1;
		ws = qr.selectQueries("select * from exercise_mapping where exercise_id = "+ex_id);
		Integer topic = -1;
		if(ws.next()){
			topic = ws.getInt("topic_id");
		}
		ws= qr.selectQueries("select id, actual_text from questions where questions.topic_id="+topic);
		List<Integer> vals = new ArrayList<Integer>();
		while(ws.next()){
			vals.add(ws.getInt("id"));
			String act  = ws.getString("actual_text");
			System.out.println("id = "+id +", actual_text="+ act);
		}
		while(true){
			System.out.println("please enter question to be added");
			Integer q_id = sc.nextInt();
			ws = qr.selectQueries("select * from exercise_questions where question_id = "+q_id +" and exercise_id = "+ex_id );
			if(ws.next())
				continue;
			qr.updateQueries("insert into exercise_questions values("+ eq_id +","+q_id+","+ex_id+")");
			eq_id++;
			System.out.println("Do yo want to enter more? (1 = y, 0 = n)");
			Integer op = sc.nextInt();
			if(op.equals(0))
				break;
		}		
	}
	public void addAdaptiveQuestions(Integer ex_id) throws SQLException {
//		ResultSet ws= qr.selectQueries("select * from topics");
//		while(ws.next()){
//			Integer topic_id = ws.getInt("id");
//			String name = ws.getString("name");
//			
//			System.out.println("topic_id = "+topic_id);
//			System.out.println("name = "+ name);
//		} 	
//		System.out.println("Please enter topic id of Adaptive Exam");
//		Integer topic = sc.nextInt();
		ResultSet ws = qr.selectQueries("select * from exercise_mapping where exercise_id = "+ex_id);
		Integer topic = -1;
		if(ws.next()){
			topic = ws.getInt("topic_id");
		}
		
		ws= qr.selectQueries("select max(id) as id from exercise_questions");
		int eq_id = -1;
		if(ws.next())
			eq_id = ws.getInt("id") + 1;
		else
			eq_id = 1;
		ws= qr.selectQueries("select id from questions where questions.topic_id="+topic);
		List<Integer> vals = new ArrayList<Integer>();
		while(ws.next()){
			vals.add(ws.getInt("id"));
		}
		for(Integer val:vals){
			ws = qr.selectQueries("select * from exercise_questions where question_id = "+val +" and exercise_id = "+ex_id );
			if(ws.next())
				continue;
			qr.updateQueries("insert into exercise_questions values("+ eq_id +","+val+","+ex_id+")");
			eq_id++;
		}

	}
	public void removeQuestionsFromExercises() throws SQLException {
		System.out.println("Please Enter Exercise Id");
		Integer id = sc.nextInt();
		ResultSet ws= qr.selectQueries("select questions.id as id, questions.actual_text as at from exercise_questions, questions where questions.id = exercise_questions.question_id and exercise_questions.exercise_id="+id);
		while(ws.next()){
			Integer q_id = ws.getInt("id");
			String act = ws.getString("at");
			System.out.println("id = "+q_id+" actual text = "+act);
		}
		System.out.println("enter the question id");
		Integer q_id = sc.nextInt();
		qr.updateQueries("delete from exercise_questions where question_id="+q_id);
	}
	
	public void searchAddQuestions() throws SQLException {
		while(true){
			System.out.println("1.Search Questions");
			System.out.println("2.Add Questions");
			System.out.println(("3.Exit"));
			Integer option = sc.nextInt();
			int flag=0;
			switch(option){
				case 1:viewQuestions();break;
				case 2:addQuestions();break;
				case 3:flag=1;break;
			}
			if(flag==1) break;
		}
	}
	public void viewQuestions() throws SQLException{
		while(true){
			System.out.println("1.Search By Questions");
			System.out.println("2.Search By Topics");
			System.out.println(("3.Exit"));
			Integer option = sc.nextInt();
			int flag=0;
			switch(option){
				case 1:searchByQuestions();break;
				case 2:searchByTopics();break;
				case 3:flag=1;break;
			}
			if(flag==1) break;
		}
	}
    public void searchByQuestions() throws SQLException{
    	System.out.println("Enter Question id");
    	Integer id = sc.nextInt();
    	ResultSet ws= qr.selectQueries("select * from questions where id="+id);
		while(ws.next()){
			String actual_text = ws.getString("actual_text");
			String detailed_explanation = ws.getString("detailed_explanation");
			String hint = ws.getString("hint");
			Integer difficulty_level = ws.getInt("difficulty_level");
			Integer topic_id = ws.getInt("topic_id");
			System.out.println("id = "+id);
			System.out.println("actual_text = "+actual_text);
			System.out.println("detailed_explanation = "+detailed_explanation);
			System.out.println("difficulty_level = "+difficulty_level);
			System.out.println("hint = "+hint);
			System.out.println("topic_id = "+topic_id);
			
			
			
		} 	
	}
    public void searchByTopics() throws SQLException{
    	System.out.println("Enter topic");
    	String topic = sc.next();
    	topic+=sc.nextLine();
    	ResultSet ws= qr.selectQueries("select * from questions where id in (select id from topics where name like '%"+topic+"%')");
		while(ws.next()){
			String actual_text = ws.getString("actual_text");
			String detailed_explanation = ws.getString("detailed_explanation");
			String hint = ws.getString("hint");
			Integer difficulty_level = ws.getInt("difficulty_level");
			Integer topic_id = ws.getInt("topic_id");
			System.out.println("id = "+id);
			System.out.println("actual_text = "+actual_text);
			System.out.println("detailed_explanation = "+detailed_explanation);
			System.out.println("difficulty_level = "+difficulty_level);
			System.out.println("hint = "+hint);
			System.out.println("topic_id = "+topic_id);	
		} 	
	}
    public void addQuestions() throws SQLException{
    	ResultSet ws= qr.selectQueries("select max(id) as id from questions");
		Integer id = -1;
		if(ws.next())
			id = ws.getInt("id")+1;
		System.out.println("Enter Question Text");
		String actual_text = sc.next();
		System.out.println("Enter detailed explanation");
		String detailed_explanation = sc.next();
		System.out.println("Enter hint");
		String hint = sc.next();
		System.out.println("Enter difficulty_level");
		Integer difficulty_level = sc.nextInt();
		System.out.println("Enter topic_id");
		Integer topic_id = sc.nextInt();
		qr.updateQueries("INSERT INTO Questions VALUES("+id+",'"+actual_text+"','"+detailed_explanation+"','"+hint+"',"+difficulty_level+","+topic_id+")");		
    } 	
	
	public void viewAddExercises() throws SQLException, ParseException{
		while(true){
			System.out.println("1.View Exercises");
			System.out.println("2.Add Exercises");
			System.out.println(("3.Exit"));
			Integer option = sc.nextInt();
			int flag=0;
			switch(option){
				case 1:viewExercises();break;
				case 2:addExercises();break;
				case 3:flag=1;break;
			}
			if(flag==1) break;
		}
	}
	
	public void viewExercises() throws SQLException{
		System.out.println("Please provide the exercise_id:");
		Integer id=sc.nextInt();
		ResultSet ws= qr.selectQueries("select * from exercises where id="+id+"");
		while(ws.next()){
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
	}
	public void addExercises() throws SQLException, ParseException{

		System.out.println("Please provide the exercise name:");
		String name=sc.next();
		System.out.println("Please provide the exercise start time:");
		String start_Date1 =sc.next();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yy");  
		Date start_Date=formatter.parse(start_Date1);
		Timestamp ts_start = new Timestamp(start_Date.getTime());
		System.out.println("Please provide the exercise end time:");
		String end_Date1 =sc.next();
		Date end_Date=formatter.parse(start_Date1);
		Timestamp ts_end = new Timestamp(end_Date.getTime());
		System.out.println("Please provide the total questions:");
		Integer total_questions=sc.nextInt();
		System.out.println("Please provide the points per question:");
		Integer points_questions=sc.nextInt();
		System.out.println("Please provide the penalty per questions:");
		Integer penalty_questions=sc.nextInt();
		System.out.println("Please provide the scoring policy");
		Integer scoring_policy=sc.nextInt();
		System.out.println("Please provide the num of retries:");
		Integer num_retries=sc.nextInt();
		System.out.println("Please provide the howmework_type:");
		String home_work_type=sc.next();
		ResultSet ws= qr.selectQueries("select max(id) as id from exercises");
		Integer id = -1;
		if(ws.next())
			id = ws.getInt("id");
		
		String query = "INSERT INTO exercises VALUES (?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = qr.conn.prepareStatement(query);
		ps.setInt(1,id+1);
		ps.setString(2, name);
		ps.setTimestamp(3, ts_start);
		ps.setTimestamp(4, ts_end);
		ps.setInt(5,total_questions);
		ps.setInt(6,penalty_questions);
		ps.setInt(7,points_questions);
		ps.setInt(8,scoring_policy);
		ps.setInt(9,num_retries);
		ps.setString(10, home_work_type);
		ps.execute();
		ps.close();			
	}
	public void setUpTa(){
		System.out.println("Please tell the student_id of student(TA):");
		System.out.println("Please provide course_id:");
		String course_id=sc.next();
		qr.updateQueries("INSERT INTO COURSES_TO_TA VALUES("+this.id+",'"+course_id+"',"+id+")");
	}
	public void viewReport() throws SQLException{
		System.out.println("Please provide course_id: ");
		String course_id=sc.next();
		int flag=0;
		ResultSet ws= qr.selectQueries("select e.scoring_policy_id as sp_id,e.id as e_id,e.name as name from exercises e,exercise_mapping em where e.id=em.exercise_id and em.course_id='"+course_id+"'");
		ArrayList<Exercise> listExercise=new ArrayList<Exercise>();
		while(ws.next()){
			Exercise exer=new Exercise();
			exer.scoring_policy_id=ws.getInt("sp_id");
			exer.id=ws.getInt("e_id");
			exer.name=ws.getString("name");
			listExercise.add(exer);
		}
		for(Exercise exer :listExercise)
			{
			ResultSet rs= qr.selectQueries("select e.student_id,s.name as name from enrollment e,students s where e.student_id=s.id and course_id='"+course_id+"'");
			ArrayList<Student> listStudent=new ArrayList<Student>();
			while(rs.next()){
				Student stud=new Student();
				stud.id=rs.getInt("student_id");
				stud.name=rs.getString("name");
				listStudent.add(stud);
			}
			flag=1;
			for(Student stud :listStudent)
			{
				if(exer.scoring_policy_id==1){
					ResultSet ss= qr.selectQueries("select score from attempts where id=(select max(attempt_id) from student_attempts_exercises where student_id='"+stud.id+"'and exercise_id="+exer.id+")");
					if(ss.next()){
						Integer max_score =ss.getInt("score");
						System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"  "+max_score);
					}
					else{
						System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"   0");
					}
				}else if(exer.scoring_policy_id==2){
					ResultSet qz=qr.selectQueries("select max(score) as score from student_attempts_exercises s, attempts a where s.student_id ="+stud.id+" and s.exercise_id ="+exer.id+" and s.attempt_id = a.id group by s.exercise_id, s.student_id");
					if(qz.next()){
						Integer max_score =qz.getInt("score");
						System.out.print(stud.id+"  "+stud.name+"  "+exer.name+"  "+max_score);
					}
					else{
						System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"   0");
					}
				}else{
					ResultSet qw=qr.selectQueries("select avg(score) as score from student_attempts_exercises s, attempts a where s.student_id ="+stud.id+" and s.exercise_id ="+exer.id+" and s.attempt_id = a.id group by s.exercise_id, s.student_id");
					if(qw.next()){
						Integer max_score =qw.getInt("score");
						System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"  "+max_score);
					}
					else{
						System.out.println(stud.id+"  "+stud.name+"  "+exer.name+"   0");
					}
				}
			}
		}if(flag==0){
			System.out.println("This Course has no exercises. Please Enter different course");
			viewReport();
		}
		
		
	}
	public void enrolDropStudent() throws SQLException{
		//int flag=0;
		options.removeAll();
		answers.removeAll();
		
		JButton btn1 = new JButton("1.Enroll a student");
		btn1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try {
				enrollStudent();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
		
		JButton btn2 = new JButton("1.Drop a student");
		btn2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			dropStudent();
		}});
		
		options.add(btn1);
		options.add(btn2);
		pack();
	/*	while(true){
			System.out.println("1.Enroll a student");
			System.out.println("2.Drop a student");
			System.out.println("3.Exit");
			Integer option=sc.nextInt();
			switch(option){
				case 1:enrollStudent();break;
				case 2:dropStudent();break;
				case 3:flag=1;break;
			}
			if(flag==1) break;
		}*/
	}
	
	public void enrollStudent() throws SQLException{
		answers.removeAll();
		JLabel c_id = new JLabel("Course_id : ");
		course_number = new JTextField(10);
		//System.out.println("Please provide course_id:");
		//String course_id=sc.next();
		JLabel s_id = new JLabel("Student_id : ");
		stud_id = new JTextField(10);
		
		JButton btn1 = new JButton("1.Available Student");
		btn1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try {
				enrollAvailableStudent();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
		
		JLabel l_id = new JLabel("Student_Level(U/UG) : ");
		stud_level = new JTextField(10);
		
		JLabel n_id = new JLabel("Student name : ");
		stud_name = new JTextField(10);
		
		JLabel u_id = new JLabel("Student user_id : ");
		stud_id1 = new JTextField(10);
		
		JLabel p_id = new JLabel("Student password : ");
		pwd_id = new JTextField(10);
		
		JButton btn2 = new JButton("2.New Student");
		btn2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try {
				enrollNewStudent();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});

		answers.add(c_id);
		answers.add(course_number);
		answers.add(s_id);
		answers.add(stud_id);
		answers.add(btn1);
		answers.add(l_id);
		answers.add(stud_level);
		answers.add(n_id);
		answers.add(stud_name);
		answers.add(u_id);
		answers.add(stud_id1);
		answers.add(p_id);
		answers.add(pwd_id);
		answers.add(btn2);
		pack();
				
		//Student stud = new Student();
		//System.out.println("1.New Student\n2.Available Student");
		//Integer option=sc.nextInt();
		//if(option==2){
		//	System.out.println("Please enter id of the student:");
		//	Integer id=sc.nextInt();
		//	qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_id+"',"+id+")");
//		}else{
	/*	System.out.println("Please enter level, name, user_id, password");
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
	}*/
	}
	
	public void enrollAvailableStudent() throws SQLException{
		qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_number.getText()+"',"+Integer.parseInt(stud_id.getText())+")");
	}
	
	public void enrollNewStudent() throws SQLException{
		
		Student stud = new Student();
		stud.lvl=stud_level.getText();
		stud.name=stud_name.getText();
		stud.user_id=stud_id1.getText();
		stud.password=pwd_id.getText();
		ResultSet ws= qr.selectQueries("select max(id) as id from students");
		if(ws.next()){
			stud.id=ws.getInt("id")+1;
			qr.updateQueries("INSERT INTO STUDENTS VALUES("+stud.id+",'"+stud.lvl+"','"+stud.name+"','"+stud.user_id+"','"+stud.password+"')");
			qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_number.getText()+"',"+stud.id+")");
		}
	}
	
	
	public void dropStudent(){
		answers.removeAll();
		
		JLabel c_label = new JLabel("Please provide the id of student to be deleted:");
		stud_id = new JTextField(10);
		
		JButton btn = new JButton("Drop Student");
		btn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			ActuallyDropStudent();
		}});
		
		answers.add(c_label);
		answers.add(stud_id);
		answers.add(btn);
		pack();
		//System.out.println("Please provide the id of student to be deleted:");
		//Integer id=sc.nextInt();
		
	}
	
	public void ActuallyDropStudent()
	{
		qr.updateQueries("DELETE FROM STUDENTS WHERE ID="+Integer.parseInt(stud_id.getText()));
	}
	
	public void viewAddCourses() throws ParseException, SQLException{
		output_panel.removeAll();
		
		JLabel course_label = new JLabel("Enter Course Number.eg, CSC540");
		course_number = new JTextField(10);
		JButton btn1 = new JButton("1.Search by course");
		error_label = new JLabel("");
		JButton btn2 = new JButton("2.Add Courses");
		
		btn1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			searchByCourse();
		}});
			
		btn2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			try {
				addCourse();
			} catch (ParseException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}});
		
		options.add(course_label);
		options.add(course_number);
		options.add(btn1);
		options.add(error_label);
		options.add(btn2);
		output_panel.add(options);
		output_panel.add(answers);
		pack();
	/*	System.out.println("1.Search by course");
		System.out.println("2.Add Courses");
		Integer option =sc.nextInt();
		if(option==1){
			searchByCourse();
		}else{
			addCourse();
		}
		*/
		
	}
	//select * from courses,exercise_mapping,exercises where courses.id = exercise_mapping.course_id and exercise_mapping.exercise_id = exercises.id
	public void searchByCourse(){
		System.out.println("Enter the course to be searched:(Course_id)");
		//String option =sc.next();
		String option = course_number.getText();
		ResultSet ws= qr.selectQueries("select c.id as id,c.name as c_name,e.name as e_name from courses c,exercise_mapping em,exercises e where c.id = em.course_id and em.exercise_id = e.id and c.id='"+option+"'");
		try {

			while(ws.next()){
				String id=ws.getString("id");
				System.out.println(id);
				String name=ws.getString("c_name");
				System.out.println(name);
				String ename=ws.getString("e_name");
				System.out.println(ename);
				JLabel out_label = new JLabel("id : "+id+", course_name : "+ name + ", exercise_name : "+ename);
				options.add(out_label);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pack();
	}

	
	public void actuallyAddCourse() throws ParseException, SQLException{
		final String course_id = course_text.getText();
		String course_name = course_name_text.getText();
		Integer instructor_id = Integer.parseInt(prof_id_text.getText());
		String start_Date1 = start_date_text.getText();
		String end_date1 = end_date_text.getText();
		//System.out.println("Please enter course_id" );
		//String course_id =sc.next();
		//System.out.println("Please enter course_name" );
		//String course_name =sc.next();
		//course_name+=sc.nextLine();
		qr.updateQueries("INSERT INTO COURSES VALUES('"+course_id+"','"+course_name+"')");
		//System.out.println("Please enter course Instructor_id");
		//Integer instructor_id =sc.nextInt();
		//System.out.println("Please enter course startdate");
		//String start_Date1 =sc.next();
		//System.out.println("Please enter course enddate");
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yy");  
		Date start_Date=formatter.parse(start_Date1);
		Timestamp ts_start = new Timestamp(start_Date.getTime());
		//String end_date1 =sc.next();
		Date end_date=formatter.parse(end_date1);
		Timestamp ts_end = new Timestamp(end_date.getTime());
		
		System.out.println(instructor_id);
		System.out.println(prof_id_text.getText());
		System.out.println(start_Date);
		System.out.println(end_date);
		String query = "INSERT INTO teaches VALUES (?,?,?,?)";
		PreparedStatement ps = qr.conn.prepareStatement(query);
		ps.setInt(1,instructor_id);
		ps.setString(2, course_id);
		ps.setTimestamp(3, ts_start);
		ps.setTimestamp(4, ts_end);
		ps.execute();
		ps.close();	
		
		System.out.println("Successfully added value");
		options.removeAll();
		JLabel course_label = new JLabel(course_name);
		
		JButton btn1 = new JButton("1.Add Students");
		JButton btn2 = new JButton("2.Add Topics");
		
		btn1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			addStudents(course_id);
		}});
		
		btn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
			addTopics(course_id);
		}});
		
		error_label.setText("");
		options.add(course_label);
		options.add(error_label);
		options.add(btn1);
		options.add(btn2);
		pack();
		/*while(true){
			int flag=0;
			System.out.println("1.Add students");
			System.out.println("2.Add topics");
			System.out.println(("3.Exit"));
			Integer option =sc.nextInt();
			switch(option){
			case 1:addStudents(course_id);break;
			case 2:addTopics(course_id);break;
			case 3: flag =1;break;
			}
			if(flag==1){
				break;
			}
		}*/
		
	}
	
	public void addCourse() throws ParseException, SQLException{
			answers.removeAll();
			JLabel c_label = new JLabel("Please enter course_id" );
			course_text = new JTextField(10);
			
			JLabel cn_label = new JLabel("Please enter course_name" );
			course_name_text = new JTextField(10);
			
			JLabel pr_label = new JLabel("Please enter course Instructor Id" );
			prof_id_text = new JTextField(10);
			
			JLabel start_label = new JLabel("Please enter course startdate(dd-MMM-yy)" );
			start_date_text = new JTextField(10);
			
			JLabel end_label = new JLabel("Please enter course enddate(dd-MMM-yy)" );
			end_date_text = new JTextField(10);
			
			JButton btn = new JButton("Add new course");
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					try {
						actuallyAddCourse();
					} catch (ParseException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}});
			
			answers.add(c_label);
			answers.add(course_text);
			answers.add(cn_label);
			answers.add(course_name_text);
			answers.add(pr_label);
			answers.add(prof_id_text);
			answers.add(start_label);
			answers.add(start_date_text);
			answers.add(end_label);
			answers.add(end_date_text);
			answers.add(btn);
			pack();
	}
	
	public void addStudents(final String course_id){
		//System.out.println("Please enter number of student you want to insert:");
		//Integer flag=sc.nextInt();
		answers.removeAll();
		JLabel stud_label = new JLabel("Please enter student details");
		JLabel id_label = new JLabel("id");
		id_text = new JTextField(10);
		JLabel level_label = new JLabel("level(U/G)");
		level_text = new JTextField(10);
		JLabel name_label = new JLabel("name");
		name_text = new JTextField(10);
		JLabel user_id_label = new JLabel("user_id");
		user_id_text = new JTextField(10);
		JLabel pwd_label = new JLabel("password");
		pwd_text = new JTextField(10);
		JButton btn = new JButton("Add Student");
		
		//while(flag>0){
		
			
			//System.out.println("Please enter student details");
		//	System.out.println("id level(U/G) name user_id password");
		//	stud.id =sc.nextInt();
		//	stud.lvl=sc.next();
		//	stud.name=sc.next();
		//	stud.name+=sc.nextLine();
		//	stud.user_id=sc.next();
		//	stud.password=sc.next();
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ActuallyAddStudents(course_id);
			}});
			
		answers.add(stud_label);
		answers.add(id_label);
		answers.add(id_text);
		answers.add(level_label);
		answers.add(level_text);
		answers.add(name_label);
		answers.add(name_text);
		answers.add(user_id_label);
		answers.add(user_id_text);
		answers.add(pwd_label);
		answers.add(btn);
		pack();
			//flag--;
		//}
	}
	
	public void ActuallyAddStudents(String course_id){
		Student stud=new Student();
		stud.id =Integer.parseInt(id_text.getText());
		stud.lvl=level_text.getText();
		stud.name=name_text.getText();
		stud.user_id=user_id_text.getText();
		stud.password=pwd_text.getText();
		
		qr.updateQueries("INSERT INTO STUDENTS VALUES("+stud.id+",'"+stud.lvl+"','"+stud.name+"','"+stud.user_id+"','"+stud.password+"')");
		qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_id+"','"+stud.id+"')");
	}
	
	public void addTopics(final String course_id){
		answers.removeAll();
		JLabel t_label = new JLabel("Please enter topic_id" );
		topic_id_text = new JTextField(10);
		
		JLabel tn_label = new JLabel("Please enter topic_name" );
		topic_name_text = new JTextField(10);
		
		JButton btn = new JButton("Add new topic");
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				actuallyAddTopic(course_id);
			}});
		
		
		answers.add(t_label);
		answers.add(topic_id_text);
		answers.add(tn_label);
		answers.add(topic_name_text);
		answers.add(btn);
		pack();
		//System.out.println("Please enter number of topics you want to insert:");
		//Integer flag=sc.nextInt();
		//while(flag>0){
			
		//	flag--;
		//}
	}
	
	public void actuallyAddTopic(String course_id){
		Topic tp=new Topic();
		System.out.println("Please enter topic_id and name of the topic");
		tp.id=Integer.parseInt(topic_id_text.getText());
		tp.name=topic_name_text.getText();
		qr.updateQueries("INSERT INTO TOPICS VALUES("+tp.id+",'"+tp.name+"')");
		qr.updateQueries("INSERT INTO COURSES_TO_TOPICS VALUES('"+course_id+"',"+tp.id+")");
		
	}
	
	public void viewProfile(){
		output_panel.removeAll();
		System.out.println(this);
		JLabel out_label = new JLabel(this.toString());
		output_panel.add(out_label);
		getCoursesForProfessor();
		
	}
	public void getCoursesForProfessor(){
		ResultSet ss= qr.selectQueries("select t.course_id as course_id,c.name as name,t.start_date as start_date,t.end_date as end_date from teaches t,courses c where t.course_id=c.id and professor_id="+id);
		System.out.println();
		try {
			
			while(ss.next()){
				Course temp=new Course();
				temp.id=ss.getString("course_id");
				temp.name=ss.getString("name");
				temp.start_date=(Date)ss.getTimestamp("start_date");
				temp.end_date=(Date)ss.getTimestamp("end_date");
				System.out.println(temp);
				JLabel out_label = new JLabel(temp.toString());
				output_panel.add(out_label);
				this.courses.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pack();
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

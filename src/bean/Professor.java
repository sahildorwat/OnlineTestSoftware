package bean;

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
		}
			
		
	}
	public void addAdaptiveQuestions(Integer ex_id) throws SQLException {
		ResultSet ws= qr.selectQueries("select * from topics");
		while(ws.next()){
			Integer topic_id = ws.getInt("id");
			String name = ws.getString("name");
			
			System.out.println("topic_id = "+topic_id);
			System.out.println("name = "+ name);
		} 	
		System.out.println("Please enter topic id of Adaptive Exam");
		Integer topic = sc.nextInt();
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
		System.out.println("Please provide course_id:");
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
//			Integer sp_id=ws.getInt("sp_id");
//			System.out.println(sp_id);
//			Integer exercise_id=ws.getInt("e_id");
//			System.out.println(exercise_id);
//			String exercise_name=ws.getString("name");
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
//				Integer student_id=rs.getInt("student_id");
//				String student_name =rs.getString("name");
//				System.out.println(student_name);
//				System.out.println(exercise_id);
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
			System.out.println("This Course has no exercises ,Please Enter different course");
			viewReport();
		}
		
		
	}
	public void enrolDropStudent() throws SQLException{
		int flag=0;
		while(true){
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
			System.out.println("Please enter level ,name,user_id,password");
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
	
	
	public void viewAddCourses() throws ParseException, SQLException{
		System.out.println("1.Search by course");
		System.out.println("2.Add Courses");
		Integer option =sc.nextInt();
		if(option==1){
			searchByCourse();
		}else{
			addCourse();
		}
	}
	//select * from courses,exercise_mapping,exercises where courses.id = exercise_mapping.course_id and exercise_mapping.exercise_id = exercises.id
	public void searchByCourse(){
		System.out.println("Enter the course to be searched:(Course_id)");
		String option =sc.next();
		ResultSet ws= qr.selectQueries("select c.id as id,c.name as c_name,e.name as e_name from courses c,exercise_mapping em,exercises e where c.id = em.course_id and em.exercise_id = e.id and c.id='"+option+"'");
		try {

			while(ws.next()){
				String id=ws.getString("id");
				System.out.println(id);
				String name=ws.getString("c_name");
				System.out.println(name);
				String ename=ws.getString("e_name");
				System.out.println(ename);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addCourse() throws ParseException, SQLException{
			System.out.println("Please enter course_id" );
			String course_id =sc.next();
			System.out.println("Please enter course_name" );
			String course_name =sc.next();
			course_name+=sc.nextLine();
			qr.updateQueries("INSERT INTO COURSES VALUES('"+course_id+"','"+course_name+"')");
			System.out.println("Please enter course Instructor_id");
			Integer instructor_id =sc.nextInt();
			System.out.println("Please enter course startdate");
			String start_Date1 =sc.next();
			System.out.println("Please enter course enddate");
			SimpleDateFormat formatter=new SimpleDateFormat("dd-MMM-yy");  
			Date start_Date=formatter.parse(start_Date1);
			Timestamp ts_start = new Timestamp(start_Date.getTime());
			String end_date1 =sc.next();
			Date end_date=formatter.parse(end_date1);
			Timestamp ts_end = new Timestamp(end_date.getTime());
			
			System.out.println(instructor_id);
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
			while(true){
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
			}
	}
	public void addStudents(String course_id){
		System.out.println("Please enter number of student you want to insert:");
		Integer flag=sc.nextInt();
		while(flag>0){
			Student stud=new Student();
			System.out.println("Please enter student details");
			System.out.println("id level(U/G) name user_id password");
			stud.id =sc.nextInt();
			stud.lvl=sc.next();
			stud.name=sc.next();
			stud.name+=sc.nextLine();
			stud.user_id=sc.next();
			stud.password=sc.next();
			qr.updateQueries("INSERT INTO STUDENTS VALUES("+stud.id+",'"+stud.lvl+"','"+stud.name+"','"+stud.user_id+"','"+stud.password+"')");
			qr.updateQueries("INSERT INTO ENROLLMENT VALUES('"+course_id+"','"+stud.id+"')");
			flag--;
		}
	}
	public void addTopics(String course_id){
		System.out.println("Please enter number of topics you want to insert:");
		Integer flag=sc.nextInt();
		while(flag>0){
			Topic tp=new Topic();
			System.out.println("Please enter topic_id and name of the topic");
			tp.id=sc.nextInt();
			tp.name=sc.next();
			qr.updateQueries("INSERT INTO TOPICS VALUES("+tp.id+",'"+tp.name+"')");
			qr.updateQueries("INSERT INTO COURSES_TO_TOPICS VALUES('"+course_id+"',"+tp.id+")");
			flag--;
		}
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
				temp.id=ss.getString("course_id");
				temp.name=ss.getString("name");
				temp.start_date=(Date)ss.getTimestamp("start_date");
				temp.end_date=(Date)ss.getTimestamp("end_date");
				System.out.println(temp);
				this.courses.add(temp);
			}
		} catch (SQLException e) {
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

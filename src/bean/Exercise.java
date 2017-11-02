package bean;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import queries.QueriesRunner;

public class Exercise {
	Integer id;
	String name;
	Date start_time;
	Date end_time;
	Integer total_questions;
	Float penalty_per_question;
	Float points_per_question;
	Integer scoring_policy_id;
	Integer num_of_retries;
	String homework_type;
	List <Attempt> student_attempts;
	
	static QueriesRunner qr = QueriesRunner.getInstance();
	static Scanner sc = new Scanner(System.in);
	
	public Exercise() {
		this.id = null;
		this.name = null;
		this.start_time = null;
		this.end_time = null;
		this.total_questions = null;
		this.penalty_per_question = null;
		this.points_per_question = null;
		this.scoring_policy_id = null;
		this.num_of_retries = null;
		this.student_attempts = new ArrayList<Attempt>();
	}
	
	public void showHomeworkMenu(ResultSet rs, Integer student_id) throws SQLException {
		this.id = rs.getInt("exercise_id");
		System.out.println("student_id = " + student_id);
		try {
			System.out.println("Homework Menu for " + rs.getString("course_id"));
			System.out.println("1. Current Open HWs");
			System.out.println("2. Past HWs");
			System.out.println("Press 0 to Go Back to Previous Menu");
			System.out.print("Enter Choice: ");
			
			Integer option = sc.nextInt();
			switch(option) {
				case 0: return;
				case 1: currentHWs(rs, student_id); break;
				case 2: pastHWs(rs, student_id); break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void currentHWs(ResultSet inputRS,Integer student_id) throws SQLException {
		ResultSet rs = null;
		ResultSet qw= qr.selectQueries("select count(*) as count1 from student_attempts_exercises where exercise_id="+this.id+" and student_id="+student_id);
		Integer tries = -1;
		if(qw.next()) {
			tries=qw.getInt("count1");
		}
		System.out.println(tries);
		rs = qr.selectQueries("select e.name from exercises e where e.end_time > CURRENT_TIMESTAMP and e.start_time <= CURRENT_TIMESTAMP and e.num_of_retries<="+tries);
		try {
			int flag = 0;
			String name;
			while(rs.next()) {			
				name=rs.getString("name");
				if (name != null) {
					System.out.println("List of available homeworks: ");
					System.out.println(name);
					System.out.println("1. Attempt Homework");	
					flag=1;
				}
			}
			if (flag == 0) {
				System.out.println("No active homeworks for this course!");
			}
			else{
				System.out.println("Please enter homework id which you wish to attempt");
				Integer option = sc.nextInt();
				attemptHomework(option,student_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void attemptHomework( int ex_id,Integer student_id) throws SQLException{

		Timestamp ts_start = new Timestamp(System.currentTimeMillis());
		ResultSet rs = null;
		rs = qr.selectQueries("select * from exercise_questions where exercise_id = "+ex_id);
		List<QuestionExercise> question = new ArrayList<QuestionExercise>();
		Integer score = 0;
		while(rs.next()){
			QuestionExercise q = new QuestionExercise();
			Integer question_id  = rs.getInt("question_id");
			Integer param_id = rs.getInt("parameter_id");
			q.question_id =  question_id;
			q.parameter_id = param_id;
			question.add(q);
		}
		rs = qr.selectQueries("select max(id) as val from attempts");
		Integer val = -1;
		if(rs.next())
			val = rs.getInt("val") + 1;
		rs = qr.selectQueries("select max(attempt_id) as val from student_attempts_exercises where student_id="+student_id+" and exercise_id="+id);
		Integer attempt_id = -1;
		if(rs.next())
			attempt_id = rs.getInt("val");
		rs = qr.selectQueries("select attempt_no from attempts where id="+attempt_id);
		Integer attempt_no=-1;
		if(rs.next()){
			attempt_no=rs.getInt("attempt_no");
		}
		if(attempt_no==null)
			attempt_no = 0;
		attempt_no++;
		Timestamp ts_end = new Timestamp(System.currentTimeMillis());
//		qr.updateQueries("insert into attempts values("+val+","+ attempt_no +"," + score +","+ts_start +","+ts_end+")");
		
		String query = "INSERT INTO attempts VALUES (?,?,?,?,?)";
		PreparedStatement ps = qr.conn.prepareStatement(query);
		ps.setInt(1,val);
		ps.setInt(2,attempt_no);
		ps.setInt(3,score);
		ps.setTimestamp(4, ts_start);
		ps.setTimestamp(5, ts_end);
		ps.execute();
		ps.close();	
		for(QuestionExercise qe: question){
			System.out.println(qe.parameter_id);
			if(qe.parameter_id==0){
				if(viewSimpleQuestion(ex_id,qe.question_id,student_id))
					score+=3;
				else
					score-=1;
			}
			else{
				viewParamQuestion(ex_id,qe.question_id,qe.parameter_id,student_id);
			}
		}
		
		rs = qr.selectQueries("select max(id) as val from attempts");
		ts_end = new Timestamp(System.currentTimeMillis());
//		qr.updateQueries("insert into attempts values("+val+","+ attempt_no +"," + score +","+ts_start +","+ts_end+")");
		System.out.println(score);
		query = "UPDATE attempts set end_time = ?, score = ? where id = ?";
		ps = qr.conn.prepareStatement(query);
		ps.setTimestamp(1, ts_end);
		ps.setInt(2,score);
		ps.setInt(3,val);
		ps.execute();
		ps.close();	

	}
	
	public boolean viewSimpleQuestion(Integer ex_id, Integer question_id,Integer student_id) throws SQLException{
		ResultSet rs = null;
		rs = qr.selectQueries("select actual_text from questions where id = " + question_id);
		String text = null;
		if(rs.next())
			text = rs.getString("actual_text");
		System.out.println(text);
		//Ensure 1 correct and 3 wrong
		
		HashMap<Integer,String> map=new HashMap<Integer,String>();
		ArrayList<String> arr_incorrect=new ArrayList<String>();
		ArrayList<String> arr_correct=new ArrayList<String>();
		ArrayList<String> arr_mix=new ArrayList<String>();
		
		rs = qr.selectQueries("select a.answer_text as answer_text from answers a,answer_set ast where ast.question_id = " + question_id+" and a.answer_set_id = ast.id and a.is_correct=0");
		while(rs.next()) {
			arr_incorrect.add(rs.getString("answer_text"));
		}
		rs = qr.selectQueries("select a.answer_text as answer_text from answers a,answer_set ast where ast.question_id = " + question_id+" and a.answer_set_id = ast.id and a.is_correct=1");
		while(rs.next()) {
			arr_correct.add(rs.getString("answer_text"));
		}
	//	System.out.println(arr_correct+" coorrrr");
	//	System.out.println(arr_incorrect+" bidsfsd");
		while(arr_mix.size()< 3) {
			
			int j=arr_incorrect.size();
			Random rand = new Random();
			int  n = rand.nextInt(j);
			if(!arr_mix.contains(arr_incorrect.get(n)))
				arr_mix.add(arr_incorrect.get(n));
	//		System.out.println(arr_mix);
		}
		int j=arr_correct.size();
		Random rand = new Random();
		int  n = rand.nextInt(j);
		arr_mix.add(arr_correct.get(n));
	//	System.out.println(arr_mix);
		Collections.shuffle(arr_mix);
		int in=1;
		while(in<=arr_mix.size()) {
			map.put(in, arr_mix.get(in-1));
			in++;
		}
		for(Integer k:map.keySet()) {
			System.out.println(k+" "+map.get(k));
		}
		System.out.println(map);
		rs = qr.selectQueries("select max(id) as val from attempts");
		Integer val = -1;
		if(rs.next())
			val = rs.getInt("val");
		System.out.println("Please provide your answer:");
		rs = qr.selectQueries("select id from exercise_questions where question_id = "+question_id +" and exercise_id = "+ex_id);
		int eq = -1;
		if(rs.next())
			eq = rs.getInt("id");
		Integer selected_ans = sc.nextInt();
		qr.updateQueries("insert into exercise_question_set values ("+val + ",'" + map.get(selected_ans) + "'," +eq+ ", NULL ,'"+map.toString()+"')");
		if(arr_correct.contains(map.get(selected_ans))){
			return true;
		}
		return false;
	}
	
	public void viewParamQuestion(Integer ex_id, Integer question_id,Integer param_id,Integer student_id){
		
	}
	
	public void pastHWs(ResultSet inputRS, Integer student_id) throws SQLException {	
		ResultSet rs = null;
		rs = qr.selectQueries("select * from exercises e" );
		ArrayList<Exercise> listExercise = new ArrayList<Exercise>();
		while(rs.next()){
			System.out.println();
			Exercise exer=new Exercise();
			exer.name = rs.getString("name");
			exer.start_time = rs.getDate("start_time");
			exer.end_time = rs.getDate("end_time");
			exer.total_questions = rs.getInt("total_questions");
			exer.penalty_per_question = rs.getFloat("penalty_per_question");
			exer.points_per_question = rs.getFloat("points_per_question");
			exer.num_of_retries = rs.getInt("num_of_retries");
			exer.homework_type = rs.getString("homework_type");
			exer.scoring_policy_id = rs.getInt("scoring_policy_id");
			exer.id = rs.getInt("id");
			listExercise.add(exer);
		}
		
		for(Exercise exer :listExercise)
		{
			int res = 0;
			String sp = "";
			System.out.println();
			rs = qr.selectQueries("select count(*) as cnt from student_attempts_exercises s where s.student_id = " + student_id + " and s.exercise_id = " + exer.id);
			if(rs.next()) {
				res = rs.getInt("cnt");
			}
			System.out.println("List of past homeworks: ");		
			rs = qr.selectQueries("select * from scoring_policies s where s.id = " + exer.scoring_policy_id);
			if(rs.next()) {
				sp = rs.getString("vals");
			}
			ArrayList<Double> scores = new ArrayList<Double>();
			rs = qr.selectQueries("select a.score from student_attempts_exercises s, attempts a where a.id = s.attempt_id and s.student_id = " + student_id + " and s.exercise_id = " + exer.id + " order by a.attempt_no");
			while(rs.next()) {
				scores.add(rs.getDouble("score"));
			}
			System.out.println("Name: " + exer.name);
			System.out.println("Start time: " + exer.start_time);
			System.out.println("End time: " + exer.end_time);
			System.out.println("Homework type: " + exer.homework_type);
			System.out.println("Scoring Methodology: " + sp);
			System.out.println("Score: " + this.getScore(scores, exer.scoring_policy_id));
			System.out.println("Total points: " + (exer.total_questions * exer.points_per_question));
			System.out.println("Maximum allowed retries: " + exer.num_of_retries);
			System.out.println("Attempts by the student: " + res);
			
			System.out.println("Detailed Report for Each Attempt :");
			ArrayList<Integer> attempt_ids = new ArrayList<Integer>();
			rs = qr.selectQueries("select a.id from student_attempts_exercises s, attempts a where a.id = s.attempt_id and s.student_id = " + student_id + " and s.exercise_id = " + exer.id + " order by a.attempt_no");
			while(rs.next())
			{
				attempt_ids.add(rs.getInt("id"));
			}
			
			for(int index = 0; index < attempt_ids.size(); index++)
			{
				System.out.println("Attempt #"+(index+1));
				ArrayList<Integer> question_ids = new ArrayList<Integer>();
				ArrayList<Integer> selected_ans_ids = new ArrayList<Integer>();
				rs = qr.selectQueries("select * from exercise_question_set eqs where eqs.attempt_id = " + (int)attempt_ids.get(index));
				while(rs.next())
				{
					question_ids.add(rs.getInt("eq_id"));
					selected_ans_ids.add(rs.getInt("selected_ans"));
				}
				
				System.out.println("Questions asked in the attempt : " + question_ids.toString());
				System.out.println("Answers selected in the attempt : " + selected_ans_ids.toString());
				
				ArrayList<ArrayList<String>> solutions = new ArrayList<ArrayList<String>>();
				for(int q_index = 0; q_index < question_ids.size(); q_index++)
				{
					ArrayList<String> per_question_solution = new ArrayList<String>();
					// Need to add the relationship between parameters too for question 3
						//Integer default_param = 1;
					rs = qr.selectQueries("select ans.answer_text from answers ans, answer_set ans_set, exercise_question_set eqs where ans_set.question_id= " + (int)question_ids.get(q_index) + " and ans.answer_set_id = ans_set.id and ans.is_correct = 1 and ans_set.parameter_id = eqs.parameter_id  and eqs.attempt_id = " + (int)attempt_ids.get(index) );
					while(rs.next())
					{
						per_question_solution.add(rs.getString("answer_text"));
					}
					solutions.add(per_question_solution);
					System.out.println("Solutions for question #" + (q_index+1) +": " + per_question_solution.toString());
				}
				
				ArrayList<String> is_correct_list = new ArrayList<String>();
				for(int q_index = 0; q_index < solutions.size(); q_index++)
				{
					is_correct_list.add("incorrect");
					if(solutions.get(q_index).contains(selected_ans_ids.get(q_index).toString()))
					{
						is_correct_list.set(is_correct_list.size()-1,"correct");
						continue;
					}
				}
				

				System.out.println("Selected answers are correct or not : " + is_correct_list.toString());
				ArrayList<Float> points_earned = new ArrayList<Float>();
				for(int q_index = 0; q_index < is_correct_list.size(); q_index++)
				{
					if(is_correct_list.get(q_index).equalsIgnoreCase("correct"))
					{
						points_earned.add(exer.points_per_question);
					}
					else if(is_correct_list.get(q_index).equalsIgnoreCase("incorrect"))
					{
						points_earned.add(exer.penalty_per_question);
					}
					
				}
					System.out.println("Points earned per question : " + points_earned.toString());
					System.out.println("Total points for this attempt : "+ scores.get(index));
				System.out.println();
			}
				
			
			
			
		}
	}
	
	private Double getScore(ArrayList<Double> scores, Integer scoring_policy_id) {
		Double final_score = 0.0;
		Double sum = 0.0;
		if(!scores.isEmpty()) {
			switch(scoring_policy_id) {
				case 1: final_score = scores.get(scores.size()-1); break;
				case 2: final_score = Collections.max(scores); break;
				case 3: for(Double i: scores) {
							sum += i;
						}
						final_score = sum / scores.size();
						break;
			}
		}
		return final_score;
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

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getTotal_questions() {
		return total_questions;
	}

	public void setTotal_questions(Integer total_questions) {
		this.total_questions = total_questions;
	}

	public Float getPenalty_per_question() {
		return penalty_per_question;
	}

	public void setPenalty_per_question(Float penalty_per_question) {
		this.penalty_per_question = penalty_per_question;
	}

	public Float getPoints_per_question() {
		return points_per_question;
	}

	public void setPoints_per_question(Float points_per_question) {
		this.points_per_question = points_per_question;
	}

	public Integer getScoring_policy_id() {
		return scoring_policy_id;
	}

	public void setScoring_policy_id(Integer scoring_policy_id) {
		this.scoring_policy_id = scoring_policy_id;
	}

	public Integer getNum_of_retries() {
		return num_of_retries;
	}

	public void setNum_of_retries(Integer num_of_retries) {
		this.num_of_retries = num_of_retries;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", start_time="
				+ start_time + ", end_time=" + end_time + ", total_questions="
				+ total_questions + ", penalty_per_question="
				+ penalty_per_question + ", points_per_question="
				+ points_per_question + ", scoring_policy_id="
				+ scoring_policy_id + ", num_of_retries=" + num_of_retries
				+ "]";
	}

}

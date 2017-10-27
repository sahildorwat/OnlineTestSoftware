package bean;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	
	public void showHomeworkMenu(ResultSet rs, Integer id) {
		this.id = id;
		try {
			System.out.println("Homework Menu for " + rs.getString("course_id"));
			System.out.println("1. Current Open HWs");
			System.out.println("2. Past HWs");
			System.out.println("Press 0 to Go Back to Previous Menu");
			System.out.print("Enter Choice: ");
			
			Integer option = sc.nextInt();
			switch(option) {
				case 0: return;
				case 1: currentHWs(); break;
				case 2: pastHWs(); break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void currentHWs() {
		ResultSet rs = null;
		rs = qr.selectQueries("select e.name from exercises e where e.end_time > CURRENT_TIMESTAMP and e.start_time >= CURRENT_TIMESTAMP");
		System.out.println("List of available homeworks: ");
		try {
			while(rs.next()) {				
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("1. Attempt Homework");
	}
	
	public void pastHWs() throws SQLException {
		ResultSet rs = null;
		rs = qr.selectQueries("select * from exercises e where e.end_time <= CURRENT_TIMESTAMP and e.start_time >= CURRENT_TIMESTAMP" );
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
			rs = qr.selectQueries("select count(*) as cnt from student_attempts_exercises s where s.student_id = " + id + " and s.exercise_id = " + exer.id);
			if(rs.next()) {
				res = rs.getInt("cnt");
			}
			System.out.println("List of past homeworks: ");		
			rs = qr.selectQueries("select * from scoring_policies s where s.id = " + exer.scoring_policy_id);
			if(rs.next()) {
				sp = rs.getString("vals");
			}
			ArrayList<Double> scores = new ArrayList<Double>();
			rs = qr.selectQueries("select a.score from student_attempts_exercises s, attempts a where a.id = s.attempt_id and s.student_id = " + id + " and s.exercise_id = " + exer.id + " order by a.attempt_no");
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

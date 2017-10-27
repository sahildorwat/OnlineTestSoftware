package bean;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		rs = qr.selectQueries("select * from exercises e where e.end_time <= CURRENT_TIMESTAMP" ); // need to add start time condition
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
		/*for(Exercise exer : listExercise) {
			System.out.println(exer);
		}*/
		for(Exercise exer :listExercise)
		{
			ResultSet ex = qr.selectQueries("select count(*) as cnt from student_attempts_exercises s where s.student_id = " + id + " and s.exercise_id = " + exer.id);
			int res = 0;
			System.out.println();
			while (ex.next()) {
				res = ex.getInt("cnt");
			}
			System.out.println("List of past homeworks: ");		
//				sc = qr.selectQueries("select * from scoring_policies s where id = " + rs.getInt("scoring_policy_id"));
			System.out.println("Name: " + exer.name);
			System.out.println("Start time: " + exer.start_time);
			System.out.println("End time: " + exer.end_time);
			System.out.println("Homework type: " + exer.homework_type);
			System.out.println("Total points: " + (exer.total_questions * exer.points_per_question));
			System.out.println("Maximum allowed retries: " + exer.num_of_retries);
			System.out.println("Attempts by the student: " + res);
		}
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

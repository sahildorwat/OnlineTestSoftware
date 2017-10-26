package bean;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	
	static QueriesRunner qr = QueriesRunner.getInstance();
	static Scanner sc = new Scanner(System.in);
	
	public void showHomeworkMenu(ResultSet rs, Integer id) {
		this.id = id;
		try {
			System.out.println("Homework Menu for " + rs.getString("course_id"));
			System.out.println("1. Current Open HWs");
			System.out.println("2. Past HWs");
			System.out.println("Press 0 to Go Back to Previous Menu");
			System.out.print("Enter Choice: ");
			
			String option = sc.next();
			switch(option) {
				case "0": return;
				case "1": currentHWs(); break;
				case "2": pastHWs(); break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void currentHWs() {
		ResultSet rs = null;
		rs = qr.selectQueries("select e.name from exercises e where e.end_time > CURRENT_TIMESTAMP");
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
	
	public void pastHWs() {
		ResultSet rs = null;
		ResultSet ex = null;
		rs = qr.selectQueries("select * from exercises e where e.end_time < CURRENT_TIMESTAMP");
		System.out.println("List of past homeworks: ");
		try {
			while(rs.next()) {				
//				ex = qr.selectQueries("select count(*) from student_attempts_exercises s where s.student_id = " + id + " and s.exercise_id = " + rs.getInt("id"));
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Start time: " + rs.getString("start_time"));
				System.out.println("End time: " + rs.getString("end_time"));
				System.out.println("Homework type: " + rs.getString("homework_type"));
				System.out.println("Total points: " + rs.getInt("total_questions")*rs.getInt("points_per_question"));
				System.out.println("Scoring policy id: " + rs.getInt("scoring_policy_id"));
				System.out.println("Number of retries: " + rs.getInt("num_of_retries"));
//				System.out.println("Attempts by the student: " + ex.getInt("count"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

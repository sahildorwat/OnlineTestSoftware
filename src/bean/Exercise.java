package bean;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
	
	static Scanner sc = new Scanner(System.in);
	
	public void showHomeworkMenu(ResultSet rs) {
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
		
	}
	
	public void pastHWs() {
		
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

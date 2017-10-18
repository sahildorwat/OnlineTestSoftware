package bean;

public class AnswerSet {
	Integer id;
	Integer question_id;
	Integer parameter_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}
	public Integer getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(Integer parameter_id) {
		this.parameter_id = parameter_id;
	}
	@Override
	public String toString() {
		return "AnswerSet [id=" + id + ", question_id=" + question_id
				+ ", parameter_id=" + parameter_id + "]";
	}
	
}

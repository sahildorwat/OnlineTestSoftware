package bean;

public class Question {
	Integer id;
	String actual_text;
	String detailed_explanation;
	String hint;
	Integer difficulty_level;
	Integer topic_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActual_text() {
		return actual_text;
	}
	public void setActual_text(String actual_text) {
		this.actual_text = actual_text;
	}
	public String getDetailed_explanation() {
		return detailed_explanation;
	}
	public void setDetailed_explanation(String detailed_explanation) {
		this.detailed_explanation = detailed_explanation;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public Integer getDifficulty_level() {
		return difficulty_level;
	}
	public void setDifficulty_level(Integer difficulty_level) {
		this.difficulty_level = difficulty_level;
	}
	public Integer getTopic_id() {
		return topic_id;
	}
	public void setTopic_id(Integer topic_id) {
		this.topic_id = topic_id;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", actual_text=" + actual_text
				+ ", detailed_explanation=" + detailed_explanation + ", hint="
				+ hint + ", difficulty_level=" + difficulty_level
				+ ", topic_id=" + topic_id + "]";
	}

}

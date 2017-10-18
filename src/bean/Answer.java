package bean;

public class Answer {
	Integer id;
	Integer is_correct;
	String answer_text;
	Integer answer_set_id;
	@Override
	public String toString() {
		return "Answer [id=" + id + ", is_correct=" + is_correct
				+ ", answer_text=" + answer_text + ", answer_set_id="
				+ answer_set_id + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIs_correct() {
		return is_correct;
	}
	public void setIs_correct(Integer is_correct) {
		this.is_correct = is_correct;
	}
	public String getAnswer_text() {
		return answer_text;
	}
	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}
	public Integer getAnswer_set_id() {
		return answer_set_id;
	}
	public void setAnswer_set_id(Integer answer_set_id) {
		this.answer_set_id = answer_set_id;
	}
}

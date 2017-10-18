package bean;

public class ScoringPolicy {
	Integer id;
	@Override
	public String toString() {
		return "ScoringPolicy [id=" + id + ", vals=" + vals + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVals() {
		return vals;
	}
	public void setVals(String vals) {
		this.vals = vals;
	}
	String vals;
}

package bean;

public class Parameter {
	Integer id;
	String vals;
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
	@Override
	public String toString() {
		return "Parameter [id=" + id + ", vals=" + vals + "]";
	}
	
	
}

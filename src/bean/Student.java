package bean;

public class Student {
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Character getLvl() {
		return lvl;
	}
	public void setLvl(Character lvl) {
		this.lvl = lvl;
	}
	Integer id;
	@Override
	public String toString() {
		return "Student [id=" + id + ", lvl=" + lvl + "]";
	}
	Character lvl; 
}

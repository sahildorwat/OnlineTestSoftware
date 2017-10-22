package bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
	String lvl;
	Integer id;
	String name;
	String user_id;
	String password;
	public void loginAsStudent(ResultSet rs){
		try {
			this.id=rs.getInt("id");
			this.lvl=rs.getString("lvl");
			this.name=rs.getString("name");
			this.user_id=rs.getString("user_id");
			this.password=rs.getString("password");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	@Override
	public String toString() {
		return "Student [lvl=" + lvl + ", id=" + id + ", name=" + name
				+ ", user_id=" + user_id + ", password=" + password + "]";
	}
	
	
	 
}

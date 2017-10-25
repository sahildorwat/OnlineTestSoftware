package bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import queries.QueriesRunner;

public class Professor {
	Integer id;
	String name;
	String user_id;
	String password;
	static QueriesRunner qr = QueriesRunner.getInstance();
	public  void loginAsProfessor(ResultSet rs){
		try {
			this.id=rs.getInt("id");
			this.name=rs.getString("name");
			this.user_id=rs.getString("user_id");
			this.password=rs.getString("password");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void viewProfile(){
		System.out.println(this);
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", name=" + name + ", user_id="
				+ user_id + ", password=" + password + "]";
	}
	
}

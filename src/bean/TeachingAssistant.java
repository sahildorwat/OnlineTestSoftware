package bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeachingAssistant extends Student{
	
	public void loginAsTeachingAssistant(ResultSet rs){
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
	
	
	
}

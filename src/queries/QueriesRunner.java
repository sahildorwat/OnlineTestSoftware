package queries;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import connection.GdConnection;


public class QueriesRunner {
	
	public static void updateQueries(String sql){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(GdConnection.jdbcURL, GdConnection.user, GdConnection.passwd);
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);

            } finally {
                GdConnection.close(stmt);
                GdConnection.close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }

	}
	public static void selectQueries(String sql){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                conn = DriverManager.getConnection(GdConnection.jdbcURL, GdConnection.user, GdConnection.passwd);
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Integer s = rs.getInt("id");
                    String n = rs.getString("lvl");
                    System.out.println(s + "   " + n);
                }


            } finally {
                GdConnection.close(stmt);
                GdConnection.close(conn);
                GdConnection.close(rs);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }

	}
	
	public static void main(String[] args){
//		updateQueries("insert into students values(1,'u')");
		updateQueries("delete from students");
		selectQueries("select * from students");
		
	}

}

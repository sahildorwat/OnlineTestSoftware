package connection;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GdConnection {
	public static final String jdbcURL    = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
	public static String user = "kspatil2";   
	public static String passwd = "200150642";

    public static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    public static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

    public static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }

		

	public static void main(String[] args) {
        
	}
    
	
}

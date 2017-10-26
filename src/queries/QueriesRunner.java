package queries;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.GdConnection;


public class QueriesRunner {	
	Connection conn;
    Statement stmt;
    private static QueriesRunner qr = null;
    
    public static QueriesRunner getInstance(){
    	if(qr == null)
    		qr = new QueriesRunner();
    	return qr;
    }
    
    private QueriesRunner(){
    	try{
    		Class.forName("oracle.jdbc.driver.OracleDriver");

    		try {

    			this.conn = DriverManager.getConnection(GdConnection.jdbcURL, GdConnection.user, GdConnection.passwd);
    			this.stmt = this.conn.createStatement();;
    			//		        ResultSet rs = null;

    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		};}
    	catch(Throwable oops) {
    		oops.printStackTrace();
    	}
    }
    public  void closeConnection(){
    	GdConnection.close(conn);
    	GdConnection.close(stmt);
    }
    
    public void updateQueries(String sql){
    	try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
//            Connection conn = null;
//            Statement stmt = null;

            try {
//                conn = DriverManager.getConnection(GdConnection.jdbcURL, GdConnection.user, GdConnection.passwd);
//                stmt = conn.createStatement();
                System.out.println(sql);
            	this.stmt.executeUpdate(sql);
                System.out.println("Successfully Updated");

            } finally {
//                GdConnection.close(stmt);
//                GdConnection.close(conn);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }
	public  ResultSet selectQueries(String sql){
//		System.out.println(sql);
		ResultSet rs = null;
//		Connection conn = null;
//        Statement stmt = null;
		try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try {
//            		System.out.println(sql);
//                conn = DriverManager.getConnection(GdConnection.jdbcURL, GdConnection.user, GdConnection.passwd);
//                stmt = conn.createStatement();
                this.stmt.executeUpdate(sql);
                rs = this.stmt.executeQuery(sql);
//                System.out.println(rs.);
//                while (rs.next()) {
//                    Integer s = rs.getInt("id");
//                    String n = rs.getString("lvl");
//                    System.out.println(s + "   " + n);
//                }


            } finally {
//                GdConnection.close(stmt);
//                GdConnection.close(conn);
//                GdConnection.close(rs);
            }
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
        return rs;
	}
	
	public static void main(String[] args){
//		updateQueries("insert into students values(1,'u')");
//		updateQueries("delete from students");
//		QueriesRunner qr=new QueriesRunner();
//		qr.updateQueries("delete from courses_to_topics where topic_id=9");
//		System.out.println(r);
//		ResultSet s=selectQueries("select * from students where USER_ID='kogan' and PASSWORD='kogan'");
//		System.out.println(s);
	}

}

//
//


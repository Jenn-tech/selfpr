package ex1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program3 {

	
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		String title ="test3";
		String content = "hahaha3";
		String files = "";
		int id = 17;
		
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String sql = "update notice "
				+ "set "
				+ "    title =?,"
				+ "    content=?,"
				+ "    files=?"
				+ "where id =?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "system", "oracle");
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, title); //1부터 시작
		st.setString(2, content);
		st.setString(3, files);
		st.setInt(4, id);
		
		int result = st.executeUpdate();
		
		System.out.println(result);
		
		st.close();
		con.close();
		
	}

}

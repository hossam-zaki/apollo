package edu.brown.cs.student.starsTimdb.registrationAndLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.brown.cs.student.starsTimdb.databases.Database;

public class Login {
	
	public String loginUser(String username, String password) {
		Connection conn = Database.getConn();
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement(
					"SELECT * FROM 'doctor' WHERE username= ?");
			prep.setString(1, username);
		    ResultSet rs = prep.executeQuery();
		    if(rs.getString("password").equals(password)) {
		    	System.out.println("login suceeded");
		    }
		    return rs.getString("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}		
	}
}

package registrationAndLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databases.Database;

public class Login {

	public String loginUser(String username, String password) {
		Connection conn = Database.getConn();
		PreparedStatement prep;
		ResultSet rs = null;
		try {
			prep = conn.prepareStatement("SELECT * FROM 'doctor' WHERE username= ?");
			prep.setString(1, username);
			rs = prep.executeQuery();
			Encryption.decrypt(rs.getBytes("password"));
			if (password.equals(Encryption.decrypt(rs.getBytes("password")))) {
				System.out.println("login suceeded");
				return rs.getString("id");
			}
			return null;
		} catch (Exception e) {
			System.err.println("ERROR: retrieving from Db and decrypting password");
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				System.err.println("ERROR: retrieving from Db and decrypting password");
			}
		}
	}
}

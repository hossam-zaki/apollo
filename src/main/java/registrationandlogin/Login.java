package registrationandlogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databases.Database;

/**
 * This is our login class, which authenticates user login via their username
 * and password.
 */
public class Login {
  /**
   * This method takes in a username and a password and queries for the password
   * from username and then checks if the password passed in equals the decryption
   * of the password in the database.
   *
   * @param username , the username the user is using to login.
   * @param password , the password the user is using to login.
   * @return String , the id of the doctor if authenticated, null otherwise.
   **/
  public String loginUser(String username, String password) {
    // establish connection to db.
    Connection conn = Database.getConn();
    PreparedStatement prep;
    ResultSet rs = null;
    try {
      prep = conn.prepareStatement("SELECT * FROM 'doctor' WHERE username= ?");
      prep.setString(1, username);
      rs = prep.executeQuery();
      if (password.equals(Encryption.decrypt(rs.getBytes("password")))) {
        System.out.println("login suceeded");
        String id = rs.getString("id");
        rs.close();
        return id;
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
        return null;
      }
    }
  }
}

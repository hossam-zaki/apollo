package edu.brown.cs.student.starsTimdb.registrationAndLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.brown.cs.student.starsTimdb.databases.Database;

public class Login {

  public String loginUser(String username, String password) {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT * FROM 'doctor' WHERE username= ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      if (password.equals(Encryption.decrypt(rs.getBytes("password")))) {
        System.out.println("login suceeded");
      }
      return rs.getString("id");
    } catch (Exception e) {
      System.err.println("ERROR: retrieving from Db and decrypting password");
      return null;
    }
  }
}

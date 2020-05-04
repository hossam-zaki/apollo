import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.ConnectToDatabase;
import databases.Database;
import registrationandlogin.DoctorRegistration;
import registrationandlogin.Encryption;

public class RegisterTest {

  @Before
  public void setUp() throws Exception {
    Encryption.registerEncryption();
    ConnectToDatabase connect = new ConnectToDatabase();
    connect.executeCommand(null);

  }

  @Test
  public void registerTest() {
    try {
      setUp();
      List<String> registeredUser = new ArrayList<String>();
      registeredUser.add("Nim");
      registeredUser.add("");
      registeredUser.add("Telson");
      registeredUser.add("Nim@brown.edu");
      registeredUser.add("NCS32");
      registeredUser.add("SafePassword");
      registeredUser.add("4018634000");
      registeredUser.add("Alpert Medical School");
      DoctorRegistration register = new DoctorRegistration();
      register.register(registeredUser);
      Connection conn = Database.getConn();
      PreparedStatement prep;
      String password = "SafePassword";

      prep = conn.prepareStatement("SELECT * FROM 'doctor' WHERE username= ?");
      prep.setString(1, "NCS32");
      ResultSet rs = prep.executeQuery();
      assertEquals(password, Encryption.decrypt(rs.getBytes("password")));
      assertEquals("Nim", (rs.getString("first_name")));
      assertEquals("", (rs.getString("mid_name")));
      assertEquals("Telson", (rs.getString("last_name")));
      assertEquals("Nim@brown.edu", (rs.getString("email")));
      assertEquals("NCS32", (rs.getString("username")));
      assertEquals("4018634000", (rs.getString("phoneNumber")));
      assertEquals("Alpert Medical School", (rs.getString("institution")));
      rs.close();

    } catch (Exception e) {
      System.out.println("ERROR: in registerTest");
    }
  }

}

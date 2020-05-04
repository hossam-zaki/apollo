import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import commands.ConnectToDatabase;
import registrationandlogin.DoctorRegistration;
import registrationandlogin.Encryption;
import registrationandlogin.Login;

public class LoginTest {

  @Before
  public void setUp() throws Exception {
    Encryption.registerEncryption();
    ConnectToDatabase connect = new ConnectToDatabase();
    connect.executeCommand(null);
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
  }

  @Test
  public void loginTest() {
    try {
      setUp();
      Login login = new Login();
      assertEquals(login.loginUser("rando", "crptic"), null);
      assertTrue(login.loginUser("NCS32", "SafePassword") != null);
    } catch (Exception e) {
      System.out.println("ERROR: in loginTest");
    }
  }

}

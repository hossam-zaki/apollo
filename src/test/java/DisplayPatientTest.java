import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import apollo.displayPatients;
import commands.ConnectToDatabase;
import patient.PatientRegistration;
import registrationandlogin.DoctorRegistration;
import registrationandlogin.Encryption;
import registrationandlogin.RegisterData;

public class DisplayPatientTest {
  @Before
  public void setUp() {
    try {
      Encryption.registerEncryption();
      ConnectToDatabase connect = new ConnectToDatabase();
      connect.executeCommand(null);
      List<String> registeredUser = new ArrayList<String>();
      registeredUser.add("Nim");
      registeredUser.add("");
      registeredUser.add("Telson");
      registeredUser.add("Nim@brown.edu");
      registeredUser.add("nTelson");
      registeredUser.add("SafePassword");
      registeredUser.add("4018634000");
      registeredUser.add("Alpert Medical School");
      RegisterData register = new DoctorRegistration();
      register.register(registeredUser);

      RegisterData pRegister = new PatientRegistration();
      List<String> registeredPatient = new ArrayList<String>();
      registeredPatient.add("Prithu");
      registeredPatient.add("Jeff");
      registeredPatient.add("Dasgupta");
      registeredPatient.add("02/05/00");
      registeredPatient.add("4015558879");
      registeredPatient.add("prithu@brown.edu");
      registeredPatient.add("4015558870");
      registeredPatient.add("nTelson");
      pRegister.register(registeredPatient);

    } catch (Exception e) {
      System.out.println("ERROR: in DisplayPatientTest");
    }

  }

  @Test
  public void displayPatientTest() {
    String html = displayPatients.buildHTML("nTelson");
    System.out.println(html);
    assertTrue(html.contains("Prithu"));
    assertFalse(html.contains("Lena"));
  }
}

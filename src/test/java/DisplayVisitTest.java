import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import apollo.displayVisits;
import commands.ConnectToDatabase;
import databases.Database;
import patient.PatientDatum;
import patient.PatientRegistration;
import patient.VisitDatum;
import patient.VisitRegistration;
import registrationandlogin.DoctorRegistration;
import registrationandlogin.Encryption;
import registrationandlogin.RegisterData;

public class DisplayVisitTest {
  private String patient_id;

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
      registeredUser.add("nLols");
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
      registeredPatient.add("nLols");
      pRegister.register(registeredPatient);
      Connection conn = Database.getConn();
      PreparedStatement prep;

      prep = conn
          .prepareStatement("SELECT id FROM 'patient' WHERE first_name= ? AND primary_doctor= ?");
      prep.setString(1, "Prithu");
      prep.setString(2, "nLols");
      ResultSet rs = prep.executeQuery();
      patient_id = rs.getString(1);
      rs.close();
      RegisterData visit = new VisitRegistration();
      List<String> visitStrings = new ArrayList<String>();
      visitStrings.add("nLols");
      visitStrings.add(patient_id);
      visitStrings.add("2015-01-20");
      visitStrings.add("11:59");
      visitStrings.add("audio/path");
      visitStrings.add("hello world");
      visitStrings.add("world");
      visitStrings.add("general");
      visit.register(visitStrings);
      List<String> visitStrings2 = new ArrayList<String>();
      visitStrings2.add("nLols");
      visitStrings2.add(patient_id);
      visitStrings2.add("2019-01-20");
      visitStrings2.add("11:59");
      visitStrings2.add("audio/path");
      visitStrings2.add("hello world");
      visitStrings2.add("world");
      visitStrings2.add("physical");
      visit.register(visitStrings2);

    } catch (Exception e) {
      System.out.println("ERROR: in DisplayVisitTest");
    }

  }

  @Test
  public void displayPatientTest() {
    String html = displayVisits.buildHTML("nLols", patient_id);
    System.out.println(html);
    assertTrue(html.contains("physical"));
    assertTrue(html.contains("general"));
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    html = displayVisits.buildHTMLDateRanges("nLols", patient_id, dates);
    assertTrue(html.contains("general"));
    assertFalse(html.contains("physical"));
    Connection conn = Database.getConn();

    try {
      PreparedStatement prep = conn
          .prepareStatement("SELECT visit_id, visit_type FROM 'appointments' WHERE patient_id = ?");
      prep.setString(1, patient_id);

      ResultSet rs = prep.executeQuery();
      String id = "";
      String type = "";
      while (rs.next()) {
        id = rs.getString(1);
        type = Encryption.decrypt(rs.getBytes(2));
        break;
      }

      rs.close();
      Set<String> ids = new HashSet<String>();
      ids.add(id);
      html = displayVisits.buildHTMLid("nLols", patient_id, ids);
      if (type.equals("general")) {
        assertTrue(html.contains("general"));
        assertFalse(html.contains("physical"));
      } else {
        assertTrue(html.contains("physical"));
        assertFalse(html.contains("general"));
      }
    } catch (SQLException e) {
      System.err.println("ERROR: in DisplayVisitTest");
    }

  }

  @Test
  public void getPatientTest() {
    PatientDatum patient = Database.getPatient(patient_id);
    assertTrue(patient.getFirstName().contains("Prithu"));
    assertFalse(patient.getFirstName().contains("Nim"));
  }

  @Test
  public void getVisitTest() {
    List<VisitDatum> visits = Database.getVisits("nLols", patient_id);
    for (VisitDatum visit : visits) {
      if (!visit.getVisitType().contains("general")) {
        assertTrue(visit.getVisitType().contains("physical"));
      }
    }
  }
}

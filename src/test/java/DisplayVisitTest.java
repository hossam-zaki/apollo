import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import apollo.DisplayVisits;
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
    String html = DisplayVisits.buildHTML("nLols", patient_id);
    assertTrue(html.contains("physical"));
    assertTrue(html.contains("general"));
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    html = DisplayVisits.buildHTMLDateRanges("nLols", patient_id, dates);
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
      html = DisplayVisits.buildHTMLid("nLols", patient_id, ids);
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

  @Test
  public void getVisitsfromDateRangesTest() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    assertTrue(visitList.size() == 1);
    assertTrue(visitList.get(0).getVisitType().contains("general"));
    assertFalse(visitList.get(0).getVisitType().contains("physical"));
  }

  @Test
  public void getVisitsFromIds() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    VisitDatum visit = visitList.get(0);
    String id = visit.getID();
    Set<String> ids = new HashSet<String>();
    List<VisitDatum> res = Database.getVisitsFromIds("nLols", patient_id, ids);
    assertTrue(res.size() == 0);
    ids.add(id);
    res = Database.getVisitsFromIds("nLols", patient_id, ids);
    assertTrue(res.size() == 1);
    assertTrue(visitList.get(0).getVisitType().contains("general"));
    assertFalse(visitList.get(0).getVisitType().contains("physical"));
  }

  @Test
  public void getAudioTest() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    VisitDatum visit = visitList.get(0);
    String id = visit.getID();
    String path1 = Database.getAudio("nLols", patient_id, id);
    String path2 = Database.getAudio("nLols", patient_id, "rr");
    String path3 = Database.getAudio("nLols", "dee", id);
    assertTrue(path1.contains("audio/path"));
    assertFalse(path1.contains("path/audio"));
    assertTrue(path2 == null);
    assertTrue(path3 == null);
  }

  @Test
  public void getTranscriptTest() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    VisitDatum visit = visitList.get(0);
    String id = visit.getID();
    String transcript = Database.getTranscript("nLols", patient_id, id);
    String transcript2 = Database.getTranscript("nLols", patient_id, "aba");
    String transcript3 = Database.getTranscript("nLols", "l", id);
    String transcript4 = Database.getTranscript("n", patient_id, id);
    assertFalse(transcript == null);
    assertTrue(transcript2 == null);
    assertTrue(transcript3 == null);
    assertTrue(transcript4 == null);
    assertTrue(transcript.contains("hello world"));
  }

  @Test
  public void getSummary() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    VisitDatum visit = visitList.get(0);
    String id = visit.getID();
    String s = Database.getSummary("nLols", patient_id, id);
    String s1 = Database.getSummary("nLols", "", id);
    String s2 = Database.getSummary("nLols", patient_id, "");
    String s3 = Database.getSummary("n", patient_id, id);
    assertTrue(s.equals("world"));
    assertTrue(s1 == null);
    assertTrue(s2 == null);
    assertTrue(s3 == null);
  }

  @Test
  public void getVisitType() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    VisitDatum visit = visitList.get(0);
    String id = visit.getID();
    String r = Database.getVisitType("nLols", patient_id, id);
    assertTrue(r.equals("general"));
    assertFalse(r.equals(""));
    String r2 = Database.getVisitType("nLols", patient_id, "");
    assertTrue(r2 == null);
    String r3 = Database.getVisitType("nLols", "", id);
    assertTrue(r3 == null);
  }

  @Test
  public void getAllTranscripts() {
    List<String> dates = new ArrayList<String>();
    dates.add("2015-01-19");
    dates.add("2015-01-21");
    List<VisitDatum> visitList = Database.getVisitsFromDateRanges("nLols", patient_id, dates);
    VisitDatum visit = visitList.get(0);
    String id = visit.getID();
    Map<String, String> res = Database.getAllTranscripts(patient_id);
    assertTrue(res.get(id).equals("hello world"));
  }

  @Test
  public void getPatientByNameTest() {
    String p = Database.getPatientByName("Nim");
    assertTrue(p == null);
    String p2 = Database.getPatientByName("Prithu");
    assertTrue(p2.equals("Dasgupta"));
  }

  @Test
  public void getDateByDoctorNameTest() {
    String res = Database.getDateByDoctorName("nLols");
    assertTrue(res.equals("2015-01-20"));
    assertFalse(res.equals("2019-01-20"));
    res = Database.getDateByDoctorName("n");
    assertTrue(res == null);
  }

  @Test
  public void getDateofAppointmentTest() {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT visit_id FROM 'appointments' WHERE patient_id = ?");
      prep.setString(1, patient_id);

      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        String id = rs.getString(1);
        assertTrue(Database.isValidDate(id, "2015-01-20"));
        assertFalse(Database.isValidDate(id, "2000-05-23"));
        break;
      }
      rs.close();
    } catch (SQLException e) {
      System.err.println("ERROR: in getDateofAppointmentTest");
    }
  }

}

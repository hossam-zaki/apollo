import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import commands.ConnectToDatabase;
import patient.PatientDatum;
import patient.PatientRegistration;
import patient.VisitDatum;
import patient.VisitRegistration;
import registrationandlogin.Encryption;

public class PatientTest {

  @Test
  public void patientDatumTest() {
    PatientDatum patient = new PatientDatum("1", "first", "middle", "last", "4/5/2003",
        "7814245312", "me@apple.com", "7814231234", "docMcgee");
    patient.setID("2");
    assertTrue(patient.getID().equals("2"));
    patient.setFirstName("bill");
    assertTrue(patient.getFirstName().equals("bill"));
    patient.setMiddleName("albert");
    assertTrue(patient.getMiddleName().equals("albert"));
    patient.setLastName("johnson");
    assertTrue(patient.getLastName().equals("johnson"));
    patient.setDateOfBirth("2/3/1994");
    assertTrue(patient.getDateOfBirth().equals("2/3/1994"));
    patient.setPhoneNumber("401");
    assertTrue(patient.getPhoneNumber().equals("401"));
    patient.setEmail("hi@me.co");
    assertTrue(patient.getEmail().equals("hi@me.co"));
    patient.setEmergencyPhoneNumber("302");
    assertTrue(patient.getEmergencyPhoneNumber().equals("302"));
    patient.setDoctorUsername("doc2");
    assertTrue(patient.getPrimaryDoctorUsername().equals("doc2"));
  }

  @Test
  public void visitDatumTest() {
    String audioRecording = "audio";
    Encryption.registerEncryption();
    VisitDatum visit = new VisitDatum("id", "doctor", "visit", "date",
        Encryption.encrypt("transcript"), null, "time", Encryption.encrypt("type"));
    visit.setID("2");
    assertTrue(visit.getID().equals("2"));
    visit.setAudioRecording(Encryption.encrypt(audioRecording));
    assertTrue(visit.getAudioRecording().equals(audioRecording));
    visit.setDate("2/3/1994");
    assertTrue(visit.getDate().equals("2/3/1994"));
    visit.setTranscript(Encryption.encrypt("newTranscript"));
    assertTrue(visit.getTranscript().equals("newTranscript"));
    visit.setTime("5:00");
    assertTrue(visit.getTime().equals("5:00"));
    assertTrue(visit.getPID().equals("visit"));
  }

  @Test
  public void registerTest() {
    ConnectToDatabase connect = new ConnectToDatabase();
    connect.executeCommand(new ArrayList<String>());
    PatientRegistration registerPatient = new PatientRegistration();
    List<String> patientString = new ArrayList<String>();
    patientString.add("first");
    patientString.add("middle");
    patientString.add("last");
    patientString.add("4/5/2003");
    patientString.add("7814245312");
    patientString.add("me@apple.com");
    patientString.add("1");
    patientString.add("docMcgee");
    registerPatient.register(patientString);
    VisitRegistration registerVisit = new VisitRegistration();
    registerVisit.register("doctor", "visit", "date", "transcript", "audio", "time", "1", "type");
  }
}

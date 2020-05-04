import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import commands.ConnectToDatabase;
import databases.Database;
import patient.PatientDatum;
import patient.PatientRegistration;
import patient.VisitDatum;
import patient.VisitRegistration;
import registrationandlogin.Encryption;
import registrationandlogin.RegisterData;

public class PatientTest {

  @Test
  public void patientDatumTest() {
    List<String> patientInfo = new ArrayList<String>();
    patientInfo.add("1"); // id
    patientInfo.add("first"); // firstname
    patientInfo.add("middle"); // midname
    patientInfo.add("last"); // lastname
    patientInfo.add("4/5/2003"); // dob
    patientInfo.add("7814245312"); // phone
    patientInfo.add("me@apple.com"); // email
    patientInfo.add("7814231234"); // emergency number
    patientInfo.add("docMcgee"); // docusername
    PatientDatum patient = new PatientDatum(patientInfo);
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
    List<String> details = new ArrayList<String>();
    details.add("id");
    details.add("doctor");
    details.add("visit");
    details.add("date");
    VisitDatum visit = new VisitDatum(details, Encryption.encrypt("transcript"), null, "time",
        Encryption.encrypt("type"));
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
    RegisterData registerPatient = new PatientRegistration();
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
    assertTrue(Database.getPatientByName("first").equals("last"));
    RegisterData registerVisit = new VisitRegistration();
    List<String> visitString = new ArrayList<String>();
    visitString.add("doctor");
    visitString.add("visit");
    visitString.add("date");
    visitString.add("transcript");
    visitString.add("audio");
    visitString.add("time");
    visitString.add("1");
    visitString.add("type");
    registerVisit.register(visitString);
    assertTrue(Database.getDateByDoctorName("doctor").equals("date"));
  }
}

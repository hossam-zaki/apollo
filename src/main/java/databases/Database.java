package databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import patient.PatientDatum;
import patient.VisitDatum;

/**
 * This is my database class, where I read from a database and make the instance
 * of a connection. I have a getter method to get the connection to the
 * database.
 */
public final class Database {

  private static Connection conn;

  /**
   * Empty necessary constructor for private initialization.
   */
  private Database() {
  }

  /**
   * Instantiates the database, creating tables if necessary. Automatically loads
   * files.
   *
   * @return int , 1 for correct, 0 for errors.
   */
  public static int makeDatabase() {
    // Initializes the database connection, turns foreign keys on.
    String filename = "data/db/apollo.sqlite3";
    try {
      FileReader file = new FileReader(filename);
    } catch (FileNotFoundException e1) {
      System.err.println("ERROR: File does not exist");
      return 0;
    }
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      System.err.println("ERROR: Database");
      return 0;
    }
    String urlToDB = "jdbc:sqlite:" + filename;
    try {
      conn = DriverManager.getConnection(urlToDB);
    } catch (SQLException e) {
      System.err.println("ERROR: Database");
      return 0;
    }
    // these two lines tell the database to enforce foreign keys during
    // operations,
    // and should be present
    Statement stat;
    try {
      stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
    } catch (SQLException e) {
      System.err.println("ERROR: Database");
      return 0;
    }
    return 1;
  }

  /**
   * Method that gets the connection to a database.
   *
   * @return connection to a database
   */
  public static Connection getConn() {
    return conn;
  }

  public static String getDocName(String username) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT last_name FROM doctor WHERE username = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      String toRet = "";
      while (rs.next()) {
        toRet = rs.getString(1);
      }
      return toRet;
    } catch (Exception e) {
      System.err.println("ERROR: couldn't find doctor name");
      return null;
    }
  }

  /**
   * This method executes the query that checks if a username is valid or not.
   * Validity in this case means weather or not a username has already been taken
   * or not.
   *
   * @param username A potiential new doctor username.
   * @return A boolean, true if the username is free, false otherwise.
   */
  public static boolean checkValidUsername(String username) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT username FROM doctor WHERE username = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      rs.getString(1);
      return true;
    } catch (Exception e) {
      System.err.println("ERROR: couldn't find doctor name");
      return false;
    }
  }

  /**
   * This method executes the query that find all the patients given a doctor's
   * username.
   *
   * @param username A String, representing a doctor's username.
   * @return A List of PatientDatums, representing all patients for a given
   *         doctor.
   */
  public static List<PatientDatum> getDoctorPatients(String username) {
    PreparedStatement prep;
    try {
      List<PatientDatum> toRet = new ArrayList<PatientDatum>();
      prep = conn.prepareStatement("SELECT * FROM patient WHERE primary_doctor = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        PatientDatum curr = new PatientDatum(rs.getString("id"), rs.getString(2), rs.getString(3),
            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
            rs.getString(9));
        toRet.add(curr);
      }
      return toRet;
    } catch (Exception e) {
      System.err.println("ERROR: no patients found");
      return null;
    }
  }

  /**
   * This method executes the query that retrieves a doctor's accoutn details.
   *
   * @param username A String, representing a doctor username.
   * @return A Map from Striing to String, where the key is the field of doctor
   *         information and the value is the detail.
   */
  public static Map<String, String> getDoctorInfo(String username) {
    PreparedStatement prep;
    try {
      Map<String, String> toRet = new LinkedHashMap<String, String>();
      prep = conn.prepareStatement("SELECT * FROM doctor WHERE username = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        toRet.put("first name:", rs.getString(2)); // first name
        toRet.put("middle name:", rs.getString(3)); // middle name
        toRet.put("last name:", rs.getString(4)); // last name
        toRet.put("email:", rs.getString(5)); // email
        toRet.put("username:", rs.getString(6)); // username
        // toRet.put("password", rs.getString(7)); // password
        toRet.put("medical institution:", rs.getString(9)); // medical
                                                            // institution
      }
      return toRet;
    } catch (Exception e) {
      System.err.println("ERROR: doctor not found");
      return null;
    }
  }

  /**
   * This method executes the query that retrieves a patient's information given a
   * patients unique ID.
   *
   * @param id A String, representing a patient's unique ID.
   * @return A patientDatum, representing the patientDatum's.
   */
  public static PatientDatum getPatient(String id) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("SELECT * FROM patient WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      PatientDatum patient = new PatientDatum(rs.getString(1), // id
          rs.getString(2), // firstname
          rs.getString(3), // midname
          rs.getString(4), // lastname
          rs.getString(5), // dob
          rs.getString(6), // phone
          rs.getString(7), // email
          rs.getString(8), // emergency number
          rs.getString(9)); // docusername
      return patient;
    } catch (SQLException e) {
      System.err.println("ERROR: Patient not found");
      return null;
    }
  }

  /**
   * This method executes the query that finds all visits given a doctor's
   * username and a patientId.
   *
   * @param docUsername A String, representing the doctor's username.
   * @param patientID   A String, representing a patient's ID.
   * @return A list of visitDatums, representing all visits between the given
   *         doctor and patient.
   */
  public static List<VisitDatum> getVisits(String docUsername, String patientID) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ?");
      prep.setString(1, docUsername);
      prep.setString(2, patientID);
      ResultSet rs = prep.executeQuery();
      List<VisitDatum> toRet = new ArrayList<VisitDatum>();
      while (rs.next()) {
        VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3),
            rs.getString(4), rs.getString(7), rs.getBytes(6), rs.getString(5));
        toRet.add(curr);
      }
      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No visits found");
      return null;
    }
  }

  /**
   * This method gets all visits between a given date range.
   *
   * @param docUsername A String, representing a doctor's username.
   * @param patientID   A String, representing a patient's ID.
   * @param dates       A List of Strings, representing dates.
   * @return A List of VisitDatums, representing all visits between a patient and
   *         a doctor in specific dates.
   */
  public static List<VisitDatum> getVisitsFromDates(String docUsername, String patientID,
      Set<String> dates) {
    PreparedStatement prep;
    try {
      List<VisitDatum> toRet = new ArrayList<VisitDatum>();
      for (String date : dates) {
        prep = conn.prepareStatement(
            "SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ? AND appointment_date = ?");
        prep.setString(1, docUsername);
        prep.setString(2, patientID);
        prep.setString(3, date);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
          VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3),
              rs.getString(4), rs.getString(7), rs.getBytes(6), rs.getString(5));
          toRet.add(curr);
        }
      }
      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No visits found");
      return null;
    }
  }

  /**
   * This method executes the query that finds all visits between a doctor and a
   * patient given a date range.
   *
   * @param docUsername A String, representing a doctor's username.
   * @param patientID   A String, representing a patientID.
   * @param dates       A List of Strings, representing a date range.
   * @return A List of VisitDatums, representing all visits between a doctor and a
   *         patient in a given dateRange.
   */
  public static List<VisitDatum> getVisitsFromDateRanges(String docUsername, String patientID,
      List<String> dates) {
    PreparedStatement prep;
    try {
      List<VisitDatum> toRet = new ArrayList<VisitDatum>();
      prep = conn.prepareStatement(
          "SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ? AND appointment_date BETWEEN ? AND ?");
      prep.setString(1, docUsername);
      prep.setString(2, patientID);
      prep.setString(3, dates.get(0));
      prep.setString(4, dates.get(1));
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3),
            rs.getString(4), rs.getString(7), rs.getBytes(6), rs.getString(5));
        toRet.add(curr);
      }

      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No visits found");
      return null;
    }
  }

  /**
   * This method executes the query that finds all visits given a doctor's
   * username, a patient's ID, and a list of visit IDs.
   *
   * @param docUsername A String, representing a doctor's username.
   * @param patientID   A String, representing a patient's ID.
   * @param Ids         A Set of Strings, representing visits IDs.
   * @return A List of VisitDatums, representing a list of visits corresponding to
   *         the doctor's ID, patient ID, and visit IDs.
   */
  public static List<VisitDatum> getVisitsFromIds(String docUsername, String patientID,
      Set<String> Ids) {
    PreparedStatement prep;
    try {
      List<VisitDatum> toRet = new ArrayList<VisitDatum>();
      for (String id : Ids) {
        prep = conn.prepareStatement(
            "SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
        prep.setString(1, docUsername);
        prep.setString(2, patientID);
        prep.setString(3, id);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
          VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3),
              rs.getString(4), rs.getString(7), rs.getBytes(6), rs.getString(5));
          toRet.add(curr);
        }
      }
      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No visits found");
      return null;
    }
  }

  /**
   * This method executes the query that retrieves the audio recording for a
   * giiven viisit.
   *
   * @param docUsername A String, representing a doctor's username.
   * @param patientID   A String, representing a patient's ID.
   * @param id          A String, representing a visit ID.
   * @return A String, representing a path to a visit's audtio file.
   */
  public static String getAudio(String docUsername, String patientID, String id) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT audio_file FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
      prep.setString(1, docUsername);
      prep.setString(2, patientID);
      prep.setString(3, id);
      ResultSet rs = prep.executeQuery();
      String toRet = null;
      while (rs.next()) {
        toRet = rs.getString(1);
      }
      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No audio file found");
      return null;
    }
  }

  /**
   * This method executes the query that gets the transcript for a certain visit.
   *
   * @param docUsername A String, representing a doctor's username.
   * @param patientID   A String, representing a patient ID.
   * @param id          A String, representing a visit ID.
   * @return A String, representing the visit's transcript.
   */
  public static String getTranscript(String docUsername, String patientID, String id) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT transcript FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
      prep.setString(1, docUsername);
      prep.setString(2, patientID);
      prep.setString(3, id);
      ResultSet rs = prep.executeQuery();
      String toRet = null;
      while (rs.next()) {
        toRet = rs.getString(1);
      }
      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No audio file found");
      return null;
    }
  }

  /**
   * This method executes the query that finds summary of a certain visit.
   *
   * @param docUsername A String, representing the a doctor's username.
   * @param patientID   A String, representing a patient's ID.
   * @param id          A String, representing a visit's ID.
   * @return A String, representing a visit's summary.
   */
  public static String getSummary(String docUsername, String patientID, String id) {
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "SELECT summary FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
      prep.setString(1, docUsername);
      prep.setString(2, patientID);
      prep.setString(3, id);
      ResultSet rs = prep.executeQuery();
      String toRet = null;
      while (rs.next()) {
        toRet = rs.getString(1);
      }
      return toRet;
    } catch (SQLException e) {
      System.err.println("ERROR: No audio file found");
      return null;
    }

  }

  /**
   * This method executes the query that gets all transcripts for a set of visits.
   *
   * @param id A String, representing a patient ID.
   * @return A Map from String to String, representing dates and transcripts for
   *         visits.
   */
  public static Map<String, String> getAllTranscripts(String id) {
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT visit_id, transcript FROM appointments WHERE patient_id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      Map<String, String> transcripts = new HashMap<String, String>();
      while (rs.next()) {
        transcripts.put(rs.getString(1), rs.getString(2));
      }
      return transcripts;
    } catch (SQLException e) {
      System.err.println("ERROR: Patient not found");
      return null;
    }
  }

}

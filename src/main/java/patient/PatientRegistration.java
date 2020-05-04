package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import databases.Database;
import registrationandlogin.RegisterData;

/**
 * Class to deal with registering a patient into the database.
 */
public class PatientRegistration implements RegisterData {

  static final int COL7 = 7;
  static final int COL8 = 8;
  static final int COL9 = 9;

  /**
   * Register a patient, inserting them as a row in the patient table.
   *
   * @param registrationDetails to set for patient
   */
  @Override
  public void register(List<String> registrationDetails) {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("INSERT INTO patient VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString());
      prep.setString(2, registrationDetails.get(0)); // first name
      prep.setString(3, registrationDetails.get(1)); // middle name
      prep.setString(4, registrationDetails.get(2)); // last name
      prep.setString(5, registrationDetails.get(3)); // DOB
      prep.setString(6, registrationDetails.get(4)); // phone
      prep.setString(COL7, registrationDetails.get(5)); // email
      prep.setString(COL8, registrationDetails.get(6)); // emergency phone
      prep.setString(COL9, registrationDetails.get(COL7).toString()); // doctor's id
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      System.err.println("ERROR: error connecting to database for registration");
    }
  }
}

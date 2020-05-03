package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import databases.Database;

/**
 * Class to deal with registering a patient into the database.
 */
public class PatientRegistration {

  /**
   * Register a patient, inserting them as a row in the patient table.
   *
   * @param registrationDetails to set for patient
   */
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
      prep.setString(7, registrationDetails.get(5)); // email
      prep.setString(8, registrationDetails.get(6)); // emergency phone
      prep.setString(9, registrationDetails.get(7).toString()); // doctor's id
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      System.err.println("ERROR: error connecting to database for registration");
    }
  }
}
package registrationandlogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import databases.Database;

/**
 * This is our registration class that inserts into out doctor table in our
 * database, and 'registers' a new user (doctor).
 */
public class DoctorRegistration implements RegisterData {
  /**
   * This is a method where we take in a list of values that are information about
   * the doctor ( email, phone, username), which will be used for registering the
   * doctor and using that information they will be able to login.
   *
   * @param registrationDetails , the list of information to insert into db.
   */
  @Override
  public void register(List<String> registrationDetails) {
    // get db connection.
    Connection conn = Database.getConn();
    PreparedStatement prep;
    final int seven = 7;
    final int eight = 8;
    final int nine = 9;
    try {
      prep = conn.prepareStatement("INSERT INTO doctor VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString()); // UUID
      prep.setString(2, registrationDetails.get(0)); // first name
      prep.setString(3, registrationDetails.get(1)); // middle name
      prep.setString(4, registrationDetails.get(2)); // last name
      prep.setString(5, registrationDetails.get(3)); // email
      prep.setString(6, registrationDetails.get(4)); // username
      prep.setBytes(seven, Encryption.encrypt(registrationDetails.get(5))); // password
      prep.setString(eight, registrationDetails.get(6)); // phone number
      prep.setString(nine, registrationDetails.get(seven)); // instituion
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) {
      System.err.println("ERROR: SQL error inserting into Db");
      return;
    }

  }
}

package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import databases.Database;
import registrationandlogin.Encryption;
import registrationandlogin.RegisterData;

/**
 * Class to deal with registering a visit into the database.
 */
public class VisitRegistration implements RegisterData {

  static final int COL7 = 7;
  static final int COL8 = 8;
  static final int COL9 = 9;

  @Override
  public void register(List<String> registrationDetails) {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString());
      prep.setString(2, registrationDetails.get(0)); // doctor username
      prep.setString(3, registrationDetails.get(1)); // patient id
      prep.setString(4, registrationDetails.get(2)); // date
      // String path = "data/hendrix.wav";
      prep.setString(5, registrationDetails.get(3));
      prep.setBytes(6, Encryption.encrypt(registrationDetails.get(4))); // audio file
      prep.setBytes(COL7, Encryption.encrypt(registrationDetails.get(5))); // transcript
      prep.setBytes(COL8, Encryption.encrypt(registrationDetails.get(6))); // summary
      prep.setBytes(COL9, Encryption.encrypt(registrationDetails.get(COL7))); // visit Type.
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      System.err.println("ERROR: error connecting to database for registration");
    }
  }
}

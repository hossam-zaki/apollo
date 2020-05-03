package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import databases.Database;
import registrationandlogin.Encryption;

public class VisitRegistration {
  public void register(String docUsername, String patientID, String date, String time, String audio,
      String transcript, String summary, String visitType) {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString());
      prep.setString(2, docUsername); // doctor username
      prep.setString(3, patientID); // patient id
      prep.setString(4, date); // date
      // String path = "data/hendrix.wav";
      prep.setString(5, time);
      prep.setBytes(6, Encryption.encrypt(audio)); // audio file
      prep.setBytes(7, Encryption.encrypt(transcript)); // transcript
      prep.setBytes(8, Encryption.encrypt(summary)); // summary
      prep.setBytes(9, Encryption.encrypt(visitType)); // visit Type.
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

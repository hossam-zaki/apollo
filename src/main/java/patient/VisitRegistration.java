package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

import databases.Database;

public class VisitRegistration {
  public void register(String docUsername, String patientID, String date, String time, String audio,
      String transcript, String summary) {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("INSERT INTO appointments VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString());
      prep.setString(2, docUsername); // doctor username
      prep.setString(3, patientID); // patient id
      prep.setString(4, date); // date
      // String path = "data/hendrix.wav";
      prep.setString(5, time);
      prep.setString(6, audio); // audio file
      prep.setString(7, transcript); // transcript
      prep.setString(8, summary); // summary
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      System.err.println("ERROR: error connecting to database for registration");
    }
  }

}

package registrationAndLogin;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import databases.Database;

public class VisitRegistration {
  public void register(String docUsername, String patientID, String date,
      String audio, String transcript) {
    Connection conn = Database.getConn();
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(
          "INSERT INTO appointments VALUES (?, ?, ?, ?, ?);");
      prep.setString(1, docUsername); // doctor username
      prep.setString(2, patientID); // patient id
      prep.setString(3, date); // date
      //String path = "data/hendrix.wav";
      prep.setString(4, audio); // audio file
      prep.setString(5, transcript); // transcript
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}

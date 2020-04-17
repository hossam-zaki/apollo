package edu.brown.cs.student.starsTimdb.registrationAndLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import edu.brown.cs.student.starsTimdb.databases.Database;

public class Registration {

  public void register(List<String> registrationDetails) {
    Connection conn = Database.getConn();

    PreparedStatement prep;
    try {
      prep = conn.prepareStatement("INSERT INTO patient VALUES (?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString());
      prep.setString(2, registrationDetails.get(0)); // first name
      prep.setString(3, registrationDetails.get(1)); // last name
      prep.setString(4, registrationDetails.get(2)); // DOB
      prep.setString(4, registrationDetails.get(3)); // email
      prep.setString(4, registrationDetails.get(4)); // phone number
      prep.setString(4, registrationDetails.get(5)); // emergency contact
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}

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
      prep = conn.prepareStatement("INSERT INTO doctor VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
      UUID uuid = UUID.randomUUID();
      prep.setString(1, uuid.toString());
      prep.setString(2, registrationDetails.get(0));
      prep.setString(3, registrationDetails.get(1));
      prep.setString(4, registrationDetails.get(2));
      prep.setString(5, registrationDetails.get(3));
      prep.setString(6, registrationDetails.get(4));
      prep.setString(7, Encryption.encrypt(registrationDetails.get(5))); // password
      prep.setString(8, registrationDetails.get(6));
      prep.setString(9, registrationDetails.get(7));
      prep.addBatch();
      prep.executeBatch();
    } catch (Exception e) { // ask about this
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}

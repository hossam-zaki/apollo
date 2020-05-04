package databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class sets up the databases needed for the Apollo platform.
 */
public final class DatabaseSetup {

  /**
   * constructor to prevent this class from being instantiated.
   */
  private DatabaseSetup() {
    // not called
  }

  /**
   * Connect to a sample database.
   *
   */
  public static void createNewDatabase() {

    String url = "jdbc:sqlite:data/db/apollo.sqlite3";
    try {
      FileReader file = new FileReader(url);
      return;
    } catch (FileNotFoundException e1) {
      System.out.println("Generating Database...");
    }

    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        PreparedStatement prep;
        // Creates the table to store doctor details
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS doctor(" + "id UUID,"
            + "first_name TEXT," + "mid_name TEXT," + "last_name TEXT," + "email NVARCHAR(320),"
            + "username NVARCHAR(320) UNIQUE," + "password NVARCHAR(320),"
            + "phoneNumber NVARCHAR(320)," + "institution TEXT);");
        prep.executeUpdate();
        System.out.println("A new database has been created.");
        // Creates the table to store patient details
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS patient(" + "id UUID,"
            + "first_name TEXT," + "mid_name TEXT," + "last_name TEXT," + "date_of_birth TEXT,"
            + "email NVARCHAR(320)," + "phoneNumber NVARCHAR(320)," + "emergency_phone_number TEXT,"
            + "primary_doctor TEXT);");
        prep.executeUpdate();
        // Creates the table to store visit detials
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS appointments(visit_id UUID,"
            + "doctor_username TEXT," + "patient_id TEXT," + "appointment_date Text," + "time TEXT,"
            + "audio_file NVARCHAR(320)," + "transcript  NVARCHAR(320)," + "summary  NVARCHAR(320),"
            + "visit_type NVARCHAR(320));");
        prep.executeUpdate();
        System.out.println("A new database has been created.");
      }
    } catch (SQLException e) {
      System.err.println("ERROR: db not made");
    }
  }
}

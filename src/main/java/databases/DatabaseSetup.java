package databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sqlitetutorial.net
 */
public class DatabaseSetup {

  /**
   * Connect to a sample database
   *
   * @param fileName the database file name
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
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS doctor("
            + "id UUID," + "first_name TEXT," + "mid_name TEXT,"
            + "last_name TEXT," + "email NVARCHAR(320),"
            + "username NVARCHAR(320)," + "password NVARCHAR(320),"
            + "phoneNumber NVARCHAR(320)," + "institution TEXT);");
        prep.executeUpdate();
        System.out.println("A new database has been created.");
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS patient("
            + "id UUID," + "first_name TEXT," + "mid_name TEXT,"
            + "last_name TEXT," + "date_of_birth TEXT,"
            + "phoneNumber NVARCHAR(320)," + "email NVARCHAR(320),"
            + "emergency_phone_number TEXT," + "primary_doctor TEXT);");
        prep.executeUpdate();
        System.out.println("A new database has been created.");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}

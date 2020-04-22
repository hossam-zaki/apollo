package databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import patientData.PatientDatum;
import patientData.VisitDatum;

/**
 * This is my database class, where I read from a database and make the instance
 * f a connection. I have a getter method to get the connection to the database.
 */
public final class Database {

  private static Connection conn;

  /**
   * Empty necessary constructor for private initialization.
   */
  private Database() {
  }

  /**
   * Instantiates the database, creating tables if necessary. Automatically
   * loads files.
   *
   * @param filename , file name of SQLite3 database to open.
   * @return int , 1 for correct, 0 for errors.
   */
  public static int makeDatabase() {
    // Initializes the database connection, turns foreign keys on.
    String filename = "data/db/apollo.sqlite3";
    try {
      FileReader file = new FileReader(filename);
    } catch (FileNotFoundException e1) {
      System.err.println("ERROR: File does not exist");
      return 0;
    }
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      System.err.println("ERROR: Database");
      return 0;
    }
    String urlToDB = "jdbc:sqlite:" + filename;
    try {
      conn = DriverManager.getConnection(urlToDB);
    } catch (SQLException e) {
      System.err.println("ERROR: Database");
      return 0;
    }
    // these two lines tell the database to enforce foreign keys during
    // operations,
    // and should be present
    Statement stat;
    try {
      stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
    } catch (SQLException e) {
      System.err.println("ERROR: Database");
      return 0;
    }
    return 1;
  }

  /**
   * Method that gets the connection to a database.
   *
   * @return connection to a database
   */
  public static Connection getConn() {
    return conn;
  }

  public static String getDocName(String username) {
    PreparedStatement prep;
    try {
      prep = conn
          .prepareStatement("SELECT last_name FROM doctor WHERE username = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      String toRet = "";
      while (rs.next()) {
        toRet = rs.getString(1);
      }
      return toRet;
    } catch (Exception e) {
      System.err.println("ERROR: couldn't find doctor name");
      return null;
    }
  }

  public static List<PatientDatum> getDoctorPatients(String username) {
    PreparedStatement prep;
    try {
      List<PatientDatum> toRet = new ArrayList<PatientDatum>();
      prep = conn
          .prepareStatement("SELECT * FROM patient WHERE primary_doctor = ?");
      prep.setString(1, username);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        PatientDatum curr = new PatientDatum(rs.getString("id"),
            rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
        toRet.add(curr);
      }
      return toRet;
    } catch (Exception e) {
      System.err.println("ERROR: no patients found");
      return null;
    }
  }

  public static List<VisitDatum> getPatientVisits(String id) {
    PreparedStatement prep;
    try {
      List<VisitDatum> toRet = new ArrayList<VisitDatum>();
      prep = conn.prepareStatement("SELECT * FROM visit WHERE id = ?");
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), null,
            null);
        toRet.add(curr);
      }
      return toRet;
    } catch (Exception e) {
      System.err.println("ERROR: no visits found");
      return null;
    }
  }
}

package databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
      System.err.println("ERROR: Databse");
      return 0;
    }
    String urlToDB = "jdbc:sqlite:" + filename;
    try {
      conn = DriverManager.getConnection(urlToDB);
    } catch (SQLException e) {
      System.err.println("ERROR: Databse");
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
      System.err.println("ERROR: Databse");
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
}

package commands;

import java.util.List;

import databases.Database;
import databases.DatabaseSetup;
import repl.Executable;

/**
 * Executable class that will connect the sqlite3 database to the backend.
 */
public class ConnectToDatabase implements Executable {

  /**
   * Method inherited from executable interface that will build a new database.
   * If it doesn't exist, and will establish connection.
   */
  @Override
  public void executeCommand(List<String> string) {
    DatabaseSetup.createNewDatabase();
    Database.makeDatabase();
  }

}

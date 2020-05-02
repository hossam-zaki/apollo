package repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class will build and run the repl.
 *
 */
public class Repl {

  private BufferedReader reader;

  /**
   * Constructor. This will make the BufferedReader, and will wait for the
   * startRepl method to be called.
   */
  public Repl() {
    reader = new BufferedReader(new InputStreamReader(System.in));
  }

  /**
   * Calling this method will begin the loop of the REPl, and will process inputs
   * that the user types in.
   *
   * @throws IOException will throw Exception if I/O error occurs
   */
  public void startRepl() throws IOException {
    String input;
    ReplHandler handler = new ReplHandler();
    try {
      while ((input = reader.readLine()) != null) {
        handler.processLine(input);
      }
      reader.close();
    } catch (IOException e) {
      System.err.println("ERROR: Error occured! Please Restart!");
    }
  }
}

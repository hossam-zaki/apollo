package edu.brown.cs.student.starsTimdb.repl;

import java.util.HashMap;

/**
 * This class will handle the lines that are read from the repl.
 */
public class ReplHandler {
  private HashMap<String, Executable> commandHashMap;

  /**
   * The constructor. Here is the hashmap of strings and classes that implement
   * the Executable interface. Removes the need for one giant IF statement.
   */
  public ReplHandler() {
    commandHashMap = new HashMap<String, Executable>();
  }
/**
 * Takes in the string from the REPL and runs the proper command.
 * @param replInput string that the user types in in the REPl.
 */
  public void processLine(String replInput) {
    String[] splittedInput = replInput.split("\\s+");
    if (!commandHashMap.containsKey(splittedInput[0])) {
      System.out.println("ERROR: Input Valid Command");
    } else {
      commandHashMap.get(splittedInput[0]).executeCommand(splittedInput);

    }
  }
}

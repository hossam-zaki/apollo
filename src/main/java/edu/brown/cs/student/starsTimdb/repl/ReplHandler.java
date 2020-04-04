package edu.brown.cs.student.starsTimdb.repl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.brown.cs.student.starsTimdb.commands.ConnectToDatabase;

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
    commandHashMap.put("build", new ConnectToDatabase());
  }
  /**
   * Takes in the string from the REPL and runs the proper command.
   * @param input string that the user types in in the REPl.
   */
  public void processLine(String input) {
    ArrayList<String> inputSplit = new ArrayList<String>();
    Pattern regex = Pattern.compile("(\"[^\"]*\"|'[^']*'|[\\S]+)+");
    Matcher regexMatcher = regex.matcher(input);
    while (regexMatcher.find()) {
      inputSplit.add(regexMatcher.group());
    }
    if (inputSplit.size() == 0 || !commandHashMap.containsKey(inputSplit.get(0))) {
      System.out.println("ERROR: Input Valid Command");
    } else {
      commandHashMap.get(inputSplit.get(0)).executeCommand(inputSplit);

    }
  }
}

package repl;

import java.util.List;

/**
 * This is an interface for every executable command. It has a method called
 * execute command that will take in a list of String, and will execute it based
 * on whatever is in the input.
 */
public interface Executable {
  /**
   * This is the method to be implemented by every class that implements
   * Executable.
   *
   * @param string list of strings to process on.
   */
  void executeCommand(List<String> string);
}

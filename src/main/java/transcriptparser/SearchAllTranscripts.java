package transcriptparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import databases.Database;
import repl.Executable;
import searchalgorithms.KMP;
import searchalgorithms.Search;

/**
 * This class executes the search by keyword on all transcripts from all visits
 * belonging to a doctor for a certain patient.
 *
 */
public class SearchAllTranscripts implements Executable {
  private Map<String, String> transcripts;
  private String pattern;

  /**
   * Empty constructor.
   */
  public SearchAllTranscripts() {
    transcripts = new HashMap<String, String>();
    pattern = "";
  }

  /**
   * This is the constructor which uses transcripts and a pattern.
   *
   * @param transcripts A Map from String to String, representing treanscripts for
   *                    a corresponding visit.
   * @param pattern     A String, representing the pattern we are looking for.
   */
  public SearchAllTranscripts(Map<String, String> transcripts, String pattern) {
    this.transcripts = transcripts;
    this.pattern = pattern;
  }

  /**
   * This is the constructor which uses a patient UUID and a pattern.
   *
   * @param patient A String, representing a patient's UUID.
   * @param pattern A String, representing the pattern we are looking for.
   */
  public SearchAllTranscripts(UUID patient, String pattern) {
    this.transcripts = Database.getAllTranscripts(patient.toString());
    this.pattern = pattern;
  }

  /**
   * This method finds the pattern in the transcript.
   *
   * @param transcript A String, representing the full visit transcript.
   * @return A List of Integers, representing indexes where the pattern was found.
   */
  public List<Integer> findPattern(String transcript) {
    List<Integer> found = new ArrayList<Integer>();
    Search current = new KMP(transcript.toCharArray());
    found = current.search(pattern.toCharArray());
    return found;
  }

  /**
   * This methos finds results from searched patter.
   *
   * @return A Map from String to a List of Strings, represeting which visits we
   *         found the pattern in.
   */
  public Map<String, List<Integer>> getAllResults() {
    Map<String, List<Integer>> allResults = new HashMap<String, List<Integer>>();
    if (transcripts == null || transcripts.isEmpty()) {
      return allResults;
    }
    for (String date : transcripts.keySet()) {
      String transcript = transcripts.get(date);
      List<Integer> results = this.findPattern(transcript);
      if (!results.isEmpty()) {
        allResults.put(date, this.findPattern(transcript));
      }
    }
    return allResults;
  }

  /**
   * This method prints the found indeces.
   *
   * @param searchResults A Map from String to a List of Integers, representing
   *                      the search results.
   * @return A String, representing the found indices in the correct format.
   */
  public String printFound(Map<String, List<Integer>> searchResults) {
    try {
      StringBuilder toReturn = new StringBuilder();
      toReturn.append("Search Results: \n\n");
      if (!searchResults.isEmpty() && !(searchResults == null)) {
        for (String date : searchResults.keySet()) {
          toReturn.append("\nID: " + date + "\nIndices: ");
          List<Integer> indices = searchResults.get(date);
          for (Integer index : indices) {
            toReturn.append(index + ", ");
          }
        }
      } else {
        toReturn.append("No results found");
      }
      return toReturn.toString();
    } catch (Exception e) {
      return null;
    }

  }

  /**
   * This isi the execute command method needed to run the command in the REPL,
   * used for testing.
   */
  @Override
  public void executeCommand(List<String> input) {
    // TODO Auto-generated method stub
    if (Database.getConn() == null) {
      System.err.println("No connection found");
      return;
    }
    if (input.size() != 3) {
      System.err.println("Incorrect Input");
      return;
    }
    if (input.get(1) == null) {
      System.err.println("No patient found");
      return;
    }
    if (input.get(2) == null) {
      System.err.println("No pattern found");
      return;
    }
    String patient = input.get(1);
    this.transcripts = Database.getAllTranscripts(patient);
    this.pattern = input.get(2);
    this.getAllResults();
  }

  /**
   * This method gets the dates for the relevant dates to display on the GUI.
   *
   * @param searchResults A Map from String to a List of Integers, representing
   *                      the search results.
   * @return A Set of Strings, representing the search reults.
   */
  public Set<String> getDates(Map<String, List<Integer>> searchResults) {
    return searchResults.keySet();
  }
}

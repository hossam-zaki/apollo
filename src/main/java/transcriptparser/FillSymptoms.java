package transcriptparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import searchalgorithms.KMP;
import searchalgorithms.Search;

/**
 * This class is used to find the symptoms reported by a patient during a
 * specific visit.
 */
public class FillSymptoms {
  private Map<String, List<String>> symptomPatterns;
  private String transcript;
  private Search kmp;

  /**
   * Constructor for the FillSymptoms class.
   *
   * @param patterns       A Map from String to a List of Strings, representing
   *                       the mapping between symptoms and their categorical
   *                       labeling.
   * @param fullTranscript A String, representing the full visit transcript.
   */
  public FillSymptoms(Map<String, List<String>> patterns, String fullTranscript) {
    symptomPatterns = patterns;
    transcript = fullTranscript;
    kmp = this.getPortion();
  }

  /**
   * This method gets the portion of the transcript in which to find symptoms.
   *
   * @return A KMP object with the correctly searched portion.
   */
  public Search getPortion() {
    if (transcript == null) {
      System.err.println("ERROR: File not found");
      return null;
    }
    KMP full = new KMP(transcript.toCharArray());
    try {
      Integer startIndex = -1; // get start phrase
      for (String phrase : AcceptablePhrases.getSymptomsStartPhrases()) {
        List<Integer> indices = full.search(phrase.toCharArray());
        if (indices != null && !indices.isEmpty()) {
          startIndex = indices.get(0);
          break;
        }
      }
      Integer endIndex = -1; // get end phrase
      for (String phrase : AcceptablePhrases.getSymptomsEndPhrases()) {
        List<Integer> indices = full.search(phrase.toCharArray());
        if (indices != null && !indices.isEmpty()) {
          endIndex = indices.get(0);
          break;
        }
      }
      String portion = transcript.substring(startIndex, endIndex);
      return new KMP(portion.toCharArray()); // kmp to only search through
                                             // symptoms portion of
                                             // transcript
    } catch (Exception e) {
      System.out
          .println("ERROR: nothing found in transcript. Please use the given manual sentences");
      return new KMP("".toCharArray());
    }

  }

  /**
   * This method gets all the symptoms from the correct portion of the transcript.
   *
   * @return A Map from String to a List of Strings, that represents the found
   *         symspomts and their categorical labelings.
   */
  public Map<String, List<String>> getSymptoms() {
    Map<String, List<String>> foundSymptoms = new HashMap<String, List<String>>();
    if (symptomPatterns.isEmpty() || symptomPatterns == null) {
      return null;
    }
    for (String key : symptomPatterns.keySet()) {
      List<String> found = new ArrayList<String>();
      for (String pattern : symptomPatterns.get(key)) {
        List<Integer> foundIndices = kmp.search(pattern.toCharArray());
        if (foundIndices.size() != 0) {
          found.add(pattern);
        }
      }
      foundSymptoms.put(key, found);
    }
    if (foundSymptoms.isEmpty()) {
      List<String> found = new ArrayList<String>();
      found.add("");
      foundSymptoms.put("none", found);
    }
    return foundSymptoms;
  }
}

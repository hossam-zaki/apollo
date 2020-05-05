package transcriptparser;

import java.util.List;
import java.util.Map;

/**
 * This class is needed to produce the summary of visit which corresponds to the
 * symptoms and reasons for visit in the EHR.
 *
 */
public class FillEHRSections {
  private FillSymptoms symptomParse;
  private FillVisit reasonsParse;
  private Map<String, List<String>> symptoms;
  private String reasons;

  /**
   * Constructor for the fillHERSections class.
   *
   * @param symptomPatterns A Map from String to a List of Strings, representing
   *                        the symptoms and their categorization.
   * @param fullTranscript  A String, representing the visit's full transcript.
   */
  public FillEHRSections(Map<String, List<String>> symptomPatterns, String fullTranscript) {
    symptomParse = new FillSymptoms(symptomPatterns, fullTranscript);
    reasonsParse = new FillVisit(fullTranscript);
    try {
      symptoms = symptomParse.getSymptoms();
      reasons = reasonsParse.getPortion().trim();
    } catch (Exception e) {
      return;
    }
  }

  /**
   * This methods builds the resulting the HMTL string needed to print the summary
   * to the GUI.
   *
   * @return A String, representing the HTML needed to display the result to the
   *         GUI.
   */
  public String buildResult() {
    try {
      if (symptoms == null || reasons == null || symptoms.isEmpty()) {
        return null;
      }
      StringBuilder toReturn = new StringBuilder();
      toReturn.append("<h5><b>Reasons for Visit: </b></h5>");
      if (!reasons.isBlank()) {
        toReturn.append("<h5>\"" + reasons + "\"</h5><br>");
      } else {
        toReturn.append("<br>");
      }
      toReturn.append("<h5><b>Symptoms Reported:</b></h5>");
      if (symptoms.containsKey("none")) {
        toReturn.append("<br>");
      } else {
        for (String key : symptoms.keySet()) {
          if (symptoms.get(key).size() != 0 && symptoms.get(key) != null) {
            toReturn.append("<h5>" + key + ": ");
          }
          for (String s : symptoms.get(key)) {
            if (s.equals(symptoms.get(key).get(symptoms.get(key).size() - 1))) {
              toReturn.append(s + "</h5>");
            } else {
              toReturn.append(s + ", ");
            }
          }
        }
      }
      return toReturn.toString();
    } catch (Exception e) {
      return null;
    }
  }
}

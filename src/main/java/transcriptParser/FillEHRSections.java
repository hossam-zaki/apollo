package transcriptParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
  public FillEHRSections(Map<String, List<String>> symptomPatterns,
      String fullTranscript) {
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
   * This method is used to produce a string of the found visit transcript onto
   * the GUI and terminal as needed.
   *
   * @return A String, representing the found symptoms and reasons for visit.
   */
  public String printFound() {
    try {
      if (symptoms == null || reasons == null || symptoms.isEmpty()) {
        return null;
      }
      StringBuilder toReturn = new StringBuilder();
      toReturn.append("Reasons for Visit: \n\n");
      if (!reasons.isBlank()) {
        toReturn.append(reasons + "\n\n");
      }
      toReturn.append("Symptoms Reported: \n\n");
      if (symptoms.containsKey("none")) {
        toReturn.append("");
      } else {
        for (String key : symptoms.keySet()) {
          if (symptoms.get(key).size() != 0 && symptoms.get(key) != null)
            toReturn.append(key + ": ");
          for (String s : symptoms.get(key)) {
            if (s.equals(symptoms.get(key).get(symptoms.get(key).size() - 1))) {
              toReturn.append(s + "\n");
            } else {
              toReturn.append(s + ", ");
            }
          }
        }
      }
      System.out.println(toReturn.toString());
      return toReturn.toString();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * This method is used to to print the found summary to a file.
   *
   * @return A boolean, true if the printing to a file succeeds, false
   *         otherwise.
   */
  public boolean printToFile() {
    String toPrint = printFound();
    if (toPrint == null) {
      toPrint = "Sorry, we couldn't find anything to report. Please use the manual sentences.";
    }
    File newFile = new File("visit_summary.txt");
    try {
      if (!newFile.createNewFile()) {
        System.err.println("ERORR: could not create summary file");
        return false;
      }

      try {
        FileWriter myWriter = new FileWriter("visit_summary.txt");
        myWriter.write(toPrint);
        myWriter.close();
        return true;
      } catch (IOException e1) {
        System.err.println("ERORR: could not create summary file");
        return false;
      }

    } catch (Exception e) {
      System.err.println("ERORR: could not create summary file");
      return false;
    }
  }

  /**
   * This methods builds the resulting the HMTL string needed to print the
   * summary to the GUI.
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
      toReturn.append("<h5>Reasons for Visit: </h5>");
      if (!reasons.isBlank()) {
        toReturn.append("<h5>" + reasons + "</h5>");
      }
      toReturn.append("<h5>Symptoms Reported:</h5>");
      if (symptoms.containsKey("none")) {
        toReturn.append("<br>");
      } else {
        for (String key : symptoms.keySet()) {
          if (symptoms.get(key).size() != 0 && symptoms.get(key) != null)
            toReturn.append("<h5>" + key + ": ");
          for (String s : symptoms.get(key)) {
            if (s.equals(symptoms.get(key).get(symptoms.get(key).size() - 1))) {
              toReturn.append(s + "</h5>");
            } else {
              toReturn.append(s + ", ");
            }
          }
        }
      }
      System.out.println(toReturn.toString());
      return toReturn.toString();
    } catch (Exception e) {
      return null;
    }
  }
}

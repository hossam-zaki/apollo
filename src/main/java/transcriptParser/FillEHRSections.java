package transcriptParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FillEHRSections {
  private FillSymptoms symptomParse;
  private FillVisit reasonsParse;
  private List<String> symptoms;
  private String reasons;
  private String result;

  public FillEHRSections(String symStart, String symEnd, String vStart,
      String vEnd, List<String> symptomPatterns, String fullTranscript) {
    symptomParse = new FillSymptoms(symStart, symEnd, symptomPatterns,
        fullTranscript);
    reasonsParse = new FillVisit(vStart, vEnd, fullTranscript);
    try {
      symptoms = symptomParse.getSymptoms();
      reasons = reasonsParse.getPortion().trim();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  public String printFound() {
    try {
      StringBuilder toReturn = new StringBuilder();
      toReturn.append("Reasons for Visit: \n\n");
      toReturn.append(reasons + "\n\n");
      System.out.println(reasons);
      toReturn.append("Symptoms Reported: \n\n");
      for (String s : symptoms) {
        toReturn.append(s + "\n");
      }
      System.out.println(toReturn.toString());
      return toReturn.toString();
    } catch (Exception e) {
      return null;
    }

  }

  public boolean printToFile() {
    String toPrint = printFound();
    this.result = toPrint;
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

  public String getResult() {
    return this.result;
  }

}

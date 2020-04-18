package edu.brown.cs.student.transcriptParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FillEHRSections {
  private FillSymptoms symptomParse;
  private FillVisit reasonsParse;
  private List<String> symptoms;
  private String reasons;

  public FillEHRSections(String start, String end, List<String> symptomPatterns,
      List<String> reasonsPatterns, String fullTranscript) {
    symptomParse = new FillSymptoms(start, end, symptomPatterns, fullTranscript);
    reasonsParse = new FillVisit(start, end, reasonsPatterns, fullTranscript);
    symptoms = symptomParse.getSymptoms();
    reasons = reasonsParse.getPortion();
  }

  public String printFound() {
    StringBuilder toReturn = new StringBuilder();
    toReturn.append("Reasons for Visit: \n\n");
    toReturn.append(reasons + "\n\n");
    toReturn.append("Symptoms Reported: \n\n");
    for (String s : symptoms) {
      toReturn.append(s + "\n");
    }
    System.out.println(toReturn.toString());
    return toReturn.toString();
  }

  public boolean printToFile() {
    String toPrint = printFound();
    File newFile = new File("visit_summary.txt");
    try {
      if (!newFile.createNewFile()) {
        System.err.println("ERORR: could not create sumamry file");
        return false;
      }

      try {
        FileWriter myWriter = new FileWriter("visit_summary.txt");
        myWriter.write(toPrint);
        myWriter.close();
        return true;
      } catch (IOException e1) {
        System.err.println("ERORR: could not create sumamry file");
        return false;
      }

    } catch (Exception e) {
      System.err.println("ERORR: could not create sumamry file");
      return false;
    }
  }

}

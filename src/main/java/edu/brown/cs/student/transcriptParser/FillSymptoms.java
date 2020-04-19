package edu.brown.cs.student.transcriptParser;

import java.util.ArrayList;
import java.util.List;

import searchAlgorithms.KMP;
import searchAlgorithms.Search;

public class FillSymptoms {
  private String doctorStart;
  private String doctorEnd;
  private List<String> symptomPatterns;
  private String transcript;
  private Search kmp;

  public FillSymptoms(String start, String end, List<String> patterns, String fullTranscript) {
    doctorStart = start;
    doctorEnd = end;
    symptomPatterns = patterns;
    transcript = fullTranscript;
    kmp = this.getPortion();
  }

  public Search getPortion() {
    KMP full = new KMP(transcript.toCharArray());
    try {
      Integer startIndex = full.search(doctorStart.toCharArray()).get(0); // get start phrase
      Integer endIndex = full.search(doctorEnd.toCharArray()).get(0); // get end phrase
      String portion = transcript.substring(startIndex, endIndex);
      return new KMP(portion.toCharArray()); // kmp to only search through symptoms portion of
                                             // transcript
    } catch (Exception e) {
      System.out
          .println("ERROR: nothing found in transcript. Please use the given manual sentences");
      return null;
    }

  }

  public List<String> getSymptoms() {
    List<String> foundSymptoms = new ArrayList<String>();

    try {
      for (String pattern : symptomPatterns) {
        List<Integer> foundIndices = kmp.search(pattern.toCharArray());
        if (foundIndices.size() != 0) {
          foundSymptoms.add(pattern);
        }
      }
      return foundSymptoms;
    } catch (Exception e) {
      return null;
    }
  }
}

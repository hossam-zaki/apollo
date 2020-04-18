package edu.brown.cs.student.transcriptParser;

import java.util.ArrayList;
import java.util.List;

import searchAlgorithms.KMP;

public class FillSymptoms {
  private String doctorStart;
  private String doctorEnd;
  private List<String> symptomPatterns;
  private String transcript;
  private KMP kmp;

  public FillSymptoms(String start, String end, List<String> patterns, String fullTranscript) {
    doctorStart = start;
    doctorEnd = end;
    symptomPatterns = patterns;
    transcript = fullTranscript;
    kmp = this.getPortion();
  }

  public KMP getPortion() {
    KMP full = new KMP(transcript.toCharArray());
    Integer startIndex = full.search(doctorStart.toCharArray()).get(0); // get start phrase
    Integer endIndex = full.search(doctorEnd.toCharArray()).get(0); // get end phrase
    String portion = transcript.substring(startIndex, endIndex);
    return new KMP(portion.toCharArray()); // kmp to only search through symptoms portion of
                                           // transcript
  }

  public List<String> getSymptoms() {
    List<String> foundSymptoms = new ArrayList<String>();
    for (String pattern : symptomPatterns) {
      List<Integer> foundIndices = kmp.search(pattern.toCharArray());
      if (foundIndices.size() != 0) {
        foundSymptoms.add(pattern);
      }
    }
    return foundSymptoms;
  }
}

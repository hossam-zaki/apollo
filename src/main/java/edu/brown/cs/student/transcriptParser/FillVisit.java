package edu.brown.cs.student.transcriptParser;

import searchAlgorithms.KMP;

public class FillVisit {
  private String doctorStart;
  private String doctorEnd;
  private String transcript;

  public FillVisit(String start, String end, String fullTranscript) {
    doctorStart = start;
    doctorEnd = end;
    transcript = fullTranscript;
  }

  public String getPortion() {
    KMP full = new KMP(transcript.toCharArray());
    Integer startIndex = full.search(doctorStart.toCharArray()).get(0); // get start phrase
    Integer endIndex = full.search(doctorEnd.toCharArray()).get(0); // get end phrase
    String portion = transcript.substring(startIndex, endIndex);
    return portion;
  }
}

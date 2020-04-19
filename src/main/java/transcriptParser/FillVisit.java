package transcriptParser;

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
    try {
      Integer startIndex = full.search(doctorStart.toCharArray()).get(0)
          + this.doctorStart.length(); // get
                                       // start
                                       // phrase
      Integer endIndex = full.search(doctorEnd.toCharArray()).get(0); // get end phrase
      String portion = transcript.substring(startIndex, endIndex);
      return portion;
    } catch (Exception e) {
      System.err.println("ERROR: portion not found");
      return "";
    }

  }
}

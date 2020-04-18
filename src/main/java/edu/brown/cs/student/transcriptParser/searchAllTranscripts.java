package edu.brown.cs.student.transcriptParser;

import java.util.ArrayList;
import java.util.List;

import searchAlgorithms.KMP;
import searchAlgorithms.Search;

public class searchAllTranscripts {
  private List<String> transcripts;

  public searchAllTranscripts(List<String> transcripts) {
    this.transcripts = transcripts;
  }

  /**
   * search all transcripts for the pattern
   *
   * @param pattern
   * @return list of indices where found for each transcript
   */
  public List<List<Integer>> findPattern(String pattern) {
    List<List<Integer>> found = new ArrayList<List<Integer>>();
    for (String transcript : transcripts) {
      Search current = new KMP(transcript.toCharArray());
      found.add(current.search(pattern.toCharArray()));
    }
    return found;
  }
}

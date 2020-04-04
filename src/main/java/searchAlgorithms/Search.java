package searchAlgorithms;

import java.util.List;

/**
 * This interface to be extended by every search algorithm to be used to search
 * a patient visit transcript. This makes our code more generic and makes adding
 * potential new search algorithms easier.
 */
public interface Search {

  /*
   * Returns a list of integers representing wanted indexes of a given text
   * transcript.
   */
  List<Integer> search(char[] V);

}

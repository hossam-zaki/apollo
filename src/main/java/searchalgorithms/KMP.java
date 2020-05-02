package searchalgorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the logic for the Knuth-Morris-Pratt (KMP) algorithm for
 * pattern-matching. This includes a pre-processing step of building a failure
 * function. The failure function indicates the largest possible shift at each
 * letter of the pattern we are searching for, and a method that uses that
 * failure function to find occurrences of that substring in the overall text.
 * Using KMP is advantageous when compared to a naive algorithm for parsing
 * through text because of this skipping as defined by the failure function,
 * leading to a worst case runtime of O(m + n), where m is the length of the
 * pattern and n is the length of the text.
 */
public class KMP implements Search {

  private char[] text;

  /**
   * Constructor takes in a character array of the text in which we are looking
   * for a pattern.
   *
   * @param text An Array of Characters, representing each character in the
   *             transcript.
   */
  public KMP(char[] text) {
    this.text = text;
  }

  /**
   * The first step of the KMP algorithm is to process the pattern by computing
   * a failure function. This failure function indicates the largest possible
   * shift using previously performed comparisons (i.e. failureFunction[j] is
   * the length of the longest prefix of the pattern that is a suffix of
   * pattern[i:j]).
   *
   * @param pattern to build the failure function for
   * @return int array with the integer values that correspond to each character
   */
  public int[] buildFailureFunction(char[] pattern) {

    int len = pattern.length;
    int[] failureFunction = new int[len];
    failureFunction[0] = 0;
    int i = 0;
    for (int j = 1; j < len; j++) {
      i = failureFunction[j - 1];
      while (pattern[j] != pattern[i] && i > 0) {
        i = failureFunction[i - 1];
      }
      if (pattern[j] != pattern[i] && i == 0) {
        failureFunction[j] = 0;
      } else {
        failureFunction[j] = i + 1;
      }

    }
    return failureFunction;

  }

  /**
   * Searches for a pattern (substring) in the text, beginning with building the
   * failure function for that pattern.
   *
   * @param pattern to search for
   * @return list of indices where the pattern was found
   */
  public List<Integer> search(char[] pattern) {

    int[] failureFunction = buildFailureFunction(pattern);
    int patInd = 0;
    int textInd = 0;
    int lenSeq = text.length;
    int lenPattern = pattern.length;
    List<Integer> successArray = new ArrayList<Integer>();
    while (lenSeq > textInd) {
      if (pattern[patInd] == text[textInd]) {
        patInd = patInd + 1;
        textInd = textInd + 1;
        if (patInd == lenPattern) {
          successArray.add(textInd - lenPattern);
          patInd = failureFunction[patInd - 1];
        }
      }
      if (textInd < lenSeq && pattern[patInd] != text[textInd]) {
        if (patInd != 0) {
          patInd = failureFunction[patInd - 1];
        } else {
          textInd += 1;
        }
      }
    }
    return successArray;
  }
}

package searchAlgorithms;

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
public class KMP {

	private char[] text;

	/**
	 * Constructor takes in a character array of the text in which we are looking
	 * for a pattern.
	 *
	 * @param text
	 */
	public KMP(char[] text) {
		this.text = text;
	}

	/**
	 * The first step of the KMP algorithm is to process the pattern by computing a
	 * failure function. This failure function indicates the largest possible shift
	 * using previously performed comparisons (i.e. failureFunction[j] is the length
	 * of the longest prefix of the pattern that is a suffix of pattern[i:j]).
	 * 
	 * @param pattern to build the failure function for
	 * @return int array with the integer values that correspond to each character
	 */
	public int[] buildFailureFunction(char[] pattern) {
		int len = pattern.length;
		int[] failureFunction = new int[len];
		failureFunction[0] = 0;
		int i = 1;
		int j = 0;
		while (i < len) {
			if (pattern[i] == pattern[j]) {
				j++;
				failureFunction[i] = j;
				i++;
			} else {
				if (j != 0) {
					j = failureFunction[j - 1];
				} else {
					failureFunction[i] = 0;
					i++;
				}
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
		List<Integer> found = new ArrayList<Integer>(); // Stores indices where the pattern was found
		int[] failure = buildFailureFunction(pattern);
		int patternLength = pattern.length;
		int textLength = text.length;
		int patternIndex = 0;
		int textIndex = 0;
		while (textIndex < textLength) {
			if (pattern[patternIndex] == text[textIndex]) {
				patternIndex++;
				textIndex++;
			}
			if (patternIndex == patternLength) {
				found.add(textIndex - patternIndex);
				patternIndex = failure[patternIndex - 1];
			} else if (textIndex < textLength && pattern[patternIndex] != text[textIndex]) {
				if (patternIndex != 0) {
					patternIndex = failure[patternIndex - 1];
				} else {
					textIndex++;
				}
			}
		}
		return found;
	}
}

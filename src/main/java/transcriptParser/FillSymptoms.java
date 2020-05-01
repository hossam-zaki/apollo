package transcriptParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import searchAlgorithms.KMP;
import searchAlgorithms.Search;

public class FillSymptoms {
	private Map<String, List<String>> symptomPatterns;
	private String transcript;
	private Search kmp;

	public FillSymptoms(Map<String, List<String>> patterns, String fullTranscript) {
		symptomPatterns = patterns;
		transcript = fullTranscript;
		kmp = this.getPortion();
	}

	public Search getPortion() {
		if (transcript == null) {
			System.out.println("ERROR: File not found");
			return null;
		}
		KMP full = new KMP(transcript.toCharArray());
		try {
			Integer startIndex = -1; // get start phrase
			for (String phrase : AcceptablePhrases.getSymptomsStartPhrases()) {
				List<Integer> indices = full.search(phrase.toCharArray());
				if (indices != null && !indices.isEmpty()) {
					startIndex = indices.get(0);
					System.out.println(phrase);
					System.out.println(startIndex);
					break;
				}
			}
			Integer endIndex = -1; // get end phrase
			for (String phrase : AcceptablePhrases.getSymptomsEndPhrases()) {
				List<Integer> indices = full.search(phrase.toCharArray());
				if (indices != null && !indices.isEmpty()) {
					endIndex = indices.get(0);
					System.out.println(phrase);
					System.out.println(endIndex);
					break;
				}
			}
			String portion = transcript.substring(startIndex, endIndex);
			return new KMP(portion.toCharArray()); // kmp to only search through symptoms portion of
													// transcript
		} catch (Exception e) {
			System.out.println("ERROR: nothing found in transcript. Please use the given manual sentences");
			return new KMP("".toCharArray());
		}

	}

	public Map<String, List<String>> getSymptoms() {
		Map<String, List<String>> foundSymptoms = new HashMap<String, List<String>>();
		if (symptomPatterns.isEmpty() || symptomPatterns == null) {
			return null;
		}
		for (String key : symptomPatterns.keySet()) {
			List<String> found = new ArrayList<String>();
			for (String pattern : symptomPatterns.get(key)) {
				List<Integer> foundIndices = kmp.search(pattern.toCharArray());
				if (foundIndices.size() != 0) {
					found.add(pattern);
				}
			}
			foundSymptoms.put(key, found);
		}
		if (foundSymptoms.isEmpty()) {
			List<String> found = new ArrayList<String>();
			found.add("");
			foundSymptoms.put("none", found);
		}
		return foundSymptoms;
	}
}

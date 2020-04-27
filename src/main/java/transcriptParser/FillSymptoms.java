package transcriptParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import searchAlgorithms.KMP;
import searchAlgorithms.Search;

public class FillSymptoms {
	private String doctorStart;
	private String doctorEnd;
	private Map<String, List<String>> symptomPatterns;
	private String transcript;
	private Search kmp;

	public FillSymptoms(String start, String end, Map<String, List<String>> patterns, String fullTranscript) {
		doctorStart = start;
		doctorEnd = end;
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
			Integer startIndex = full.search(doctorStart.toCharArray()).get(0); // get start phrase
			Integer endIndex = full.search(doctorEnd.toCharArray()).get(0); // get end phrase
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

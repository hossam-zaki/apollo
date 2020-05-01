package transcriptParser;

import java.util.List;

import searchAlgorithms.KMP;

public class FillVisit {
	private String transcript;

	public FillVisit(String fullTranscript) {
		transcript = fullTranscript;
	}

	public String getPortion() {
		KMP full = new KMP(transcript.toCharArray());
		try {
			Integer startIndex = -1; // get start phrase
			for (String phrase : AcceptablePhrases.getVisitStartPhrases()) {
				List<Integer> indices = full.search(phrase.toCharArray());
				if (indices != null && !indices.isEmpty()) {
					startIndex = indices.get(0) + phrase.length();
					break;
				}
			}
			Integer endIndex = -1; // get end phrase
			for (String phrase : AcceptablePhrases.getVisitEndPhrases()) {
				List<Integer> indices = full.search(phrase.toCharArray());
				if (indices != null && !indices.isEmpty()) {
					endIndex = indices.get(0);
					break;
				}
			}
			String portion = transcript.substring(startIndex, endIndex);
			return portion;
		} catch (Exception e) {
			System.err.println("ERROR: portion not found");
			return "";
		}

	}
}

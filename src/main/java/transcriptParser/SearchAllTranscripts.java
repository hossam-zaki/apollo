package transcriptParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import databases.Database;
import repl.Executable;
import searchAlgorithms.KMP;
import searchAlgorithms.Search;

public class SearchAllTranscripts implements Executable {
	private Map<String, String> transcripts;
	private String pattern;
	private String result;

	/**
	 * Empty constructor.
	 */
	public SearchAllTranscripts() {
		transcripts = new HashMap<String, String>();
		pattern = "";
	}

	/**
	 * Constructors made for testing.
	 *
	 * @param transcripts
	 */
	public SearchAllTranscripts(Map<String, String> transcripts, String pattern) {
		this.transcripts = transcripts;
		this.pattern = pattern;
	}

	/**
	 * Constructors made for testing.
	 *
	 * @param transcripts
	 */
	public SearchAllTranscripts(UUID patient, String pattern) {
		this.transcripts = Database.getAllTranscripts(patient.toString());
		this.pattern = pattern;
	}

	/**
	 * search all transcripts for the pattern
	 *
	 * @param pattern
	 * @return list of indices where found for each transcript
	 */
	public List<Integer> findPattern(String transcript) {
		List<Integer> found = new ArrayList<Integer>();
		Search current = new KMP(transcript.toCharArray());
		found = current.search(pattern.toCharArray());
		return found;
	}

	public Map<String, List<Integer>> getAllResults() {
		Map<String, List<Integer>> allResults = new HashMap<String, List<Integer>>();
		if (transcripts == null || transcripts.isEmpty()) {
			return allResults;
		}
		for (String date : transcripts.keySet()) {
			String transcript = transcripts.get(date);
			List<Integer> results = this.findPattern(transcript);
			if (!results.isEmpty()) {
				allResults.put(date, this.findPattern(transcript));
			}
		}
		return allResults;
	}

	public String printFound(Map<String, List<Integer>> searchResults) {
		try {
			StringBuilder toReturn = new StringBuilder();
			toReturn.append("Search Results: \n\n");
			if (!searchResults.isEmpty() && !(searchResults == null)) {
				for (String date : searchResults.keySet()) {
					toReturn.append("\nDate: " + date + "\nIndices: ");
					List<Integer> indices = searchResults.get(date);
					for (Integer index : indices) {
						toReturn.append(index + ", ");
					}
				}
			} else {
				toReturn.append("No results found");
			}
			// System.out.println(toReturn.toString());
			return toReturn.toString();
		} catch (Exception e) {
			return null;
		}

	}

	public void buildResults(Map<String, List<Integer>> searchResults) {
		try {
			StringBuilder toReturn = new StringBuilder();
			toReturn.append("<h5>Search Results:</h5>");
			toReturn.append("<br>");
			if (!searchResults.isEmpty() && !(searchResults == null)) {
				for (String date : searchResults.keySet()) {
					toReturn.append("<h5>Date: " + date + "</h5>");
					toReturn.append("<br>");
					toReturn.append("<h5>Indices: ");
					List<Integer> indices = searchResults.get(date);
					for (Integer index : indices) {
						if (index.equals(indices.get(indices.size() - 1))) {
							toReturn.append(index + "</h5>");
						} else {
							toReturn.append(index + ", ");
						}
					}
				}
			} else {
				toReturn.append("<h5>No results found</h5>");
			}
			// System.out.println(toReturn.toString());
			result = toReturn.toString();
		} catch (Exception e) {
			result = "<h5>No results found</h5>";
		}

	}

	/**
	 * For testing in REPL.
	 */
	@Override
	public void executeCommand(List<String> input) {
		// TODO Auto-generated method stub
		if (Database.getConn() == null) {
			System.err.println("No connection found");
			return;
		}
		if (input.size() != 3) {
			System.err.println("Incorrect Input");
			return;
		}
		if (input.get(1) == null) {
			System.err.println("No patient found");
			return;
		}
		if (input.get(2) == null) {
			System.err.println("No pattern found");
			return;
		}
		String patient = input.get(1);
		this.transcripts = Database.getAllTranscripts(patient);
		this.pattern = input.get(2);
		// System.out.println(patient);
		// System.out.println(transcripts);
		// System.out.println(pattern);
		// this.printFound(this.getAllResults());
		this.buildResults(this.getAllResults());
	}

	public String getResult() {
		return result;
	}

	public Set<String> getDates(Map<String, List<Integer>> searchResults) {
		return searchResults.keySet();
	}
}

package transcriptParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FillEHRSections {
	private FillSymptoms symptomParse;
	private FillVisit reasonsParse;
	private Map<String, List<String>> symptoms;
	private String reasons;

	public FillEHRSections(String symStart, String symEnd, String vStart, String vEnd,
			Map<String, List<String>> symptomPatterns, String fullTranscript) {
		symptomParse = new FillSymptoms(symStart, symEnd, symptomPatterns, fullTranscript);
		reasonsParse = new FillVisit(vStart, vEnd, fullTranscript);
		try {
			symptoms = symptomParse.getSymptoms();
			reasons = reasonsParse.getPortion().trim();
		} catch (Exception e) {
			return;
		}
	}

	public String printFound() {
		try {
			if (symptoms == null || reasons == null || symptoms.isEmpty()) {
				return null;
			}
			StringBuilder toReturn = new StringBuilder();
			toReturn.append("Reasons for Visit: \n\n");
			if (!reasons.isBlank()) {
				toReturn.append(reasons + "\n\n");
			}
			toReturn.append("Symptoms Reported: \n\n");
			if (symptoms.containsKey("none")) {
				toReturn.append("");
			} else {
				for (String key : symptoms.keySet()) {
					if (symptoms.get(key).size() != 0 && symptoms.get(key) != null)
						toReturn.append(key + ": ");
					for (String s : symptoms.get(key)) {
						if (s.equals(symptoms.get(key).get(symptoms.get(key).size() - 1))) {
							toReturn.append(s + "\n");
						} else {
							toReturn.append(s + ", ");
						}
					}
				}
			}
			System.out.println(toReturn.toString());
			return toReturn.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean printToFile() {
		String toPrint = printFound();
		if (toPrint == null) {
			toPrint = "Sorry, we couldn't find anything to report. Please use the manual sentences.";
		}
		File newFile = new File("visit_summary.txt");
		try {
			if (!newFile.createNewFile()) {
				System.err.println("ERORR: could not create summary file");
				return false;
			}

			try {
				FileWriter myWriter = new FileWriter("visit_summary.txt");
				myWriter.write(toPrint);
				myWriter.close();
				return true;
			} catch (IOException e1) {
				System.err.println("ERORR: could not create summary file");
				return false;
			}

		} catch (Exception e) {
			System.err.println("ERORR: could not create summary file");
			return false;
		}
	}

	public String buildResult() {
		try {
			if (symptoms == null || reasons == null || symptoms.isEmpty()) {
				return null;
			}
			StringBuilder toReturn = new StringBuilder();
			toReturn.append("<br>");
			toReturn.append("<h5>Reasons for Visit: </h5>");
			if (!reasons.isBlank()) {
				toReturn.append("<h5>" + reasons + "</h5>");
			}
			toReturn.append("<br>");
			toReturn.append("<h5>Symptoms Reported:</h5>");
			if (symptoms.containsKey("none")) {
				toReturn.append("<br>");
			} else {
				for (String key : symptoms.keySet()) {
					if (symptoms.get(key).size() != 0 && symptoms.get(key) != null)
						toReturn.append("<h5>" + key + ": ");
					for (String s : symptoms.get(key)) {
						if (s.equals(symptoms.get(key).get(symptoms.get(key).size() - 1))) {
							toReturn.append(s + "</h5>");
						} else {
							toReturn.append(s + ", ");
						}
					}
				}
			}
			System.out.println(toReturn.toString());
			return toReturn.toString();
		} catch (Exception e) {
			return null;
		}
	}

}

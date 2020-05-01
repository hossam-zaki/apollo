package transcriptParser;

import java.util.ArrayList;
import java.util.List;

public final class AcceptablePhrases {

	public static List<String> getSymptomsStartPhrases() {
		List<String> start = new ArrayList<String>();
		start.add("start symptoms");
		start.add("Start symptoms");
		start.add("begin symptoms");
		start.add("Begin symptoms");
		start.add("what are your symptoms");
		start.add("What are your symptoms");
		start.add("what symptoms do you have");
		start.add("What symptoms do you have");
		return start;
	}

	public static List<String> getSymptomsEndPhrases() {
		List<String> end = new ArrayList<String>();
		end.add("end symptoms");
		end.add("End symptoms");
		end.add("stop symptoms");
		end.add("Stop symptoms");
		end.add(". Symptoms");
		end.add("no more symptoms");
		end.add("No more symptoms");
		return end;
	}

	public static List<String> getVisitStartPhrases() {
		List<String> start = new ArrayList<String>();
		start.add("start reasons");
		start.add("Start reasons");
		start.add("begin reasons");
		start.add("Begin reasons");
		start.add("what are your reasons");
		start.add("What are your reasons");
		start.add("what brings you here today");
		start.add("What brings you here today");
		return start;
	}

	public static List<String> getVisitEndPhrases() {
		List<String> end = new ArrayList<String>();
		end.add("end reasons");
		end.add("End reasons");
		end.add("stop reasons");
		end.add("Stop reasons");
		end.add(". Reasons");
		end.add("no more reasons");
		end.add("No more reasons");
		return end;
	}
}

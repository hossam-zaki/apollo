package edu.brown.cs.student.starsTimdb.commands;

import java.util.List;

import edu.brown.cs.student.starsTimdb.repl.Executable;
import speechToText.RunDeepSpeech;

public class StartSpeechToText implements Executable {

	@Override
	public void executeCommand(List<String> string) {
		new RunDeepSpeech();
		RunDeepSpeech.transcribe(string.get(1));
	}

}

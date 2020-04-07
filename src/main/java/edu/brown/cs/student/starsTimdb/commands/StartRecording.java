package edu.brown.cs.student.starsTimdb.commands;

import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import audioRecording.ProcessRecording;
import audioRecording.RecordingTest;
import edu.brown.cs.student.starsTimdb.repl.Executable;

public class StartRecording implements Executable {

  @Override
  public void executeCommand(List<String> string) {
    try {
		RecordingTest.start();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

}

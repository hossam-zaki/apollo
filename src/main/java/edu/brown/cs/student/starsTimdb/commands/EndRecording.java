package edu.brown.cs.student.starsTimdb.commands;

import java.util.List;

import audioRecording.ProcessRecording;
import edu.brown.cs.student.starsTimdb.repl.Executable;

public class EndRecording implements Executable {

  @Override
  public void executeCommand(List<String> string) {
    ProcessRecording.stop();
  }

}

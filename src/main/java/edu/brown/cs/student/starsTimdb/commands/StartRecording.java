package edu.brown.cs.student.starsTimdb.commands;

import java.util.List;

import audioRecording.ProcessRecording;
import edu.brown.cs.student.starsTimdb.repl.Executable;

public class StartRecording implements Executable {

  @Override
  public void executeCommand(List<String> string) {
    ProcessRecording.start();
  }

}

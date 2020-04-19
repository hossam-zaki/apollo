package edu.brown.cs.student.starsTimdb.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import edu.brown.cs.student.starsTimdb.repl.Executable;
import speechToText.RunDeepSpeech;

public class StartSpeechToText implements Executable {

  @Override
  public void executeCommand(List<String> string) {
    String wavFile = string.get(1);
    if (!wavFile.endsWith(".wav")) {
      System.err.println("ERROR: Did not pass in a WAV File");
      return;
    }
    try {
      FileReader file = new FileReader(wavFile);
    } catch (FileNotFoundException e1) {
      System.err.println("ERROR: WAV File does not exist");
      return;
    }
    new RunDeepSpeech();
    RunDeepSpeech.transcribe(wavFile);
  }

}

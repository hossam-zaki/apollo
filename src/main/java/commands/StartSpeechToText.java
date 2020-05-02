package commands;

import java.util.List;

import repl.Executable;
import speechtotext.RunDeepSpeech;

/**
 * Class that will start the speech to text given a filename.
 */
public class StartSpeechToText implements Executable {

  /**
   * Run speech to text given a filename.
   */
  @Override
  public void executeCommand(List<String> string) {
    String wavFile = string.get(1);
    RunDeepSpeech.transcribe(wavFile);
  }

}

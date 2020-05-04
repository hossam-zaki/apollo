package speechtotext;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class for running the Speech to text.
 */
public class RunDeepSpeech {

  protected RunDeepSpeech() {
  }

  /**
   * This method will take in an audio file name, and run the speechtotext.py file
   * in the same package. This will run speech to text, and will save a transcript
   * locally.
   *
   * @param filename this is the filename of the audioFile you would like to
   *                 transcribe.
   */
  public static void transcribe(String filename) {
    if (!filename.endsWith(".wav")) {
      System.err.println("ERROR: Did not pass in a WAV File"); // checks file type
      return;
    }
    try {
      FileReader file = new FileReader(filename);
    } catch (FileNotFoundException e1) {
      System.err.println("ERROR: WAV File does not exist"); // checks filename
      return;
    }
    System.out.println("Valid file inputted"); // used for testing
    Process process;
    try {
      StringBuilder str = new StringBuilder();
      str.append("src/main/java/speechToText/speechtotext.py ");
      str.append("--audio ");
      str.append(filename);
      System.out.println(str.toString());
      System.out.println("Speech To Text Starting");
      process = Runtime.getRuntime().exec(str.toString());
    } catch (IOException e) {
      System.err.println("ERROR: Error with executing the script");
      return;
    }
  }
}

package audioRecording;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public class ProcessRecording {
  private static final int RECORD_TIME = 60000; // 60 seconds
  private static boolean recording;
  private static Recording recorder;
  private static File wavFile;
  private static Thread recordThread;

  public ProcessRecording() {
    wavFile = new File("E:/Test/Record.wav");
    recorder = new Recording();
    recording = false;
  }

  public synchronized static void start() {
    // create a separate thread for recording
    recordThread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println("Start recording...");
          recorder.start();
          recording = true;
        } catch (LineUnavailableException ex) {
          ex.printStackTrace();
          System.exit(-1);
        }
      }
    });

    recordThread.start();
    try {
      recordThread.wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public synchronized static void stop() {
    recordThread.notify();
    try {
      recorder.stop();
      recorder.save(wavFile);
      System.out.println("STOPPED");
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    System.out.println("DONE");
  }
}

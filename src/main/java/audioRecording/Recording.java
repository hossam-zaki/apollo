package audioRecording;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioPermission;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.TargetDataLine;

/**
 * A utility class provides general functions for recording sound.
 *
 * @author www.codejava.net
 *
 */
public class Recording {

  private static final int BUFFER_SIZE = 4096;
  private ByteArrayOutputStream recordBytes;
  private TargetDataLine audioLine;
  private static AudioFormat format;
  File wavFile = new File("data/tester.wav");
  private boolean isRunning;
  TargetDataLine line;
  AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;


  /**
   * Defines a default audio format used to record
   */
  AudioFormat getAudioFormat() {
    float sampleRate = 16000;
    int sampleSizeInBits = 8;
    int channels = 2;
    boolean signed = true;
    boolean bigEndian = true;
    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
  }

  /**
   * Start recording sound.
   *
   * @throws LineUnavailableException if the system does not support the specified
   *                                  audio format nor open the audio data line.
   */
  public void start() throws LineUnavailableException {
//    format = getAudioFormat();
//    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
//
//    // checks if system supports the data line
//    if (!AudioSystem.isLineSupported(info)) {
//      throw new LineUnavailableException("The system does not support the specified format.");
//    }
//    
//    audioLine = AudioSystem.getTargetDataLine(format);
//
//    audioLine.open();
//    audioLine.start();
//    byte[] buffer = new byte[BUFFER_SIZE];
//    int bytesRead = 0;
//
//    recordBytes = new ByteArrayOutputStream();
//    isRunning = true;
//
//    while (isRunning) {
//      bytesRead = audioLine.read(buffer, 0, buffer.length);
//      System.out.println(buffer);
//      recordBytes.write(buffer, 0, bytesRead);
//    }
	  try {
          AudioFormat format = getAudioFormat();
          DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

          // checks if system supports the data line
          if (!AudioSystem.isLineSupported(info)) {
              System.out.println("Line not supported");
              System.exit(0);
          }
          line = (TargetDataLine) AudioSystem.getLine(info);
          line.open(format);
          line.start();   // start capturing

          System.out.println("Start capturing...");

          AudioInputStream ais = new AudioInputStream(line);

          System.out.println("Start recording...");

          // start recording
          AudioSystem.write(ais, fileType, wavFile);

      } catch (LineUnavailableException ex) {
          ex.printStackTrace();
      } catch (IOException ioe) {
          ioe.printStackTrace();
      }
  }

  /**
   * Stop recording sound.
   *
   * @throws IOException if any I/O error occurs.
   */
  public void stop() throws IOException {
//    isRunning = false;
//
//    if (audioLine != null) {
//      audioLine.flush();
//      audioLine.close();
//    }
	  line.stop();
      line.close();
      System.out.println("Finished");
  }

//  /**
//   * Stop recording sound.
//   *
//   * @throws IOException if any I/O error occurs.
//   */
//  public void pause() throws IOException {
//    isRunning = false;
//
//    if (audioLine != null) {
//      this.currentFrame = this.clip.getMicrosecondPosition();
//      audioLine.stop();
//      audioLine.close();
//    }
//  }

  /**
   * Save recorded sound data into a .wav file format.
   *
   * @param wavFile The file to be saved.
   * @throws IOException if any I/O error occurs.
   */
  public void save(File wavFile) throws IOException {
    byte[] audioData = recordBytes.toByteArray();
    System.out.println(recordBytes.toString());
    ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
    AudioInputStream audioInputStream = new AudioInputStream(bais, format,
        audioData.length / format.getFrameSize());

    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);

    audioInputStream.close();
    recordBytes.close();
  }

}

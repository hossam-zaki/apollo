package audioRecording;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class RecordingTest {
	private static final int BUFFER_SIZE = 4096;
	  private ByteArrayOutputStream recordBytes;
	  private TargetDataLine audioLine;
	  private static AudioFormat format;

	  private boolean isRunning;

	  /**
	   * Defines a default audio format used to record
	   */
	  static AudioFormat getAudioFormat() {
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
	  public static void start() throws LineUnavailableException {
	    format = getAudioFormat();
	    DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	    if (!AudioSystem.isLineSupported(info)) {
	        throw new LineUnavailableException("The system does not support the specified format.");
	      }
	    
	   final TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
	   targetDataLine.open();
	   System.out.println("Starting");
	   targetDataLine.start();
	   Thread stopper = new Thread( new Runnable() {

		@Override
		public void run() {
			AudioInputStream audioStream = new AudioInputStream(targetDataLine);
			File wavFile = new File("data/test.au");
			try {
				AudioSystem.write(audioStream, AudioFileFormat.Type.AU, wavFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		   
	   });
	   stopper.start();
	   try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   targetDataLine.stop();
	   targetDataLine.close();
	  }
	  
	  
}

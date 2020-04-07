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
		wavFile = new File("data/Record.wav");
		recorder = new Recording();
		recording = false;
	}

	public static void start() {
		// create a separate thread for recording
		recordThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					recorder.start();
					recording = true;
				} catch (LineUnavailableException ex) {
					ex.printStackTrace();
					System.exit(-1);
				}
			}
		});
		try {
			synchronized (recordThread) {
				recordThread.start();
				recordThread.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void stop() {
		System.out.println("Starting to stop...");
		synchronized (recordThread) {
			recordThread.notify();
		}
		System.out.println("Finished notification");
		try {
			recorder.stop();
			//recorder.save(wavFile);
			System.out.println("STOPPED");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		System.out.println("DONE");
	}
}

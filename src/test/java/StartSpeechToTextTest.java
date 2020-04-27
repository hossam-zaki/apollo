import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import speechToText.RunDeepSpeech;

public class StartSpeechToTextTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUp() throws Exception {
		new RunDeepSpeech();
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

//	@Test
//	public void testValidInput() {
//		RunDeepSpeech.transcribe("data/audioFiles/test.wav");
//		try {
//			FileReader file = new FileReader("data/transcripts/test.txt");
//		} catch (FileNotFoundException e1) {
//			fail("text file not made");
//			return;
//		}
//	}

	@Test
	public void testinValidInput() {
		RunDeepSpeech.transcribe("data/audioFiles/tes");
	    assertTrue(errContent.toString().contains("ERROR:"));

	}
	@Test
	public void testinValidInputAgain() {
		RunDeepSpeech.transcribe("data/audioFiles/testyawefaewf.wav");
	    assertTrue(errContent.toString().contains("ERROR:"));

	}

}

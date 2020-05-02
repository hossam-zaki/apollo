import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import speechtotext.RunDeepSpeech;

public class StartSpeechToTextTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void testinValidInput() throws Exception {
		setUp();
		RunDeepSpeech.transcribe("data/audioFiles/tes");
	    assertTrue(errContent.toString().contains("ERROR:"));
	    restoreStreams();


	}
	@Test
	public void testinValidInputAgain() throws Exception {
		setUp();
		RunDeepSpeech.transcribe("data/audioFiles/testyawefaewf.wav");
	    assertTrue(errContent.toString().contains("ERROR:"));
	    restoreStreams();

	}
	@Test
	public void testValidInput() throws Exception {
		setUp();
		RunDeepSpeech.transcribe("data/audioFiles/tester.wav");
	    assertFalse(outContent.toString().contains("ERROR:"));
	    restoreStreams();
	}

}

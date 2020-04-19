package timdb_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.student.maps.commands.MdbCommand;

/**
 * Tester class for mdb command.
 */
public class MDBTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
  /**
   * Tester method for mdb command.
   */
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}
  @Test
  public void mdbTest() {
	setUpStreams();
    MdbCommand mdb = new MdbCommand();
    List<String> list = new ArrayList<String>();
    list.add("mdb");
    list.add("data/timdb/timdb.sqlite3");
    mdb.executeCommand(list);    
    assertTrue(outContent.toString().contains("data/timdb/timdb.sqlite3"));
    restoreStreams();

  }
  @Test
  public void mdbIncorrectTest() {
	setUpStreams();
    MdbCommand mdb = new MdbCommand();
    List<String> list = new ArrayList<String>();
    list.add("mdb");
    list.add("awefawefawef");
    mdb.executeCommand(list);    
    assertTrue(errContent.toString().contains("ERROR"));
    restoreStreams();

  }
}

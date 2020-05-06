import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import commands.ConnectToDatabase;
import databases.Database;
import transcriptparser.SearchAllTranscripts;

public class AllSearchTest {
  private Map<String, String> dates;

  @Before
  public void setUp() {
    ConnectToDatabase connect = new ConnectToDatabase();
    List<String> command = new ArrayList<String>();
    command.add("build");
    connect.executeCommand(command);
    dates = new HashMap<String, String>();
    String date1 = "March10";
    String transcript1 = "Jcnrjncr\n" + "\n" + "what are your reasons for today's visit\n" + "\n"
        + "I fell from my bike and hit my head on a rock\n" + "\n" + "end reasons for visit\n"
        + "\n" + "what are your symptoms\n" + "\n" + "my head hurts and my stomach hurts\n" + "\n"
        + "end symptoms\n" + "\n" + "Gjntjtn";
    String date2 = "May7";
    String transcript2 = "Jcnrjncr\n" + "\n" + "what are your reasons for today's visit\n" + "\n"
        + "I keep feeling my head hurts";
    String date3 = "June5";
    String transcript3 = "Jcnrjncr\n" + "\n" + "what are your reasons for today's visit\n" + "\n"
        + "these headaches don't go away I think it's a migraine";
    dates.put(date1, transcript1);
    dates.put(date2, transcript2);
    dates.put(date3, transcript3);
  }

  @After
  public void tearDown() {
    dates = null;
  }

  @Test
  public void simpleTest() {
    setUp();
    SearchAllTranscripts searchAll = new SearchAllTranscripts(dates, "head");
    Map<String, List<Integer>> results = searchAll.getAllResults();
    assertTrue(results.size() == 3);
    assertTrue(results.containsKey("March10"));
    assertTrue(results.containsKey("May7"));
    assertTrue(results.containsKey("June5"));
    searchAll.printFound(results);
    assertTrue(searchAll.getDates(results).contains("March10"));
    assertTrue(searchAll.getDates(results).contains("May7"));
    assertTrue(searchAll.getDates(results).contains("June5"));
    tearDown();
  }

  @Test
  public void excludeTest() {
    setUp();
    SearchAllTranscripts searchAll = new SearchAllTranscripts(dates, "my");
    Map<String, List<Integer>> results = searchAll.getAllResults();
    assertTrue(results.size() == 2);
    assertTrue(results.containsKey("March10"));
    assertTrue(results.containsKey("May7"));
    searchAll.printFound(results);
    tearDown();
  }

  @Test
  public void noTranscriptsTest() {
    setUp();
    dates.clear();
    SearchAllTranscripts searchAll = new SearchAllTranscripts(dates, "my");
    Map<String, List<Integer>> results = searchAll.getAllResults();
    assertTrue(results.size() == 0);
    tearDown();
  }

  @Test
  public void noSuchPatientTest() {
    setUp();
    UUID patient = UUID.fromString("123e4567-e89b-12d3-a456-556642440000"); // doesn't exist
    SearchAllTranscripts searchAll = new SearchAllTranscripts(patient, "my");
    Map<String, List<Integer>> results = searchAll.getAllResults();
    assertTrue(results.size() == 0);
    tearDown();
  }

  @Test
  public void noPatientAppointmentsTest() {
    Database.makeDatabase();
    UUID patient = UUID.fromString("40d16dde-e035-4580-a5ca-cef945bdb8c4"); // exists, but no
                                                                            // appointments
    SearchAllTranscripts searchAll = new SearchAllTranscripts(patient, "my");
    Map<String, List<Integer>> results = searchAll.getAllResults();
    assertTrue(results.size() == 0);
    tearDown();
  }
}

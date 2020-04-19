import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.student.transcriptParser.FillEHRSections;
import edu.brown.cs.student.transcriptParser.ToParse;

public class ParserTest {
  ToParse parser = new ToParse();
  FillEHRSections filler;

  @Test
  public void basicTest() {
    File symptomsFile = new File("data/symptoms.csv");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n" + "what are your reasons for today's visit\n"
        + "\n" + "i fell from my bike and hit my head on a rock\n" + "\n"
        + "end reasons for visit\n" + "\n" + "what are your symptoms\n" + "\n"
        + "my head hurts and my stomach hurts\n" + "\n" + "end symptoms\n" + "\n" + "Gjntjtn";
    filler = new FillEHRSections("what are your symptoms", "end symptoms",
        "what are your reasons for today's visit", "end reasons for visit", symptoms,
        transcriptString);
    String res = filler.printFound();
    StringBuilder test = new StringBuilder();
    test.append("Reasons for Visit: \n\n");
    test.append("i fell from my bike and hit my head on a rock" + "\n\n");
    test.append("Symptoms Reported: \n\n");
    test.append("my head hurts\n");
    assertEquals(res, test.toString());
  }

  @Test
  public void realisticTest() {
    File symptomsFile = new File("data/symptoms.csv");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "hello, thanks for coming in what are your reasons for today's visit i ate some weird food and now i don't feel great end reasons for visit what are your symptoms i have the chills and i feel sick end symptoms okay have a good day";
    filler = new FillEHRSections("what are your symptoms", "end symptoms",
        "what are your reasons for today's visit", "end reasons for visit", symptoms,
        transcriptString);
    String res = filler.printFound();
    StringBuilder test = new StringBuilder();
    test.append("Reasons for Visit: \n\n");
    test.append("i ate some weird food and now i don't feel great" + "\n\n");
    test.append("Symptoms Reported: \n\n");
    test.append("i have the chills\n");
    test.append("i feel sick\n");
    assertEquals(res, test.toString());
  }

  @Test
  public void nothingTest() {
    File symptomsFile = new File("data/symptoms.csv");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "hello so today i wanted to see how you are doing and this test has none of the trigger sentences so nothing should show up";
    filler = new FillEHRSections("what are your symptoms", "end symptoms",
        "what are your reasons for today's visit", "end reasons for visit", symptoms,
        transcriptString);
    String res = filler.printFound();
    assertEquals(res, null);
  }

}

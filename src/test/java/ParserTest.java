import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;

import transcriptParser.FillEHRSections;
import transcriptParser.ToParse;

public class ParserTest {
  ToParse parser = new ToParse();
  FillEHRSections filler;

  @Test
  public void basicTest() {
    File symptomsFile = new File("data/symptoms.csv");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n"
        + "what are your reasons for todays visit\n" + "\n"
        + "I fell from my bike and hit my head on a rock\n" + "\n"
        + "stop reasons for visit\n" + "\n" + "what are your symptoms\n" + "\n"
        + "my head hurts and my stomach hurts\n" + "\n" + "stop symptoms\n"
        + "\n" + "Gjntjtn";
    filler = new FillEHRSections("what are your symptoms", "stop symptoms",
        "what are your reasons for todays visit", "stop reasons for visit",
        symptoms, transcriptString);
    String res = filler.printFound();
    StringBuilder test = new StringBuilder();
    test.append("Reasons for Visit: \n\n");
    test.append("I fell from my bike and hit my head on a rock" + "\n\n");
    test.append("Symptoms Reported: \n\n");
    test.append("my head hurts\n");
    assertEquals(res, test.toString());
  }

  @Test
  public void realisticTest() {
    File symptomsFile = new File("data/symptoms.csv");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "hello, thanks for coming in what are your reasons for today's visit I ate some weird food and now I don't feel great stop reasons for visit what are your symptoms I have the chills and I feel sick stop symptoms okay have a good day";
    filler = new FillEHRSections("what are your symptoms", "stop symptoms",
        "what are your reasons for today's visit", "stop reasons for visit",
        symptoms, transcriptString);
    String res = filler.printFound();
    StringBuilder test = new StringBuilder();
    test.append("Reasons for Visit: \n\n");
    test.append("I ate some weird food and now I don't feel great" + "\n\n");
    test.append("Symptoms Reported: \n\n");
    test.append("I have the chills\n");
    test.append("I feel sick\n");
    assertEquals(res, test.toString());
  }

  @Test
  public void nothingTest() {
    File symptomsFile = new File("data/symptoms.csv");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "hello so today I wanted to see how you are doing and this test has none of the trigger sentences so nothing should show up";
    filler = new FillEHRSections("what are your symptoms", "stop symptoms",
        "what are your reasons for today's visit", "stop reasons for visit",
        symptoms, transcriptString);
    String res = filler.printFound();
    assertEquals(res, null);
  }

  @Test
  public void noSuchFileTest() {
    File symptomsFile = new File("doesn't exist");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n"
        + "what are your reasons for today's visit\n" + "\n"
        + "I fell from my bike and hit my head on a rock\n" + "\n"
        + "stop reasons for visit\n" + "\n" + "what are your symptoms\n" + "\n"
        + "my head hurts and my stomach hurts\n" + "\n" + "stop symptoms\n"
        + "\n" + "Gjntjtn";
    filler = new FillEHRSections("what are your symptoms", "stop symptoms",
        "what are your reasons for today's visit", "stop reasons for visit",
        symptoms, transcriptString);
    String res = filler.printFound();
    assertEquals(res, null);
  }

  @Test
  public void emptyFileTest() {
    File symptomsFile = new File("");
    List<String> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n"
        + "what are your reasons for today's visit\n" + "\n"
        + "I fell from my bike and hit my head on a rock\n" + "\n"
        + "stop reasons for visit\n" + "\n" + "what are your symptoms\n" + "\n"
        + "my head hurts and my stomach hurts\n" + "\n" + "stop symptoms\n"
        + "\n" + "Gjntjtn";
    filler = new FillEHRSections("what are your symptoms", "end symptoms",
        "what are your reasons for today's visit", "stop reasons for visit",
        symptoms, transcriptString);
    String res = filler.printFound();
    assertEquals(res, null);
  }
}

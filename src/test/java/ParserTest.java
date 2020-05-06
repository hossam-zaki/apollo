import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import transcriptparser.FillEHRSections;
import transcriptparser.ToParse;

public class ParserTest {
  ToParse parser = new ToParse();
  FillEHRSections filler;

  @Test
  public void basicTest() {
    File symptomsFile = new File("data/categorized_symptoms.csv");
    Map<String, List<String>> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n" + "what are your reasons\n" + "\n"
        + "I fell from my bike and hit my head on a rock\n" + "\n" + "stop reasons\n" + "\n"
        + "what are your symptoms\n" + "\n" + "my head hurts and my stomach hurts\n" + "\n"
        + "stop symptoms\n" + "\n" + "Gjntjtn";
    filler = new FillEHRSections(symptoms, transcriptString);
    String res = filler.buildResult();
    StringBuilder test = new StringBuilder();
    test.append("<h5><b>Reasons for Visit: </b></h5>");
    test.append("<h5>\"I fell from my bike and hit my head on a rock\"</h5><br>");
    test.append("<h5><b>Symptoms Reported:</b></h5>");
    test.append("<h5>General: head hurts, stomach hurts</h5>");
    assertEquals(res, test.toString());
  }

  @Test
  public void realisticTest() {
    File symptomsFile = new File("data/categorized_symptoms.csv");
    Map<String, List<String>> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "hello, thanks for coming in what are your reasons I ate some weird food and now I don't feel great"
        + " stop reasons for visit what are your symptoms I have the chills and I feel sick stop symptoms okay have a good day";
    filler = new FillEHRSections(symptoms, transcriptString);
    String res = filler.buildResult();
    StringBuilder test = new StringBuilder();
    test.append("<h5><b>Reasons for Visit: </b></h5>");
    test.append("<h5>\"I ate some weird food and now I don't feel great\"</h5><br>");
    test.append("<h5><b>Symptoms Reported:</b></h5>");
    test.append("<h5>General: chills</h5>");
    test.append("<h5>Gastrointestinal: feel sick</h5>");
    assertEquals(res, test.toString());
  }

  @Test
  public void nothingTest() {
    File symptomsFile = new File("data/categorized_symptoms.csv");
    Map<String, List<String>> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "hello so today I wanted to see how you are doing and this test has none of the trigger sentences so nothing should show up";
    filler = new FillEHRSections(symptoms, transcriptString);
    String res = filler.buildResult();
    StringBuilder test = new StringBuilder();
    test.append("<h5><b>Reasons for Visit: </b></h5><br>");
    test.append("<h5><b>Symptoms Reported:</b></h5>");
    assertEquals(res, test.toString());
  }

  @Test
  public void noSuchFileTest() {
    File symptomsFile = new File("doesn't exist");
    Map<String, List<String>> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n" + "what are your reasons\n" + "\n"
        + "I fell from my bike and hit my head on a rock\n" + "\n" + "stop reasons for visit\n"
        + "\n" + "what are your symptoms\n" + "\n" + "my head hurts and my stomach hurts\n" + "\n"
        + "stop symptoms\n" + "\n" + "Gjntjtn";
    filler = new FillEHRSections(symptoms, transcriptString);
    String res = filler.buildResult();
    assertEquals(res, null);
  }

  @Test
  public void emptyFileTest() {
    File symptomsFile = new File("");
    Map<String, List<String>> symptoms = parser.readSymptoms(symptomsFile);
    String transcriptString = "Jcnrjncr\n" + "\n" + "what are your reasons for today's visit\n"
        + "\n" + "I fell from my bike and hit my head on a rock\n" + "\n"
        + "end reasons for visit\n" + "\n" + "what are your symptoms\n" + "\n"
        + "my head hurts and my stomach hurts\n" + "\n" + "end symptoms\n" + "\n" + "Gjntjtn";
    filler = new FillEHRSections(symptoms, transcriptString);
    String res = filler.buildResult();
    assertEquals(res, null);
  }

  @Test
  public void stringFromFileTest() {
    File symptomsFile = new File("data/parserTests/realisticTest.txt");
    String result = parser.getTranscriptString(symptomsFile);
    String transcriptString = "hello, thanks for coming in what are your reasons"
        + " for todays visit I ate some weird food and now I don't feel great stop reasons for visit what "
        + "are your symptoms I have the chills and I feel sick stop symptoms okay have a good day\n";
    assertTrue(result.equals(transcriptString));
    File badFile = new File("badFile.txt");
    String badResult = parser.getTranscriptString(badFile);
    assertEquals(badResult, null);
  }
}
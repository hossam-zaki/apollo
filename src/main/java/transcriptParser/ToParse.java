package transcriptParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import repl.Executable;

public class ToParse implements Executable {

  public String getTranscriptString(File transcript) {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(transcript));
      String line = null;
      StringBuilder stringBuilder = new StringBuilder();
      String ls = System.getProperty("line.separator");
      try {
        while ((line = reader.readLine()) != null) {
          stringBuilder.append(line);
          stringBuilder.append(ls);
        }
        reader.close();
        return stringBuilder.toString();
      } catch (IOException e) {
        System.err.println("ERROR: reading file");

      }
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: initializing reader");
    }

    return null;

  }

  public List<String> readSymptoms(File symptomsFile) {
    System.out.println(symptomsFile);
    List<String> symptoms = new ArrayList<String>();
    BufferedReader csvReader = null;
    try {
      csvReader = new BufferedReader(new FileReader(symptomsFile));
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: Symptoms File not found");
      symptoms.add("ERROR: Symptoms File not found");
      return symptoms;
    }
    try {
      String info;
      csvReader.readLine();
      while ((info = csvReader.readLine()) != null) {
        symptoms.add(info);
      }
    } catch (IOException e) {
      System.err.println("ERROR: Symptoms Read error");
      symptoms.add("ERROR: Symptoms File not found");
      return symptoms;
    } finally {
      try {
        csvReader.close();
      } catch (IOException e) {
        System.err.println("ERROR: closing file");
      }
    }
    return symptoms;
  }

  @Override
  public void executeCommand(List<String> input) {
    File transcript = new File(input.get(1));
    String transcriptString = this.getTranscriptString(transcript);
    File symptomsFile = new File(input.get(2));
    List<String> symptoms = this.readSymptoms(symptomsFile);
    String symStart = "what are your symptoms";
    String symEnd = "stop symptoms";
    String vStart = "what are your reasons";
    String vEnd = "stop reasons";
    FillEHRSections fill = new FillEHRSections(symStart, symEnd, vStart, vEnd,
        symptoms, transcriptString);
    fill.printToFile();
  }
}

package transcriptParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import repl.Executable;

public class ToParse implements Executable {
  private String result;

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

  public Map<String, List<String>> readSymptoms(File symptomsFile) {
    Map<String, List<String>> symptoms = new HashMap<String, List<String>>();
    List<String> general = new ArrayList<String>();
    List<String> cardiovascular = new ArrayList<String>();
    List<String> earNoseThroat = new ArrayList<String>();
    List<String> gastro = new ArrayList<String>();
    List<String> pulmonary = new ArrayList<String>();
    List<String> neurologic = new ArrayList<String>();
    List<String> rheumatologic = new ArrayList<String>();
    List<String> urologic = new ArrayList<String>();
    BufferedReader csvReader = null;
    try {
      csvReader = new BufferedReader(new FileReader(symptomsFile));
    } catch (FileNotFoundException e) {
      System.err.println("ERROR: Symptoms File not found");
      return symptoms;
    }
    try {
      String info;
      csvReader.readLine();
      while ((info = csvReader.readLine()) != null) {
        String[] split = info.split(",");
        if (!split[0].equals(" ")) {
          general.add(split[0]);
        }
        if (!split[1].equals(" ")) {
          cardiovascular.add(split[1]);
        }
        if (!split[2].equals(" ")) {
          earNoseThroat.add(split[2]);
        }
        if (!split[3].equals(" ")) {
          gastro.add(split[3]);
        }
        if (!split[4].equals(" ")) {
          pulmonary.add(split[4]);
        }
        if (!split[5].equals(" ")) {
          neurologic.add(split[5]);
        }
        if (!split[6].equals(" ")) {
          rheumatologic.add(split[6]);
        }
        if (!split[7].equals(" ")) {
          urologic.add(split[7]);
        }
      }
      symptoms.put("General", general);
      symptoms.put("Cardiovascular", cardiovascular);
      symptoms.put("Ear, Nose, Throat", earNoseThroat);
      symptoms.put("Gastrointestinal", gastro);
      symptoms.put("Pulmonary", pulmonary);
      symptoms.put("Neurologic", neurologic);
      symptoms.put("Rheumatologic", rheumatologic);
      symptoms.put("Urologic", urologic);
      int numEmpty = 0;
      for (String key : symptoms.keySet()) {
        if (symptoms.get(key).isEmpty()) {
          numEmpty++;
        }
      }
      if (numEmpty == 8) {
        System.err.println("ERROR: Invalid symptoms file");
        return null;
      }
    } catch (IOException e) {
      System.err.println("ERROR: Symptoms Read error");
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
    Map<String, List<String>> symptoms = this.readSymptoms(symptomsFile);
    String symStart = "what are your symptoms";
    String symEnd = "stop symptoms";
    String vStart = "what are your reasons";
    String vEnd = "stop reasons";
    FillEHRSections fill = new FillEHRSections(symStart, symEnd, vStart, vEnd,
        symptoms, transcriptString);
    this.result = fill.buildResult();
  }

  public String getResult() {
    return result;
  }

}

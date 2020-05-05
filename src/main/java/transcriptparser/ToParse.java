package transcriptparser;

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

/**
 * This class is used to parse the symptoms found in KMP and give them the
 * correct categorical labeling.
 *
 */
public class ToParse implements Executable {
  private String result;

  static final int COL7 = 7;
  static final int COL8 = 8;

  /**
   * This method gets the transcript string from a transcript file. Used for
   * testing.
   *
   * @param transcript A file, representing the transcript file.
   * @return A String, representing the string read in from the file.
   */
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

  /**
   * This method reads the symtpoms from a CSV file of symptoms.
   *
   * @param symptomsFile A File of symptoms.
   * @return A Ma from String to a List of Strings, where each category is mapped
   *         to a list of symptoms belonging to the category.
   */
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
        if (!split[COL7].equals(" ")) {
          urologic.add(split[COL7]);
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
      if (numEmpty == COL8) {
        System.err.println("ERROR: Invalid symptoms file");
        return null;
      }
    } catch (Exception e) {
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

  /**
   * This isi the execute command method needed to run the command in the REPL,
   * used for testing.
   */
  @Override
  public void executeCommand(List<String> input) {
    if (input.size() != 3) {
      System.err.println("ERROR: invalid command");
    } else {
      File transcript = new File(input.get(1));
      String transcriptString = this.getTranscriptString(transcript);
      File symptomsFile = new File(input.get(2));
      Map<String, List<String>> symptoms = this.readSymptoms(symptomsFile);
      FillEHRSections fill = new FillEHRSections(symptoms, transcriptString);
      this.result = fill.buildResult();
    }
  }

  /**
   * Getter for the result String.
   *
   * @return A String, representing the result.
   */
  public String getResult() {
    return result;
  }

}

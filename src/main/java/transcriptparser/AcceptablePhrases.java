package transcriptparser;

import java.util.ArrayList;
import java.util.List;

/**
 * This class outlines all acceptable phrases that a doctor can say for our
 * software to transcribe symptoms and a patient's reasons for visit.
 *
 */
public final class AcceptablePhrases {

  /**
   * Empty constructor.
   */
  private AcceptablePhrases() {
    // not called
  }

  /**
   * This method outlines acceptables phrases to begin listening for symtpoms.
   *
   * @return A List of Strings, representing all acceptable phrases to begin
   *         listening for symptoms.
   */
  public static List<String> getSymptomsStartPhrases() {
    List<String> start = new ArrayList<String>();
    start.add("start symptoms");
    start.add("Start symptoms");
    start.add("begin symptoms");
    start.add("Begin symptoms");
    start.add("what are your symptoms");
    start.add("What are your symptoms");
    start.add("what symptoms do you have");
    start.add("What symptoms do you have");
    return start;
  }

  /**
   * This method outlines acceptable phrases to stop listening for symptoms.
   *
   * @return A List of Strings, representing all acceptable phrases to stop
   *         listening for symptoms.
   */
  public static List<String> getSymptomsEndPhrases() {
    List<String> end = new ArrayList<String>();
    end.add("end symptoms");
    end.add("and symptoms");
    end.add("and the symptoms");
    end.add("End symptoms");
    end.add("stop symptoms");
    end.add("Stop symptoms");
    end.add("stopped symptoms");
    end.add("Stopped symptoms");
    end.add(". Symptoms");
    end.add("no more symptoms");
    end.add("No more symptoms");
    return end;
  }

  /**
   * This method outlines all acceptable phrases to start listening for reasons
   * for a patient's visit.
   *
   * @return A List of Strings, representing all acceptable phrases to start
   *         listening for reasons for a patient's visit.
   */
  public static List<String> getVisitStartPhrases() {
    List<String> start = new ArrayList<String>();
    start.add("start reasons");
    start.add("Start reasons");
    start.add("begin reasons");
    start.add("Begin reasons");
    start.add("what are your reasons");
    start.add("What are your reasons");
    start.add("what brings you here today");
    start.add("What brings you here today");
    return start;
  }

  /**
   * This method outlines all acceptable phrases to stop listening for reasons for
   * a patient's visit.
   *
   * @return A List of Strings, representing all acceptable phrases to stop
   *         listening for reasons for a patient's visit.
   */
  public static List<String> getVisitEndPhrases() {
    List<String> end = new ArrayList<String>();
    end.add("end reasons");
    end.add("and reasons");
    end.add("and the reasons");
    end.add("End reasons");
    end.add("stop reasons");
    end.add("Stop reasons");
    end.add("stopped reasons");
    end.add("Stopped reasons");
    end.add(". Reasons");
    end.add("no more reasons");
    end.add("No more reasons");
    return end;
  }
}

package patient;

import java.util.List;

import registrationandlogin.Encryption;

/**
 * Creates an object for visit registered.
 */
public class VisitDatum implements Datum {
  private String id;
  private String pUuid;
  private String date;
  private byte[] transcript;
  private byte[] audioRecording;
  private String time;
  private byte[] visitType;

  /**
   * Constructor with information from database.
   *
   * @param details        list of id, doctor, patient, date
   * @param transcript     recorded text
   * @param audioRecording audio recorded
   * @param time           of appointment
   * @param visitype       type of visit
   */
  public VisitDatum(List<String> details, byte[] transcript, byte[] audioRecording, String time,
      byte[] visitype) {
    this.id = details.get(0);
    this.pUuid = details.get(2);
    this.date = details.get(3);
    this.transcript = transcript;
    this.audioRecording = audioRecording;
    this.time = time;
    this.visitType = visitype;
  }

  // ---------- Getters for VisitDatum ----------
  @Override
  public String getID() {
    return this.id;
  }

  /**
   * @return patient's id
   */
  public String getPID() {
    return this.pUuid;
  }

  /**
   * @return appointment date
   */
  public String getDate() {
    return this.date;
  }

  /**
   * @return transcript
   */
  public String getTranscript() {
    try {
      return Encryption.decrypt(this.transcript);
    } catch (Exception e) {
      System.err.println("ERROR: decryption of transcript in visitDatum");
    }
    return "";
  }

  /**
   * @return audio
   */
  public String getAudioRecording() {
    try {
      return Encryption.decrypt(this.audioRecording);
    } catch (Exception e) {
      System.err.println("ERROR: decryption of audio path in visitDatum");
    }
    return "";
  }

  /**
   * @return appointment time
   */
  public String getTime() {
    return this.time;
  }

  /**
   * @return type of visit
   */
  public String getVisitType() {
    try {
      return Encryption.decrypt(this.visitType);
    } catch (Exception e) {
      System.err.println("ERROR: decryption of visitype in visitDatum");
    }
    return "";
  }

  // ---------- Setters for VisitDatum ----------
  @Override
  public void setID(String newId) {
    this.id = newId;
  }

  /**
   * @param date to set
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * @param transcript to set
   */
  public void setTranscript(byte[] transcript) {
    this.transcript = transcript;
  }

  /**
   * @param audio to assign
   */
  public void setAudioRecording(byte[] audio) {
    this.audioRecording = audio;
  }

  /**
   * @param time to set
   */
  public void setTime(String time) {
    this.time = time;
  }

}

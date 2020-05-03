package patient;

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
   * @param id             unique id for appointment
   * @param doctor         holding appointment
   * @param patient        whose appointment it is
   * @param date           of appointment
   * @param transcript     recorded text
   * @param audioRecording audio recorded
   * @param time           of appointment
   * @param visitype       type of visit
   */
  public VisitDatum(String id, String doctor, String patient, String date, byte[] transcript,
      byte[] audioRecording, String time, byte[] visitype) {
    this.id = id;
    this.pUuid = patient;
    this.date = date;
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

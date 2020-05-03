package patient;

/**
 * Creates an object for visit registered.
 */
public class VisitDatum implements Datum {
  private String id;
  private String pUuid;
  private String date;
  private String transcript;
  private byte[] audioRecording;
  private String time;

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
   */
  public VisitDatum(String id, String doctor, String patient, String date, String transcript,
      byte[] audioRecording, String time) {
    this.id = id;
    this.pUuid = patient;
    this.date = date;
    this.transcript = transcript;
    this.audioRecording = audioRecording;
    this.time = time;
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
    return this.transcript;
  }

  /**
   * @return audio recording
   */
  public byte[] getAudioRecording() {
    return this.audioRecording;
  }

  /**
   * @return appointment time
   */
  public String getTime() {
    return this.time;
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
  public void setTranscript(String transcript) {
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

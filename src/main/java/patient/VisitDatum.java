package patient;

public class VisitDatum implements Datum {
  private String id;
  private String Puuid;
  private String date;
  private String transcript;
  private byte[] audioRecording;
  private String time;
  private String visitType;

  public VisitDatum(String id, String doctor, String patient, String date, String transcript,
      byte[] audioRecording, String time, String visitype) {
    this.id = id;
    this.Puuid = patient;
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

  public String getPID() {
    return this.Puuid;
  }

  public String getDate() {
    return this.date;
  }

  public String getTranscript() {
    return this.transcript;
  }

  public byte[] getAudioRecording() {
    return this.audioRecording;
  }

  public String getTime() {
    return this.time;
  }

  public String getVisitType() {
    return this.visitType;
  }

  // ---------- Setters for VisitDatum ----------
  @Override
  public void setID(String id) {
    this.Puuid = id;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTranscript(String transcript) {
    this.transcript = transcript;
  }

  public void setAudioRecording(byte[] audio) {
    this.audioRecording = audio;
  }

  public void setTime(String time) {
    this.time = time;
  }

}

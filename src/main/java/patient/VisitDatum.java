package patient;

import registrationandlogin.Encryption;

public class VisitDatum implements Datum {
  private String id;
  private String Puuid;
  private String date;
  private byte[] transcript;
  private byte[] audioRecording;
  private String time;
  private byte[] visitType;

  public VisitDatum(String id, String doctor, String patient, String date, byte[] transcript,
      byte[] audioRecording, String time, byte[] visitype) {
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
    try {
      return Encryption.decrypt(this.transcript);
    } catch (Exception e) {
      System.err.println("ERROR: decryption of transcript in visitDatum");
    }
    return "";
  }

  public String getAudioRecording() {
    try {
      return Encryption.decrypt(this.audioRecording);
    } catch (Exception e) {
      System.err.println("ERROR: decryption of audio path in visitDatum");
    }
    return "";
  }

  public String getTime() {
    return this.time;
  }

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
  public void setID(String id) {
    this.Puuid = id;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTranscript(byte[] transcript) {
    this.transcript = transcript;
  }

  public void setAudioRecording(byte[] audio) {
    this.audioRecording = audio;
  }

  public void setTime(String time) {
    this.time = time;
  }

}

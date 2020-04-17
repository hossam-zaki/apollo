package edu.brown.cs.student.patientData;

import java.io.File;
import java.util.UUID;

public class VisitDatum implements Datum {
  private UUID uuid;
  private String date;
  private File transcript;
  private File audioRecording;

  public VisitDatum(UUID id, String date, File transcript,
      File audioRecording) {
    this.uuid = id;
    this.date = date;
    this.transcript = transcript;
    this.audioRecording = audioRecording;
  }

  // ---------- Getters for VisitDatum ----------
  @Override
  public UUID getID() {
    return this.uuid;
  }

  public String getDate() {
    return this.date;
  }

  public File getTranscript() {
    return this.transcript;
  }

  public File getAudioRecording() {
    return this.audioRecording;
  }

  // ---------- Setters for VisitDatum ----------
  @Override
  public void setID(UUID id) {
    this.uuid = id;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setTranscript(File transcript) {
    this.transcript = transcript;
  }

  public void setAudioRecording(File audio) {
    this.audioRecording = audio;
  }

}

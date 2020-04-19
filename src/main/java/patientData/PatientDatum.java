package patientData;

import java.util.UUID;

public class PatientDatum implements Datum {
  private UUID uuid;
  private String firstName;
  private String middleName;
  private String lastName;
  private String dateOfBirth;
  private String phoneNumber;
  private String email;
  private String emergencyPhoneNumber;
  private UUID primaryDoctorID;

  public PatientDatum(UUID uuid, String firstName, String middleName,
      String lastName, String dateOfBirth, String phoneNumber, String email,
      String emergencyPhoneNumber, UUID primaryDoctorID) {
    this.uuid = uuid;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.emergencyPhoneNumber = emergencyPhoneNumber;
    this.primaryDoctorID = primaryDoctorID;
  }

  // --------- Getters for PatientDatum Class ---------
  @Override
  public UUID getID() {
    return this.uuid;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getMiddleName() {
    return this.middleName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getDateOfBirth() {
    return this.dateOfBirth;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public String energencyPhoneNumber() {
    return this.emergencyPhoneNumber;
  }

  public String getEmail() {
    return this.email;
  }

  public UUID getPrimaryDoctorID() {
    return this.primaryDoctorID;
  }

  // --------- Setters for PatientDatum Class ---------
  @Override
  public void setID(UUID id) {
    this.uuid = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setPhoneNumber(String number) {
    this.phoneNumber = number;
  }

  public void setEmergencyPhoneNumber(String number) {
    this.emergencyPhoneNumber = number;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPrimaryDoctorID(UUID id) {
    this.primaryDoctorID = id;
  }

}

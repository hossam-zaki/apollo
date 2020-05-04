package patient;

import java.util.List;

/**
 * Creates an object for patient registered.
 */
public class PatientDatum implements Datum {
  private String uuid;
  private String firstName;
  private String middleName;
  private String lastName;
  private String dateOfBirth;
  private String phoneNumber;
  private String email;
  private String emergencyPhoneNumber;
  private String docUsername;

  static final int COL7 = 7;
  static final int COL8 = 8;

  /**
   * Constructor for information from database.
   *
   * @param dataDetails Strings to fill in
   */

  public PatientDatum(List<String> dataDetails) {
    this.uuid = dataDetails.get(0);
    this.firstName = dataDetails.get(1);
    this.middleName = dataDetails.get(2);
    this.lastName = dataDetails.get(3);
    this.dateOfBirth = dataDetails.get(4);
    this.phoneNumber = dataDetails.get(5);
    this.email = dataDetails.get(6);
    this.emergencyPhoneNumber = dataDetails.get(COL7);
    this.docUsername = dataDetails.get(COL8);
  }

  // --------- Getters for PatientDatum Class ---------
  @Override
  public String getID() {
    return this.uuid;
  }

  /**
   * @return patient's first name
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * @return patient's middle name
   */
  public String getMiddleName() {
    return this.middleName;
  }

  /**
   * @return patient's last name
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * @return patient's DOB
   */
  public String getDateOfBirth() {
    return this.dateOfBirth;
  }

  /**
   * @return patient's phone number
   */
  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  /**
   * @return patient's emergency contact
   */
  public String getEmergencyPhoneNumber() {
    return this.emergencyPhoneNumber;
  }

  /**
   * @return patient's email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * @return patient's primary doctor
   */
  public String getPrimaryDoctorUsername() {
    return this.docUsername;
  }

  // --------- Setters for PatientDatum Class ---------
  @Override
  public void setID(String id) {
    this.uuid = id;
  }

  /**
   * @param firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @param middleName to set
   */
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  /**
   * @param lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @param dateOfBirth to set
   */
  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  /**
   * @param number to set as phone number
   */
  public void setPhoneNumber(String number) {
    this.phoneNumber = number;
  }

  /**
   * @param number to set for emergency
   */
  public void setEmergencyPhoneNumber(String number) {
    this.emergencyPhoneNumber = number;
  }

  /**
   * @param email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @param us doctor username associated with this patient
   */
  public void setDoctorUsername(String us) {
    this.docUsername = us;
  }

}

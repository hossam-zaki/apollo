package patient;

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

  /**
   * Constructor for information from database.
   *
   * @param uuid                 patient's unique id
   * @param firstName            of patient
   * @param middleName           of patient
   * @param lastName             of patient
   * @param dateOfBirth          of patient
   * @param phoneNumber          of patient
   * @param email                of patient
   * @param emergencyPhoneNumber of patient
   * @param docUsername          of patient
   */
  public PatientDatum(String uuid, String firstName, String middleName, String lastName,
      String dateOfBirth, String phoneNumber, String email, String emergencyPhoneNumber,
      String docUsername) {
    this.uuid = uuid;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.emergencyPhoneNumber = emergencyPhoneNumber;
    this.docUsername = docUsername;
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

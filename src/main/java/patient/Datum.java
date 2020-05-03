package patient;

/**
 * Creates an object for a row in a database table.
 */
public interface Datum {

  /**
   * @return a unique id associated with the object
   */
  String getID();

  /**
   * @param id to set
   */
  void setID(String id);

}

package registrationandlogin;

import java.util.List;

/**
 * Interface for Registration classes (patient and visit).
 */
public interface RegisterData {

  /**
   * Register the information into the database.
   *
   * @param registrationDetails to register
   */
  void register(List<String> registrationDetails);
}

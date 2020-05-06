package apollo;

import java.util.List;

import databases.Database;
import patient.PatientDatum;

/**
 * This class is used to display patient information onto the GUI.
 */
public final class DisplayPatients {

  /**
   * Empty constructor for displayPatients class.
   */
  private DisplayPatients() {
  }

  /**
   * This method is needed to build the HTML that displays the information of all
   * patients of a doctor given a doctor username.
   *
   * @param username A String, representing a doctor's username.
   * @return A String, the HTML needed to display patient information on the GUI.
   */
  public static String buildHTML(String username) {
    List<PatientDatum> patients = Database.getDoctorPatients(username);
    if (patients == null) {
      return "";
    }
    StringBuilder html = new StringBuilder();
    if (patients.size() == 0) {
      html.append("<h3>Registered patients will appear here!</h3>");
    } else {
      html.append("<table class=\"table table-striped\" style=\"width: 80%; "
          + "color: black; margin-left:auto; margin-right:auto; border-collapse:separate;\">");
      html.append("<thead>");
      html.append("<tr>");
      html.append("<th scope=\"col\"><h5><b>Name</b></h5></th>"); // building table headers
      html.append("<th scope=\"col\"><h5>Date of Birth</h5></th>");
      html.append("<th scope=\"col\"><h5>Phone number</h5></th>");
      html.append("<th scope=\"col\"><h5>Email</h5></th>");
      html.append("<th scope=\"col\"><h5>Patient Link</h5></th>");
      html.append("</tr>");
      html.append("</thead>");
      html.append("<tbody id=\"patientTable\">");
      for (PatientDatum patient : patients) { // building each row in a loop
        html.append("<tr>");
        html.append("<td>");
        html.append(patient.getFirstName());
        html.append(" ");
        if (!patient.getMiddleName().equals("")) { // if the patient has no middle name
          html.append(patient.getMiddleName());
          html.append(" ");
        }
        html.append(patient.getLastName());
        html.append("</td>");
        html.append("<td>");
        html.append(patient.getDateOfBirth());
        html.append("</td>");
        html.append("<td>");
        html.append(patient.getEmail());
        html.append("</td>");
        html.append("<td>");
        html.append(patient.getPhoneNumber());
        html.append("</td>");
        html.append("<td>");
        html.append("<a href=\"/apollo/patientBase/:");
        html.append(username);
        html.append("/:");
        html.append(patient.getID());
        html.append("\">Go to patient");
        html.append("</a>");
        html.append("</tr>");

      }
      html.append("</table>");
    }
    return html.toString();
  }

}

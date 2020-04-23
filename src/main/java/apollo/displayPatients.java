package apollo;

import java.util.List;

import databases.Database;
import patientData.PatientDatum;

public class displayPatients {

  public displayPatients() {
  }

  public static String buildHTML(String username) {
    List<PatientDatum> patients = Database.getDoctorPatients(username);
    if (patients == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    // 4 folders per row
    html.append(
        "<table class=\"table-borderless\" style=\"margin-left:auto; margin-right:auto; border-collapse:separate; border-spacing:20px 10px;\">");
    html.append("<tr>");
    int count = 0;
    for (PatientDatum patient : patients) {
      if (count == 4) {
        html.append("</tr>\n<tr>");
        count = 0;
      }
      html.append("<td>");
      html.append("<a href=\"/apollo/:");
//      html.append(username);
//      html.append("&:");
      html.append(patient.getID());
      html.append(
          "\"><i class=\"fas fa-folder\" style=\"font-size:75px;\"> </i>");
      html.append("</a>");
      html.append("<h5>");
      if (!patient.getMiddleName().equals("")) {
        html.append(patient.getFirstName() + " "
            + patient.getMiddleName().charAt(0) + ".");
        html.append(" ");
      } else {
        html.append(patient.getFirstName());
        html.append(" ");
      }
      // html.append("<br>");
      html.append(patient.getLastName());
      html.append("</h5>");
      count++;
    }
    html.append("</table>");
    return html.toString();
  }

}

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
        "<table cellpadding=120px; style=\"width:400px; margin-left:auto; margin-right:auto; border-collapse:separate; border-spacing:20px 10px;\">");
    html.append("<tr>");
    int count = 0;
    for (PatientDatum patient : patients) {
      if (count == 4) {
        html.append("</tr>\n<tr>");
        count = 0;
      }
      html.append("<td>");
      html.append("<ahref=\"/apollo/:username&:patientID>");
      html.append("<i class=\"far fa-folder\" style=\"font-size:115px;\">");
      html.append("</a>");
      html.append("<h5>");
      html.append(patient.getFirstName());
      html.append(" ");
      if (!patient.getMiddleName().equals("")) {
        html.append(patient.getMiddleName());
        html.append(" ");
      }
      html.append("<br>");
      html.append(patient.getLastName());
      html.append("</h5>");
      count++;
    }
    html.append("</table>");
    return html.toString();
  }

}

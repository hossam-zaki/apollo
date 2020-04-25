package apollo;

import java.util.List;

import databases.Database;
import patientData.VisitDatum;

public class displayVisits {

  public displayVisits() {
  }

  public static String buildHTML(String docUsername, String patientID) {
    List<VisitDatum> visits = Database.getVisits(docUsername, patientID);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    // 4 folders per row
    html.append(
        "<table class=\"table-borderless\" style=\"margin-left:auto; margin-right:auto; border-collapse:separate; border-spacing:20px 10px;\">");
    html.append("<tr>");
    int count = 0;
    for (VisitDatum visit : visits) {
      if (count == 4) {
        html.append("</tr>\n<tr>");
        count = 0;
      }
      html.append("<td>");
      html.append("<a href=\"/apollo/patientBase/:");
//      html.append(username);
//      html.append("&:");
      html.append(docUsername);
      html.append("/:");
      html.append(visit.getID());
      html.append(
          "\"><i class=\"fas fa-folder\" style=\"font-size:75px;\"> </i>");
      html.append("</a>");
      html.append("<h5>");
      // html.append("<br>");
      html.append(visit.getDate());
      html.append("</h5>");
      count++;
    }
    html.append("</table>");
    return html.toString();
  }
}

package apollo;

import java.util.List;
import java.util.Set;

import databases.Database;
import patientData.VisitDatum;

/**
 * This class is used to display visits into the GUI.
 */
public class displayVisits {

  /**
   * Empty constructor for the displayVisits class.
   */
  public displayVisits() {
  }

  /**
   * This method is needed to display the visits for a given patients into the
   * GUI.
   *
   * @param docUsername A String, representing a doctor username.
   * @param patientID   A String, representing a patientID's generate unique ID.
   * @return A string, representing the HTML needed to display the patient's
   *         visits.
   */
  public static String buildHTML(String docUsername, String patientID) {
    List<VisitDatum> visits = Database.getVisits(docUsername, patientID);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    // 4 folders per row
    html.append("<br>");
    html.append("<br>");
    html.append("<br>");
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
      html.append("<a href=\"/apollo/:" + docUsername + "/:" + patientID
          + "/visit/:" + visit.getDate() + "/:" + visit.getID());
      html.append(
          "\"><i class=\"fas fa-folder\" style=\"font-size:75px;\"> </i>");
      html.append("</a>");
      html.append("<h5>");
      html.append(visit.getDate());
      html.append("</h5>");
      count++;
    }
    html.append("</table>");
    return html.toString();
  }

  public static String buildHTMLDates(String docUsername, String patientID,
      Set<String> dates) {
    List<VisitDatum> visits = Database.getVisitsFromDates(docUsername,
        patientID, dates);
    System.out.println(visits);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    // 4 folders per row
    html.append("<br>");
    html.append("<br>");
    html.append("<br>");
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
      html.append("<a href=\"/apollo/:" + docUsername + "/:" + patientID
          + "/visit/:" + visit.getDate() + "/:" + visit.getID());
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

  /**
   * This method builds HTML needed to display patient visits in the case that a
   * certain keyword is searched for.
   *
   * @param docUsername A String, a doctor's username.
   * @param patientID   A String, a unique generated patientID.
   * @param ids         A Set of Strings, representing the visit IDs we want to
   *                    display.
   * @return A String, representing the HTML needed to display these visits.
   */
  public static String buildHTMLid(String docUsername, String patientID,
      Set<String> ids) {
    List<VisitDatum> visits = Database.getVisitsFromIds(docUsername, patientID,
        ids);
    System.out.println(visits);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    // 4 folders per row
    html.append("<br>");
    html.append("<br>");
    html.append("<br>");
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
      html.append("<a href=\"/apollo/:" + docUsername + "/:" + patientID
          + "/visit/:" + visit.getDate() + "/:" + visit.getID());
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

  /**
   * This method is needed to display visits into the GUI in case that a
   * spacific data range is searched for.
   *
   * @param docUsername A String, a doctor's username.
   * @param patientID   A String, a unique patient IDs.
   * @param dates       A List of Strings, representing the dates between which
   *                    we want to find visits.
   * @return A String, representing the HTML string needed to display these
   *         visits into the GUI.
   */
  public static String buildHTMLDateRanges(String docUsername, String patientID,
      List<String> dates) {
    List<VisitDatum> visits = Database.getVisitsFromDateRanges(docUsername,
        patientID, dates);
    System.out.println(visits);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    // 4 folders per row
    html.append("<br>");
    html.append("<br>");
    html.append("<br>");
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
      html.append("<a href=\"/apollo/:" + docUsername + "/:" + patientID
          + "/visit/:" + visit.getDate() + "/:" + visit.getID());
      html.append(
          "\"><i class=\"fas fa-folder\" style=\"font-size:75px;\"> </i>");
      html.append("</a>");
      html.append("<h5>");
      html.append(visit.getDate());
      html.append("</h5>");
      count++;
    }
    html.append("</table>");
    return html.toString();
  }
}

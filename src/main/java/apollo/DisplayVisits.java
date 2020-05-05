package apollo;

import java.util.List;
import java.util.Set;

import databases.Database;
import patient.VisitDatum;

/**
 * This class is used to display visits into the GUI.
 */
public final class DisplayVisits {

  /**
   * Empty constructor for the displayVisits class.
   */
  private DisplayVisits() {
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
    html.append("<br>");

    if (visits.size() != 0) {
      html.append("<table class=\"table table-striped\" style=\"width: 80%; ");
      html.append(
          "color: black; margin-left:auto; margin-right:auto; border-collapse:separate;\">");
      html.append("<thead>");
      html.append("<tr>");
      html.append("<th scope=\"col\"><h4><b>Date</b></h4></th>");
      html.append("<th scope=\"col\"><h4>Time</h4></th>");
      html.append("<th scope=\"col\"><h4>Type of Visit</h4></th>");
      html.append("<th scope=\"col\"><h4>Visit Link</h4></th>");
      html.append("</tr>");
      html.append("</thead>");
      html.append("<tbody");
      for (VisitDatum visit : visits) { // building a table of visits
        html.append("<tr>");
        html.append("<td>");
        html.append(visit.getDate());
        html.append("</td>");
        html.append("<td>");
        html.append(visit.getTime().substring(0, 5));
        html.append("</td>");
        if (!visit.getVisitType().equals("")) { // if the doctor doesn't specify a visit type
          html.append("<td>");
          html.append(visit.getVisitType());
          html.append("</td>");
        } else {
          html.append("<td> N/A </td>");
        }
        html.append("<td>");
        html.append("<a href=\"/apollo/:");
        html.append(docUsername);
        html.append("/:");
        html.append(patientID);
        html.append("/visit/:");
        html.append(visit.getDate());
        html.append("/:");
        html.append(visit.getID());
        html.append("\">Go to Visit");
        html.append("</a>");
        html.append("</td>");
        html.append("</tr>");

      }
      html.append("</tbody>");
      html.append("</table>");
    } else {
      html.append("<br>");
      html.append("<h3>Registered visits will appear here!</h3>");
    }

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
  public static String buildHTMLid(String docUsername, String patientID, Set<String> ids) {
    List<VisitDatum> visits = Database.getVisitsFromIds(docUsername, patientID, ids);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    html.append("<br>");

    if (visits.size() != 0) {
      html.append("<table class=\"table table-striped\" style=\"width: 80%; ");
      html.append(
          "color: black; margin-left:auto; margin-right:auto; border-collapse:separate;\">");
      html.append("<thead>");
      html.append("<tr>");
      html.append("<th scope=\"col\"><h4><b>Date</b></h4></th>");
      html.append("<th scope=\"col\"><h4>Time</h4></th>");
      html.append("<th scope=\"col\"><h4>Type of Visit</h4></th>");
      html.append("<th scope=\"col\"><h4>Visit Link</h4></th>");
      html.append("</tr>");
      html.append("</thead>");
      html.append("<tbody");
      for (VisitDatum visit : visits) {
        html.append("<tr>");
        html.append("<td>");
        html.append(visit.getDate());
        html.append("</td>");
        html.append("<td>");
        html.append(visit.getTime().substring(0, 5));
        html.append("</td>");
        if (!visit.getVisitType().equals("")) {
          html.append("<td>");
          html.append(visit.getVisitType());
          html.append("</td>");
        } else {
          html.append("<td> N/A </td>");
        }
        html.append("<td>");
        html.append("<a href=\"/apollo/:");
        html.append(docUsername);
        html.append("/:");
        html.append(patientID);
        html.append("/visit/:");
        html.append(visit.getDate());
        html.append("/:");
        html.append(visit.getID());
        html.append("\">Go to Visit");
        html.append("</a>");
        html.append("</td>");
        html.append("</tr>");

      }
      html.append("</tbody>");
      html.append("</table>");
    } else {
      html.append("<br>");
      html.append("<h3>No visits found!</h3>");
    }

    return html.toString();
  }

  /**
   * This method is needed to display visits into the GUI in case that a spacific
   * data range is searched for.
   *
   * @param docUsername A String, a doctor's username.
   * @param patientID   A String, a unique patient IDs.
   * @param dates       A List of Strings, representing the dates between which we
   *                    want to find visits.
   * @return A String, representing the HTML string needed to display these visits
   *         into the GUI.
   */
  public static String buildHTMLDateRanges(String docUsername, String patientID,
      List<String> dates) {
    List<VisitDatum> visits = Database.getVisitsFromDateRanges(docUsername, patientID, dates);
    if (visits == null) {
      return "";
    }

    StringBuilder html = new StringBuilder();
    html.append("<br>");

    if (visits.size() != 0) {
      html.append("<table class=\"table table-striped\" style=\"width: 80%; color: black; "
          + "margin-left:auto; margin-right:auto; border-collapse:separate;\">");
      html.append("<thead>");
      html.append("<tr>");
      html.append("<th scope=\"col\"><h4><b>Date</b></h4></th>");
      html.append("<th scope=\"col\"><h4>Time</h4></th>");
      html.append("<th scope=\"col\"><h4>Type of Visit</h4></th>");
      html.append("<th scope=\"col\"><h4>Visit Link</h4></th>");
      html.append("</tr>");
      html.append("</thead>");
      html.append("<tbody");
      for (VisitDatum visit : visits) {
        html.append("<tr>");
        html.append("<td>");
        html.append(visit.getDate());
        html.append("</td>");
        html.append("<td>");
        html.append(visit.getTime().substring(0, 5));
        html.append("</td>");
        if (!visit.getVisitType().equals("")) {
          html.append("<td>");
          html.append(visit.getVisitType());
          html.append("</td>");
        } else {
          html.append("<td> N/A </td>");
        }

        html.append("<td>");
        html.append("<a href=\"/apollo/:");
        html.append(docUsername);
        html.append("/:");
        html.append(patientID);
        html.append("/visit/:");
        html.append(visit.getDate());
        html.append("/:");
        html.append(visit.getID());
        html.append("\">Go to Visit");
        html.append("</a>");
        html.append("</td>");
        html.append("</tr>");

      }
      html.append("</tbody>");
      html.append("</table>");
    } else {
      html.append("<br>");
      html.append("<h3>No visits found!</h3>");
    }

    return html.toString();
  }
}

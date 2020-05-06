package apollo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.common.collect.ImmutableMap;

import commands.ConnectToDatabase;
import databases.Database;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import patient.PatientDatum;
import patient.PatientRegistration;
import patient.VisitRegistration;
import registrationandlogin.DoctorRegistration;
import registrationandlogin.Encryption;
import registrationandlogin.Login;
import registrationandlogin.RegisterData;
import repl.Repl;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import speechtotext.RunDeepSpeech;
import transcriptparser.SearchAllTranscripts;
import transcriptparser.ToParse;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments.
   * @throws IOException will throw exception if this happens
   */
  public static void main(String[] args) throws IOException {
    Encryption.registerEncryption();
    new Main(args).run();

  }

  private String[] args;
  private static String error;

  private Main(String[] args) {
    this.args = args;
    error = "";
  }

  /**
   * Method to run program in terminal with option of GUI tag.
   *
   * @throws IOException May results in IO Exception in case of unwanted
   *                     behaviour.
   */
  private void run() throws IOException {
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      ConnectToDatabase connect = new ConnectToDatabase();
      connect.executeCommand(null);
      runSparkServer((int) options.valueOf("port"));
    }
    Repl repl = new Repl();
    repl.startRepl();

  }

  /**
   * Method to create the FreeMarker Engine used to build GUI.
   *
   * @return A free market engine.
   */
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n", templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  /**
   * Method that maps all necessary Spark Rotues for our GUI.
   *
   * @param port A port number on which to run our GUI.
   */
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/apollo", new FrontHandler(), freeMarker);
    Spark.get("/error", new ErrorHandler(), freeMarker);
    Spark.get("/register", new RegisterHandler(), freeMarker);
    Spark.post("/registerDoctor", new RegisterDoctorHandler(), freeMarker);
    Spark.post("/loginDoctor", new LoginDoctorHandler(), freeMarker);
    Spark.post("/send/:username/:patient", new SendHandler(), freeMarker);
    Spark.get("/apollo/:username", new BaseHandler(), freeMarker);
    Spark.get("/apollo/registerPatient/:username", new RegisterPatientHandler(), freeMarker);
    Spark.post("/apollo/registerPatient/addPatient/:username", new AddPatientHandler(), freeMarker);
    Spark.get("/apollo/patientBase/:username/:patient", new VisitHandler(), freeMarker);
    Spark.get("/apollo/account-details/:username", new AccountDetailsHandler(), freeMarker);
    Spark.get("/apollo/:username/:patient/registerVisit", new NewVisitHandler(), freeMarker);
    Spark.get("/apollo/:username/:patient/visit/:date/:id", new SingleVisitHandler(), freeMarker);
  }

  /**
   * Handle requests to the front page of our website.
   *
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status", error);
      error = "";
      return new ModelAndView(map, "homepage.ftl");
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * Handle requests to the front page of our website.
   *
   */
  private static class RegisterHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status", error);
      error = "";
      return new ModelAndView(map, "register.ftl");
    }
  }

  /**
   * Handles requests to log a doctor into the Apollo platform.
   *
   */
  private static class LoginDoctorHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      Login login = new Login();
      if (login.loginUser(qm.value("username"), qm.value("password")) == null) {
        error = "Username/Password incorrect";
        res.redirect("/apollo");
        return null;
      }
      error = "";
      res.redirect("/apollo/:" + qm.value("username"));
      return null;
    }
  }

  /**
   * Handle requests to register a doctor into our Apollo platform.
   *
   */
  private static class RegisterDoctorHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      List<String> forRegister = new ArrayList<String>();

      if (!qm.value("password").equals(qm.value("passwordVali"))) {
        error = "Passwords do not match!";
        res.redirect("/register");
        return null;
      }
      if (Database.ifUsernameExists(qm.value("username"))) {
        error = "Username is unavailable";
        res.redirect("/register");
        return null;
      }
      forRegister.add(qm.value("first_name"));
      forRegister.add(qm.value("middle_name"));
      forRegister.add(qm.value("last_name"));
      forRegister.add(qm.value("email"));
      forRegister.add(qm.value("username"));
      forRegister.add(qm.value("password"));
      forRegister.add(qm.value("phone"));
      forRegister.add(qm.value("institution"));
      DoctorRegistration register = new DoctorRegistration();
      register.register(forRegister);
      error = "";
      res.redirect("/apollo");
      return null;
    }
  }

  /**
   * Handles requests to actually create a visit entry into our database using a
   * certain recording. This is also where a text transcript is created and where
   * a visit summary is added to the database.
   */
  private static class SendHandler implements TemplateViewRoute {

    static final int CONFIG = 100000000;
    static final int BYTES = 1024;
    static final int START = 0; // indices for file name
    static final int MID = 10;
    static final int MID2 = 11;
    static final int END = 19;
    static final int COUNTER = 100000000;

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
      try {
        String username = request.params(":username").replaceAll(":", "");
        String patient = request.params(":patient").replaceAll(":", "");
        if (!Database.ifUsernameExists(username)) {
          response.redirect("/error");
          return null;
        }
        if (Database.getPatient(patient) == null) {
          response.redirect("/error");
          return null;
        }
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
            new MultipartConfigElement("/tmp", CONFIG, CONFIG, BYTES));
        String filename = request.raw().getPart("audio_data").getSubmittedFileName();
        String visitType = request.raw().getPart("typeMeeting").getSubmittedFileName();
        Part uploadedFile = request.raw().getPart("audio_data");
        final InputStream in = uploadedFile.getInputStream();
        StringBuilder file = new StringBuilder();
        file.append("src/main/resources/static/audio/");
        file.append(filename);
        file.append(".wav");
        Files.copy(in, Paths.get(file.toString()));
        RunDeepSpeech.transcribe(file.toString());
        System.out.println("Transcribing...");

        File myObj = new File(filename + ".txt");
        myObj.createNewFile();
        int counter = 0;
        while (Paths.get(filename + ".txt").toFile().exists()) {
          if (counter == COUNTER) {
            System.out.println("Waiting on speech-to-text...");
            counter = 0;
          }
          counter++;
        }
        String content = Files.readString(Paths.get("data/transcripts/test.txt"),
            StandardCharsets.US_ASCII);

        RegisterData visitRegister = new VisitRegistration();
        ToParse parser = new ToParse();
        ArrayList<String> input = new ArrayList<String>();
        input.add("parseTranscript");
        input.add("data/transcripts/test.txt");
        input.add("data/categorized_symptoms.csv");
        parser.executeCommand(input);
        String summary = parser.getResult();
        List<String> visitStrings = new ArrayList<String>();
        visitStrings.add(username);
        visitStrings.add(patient);
        visitStrings.add(filename.substring(START, MID));
        visitStrings.add(filename.substring(MID2, END));
        visitStrings.add("src/main/resources/static/audio/" + filename + ".wav");
        visitStrings.add(content);
        visitStrings.add(summary);
        visitStrings.add(visitType);
        visitRegister.register(visitStrings);
        Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status", error);
        error = "";

        Paths.get("data/transcripts/test.txt").toFile().delete();
        return new ModelAndView(map, "recording.ftl");
      } catch (Exception e) {
        response.redirect("/error");
        return null;
      }
    }

  }

  /**
   * Handle requests to the base page of our platform, where a doctor can see al
   * infromation about patients and register new patients.
   *
   */
  private static class BaseHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      String docName = Database.getDocName(username);
      String route = "/apollo/registerPatient/:" + username;
      Map<String, String> map = ImmutableMap.of("title", "Apollo", "docName", docName, "username",
          username, "route", route, "patients", DisplayPatients.buildHTML(username));
      return new ModelAndView(map, "base2.ftl");
    }
  }

  /**
   * Handle requests to the base page of our platform, where a doctor can see al
   * infromation about patients and register new patients.
   *
   */
  private static class ErrorHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, String> map = ImmutableMap.of("title", "Apollo");
      return new ModelAndView(map, "error.ftl");
    }
  }

  /**
   * Handles requests to the register patient page.
   */
  private static class RegisterPatientHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      Map<String, String> map = ImmutableMap.of("title", "Apollo", "username", username);
      return new ModelAndView(map, "registerPatient.ftl");
    }
  }

  /**
   * Handles requests to actually register a patient into our database.
   */
  private static class AddPatientHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String username = req.params(":username").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      List<String> forRegister = new ArrayList<String>();
      forRegister.add(qm.value("first_name"));
      forRegister.add(qm.value("middle_name"));
      forRegister.add(qm.value("last_name"));
      forRegister.add(qm.value("dob"));
      forRegister.add(qm.value("email"));
      forRegister.add(qm.value("phone"));
      forRegister.add(qm.value("emergency contact phone"));
      forRegister.add(req.params(":username").replaceAll(":", ""));
      RegisterData register = new PatientRegistration();
      register.register(forRegister);
      error = "";
      res.redirect("/apollo/:" + username);
      return null;
    }
  }

  /**
   * Handles requests to the visiits page for an individual patient, where all
   * patient visits are displayed.
   *
   */
  private static class VisitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qmap = req.queryMap();
      String username = req.params(":username").replaceAll(":", "");
      String patient = req.params(":patient").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      if (Database.getPatient(patient) == null) {
        res.redirect("/error");
        return null;
      }
      String route = "/apollo/:" + username + "/:" + patient + "/registerVisit";
      String route2 = "/apollo/patientBase/:" + username + "/:" + patient;
      PatientDatum patientData = Database.getPatient(patient);
      String visits = DisplayVisits.buildHTML(username, patient);
      Map<String, String> map = new HashMap<String, String>();
      map.put("title", "Apollo");
      map.put("username", username);
      String firstName = patientData.getFirstName();
      String middleName = patientData.getMiddleName();
      String lastName = patientData.getLastName();
      StringBuilder name = new StringBuilder();
      name.append(firstName);
      name.append(" ");
      name.append(middleName);
      name.append(" ");
      name.append(lastName);
      map.put("name", name.toString());
      map.put("route", route);
      map.put("visits", visits);
      map.put("route2", route2);
      map.put("patient", patient);
      try {
        String searched = req.queryParams("searched");
        String startDate = req.queryParams("startDate");
        String endDate = req.queryParams("endDate");
        if (searched != null && searched != "") {
          SearchAllTranscripts searcher = new SearchAllTranscripts();
          ArrayList<String> input = new ArrayList<String>();
          input.add("searchAll");
          input.add(patient);
          input.add(searched);
          searcher.executeCommand(input);
          Set<String> ids = new HashSet<String>();
          if (searcher.getAllResults() != null && !searcher.getAllResults().isEmpty()) {
            ids = searcher.getDates(searcher.getAllResults());
          }
          String visitsSearched = DisplayVisits.buildHTMLid(username, patient, ids);
          map.put("visits", visitsSearched);
        } else if (startDate != null && endDate != null) {
          startDate = dateProcessor(startDate);
          endDate = dateProcessor(endDate);
          List<String> dateRanges = new ArrayList<String>();
          dateRanges.add(startDate);
          dateRanges.add(endDate);
          String visitsDate = DisplayVisits.buildHTMLDateRanges(username, patient, dateRanges);
          map.put("visits", visitsDate);
        }
      } catch (Exception e) {
        res.redirect("/error");
        System.err.println("Nothing in search bar or no date range given");
      }
      return new ModelAndView(map, "visits.ftl");
    }

    /**
     * This is a parser that is used to convert dates from the JavaScript format
     * into the format that we use when registering a visit. This is needed for us
     * to properly search for visits between date ranges.
     *
     * @param date A String, representing a date to convert into our database's
     *             format.
     * @return A String, the same date into tehhe correct format: "day-month-year".
     */
    private String dateProcessor(String date) {
      String[] dateArray = new String[3];
      String[] splitDate = date.split("\\s+");
      dateArray[0] = splitDate[1]; // month
      dateArray[1] = splitDate[2]; // day
      dateArray[2] = splitDate[3]; // year
      Map<String, String> months = new HashMap<String, String>();
      months.put("Jan", "01");
      months.put("Feb", "02");
      months.put("Mar", "03");
      months.put("Apr", "04");
      months.put("May", "05");
      months.put("Jun", "06");
      months.put("Jul", "07");
      months.put("Aug", "08");
      months.put("Sep", "09");
      months.put("Oct", "10");
      months.put("Nov", "11");
      months.put("Dec", "12");
      String numericMonth = months.get(dateArray[0]);
      dateArray[0] = numericMonth;
      StringBuilder toRet = new StringBuilder();
      toRet.append(dateArray[2]);
      toRet.append("-");
      toRet.append(dateArray[0]);
      toRet.append("-");
      toRet.append(dateArray[1]);
      return toRet.toString();
    }
  }

  /**
   * Handles requests to view a doctor's account details page.
   */
  private static class AccountDetailsHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      String route = "/apollo/registerPatient/:" + username;
      String docName = Database.getDocName(username);
      Map<String, String> details = Database.getDoctorInfo(username);
      Map<String, Object> map = ImmutableMap.of("title", "Apollo", "route", route, "docName",
          docName, "details", details, "username", username);
      return new ModelAndView(map, "accountDetails.ftl");
    }
  }

  /**
   * Handles requests to register a new visit.
   *
   */
  private static class NewVisitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      String patient = req.params(":patient").replaceAll(":", "");
      PatientDatum patientData = Database.getPatient(patient);
      if (patientData == null) {
        res.redirect("/error");
        return null;
      }
      String route = "/apollo/:" + username + "/:" + patient + "/registerVisit";
      Map<String, String> map = ImmutableMap.of("title", "Apollo", "username", username, "name",
          patientData.getFirstName(), "route", route);
      return new ModelAndView(map, "registerVisit.ftl");
    }
  }

  /**
   * Handles requests to view the page for a single visit. This includes
   * displaying the visit transcript, as well as the visit summary, visit
   * recording, and patient details.
   *
   */
  private static class SingleVisitHandler implements TemplateViewRoute {

    static final int BYTES = 32; // for audio

    @Override
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      if (!Database.ifUsernameExists(username)) {
        res.redirect("/error");
        return null;
      }
      String patient = req.params(":patient").replaceAll(":", "");
      PatientDatum patientData = Database.getPatient(patient);
      if (patientData == null) {
        res.redirect("/error");
        return null;
      }
      String date = req.params(":date").replaceAll(":", "");
      String id = req.params(":id").replaceAll(":", "");
      if (!Database.isValidDate(id, date)) {
        res.redirect("/error");
        return null;
      }
      String audio = Database.getAudio(username, patient, id);
      if (audio == null) {
        res.redirect("/error");
        return null;
      }
      String route = "/apollo/patientBase/:" + username + "/:" + patient;
      String transcript = Database.getTranscript(username, patient, id);
      String summary = Database.getSummary(username, patient, id);
      String firstName = patientData.getFirstName();
      String middleName = patientData.getMiddleName();
      String lastName = patientData.getLastName();
      StringBuilder name = new StringBuilder();
      String dob = patientData.getDateOfBirth();
      String number = patientData.getPhoneNumber();
      String email = patientData.getEmail();
      name.append(firstName);
      name.append(" ");
      name.append(middleName);
      name.append(" ");
      name.append(lastName);
      if (summary == null) {
        summary = "We could not find any symptoms or reasons for visit in the transcript. "
            + "Please use the manual commands.";
      }
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("title", "Apollo");
      map.put("username", username);
      map.put("patient", patient);
      map.put("date", date);
      map.put("route", route);
      map.put("transcript", transcript);
      map.put("summary", summary);
      map.put("audio", audio.substring(BYTES));
      map.put("name", name);
      map.put("dob", dob);
      map.put("number", number);
      map.put("email", email);
      return new ModelAndView(map, "single_visit.ftl");
    }
  }

}

package apollo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import com.google.common.collect.ImmutableMap;

import commands.ConnectToDatabase;
import databases.Database;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import registrationAndLogin.Encryption;
import registrationAndLogin.Login;
import registrationAndLogin.PatientRegistration;
import registrationAndLogin.Registration;
import repl.Repl;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

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
//    Permissions permit = new Permissions();
//    permit.start();
    new Encryption();
    new Main(args).run();

  }

  private String[] args;
  private static String error;

  private Main(String[] args) {
    this.args = args;
    error = "";
  }

  private void run() throws IOException {
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      ConnectToDatabase connect = new ConnectToDatabase();
      connect.executeCommand(null);
      runSparkServer((int) options.valueOf("port"));
    }
    Repl repl = new Repl();
    repl.startRepl();

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/apollo", new FrontHandler(), freeMarker);
    Spark.get("/register", new RegisterHandler(), freeMarker);
    Spark.post("/registerDoctor", new RegisterDoctorHandler(), freeMarker);
    Spark.post("/loginDoctor", new LoginDoctorHandler(), freeMarker);
    Spark.get("/record", new RecordHandler(), freeMarker);
    Spark.post("/send", new SendHandler(), freeMarker);
    Spark.get("/apollo/:username", new baseHandler(), freeMarker);
    Spark.get("/apollo/registerPatient/:username", new registerPatientHandler(),
        freeMarker);
    Spark.post("/apollo/registerPatient/addPatient/:username",
        new addPatientHandler(), freeMarker);
    Spark.get("/apollo/:username&:patient", new visitHandler(), freeMarker);
    ;

  }

  /**
   * Handle requests to the front page of our Stars website.
   *
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status",
          error);
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
   * Handle requests to the front page of our Stars website.
   *
   */
  private static class RegisterHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status",
          error);
      error = "";
      return new ModelAndView(map, "register.ftl");
    }
  }

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
   * Handle requests to the front page of our Stars website.
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
      forRegister.add(qm.value("first_name"));
      forRegister.add(qm.value("middle_name"));
      forRegister.add(qm.value("last_name"));
      forRegister.add(qm.value("email"));
      forRegister.add(qm.value("username"));
      forRegister.add(qm.value("password"));
      forRegister.add(qm.value("phone"));
      forRegister.add(qm.value("institution"));
      Registration register = new Registration();
      register.register(forRegister);
      error = "";
      res.redirect("/apollo");
      return null;
    }
  }

  private static class RecordHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response)
        throws Exception {
      Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status",
          error);
      error = "";
      return new ModelAndView(map, "recording.ftl");
    }

  }

  private static class SendHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request request, Response response)
        throws Exception {
      try {
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
            new MultipartConfigElement("/tmp", 100000000, 100000000, 1024));
        String filename = request.raw().getPart("audio_data")
            .getSubmittedFileName();
        System.out.println(filename);
        Part uploadedFile = request.raw().getPart("audio_data");
        final InputStream in = uploadedFile.getInputStream();
        System.out
            .println(Files.copy(in, Paths.get("data/" + filename + ".wav")));

        response.redirect("/record");
        Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status",
            error);
        error = "";
        return new ModelAndView(map, "recording.ftl");
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

  }

  /**
   * Handle requests to the front page of our Stars website.
   *
   */
  private static class baseHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      String docName = Database.getDocName(username);
      String route = "/apollo/registerPatient/:" + username;
      new displayPatients();
      Map<String, String> map = ImmutableMap.of("title", "Apollo", "docName",
          docName, "username", username, "route", route, "patients",
          displayPatients.buildHTML(username));
      return new ModelAndView(map, "base2.ftl");
    }
  }

  private static class registerPatientHandler implements TemplateViewRoute {
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      Map<String, String> map = ImmutableMap.of("title", "Apollo", "username",
          username);
      return new ModelAndView(map, "registerPatient.ftl");
    }
  }

  private static class addPatientHandler implements TemplateViewRoute {
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      List<String> forRegister = new ArrayList<String>();
      forRegister.add(qm.value("first_name"));
      forRegister.add(qm.value("middle_name"));
      forRegister.add(qm.value("last_name"));
      forRegister.add(qm.value("dob"));
      forRegister.add(qm.value("email"));
      forRegister.add(qm.value("phone"));
      forRegister.add(qm.value("emergency contact phone"));
      forRegister.add(req.params(":username").replaceAll(":", ""));
      PatientRegistration register = new PatientRegistration();
      register.register(forRegister);
      error = "";
      res.redirect("/apollo/:" + req.params(":username").replaceAll(":", ""));
      return null;
    }
  }

  private static class visitHandler implements TemplateViewRoute {
    public ModelAndView handle(Request req, Response res) {
      String username = req.params(":username").replaceAll(":", "");
      String patient = req.params(":patient").replace(":", "");
      Map<String, String> map = ImmutableMap.of("title", "Apollo", "username",
          username, "patient", patient);
      return new ModelAndView(map, "visits.ftl");
    }
  }

}

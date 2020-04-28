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
import patientData.PatientDatum;
import registrationAndLogin.Encryption;
import registrationAndLogin.Login;
import registrationAndLogin.PatientRegistration;
import registrationAndLogin.Registration;
import registrationAndLogin.VisitRegistration;
import repl.Repl;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import speechToText.RunDeepSpeech;
import transcriptParser.SearchAllTranscripts;
import transcriptParser.ToParse;

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
		Spark.post("/send/:username/:patient", new SendHandler(), freeMarker);
		Spark.get("/apollo/:username", new baseHandler(), freeMarker);
		Spark.get("/apollo/registerPatient/:username", new registerPatientHandler(), freeMarker);
		Spark.post("/apollo/registerPatient/addPatient/:username", new addPatientHandler(), freeMarker);
		Spark.get("/apollo/patientBase/:username/:patient", new visitHandler(), freeMarker);
		Spark.get("/apollo/account-details/:username", new accountDetailsHandler(), freeMarker);
		Spark.get("/apollo/:username/:patient/registerVisit", new newVisitHandler(), freeMarker);
		Spark.get("/apollo/:username/:patient/visit/:date", new singleVisitHandler(), freeMarker);

	}

	/**
	 * Handle requests to the front page of our Stars website.
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
	 * Handle requests to the front page of our Stars website.
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
		public ModelAndView handle(Request request, Response response) throws Exception {
			Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status", error);
			error = "";
			return new ModelAndView(map, "recording.ftl");
		}

	}

	private static class SendHandler implements TemplateViewRoute {

		@Override
		public ModelAndView handle(Request request, Response response) throws Exception {
			try {
				request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
						new MultipartConfigElement("/tmp", 100000000, 100000000, 1024));
				String filename = request.raw().getPart("audio_data").getSubmittedFileName();
				System.out.println(filename);
				Part uploadedFile = request.raw().getPart("audio_data");
				final InputStream in = uploadedFile.getInputStream();
				Files.copy(in, Paths.get("data/" + filename + ".wav"));
				RunDeepSpeech.transcribe("data/" + filename + ".wav");
				System.out.println("Transcribing...");
				while (Paths.get("data/" + filename + ".wav").toFile().exists()) {
					;
				}
				String username = request.params(":username").replaceAll(":", "");
				String patient = request.params(":patient").replaceAll(":", "");
				String content = Files.readString(Paths.get("data/transcripts/test.txt"), StandardCharsets.US_ASCII);

				VisitRegistration visitRegister = new VisitRegistration();
				System.out.println(in.readAllBytes().toString());
				ToParse parser = new ToParse();
				ArrayList<String> input = new ArrayList<String>();
				input.add("parseTranscript");
				input.add("data/transcripts/test.txt");
				input.add("data/categorized_symptoms.csv");
				parser.executeCommand(input);
				String summary = parser.getResult();
				visitRegister.register(username, patient, filename.substring(0, 10), "data/" + filename + ".wav",
						content, summary);
				Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status", error);
				error = "";

				// Paths.get("data/transcripts/test.txt").toFile().delete();
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
			Map<String, String> map = ImmutableMap.of("title", "Apollo", "docName", docName, "username", username,
					"route", route, "patients", displayPatients.buildHTML(username));
			return new ModelAndView(map, "base2.ftl");
		}
	}

	private static class registerPatientHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			String username = req.params(":username").replaceAll(":", "");
			Map<String, String> map = ImmutableMap.of("title", "Apollo", "username", username);
			return new ModelAndView(map, "registerPatient.ftl");
		}
	}

	private static class addPatientHandler implements TemplateViewRoute {
		@Override
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
		@Override
		public ModelAndView handle(Request req, Response res) {
			QueryParamsMap qmap = req.queryMap();
			String username = req.params(":username").replaceAll(":", "");
			String patient = req.params(":patient").replaceAll(":", "");
			String route = "/apollo/:" + username + "/:" + patient + "/registerVisit";
			String route2 = "/apollo/patientBase/:" + username + "/:" + patient;
			PatientDatum patientData = Database.getPatient(patient);
			String visits = displayVisits.buildHTML(username, patient);
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", "Apollo");
			map.put("username", username);
			map.put("name", patientData.getFirstName());
			map.put("route", route);
			map.put("visits", visits);
			map.put("route2", route2);
			map.put("patient", patient);
			try {
				String searched = req.queryParams("searched");
				SearchAllTranscripts searcher = new SearchAllTranscripts();
				ArrayList<String> input = new ArrayList<String>();
				input.add("searchAll");
				input.add(patient);
				input.add(searched);
				searcher.executeCommand(input);
				String summary = searcher.getResult();
				Set<String> dates = searcher.getDates(searcher.getAllResults());
				// getVisits with this username, patient, dates
				// visits = new display visits method that also takes in date
			} catch (Exception e) {
				;
			}
			try {
				String startDate = req.queryParams("startDate");
				String endDate = req.queryParams("endDate");
				System.out.println(startDate + endDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView(map, "visits.ftl");
		}
	}

	private static class accountDetailsHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			String username = req.params(":username").replaceAll(":", "");
			String route = "/apollo/registerPatient/:" + username;
			String docName = Database.getDocName(username);
			Map<String, String> details = Database.getDoctorInfo(username);
			Map<String, Object> map = ImmutableMap.of("title", "Apollo", "route", route, "docName", docName, "details",
					details, "username", username);
			return new ModelAndView(map, "accountDetails.ftl");
		}
	}

	private static class newVisitHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			String username = req.params(":username").replaceAll(":", "");
			String patient = req.params(":patient").replaceAll(":", "");
			String route = "/apollo/:" + username + "/:" + patient + "/registerVisit";
			PatientDatum patientData = Database.getPatient(patient);
			Map<String, String> map = ImmutableMap.of("title", "Apollo", "username", username, "name",
					patientData.getFirstName(), "route", route);
			return new ModelAndView(map, "registerVisit.ftl");
		}
	}

	private static class singleVisitHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			String username = req.params(":username").replaceAll(":", "");
			String patient = req.params(":patient").replaceAll(":", "");
			String date = req.params(":date").replaceAll(":", "");
			// byte[] audio = Database.getAudioFile(username, patient, date);
			String route = "/apollo/:" + username + "/:" + patient + "/registerVisit";
			String transcript = Database.getTranscript(username, patient, date);
			String summary = Database.getSummary(username, patient, date);
			if (summary == null) {
				summary = "We could not find any symptoms or reasons for visit in the transcript. Please use the manual commands.";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "Apollo");
			map.put("username", username);
			map.put("patient", patient);
			map.put("date", date);
			map.put("route", route);
			// map.put("audio", audio);
			map.put("transcript", transcript);
			map.put("summary", summary);
			return new ModelAndView(map, "single_visit.ftl");
		}
	}

}

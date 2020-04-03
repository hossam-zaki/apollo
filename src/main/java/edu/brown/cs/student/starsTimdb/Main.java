package edu.brown.cs.student.starsTimdb;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.student.starsTimdb.commands.ConnectToDatabase;
import edu.brown.cs.student.starsTimdb.databases.Database;
import edu.brown.cs.student.starsTimdb.databases.DatabaseSetup;
import edu.brown.cs.student.starsTimdb.registrationAndLogin.Login;
import edu.brown.cs.student.starsTimdb.registrationAndLogin.Registration;
import edu.brown.cs.student.starsTimdb.repl.Repl;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
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
		Spark.get("/login", new LoginHandler(), freeMarker);
		Spark.post("/registerDoctor", new RegisterDoctorHandler(), freeMarker);
		Spark.post("/loginDoctor",new LoginDoctorHandler(), freeMarker);
	}

	/**
	 * Handle requests to the front page of our Stars website.
	 *
	 */
	private static class FrontHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			Map<String, Object> map = ImmutableMap.of("title", "Apollo");

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

			return new ModelAndView(map, "register.ftl");
		}
	}
	
	/**
	 * Handle requests to the front page of our Stars website.
	 *
	 */
	private static class LoginHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			Map<String, Object> map = ImmutableMap.of("title", "Apollo", "status", error);
			return new ModelAndView(map, "login.ftl");
		}
	}
	private static class LoginDoctorHandler implements TemplateViewRoute {
		@Override
		public ModelAndView handle(Request req, Response res) {
			QueryParamsMap qm = req.queryMap();
			Login login = new Login();
			if(login.loginUser(qm.value("username"), qm.value("password")) == null) {
				error = "Username/Password incorrect";
				res.redirect("/login");
			}
			res.redirect("/apollo");
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

			if(!qm.value("password").equals(qm.value("passwordVali"))) {
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
			res.redirect("/apollo");
			return null;
		}
	}
}

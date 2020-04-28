package databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import patientData.PatientDatum;
import patientData.VisitDatum;

/**
 * This is my database class, where I read from a database and make the instance
 * f a connection. I have a getter method to get the connection to the database.
 */
public final class Database {

	private static Connection conn;

	/**
	 * Empty necessary constructor for private initialization.
	 */
	private Database() {
	}

	/**
	 * Instantiates the database, creating tables if necessary. Automatically loads
	 * files.
	 *
	 * @param filename , file name of SQLite3 database to open.
	 * @return int , 1 for correct, 0 for errors.
	 */
	public static int makeDatabase() {
		// Initializes the database connection, turns foreign keys on.
		String filename = "data/db/apollo.sqlite3";
		try {
			FileReader file = new FileReader(filename);
		} catch (FileNotFoundException e1) {
			System.err.println("ERROR: File does not exist");
			return 0;
		}
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Database");
			return 0;
		}
		String urlToDB = "jdbc:sqlite:" + filename;
		try {
			conn = DriverManager.getConnection(urlToDB);
		} catch (SQLException e) {
			System.err.println("ERROR: Database");
			return 0;
		}
		// these two lines tell the database to enforce foreign keys during
		// operations,
		// and should be present
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate("PRAGMA foreign_keys=ON;");
		} catch (SQLException e) {
			System.err.println("ERROR: Database");
			return 0;
		}
		return 1;
	}

	/**
	 * Method that gets the connection to a database.
	 *
	 * @return connection to a database
	 */
	public static Connection getConn() {
		return conn;
	}

	public static String getDocName(String username) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("SELECT last_name FROM doctor WHERE username = ?");
			prep.setString(1, username);
			ResultSet rs = prep.executeQuery();
			String toRet = "";
			while (rs.next()) {
				toRet = rs.getString(1);
			}
			return toRet;
		} catch (Exception e) {
			System.err.println("ERROR: couldn't find doctor name");
			return null;
		}
	}

	public static List<PatientDatum> getDoctorPatients(String username) {
		PreparedStatement prep;
		try {
			List<PatientDatum> toRet = new ArrayList<PatientDatum>();
			prep = conn.prepareStatement("SELECT * FROM patient WHERE primary_doctor = ?");
			prep.setString(1, username);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				PatientDatum curr = new PatientDatum(rs.getString("id"), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9));
				toRet.add(curr);
			}
			return toRet;
		} catch (Exception e) {
			System.err.println("ERROR: no patients found");
			return null;
		}
	}

	public static Map<String, String> getDoctorInfo(String username) {
		PreparedStatement prep;
		try {
			Map<String, String> toRet = new LinkedHashMap<String, String>();
			prep = conn.prepareStatement("SELECT * FROM doctor WHERE username = ?");
			prep.setString(1, username);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				toRet.put("first name:", rs.getString(2)); // first name
				toRet.put("middle name:", rs.getString(3)); // middle name
				toRet.put("last name:", rs.getString(4)); // last name
				toRet.put("email:", rs.getString(5)); // email
				toRet.put("username:", rs.getString(6)); // username
				// toRet.put("password", rs.getString(7)); // password
				toRet.put("phone number:", rs.getString(8)); // phone number
				toRet.put("medical institution:", rs.getString(9)); // medical
																	// institution
			}
			return toRet;
		} catch (Exception e) {
			System.err.println("ERROR: doctor not found");
			return null;
		}
	}

	public static PatientDatum getPatient(String id) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("SELECT * FROM patient WHERE id = ?");
			prep.setString(1, id);
			ResultSet rs = prep.executeQuery();
			PatientDatum patient = new PatientDatum(rs.getString(1), // id
					rs.getString(2), // firstname
					rs.getString(3), // midname
					rs.getString(4), // lastname
					rs.getString(5), // dob
					rs.getString(6), // phone
					rs.getString(7), // email
					rs.getString(8), // emergency number
					rs.getString(9)); // docusername
			return patient;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ERROR: Patient not found");
			return null;
		}
	}

	public static List<VisitDatum> getVisits(String docUsername, String patientID) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ?");
			prep.setString(1, docUsername);
			prep.setString(2, patientID);
			ResultSet rs = prep.executeQuery();
			List<VisitDatum> toRet = new ArrayList<VisitDatum>();
			while (rs.next()) {
				VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(6), rs.getBytes(5));
				toRet.add(curr);
			}
			return toRet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: No visits found");
			return null;
		}
	}

	public static List<VisitDatum> getVisitsFromDates(String docUsername, String patientID, Set<String> dates) {
		PreparedStatement prep;
		try {
			List<VisitDatum> toRet = new ArrayList<VisitDatum>();
			for (String date : dates) {
				prep = conn.prepareStatement(
						"SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ? AND appointment_date = ?");
				prep.setString(1, docUsername);
				prep.setString(2, patientID);
				prep.setString(3, date);
				ResultSet rs = prep.executeQuery();
				while (rs.next()) {
					VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(6), rs.getBytes(5));
					toRet.add(curr);
				}
			}
			return toRet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: No visits found");
			return null;
		}
	}

	public static List<VisitDatum> getVisitsFromIds(String docUsername, String patientID, Set<String> Ids) {
		PreparedStatement prep;
		try {
			List<VisitDatum> toRet = new ArrayList<VisitDatum>();
			for (String id : Ids) {
				prep = conn.prepareStatement(
						"SELECT * FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
				prep.setString(1, docUsername);
				prep.setString(2, patientID);
				prep.setString(3, id);
				ResultSet rs = prep.executeQuery();
				while (rs.next()) {
					VisitDatum curr = new VisitDatum(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(6), rs.getBytes(5));
					toRet.add(curr);
				}
			}
			return toRet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: No visits found");
			return null;
		}
	}

	public static byte[] getVisitDetails(String docUsername, String patientID, String date) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement(
					"SELECT audio_file FROM appointments WHERE doctor_username = ? AND patient_id = ? AND appointment_date = ?");
			prep.setString(1, docUsername);
			prep.setString(2, patientID);
			prep.setString(3, date);
			ResultSet rs = prep.executeQuery();
			byte[] toRet = null;
			while (rs.next()) {
				toRet = rs.getBytes(1);
			}
			return toRet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: No audio file found");
			return null;
		}
	}

	public static String getTranscript(String docUsername, String patientID, String id) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement(
					"SELECT transcript FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
			prep.setString(1, docUsername);
			prep.setString(2, patientID);
			prep.setString(3, id);
			ResultSet rs = prep.executeQuery();
			String toRet = null;
			while (rs.next()) {
				toRet = rs.getString(1);
			}
			return toRet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: No audio file found");
			return null;
		}
	}

	public static String getSummary(String docUsername, String patientID, String id) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement(
					"SELECT summary FROM appointments WHERE doctor_username = ? AND patient_id = ? AND visit_id = ?");
			prep.setString(1, docUsername);
			prep.setString(2, patientID);
			prep.setString(3, id);
			ResultSet rs = prep.executeQuery();
			String toRet = null;
			while (rs.next()) {
				toRet = rs.getString(1);
			}
			return toRet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("ERROR: No audio file found");
			return null;
		}
	}

	public static Map<String, String> getAllTranscripts(String id) {
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("SELECT visit_id, transcript FROM appointments WHERE patient_id = ?");
			prep.setString(1, id);
			ResultSet rs = prep.executeQuery();
			Map<String, String> transcripts = new HashMap<String, String>();
			while (rs.next()) {
				transcripts.put(rs.getString(1), rs.getString(2));
			}
			return transcripts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ERROR: Patient not found");
			return null;
		}
	}
}

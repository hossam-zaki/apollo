package edu.brown.cs.student.starsTimdb.databases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author sqlitetutorial.net
 */
public class DatabaseSetup {

	/**
	 * Connect to a sample database
	 *
	 * @param fileName the database file name
	 */
	public static void createNewDatabase() {

		String url = "jdbc:sqlite:data/db/apollo.sqlite3";
		try {
			FileReader file = new FileReader(url);
			return;
		} catch (FileNotFoundException e1) {
			System.out.println("Generating Database...");
		}

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				PreparedStatement prep;
				prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS doctor(" + "id UUID," + "first_name TEXT," + "mid_name TEXT,"
						+ "last_name TEXT," + "email NVARCHAR(320)," + "username NVARCHAR(320),"
						+ "password NVARCHAR(320)," + "phoneNumber NVARCHAR(320)," + "institution TEXT);");
				prep.executeUpdate();
				prep = conn.prepareStatement(
						"INSERT INTO doctor VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
				UUID uuid = UUID.randomUUID(); 
				prep.setString(1, uuid.toString());
				prep.setString(2, "Hossam");
				prep.setString(3, "");
				prep.setString(4, "Zaki");
				prep.setString(5, "hossam_zaki@brown.edu");
				prep.setString(6, "hzaki1");
				prep.setString(7, "mohamedisabozo");
				prep.setString(8, "40155555555");
				prep.setString(9, "Brown University");
				prep.addBatch();
				prep.executeBatch();
				System.out.println("A new database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}

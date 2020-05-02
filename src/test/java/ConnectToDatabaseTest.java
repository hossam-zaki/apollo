import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import commands.ConnectToDatabase;
import databases.Database;

public class ConnectToDatabaseTest {
	@Test
	public void testDatabaseConnection() throws Exception {
		ConnectToDatabase connect = new ConnectToDatabase();
		List<String> in = new ArrayList<String>();
		in.add("build");
		connect.executeCommand(in);
		assertTrue(Database.getConn() != null);
	}
	@Test
    public void testDatabaseCreation() throws Exception {
	  
        ConnectToDatabase connect = new ConnectToDatabase();
        List<String> in = new ArrayList<String>();
        in.add("build");
        connect.executeCommand(in);
        try {
          FileReader file = new FileReader("data/db/apollo.sqlite3");
        } catch (FileNotFoundException e1) {
          fail();
          return;
        }
    }


}

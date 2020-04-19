package commands;

import java.util.List;

import databases.Database;
import databases.DatabaseSetup;
import repl.Executable;

public class ConnectToDatabase implements Executable {

	@Override
	public void executeCommand(List<String> string) {
		DatabaseSetup.createNewDatabase();
		Database.makeDatabase();
	}

}

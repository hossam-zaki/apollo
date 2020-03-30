package edu.brown.cs.student.starsTimdb.commands;

import java.util.List;

import edu.brown.cs.student.starsTimdb.databases.Database;
import edu.brown.cs.student.starsTimdb.databases.DatabaseSetup;
import edu.brown.cs.student.starsTimdb.repl.Executable;

public class ConnectToDatabase implements Executable {

	@Override
	public void executeCommand(List<String> string) {
		DatabaseSetup.createNewDatabase();
		Database.makeDatabase();
	}

}

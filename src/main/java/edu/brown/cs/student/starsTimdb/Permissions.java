package edu.brown.cs.student.starsTimdb;

import org.junit.internal.management.ManagementFactory;

public class Permissions {

//	private boolean IsAlreadyRunning()
//	{
//	    System.out.println("Checking if applet already running by opening applet locked file");
//	    try
//	    {
//	        file_locked_by_applet=new File("my_java_application.lock");
//	        // createNewFile atomically creates a new, empty file ... if and only if a file with this name does not yet exist. 
//
//	        System.out.println("Locked file path: " + file_locked_by_applet.getAbsolutePath());
//
//	        if (file_locked_by_applet.createNewFile())
//	        {
//	            System.out.println("Opened applet locked file successfully");
//	            file_locked_by_applet.deleteOnExit();
//	            return false;
//	        }
//
//	        System.out.println("Cannot open applet locked file. Applet might be already running.");
//	        return true;
//	    }
//	    catch (IOException e)
//	    {
//	        System.out.println("Exception while opening applet locked file. Applet might be already running.");
//	        e.printStackTrace();
//	        return true;
//	    }
//	}

	private boolean IsOSMacCatalina()
	{
	    System.out.println("Checking if current operating system is MacOS Catalina");
	    String OS = System.getProperty("os.name").toLowerCase();
	    String OSVersion = System.getProperty("os.version").toLowerCase();      
	    String OSArch = System.getProperty("os.arch").toLowerCase();

	    System.out.println("OS detected: " + OS);
	    System.out.println("OS version detected: " + OSVersion);
	    System.out.println("OS arch detected: " + OSArch);

	    if (OS.contains ("mac os") && OSVersion.contains("10.15"))
	    {   
	            System.out.println("Operating system: Mac Catalina detected");
	            return true;
	    }

	    System.out.println("Operating system is not Mac Catalina");
	    return false;

	}

	// Method that first gets invoked by applet at the beginning
	public void start() {
	    System.out.println("Starting applet here");
	    System.out.println("JNLP file name: " + System.getProperty("jnlpx.origFilenameArg"));
	    System.out.println("JVM command line: " + ManagementFactory.getRuntimeMXBean().getInputArguments());

	if ((!IsOSMacCatalina()))
	{
	    System.out.println("Either OS is not Catalina or applet is already launched with bash and javaws. Continuing with applet...");
	}
	else
	{
	    try
	    {
	        System.out.println("Applet running first time on Mac Catalina. Starting again with bash and javaws");

	        // "javaws -wait" causes javaws to start java process and wait for it to exit
	        String javawsCommandLine = "javaws -wait \"" + System.getProperty("jnlpx.origFilenameArg").replace("\\","/") + "\"";
	        System.out.println("bash javaws command line to run: " + javawsCommandLine);
	        // String[] args = new String[] {"bash", "-c", javawsCommandLine}; // Works on Windows where Bash is installed
	        String[] args = new String[] {"/bin/bash", "-c", javawsCommandLine};
	        System.out.println("---\nStarting bash javaws process withh args:");
	        for (String arg: args)
	            System.out.println(arg);
	        System.out.println("\n---");

	        // Runtime.getRuntime() discouraged. Hence we using ProcessBuilder
	        // Process proc = Runtime.getRuntime().exec("bash -c \"" + javawsCommandLine + "\"");

	        Process proc = new ProcessBuilder(args).start();

	        System.out.println("Waiting for bash process to finish");
	        proc.waitFor();
	        System.out.println("Bash process finished. Deleting instance locked file");
//	        file_locked_by_applet.delete();
	        System.out.println("Stopping applet here");
	    }
	    catch (java.io.IOException e) 
	    {
	        e.printStackTrace();
	    }
	    catch (java.lang.InterruptedException e)
	    {
	        e.printStackTrace();
	    }
	    return;             
	}
}
}

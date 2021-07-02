package edu.odu.cs.websiteanalyzer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.jsoup.Jsoup;


/** 
 * A class that handles the logic for the CLI.
 * It prints usage messages if there are no arguments.
 * It will call the other classes to handle input/output.
 * 
 * @author Evan Mulloy
 */
public class Console {
	
	/**
	 * The message printed when no command line arguments are given.
	 */
	private String usageMessage;
	
	/**
	 * The message printed when the directory is nonexistent.
	 */
	private String directoryErrorMessage;
	
	/**
	 * The message printed when URLs are malformed.
	 */
	private String malformedURLErrorMessage;
	
	/**
	 * The message printed when no URL argument (second parameter) is given.
	 */
	private String noURLErrorMessage;
	
	
	
	/**
	 * 
	 * The main function for the entire program.
	 * 
	 * Main should, ideally, call getConsoleOutput(args), and then print
	 * the message returned from that function.
	 * 
	 * 
	 * @param args Command-line arguments.
	 */
	static public void main (String args[]) 
    {
		String usage = "HOW TO USE WEBSITEANALYSIS: Enter the path of the directory of your local copy as the first parameter. You may specify a URL to be translated during analysis as additional parameters.";
		
		String directoryError = "ERROR: The path to the directory of your local copy is nonexistent or inaccessible.";
		
		String malformedURLError = "ERROR: One or more of the supplied URLs are malformed.";
		// missing URL not same as malformed URL.
		
		String noURLError = "ERROR: You failed to provide at least one URL.";
		
		Console console = new Console(usage, directoryError, malformedURLError, noURLError);
		
		System.out.println(console.getConsoleOutput(args));
		
    }
	
	/**
	 * The Default constructor.
	 * Every message should be "" by default.
	 * 
	 */
	public Console() {
		
		usageMessage = "";
		directoryErrorMessage = "";
		malformedURLErrorMessage = "";
		noURLErrorMessage = "";
		
		
	}
	
	/**
	 * 
	 * The non-default constructor.
	 * 
	 * @param newUsage The new usage message.
	 * @param newDirectoryError The new error message for non-existent or inaccessible local copy directories.
	 * @param newMalformedURLError The new error message for malformed URLs.
	 * @param newNoURLError The new error message for not supplying URLs.
	 */
	public Console(String newUsage, String newDirectoryError, String newMalformedURLError, String newNoURLError) {
		
		usageMessage = newUsage;
		directoryErrorMessage = newDirectoryError;
		malformedURLErrorMessage = newMalformedURLError;
		noURLErrorMessage = newNoURLError;
		
		
	}
	
	/**
	 * 
	 * getConsoleOutput returns an appropriate message when given, ideally, the same arguments 
	 * that main() is given.
	 * 
	 * It would return either the usage message, an error message, or the filenames depending on Main's passed arguments.
	 * 
	 * Main should pass the args[] as an argument to getMessage, and then proceed to print that message.
	 * 
	 * 
	 * @param args The command-line arguments given to main.
	 * @return An error message, a usage message, or the filenames.
	 */
	public String getConsoleOutput(String args[]) {
		
		
		
		// Note: You can only newlines in strings with \n
		
		if(args.length <= 0) {
			
			return usageMessage;
		}
			
		
		
		File siteRoot = new File(args[0]);
		
		// Check the first argument for an existing and accessible local copy
		
		try {
			if(!siteRoot.exists()) {
				
				return directoryErrorMessage;
			}
		} catch(SecurityException e) {
			
			return directoryErrorMessage;
		}
		
		
		// Check the second CLI parameter to make sure that at least 1 URL has been supplied
		if(args.length == 1) {
			
			return noURLErrorMessage;
		}
		
		// Check the second, third, fourth, etc. arguments for malformed URLs while building the URLs array for DIrectoryTraverser.
		
		String[] URLs = new String[args.length-1];
		
		for(int urlIndex = 1; urlIndex < args.length; urlIndex++) {
			
			if(!validateURL(args[urlIndex])) {
				
				return malformedURLErrorMessage;
			}
			
			URLs[urlIndex-1] = args[urlIndex];
			
		}
		
		
		DirectoryTraverser traverser = new DirectoryTraverser(args[0],URLs);
		
		String fileNames = "";
		
		
		for(String fileName : traverser.AnalyzeWebsite()) {
			
			if(fileName != "") {
			
				fileNames = fileNames.concat(fileName+"\n");
			
			}
			
		}
		
		if(fileNames == "") {
			
			return "Error: No files in specified directory";
		}
		
		
		/*
		ArrayList<String> arguments = new ArrayList<String>();
		
		Collections.addAll(arguments, args);
		
		
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd-hhmmss");
		
		String timeStamp = date.format(Calendar.getInstance().getTime());
		
		fileNames = ""+timeStamp+"-summary.json\n"+timeStamp+"-summary.xlsx\n"+timeStamp+"-summary.txt\n";
		*/
		
		
		return fileNames;
		
	}
	
	/**
	 * Validate whether a given URL is of a valid format.
	 * 
	 * 
	 * @param URL The URL as represented by a String.
	 * @return False if the URL is invalid, true if otherwise.
	 */
	public static boolean validateURL(String URL) {
		
		
		return ((URL.contains("http://") ||
				URL.contains("https://"))) &&
				!URL.contains("/.");
	}
	
	
	
	/**
	 * Gets the usage message displayed when no arguments are provided.
	 * 
	 * @return usageMessage The usage message.
	 */
	public String getUsageMessage() {
		
		
		return usageMessage;
	}
	
	/**
	 * 
	 * Returns the error message for if a directory is non-existent or inaccessible.
	 * 
	 * @return directoryErrorMessage The directory error message.
	 */
	public String getDirectoryErrorMessage() {
		
		
		return directoryErrorMessage;
	}
	
	
	/**
	 * 
	 * Returns the error message for if a URL is malformed.
	 * 
	 * @return urlErrorMessage The malformed URL error message.
	 */
	public String getMalformedURLErrorMessage() {
		
		
		return malformedURLErrorMessage;
	}
	
	/**
	 * Gets the error message for if no URLs have been supplied.
	 * 
	 * @return The un-supplied URL error message.
	 */
	public String getNoURLErrorMessage() {
		return noURLErrorMessage;
	}
	
	/**
	 * Sets the usage message for if no command-line arguments are provided.
	 * 
	 * @param newUsage The new usage message.
	 * 
	 */
	public void setUsageMessage(String newUsage) {
		
		usageMessage = newUsage;
		
	}
	
	/**
	 * 
	 * Sets the error message for if a directory is non-existent or inaccessible.
	 * 
	 * @param newError The new directory error message.
	 */
	public void setDirectoryErrorMessage(String newError) {
		
		directoryErrorMessage = newError;
	}
	
	
	/**
	 * 
	 * 
	 * Sets the error message for if a URL is malformed.
	 * 
	 * @param newError The new malformed URL error message.
	 * 
	 */
	public void setMalformedURLErrorMessage(String newError) {
		
		malformedURLErrorMessage = newError;
	}
	
	/**
	 * Sets the error message for if no URLs have been supplied.
	 * 
	 * @param newError The new un-supplied URL error message.
	 */
	public void setNoURLErrorMessage(String newError) {
		
		noURLErrorMessage = newError;
	}
	
	/**
	 * 
	 * Check if two consoles are equal; that is, they have the same messages.
	 * 
	 * @param rhs Another Console object.
	 * @return Whether or not the two consoles are equal.
	 * 
	 */
	@Override
	public boolean equals(Object rhs) {
		
		Console other = (Console)rhs;
		
		return usageMessage.equals(other.getUsageMessage()) &&
				directoryErrorMessage.equals(other.getDirectoryErrorMessage()) &&
				malformedURLErrorMessage.equals(other.getMalformedURLErrorMessage()) &&
				noURLErrorMessage.equals(other.getNoURLErrorMessage());
			
			
	}
	
	
	/**
	 * 
	 * Return a string that contains every message in the class.
	 * 
	 * @return The usage message, directory error message, 
	 * malformed URL error message, and un-supplied URL error message on one line.
	 */
	@Override
	public String toString() {
		
		return ""+usageMessage+" "+directoryErrorMessage+" "+malformedURLErrorMessage+" "+noURLErrorMessage;
	}
	
	/**
	 * 
	 * Produce a hashcode based on the messages.
	 * 
	 * @return Zero plus the usage message hashcode, directory error message hashcode, 
	 * malformed URL error message hashcode, and missing URL error hash code.
	 */
	@Override
	public int hashCode() {
		
		
		return 0+usageMessage.hashCode()+directoryErrorMessage.hashCode()+malformedURLErrorMessage.hashCode()+noURLErrorMessage.hashCode();
	}
	
	
}

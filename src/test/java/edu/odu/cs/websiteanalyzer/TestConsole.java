package edu.odu.cs.websiteanalyzer;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import org.hamcrest.core.IsNull;

import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;


/**
 * 
 * Test the Console class.
 * 
 * 
 * @author Evan Mulloy
 *
 */
public class TestConsole {
	
	Console defaultConsole, nonDefaultConsole, otherDefault, otherNonDefault;
	
	int oldDefaultHash, oldNonDefaultHash;
	
	String oldDefaultString, oldNonDefaultString, goodURL, badURL1, badURL2, badURL3;
	
	
	String[] usage, directoryError1, directoryError2, urlError1, urlError2, urlError3,
	loneDirectory, directoryAndURL, directoryAnd2URLs, emptyDirectory;

	@Before
	public void setUp() {
		
		defaultConsole = new Console();
		
		nonDefaultConsole = new Console("usage","directory error","malformed URL error","no URL error");
		
		otherDefault = new Console();
		
		otherNonDefault = new Console("usage","directory error","malformed URL error","no URL error");
		
		oldDefaultHash = defaultConsole.hashCode();
		
		oldNonDefaultHash = nonDefaultConsole.hashCode();
		
		oldDefaultString = defaultConsole.toString();
		
		oldNonDefaultString = nonDefaultConsole.toString();
		
		
		goodURL = "http://www.example.com";
		
		badURL1 = "http:/www.malformedurl.com";
				
		badURL2 = "htp://www.malformedurl.com";
		
		badURL3 = "http://.com";

		
		
		// Trigger usage message
		usage = new String[]{};
		
		// Trigger Directory error message
		directoryError1 = new String[]{"gradle/NonExistentDirectory"};
		
		directoryError2 = new String[]{"src/test/data/inaccessible",
										"http://www.testsite.net"};
		
		// Trigger malformed URL error message
		

		loneDirectory = new String[]{"gradle/wrapper"};
		
		urlError1 = new String[]{"gradle/wrapper", badURL1};
		
		urlError2 = new String[]{"gradle/wrapper", badURL2, "http://www.testsite.net"};
		
		urlError3 = new String[]{"gradle/wrapper", "http://www.testsite.net", badURL3};
		
		
		// Trigger empty directory message
		
		emptyDirectory = new String[] {"src/test/data/emptysite",
										"http://www.example.com"};
		
		
		// Valid input 1
		directoryAndURL = new String[]{"src/test/data/testsite/", "http://www.testsite.net"};
		
		// Valid input 1
		directoryAnd2URLs = new String[]{"src/test/data/testsite/","http://www.testsite.net","https://www.cs.testsite.net"};
	}
	
	@Test
	public void testMain() {
		
		// Inspired by this solution shared by Ernest Friedman-Hill: https://stackoverflow.com/a/8708357
		
		String usage = "HOW TO USE WEBSITEANALYSIS: Enter the path of the directory of your local copy as the first parameter."
				+ " You may specify a URL to be translated during analysis as additional parameters.";
		
		String directoryError = "ERROR: The path to the directory of your local copy is nonexistent or inaccessible.";
		
		String malformedURLError = "ERROR: One or more of the supplied URLs are malformed.";
		// missing URL not same as malformed URL.
		
		String noURLError = "ERROR: You failed to provide at least one URL.";
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteStream);


		PrintStream oldStream = System.out;
		System.setOut(printStream);
		
		Console.main(this.usage);
		
		assertThat(byteStream.toString(),containsString(usage));
		
		Console.main(directoryError1);
		
		assertThat(byteStream.toString(),containsString(directoryError));
		
		Console.main(directoryError2);
		
		assertThat(byteStream.toString(),containsString(directoryError));
		
		Console.main(urlError1);
		
		assertThat(byteStream.toString(),containsString(malformedURLError));
		
		Console.main(urlError2);
		
		assertThat(byteStream.toString(),containsString(malformedURLError));
		
		Console.main(urlError3);
		
		assertThat(byteStream.toString(),containsString(malformedURLError));
		
		Console.main(loneDirectory);
		
		assertThat(byteStream.toString(),containsString(noURLError));
		
		Console.main(emptyDirectory);
		
		assertThat(byteStream.toString(),containsString("Error: No files in specified directory"));
		
		
		String returned = "";
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd-hhmmss");
		
		String timeStamp = date.format(Calendar.getInstance().getTime());
		
		String textFilename = timeStamp+"-summary.txt";
		String excelFilename = timeStamp+"-summary.xlsx";
		String jsonFilename = timeStamp+"-summary.json";
		
		Console.main(directoryAnd2URLs);
		
		returned = byteStream.toString();
		
		
		assertThat(returned, containsString(excelFilename));
		//assertThat(returned, containsString(textFilename));
		//assertThat(returned, containsString(jsonFilename));
		
		
		
		System.out.flush();
		System.setOut(oldStream);
		
	}
	
	@Test
	public void testDefaultConstructor() {
		
		
		assertThat(defaultConsole.getUsageMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getDirectoryErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getMalformedURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getNoURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole, is(equalTo(defaultConsole)));
		
		assertThat(defaultConsole.validateURL(goodURL),is(true));
		
		assertThat(defaultConsole.validateURL(badURL1),is(false));
		
		assertThat(defaultConsole.validateURL(badURL2),is(false));
		
		assertThat(defaultConsole.validateURL(badURL3),is(false));
		
	}
	
	@Test
	public void testNonDefaultConstructor() {
		
		assertThat(nonDefaultConsole.getUsageMessage(), is(equalTo("usage")));
		
		assertThat(nonDefaultConsole.getDirectoryErrorMessage(), is(equalTo("directory error")));
		
		assertThat(nonDefaultConsole.getMalformedURLErrorMessage(), is(equalTo("malformed URL error")));
		
		assertThat(nonDefaultConsole.getNoURLErrorMessage(), is(equalTo("no URL error")));
		
		assertThat(nonDefaultConsole.hashCode(), not(equalTo(defaultConsole.hashCode())));
		
		assertThat(nonDefaultConsole.toString(), not(equalTo(defaultConsole.toString())));
		
		assertThat(nonDefaultConsole, not(equalTo(defaultConsole)));
		
		assertThat(nonDefaultConsole.toString(), containsString("usage"));
		
		assertThat(nonDefaultConsole.toString(), containsString("directory error"));
		
		assertThat(nonDefaultConsole.toString(), containsString("malformed URL error"));
		
		assertThat(nonDefaultConsole.validateURL(goodURL),is(true));
		
		assertThat(nonDefaultConsole.validateURL(badURL1),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL2),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL3),is(false));
		
	}

	/**
	 * 
	 * Simulate the returning of error messages, usage messages, and filenames
	 * that would/should be printed to the Console, when given String arguments.
	 * 
	 */
	@Test
	public void testConsoleOutput() {
		
		String returned = "";
		
		
		
		// usage message
		returned = nonDefaultConsole.getConsoleOutput(usage);
		
		assertThat(returned, is(equalTo(nonDefaultConsole.getUsageMessage())));
		
		// directory error message
		returned = nonDefaultConsole.getConsoleOutput(directoryError1);
		
		assertThat(returned, is(equalTo(nonDefaultConsole.getDirectoryErrorMessage())));
		
		
		returned = nonDefaultConsole.getConsoleOutput(directoryError2);
		
		assertThat(returned.toLowerCase(), containsString("error"));
		
		
		
		// URL error message
		
		returned = nonDefaultConsole.getConsoleOutput(loneDirectory);
		
		assertThat(returned, is(equalTo(nonDefaultConsole.getNoURLErrorMessage())));
		
		returned = nonDefaultConsole.getConsoleOutput(urlError1);
		
		assertThat(returned, is(equalTo(nonDefaultConsole.getMalformedURLErrorMessage())));
		
		returned = nonDefaultConsole.getConsoleOutput(urlError2);
		
		assertThat(returned, is(equalTo(nonDefaultConsole.getMalformedURLErrorMessage())));
		
		returned = nonDefaultConsole.getConsoleOutput(urlError3);
		
		assertThat(returned, is(equalTo(nonDefaultConsole.getMalformedURLErrorMessage())));
		
		// directory error message
		returned = nonDefaultConsole.getConsoleOutput(emptyDirectory);
		
		assertThat(returned, equalTo("Error: No files in specified directory"));
				
		assertThat(returned.toLowerCase(), containsString("error"));
		
		assertThat(returned.toLowerCase(), containsString("directory"));
		
		assertThat(returned, not(equalTo(nonDefaultConsole.getMalformedURLErrorMessage())));
		
		assertThat(returned, not(equalTo(nonDefaultConsole.getNoURLErrorMessage())));
		
		assertThat(returned, not(equalTo(nonDefaultConsole.getDirectoryErrorMessage())));
		
		assertThat(returned, not(equalTo(nonDefaultConsole.getUsageMessage())));
		
		
		
		// local copy and URL to translate
		returned = nonDefaultConsole.getConsoleOutput(directoryAndURL);
		
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd-hhmmss");
		
		String timeStamp = date.format(Calendar.getInstance().getTime());
		
		String textFilename = timeStamp+"-summary.txt";
		String excelFilename = timeStamp+"-summary.xlsx";
		String jsonFilename = timeStamp+"-summary.json";
		
		assertThat(returned, containsString(excelFilename));
		//assertThat(returned, containsString(textFilename));
		
		//assertThat(returned, containsString(jsonFilename));
		
		// local copy and URLs to translate
		returned = nonDefaultConsole.getConsoleOutput(directoryAnd2URLs);
		
		assertThat(returned, containsString(excelFilename));
		//assertThat(returned, containsString(textFilename));
		
		//assertThat(returned, containsString(jsonFilename));
	}
	
	
	@Test
	public void testSetUsageMessage() {
		
		// default
		
		defaultConsole.setUsageMessage("new usage");
		
		assertThat(defaultConsole.getUsageMessage(), is(equalTo("new usage")));
		
		assertThat(defaultConsole.getDirectoryErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getMalformedURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getNoURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.toString(), containsString("new usage"));
		
		assertThat(defaultConsole.toString(), not(equalTo(oldDefaultString)));
		
		assertThat(defaultConsole.hashCode(), not(equalTo(oldDefaultHash)));
		
		assertThat(defaultConsole, not(equalTo(otherDefault)));
		
		assertThat(defaultConsole.validateURL(goodURL),is(true));
		
		assertThat(defaultConsole.validateURL(badURL1),is(false));
		
		assertThat(defaultConsole.validateURL(badURL2),is(false));
		
		assertThat(defaultConsole.validateURL(badURL3),is(false));
		
		// non default
		
		nonDefaultConsole.setUsageMessage("new usage");
		
		assertThat(nonDefaultConsole.getUsageMessage(), is(equalTo("new usage")));
		
		assertThat(nonDefaultConsole.getDirectoryErrorMessage(), is(equalTo("directory error")));
		
		assertThat(nonDefaultConsole.getMalformedURLErrorMessage(), is(equalTo("malformed URL error")));
		
		assertThat(nonDefaultConsole.getNoURLErrorMessage(), is(equalTo("no URL error")));
		
		assertThat(nonDefaultConsole.toString(), containsString("directory error"));
		
		assertThat(nonDefaultConsole.toString(), containsString("new usage"));
		
		assertThat(nonDefaultConsole.toString(), containsString("malformed URL error"));
		
		assertThat(nonDefaultConsole.toString(), not(equalTo(oldNonDefaultString)));
		
		assertThat(nonDefaultConsole.hashCode(), not(equalTo(oldNonDefaultHash)));
		
		assertThat(nonDefaultConsole, not(equalTo(otherNonDefault)));
		
		assertThat(nonDefaultConsole.validateURL(goodURL),is(true));
		
		assertThat(nonDefaultConsole.validateURL(badURL1),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL2),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL3),is(false));
	}
	
	@Test
	public void testSetDirectoryErrorMessage() {
		
		// default
		
		defaultConsole.setDirectoryErrorMessage("new directory error");
		
		assertThat(defaultConsole.getUsageMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getDirectoryErrorMessage(), is(equalTo("new directory error")));
		
		assertThat(defaultConsole.getMalformedURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getNoURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.toString(), containsString("new directory error"));
		
		assertThat(defaultConsole.toString(), not(equalTo(oldDefaultString)));
		
		assertThat(defaultConsole.hashCode(), not(equalTo(oldDefaultHash)));
		
		assertThat(defaultConsole, not(equalTo(otherDefault)));
		
		assertThat(defaultConsole.validateURL(goodURL),is(true));
		
		assertThat(defaultConsole.validateURL(badURL1),is(false));
		
		assertThat(defaultConsole.validateURL(badURL2),is(false));
		
		assertThat(defaultConsole.validateURL(badURL3),is(false));
		
		// non default
		
		nonDefaultConsole.setDirectoryErrorMessage("new directory error");
		
		assertThat(nonDefaultConsole.getUsageMessage(), is(equalTo("usage")));
		
		assertThat(nonDefaultConsole.getDirectoryErrorMessage(), is(equalTo("new directory error")));
		
		assertThat(nonDefaultConsole.getMalformedURLErrorMessage(), is(equalTo("malformed URL error")));
		
		assertThat(nonDefaultConsole.getNoURLErrorMessage(), is(equalTo("no URL error")));
		
		assertThat(nonDefaultConsole.toString(), containsString("new directory error"));
		
		assertThat(nonDefaultConsole.toString(), containsString("usage"));
		
		assertThat(nonDefaultConsole.toString(), containsString("malformed URL error"));
		
		assertThat(nonDefaultConsole.toString(), not(equalTo(oldNonDefaultString)));
		
		assertThat(nonDefaultConsole.hashCode(), not(equalTo(oldNonDefaultHash)));
		
		assertThat(nonDefaultConsole, not(equalTo(otherNonDefault)));
		
		assertThat(nonDefaultConsole.validateURL(goodURL),is(true));
		
		assertThat(nonDefaultConsole.validateURL(badURL1),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL2),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL3),is(false));
	}
	
	@Test
	public void testSetMalformedURLErrorMessage() {
		
		// default
		
		defaultConsole.setMalformedURLErrorMessage("new url error");
		
		assertThat(defaultConsole.getUsageMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getDirectoryErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getMalformedURLErrorMessage(), is(equalTo("new url error")));
		
		assertThat(defaultConsole.getNoURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.toString(), containsString("new url error"));
		
		assertThat(defaultConsole.toString(), not(equalTo(oldDefaultString)));
		
		assertThat(defaultConsole.hashCode(), not(equalTo(oldDefaultHash)));
		
		assertThat(defaultConsole, not(equalTo(otherDefault)));
		
		assertThat(defaultConsole.validateURL(goodURL),is(true));
		
		assertThat(defaultConsole.validateURL(badURL1),is(false));
		
		assertThat(defaultConsole.validateURL(badURL2),is(false));
		
		assertThat(defaultConsole.validateURL(badURL3),is(false));
		
		// non default
		
		nonDefaultConsole.setMalformedURLErrorMessage("new url error");
		
		assertThat(nonDefaultConsole.getUsageMessage(), is(equalTo("usage")));
		
		assertThat(nonDefaultConsole.getDirectoryErrorMessage(), is(equalTo("directory error")));
		
		assertThat(nonDefaultConsole.getMalformedURLErrorMessage(), is(equalTo("new url error")));
		
		assertThat(nonDefaultConsole.getNoURLErrorMessage(), is(equalTo("no URL error")));
		
		assertThat(nonDefaultConsole.toString(), containsString("directory error"));
		
		assertThat(nonDefaultConsole.toString(), containsString("usage"));
		
		assertThat(nonDefaultConsole.toString(), containsString("new url error"));
		
		assertThat(nonDefaultConsole.toString(), not(equalTo(oldNonDefaultString)));
		
		assertThat(nonDefaultConsole.hashCode(), not(equalTo(oldNonDefaultHash)));
		
		assertThat(nonDefaultConsole, not(equalTo(otherNonDefault)));
		
		assertThat(nonDefaultConsole.validateURL(goodURL),is(true));
		
		assertThat(nonDefaultConsole.validateURL(badURL1),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL2),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL3),is(false));

	}
	
	@Test
	public void testSetNoURLErrorMessage() {
		
		// default
		
		defaultConsole.setNoURLErrorMessage("new url error");
		
		assertThat(defaultConsole.getUsageMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getDirectoryErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getMalformedURLErrorMessage(), is(equalTo("")));
		
		assertThat(defaultConsole.getNoURLErrorMessage(), is(equalTo("new url error")));
		
		assertThat(defaultConsole.toString(), containsString("new url error"));
		
		assertThat(defaultConsole.toString(), not(equalTo(oldDefaultString)));
		
		assertThat(defaultConsole.hashCode(), not(equalTo(oldDefaultHash)));
		
		assertThat(defaultConsole, not(equalTo(otherDefault)));
		
		assertThat(defaultConsole.validateURL(goodURL),is(true));
		
		assertThat(defaultConsole.validateURL(badURL1),is(false));
		
		assertThat(defaultConsole.validateURL(badURL2),is(false));
		
		assertThat(defaultConsole.validateURL(badURL3),is(false));
		
		// non default
		
		nonDefaultConsole.setNoURLErrorMessage("new url error");
		
		assertThat(nonDefaultConsole.getUsageMessage(), is(equalTo("usage")));
		
		assertThat(nonDefaultConsole.getDirectoryErrorMessage(), is(equalTo("directory error")));
		
		assertThat(nonDefaultConsole.getMalformedURLErrorMessage(), is(equalTo("malformed URL error")));
		
		assertThat(nonDefaultConsole.getNoURLErrorMessage(), is(equalTo("new url error")));
		
		assertThat(nonDefaultConsole.toString(), containsString("directory error"));
		
		assertThat(nonDefaultConsole.toString(), containsString("usage"));
		
		assertThat(nonDefaultConsole.toString(), containsString("new url error"));
		
		assertThat(nonDefaultConsole.toString(), not(equalTo(oldNonDefaultString)));
		
		assertThat(nonDefaultConsole.hashCode(), not(equalTo(oldNonDefaultHash)));
		
		assertThat(nonDefaultConsole, not(equalTo(otherNonDefault)));
		
		assertThat(nonDefaultConsole.validateURL(goodURL),is(true));
		
		assertThat(nonDefaultConsole.validateURL(badURL1),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL2),is(false));
		
		assertThat(nonDefaultConsole.validateURL(badURL3),is(false));

	}

}

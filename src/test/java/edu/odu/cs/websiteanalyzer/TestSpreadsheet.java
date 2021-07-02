package edu.odu.cs.websiteanalyzer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.xssf.usermodel.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSpreadsheet {
	
	
	Spreadsheet defaultSpreadsheet;
	Spreadsheet smallSpreadsheet;
	Spreadsheet badSpreadsheet;
	
	// It would be convenient to reference this.
	List<Row> rowData;
	
	// These are common names for HTML pages; at least, they are ones I use for my website.
	Row about, contact;

	@Before
	public void setUp() throws Exception {
		
		defaultSpreadsheet = new Spreadsheet();
		
		about = new Row("about.html",1,2,3,4,5,6);
		contact = new Row("contact.html",0,0,0,20,13,15);
		
		rowData = Arrays.asList(about,contact);
		
		smallSpreadsheet = new Spreadsheet(rowData);
		
		
		badSpreadsheet = new Spreadsheet(null);
	}

	@Test
	public void testDefaultConstructor() {
		
		assertTrue("Default rows are no empty!", defaultSpreadsheet.getRows().isEmpty());
		
		assertThat(defaultSpreadsheet.getRow("nada"), is(nullValue()));
		
		assertThat(defaultSpreadsheet, is(equalTo(defaultSpreadsheet)));
		
		assertThat(defaultSpreadsheet.hashCode(), is(equalTo(defaultSpreadsheet.hashCode())));
		
		assertThat(defaultSpreadsheet.toString(), is(equalTo(defaultSpreadsheet.toString())));
		
	}

	@Test
	public void testNonDefaultConstructor() {
		
		assertThat(smallSpreadsheet.getRow("about.html"), is(equalTo(about)));
		
		assertThat(smallSpreadsheet.getRow("contact.html"), is(equalTo(contact)));
		
		assertThat(smallSpreadsheet.getRows(), equalTo(rowData));
		
		assertThat(smallSpreadsheet, not(equalTo(defaultSpreadsheet)));
		
		assertThat(smallSpreadsheet, is(equalTo(smallSpreadsheet)));
		
		assertThat(smallSpreadsheet.hashCode(), not(equalTo(defaultSpreadsheet.hashCode())));
		
		assertThat(smallSpreadsheet.hashCode(), is(equalTo(smallSpreadsheet.hashCode())));
		
		assertThat(smallSpreadsheet.toString(), not(equalTo(defaultSpreadsheet.toString())));
		
		assertThat(smallSpreadsheet.toString(), containsString(about.toString()));
		
		assertThat(smallSpreadsheet.toString(), containsString(contact.toString()));
		
		
		// Test the bad/null spreadsheet
		
		assertThat(badSpreadsheet.hashCode(), is(0));
		
		assertThat(badSpreadsheet.getRows(), is(nullValue()));
		
		assertThat(badSpreadsheet.getRow("blah"), is(nullValue()));
		
	}

	@Test
	public void testAddRow() {
		
		// Set up "old" values
		
		int oldDefaultHash = defaultSpreadsheet.hashCode();
		
		int oldSmallHash = smallSpreadsheet.hashCode();
		
		String oldDefaultString = defaultSpreadsheet.toString();
		
		String oldSmallString = smallSpreadsheet.toString();
		
		Spreadsheet newSmall = new Spreadsheet(rowData);
		
		Spreadsheet newDefault = new Spreadsheet();
		
		
		
		// Test default
		
		defaultSpreadsheet.addRow("gallery.php",100,1,1,100,30,20);

		assertThat(defaultSpreadsheet, not(equalTo(newDefault)));
		
		assertThat(defaultSpreadsheet.hashCode(), not(equalTo(oldDefaultHash)));
		
		assertThat(defaultSpreadsheet.toString(), not(equalTo(oldDefaultString)));
		
		assertThat(defaultSpreadsheet.getRow("gallery.php"),
				is(equalTo(new Row("gallery.php",100,1,1,100,30,20))));
		
		assertThat(defaultSpreadsheet.getRows(),
				is(equalTo(Arrays.asList(new Row("gallery.php",100,1,1,100,30,20)))));
		

		
		// Test non-default
		
		smallSpreadsheet.addRow("gallery.php",100,1,1,100,30,20);
		
		assertThat(smallSpreadsheet, not(equalTo(newSmall)));
		
		assertThat(smallSpreadsheet.hashCode(), not(equalTo(oldSmallHash)));
		
		assertThat(smallSpreadsheet.toString(), not(equalTo(oldSmallString)));
		
		assertThat(smallSpreadsheet.getRow("gallery.php"),
				is(equalTo(new Row("gallery.php",100,1,1,100,30,20))));
		
		assertThat(smallSpreadsheet.getRows(),
				is(equalTo(Arrays.asList(about, contact, new Row("gallery.php",100,1,1,100,30,20)))));
	
		
		assertThat(badSpreadsheet.getRow("blah"), is(nullValue()));
		
		badSpreadsheet.addRow("blah",0,0,0,0,0,0);
		
		assertThat(badSpreadsheet.hashCode(), not(0));
		
		assertThat(badSpreadsheet.getRows(), not(nullValue()));
		
		assertThat(badSpreadsheet.getRow("blah"), not(nullValue()));
	}

	@Test
	public void testAddRowWithRow() {
		
		// In this test, we test the function that accepts a Row as a parameter.
		
		// A row of what a typical image gallery might look like
		Row gallery = new Row("gallery.php",100,1,1,100,30,20);
		
		// Set up "old" values

		int oldDefaultHash = defaultSpreadsheet.hashCode();
		
		int oldSmallHash = smallSpreadsheet.hashCode();
		
		String oldDefaultString = defaultSpreadsheet.toString();
		
		String oldSmallString = smallSpreadsheet.toString();
		
		Spreadsheet newSmall = new Spreadsheet(rowData);
		
		Spreadsheet newDefault = new Spreadsheet();
		
		// Test default
		
		defaultSpreadsheet.addRow(gallery);

		assertThat(defaultSpreadsheet, not(equalTo(newDefault)));
		
		assertThat(defaultSpreadsheet.hashCode(), not(equalTo(oldDefaultHash)));
		
		assertThat(defaultSpreadsheet.toString(), not(equalTo(oldDefaultString)));
		
		assertThat(defaultSpreadsheet.getRow("gallery.php"),
				is(equalTo(new Row("gallery.php",100,1,1,100,30,20))));
		
		assertThat(defaultSpreadsheet.getRows(),
				is(equalTo(Arrays.asList(new Row("gallery.php",100,1,1,100,30,20)))));
		

		
		
		// Test non-default
		
		smallSpreadsheet.addRow(gallery);
		
		assertThat(smallSpreadsheet, not(equalTo(newSmall)));
		
		assertThat(smallSpreadsheet.hashCode(), not(equalTo(oldSmallHash)));
		
		assertThat(smallSpreadsheet.toString(), not(equalTo(oldSmallString)));
		
		assertThat(smallSpreadsheet.getRow("gallery.php"),
				is(equalTo(new Row("gallery.php",100,1,1,100,30,20))));
		
		assertThat(smallSpreadsheet.getRows(),
				is(equalTo(Arrays.asList(about, contact, new Row("gallery.php",100,1,1,100,30,20)))));
	
	
		badSpreadsheet.addRow(gallery);
		
		assertThat(badSpreadsheet.hashCode(), not(is(0)));
		
		assertThat(badSpreadsheet.getRows(), not(is(nullValue())));
	}

	@Test
	public void testWriteFile() throws IOException {
		
		// First, ensure that the filename is in the format YYYYMMDD-HHMMSS-summary.xlsx
		
		// Load file based on time stamp
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
		
		// Write file with data
		String returned = smallSpreadsheet.writeFile("build/");
		
		assertThat(returned, containsString("-summary.xlsx"));
		
		String datePart = returned;
		
		datePart.replace("-summary.xlsx","");
		
		try {
			
			assertThat(dateFormat.parse(datePart), notNullValue());
			
		} catch (ParseException e1) {
			
			fail("The Excel file does not match the naming convention");
			
		}
		
		// Next, see if the loaded file contains the required data / format.
		
		FileInputStream loadedFile = null;
		
		
		try {
			loadedFile = new FileInputStream("build/"+returned);
			
			assertThat(loadedFile, notNullValue());
			
			XSSFWorkbook readBook  = new XSSFWorkbook(loadedFile);
			
			
			assertThat(readBook.getNumberOfSheets(), is(equalTo(1))); // there should be 1 sheet
			
			XSSFSheet readSheet = readBook.getSheetAt(0);
			
			assertThat(readSheet.getSheetName(),is(equalTo("summary")));
			
			// Get first row
			
			assertThat(readSheet.getRow(0).getCell(0).getStringCellValue(), is(equalTo("Page")));
			
			assertThat(readSheet.getRow(0).getCell(1).getStringCellValue(), is(equalTo("#Images")));
			
			assertThat(readSheet.getRow(0).getCell(2).getStringCellValue(), is(equalTo("#CSS")));
			
			assertThat(readSheet.getRow(0).getCell(3).getStringCellValue(), is(equalTo("#Scripts")));
			
			assertThat(readSheet.getRow(0).getCell(4).getStringCellValue(), is(equalTo("#Links (Intra-page)")));
			
			assertThat(readSheet.getRow(0).getCell(5).getStringCellValue(), is(equalTo("#Links (Internal)")));
			
			assertThat(readSheet.getRow(0).getCell(6).getStringCellValue(), is(equalTo("#Links (External)")));
			
			// Get row of first page
			
			assertThat(readSheet.getRow(1).getCell(0).getStringCellValue(), is(equalTo("about.html")));
			
			assertThat(readSheet.getRow(1).getCell(1).getNumericCellValue(), is(1.0));
			
			assertThat(readSheet.getRow(1).getCell(2).getNumericCellValue(), is(2.0));
			
			assertThat(readSheet.getRow(1).getCell(3).getNumericCellValue(), is(3.0));
			
			assertThat(readSheet.getRow(1).getCell(4).getNumericCellValue(), is(4.0));
			
			assertThat(readSheet.getRow(1).getCell(5).getNumericCellValue(), is(5.0));
			
			assertThat(readSheet.getRow(1).getCell(6).getNumericCellValue(), is(6.0));
			
			// get second page row
			
			assertThat(readSheet.getRow(2).getCell(0).getStringCellValue(), is(equalTo("contact.html")));
			
			assertThat(readSheet.getRow(2).getCell(1).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(2).getCell(2).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(2).getCell(3).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(2).getCell(4).getNumericCellValue(), is(20.0));
			
			assertThat(readSheet.getRow(2).getCell(5).getNumericCellValue(), is(13.0));
			
			assertThat(readSheet.getRow(2).getCell(6).getNumericCellValue(), is(15.0));
			
			readBook.close();
			
			loadedFile.close();
			
			
			// For the next step, lets add data, and ensure that the data exists in the file.
			
			smallSpreadsheet.addRow(new Row("gallery.php",100,1,1,100,30,20));
			
			smallSpreadsheet.addRow("forum.php",5,1,3,0,40,0);
			
			
			loadedFile = new FileInputStream("build/"+smallSpreadsheet.writeFile("build/"));
			
			assertThat(loadedFile, notNullValue());
			
			readBook  = new XSSFWorkbook(loadedFile);
			
			
			assertThat(readBook.getNumberOfSheets(), is(equalTo(1))); // there should be 1 sheet
			
			readSheet = readBook.getSheetAt(0);
			
			assertThat(readSheet.getSheetName(),is(equalTo("summary")));
			
			// Get first row
			
			assertThat(readSheet.getRow(0).getCell(0).getStringCellValue(), is(equalTo("Page")));
			
			assertThat(readSheet.getRow(0).getCell(1).getStringCellValue(), is(equalTo("#Images")));
			
			assertThat(readSheet.getRow(0).getCell(2).getStringCellValue(), is(equalTo("#CSS")));
			
			assertThat(readSheet.getRow(0).getCell(3).getStringCellValue(), is(equalTo("#Scripts")));
			
			assertThat(readSheet.getRow(0).getCell(4).getStringCellValue(), is(equalTo("#Links (Intra-page)")));
			
			assertThat(readSheet.getRow(0).getCell(5).getStringCellValue(), is(equalTo("#Links (Internal)")));
			
			assertThat(readSheet.getRow(0).getCell(6).getStringCellValue(), is(equalTo("#Links (External)")));
			
			// Get row of first page
			
			assertThat(readSheet.getRow(1).getCell(0).getStringCellValue(), is(equalTo("about.html")));
			
			assertThat(readSheet.getRow(1).getCell(1).getNumericCellValue(), is(1.0));
			
			assertThat(readSheet.getRow(1).getCell(2).getNumericCellValue(), is(2.0));
			
			assertThat(readSheet.getRow(1).getCell(3).getNumericCellValue(), is(3.0));
			
			assertThat(readSheet.getRow(1).getCell(4).getNumericCellValue(), is(4.0));
			
			assertThat(readSheet.getRow(1).getCell(5).getNumericCellValue(), is(5.0));
			
			assertThat(readSheet.getRow(1).getCell(6).getNumericCellValue(), is(6.0));
			
			// get second page row
			
			assertThat(readSheet.getRow(2).getCell(0).getStringCellValue(), is(equalTo("contact.html")));
			
			assertThat(readSheet.getRow(2).getCell(1).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(2).getCell(2).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(2).getCell(3).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(2).getCell(4).getNumericCellValue(), is(20.0));
			
			assertThat(readSheet.getRow(2).getCell(5).getNumericCellValue(), is(13.0));
			
			assertThat(readSheet.getRow(2).getCell(6).getNumericCellValue(), is(15.0));
			
			// get third page row
			
			assertThat(readSheet.getRow(3).getCell(0).getStringCellValue(), is(equalTo("gallery.php")));
			
			assertThat(readSheet.getRow(3).getCell(1).getNumericCellValue(), is(100.0));
			
			assertThat(readSheet.getRow(3).getCell(2).getNumericCellValue(), is(1.0));
			
			assertThat(readSheet.getRow(3).getCell(3).getNumericCellValue(), is(1.0));
			
			assertThat(readSheet.getRow(3).getCell(4).getNumericCellValue(), is(100.0));
			
			assertThat(readSheet.getRow(3).getCell(5).getNumericCellValue(), is(30.0));
			
			assertThat(readSheet.getRow(3).getCell(6).getNumericCellValue(), is(20.0));

			// 5,1,3,0,40,0
			
			// get fourth page row
			
			assertThat(readSheet.getRow(4).getCell(0).getStringCellValue(), is(equalTo("forum.php")));
			
			assertThat(readSheet.getRow(4).getCell(1).getNumericCellValue(), is(5.0));
			
			assertThat(readSheet.getRow(4).getCell(2).getNumericCellValue(), is(1.0));
			
			assertThat(readSheet.getRow(4).getCell(3).getNumericCellValue(), is(3.0));
			
			assertThat(readSheet.getRow(4).getCell(4).getNumericCellValue(), is(0.0));
			
			assertThat(readSheet.getRow(4).getCell(5).getNumericCellValue(), is(40.0));
			
			assertThat(readSheet.getRow(4).getCell(6).getNumericCellValue(), is(0.0));

			readBook.close();
			
			loadedFile.close();
			
			
			
		} catch (FileNotFoundException e) {
			
			fail("Could not find Excel file!");
			
			e.printStackTrace();
		} catch (IOException e) {
			
			fail("Could not read loaded Excel file!");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testClone() {
		
		// Default
		
		Spreadsheet newDefault = new Spreadsheet();
		
		Spreadsheet defaultClone = (Spreadsheet)newDefault.clone();
		
		assertThat(defaultClone, is(equalTo(defaultSpreadsheet)));
		
		assertThat(defaultClone.hashCode(), is(equalTo(defaultSpreadsheet.hashCode())));
		
		assertThat(defaultClone.toString(), is(equalTo(defaultSpreadsheet.toString())));
		
		assertThat(defaultClone.getRows(), is(equalTo(defaultSpreadsheet.getRows())));
		
		
		// Make sure nothing happened to that which was cloned.
		
		assertThat(newDefault, is(equalTo(defaultSpreadsheet)));
		
		assertThat(newDefault.hashCode(), is(equalTo(defaultSpreadsheet.hashCode())));
		
		assertThat(newDefault.toString(), is(equalTo(defaultSpreadsheet.toString())));
		
		assertThat(newDefault.getRows(), is(equalTo(defaultSpreadsheet.getRows())));
		
		
		// Non default
		
		Spreadsheet newSmall = new Spreadsheet(rowData);
		
		Spreadsheet smallClone = (Spreadsheet)newSmall.clone();
		
		assertThat(smallClone, is(equalTo(smallSpreadsheet)));
		
		assertThat(smallClone.hashCode(), is(equalTo(smallSpreadsheet.hashCode())));
		
		assertThat(smallClone.toString(), is(equalTo(smallSpreadsheet.toString())));
		
		assertThat(smallClone.getRows(), is(equalTo(rowData)));
		
		assertThat(smallClone.getRow("about.html"), is(equalTo(about)));
		
		assertThat(smallClone.getRow("contact.html"), is(equalTo(contact)));
		
		
		// Test that original is unchanged
		
		assertThat(newSmall, is(equalTo(smallSpreadsheet)));
		
		assertThat(newSmall.hashCode(), is(equalTo(smallSpreadsheet.hashCode())));
		
		assertThat(newSmall.toString(), is(equalTo(smallSpreadsheet.toString())));
		
		assertThat(newSmall.getRows(), is(equalTo(rowData)));
		
		assertThat(newSmall.getRow("about.html"), is(equalTo(about)));
		
		assertThat(newSmall.getRow("contact.html"), is(equalTo(contact)));
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		/*
		File folder = new File("build/");
		
		
		if(folder.listFiles() != null) {
			for(File file : folder.listFiles()) {
				
				if(file.getAbsolutePath().contains(".xlsx")) {
					
					file.delete();
				}
			}
			
		}*/
	}

}


package edu.odu.cs.websiteanalyzer;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.*;
import org.apache.poi.xssf.usermodel.*;


/**
 * 
 * @author Evan Mulloy
 * 
 * A spreadsheet that is output after the analysis is complete.
 *
 */
public class Spreadsheet implements Cloneable {
	
	/**
	 * The Rows within the spreadsheet.
	 * 
	 */
	private List<Row> rows;
	
	
	/**
	 * 
	 * The default constructor.
	 * 
	 */
	public Spreadsheet() {
		
		rows = new ArrayList<Row>();
	}
	
	/**
	 * 
	 * The non-default constructor.
	 * 
	 * @param rows A list of Rows.
	 * 
	 */
	public Spreadsheet(List<Row> rows) {
		
		if(rows == null) {
			
			this.rows = null;
			
		} else {
			
			this.rows = new ArrayList<Row>(rows);
		}
		
		
		
	}
	/**
	 * 
	 * 
	 * @param 
	 * 
	 */
	
	/**
	 * 
	 * Adds a Row to the Spreadsheet.
	 * 
	 * 
	 * @param page A page name.
	 * @param images Count of images.
	 * @param css Count of stylesheets. 
	 * @param scripts Count of scripts.
	 * @param intrapage Intrapage link counts. 
	 * @param intrasite Intrasite link counts. 
	 * @param external External link counts.
	 */
	public void addRow(String page, int images, int css, int scripts, int intrapage, int intrasite, int external) {
		
		if(rows == null) {
			rows = new ArrayList<Row>();
		}
		
		rows.add(new Row(page, images, css, scripts, intrapage, intrasite, external));
		
	}
	
	/**
	 * Adds a Row to the Spreadsheet.
	 * 
	 * 
	 * @param row A Row object.
	 * 
	 */
	public void addRow(Row row) { // row your boat
		
		if(rows == null) {
			rows = new ArrayList<Row>();
		}
		
		rows.add(row);
		
	}
	
	/**
	 * 
	 * Find the Row that corresponds to a given page name.
	 * 
	 * @param page A page name.
	 * 
	 * @return The corresponding Row or null if it doesn't exist.
	 */
	public Row getRow(String page) {
		
		if(rows != null) {
			
			for(int i = 0; i < rows.size(); i++)
			{
				
				if(rows.get(i).getPage().equals(page))
				{
					
					return rows.get(i);
				}
			}
		}
		
		return null;
		
	}
	
	/**
	 * Gets the Rows contained within the Spreadsheet.
	 * 
	 * @return rows The Rows in the Spreadsheet.
	 */
	public List<Row> getRows() {
		
		
		return rows;
	}
	
	
	/**
	 * Actually write the file to output, specifically, to the build directly.
	 * 
	 * The output file should use the format defined in user story #1025.
	 * 
	 * @param path The folder to write the file into.
	 * @return filename The written filename upon completion.
	 * @throws IOException An IO Exception is thrown if a spreadsheet cannot be written, for whatever reason.
	 * 
	 */
	public String writeFile(String path) throws IOException {
		
		FileOutputStream writtenFile;
		
		// Load file based on time stamp
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd-hhmmss");
		
		String timeStamp = date.format(Calendar.getInstance().getTime());
		
		String filename = ""+timeStamp+"-summary.xlsx";
	
		
		writtenFile = new FileOutputStream(path+filename);
		
		XSSFWorkbook writtenBook  = new XSSFWorkbook();
		
		writtenBook.createSheet("summary");
		
		XSSFSheet sheet = writtenBook.getSheetAt(0);
		
		XSSFRow row = sheet.createRow(0);
		
		XSSFCell cell = row.createCell(0);
		
		cell.setCellValue(new XSSFRichTextString("Page"));
		
		cell = row.createCell(1);
		
		cell.setCellValue(new XSSFRichTextString("#Images"));
		
		cell = row.createCell(2);
		
		cell.setCellValue(new XSSFRichTextString("#CSS"));
		
		cell = row.createCell(3);
		
		cell.setCellValue(new XSSFRichTextString("#Scripts"));
		
		cell = row.createCell(4);
		
		cell.setCellValue(new XSSFRichTextString("#Links (Intra-page)"));
		
		cell = row.createCell(5);
		
		cell.setCellValue(new XSSFRichTextString("#Links (Internal)"));
		
		cell = row.createCell(6);
		
		cell.setCellValue(new XSSFRichTextString("#Links (External)"));
		
		for(int i = 0; i < rows.size(); i++)
		{
			
			// Row 0 is taken, so start at the first row.
			row = sheet.createRow(i+1);
			
			cell = row.createCell(0);
			
			cell.setCellValue(new XSSFRichTextString(rows.get(i).getPage()));
			
			cell = row.createCell(1);
			
			cell.setCellValue(rows.get(i).getImageCount());
			
			cell = row.createCell(2);
			
			cell.setCellValue(rows.get(i).getCSSCount());
			
			cell = row.createCell(3);
			
			cell.setCellValue(rows.get(i).getScriptCount());
			
			cell = row.createCell(4);
			
			cell.setCellValue(rows.get(i).getIntrapageLinkCount());
			
			cell = row.createCell(5);
			
			cell.setCellValue(rows.get(i).getIntrasiteLinkCount());
			
			cell = row.createCell(6);
			
			cell.setCellValue(rows.get(i).getExternalLinkCount());
		
			
		}
		
		// auto size the columns so that text isn't cut off
		for(int i = 0; i < 7; i++) {
			
			sheet.autoSizeColumn(i);
		}
		
		writtenBook.write(writtenFile);
		
		writtenBook.close();
		
		writtenFile.close();
		
		
		return filename;
	}
	
	
	/**
	 * 
	 * Clones the spreadsheet.
	 * 
	 * @return A clone.
	 */
    @Override
    public Object clone()
    {
        return new Spreadsheet(rows);
    }

    /**
     * 
     * Compare spreadsheets based on their row lists,
     * would would in turn rely on comparing rows against rows.
     * 
     * @param rhs Another Spreadsheet;
     */
    @Override
    public boolean equals(Object rhs)
    {
    	Spreadsheet other = (Spreadsheet)rhs;
    	
    	
        return other.getRows().equals(rows);
    }


    /**
     * 
     * The hashCode for a Spreadsheet is the addition of the hashCode of each page in the rows.
     * 
     * 
     * @return spreadsheetHash A hashcode.
     */
    @Override
    public int hashCode()
    {
        int spreadsheetHash = 0;
        
        
        if(rows != null) {
	        for(int i = 0; i < rows.size(); i++) {
	        	
	        	spreadsheetHash += rows.get(i).getPage().hashCode();
	        }
        }
        
        return spreadsheetHash;
    }
    
    /**
     *  Returns the toString() result of every row placed onto one line,
     *  so the result would be something like:
     *  "about.html 2 1 0 17 28 73 contact.php 4 1 1 0 30 40 ....".
     *  
     *  @return spreadsheetString A representative string containing the representative string of every Row.
     */
    @Override
    public String toString()
    {
        String spreadsheetString = "";
        
        for(int i = 0; i < rows.size(); i++) {
        	
        	spreadsheetString = spreadsheetString.concat((rows.get(i).toString()+" "));
        }
        
        return spreadsheetString;
    }

}

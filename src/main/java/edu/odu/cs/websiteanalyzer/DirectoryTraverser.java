package edu.odu.cs.websiteanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import org.jsoup.Jsoup;

import edu.odu.cs.websiteanalyzer.Resource.*;

import edu.odu.cs.websiteanalyzer.*;

public class DirectoryTraverser {
	
	
	String path;
	
	String[] URLs;

	/**
	 * 
	 * Default constructor.
	 */
	public DirectoryTraverser() {
		
		path = "";
		
		URLs = new String[] {};
		
	}
	
	/**
	 * Non-default constructor.
	 * 
	 * 
	 * 
	 * @param directoryPath The path of the local copy.
	 * @param URLs The URLs to be used during HTML extraction.
	 */
	public DirectoryTraverser(String directoryPath, String[] URLs) {
		
		
		path = directoryPath;
		
		
		this.URLs = URLs;
	}
	
	/**
	 * Sets the path of the directory containing the website to be scanned.
	 * 
	 * @param directoryPath The path of the local copy.
	 */
	public void SetDirectoryPath(String directoryPath) {
		
		path = directoryPath;
	}
	
	/**
	 * 
	 * Sets the URLs to be used during analysis of the website.
	 * 
	 * @param URLs The URLs to be used during HTML extraction.
	 */
	public void SetURLs(String[] URLs) {
		
		this.URLs = URLs;
	}
	
	/**
	 * Gets the path of the directory containing the website to be scanned.
	 * 
	 * @return The path of the local copy.
	 */
	public String GetDirectoryPath() {
		
		return path;
	}
	
	/**
	 * 
	 * Gets the URLs to be used during analysis of the website.
	 * 
	 * @return The URLs to be used during HTML extraction.
	 */
	public String[] GetURLs() {
		
		return URLs;
	}
	
	
	/**
	 * Counts the number of valid HTML documents within the supplied directory.
	 * 
	 * @return count The number of documents within the supplied directory.
	 */
	public int GetDocumentCount() {
		
		
		int count = 0;
		
		// First, make sure the path isn't blank
		
		if(path == "") {
			
			return count;
		}
				
		// First, test to make sure the path isn't empty.
		
		File test = new File(path);
		
		if(test.listFiles() == null || test.listFiles().length <= 0) {
			
			return count;
		}
		
		
		List<File> folders = new ArrayList<File>();
		
		List<File> searched = new ArrayList<File>();
		
		
		folders.add(new File(path));
		
		boolean done = false;
		
		
		while(done == false) {
			
			File temp = null;
			
			for(File toSearch : folders) {
				
				if(!searched.contains(toSearch)) {
					
					temp = toSearch;
					break;
				}
			}
			
			if(temp == null) {
				
				break;
				
			} else if(temp.listFiles() != null) {
				
				for(File file : temp.listFiles()) {
					
					if(file.isDirectory()) {
						
						folders.add(file);
						
						
					} else if(HTMLExtractor.isValidPagePath(file.getPath())) {
						

						count++;
						
					}
				
				}
				
				searched.add(temp);
			}
		}
		
		return count;
	}
	
	/**
	 * Performs the analysis of the website; generates the output files and returns their names.
	 * 
	 * @return The filenames of the excel, json, and text files provided as output.
	 */
	public String[] AnalyzeWebsite() {
		
		String[] fileNames = new String[1];
		fileNames[0] = "";

		// First, do spreadsheet.
		
		List<Row> rows = new ArrayList<Row>();
		
		List<Resource> pages = new ArrayList<Resource>();
		
		File directory = new File(path);
		
		
		// GetPagesInFolder explores subfolders, so all we have to do is call it on the root.
		pages.addAll(GetPagesInFolder(directory));
					
		
		for(Resource p : pages) {
			
			// Here, we'll count the images, stylesheets, scripts etc. of each page for the Spreadsheet.
			int images = 0, css = 0, scripts = 0, intrapage = 0, internal = 0, external = 0;
			
			for(Resource r : p.getExtractedResources()) {
				
				switch(r.getType()) {
				case IMAGE:
					images++;
					break;
				case CSS:
					css++;
					break;
				case SCRIPT:
					scripts++;
					break;
				case ANCHOR:
				case PAGE_ANCHOR:
				case IMAGE_ANCHOR:
				case SCRIPT_ANCHOR:
				case CSS_ANCHOR:
				case AUDIO_ANCHOR:
				case VIDEO_ANCHOR:
				case ARCHIVE_ANCHOR:
				case UNCATEGORIZED_ANCHOR:
					
					switch(r.getClassification()) {
					case EXTERNAL:
						external++;
						break;
					case INTERNAL:
						internal++;
						break;
					case INTRAPAGE:
						intrapage++;
						break;
					default:
						break;
					}
					
					break;
				default:
					break;
				
				}
				
				
			}
			
			rows.add(new Row(p.getPath(),images,css,scripts,intrapage,internal,external));
			
			
			
		}
		
		
		if(rows.isEmpty() == false) {
		
			Spreadsheet sheet = new Spreadsheet(rows);
			
			try {
				fileNames[0] = sheet.writeFile("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return fileNames;
	}
	
	
	/**
	 * 
	 * Retrieves a list of resources representing the pages scanned within a given folder.
	 * This includes all the pages contained within sub-folders within this folder.
	 * 
	 * @param folder The folder to search.
	 * @return pages All the resources representing the pages within a folder.
	 */
	public List<Resource> GetPagesInFolder(File folder) {
		
		ArrayList<Resource> pages = new ArrayList<Resource>();
		
		
		// We'll use a stack to traverse the directory, searching for sub-directories.
		Stack<File> subfolders = new Stack<File>();
		subfolders.add(folder);
		
		while(subfolders.empty() == false) {
			
			// Get the current directory
			folder = subfolders.pop();
			
			if(folder.listFiles() != null) {
				for(File file : folder.listFiles()) {
					
					String tempPath = path.replace('\\','/');
					String workingDirectory = file.getAbsolutePath().replace('\\','/');
					String relativePath = workingDirectory.split(tempPath)[1];
					
					
					
					if(!file.isDirectory() && HTMLExtractor.isValidPagePath(tempPath+relativePath)) {
						
						
						
						HTMLExtractor extractor = new HTMLExtractor(tempPath+relativePath, path, URLs);
						
						
						pages.add(extractor.getPage());
						
						
					} else if(file.isDirectory()) {
						
						// Push sub-directories onto the stack for us to explore later
						subfolders.push(file);
					}
					
				}
			}
		}
		
		
		return pages;
	}
	

	/**
	 * 
	 * Clones the DirectoryTraverser.
	 * 
	 * @return A clone.
	 */
    @Override
    public Object clone()
    {
        return new DirectoryTraverser(path, URLs);
    }

    /**
     * 
     * Compare DirectoryTraversers.
     * 
     * @param rhs Another DirectoryTraverser;
     */
    @Override
    public boolean equals(Object rhs)
    {
    	DirectoryTraverser copy = (DirectoryTraverser)rhs;
    	
    	return (path.equals(copy.GetDirectoryPath()) &&
    			Arrays.equals(URLs,copy.GetURLs()));
    }


    /**
     * 
     * The hashCode function.
     * 
     * @return hashReturn A hashcode formed from the path and the URLs.
     */
    @Override
    public int hashCode()
    {
    	int hashReturn = 7*path.hashCode();
    	
    	for(String s : URLs) {
    		
    		hashReturn += s.hashCode();
    		
    	}
    	
    	
        return hashReturn;
    }
    
    /**
     *  Returns a String representative of the DirectoryTraverser.
     *  
     *  The string contains the directory path, followed by the URLs.
     *  
     *  @return The representative string.
     */
    @Override
    public String toString()
    {
    	String returnString = path;
    	
    	
    	for(String s : URLs) {
    		
    		returnString = returnString.concat(" "+s);
    		
    	}
    	
    	
        return returnString;
    }
	
}

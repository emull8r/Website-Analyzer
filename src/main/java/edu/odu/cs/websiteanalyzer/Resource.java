package edu.odu.cs.websiteanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * @author Evan Mulloy
 * 
 * A Resource represents a page, anchor, image, JavaScript file, CSS file, audio file,
 * video file, archive file, or uncategorized file found within a website.
 *
 */
public class Resource implements Cloneable, Iterable<Resource> {
	
	/** The Type of the Resource represented by the object.
	 * 
	 * @author Evan Mulloy
	 *
	 */
	public enum Type {PAGE, ANCHOR, IMAGE, SCRIPT, CSS, AUDIO, VIDEO, ARCHIVE, UNCATEGORIZED,
		PAGE_ANCHOR,IMAGE_ANCHOR, SCRIPT_ANCHOR, CSS_ANCHOR, AUDIO_ANCHOR, VIDEO_ANCHOR, ARCHIVE_ANCHOR, UNCATEGORIZED_ANCHOR};
	
	
	/**
	 *  A label denoting whether the Resource represented by the Resource object is
	 *  inside the website, outside the website, or is an intrapage anchor tag.
	 *  
	 *  @author Evan Mulloy
	 */
	public enum Classification {INTERNAL, EXTERNAL, INTRAPAGE};
	
	/**
	 * 
	 * The Type of the Resource.
	 */
	private Type type;
	
	/**
	 * 
	 * The Classification of the Resource.
	 */
	private Classification classification;
	
	/**
	 * 
	 * The file path of the actual file that the Resource object, as an object, represents in the system.
	 */
	private String path;
	
	/**
	 * 
	 * The size of the file represented by the Resource object.
	 */
	private double filesize;
	
	/**
	 * 
	 * 
	 * The list of pages that a given Resource (such as an image) is found on.
	 *
	 */
	private List<Resource> pages;
	
	/**
	 * 
	 * 
	 * The list of Resources that were extracted from a Page, specifically, a Resource marked with Type PAGE.
	 *
	 */
	private List<Resource> extractedResources;
	
	
	/**
	 * 
	 * The default constructor.
	 * 
	 * Type should be Page.
	 * 
	 * Classification should be Internal.
	 * 
	 * Path should be ""
	 * 
	 * Size should be 0.0
	 * 
	 * Pages should be an empty list of Resources.
	 * 
	 * ExtractedResources should be an empty list of Resources.
	 * 
	 */
	public Resource() {
		
		type = Type.PAGE;
		
		classification = Classification.INTERNAL;
		
		path = "";
		
		filesize = 0.0;
		
		pages = new ArrayList<Resource>();
		
		extractedResources = new ArrayList<Resource>();
	}
	
	/**
	 * Non-default constructor.
	 * 
	 * @param type The type of Resource the object is.
	 * @param classification The Classification (internal, external, or intrapage) of the object.
	 * @param path The path to the file, stripped of the site root.
	 * @param size The file size in MB.
	 * @param pages The pages this resource is on.
	 * @param extracted The Resources extracted from this page resource.
	 */
	public Resource(Type type, Classification classification,
			String path, double size, List<Resource> pages, List<Resource> extracted) {
		
		this.type = type;
		
		this.classification = classification;
		
		this.path = path;
		
		this.filesize = size;
		
		this.pages = pages;
		
		extractedResources = extracted;
		
	}
	
	/**
	 * 
	 * Gets the type of the Resource object.
	 * 
	 * @return The Type value.
	 */
	public Type getType() {
		
		
		return type;
	}
	
	/**
	 * Gets the Classification of the Resource object.
	 * 
	 * @return The Classification value.
	 */
	public Classification getClassification() {
		
		
		return classification;
	}
	
	/**
	 * 
	 * Gets the file size as a double.
	 * 
	 * @return The file size.
	 */
	public double getFileSize() {
		
		return filesize;
		
	}
	
	/**
	 * 
	 * Gets the path to the Resource, stripped of site root, as a String.
	 * 
	 * @return The path to the Resource.
	 */
	public String getPath() {
		
		
		return path;
	}
	
	/**
	 * Gets the list of pages that this Resource is found on.
	 * 
	 * @return The pages.
	 */
	public List<Resource> getPages() {
		
		if(pages == null) {
			
			return null;
			
		} else {
			
			return new ArrayList<Resource>(pages);
		}
	}
	
	/**
	 * Gets the list of Resources that a given page contains.
	 * 
	 * @return The Resources.
	 */
	public List<Resource> getExtractedResources() {
		
		return extractedResources;
	}
	
	/**
	 * Sets the Type (page, anchor, images, CSS, scripts, audio, video, archive, uncategorized) of the Resource object.
	 * 
	 * 
	 * @param newType The new Type for the Resource object.
	 */
	public void setType(Type newType) {
		
		type = newType;
	}
	
	/**
	 * Sets the Classification (internal, external, or intrapage) of the Resource object.
	 * 
	 * @param newClassification The new classification for the Resource object.
	 */
	public void setClassification(Classification newClassification) {
		
		classification = newClassification;
		
	}
	
	/**
	 * Sets the file size in MB, as a double, of the file represented by the Resouce object.
	 * 
	 * 
	 * @param size The new file size.
	 */
	public void setFileSize(double size) {
		
		filesize = size;
		
	}
	
	/**
	 * Sets the path to the file represented by the Resource object.
	 * 
	 * @param URI The path as a String.
	 */
	public void setPath(String URI) {
		
		path = URI;
		
	}
	
	/**
	 * Sets the list of pages--as a list of Resource objects--that the Resource appears on.
	 * 
	 * 
	 * @param pages The new list of pages.
	 */
	public void setPages(List<Resource> pages) {
		
		this.pages = pages;
	
	}
	
	/**
	 * Sets the list of Resources extracted from a Page, which is itself represented by a Resource object.
	 * 
	 * 
	 * @param resources The Resources extracted from a page.
	 */
	public void setExtractedResources(List<Resource> resources) {
		
		extractedResources = resources;
	}
	
	/**
	 * 
	 * Duplicates a given Resource object.
	 * 
	 * @return A copy of the Resource object.
	 */
	@Override
    public Object clone()
    {
		
		return new Resource(type,classification,path,filesize,pages,extractedResources);
	
    }
	/**
	 * Checks two Resources to see if they are equal.
	 * Every value in the right-hand Resource object will be compared with the value of the current object.
	 * If all the values are the same, the Resource objects are the same.
	 * 
	 * @param rhs Another Resource.
	 */
	@Override
    public boolean equals(Object rhs)
    {
		Resource other = (Resource)rhs;
		
		//System.out.println(other.getExtractedResources());
		//System.out.println("versus");
		//System.out.println(getExtractedResources());
		
		
		return other.getType().equals(type) &&
				other.getClassification().equals(classification) &&
				other.getPath().equals(path) &&
				other.getExtractedResources().equals(extractedResources) &&
				other.getPages().equals(pages) &&
				other.getFileSize() == filesize;
		// also do deep comparisons of the lists.
    }
	
	/**
	 * 
	 * Returns a hashCode representative of a Resource object.
	 * Each hashCode would be unique to each Resource object based on the member values.
	 * 
	 * @return hash The HashCode of the Resource object.
	 */
	@Override
    public int hashCode()
    {
		int hash = type.name().hashCode()+
				classification.name().hashCode()+
				path.hashCode()+
				(int)Math.round(100*filesize);
		
		if(pages != null) {
			
			hash += pages.hashCode();
		}
		
		if(extractedResources != null) {
			
			hash += extractedResources.hashCode();
		}
		
		
		return hash;
    }
	
	/**
	 * 
	 * Returns a string representative of a Resource object.
	 * Every value of the object is represented using the following format:
	 * "[type] [classification] [path] [filesize] [page path 1, page path 2, ...] [extracted resource path 1, extracted resource path 2, ...]"
	 * For example:
	 * "image external /directory/gallery/image.png 2.30 /directory/gallery/page1.html /directory/gallery/page2.html"
	 * Everything aside from the paths should be in lower case. File size is rounded to 2 decimal places.
	 * 
	 * @return string The representative string.
	 */
	@Override
    public String toString()
    {
		String string = ""+type.name().toLowerCase()+" "+classification.name().toLowerCase()+" "+path+" "+String.format("%.2f", filesize);
		
		if(pages != null) {
				
			for(int i = 0; i < pages.size(); i++) {
				
				string = string.concat(" "+pages.get(i).getPath());
				
			}
		}
		
		if(extractedResources != null) {
			
			for(int i = 0; i < extractedResources.size(); i++) {
				
				string = string.concat(" "+extractedResources.get(i).getPath());
				
			}
		}
		
		
		return string;
    }

	
	/**
	 * 
	 * Iterates over the list of pages that the Resource is on.
	 * 
	 * 
	 * @return An iterator to the current page.
	 */
	public Iterator<Resource> iterator() {
		return new Iterator<Resource>() {

        	private int currentPageIndex = 0; // used for iterator

            public boolean hasNext() {
                return currentPageIndex < pages.size();
            }

            public Resource next() {
                if (!hasNext()) {
                	
                	throw new NoSuchElementException();
                }

                return (Resource)pages.get(currentPageIndex++).clone();
            }
        };
	}

}

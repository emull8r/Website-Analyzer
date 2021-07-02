package edu.odu.cs.websiteanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.odu.cs.websiteanalyzer.Resource.Type.*;
import edu.odu.cs.websiteanalyzer.Resource.Classification;
import edu.odu.cs.websiteanalyzer.Resource.Classification.*;
import edu.odu.cs.websiteanalyzer.Resource.Type;

/**
 * 
 * A class that extracts tags from the page supplied by a given path, and builds a list of Resources.
 * 
 * 
 * @author Evan Mulloy
 *
 */


public class HTMLExtractor implements Cloneable {
	
	/**
	 * The file path to a given page, represented as a String.
	 */
	private String pagePath;
	
	/**
	 * The file path of the directory of the local copy.
	 */
	private String localCopyPath;
	
	/**
	 * One or more URLs of the site root that are to be translated during the extraction process.
	 * 
	 */
	private String[] URLs;
	
	/**
	 * 
	 * Default constructor.
	 * 
	 * 
	 * The path and local copy path would be "".
	 * 
	 * The URLs would be empty.
	 * 
	 */
	public HTMLExtractor() {
		
		pagePath = "";
		localCopyPath = "";
		URLs = new String[] {};
		
		
	}
	
	/**
	 * 
	 * 
	 * Non-default constructor that accepts a path to a page as a parameter.
	 * HTML tags would be extracted from the page retrieved using the supplied page path.
	 * 
	 * @param page A path to a file in the local copy.
	 * @param localcopy The path of the local copy.
	 * @param URLs The URL(s) required for translation.
	 */
	public HTMLExtractor(String page, String localcopy, String[] URLs) {
		
		if(localcopy.equals("") == false && !localcopy.endsWith("/")) {
			
			localcopy = localcopy.concat("/");
		}
		
		pagePath = page;
		localCopyPath= localcopy;
		this.URLs = URLs;
		
		
	}
	
	/**
	 * Sets the Path to the local copy.
	 * HTML tags would be extracted from the page retrieved using the path.
	 * 
	 * @param path A path to a file in the local copy.
	 */
	public void setPagePath(String path) {
		
		
		pagePath = path;
		
	}
	
	/**
	 * Sets the path of the local copy of the HTML Extractor, which would be used to
	 * make the paths of Resource objects absolute and to find file sizes.
	 * 
	 * 
	 * @param path The root of a site to be removed from paths.
	 */
	public void setLocalCopyPath(String path) {
		
		if(path.equals("") == false && !path.endsWith("/")) {
			
			path = path.concat("/");
		}
		
		localCopyPath = path;
	}
	
	/**
	 * Sets the URLs of the HTML Extractor that should be stripped from the paths of static HTML content.
	 * 
	 * @param URLs The URLs to be used to make paths reflect the local directory structure.
	 */
	public void setURLs(String[] URLs) {
		
		this.URLs = URLs;
	}
	
	/**
	 * Returns the path to the file in the local copy.
	 * 
	 * @return The path.
	 */
	public String getPagePath() {
		
		
		return pagePath;
	}
	
	/**
	 * Returns the path to the local copy as defined for this HTML Extractor.
	 * 
	 * 
	 * @return The set path to the local copy.
	 */
	public String getLocalCopyPath() {
		
		return localCopyPath;
	}
	
	/**
	 * Returns the URLs that are stripped from the source paths of files embedded in HTML documents.
	 * 
	 * @return urls The URLs used for path translation.
	 */
	public String[] getURLs() {
		
		String[] urls;
		
		if(URLs == null) {
			
			urls = null;
		} else {
			
			urls = URLs.clone();
		}
		
		return urls;
	}
	
	/**
	 * Checks to see if a supplied path is valid.
	 * A path is valid if it is possible to use it to load an HTML document.
	 * 
	 * @param path The path to be input and checked for validity.
	 * @return A boolean indicating whether or not the path is valid.
	 */
	public static boolean isValidPagePath(String path) {
		
		
		try {
			
			if(path.equals(""))
				return false;
			
			if(!(path.contains(".html") || path.contains(".cgi") || path.contains(".php")))
				return false;
			
			File input = new File(path);
			
			if(!input.exists())
				return false;
			
			@SuppressWarnings("unused")
			Document doc = Jsoup.parse(input,null);
			
		} catch (IOException e) {
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Converts the "src" or "href" attribute from static HTML content into a full path (without site root)
	 * that leads to the respective file within the local copy.
	 * 
	 * If the "src" contains an external URL, it will return the external URL.
	 * 
	 * If the "src" contains a supplied website URL, this website URL will be removed.
	 * 
	 * If the "src" contains a relative path, the path will be made not-relative.
	 * 
	 * 
	 * @param source The "src" or "href" attribute from some HTML tags.
	 * @return A path within the local copy or an external URL.
	 */
	public String sourceToPath(String source) {
		
		
		/* Check for familiar URLs */
		
		for(String URL : URLs) {
			
			if(URL.endsWith("/")) {
				
				source = source.replace(URL,"");
				
			} else {

				source = source.replace(URL+'/',"");
				
			}
		}
		
		
		
		if(Console.validateURL(source)) {
			
			return source;
			
		}
		
		// Now, to handle relative paths
		
		if(source.startsWith("./")) {
			
			String tempSource = "";
			
			String[] pageParts = pagePath.split("/");
			
			pageParts = Arrays.copyOf(pageParts, pageParts.length-1);
			
			for(String substring : pageParts) {
				
				tempSource = tempSource.concat(substring+"/");
			}
			
			source = source.replace("./","");
			
			tempSource = tempSource.concat(source);
			
			source = tempSource;
			
		} else if(source.startsWith("../")) {
			
			
			String tempSource = "";
			
			String[] pageParts = pagePath.split("/");
			
			pageParts = Arrays.copyOf(pageParts, pageParts.length-1);
			
			
			String[] sourceParts = source.split("/");
			
			
			
			for(String substring : sourceParts) {
				
				if(substring.contains("..")) {
					
					if(pageParts.length > 0) {
						pageParts = Arrays.copyOf(pageParts, pageParts.length-1);
					}
					
				}
				
			}
			
			for(String substring : pageParts) {
				
				tempSource = tempSource.concat(substring+"/");
				
			}
			
			for(int i = 0; i < sourceParts.length; i++) {
				
				if(sourceParts[i].contains("..") == false) {
				
					tempSource = tempSource.concat(sourceParts[i]);
					
					if(i != sourceParts.length-1) {
						
						tempSource = tempSource.concat("/");
					}
				}
			}
			
			source = tempSource;
			
			
		}
		
		source = source.replace(localCopyPath,"");
		
		return source;
	}
	
	/**
	 * Determines whether or not the internal path to a page leads to a 404 error page.
	 * 
	 * Essentially, it would determine if a given page is a 404 page by inspecting the Title tags.
	 * 
	 * It would also determine if a file or directory doesn't exist, which would also lead to a 404 page.
	 * 
	 * @param path The path of the file to be checked to see if it leads to a 404 page.
	 * @return A boolean indicating whether or not the path would lead to a 404 error page.
	 */
	public boolean is404(String path) {
		
		File input = new File(path);
		
		if(!input.exists()) {
			
			// If the file doesn't exist, return true.
			return true;
		}
		
		
		try {
			Document doc = Jsoup.parse(input, null);
			
			Elements tags = doc.select("title");
			
			for(Element tag : tags) {
				
				
				// If the title contains 404, return true.
				if(tag.text().contains("404")) {
					
					return true;
				}
			}
			
		} catch (IOException e) {
			
			// If the file exists, but can't be parsed, it may be some other file like an image or a directory.

			return false;
		}
		
		// If the document can't be parsed, but isn't 404, return false
		return false;
	}
	
	/**
	 * 
	 * The function getPage() returns a Resource object that represents an Internal Page.
	 * The extractedResources of this object is set to be the result of calling getResources().
	 * Basically, this function returns a Resource that represents the entire page and all Resources extracted.
	 * 
	 * Not to be confused with getPagePath(), which returns the file path of the page.
	 * 
	 * This will return null if the page path is "", the page can't be processed, 
	 * the local path is "", or the URLs are incomplete or null.
	 * 
	 * @return The Resource object that represents the page and all Resources extracted from the page.
	 */
	public Resource getPage() {
		
		
		if(pagePath.equals("") || 
			!isValidPagePath(pagePath) || 
			localCopyPath.equals("") || 
			URLs == null || 
			URLs.length == 0) {
			
			
			return null;
		}
		
		List<Resource> emptyList = new ArrayList<Resource>();
		
		
		
		List<Resource> resources = getResources();
		
		
		Resource thePage = new Resource(Type.PAGE,
				Classification.INTERNAL,
				pagePath.replace(localCopyPath,""),
				((double)new File(pagePath).length() / (1024 * 1024)),
				emptyList,resources);
		
		
		return thePage;
				
		
	}
	
	/**
	 * Returns a list of all Resource objects that represent every Resource (image, CSS, script, etc.)
	 * embedded into a page via HTML tags.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * Ideally, it should return a list comprised of the contents of
	 * the lists getScripts, getImages, getIntrapageAnchors, etc., all return.
	 * 
	 * @return The list of Resource objects.
	 */
	public List<Resource> getResources() {
		
		if(!isValidPagePath(pagePath) || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
		
			List<Resource> resources = new ArrayList<Resource>();
			
			
			resources.addAll(getImages());
			
			resources.addAll(getCSS());
			
			resources.addAll(getScripts());
			
			resources.addAll(getIntrapageAnchors());
			
			// Get HTML document
			
			
			// iterate over get Internal and External anchors
			
			List<Resource> anchors = getInternalAnchors();
			anchors.addAll(getExternalAnchors());
			
			for(Resource r : anchors) {
				
				String path = r.getPath().toLowerCase();
				
				
				if(path.endsWith(".png") ||
						path.endsWith(".jpg") ||
						path.endsWith(".jpeg") ||
						path.endsWith(".gif") ||
						path.endsWith(".bmp") ||
						path.endsWith(".svg")) {
						
						r.setType(Type.IMAGE_ANCHOR);
					
				} else if(path.endsWith(".m4a") ||
						path.endsWith(".mka") ||
						path.endsWith(".ogg") ||
						path.endsWith(".mp3") ||
						path.endsWith(".avc") ||
						path.endsWith(".vqf")||
						path.endsWith(".aac")) {
					
						r.setType(Type.AUDIO_ANCHOR);
					
				} else if(path.endsWith(".mp4") ||
						path.endsWith(".mkv")) {
					
						r.setType(Type.VIDEO_ANCHOR);
					
				} else if(path.endsWith(".css")) {
					
						r.setType(Type.CSS_ANCHOR);
					
				} else if(path.endsWith(".js")) {
					
						r.setType(Type.SCRIPT_ANCHOR);
					
				} else if(path.endsWith(".zip") ||
						path.endsWith(".tar.gz") ||
						path.endsWith(".tar") ||
						path.endsWith(".7z")) {
					

						r.setType(Type.ARCHIVE_ANCHOR);
					
					
				} else if(path.endsWith(".html") ||
						path.endsWith(".htm") ||
						path.endsWith(".php") ||
						path.endsWith(".cgi") ||
						path.endsWith(".page")) {
					
					
						r.setType(Type.PAGE_ANCHOR);
					
					
				} else {
					
					r.setType(Type.UNCATEGORIZED_ANCHOR);
					
				}
				
				
				
				resources.add(r);
				
				
			}
			
			
			return resources;
		}
	}
	
	/**
	 * Returns the list of Script Resources extracted from the page.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any such resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * 
	 * 
	 * @return A list of Resource objects with the Type "Script".
	 */
	public List<Resource> getScripts() {
		
		if(pagePath.equals("") || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
			
			File input = new File(pagePath);
			
			List<Resource> scripts = new ArrayList<Resource>();
			
			List<Resource> pages = new ArrayList<Resource>();
			
			pages.add(new Resource(Type.PAGE,
					Classification.INTERNAL,
					pagePath.replace(localCopyPath,""),
					((double)input.length() / (1024 * 1024)),
					new ArrayList<Resource>(),new ArrayList<Resource>()));
			
			
			try {
				Document doc = Jsoup.parse(input, null);
			
				Elements tags = doc.select("script");
				
				for(Element tag : tags) {
					
					if(tag.attr("src").equals("") == false) {
						
						Resource script = new Resource();
						
						script.setType(Type.SCRIPT);
						
						script.setPages(pages);
						
						script.setPath(sourceToPath(tag.attr("src")));
						
						if(Console.validateURL(script.getPath())) {
							
							script.setClassification(Classification.EXTERNAL);
							
							script.setFileSize(0.00);
							
						} else {
							
							script.setClassification(Classification.INTERNAL);
							
							File loadedResource = new File(localCopyPath+script.getPath());
							
							script.setFileSize(((double)loadedResource.length() / (1024 * 1024)));
						}
						
						if(!scripts.contains(script)) {
						
							scripts.add(script);
						}
					}
				}
				
				
			} catch (IOException e) {
				
				return null;
				
				
			}
			
			return scripts;
		}
	}
	
	/**
	 * Returns the list of CSS Resources extracted from the page.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any such resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * 
	 * @return A list of Resource objects with the Type "CSS".
	 */
	public List<Resource> getCSS() {
		
		
		if(pagePath.equals("") || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
			
			File input = new File(pagePath);
			
			List<Resource> stylesheets = new ArrayList<Resource>();
			
			List<Resource> pages = new ArrayList<Resource>();
			
			pages.add(new Resource(Type.PAGE,
					Classification.INTERNAL,
					pagePath.replace(localCopyPath,""),
					((double)input.length() / (1024 * 1024)),
					new ArrayList<Resource>(),new ArrayList<Resource>()));
			
			
			try {
				Document doc = Jsoup.parse(input, null);
			
				Elements tags = doc.select("link");
				
				
				for(Element tag : tags) {
					
					// Not all link tags may contain stylesheets, so check for this.
					
					if(tag.attr("rel").contains("stylesheet") &&
					   tag.attr("href").equals("") == false) {
					
						Resource css = new Resource();
						
						css.setType(Type.CSS);
						
						css.setPages(pages);
						
						css.setPath(sourceToPath(tag.attr("href")));
						
						if(Console.validateURL(css.getPath())) {
							
							css.setClassification(Classification.EXTERNAL);
							
							css.setFileSize(0.00);
							
						} else {
							
							css.setClassification(Classification.INTERNAL);
							
							File loadedResource = new File(localCopyPath+css.getPath());
							
							css.setFileSize(((double)loadedResource.length() / (1024 * 1024)));
						}
						
						if(!stylesheets.contains(css)) {
						
							stylesheets.add(css);
						}
					}
				}
				
				
			} catch (IOException e) {
				
				return null;
				
				
			}
			
			return stylesheets;
		}
	}
	
	/**
	 * Returns the list of Image Resources extracted from the page.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any such resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * 
	 * @return A list of Resource objects with the Type "Image".
	 */
	public List<Resource> getImages() {
		
		if(pagePath.equals("") || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
			
			File input = new File(pagePath);
			
			List<Resource> images = new ArrayList<Resource>();
			
			List<Resource> pages = new ArrayList<Resource>();
			
			pages.add(new Resource(Type.PAGE,
					Classification.INTERNAL,
					pagePath.replace(localCopyPath,""),
					((double)input.length() / (1024 * 1024)),
					new ArrayList<Resource>(),new ArrayList<Resource>()));
			
			
			try {
				Document doc = Jsoup.parse(input, null);
			
				Elements pics = doc.select("img");
				
				for(Element pic : pics) {
					
					if(pic.attr("src").equals("") == false) {
						
						
						Resource image = new Resource();
						
						image.setType(Type.IMAGE);
						
						image.setPages(pages);
						
						image.setPath(sourceToPath(pic.attr("src")));
						
						if(Console.validateURL(image.getPath())) {
							
							image.setClassification(Classification.EXTERNAL);
							
							image.setFileSize(0.00);
							
						} else {
							
							image.setClassification(Classification.INTERNAL);
							
							File loadedResource = new File(localCopyPath+image.getPath());
							
							image.setFileSize(((double)loadedResource.length() / (1024 * 1024)));
						}
						
						if(!images.contains(image)) {
						
							images.add(image);
						}
					}
				}
				
				
			} catch (IOException e) {
				
				return null;
				
				
			}
			
			return images;
		}
	}
	
	/**
	 * Returns the list of Internal links extracted from the page.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any such resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * 
	 * @return A list of Resource objects with the Type "Anchor" and Classification "Internal".
	 */
	public List<Resource> getInternalAnchors() {
		
		if(pagePath.equals("") || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
			
			File input = new File(pagePath);
			
			List<Resource> anchors = new ArrayList<Resource>();
			
			List<Resource> pages = new ArrayList<Resource>();
			
			pages.add(new Resource(Type.PAGE,
					Classification.INTERNAL,
					pagePath.replace(localCopyPath,""),
					((double)input.length() / (1024 * 1024)),
					new ArrayList<Resource>(),new ArrayList<Resource>()));
			
			
			try {
				Document doc = Jsoup.parse(input, null);
			
				Elements tags = doc.select("a");
				
				for(Element tag : tags) {
					
					if(tag.attr("href").equals("") == false) {
					
						Resource anchor = new Resource();
						
						
						anchor.setPath(sourceToPath(tag.attr("href")));
						
						
						if(Console.validateURL(anchor.getPath()) == false){
							
							String[] pageSubstrings = pagePath.split("/");
							
							
							/* An intrapage link may include the current page and/or begin with #
							 * 
							 * Examples are "#gameplay", "./page.html#gameplay", and "./page.html"
							 * if "page.html" is the current page.
							 * 
							 * Do a check to make sure the link isn't intrapage.
							 * 
							 * Also do a check for 404 content.
							 */
							if(anchor.getPath().contains(pageSubstrings[pageSubstrings.length-1]) == false &&
								anchor.getPath().startsWith("#") == false &&
								is404(localCopyPath+anchor.getPath()) == false) {
								
								
								anchor.setClassification(Classification.INTERNAL);
								
								anchor.setType(Type.ANCHOR);
								
								anchor.setPages(pages);
								
								anchor.setFileSize(0.00);
								
								if(!anchors.contains(anchor)) {
									
									anchors.add(anchor);
								}
							}
						}
					}
				}
				
			} catch (IOException e) {
				
				return null;
			}
			
			return anchors;
		}
	}
	
	/**
	 * Returns the list of External links extracted from the page.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any such resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * 
	 * @return A list of Resource objects with the Type "Anchor" and Classification "External".
	 */
	public List<Resource> getExternalAnchors() {
		
		
		if(pagePath.equals("") || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
			
			File input = new File(pagePath);
			
			List<Resource> anchors = new ArrayList<Resource>();
			
			List<Resource> pages = new ArrayList<Resource>();
			
			pages.add(new Resource(Type.PAGE,
					Classification.INTERNAL,
					pagePath.replace(localCopyPath,""),
					((double)input.length() / (1024 * 1024)),
					new ArrayList<Resource>(),new ArrayList<Resource>()));
			
			
			try {
				Document doc = Jsoup.parse(input, null);
			
				Elements tags = doc.select("a");
				
				for(Element tag : tags) {
					
					if(tag.attr("href").equals("") == false) {
					
						
						Resource anchor = new Resource();
						
						
						
						anchor.setPath(sourceToPath(tag.attr("href")));
						
						
						
						if(Console.validateURL(anchor.getPath())) {
							
							anchor.setClassification(Classification.EXTERNAL);
							
							anchor.setType(Type.ANCHOR);
							
							anchor.setPages(pages);
							
							anchor.setFileSize(0.00);
							
							if(!anchors.contains(anchor)) {
								
								anchors.add(anchor);
							}
						}
						
					}
				
				}
			} catch (IOException e) {
				
				return null;
				
				
			}
			
			return anchors;
		}
	}
	
	/**
	 * Returns the list of Intra-page links extracted from the page.
	 * 
	 * If the path is invalid, there are no URLs, or there aren't any such resources to be extracted, 
	 * either a null value or an empty list will be returned.
	 * 
	 * 
	 * @return A list of Resource objects with the Type "Anchor" and Classification "Intrapage".
	 */
	public List<Resource> getIntrapageAnchors() {
		
		
		if(pagePath.equals("") || URLs == null || URLs.length == 0  || localCopyPath.equals("")) {
			
			return null;
			
		} else {
			
			File input = new File(pagePath);
			
			List<Resource> anchors = new ArrayList<Resource>();
			
			List<Resource> pages = new ArrayList<Resource>();
			
			pages.add(new Resource(Type.PAGE,
					Classification.INTERNAL,
					pagePath.replace(localCopyPath,""),
					((double)input.length() / (1024 * 1024)),
					new ArrayList<Resource>(),new ArrayList<Resource>()));
			
			
			try {
				Document doc = Jsoup.parse(input, null);
			
				Elements tags = doc.select("a");
				
				for(Element tag : tags) {
					
					if(tag.attr("href").equals("") == false) {
					
						
						Resource anchor = new Resource();
						
						anchor.setPath(sourceToPath(tag.attr("href")));
						
						anchor.setClassification(Classification.INTRAPAGE);
						
						anchor.setType(Type.ANCHOR);
						
						anchor.setPages(pages);
						
						anchor.setFileSize(0.00);
						
						if(Console.validateURL(anchor.getPath()) == false) {
							
							
							String[] pageSubstrings = pagePath.split("/");
							
							
							/* An intrapage link may include the current page and/or begin with #
							 * 
							 * Examples are "#gameplay", "./page.html#gameplay", and "./page.html"
							 * if "page.html" is the current page.
							 */
							if(anchor.getPath().contains(pageSubstrings[pageSubstrings.length-1]) ||
								anchor.getPath().startsWith("#")) {
								
								String tempPath = pagePath;
							
								if(anchor.getPath().contains("#")) {
									
									String[] halves = anchor.getPath().split("#");
									
									tempPath = tempPath.concat("#"+halves[halves.length-1]);
									
								}
								
								tempPath = tempPath.replace(localCopyPath,"");
								
								anchor.setPath(tempPath);
								
								if(!anchors.contains(anchor)) {
									
									anchors.add(anchor);
								}
							}
							
						}
					
					}
				}
				
			} catch (IOException e) {
				
				return null;
				
				
			}
			
			return anchors;
		}
	}
	
	
	
	
	
	/**
	 * Returns a duplicate of an HTML Extractor object.
	 * 
	 * @return A clone.
	 */
	@Override
    public Object clone()
    {
		
		
		return new HTMLExtractor(pagePath,localCopyPath,URLs);
    }
	
	
	/**
	 * Determines whether two HTML Extractor objects are the same.
	 * 
	 * Two HTML Extractor objects are equivalent if they have the same file path,
	 * local copy path, and URLs. 
	 * Otherwise, they are not equivalent.
	 * 
	 * @param rhs An HTML Extractor object to compare the current object to.
	 */
	@Override
    public boolean equals(Object rhs)
    {
		HTMLExtractor other = (HTMLExtractor)rhs;
		
		return other.getPagePath().equals(pagePath) &&
				other.getLocalCopyPath().equals(localCopyPath) &&
				Arrays.equals(other.getURLs(), URLs);
    }
	
	/**
	 * Returns a hash code representative of the object.
	 * 
	 * Ideally, it would consist of the hash code of the Strings of the file path and local copy / directory path,
	 * plus the hash codes of the Strings of the URLs.
	 * 
	 * Return hash A representative hash code.
	 */
	@Override
    public int hashCode()
    {
		
		int hash = pagePath.hashCode() + localCopyPath.hashCode();
		
		if(URLs != null) {
			
			for(String URL : URLs) {
				
				hash += URL.hashCode();
			}
		}
		
		
		return hash;
    }
	
	
	/**
	 * Returns a representative String. The String should list the path of the to-be-extracted document,
	 * the path of the local copy, and the URLs.
	 * 
	 * The format would be:
	 * "[file path] [local copy path] [URL 1] [URL 2] [URL 3, etc.]"
	 * 
	 * @return string A representative String.
	 */
	@Override
	public String toString() {
		
	
		String string = pagePath;
		
		if(localCopyPath.equals("") == false) {
			
			string = string+" "+localCopyPath;
		}
		
		if(URLs != null) {
		
			for(String URL : URLs) {
				
				string = string.concat(" "+URL);
				
			}
		}
		
		return string;
	}
	
	
}

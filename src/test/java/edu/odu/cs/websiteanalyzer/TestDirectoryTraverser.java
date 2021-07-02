package edu.odu.cs.websiteanalyzer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import edu.odu.cs.websiteanalyzer.Resource.Classification;
import edu.odu.cs.websiteanalyzer.Resource.Type;

import static org.hamcrest.Matchers.*;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDirectoryTraverser {
	
	private DirectoryTraverser defaultTraverser, nonDefaultTraverser;
	
	private String[] URLs;
	private String path;
	
	List<Resource> emptyList,
	shoppingResources, 
	shoppingImages, 
	shoppingScripts, 
	shoppingCSS,
	shoppingIntrapageLinks, 
	shoppingExternalLinks, 
	shoppingInternalLinks, 
	shoppingPages,
	gamesResources, 
	gamesImages, 
	gamesScripts, 
	gamesCSS,
	gamesIntrapageLinks, 
	gamesExternalLinks, 
	gamesInternalLinks, 
	gamesPages;
	
	Resource gamesPage, extractedGamesPage, shoppingPage, extractedShoppingPage;
	
	File cssFile, gamesFile, shoppingFile, temporaryFile;
	
	
	

	@Before
	public void setUp() throws Exception {
		
		path = "src/test/data/testsite/";
		
		URLs = new String[] {"http://www.testsite.net","https://www.cs.testsite.net"};
		
		
		defaultTraverser = new DirectoryTraverser();
		nonDefaultTraverser = new DirectoryTraverser(path, URLs);
		
		
		/** Set up files **/
		
		gamesFile = new File(path+"games/games.cgi");
		shoppingFile = new File(path+"games/Shopping.php");
		cssFile = new File(path+"css/Test_Style.css");
		
		emptyList = new ArrayList<Resource>();
		
		
		/** Set up Games Extractor **/
		
		gamesPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"games/games.cgi",
				((double)gamesFile.length() / (1024 * 1024)),
				emptyList,emptyList);
		
		gamesPages = new ArrayList<Resource>();
		gamesPages.add(gamesPage);
		
		gamesScripts = new ArrayList<Resource>();
		
		gamesCSS = new ArrayList<Resource>();
		gamesCSS.add(new Resource(Type.CSS, 
				Classification.INTERNAL,
				"css/Test_Style.css",
				((double)cssFile.length() / (1024 * 1024)),gamesPages,emptyList));
		
		gamesIntrapageLinks = new ArrayList<Resource>();
		gamesIntrapageLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTRAPAGE,
				"games/games.cgi#payment",
				0.00,gamesPages,emptyList));
		gamesIntrapageLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTRAPAGE,
				"games/games.cgi#gameplay",
				0.00,gamesPages,emptyList));
		gamesIntrapageLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTRAPAGE,
				"games/games.cgi#final",
				0.00,gamesPages,emptyList));
		gamesIntrapageLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTRAPAGE,
				"games/games.cgi#top",
				0.00,gamesPages,emptyList));
		
		gamesExternalLinks = new ArrayList<Resource>();
		
		gamesInternalLinks = new ArrayList<Resource>(); 
		
		gamesImages = new ArrayList<Resource>();
		
		gamesResources = new ArrayList<Resource>();
		
		gamesResources.addAll(gamesScripts);
		gamesResources.addAll(gamesCSS);
		gamesResources.addAll(gamesImages);
		gamesResources.addAll(gamesInternalLinks);
		gamesResources.addAll(gamesExternalLinks);
		gamesResources.addAll(gamesIntrapageLinks);
		
		extractedGamesPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"games/games.cgi",
				((double)gamesFile.length() / (1024 * 1024)),
				emptyList,gamesResources);
		

		/** Set up shopping extractor. **/
		shoppingPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"games/Shopping.php",
				((double)shoppingFile.length() / (1024 * 1024)),
				emptyList,emptyList);
		
		shoppingPages = new ArrayList<Resource>();
		shoppingPages.add(shoppingPage);
		
		shoppingScripts = new ArrayList<Resource>();
		
		shoppingCSS = new ArrayList<Resource>();
		shoppingCSS.add(new Resource(Type.CSS, 
				Classification.INTERNAL,
				"css/Test_Style.css",
				((double)cssFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		shoppingIntrapageLinks = new ArrayList<Resource>(); 
		shoppingIntrapageLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTRAPAGE,
				"games/Shopping.php",
				0.00,shoppingPages,emptyList));
		
		
		shoppingExternalLinks = new ArrayList<Resource>();
		
		shoppingExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.bricklink.com/v2/main.page",
				0.00,shoppingPages,emptyList));
		
		shoppingInternalLinks = new ArrayList<Resource>(); 
		shoppingInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"index.html",
				0.00,shoppingPages,emptyList));
		
		shoppingInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"tools/Grapher.html",
				0.00,shoppingPages,emptyList));
		
		temporaryFile = new File(path+"gallery/pics/products/Axle.png");
		
		shoppingImages = new ArrayList<Resource>();
		shoppingImages.add(new Resource(Type.IMAGE, 
					Classification.INTERNAL,
					"gallery/pics/products/Axle.png",
					((double)temporaryFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		temporaryFile = new File(path+"gallery/pics/products/tile.png");
		
		shoppingImages.add(new Resource(Type.IMAGE, 
				Classification.INTERNAL,
				"gallery/pics/products/tile.png",
				((double)temporaryFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		temporaryFile = new File(path+"shopping/pics/pizza.jpg");
		
		
		shoppingImages.add(new Resource(Type.IMAGE, 
				Classification.INTERNAL,
				"shopping/pics/pizza.jpg",
				((double)temporaryFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		
		shoppingImages.add(new Resource(Type.IMAGE, 
				Classification.EXTERNAL,
				"http://www.cs.odu.edu/~emulloy/cs350/sshAsst/brick2x4.jpg",
				0.00,shoppingPages,emptyList));
		
		
		shoppingResources = new ArrayList<Resource>();
		
		shoppingResources.addAll(shoppingImages);
		shoppingResources.addAll(shoppingCSS);
		shoppingResources.addAll(shoppingScripts);
		shoppingResources.addAll(shoppingIntrapageLinks);
		
		shoppingResources.add(new Resource(Type.PAGE_ANCHOR, 
				Classification.INTERNAL,
				"index.html",
				0.00,shoppingPages,emptyList));
		
		shoppingResources.add(new Resource(Type.PAGE_ANCHOR, 
				Classification.INTERNAL,
				"tools/Grapher.html",
				0.00,shoppingPages,emptyList));
		
		shoppingResources.add(new Resource(Type.PAGE_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.bricklink.com/v2/main.page",
				0.00,shoppingPages,emptyList));
		
		extractedShoppingPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"games/Shopping.php",
				((double)shoppingFile.length() / (1024 * 1024)),
				emptyList,shoppingResources);
		
		
	}
	

	@Test
	public void TestDefaultConstructor() {
		
		assertThat(defaultTraverser.GetDirectoryPath(), is(equalTo("")));
		assertThat(defaultTraverser.GetURLs(), is(equalTo(new String[] {})));
		assertThat(defaultTraverser.GetDocumentCount(), is(0));
		assertThat(defaultTraverser.toString(), is(equalTo("")));
		
		assertThat(defaultTraverser.hashCode(), is(equalTo(defaultTraverser.hashCode())));
		assertThat(defaultTraverser, is(equalTo(defaultTraverser)));
		
	}
	
	
	@Test
	public void TestNonDefaultConstructor() {
		
		assertThat(nonDefaultTraverser.GetDirectoryPath(), is(equalTo(path)));
		assertThat(nonDefaultTraverser.GetURLs(), is(equalTo(URLs)));
		assertThat(nonDefaultTraverser.GetDocumentCount(), is(7));
		assertThat(nonDefaultTraverser.toString(), containsString(path));
		assertThat(nonDefaultTraverser.toString(),
				stringContainsInOrder(Arrays.asList(URLs)));
		
		assertThat(nonDefaultTraverser.hashCode(), is(equalTo(nonDefaultTraverser.hashCode())));
		assertThat(nonDefaultTraverser, is(equalTo(nonDefaultTraverser)));
		
		
		assertThat(nonDefaultTraverser.GetDirectoryPath(), not(equalTo(defaultTraverser.GetDirectoryPath())));
		assertThat(nonDefaultTraverser.GetURLs(), not(equalTo(defaultTraverser.GetURLs())));
		assertThat(nonDefaultTraverser.GetDocumentCount(), not(equalTo(defaultTraverser.GetDocumentCount())));
		assertThat(nonDefaultTraverser.toString(), not(equalTo(defaultTraverser.toString())));
		
		assertThat(nonDefaultTraverser.hashCode(), not(equalTo(defaultTraverser.hashCode())));
		assertThat(nonDefaultTraverser, not(equalTo(defaultTraverser)));
		
	}
	
	
	
	
	@Test
	public void TestSetDirectoryPath() {
		
		// Should be nothing in here.
		String newPath = "src/test/data/emptysite/";
		
		String oldDefaultString = defaultTraverser.toString();
		
		String oldNonDefaultString = nonDefaultTraverser.toString();
		
		int oldDefaultHash = defaultTraverser.hashCode();
		
		int oldNonDefaultHash = nonDefaultTraverser.hashCode();
		
		DirectoryTraverser otherDefault = new DirectoryTraverser();
		
		DirectoryTraverser otherNonDefault = new DirectoryTraverser(path,URLs);
		
		defaultTraverser.SetDirectoryPath(newPath);
		
		nonDefaultTraverser.SetDirectoryPath(newPath);
		
		
		assertThat(defaultTraverser.GetDirectoryPath(), is(equalTo(newPath)));
		assertThat(defaultTraverser.GetURLs(), is(equalTo(new String[] {})));
		assertThat(defaultTraverser.GetDocumentCount(), is(0));
		assertThat(defaultTraverser.toString(), containsString(newPath));
		assertThat(defaultTraverser.toString(), not(equalTo(oldDefaultString)));
		assertThat(defaultTraverser.hashCode(), not(equalTo(oldDefaultHash)));
		assertThat(defaultTraverser, not(equalTo(otherDefault)));
		
		assertThat(nonDefaultTraverser.GetDirectoryPath(), equalTo(newPath));
		assertThat(nonDefaultTraverser.GetURLs(), equalTo(URLs));
		assertThat(nonDefaultTraverser.GetDocumentCount(), is(0));
		assertThat(nonDefaultTraverser.toString(), containsString(newPath));
		assertThat(nonDefaultTraverser.toString(), not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultTraverser.hashCode(), not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultTraverser, not(equalTo(otherNonDefault)));
		
	}
	
	@Test
	public void TestSetURLs() {
		
		// Should be nothing in here.
		String[] newURLs = {"http://www.nextsite.com", "https://www.cs.nextsite.com"};
		
		String oldDefaultString = defaultTraverser.toString();
		
		String oldNonDefaultString = nonDefaultTraverser.toString();
		
		int oldDefaultHash = defaultTraverser.hashCode();
		
		int oldNonDefaultHash = nonDefaultTraverser.hashCode();
		
		DirectoryTraverser otherDefault = new DirectoryTraverser();
		
		DirectoryTraverser otherNonDefault = new DirectoryTraverser(path,URLs);
		
		defaultTraverser.SetURLs(newURLs);
		
		nonDefaultTraverser.SetURLs(newURLs);
		
		
		assertThat(defaultTraverser.GetDirectoryPath(), is(equalTo("")));
		assertThat(defaultTraverser.GetURLs(), is(equalTo(newURLs)));
		assertThat(defaultTraverser.GetDocumentCount(), is(0));
		assertThat(defaultTraverser.toString(),
				stringContainsInOrder(Arrays.asList(newURLs)));
		assertThat(defaultTraverser.toString(), not(equalTo(oldDefaultString)));
		assertThat(defaultTraverser.hashCode(), not(equalTo(oldDefaultHash)));
		assertThat(defaultTraverser, not(equalTo(otherDefault)));
		
		assertThat(nonDefaultTraverser.GetDirectoryPath(), equalTo(path));
		assertThat(nonDefaultTraverser.GetURLs(), equalTo(newURLs));
		assertThat(nonDefaultTraverser.GetDocumentCount(), equalTo(otherNonDefault.GetDocumentCount()));
		assertThat(nonDefaultTraverser.toString(), containsString(path));
		assertThat(nonDefaultTraverser.toString(),
				stringContainsInOrder(Arrays.asList(newURLs)));
		assertThat(nonDefaultTraverser.toString(), not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultTraverser.hashCode(), not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultTraverser, not(equalTo(otherNonDefault)));
	}
	
	@Test
	public void TestAnalyzeWebsite() {
		
		// Test to ensure that the six files within the test site are found.
		
		assertThat(nonDefaultTraverser.GetDocumentCount(), is(7));
		
		
		// Test to ensure that the file(s) are written.
		

		// First, ensure that the filename is in the format YYYYMMDD-HHMMSS-summary.xlsx
		
		// Load file based on time stamp
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
		
		// Write the file(s)
		String[] returned = nonDefaultTraverser.AnalyzeWebsite();
		
		assertThat(returned, notNullValue());
		
		// NOTE: May need to adjust length.
		assertThat(returned.length, is(1));
		
		assertThat(returned[0], containsString("-summary.xlsx"));
		
		String datePart1 = returned[0];
		
		datePart1.replace("-summary.xlsx","");
		
		try {
			
			assertThat(dateFormat.parse(datePart1), notNullValue());
			
		} catch (ParseException e1) {
			
			fail("The file does not match the naming convention");
			
		}
		
		// Next, see if the loaded file contains the required data / format.
		
		
		try {
			FileInputStream loadedFile  = new FileInputStream("build/"+returned[0]);
			
			
		} catch (FileNotFoundException e) {
			
			fail("Could not find file!");
			
		}
	}
	
	
	@Test
	public void TestGetPagesInFolder() {
		
		List<Resource> pages = new ArrayList<Resource>();
		List<Resource> emptyList = new ArrayList<Resource>();
		pages.add(extractedGamesPage);
		pages.add(extractedShoppingPage);
		
		
		List<Resource> folderPages = nonDefaultTraverser.GetPagesInFolder(new File(path+"games"));
		assertThat(folderPages.get(0).getExtractedResources(), equalTo(pages.get(0).getExtractedResources()));
		assertThat(folderPages.get(1).getExtractedResources(), equalTo(pages.get(1).getExtractedResources()));
		assertThat(folderPages.get(0).getPages(), equalTo(pages.get(0).getPages()));
		assertThat(folderPages.get(1).getPages(), equalTo(pages.get(1).getPages()));

		assertThat(folderPages, equalTo(pages));
		
		
		assertThat(defaultTraverser.GetPagesInFolder(new File("")), is(equalTo(emptyList)));
		
		
		defaultTraverser.SetDirectoryPath("src/test/data/emptysite/");
		
		assertThat(defaultTraverser.GetPagesInFolder(new File("")), is(equalTo(emptyList)));
		
		
	}

	@Test
	public void TestClone() {
		
		
		DirectoryTraverser otherDefault = new DirectoryTraverser();
		
		DirectoryTraverser defaultClone = (DirectoryTraverser)defaultTraverser.clone();
		
		assertThat(defaultClone.GetURLs(), equalTo(otherDefault.GetURLs()));
		
		assertThat(defaultClone.GetDocumentCount(), equalTo(otherDefault.GetDocumentCount()));
		
		assertThat(defaultClone.GetDirectoryPath(), equalTo(otherDefault.GetDirectoryPath()));
		
		assertThat(defaultClone.toString(), equalTo(otherDefault.toString()));
		
		assertThat(defaultClone.hashCode(), equalTo(otherDefault.hashCode()));
		
		assertThat(defaultClone, equalTo(otherDefault));
		
		assertThat(defaultTraverser.GetURLs(), equalTo(otherDefault.GetURLs()));
		
		assertThat(defaultTraverser.GetDocumentCount(), equalTo(otherDefault.GetDocumentCount()));
		
		assertThat(defaultTraverser.GetDirectoryPath(), equalTo(otherDefault.GetDirectoryPath()));
		
		assertThat(defaultTraverser.toString(), equalTo(otherDefault.toString()));
		
		assertThat(defaultTraverser.hashCode(), equalTo(otherDefault.hashCode()));
		
		assertThat(defaultTraverser, equalTo(otherDefault));
		
		
		DirectoryTraverser otherNonDefault = new DirectoryTraverser(path,URLs);
		
		DirectoryTraverser nonDefaultClone = (DirectoryTraverser)nonDefaultTraverser.clone();
		
		assertThat(nonDefaultClone.GetURLs(), equalTo(otherNonDefault.GetURLs()));
		
		assertThat(nonDefaultClone.GetDocumentCount(), equalTo(otherNonDefault.GetDocumentCount()));
		
		assertThat(nonDefaultClone.GetDirectoryPath(), equalTo(otherNonDefault.GetDirectoryPath()));
		
		assertThat(nonDefaultClone.toString(), equalTo(otherNonDefault.toString()));
		
		assertThat(nonDefaultClone.hashCode(), equalTo(otherNonDefault.hashCode()));
		
		assertThat(nonDefaultClone, equalTo(otherNonDefault));
		
		assertThat(nonDefaultTraverser.GetURLs(), equalTo(otherNonDefault.GetURLs()));
		
		assertThat(nonDefaultTraverser.GetDocumentCount(), equalTo(otherNonDefault.GetDocumentCount()));
		
		assertThat(nonDefaultTraverser.GetDirectoryPath(), equalTo(otherNonDefault.GetDirectoryPath()));
		
		assertThat(nonDefaultTraverser.toString(), equalTo(otherNonDefault.toString()));
		
		assertThat(nonDefaultTraverser.hashCode(), equalTo(otherNonDefault.hashCode()));
		
		assertThat(nonDefaultTraverser, equalTo(otherNonDefault));
		
		
		
	}
}

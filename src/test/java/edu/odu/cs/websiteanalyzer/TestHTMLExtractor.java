package edu.odu.cs.websiteanalyzer;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.*;

import edu.odu.cs.websiteanalyzer.Resource.*;

public class TestHTMLExtractor {
	
	
	HTMLExtractor defaultExtractor, shoppingExtractor, gamesExtractor, grapherExtractor,
	downloadsExtractor, otherDefault, otherShopping;
	
	Resource shoppingPage, gamesPage, grapherPage, downloadsPage,
			extractedShoppingPage, extractedGamesPage, extractedGrapherPage, extractedDownloadsPage;
	
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
	gamesPages,
	grapherResources, 
	grapherImages, 
	grapherScripts, 
	grapherCSS,
	grapherIntrapageLinks, 
	grapherExternalLinks, 
	grapherInternalLinks, 
	grapherPages,
	downloadsResources, 
	downloadsImages, 
	downloadsScripts, 
	downloadsCSS,
	downloadsIntrapageLinks, 
	downloadsExternalLinks, 
	downloadsInternalLinks, 
	downloadsPages;
	
	String emptyString, localCopyPath, otherLocalCopyPath, 
	URL1, URL2, shoppingPath, gamesPath, grapherPath,
	oldShoppingString, oldDefaultString, _404path, downloadsPath;
	
	String[] emptyURLs, nonEmptyURLs;
	
	int oldDefaultHash, oldShoppingHash;
	
	File shoppingFile, gamesFile, grapherFile, cssFile, temporaryFile;
	
	

	@Before
	public void setUp() throws Exception {
		
		localCopyPath = "src/test/data/testsite/";
		
		otherLocalCopyPath = "src/test/data/testsite";
		
		URL1 = "http://www.testsite.net";
		
		URL2 = "https://www.cs.testsite.net/";
		
		shoppingPath = "src/test/data/testsite/shopping/toys/Shopping.php";
		
		gamesPath = "src/test/data/testsite/games/games.cgi";
		
		grapherPath = "src/test/data/testsite/tools/Grapher.html";
		
		downloadsPath = "src/test/data/testsite/Downloads.html";
		
		_404path = "src/test/data/testsite/error.html";
		
		File shoppingFile = new File(shoppingPath);
		File gamesFile = new File(gamesPath);
		File grapherFile = new File(grapherPath);
		File cssFile = new File(localCopyPath+"css/Test_Style.css");
		File downloadsFile = new File(downloadsPath);
		
		emptyURLs = new String[] {};
		
		nonEmptyURLs = new String[] {URL1, URL2};
		
		defaultExtractor = new HTMLExtractor();
		
		shoppingExtractor = new HTMLExtractor(shoppingPath,localCopyPath,nonEmptyURLs);
		
		gamesExtractor = new HTMLExtractor(gamesPath,"src/test/data/testsite",nonEmptyURLs);
		
		grapherExtractor = new HTMLExtractor(grapherPath,localCopyPath,nonEmptyURLs);
		
		downloadsExtractor = new HTMLExtractor(downloadsPath,localCopyPath,nonEmptyURLs);
		
		emptyList = new ArrayList<Resource>();
		
		
		
		/** Set up Shopping Extractor **/
		
		shoppingPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"shopping/toys/Shopping.php",
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
				"shopping/toys/Shopping.php",
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
		
		temporaryFile = new File(localCopyPath+"gallery/pics/products/Axle.png");
		
		shoppingImages = new ArrayList<Resource>();
		shoppingImages.add(new Resource(Type.IMAGE, 
					Classification.INTERNAL,
					"gallery/pics/products/Axle.png",
					((double)temporaryFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		temporaryFile = new File(localCopyPath+"gallery/pics/products/tile.png");
		
		shoppingImages.add(new Resource(Type.IMAGE, 
				Classification.INTERNAL,
				"gallery/pics/products/tile.png",
				((double)temporaryFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		temporaryFile = new File(localCopyPath+"shopping/pics/pizza.jpg");
		
		
		shoppingImages.add(new Resource(Type.IMAGE, 
				Classification.INTERNAL,
				"shopping/pics/pizza.jpg",
				((double)temporaryFile.length() / (1024 * 1024)),shoppingPages,emptyList));
		
		
		shoppingImages.add(new Resource(Type.IMAGE, 
				Classification.EXTERNAL,
				"http://www.cs.odu.edu/~emulloy/cs350/sshAsst/brick2x4.jpg",
				0.00,shoppingPages,emptyList));
		
		
		shoppingResources = new ArrayList<Resource>();
		
		shoppingResources.addAll(shoppingScripts);
		shoppingResources.addAll(shoppingCSS);
		shoppingResources.addAll(shoppingImages);
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
				"shopping/toys/Shopping.php",
				((double)shoppingFile.length() / (1024 * 1024)),
				emptyList,shoppingResources);
		
		
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
		
		/** Set up Grapher **/
		
		grapherPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"tools/Grapher.html",
				((double)grapherFile.length() / (1024 * 1024)),
				emptyList,emptyList);
		
		grapherPages = new ArrayList<Resource>();
		grapherPages.add(grapherPage);
		
		temporaryFile = new File(localCopyPath+"scripts/Grapher.js");
		
		grapherScripts = new ArrayList<Resource>();
		grapherScripts.add(new Resource(Type.SCRIPT, 
				Classification.INTERNAL,
				"scripts/Grapher.js",
				((double)temporaryFile.length() / (1024 * 1024)),grapherPages,emptyList));
		grapherScripts.add(new Resource(Type.SCRIPT, 
				Classification.EXTERNAL,
				"https://www.cs.odu.edu/~emulloy/cs350/sshAsst/doSomething.js",
				0.00,grapherPages,emptyList));
		
		grapherCSS = new ArrayList<Resource>();
		grapherCSS.add(new Resource(Type.CSS, 
				Classification.EXTERNAL,
				"https://www.cs.odu.edu/~emulloy/cs350/sshAsst/Test_Style.css",
				0.00,grapherPages,emptyList));
		
		grapherIntrapageLinks = new ArrayList<Resource>(); 
		grapherExternalLinks = new ArrayList<Resource>();
		
		grapherInternalLinks = new ArrayList<Resource>();
		
		
		grapherImages = new ArrayList<Resource>();
		
		
		grapherResources = new ArrayList<Resource>();
		
		grapherResources.addAll(grapherScripts);
		grapherResources.addAll(grapherCSS);
		grapherResources.addAll(grapherImages);
		grapherResources.addAll(grapherExternalLinks);
		grapherResources.addAll(grapherIntrapageLinks);
		
		extractedGrapherPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"tools/Grapher.html",
				((double)grapherFile.length() / (1024 * 1024)),
				emptyList,grapherResources);
		
		/** Set up Games Extractor **/
		
		downloadsPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"Downloads.html",
				((double)downloadsFile.length() / (1024 * 1024)),
				emptyList,emptyList);
		
		downloadsPages = new ArrayList<Resource>();
		downloadsPages.add(downloadsPage);
		
		downloadsScripts = new ArrayList<Resource>();
		
		downloadsCSS = new ArrayList<Resource>();
		downloadsCSS.add(new Resource(Type.CSS, 
				Classification.INTERNAL,
				"css/Test_Style.css",
				((double)cssFile.length() / (1024 * 1024)),downloadsPages,emptyList));
		
		downloadsIntrapageLinks = new ArrayList<Resource>();
		downloadsIntrapageLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTRAPAGE,
				"Downloads.html",
				0.00,downloadsPages,emptyList));
		
		downloadsExternalLinks = new ArrayList<Resource>();
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"http://www.example.com/blah.cgi",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"http://www.example.com/bleh.htm",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.aac",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.avc",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.vqf",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.mka",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.m4a",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.tar.gz",
				0.00,
				downloadsPages,emptyList));
		downloadsExternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/video/something.mkv",
				0.00,
				downloadsPages,emptyList));
		
		downloadsInternalLinks = new ArrayList<Resource>();
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"shopping/toys/Shopping.php",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"scripts/Grapher.js",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"css/Test_Style.css",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/video/NexusDefenders.mp4",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/audio/After_School_Special.ogg",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/audio/POTC-Reward.mp3",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blockland.jpg",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"games/other_files/Basic_Diesel.blend",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"inaccessible.zip",
				0.00,
				downloadsPages,emptyList));
		
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"games/archive/archive.7z",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"games/archive/archive.tar",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Diagram1.svg",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blank.bmp",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blank.jpeg",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blank.gif",
				0.00,
				downloadsPages,emptyList));
		downloadsInternalLinks.add(new Resource(Type.ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/cs_462_desc.png",
				0.00,
				downloadsPages,emptyList));
		
		
		downloadsImages = new ArrayList<Resource>();
		
		downloadsResources = new ArrayList<Resource>();
		
		downloadsResources.addAll(downloadsScripts);
		downloadsResources.addAll(downloadsCSS);
		downloadsResources.addAll(downloadsImages);
		downloadsResources.addAll(downloadsIntrapageLinks);
		
		downloadsResources.add(new Resource(Type.PAGE_ANCHOR, 
				Classification.INTERNAL,
				"shopping/toys/Shopping.php",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.PAGE_ANCHOR, 
				Classification.EXTERNAL,
				"http://www.example.com/blah.cgi",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.PAGE_ANCHOR, 
				Classification.EXTERNAL,
				"http://www.example.com/bleh.htm",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.SCRIPT_ANCHOR, 
				Classification.INTERNAL,
				"scripts/Grapher.js",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.CSS_ANCHOR, 
				Classification.INTERNAL,
				"css/Test_Style.css",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.VIDEO_ANCHOR, 
				Classification.INTERNAL,
				"gallery/video/NexusDefenders.mp4",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.VIDEO_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/video/something.mkv",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.INTERNAL,
				"gallery/audio/POTC-Reward.mp3",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.INTERNAL,
				"gallery/audio/After_School_Special.ogg",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.aac",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.avc",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.vqf",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.mka",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.AUDIO_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.m4a",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.IMAGE_ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blockland.jpg",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.IMAGE_ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Diagram1.svg",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.IMAGE_ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blank.bmp",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.IMAGE_ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blank.jpeg",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.IMAGE_ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/Blank.gif",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.IMAGE_ANCHOR, 
				Classification.INTERNAL,
				"gallery/pics/cs_462_desc.png",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.UNCATEGORIZED_ANCHOR, 
				Classification.INTERNAL,
				"games/other_files/Basic_Diesel.blend",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.ARCHIVE_ANCHOR, 
				Classification.INTERNAL,
				"inaccessible.zip",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.ARCHIVE_ANCHOR, 
				Classification.EXTERNAL,
				"https://www.example.com/gallery/audio/something.tar.gz",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.ARCHIVE_ANCHOR, 
				Classification.INTERNAL,
				"games/archive/archive.7z",
				0.00,
				downloadsPages,emptyList));
		downloadsResources.add(new Resource(Type.ARCHIVE_ANCHOR, 
				Classification.INTERNAL,
				"games/archive/archive.tar",
				0.00,
				downloadsPages,emptyList));
		
		extractedDownloadsPage = new Resource(Type.PAGE, 
				Classification.INTERNAL,
				"Downloads.html",
				((double)downloadsFile.length() / (1024 * 1024)),
				emptyList,downloadsResources);
	
		/** Comparison data **/
	
		oldShoppingString = shoppingExtractor.toString();
				
		oldDefaultString = defaultExtractor.toString();
		
		oldShoppingHash = shoppingExtractor.hashCode();
		
		oldDefaultHash = defaultExtractor.hashCode();
		
		otherDefault = new HTMLExtractor();
		
		otherShopping = new HTMLExtractor(shoppingPath,localCopyPath,nonEmptyURLs);
		
	
	}

	@Test
	public void testDefaultConstructor() {
		
		assertThat(defaultExtractor.getLocalCopyPath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getPagePath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getURLs(),is(equalTo(emptyURLs)));
		
		
		// Without a valid path and URLs, any Resources we return should be null.
		
		assertThat(defaultExtractor.getImages(),is(nullValue()));
		
		assertThat(defaultExtractor.getCSS(),is(nullValue()));
		
		assertThat(defaultExtractor.getScripts(),is(nullValue()));
		
		assertThat(defaultExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getResources(),is(nullValue()));
		
		assertThat(defaultExtractor.getPage(),is(nullValue()));
		
		
		assertThat(defaultExtractor,is(equalTo(defaultExtractor)));
		
		assertThat(defaultExtractor.hashCode(),is(equalTo(defaultExtractor.hashCode())));
		
		assertThat(defaultExtractor.toString(),is(equalTo("")));
		
		
		
	}
	
	@Test
	public void testNonDefaultConstructor() {
		
		Resource badLink1 = new Resource(Type.ANCHOR,Classification.INTERNAL,"",0.00,shoppingPages,emptyList);
		Resource badLink2 = new Resource(Type.ANCHOR,Classification.EXTERNAL,"",0.00,shoppingPages,emptyList);
		Resource badLink3 = new Resource(Type.ANCHOR,Classification.INTRAPAGE,"",0.00,shoppingPages,emptyList);
		
		
		
		assertThat(shoppingExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(shoppingExtractor.getPagePath(),is(equalTo(shoppingPath)));
		
		assertThat(shoppingExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		assertThat(shoppingExtractor.getImages(),containsInAnyOrder(shoppingImages.toArray()));
		
		assertThat(shoppingExtractor.getCSS(),containsInAnyOrder(shoppingCSS.toArray()));
		
		assertThat(shoppingExtractor.getScripts(),containsInAnyOrder(shoppingScripts.toArray()));
		
		assertThat(shoppingExtractor.getInternalAnchors(),containsInAnyOrder(shoppingInternalLinks.toArray()));
		
		assertThat(shoppingExtractor.getExternalAnchors(),containsInAnyOrder(shoppingExternalLinks.toArray()));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(),containsInAnyOrder(shoppingIntrapageLinks.toArray()));
		
		assertThat(shoppingExtractor.getResources(),containsInAnyOrder(shoppingResources.toArray()));
		
		assertThat(shoppingExtractor.getPage().getExtractedResources(),containsInAnyOrder(shoppingExtractor.getResources().toArray()));
		
		assertThat(shoppingExtractor.getInternalAnchors(), not(contains(badLink1)));
		
		assertThat(shoppingExtractor.getExternalAnchors(), not(contains(badLink2)));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(), not(contains(badLink3)));
		
		assertThat(shoppingExtractor, not(equalTo(defaultExtractor)));
		
		assertThat(shoppingExtractor.hashCode(),not(equalTo(defaultExtractor.hashCode())));
		
		assertThat(shoppingExtractor.toString(),not(equalTo(defaultExtractor.toString())));
		
		
		assertThat(gamesExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(gamesExtractor.getPagePath(),is(equalTo(gamesPath)));
		
		assertThat(gamesExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		assertThat(gamesExtractor.getImages(),containsInAnyOrder(gamesImages.toArray()));
		
		assertThat(gamesExtractor.getCSS(),containsInAnyOrder(gamesCSS.toArray()));
		
		assertThat(gamesExtractor.getScripts(),containsInAnyOrder(gamesScripts.toArray()));
		
		assertThat(gamesExtractor.getInternalAnchors(),containsInAnyOrder(gamesInternalLinks.toArray()));
		
		assertThat(gamesExtractor.getExternalAnchors(),containsInAnyOrder(gamesExternalLinks.toArray()));
		
		assertThat(gamesExtractor.getIntrapageAnchors(),containsInAnyOrder(gamesIntrapageLinks.toArray()));
		
		assertThat(gamesExtractor.getResources(),containsInAnyOrder(gamesResources.toArray()));
		
		assertThat(gamesExtractor.getPage().getExtractedResources(),is(equalTo(gamesExtractor.getResources())));
		
		
		assertThat(gamesExtractor.getInternalAnchors(), not(contains(badLink1)));
		
		assertThat(gamesExtractor.getExternalAnchors(), not(contains(badLink2)));
		
		assertThat(gamesExtractor.getIntrapageAnchors(), not(contains(badLink3)));
		
		
		assertThat(gamesExtractor,not(equalTo(defaultExtractor)));
		
		assertThat(gamesExtractor.hashCode(),not(equalTo(defaultExtractor.hashCode())));
		
		assertThat(gamesExtractor.toString(),not(equalTo(defaultExtractor.toString())));
		
		
		assertThat(grapherExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(grapherExtractor.getPagePath(),is(equalTo(grapherPath)));
		
		assertThat(grapherExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		assertThat(grapherExtractor.getImages(),containsInAnyOrder(grapherImages.toArray()));
		
		assertThat(grapherExtractor.getCSS(),containsInAnyOrder(grapherCSS.toArray()));
		
		assertThat(grapherExtractor.getScripts(),containsInAnyOrder(grapherScripts.toArray()));
		
		assertThat(grapherExtractor.getInternalAnchors(),containsInAnyOrder(grapherInternalLinks.toArray()));
		
		assertThat(grapherExtractor.getExternalAnchors(),containsInAnyOrder(grapherExternalLinks.toArray()));
		
		assertThat(grapherExtractor.getIntrapageAnchors(),containsInAnyOrder(grapherIntrapageLinks.toArray()));
		
		assertThat(grapherExtractor.getResources(),containsInAnyOrder(grapherResources.toArray()));
		
		assertThat(grapherExtractor.getPage().getExtractedResources(),is(equalTo(grapherExtractor.getResources())));
		
		assertThat(grapherExtractor.getInternalAnchors(), not(contains(badLink1)));
		
		assertThat(grapherExtractor.getExternalAnchors(), not(contains(badLink2)));
		
		assertThat(grapherExtractor.getIntrapageAnchors(), not(contains(badLink3)));
		
		assertThat(grapherExtractor,not(equalTo(defaultExtractor)));
		
		assertThat(grapherExtractor.hashCode(),not(equalTo(defaultExtractor.hashCode())));
		
		assertThat(grapherExtractor.toString(),not(equalTo(defaultExtractor.toString())));
		
		
		
		
		assertThat(downloadsExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(downloadsExtractor.getPagePath(),is(equalTo(downloadsPath)));
		
		assertThat(downloadsExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		assertThat(downloadsExtractor.getImages(),containsInAnyOrder(downloadsImages.toArray()));
		
		assertThat(downloadsExtractor.getCSS(),containsInAnyOrder(downloadsCSS.toArray()));
		
		assertThat(downloadsExtractor.getScripts(),containsInAnyOrder(downloadsScripts.toArray()));
		
		assertThat(downloadsExtractor.getInternalAnchors(),containsInAnyOrder(downloadsInternalLinks.toArray()));
		
		assertThat(downloadsExtractor.getExternalAnchors(),containsInAnyOrder(downloadsExternalLinks.toArray()));
		
		assertThat(downloadsExtractor.getIntrapageAnchors(),containsInAnyOrder(downloadsIntrapageLinks.toArray()));
		
		assertThat(downloadsExtractor.getResources(),containsInAnyOrder(downloadsResources.toArray()));
		
		assertThat(downloadsExtractor.getPage().getExtractedResources(),is(equalTo(downloadsExtractor.getResources())));
		
		
		assertThat(downloadsExtractor.getInternalAnchors(), not(contains(badLink1)));
		
		assertThat(downloadsExtractor.getExternalAnchors(), not(contains(badLink2)));
		
		assertThat(downloadsExtractor.getIntrapageAnchors(), not(contains(badLink3)));
		
		assertThat(downloadsExtractor,not(equalTo(defaultExtractor)));
		
		assertThat(downloadsExtractor.hashCode(),not(equalTo(defaultExtractor.hashCode())));
		
		assertThat(downloadsExtractor.toString(),not(equalTo(defaultExtractor.toString())));
		
		
		// Valid file, but can't be parsed.
		HTMLExtractor badExtractor = new HTMLExtractor("src/test/data/testsite/gallery/pics/products/pizza.jpg",localCopyPath,nonEmptyURLs);
		
		assertThat(badExtractor.getImages(),is(nullValue()));
		
		assertThat(badExtractor.getCSS(),is(nullValue()));
		
		assertThat(badExtractor.getScripts(),is(nullValue()));
		
		assertThat(badExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(badExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(badExtractor.getIntrapageAnchors(),is(nullValue()));
		
		// make sure local copy has slash at the end
		
		HTMLExtractor noSlashExtractor = new HTMLExtractor("src/test/data/testsite/index.html","src/test/data/testsite",nonEmptyURLs);
		
		assertThat(noSlashExtractor.getLocalCopyPath(),is(equalTo("src/test/data/testsite/")));
		
		// Make sure 404 content is ignored.
		File index = new File("src/test/data/testsite/index.html");
		
		List<Resource> indexPage = new ArrayList<Resource>();
		indexPage.add(new Resource(Type.PAGE,Classification.INTERNAL,"index.html",
				((double)index.length() / (1024 * 1024)),emptyList,emptyList));
		
		// Get the two internal anchors that work.
		assertThat(noSlashExtractor.getInternalAnchors(),containsInAnyOrder(new Resource(Type.ANCHOR,
				Classification.INTERNAL,"tools/Grapher.html",0.00,indexPage,emptyList),
				new Resource(Type.ANCHOR,Classification.INTERNAL,"shopping/toys/Shopping.php",
						0.00,indexPage,emptyList)));
		
		// Assert that it doesn't contain the 404 content.
		
		assertThat(noSlashExtractor.getInternalAnchors(),not(containsInAnyOrder(new Resource(Type.ANCHOR,
				Classification.INTERNAL,"shopping/NO_EXIST/Shopping.php",0.00,indexPage,emptyList),
				new Resource(Type.ANCHOR, Classification.INTERNAL,"error.html",
						0.00,indexPage,emptyList))));
		
	}
	
	@Test
	public void testSetPagePath() {
		
		defaultExtractor.setPagePath(shoppingPath);
		
		assertThat(defaultExtractor.getLocalCopyPath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getPagePath(),is(equalTo(shoppingPath)));
		
		assertThat(defaultExtractor.getURLs(),is(equalTo(emptyURLs)));
		
		
		// Without a valid path and URLs, any Resources we return should be null.
		
		assertThat(defaultExtractor.getImages(),is(nullValue()));
		
		assertThat(defaultExtractor.getCSS(),is(nullValue()));
		
		assertThat(defaultExtractor.getScripts(),is(nullValue()));
		
		assertThat(defaultExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getResources(),is(nullValue()));
		
		assertThat(defaultExtractor.getPage(),is(nullValue()));
		
		
		assertThat(defaultExtractor,not(equalTo(otherDefault)));
		
		assertThat(defaultExtractor.hashCode(),not(equalTo(oldDefaultHash)));
		
		assertThat(defaultExtractor.toString(),not(equalTo(oldDefaultString)));
		
		// We can't extract from a .jpg
		defaultExtractor.setPagePath(localCopyPath+"gallery/pics/products/pizza.jpg");
		
		assertThat(defaultExtractor.getImages(),is(nullValue()));
		
		assertThat(defaultExtractor.getCSS(),is(nullValue()));
		
		assertThat(defaultExtractor.getScripts(),is(nullValue()));
		
		assertThat(defaultExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getPage(),is(nullValue()));
		
		
		shoppingExtractor.setPagePath("");
		
		assertThat(shoppingExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(shoppingExtractor.getPagePath(),is(equalTo("")));
		
		assertThat(shoppingExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		assertThat(shoppingExtractor.getImages(),is(nullValue()));
		
		assertThat(shoppingExtractor.getCSS(),is(nullValue()));
		
		assertThat(shoppingExtractor.getScripts(),is(nullValue()));
		
		assertThat(shoppingExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getResources(),is(nullValue()));
		
		assertThat(shoppingExtractor.getPage(),is(nullValue()));
		
		
		assertThat(shoppingExtractor,not(equalTo(otherShopping)));
		
		assertThat(shoppingExtractor.hashCode(),not(equalTo(oldShoppingHash)));
		
		assertThat(shoppingExtractor.toString(),not(equalTo(oldShoppingString)));
		
		
		
		// We can't extract from a .jpg
		shoppingExtractor.setPagePath(localCopyPath+"gallery/pics/products/pizza.jpg");
		
		assertThat(shoppingExtractor.getImages(),is(nullValue()));
		
		assertThat(shoppingExtractor.getCSS(),is(nullValue()));
		
		assertThat(shoppingExtractor.getScripts(),is(nullValue()));
		
		assertThat(shoppingExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getPage(),is(nullValue()));
		
		
	}
	
	@Test
	public void testSetLocalCopyPath() {
		
		
		defaultExtractor.setLocalCopyPath(localCopyPath);
		
		assertThat(defaultExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(defaultExtractor.getPagePath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getURLs(),is(equalTo(emptyURLs)));
		
		
		// Without a valid path and URLs, any Resources we return should be null.
		
		assertThat(defaultExtractor.getImages(),is(nullValue()));
		
		assertThat(defaultExtractor.getCSS(),is(nullValue()));
		
		assertThat(defaultExtractor.getScripts(),is(nullValue()));
		
		assertThat(defaultExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getResources(),is(nullValue()));
		
		assertThat(defaultExtractor.getPage(),is(nullValue()));
		
		
		assertThat(defaultExtractor,not(equalTo(otherDefault)));
		
		assertThat(defaultExtractor.hashCode(),not(equalTo(oldDefaultHash)));
		
		assertThat(defaultExtractor.toString(),not(equalTo(oldDefaultString)));
		
		// Make sure that the / gets added.
		
		defaultExtractor.setLocalCopyPath(otherLocalCopyPath);
		
		// The result should be localCopyPath, because a forward slash should be added
		assertThat(defaultExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(defaultExtractor.getPagePath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getURLs(),is(equalTo(emptyURLs)));
		
		
		// Without a valid path and URLs, any Resources we return should be null.
		
		assertThat(defaultExtractor.getImages(),is(nullValue()));
		
		assertThat(defaultExtractor.getCSS(),is(nullValue()));
		
		assertThat(defaultExtractor.getScripts(),is(nullValue()));
		
		assertThat(defaultExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getResources(),is(nullValue()));
		
		assertThat(defaultExtractor.getPage(),is(nullValue()));
		
		
		assertThat(defaultExtractor,not(equalTo(otherDefault)));
		
		assertThat(defaultExtractor.hashCode(),not(equalTo(oldDefaultHash)));
		
		assertThat(defaultExtractor.toString(),not(equalTo(oldDefaultString)));
		
		
		shoppingExtractor.setLocalCopyPath("");
		
		assertThat(shoppingExtractor.getLocalCopyPath(),is(equalTo("")));
		
		assertThat(shoppingExtractor.getPagePath(),is(equalTo(shoppingPath)));
		
		assertThat(shoppingExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		assertThat(shoppingExtractor.getImages(),is(nullValue()));
		
		assertThat(shoppingExtractor.getCSS(),is(nullValue()));
		
		assertThat(shoppingExtractor.getScripts(),is(nullValue()));
		
		assertThat(shoppingExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getResources(),is(nullValue()));
		
		assertThat(shoppingExtractor.getPage(),is(nullValue()));
		
		
		assertThat(shoppingExtractor,not(equalTo(otherShopping)));
		
		assertThat(shoppingExtractor.hashCode(),not(equalTo(oldShoppingHash)));
		
		assertThat(shoppingExtractor.toString(),not(equalTo(oldShoppingString)));
		
		
	}
	
	
	
	@Test
	public void testSetURLs() {
		
		defaultExtractor.setURLs(nonEmptyURLs);
		
		assertThat(defaultExtractor.getLocalCopyPath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getPagePath(),is(equalTo("")));
		
		assertThat(defaultExtractor.getURLs(),is(equalTo(nonEmptyURLs)));
		
		
		// Without a valid path and URLs, any Resources we return should be null.
		
		assertThat(defaultExtractor.getImages(),is(nullValue()));
		
		assertThat(defaultExtractor.getCSS(),is(nullValue()));
		
		assertThat(defaultExtractor.getScripts(),is(nullValue()));
		
		assertThat(defaultExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(defaultExtractor.getResources(),is(nullValue()));
		
		assertThat(defaultExtractor.getPage(),is(nullValue()));
		
		
		assertThat(defaultExtractor,not(equalTo(otherDefault)));
		
		assertThat(defaultExtractor.hashCode(),not(equalTo(oldDefaultHash)));
		
		assertThat(defaultExtractor.toString(),not(equalTo(oldDefaultString)));
		
		
		shoppingExtractor.setURLs(null);
		
		assertThat(shoppingExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(shoppingExtractor.getPagePath(),is(equalTo(shoppingPath)));
		
		assertThat(shoppingExtractor.getURLs(),is(nullValue()));
		
		
		assertThat(shoppingExtractor.getImages(),is(nullValue()));
		
		assertThat(shoppingExtractor.getCSS(),is(nullValue()));
		
		assertThat(shoppingExtractor.getScripts(),is(nullValue()));
		
		assertThat(shoppingExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getResources(),is(nullValue()));
		
		assertThat(shoppingExtractor.getPage(),is(nullValue()));
		
		
		assertThat(shoppingExtractor,not(equalTo(otherShopping)));
		
		assertThat(shoppingExtractor.hashCode(),not(equalTo(oldShoppingHash)));
		
		assertThat(shoppingExtractor.toString(),not(equalTo(oldShoppingString)));
		
		
		shoppingExtractor.setURLs(new String[] {});
		
		assertThat(shoppingExtractor.getLocalCopyPath(),is(equalTo(localCopyPath)));
		
		assertThat(shoppingExtractor.getPagePath(),is(equalTo(shoppingPath)));
		
		assertThat(shoppingExtractor.getURLs(),is(equalTo(new String[] {})));
		
		
		assertThat(shoppingExtractor.getImages(),is(nullValue()));
		
		assertThat(shoppingExtractor.getCSS(),is(nullValue()));
		
		assertThat(shoppingExtractor.getScripts(),is(nullValue()));
		
		assertThat(shoppingExtractor.getInternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getExternalAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getIntrapageAnchors(),is(nullValue()));
		
		assertThat(shoppingExtractor.getResources(),is(nullValue()));
		
		assertThat(shoppingExtractor.getPage(),is(nullValue()));
		
		
		assertThat(shoppingExtractor,not(equalTo(otherShopping)));
		
		assertThat(shoppingExtractor.hashCode(),not(equalTo(oldShoppingHash)));
		
		assertThat(shoppingExtractor.toString(),not(equalTo(oldShoppingString)));
	}
	
	@Test
	public void testIsValidPagePath() {
		
		// Test loading PHP
		
		assertThat(shoppingExtractor.isValidPagePath(shoppingPath),is(true));
		
		// Test loading HTML
		
		assertThat(shoppingExtractor.isValidPagePath(grapherPath),is(true));
		
		// Test loading CGI
		
		assertThat(shoppingExtractor.isValidPagePath(gamesPath),is(true));
		
		// test loading nothing
		
		assertThat(shoppingExtractor.isValidPagePath(""),is(false));
		
		assertThat(shoppingExtractor.isValidPagePath(localCopyPath+"shopping/incorrect/Shopping.html"),is(false));
		
		// Same for default
		
		// test loading a file that exists, but can't be parsed
		
		assertThat(shoppingExtractor.isValidPagePath(localCopyPath+"gallery/pics/products/pizza.jpg"),is(false));
		
		
		assertThat(defaultExtractor.isValidPagePath(shoppingPath),is(true));
		
		assertThat(defaultExtractor.isValidPagePath(grapherPath),is(true));
		
		assertThat(defaultExtractor.isValidPagePath(gamesPath),is(true));
		
		assertThat(defaultExtractor.isValidPagePath(""),is(false));
		
		assertThat(defaultExtractor.isValidPagePath(localCopyPath+"shopping/incorrect/Shopping.html"),is(false));
		
		assertThat(defaultExtractor.isValidPagePath(localCopyPath+"gallery/pics/products/pizza.jpg"),is(false));
		
		
	}
	
	
	@Test
	public void testSourceToPath() {
		
		File loadedFile;
		
		String srcPath = shoppingExtractor.sourceToPath("http://www.testsite.net/gallery/pics/products/Axle.png");
		
		assertThat(srcPath,is(equalTo("gallery/pics/products/Axle.png")));
		
		loadedFile = new File(localCopyPath+srcPath);
		
		assertThat(loadedFile.exists(),is(true));
		
		srcPath = shoppingExtractor.sourceToPath("https://www.cs.testsite.net/gallery/pics/products/tile.png");
		
		assertThat(srcPath,is(equalTo("gallery/pics/products/tile.png")));
		
		
		
		loadedFile = new File(localCopyPath+srcPath);
		
		assertThat(loadedFile.exists(),is(true));
		
		srcPath = shoppingExtractor.sourceToPath("./Shopping.php");
		
		assertThat(srcPath,is(equalTo("shopping/toys/Shopping.php")));
		
		loadedFile = new File(localCopyPath+srcPath);
		
		assertThat(loadedFile.exists(),is(true));
		
		srcPath = shoppingExtractor.sourceToPath("../pics/pizza.jpg");
		
		assertThat(srcPath,is(equalTo("shopping/pics/pizza.jpg")));
		
		loadedFile = new File(localCopyPath+srcPath);
		
		assertThat(loadedFile.exists(),is(true));
		
		srcPath = shoppingExtractor.sourceToPath("../../gallery/NOPE/products/DOES_NOT_EXIST.jpg");
		
		// Our goal is to translate a path into the proper format, regardless of whether the file exists.
		
		assertThat(srcPath,is(equalTo("gallery/NOPE/products/DOES_NOT_EXIST.jpg")));
		
		loadedFile = new File(localCopyPath+srcPath);
		
		assertThat(loadedFile.exists(),is(false));
		
		srcPath = shoppingExtractor.sourceToPath("https://www.cs.odu.edu/~emulloy/cs350/sshAsst/brick2x4.jpg");
		
		assertThat(srcPath,is(equalTo("https://www.cs.odu.edu/~emulloy/cs350/sshAsst/brick2x4.jpg")));
		
		
	}
	
	@Test
	public void testIs404() {
		
		assertThat(shoppingExtractor.is404(_404path), is(true));
		
		assertThat(shoppingExtractor.is404("src/test/data/testsite/index.html"), is(false));
		
		assertThat(shoppingExtractor.is404("src/test/data/testsite/DOES_NOT_EXIST.html"), is(true));
		
		assertThat(shoppingExtractor.is404("src/test/data/testsite/gallery/pics/products/pizza.jpg"), is(false));
		
		assertThat(shoppingExtractor.is404("src/test/data/testsite/gallery/pics/products/NOEXIST.jpg"), is(true));
		
		assertThat(shoppingExtractor.is404("src/test/data/testsite/gallery/pics/products/zorgenblars/"), is(true));
		
		assertThat(defaultExtractor.is404(_404path), is(true));
		
		assertThat(defaultExtractor.is404("src/test/data/testsite/index.html"), is(false));
		
		assertThat(defaultExtractor.is404("src/test/data/testsite/DOES_NOT_EXIST.html"), is(true));
		
		assertThat(defaultExtractor.is404("src/test/data/testsite/gallery/pics/products/pizza.jpg"), is(false));
		
		assertThat(defaultExtractor.is404("src/test/data/testsite/gallery/pics/products/NOEXIST.jpg"), is(true));
		
		assertThat(defaultExtractor.is404("src/test/data/testsite/gallery/pics/zorgenblars/"), is(true));
		
		
	}
	
	
	@Test
	public void testClone() {
		
		
		HTMLExtractor cloneDefault = (HTMLExtractor)otherDefault.clone();
		
		HTMLExtractor cloneShopping = (HTMLExtractor)otherShopping.clone();
		
		
		assertThat(cloneDefault.getLocalCopyPath(),is(equalTo(defaultExtractor.getLocalCopyPath())));
		
		assertThat(cloneDefault.getPagePath(),is(equalTo(defaultExtractor.getPagePath())));
		
		assertThat(cloneDefault.getURLs(),is(equalTo(defaultExtractor.getURLs())));
		
		assertThat(cloneDefault.getImages(),is(equalTo(defaultExtractor.getImages())));
		
		assertThat(cloneDefault.getCSS(),is(equalTo(defaultExtractor.getCSS())));
		
		assertThat(cloneDefault.getScripts(),is(equalTo(defaultExtractor.getScripts())));
		
		assertThat(cloneDefault.getInternalAnchors(),is(equalTo(defaultExtractor.getInternalAnchors())));
		
		assertThat(cloneDefault.getExternalAnchors(),is(equalTo(defaultExtractor.getExternalAnchors())));
		
		assertThat(cloneDefault.getIntrapageAnchors(),is(equalTo(defaultExtractor.getIntrapageAnchors())));
		
		assertThat(cloneDefault.getResources(),is(equalTo(defaultExtractor.getResources())));
		
		assertThat(cloneDefault.getPage().getExtractedResources(),is(equalTo(defaultExtractor.getResources())));
		
		assertThat(cloneDefault,is(equalTo(defaultExtractor)));
		
		assertThat(cloneDefault.hashCode(),is(equalTo(defaultExtractor.hashCode())));
		
		assertThat(cloneDefault.toString(),is(equalTo(defaultExtractor.toString())));
		
		
		assertThat(otherDefault.getLocalCopyPath(),is(equalTo(defaultExtractor.getLocalCopyPath())));
		
		assertThat(otherDefault.getPagePath(),is(equalTo(defaultExtractor.getPagePath())));
		
		assertThat(otherDefault.getURLs(),is(equalTo(defaultExtractor.getURLs())));
		
		assertThat(otherDefault.getImages(),is(equalTo(defaultExtractor.getImages())));
		
		assertThat(otherDefault.getCSS(),is(equalTo(defaultExtractor.getCSS())));
		
		assertThat(otherDefault.getScripts(),is(equalTo(defaultExtractor.getScripts())));
		
		assertThat(otherDefault.getInternalAnchors(),is(equalTo(defaultExtractor.getInternalAnchors())));
		
		assertThat(otherDefault.getExternalAnchors(),is(equalTo(defaultExtractor.getExternalAnchors())));
		
		assertThat(otherDefault.getIntrapageAnchors(),is(equalTo(defaultExtractor.getIntrapageAnchors())));
		
		assertThat(otherDefault.getResources(),is(equalTo(defaultExtractor.getResources())));
		
		assertThat(otherDefault.getPage(),is(nullValue()));
		
		assertThat(otherDefault,is(equalTo(defaultExtractor)));
		
		assertThat(otherDefault.hashCode(),is(equalTo(defaultExtractor.hashCode())));
		
		assertThat(otherDefault.toString(),is(equalTo(defaultExtractor.toString())));
		
		
		assertThat(cloneShopping.getLocalCopyPath(),is(equalTo(shoppingExtractor.getLocalCopyPath())));
		
		assertThat(cloneShopping.getPagePath(),is(equalTo(shoppingExtractor.getPagePath())));
		
		assertThat(cloneShopping.getURLs(),is(equalTo(shoppingExtractor.getURLs())));
		
		assertThat(cloneShopping.getImages(),is(equalTo(shoppingExtractor.getImages())));
		
		assertThat(cloneShopping.getCSS(),is(equalTo(shoppingExtractor.getCSS())));
		
		assertThat(cloneShopping.getScripts(),is(equalTo(shoppingExtractor.getScripts())));
		
		assertThat(cloneShopping.getInternalAnchors(),is(equalTo(shoppingExtractor.getInternalAnchors())));
		
		assertThat(cloneShopping.getExternalAnchors(),is(equalTo(shoppingExtractor.getExternalAnchors())));
		
		assertThat(cloneShopping.getIntrapageAnchors(),is(equalTo(shoppingExtractor.getIntrapageAnchors())));
		
		assertThat(cloneShopping.getResources(),is(equalTo(shoppingExtractor.getResources())));
		
		assertThat(cloneShopping.getPage().getExtractedResources(),is(equalTo(shoppingExtractor.getResources())));
		
		assertThat(cloneShopping,is(equalTo(shoppingExtractor)));
		
		assertThat(cloneShopping.hashCode(),is(equalTo(shoppingExtractor.hashCode())));
		
		assertThat(cloneShopping.toString(),is(equalTo(shoppingExtractor.toString())));
		
		
		assertThat(otherShopping.getLocalCopyPath(),is(equalTo(shoppingExtractor.getLocalCopyPath())));
		
		assertThat(otherShopping.getPagePath(),is(equalTo(shoppingExtractor.getPagePath())));
		
		assertThat(otherShopping.getURLs(),is(equalTo(shoppingExtractor.getURLs())));
		
		assertThat(otherShopping.getImages(),is(equalTo(shoppingExtractor.getImages())));
		
		assertThat(otherShopping.getCSS(),is(equalTo(shoppingExtractor.getCSS())));
		
		assertThat(otherShopping.getScripts(),is(equalTo(shoppingExtractor.getScripts())));
		
		assertThat(otherShopping.getInternalAnchors(),is(equalTo(shoppingExtractor.getInternalAnchors())));
		
		assertThat(otherShopping.getExternalAnchors(),is(equalTo(shoppingExtractor.getExternalAnchors())));
		
		assertThat(otherShopping.getIntrapageAnchors(),is(equalTo(shoppingExtractor.getIntrapageAnchors())));
		
		assertThat(otherShopping.getResources(),is(equalTo(shoppingExtractor.getResources())));
		
		assertThat(otherShopping.getPage().getExtractedResources(),is(equalTo(shoppingExtractor.getResources())));
		
		assertThat(otherShopping,is(equalTo(shoppingExtractor)));
		
		assertThat(otherShopping.hashCode(),is(equalTo(shoppingExtractor.hashCode())));
		
		assertThat(otherShopping.toString(),is(equalTo(shoppingExtractor.toString())));
		
	}


}

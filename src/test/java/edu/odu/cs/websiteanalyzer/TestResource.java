package edu.odu.cs.websiteanalyzer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;

import edu.odu.cs.websiteanalyzer.Resource;
import edu.odu.cs.websiteanalyzer.Resource.*;


public class TestResource {
	
	
	Resource defaultResource, nonDefaultResource, 
				otherDefault, otherNonDefault,
				page1, page2, page3, image, script;
	
	List<Resource> emptyList, pageList, extractedList;

	@Before
	public void setUp() throws Exception {
		
		emptyList = new ArrayList<Resource>();
		
		defaultResource = new Resource();
		
		otherDefault = new Resource();
		
		page1 = new Resource(Resource.Type.PAGE,
				Resource.Classification.INTERNAL,
				"/directory/gallery/page1.html",
				0.5,emptyList,emptyList);
		
		page2 = new Resource(Resource.Type.PAGE,
				Resource.Classification.INTERNAL,
				"/directory/gallery/page2.html",
				0.5,emptyList,emptyList);
		
		page3 = new Resource(Resource.Type.PAGE,
				Resource.Classification.INTERNAL,
				"/directory/gallery/page3.html",
				0.5,emptyList,emptyList);
		
		pageList = Arrays.asList(page1,page2,page3);
		
		script = new Resource(Resource.Type.SCRIPT,
				Resource.Classification.INTERNAL,
				"scripts/Grapher.js",
				0.50,pageList,emptyList);
		
		image = new Resource(Resource.Type.IMAGE,
				Resource.Classification.INTERNAL,
				"gallery/pics/products.pizza.jpg",
				0.50,pageList,emptyList);
		
		extractedList = Arrays.asList(script,image);
		
		nonDefaultResource = new Resource(Resource.Type.IMAGE,
				Resource.Classification.EXTERNAL,
				"/directory/gallery/image.jpg",
				1.24,pageList,emptyList);
		
		otherNonDefault = new Resource(Resource.Type.IMAGE,
				Resource.Classification.EXTERNAL,
				"/directory/gallery/image.jpg",
				1.24,pageList,emptyList);
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	

	@Test
	public void testDefaultConstructor() {


		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "internal", "0.0")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        thrown.expect(NoSuchElementException.class);
        
        defaultIt.next();
        
		
        assertThat(defaultResource.toString(), is(equalTo(otherDefault.toString())));
		
        assertThat(defaultResource.hashCode(), is(equalTo(otherDefault.hashCode())));
		
        assertThat(defaultResource, is(equalTo(otherDefault)));
		
	}
	
	
	@Test
	public void testNonDefaultConstructor() {
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
        
        thrown.expect(NoSuchElementException.class);
        
        nonDefaultIt.next();
        
        
		assertThat(nonDefaultResource.toString(), not(equalTo(defaultResource.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(defaultResource.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(defaultResource)));
		
		assertThat(nonDefaultResource.toString(), is(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), is(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, is(equalTo(otherNonDefault)));
		
		// Test null Resource.
		Resource nullResource = new Resource(Resource.Type.IMAGE,
				Resource.Classification.EXTERNAL,
				"/directory/gallery/image.jpg",
				1.24,null,null);
		
		assertThat(nullResource.toString(), is(equalTo("image external /directory/gallery/image.jpg 1.24")));
		
		assertThat(nullResource.hashCode(), not(equalTo(nonDefaultResource.hashCode())));
		
		assertThat(nullResource.getPages(), equalTo(null));
	}
	
	@Test
	public void testSetType() {
		
		// Test using the default constructor
		
		defaultResource.setType(Resource.Type.ANCHOR);
		
		assertThat(defaultResource.getType(), is(Resource.Type.ANCHOR));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("anchor", "internal", "0.0")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.IMAGE);
		
		assertThat(defaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.SCRIPT);
		
		assertThat(defaultResource.getType(), is(Resource.Type.SCRIPT));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("script", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.CSS);
		
		assertThat(defaultResource.getType(), is(Resource.Type.CSS));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("css", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.AUDIO);
		
		assertThat(defaultResource.getType(), is(Resource.Type.AUDIO));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("audio", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.VIDEO);
		
		assertThat(defaultResource.getType(), is(Resource.Type.VIDEO));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("video", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.ARCHIVE);
		
		assertThat(defaultResource.getType(), is(Resource.Type.ARCHIVE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("archive", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		defaultResource.setType(Resource.Type.UNCATEGORIZED);
		
		assertThat(defaultResource.getType(), is(Resource.Type.UNCATEGORIZED));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("uncategorized", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, not(equalTo(otherDefault)));
		
		
		// Finally, go back to where we started
		
		defaultResource.setType(Resource.Type.PAGE);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
		
		assertThat(defaultResource.hashCode(), is(equalTo(otherDefault.hashCode())));
		
		assertThat(defaultResource.toString(), is(equalTo(otherDefault.toString())));
		
		assertThat(defaultResource, is(equalTo(otherDefault)));
		
		
		
		
		/* 
		 * 
		 * Test using the non-default constructor
		 * 
		 */
		
		nonDefaultResource.setType(Resource.Type.PAGE);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.ANCHOR);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.ANCHOR));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("anchor", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.SCRIPT);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.SCRIPT));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("script", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.CSS);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.CSS));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("css", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.AUDIO);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.AUDIO));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("audio", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.VIDEO);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.VIDEO));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("video", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.ARCHIVE);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.ARCHIVE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("archive", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.UNCATEGORIZED);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.UNCATEGORIZED));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("uncategorized", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setType(Resource.Type.IMAGE);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
        // And, back to where we started
        
		assertThat(nonDefaultResource.toString(), is(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), is(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, is(equalTo(otherNonDefault)));
	}
	
	@Test
	public void testSetClassification() {
		
		// Start with the default constructor
		
		
		defaultResource.setClassification(Resource.Classification.EXTERNAL);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "external", "0.0")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
        assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
        
        assertThat(defaultResource, not(equalTo(otherDefault)));
        
        defaultResource.setClassification(Resource.Classification.INTRAPAGE);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTRAPAGE));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "intrapage", "0.0")));

		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
        assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
        
        assertThat(defaultResource, not(equalTo(otherDefault)));
        
        defaultResource.setClassification(Resource.Classification.INTERNAL);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "internal", "0.0")));
		
		defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        assertThat(defaultResource.toString(), is(equalTo(otherDefault.toString())));
		
        assertThat(defaultResource.hashCode(), is(equalTo(otherDefault.hashCode())));
        
        assertThat(defaultResource, is(equalTo(otherDefault)));
		
		
		
		/*
		 * 
		 *  Next, the non-default constructor
		 *  
		 *  
		 */
		
        nonDefaultResource.setClassification(Resource.Classification.INTERNAL);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"internal",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		nonDefaultResource.setClassification(Resource.Classification.INTRAPAGE);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.INTRAPAGE));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"intrapage",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		
		nonDefaultResource.setClassification(Resource.Classification.EXTERNAL);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), is(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), is(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, is(equalTo(otherNonDefault)));
	}
	
	@Test
	public void testSetPath() {
		
		// Start with the default constructor
		
	
		defaultResource.setPath("/directory/about/somepage.html");
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("/directory/about/somepage.html")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "internal", "/directory/about/somepage.html", "0.0")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
        assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
        
        assertThat(defaultResource, not(equalTo(otherDefault)));
        
		/*
		 * 
		 *  Next, the non-default constructor
		 *  
		 *  
		 */
		
        nonDefaultResource.setPath("/home/about/something.gif");
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/home/about/something.gif")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"external",
                		"/home/about/something.gif",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
				
				
	}
	
	@Test
	public void testSetFilesize() {
		
		// Start with the default constructor


		defaultResource.setFileSize(10.25);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(10.25));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
	            stringContainsInOrder(Arrays.asList("page", "internal", "10.25")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
	    
	    assertFalse(defaultIt.hasNext());
	    
	    assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
	    assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
	    
	    assertThat(defaultResource, not(equalTo(otherDefault)));
	    
		/*
		 * 
		 *  Next, the non-default constructor
		 *  
		 *  
		 */
		
	    nonDefaultResource.setFileSize(6.30);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(6.30));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
	            stringContainsInOrder(Arrays.asList("image", 
	            		"external",
	            		"/directory/gallery/image.jpg",
	            		"6.30",
	            		"/directory/gallery/page1.html",
	            		"/directory/gallery/page2.html",
	            		"/directory/gallery/page3.html")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	       
	    assertThat(nonDefaultIt.next(), is(page1));
	    
	    assertThat(nonDefaultIt.next(), is(page2));
	    
	    assertThat(nonDefaultIt.next(), is(page3));
	    
	    assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
			
	}
	
	
	@Test
	public void testSetPages() {
		
		
		// Start with the default constructor

		defaultResource.setPages(pageList);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(pageList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
	            stringContainsInOrder(Arrays.asList("page",
	            		"internal",
	            		"0.0",
	            		"/directory/gallery/page1.html",
	            		"/directory/gallery/page2.html",
	            		"/directory/gallery/page3.html")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
	       
	    assertThat(defaultIt.next(), is(page1));
	    
	    assertThat(defaultIt.next(), is(page2));
	    
	    assertThat(defaultIt.next(), is(page3));
	    
	    assertFalse(defaultIt.hasNext());
	    
	    assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
	    assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
	    
	    assertThat(defaultResource, not(equalTo(otherDefault)));
	    
		/*
		 * 
		 *  Next, the non-default constructor
		 *  
		 *  
		 */
		
	    nonDefaultResource.setPages(emptyList);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
	            stringContainsInOrder(Arrays.asList("image","external","/directory/gallery/image.jpg","1.24")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	    
	    assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		
		nonDefaultResource.setPages(null);
		
		assertThat(nonDefaultResource.getPages(), equalTo(null));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		
	}
	
	@Test
	public void testSetExtractedResources() {
		
		
		// Start with the default constructor

		defaultResource.setExtractedResources(extractedList);
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(extractedList)));
		
		assertThat(defaultResource.toString(),
	            stringContainsInOrder(Arrays.asList("page",
	            		"internal",
	            		"0.0",
	            		"scripts/Grapher.js",
	            		"gallery/pics/products.pizza.jpg")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
	    
	    assertFalse(defaultIt.hasNext());
	    
	    assertThat(defaultResource.toString(), not(equalTo(otherDefault.toString())));
		
	    assertThat(defaultResource.hashCode(), not(equalTo(otherDefault.hashCode())));
	    
	    assertThat(defaultResource, not(equalTo(otherDefault)));
	    
		/*
		 * 
		 *  Next, the non-default constructor
		 *  
		 *  
		 */
		
	    nonDefaultResource.setExtractedResources(extractedList);
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(extractedList)));
		
		assertThat(nonDefaultResource.toString(),
	            stringContainsInOrder(Arrays.asList("image","external","/directory/gallery/image.jpg","1.24",
	            		"/directory/gallery/page1.html",
	            		"/directory/gallery/page2.html",
	            		"/directory/gallery/page3.html",
	            		"scripts/Grapher.js",
	            		"gallery/pics/products.pizza.jpg")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
		
		assertThat(nonDefaultIt.next(), is(page1));
	    
	    assertThat(nonDefaultIt.next(), is(page2));
	    
	    assertThat(nonDefaultIt.next(), is(page3));
	    
	    assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), not(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
		
		
		nonDefaultResource.setExtractedResources(null);
		
		assertThat(nonDefaultResource.getExtractedResources(), equalTo(null));
		
		assertThat(nonDefaultResource.toString(), is(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource, not(equalTo(otherNonDefault)));
				
		assertThat(nonDefaultResource.hashCode(), not(equalTo(otherNonDefault.hashCode())));
		
		
	}
	
	@Test
	public void testClone() {
		
		Resource defaultClone = (Resource)defaultResource.clone();
		
		// First, make sure the original copy is fine:
		
		assertThat(defaultResource.getType(), is(Resource.Type.PAGE));
		assertThat(defaultResource.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultResource.getPath(), is(equalTo("")));
		assertThat(defaultResource.getFileSize(), is(0.0));
		assertThat(defaultResource.getPages(), is(equalTo(emptyList)));
		assertThat(defaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultResource.toString(),
                stringContainsInOrder(Arrays.asList("page", "internal", "0.0")));
		
		Iterator<Resource> defaultIt = defaultResource.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        assertThat(defaultResource.toString(),is(equalTo(otherDefault.toString())));
        
        assertThat(defaultResource.hashCode(),is(equalTo(otherDefault.hashCode())));
        
        assertThat(defaultResource,is(equalTo(otherDefault)));
        
        // Next, check the clone:
        
        assertThat(defaultClone.getType(), is(Resource.Type.PAGE));
		assertThat(defaultClone.getClassification(), is(Resource.Classification.INTERNAL));
		assertThat(defaultClone.getPath(), is(equalTo("")));
		assertThat(defaultClone.getFileSize(), is(0.0));
		assertThat(defaultClone.getPages(), is(equalTo(emptyList)));
		assertThat(defaultClone.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(defaultClone.toString(),
                stringContainsInOrder(Arrays.asList("page", "internal", "0.0")));
		
		defaultIt = defaultClone.iterator();
        
        assertFalse(defaultIt.hasNext());
        
        assertThat(defaultClone.toString(),is(equalTo(otherDefault.toString())));
        
        assertThat(defaultClone.hashCode(),is(equalTo(otherDefault.hashCode())));
        
        assertThat(defaultClone,is(equalTo(otherDefault)));
        
        // Same process, but with the non-default resource
		
		Resource nonDefaultClone = (Resource)nonDefaultResource.clone();
		
		assertThat(nonDefaultResource.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultResource.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultResource.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultResource.getFileSize(), is(1.24));
		assertThat(nonDefaultResource.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultResource.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultResource.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		Iterator<Resource> nonDefaultIt = nonDefaultResource.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultResource.toString(), is(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultResource.hashCode(), is(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultResource, is(equalTo(otherNonDefault)));
		
		// Check clone
		
		assertThat(nonDefaultClone.getType(), is(Resource.Type.IMAGE));
		assertThat(nonDefaultClone.getClassification(), is(Resource.Classification.EXTERNAL));
		assertThat(nonDefaultClone.getPath(), is(equalTo("/directory/gallery/image.jpg")));
		assertThat(nonDefaultClone.getFileSize(), is(1.24));
		assertThat(nonDefaultClone.getPages(), is(equalTo(pageList)));
		assertThat(nonDefaultClone.getExtractedResources(), is(equalTo(emptyList)));
		
		assertThat(nonDefaultClone.toString(),
                stringContainsInOrder(Arrays.asList("image", 
                		"external",
                		"/directory/gallery/image.jpg",
                		"1.24",
                		"/directory/gallery/page1.html",
                		"/directory/gallery/page2.html",
                		"/directory/gallery/page3.html")));
		
		nonDefaultIt = nonDefaultClone.iterator();
	       
        assertThat(nonDefaultIt.next(), is(page1));
        
        assertThat(nonDefaultIt.next(), is(page2));
        
        assertThat(nonDefaultIt.next(), is(page3));
        
        assertFalse(nonDefaultIt.hasNext());
		
		assertThat(nonDefaultClone.toString(), is(equalTo(otherNonDefault.toString())));
		
		assertThat(nonDefaultClone.hashCode(), is(equalTo(otherNonDefault.hashCode())));
		
		assertThat(nonDefaultClone, is(equalTo(otherNonDefault)));
		
	}
	

}

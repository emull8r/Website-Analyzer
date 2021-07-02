package edu.odu.cs.websiteanalyzer;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;



public class TestRow {
	
	Row defaultRow;
	Row nonDefaultRow;
	Row otherDefaultRow;
	Row otherNonDefaultRow;
	int oldDefaultHash;
	String oldDefaultString;
	
	int oldNonDefaultHash;
	String oldNonDefaultString;
	
	

	@Before
	public void setUp() throws Exception {
		
		defaultRow = new Row();
		nonDefaultRow = new Row("index",4,1,3,0,8,9);
		
		otherDefaultRow = new Row();
		otherNonDefaultRow = new Row("index",4,1,3,0,8,9);
		
		oldDefaultHash = defaultRow.hashCode();
		oldDefaultString = defaultRow.toString();
		
		oldNonDefaultHash = nonDefaultRow.hashCode();
		oldNonDefaultString = nonDefaultRow.toString();
	}

	@Test
	public void testDefaultConstructor() {
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,is(equalTo(defaultRow)));
		assertThat(defaultRow,is(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),is(equalTo(defaultRow.hashCode())));
		assertThat(defaultRow.toString(), containsString("0"));
	}

	@Test
	public void testNonDefaultConstructor() {
		
		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(defaultRow)));
		assertThat(nonDefaultRow,is(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),is(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "4", "1", "3", "0", "8", "9")));
		assertThat(nonDefaultRow.toString(),is(equalTo(oldNonDefaultString)));
	}

	@Test
	public void testSetPage() {
		
		
		
		defaultRow.setPage("about");
		
		assertThat(defaultRow.getPage(),is(equalTo("about")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("about", "0", "0", "0", "0", "0", "0")));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		
		nonDefaultRow.setPage("about");

		assertThat(nonDefaultRow.getPage(),is(equalTo("about")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("about", "4", "1", "3", "0", "8", "9")));
		
		
		
	}

	@Test
	public void testSetImageCount() {
		

		defaultRow.setImageCount(20);
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(20));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("", "20", "0", "0", "0", "0", "0")));
		
		nonDefaultRow.setImageCount(20);

		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(20));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "20", "1", "3", "0", "8", "9")));
	}

	@Test
	public void testSetCSSCount() {
		
		
		defaultRow.setCSSCount(20);
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(20));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("", "0", "20", "0", "0", "0", "0")));
		
		nonDefaultRow.setCSSCount(20);

		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(20));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "4", "20", "3", "0", "8", "9")));
	}

	@Test
	public void testSetScriptCount() {
		
		defaultRow.setScriptCount(20);
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(20));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("", "0", "0", "20", "0", "0", "0")));
		
		nonDefaultRow.setScriptCount(20);

		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(20));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "4", "1", "20", "0", "8", "9")));
	}

	@Test
	public void testSetIntrasiteLinkCount() {
		
		defaultRow.setIntrapageLinkCount(20);
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(20));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("", "0", "0", "0", "20", "0", "0")));
		
		nonDefaultRow.setIntrapageLinkCount(20);

		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(20));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "4", "1", "3", "20", "8", "9")));
	}

	@Test
	public void testSetIntrapageLinkCount() {
		
		defaultRow.setIntrasiteLinkCount(20);
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(20));
		assertThat(defaultRow.getExternalLinkCount(),is(0));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("", "0", "0", "0", "0", "20", "0")));
		
		nonDefaultRow.setIntrasiteLinkCount(20);

		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(20));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(9));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "4", "1", "3", "0", "20", "9")));
	}

	@Test
	public void testSetExternalLinkCount() {
		
		defaultRow.setExternalLinkCount(20);
		
		assertThat(defaultRow.getPage(),is(equalTo("")));
		assertThat(defaultRow.getImageCount(),is(0));
		assertThat(defaultRow.getCSSCount(),is(0));
		assertThat(defaultRow.getScriptCount(),is(0));
		assertThat(defaultRow.getIntrapageLinkCount(),is(0));
		assertThat(defaultRow.getIntrasiteLinkCount(),is(0));
		assertThat(defaultRow.getExternalLinkCount(),is(20));
		
		assertThat(defaultRow,not(equalTo(otherDefaultRow)));
		assertThat(defaultRow.hashCode(),not(equalTo(oldDefaultHash)));
		assertThat(defaultRow.toString(),not(equalTo(oldDefaultString)));
		assertThat(defaultRow.toString(),
				stringContainsInOrder(Arrays.asList("", "0", "0", "0", "0", "0", "20")));
		
		nonDefaultRow.setExternalLinkCount(20);

		assertThat(nonDefaultRow.getPage(),is(equalTo("index")));
		assertThat(nonDefaultRow.getImageCount(),is(4));
		assertThat(nonDefaultRow.getCSSCount(),is(1));
		assertThat(nonDefaultRow.getScriptCount(),is(3));
		assertThat(nonDefaultRow.getIntrapageLinkCount(),is(0));
		assertThat(nonDefaultRow.getIntrasiteLinkCount(),is(8));
		assertThat(nonDefaultRow.getExternalLinkCount(),is(20));
		
		assertThat(nonDefaultRow,not(equalTo(otherNonDefaultRow)));
		assertThat(nonDefaultRow.hashCode(),not(equalTo(oldNonDefaultHash)));
		assertThat(nonDefaultRow.toString(),not(equalTo(oldNonDefaultString)));
		assertThat(nonDefaultRow.toString(),
                stringContainsInOrder(Arrays.asList("index", "4", "1", "3", "0", "8", "20")));
	}

	@Test
	public void testClone() {
		
		// Clone the default
		Row defaultClone = new Row();
		defaultClone = (Row)defaultRow.clone();
		
		assertThat(defaultClone.getPage(),is(equalTo(defaultRow.getPage())));
		assertThat(defaultClone.getImageCount(),is(equalTo(defaultRow.getImageCount())));
		assertThat(defaultClone.getCSSCount(),is(equalTo(defaultRow.getCSSCount())));
		assertThat(defaultClone.getScriptCount(),is(equalTo(defaultRow.getScriptCount())));
		assertThat(defaultClone.getIntrapageLinkCount(),is(equalTo(defaultRow.getIntrapageLinkCount())));
		assertThat(defaultClone.getIntrasiteLinkCount(),is(equalTo(defaultRow.getIntrasiteLinkCount())));
		assertThat(defaultClone.getExternalLinkCount(),is(equalTo(defaultRow.getExternalLinkCount())));
		
		assertThat(defaultClone,is(equalTo(defaultRow)));
		assertThat(defaultClone.hashCode(),is(equalTo(defaultRow.hashCode())));
		assertThat(defaultClone.toString(),is(equalTo(defaultRow.toString())));
		
		
		// Clone the non-default
		Row nonDefaultClone = new Row();
		nonDefaultClone = (Row)nonDefaultRow.clone();
		
		assertThat(nonDefaultClone.getPage(),is(equalTo(nonDefaultRow.getPage())));
		assertThat(nonDefaultClone.getImageCount(),is(equalTo(nonDefaultRow.getImageCount())));
		assertThat(nonDefaultClone.getCSSCount(),is(equalTo(nonDefaultRow.getCSSCount())));
		assertThat(nonDefaultClone.getScriptCount(),is(equalTo(nonDefaultRow.getScriptCount())));
		assertThat(nonDefaultClone.getIntrapageLinkCount(),is(equalTo(nonDefaultRow.getIntrapageLinkCount())));
		assertThat(nonDefaultClone.getIntrasiteLinkCount(),is(equalTo(nonDefaultRow.getIntrasiteLinkCount())));
		assertThat(nonDefaultClone.getExternalLinkCount(),is(equalTo(nonDefaultRow.getExternalLinkCount())));
		
		assertThat(nonDefaultClone,is(equalTo(nonDefaultRow)));
		assertThat(nonDefaultClone.hashCode(),is(equalTo(nonDefaultRow.hashCode())));
		assertThat(nonDefaultClone.toString(),is(equalTo(nonDefaultRow.toString())));
		
	}

}

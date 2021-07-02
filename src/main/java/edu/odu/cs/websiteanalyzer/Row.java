package edu.odu.cs.websiteanalyzer;

/**
 * 
 * The information that would be displayed in the row of a spreadsheet.
 * 
 * @author Evan Mulloy
 * 
 */

public class Row implements Cloneable {
	
	/**
	 * The path of the page.
	 * 
	 */
	private String page;
	
	/**
	 * The number of images embedded on the page.
	 * 
	 */
	private int images;
	
	/**
	 * The number of CSS files embedded on the page.
	 * 
	 */
	private int css;
	
	/**
	 * The number of JavaScript files embedded on the page.
	 * 
	 */
	private int scripts;
	
	/**
	 * The number of Intra-page links.
	 * 
	 */
	private int intrapage;
	
	/**
	 * The number of Internal links.
	 * 
	 */
	private int intrasite;
	
	/**
	 * The number of External links.
	 * 
	 */
	private int external;
	
	

	/**
	 * 
	 * The default constructor.
	 * By default, the page name is "", and every int variable is 0.
	 * 
	 * 
	 */
	public Row() {
		
		page = "";
		images = 0;
		css = 0;
		scripts = 0;
		intrapage = 0;
		intrasite = 0;
		external = 0;
		
	}
	
	/**
	 * The non-default constructor.
	 * 
	 * 
	 * @param page The name of a page.
	 * @param images The number of image tags on the page.
	 * @param css The number of stylesheet tags on the page. 
	 * @param scripts The number of script tags on the page. 
	 * @param intrapage The number of intra-page links on the page. 
	 * @param intrasite The number of intra-site (AKA internal) links on the page. 
	 * @param external The number of external links on the page. 
	 */
	public Row(String page, int images, int css, int scripts, int intrapage, int intrasite, int external) {
		
		this.page = page;
		this.images = images;
		this.css = css;
		this.scripts = scripts;
		this.intrapage = intrapage;
		this.intrasite = intrasite;
		this.external = external;
	}
	
	
	/**
	 * Sets the page name.
	 * 
	 * @param page The new name of the page.
	 */
	public void setPage(String page) {
		
		this.page = page;
		
	}
	
	/**
	 * Sets the image count.
	 * 
	 * @param images The new number of image tags on the page.
	 */
	public void setImageCount(int images) {

		this.images = images;
		
	}
	
	/**
	 * Sets the CSS count.
	 * 
	 * @param css The new number of stylesheet tags on the page.
	 */
	public void setCSSCount(int css) {
			
		this.css = css;
		
	}

	/**
	 * Sets the Script count.
	 * 
	 * @param scripts The new number of script tags on the page.
	 */
	public void setScriptCount(int scripts) {
		
		this.scripts = scripts;
		
	}
	
	/**
	 * Sets the intra-site link count.
	 * 
	 * @param intrasite The number of intra-site (AKA internal) links on the page. 
	 */
	public void setIntrasiteLinkCount(int intrasite) {
		
		this.intrasite = intrasite;
		
	}
	
	/**
	 * Sets the intra-page link count.
	 * 
	 * @param intrapage The new number of intra-page links on the page. 
	 */
	public void setIntrapageLinkCount(int intrapage) {
		
		this.intrapage = intrapage;
		
	}
	
	/**
	 * Sets the external link count.
	 * 
	 * @param external The new number of external links on the page. 
	 */
	public void setExternalLinkCount(int external) {
	
		this.external = external;
		
	}
	
	
	/**
	 * Gets the page.
	 * 
	 * @return page The name of the page.
	 */
	public String getPage() {
		
		return page;
	}
	
	/**
	 * Gets the image count.
	 * 
	 * @return images The number of image tags on the page.
	 */
	public int getImageCount() {
		
		return images;
	}
	
	/**
	 * Gets the CSS count.
	 * 
	 * @return css The number of stylesheet tags on the page.
	 */
	public int getCSSCount() {
			
		return css;
	}

	/**
	 * Gets the script count.
	 * 
	 * @return scripts The number of script tags on the page.
	 */
	public int getScriptCount() {
		
		return scripts;
	}
	
	/**
	 * Gets the intra-site link count.
	 * 
	 * @return The number of intra-site links on the page.
	 */
	public int getIntrasiteLinkCount() {
		
		return intrasite;
	}
	
	/**
	 * Gets the intra-page link count.
	 * 
	 * @return The number of intra-page links.
	 */
	public int getIntrapageLinkCount() {
		
		return intrapage;
	}
	
	/**
	 * Gets the external link count.
	 * 
	 * @return external The number of external links on the page.
	 */
	public int getExternalLinkCount() {
		
		return external;
	}
	
	
	 
	/**
	 * 
	 * Clones a Row.
	 * 
	 * @return A copy of the Row clone() was called on.
	 */
    @Override
    public Object clone()
    {
        return new Row(page,images,css,scripts,intrapage,intrasite,external);
    }

    /**
     * 
     * Compares two Spreadsheets.
     * Two Spreadsheets are considered equal if they have the same page name,
     * image count, script count, CSS count, intra-page link count,
     * internal link count, and external link count.
     * 
     * @param rhs Another spreadsheet.
     * 
     */
    @Override
    public boolean equals(Object rhs)
    {
        Row other = (Row)rhs;
        
        return (other.getPage() == page &&
        		other.getImageCount() == images &&
        		other.getCSSCount() == css &&
        		other.getScriptCount() == scripts &&
        		other.getIntrapageLinkCount() == intrapage &&
        		other.getIntrasiteLinkCount() == intrasite &&
        		other.getExternalLinkCount() == external);
        		
    }


    /**
     * Gets the hashcode for the row.
     * Each hashcode is computed as being the hashcode of the page name, plus thrice the image count,
     * plus four times the CSS count, plus five times the script count, plus twice the intra-page link count,
     * plus seven times the internal link count, plus six times the external link count.
     * 
     * @return The hashcode.
     */
    @Override
    public int hashCode()
    {
    	return page.hashCode()+(3*images)+(4*css)+(5*scripts)+(2*intrapage)+(7*intrasite)+(6*external);
    }
    
    /**
     *  Gets a representative String in the following format:
     *  "[page name] #images #CSS #scripts #intrapage #intrasite #external"
     *  An example using this format would be "about.html 4 1 2 5 10 6".
     * 
     * 
     *  @return A representative String.
     *  
     */
    @Override
    public String toString()
    {
        return ""+page+" "+images+" "+css+" "+scripts+" "+intrapage+" "+intrasite+" "+external;
    }
	
}
# Website Analyzer

Website Analyzer is a Java-based offline web scraper.
An offshoot of the CS 350 Summer 2019 semester project, Website Analyzer traverses the directory of the
local copy of a website to analyze the use of static content within each HTML document,
and produces an Excel spreadsheet that documents the overall analysis for each page.
The spreadsheet contains a row for each page, and lists the number of embedded images, non-inline stylesheets, non-inline scripts, internal links, intrapage links, and external links found within each page.

<b>HOW TO USE</b>

WebsiteAnalyzer.jar is run via command line.
For parameters, provide the path to a local copy of a website, followed by one or more URLs that are used by the website (e.g., http://www.cs.odu.edu).

Upon completion of its analysis, Website Analyzer will produce 1 Excel spreadsheet located in the same directory as the .jar. The name of the file will take the format YYYYMMDD-HHMMSS-summary.xlsx, where YYYY is the year, MM is the month, DD the day, HH the hour, MM the minute, and SS the second of when the file was written.

<b>SYSTEM REQUIREMENTS</b>

The user's operating system must have a JRE that supports Java 8.

package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

import org.junit.Assert;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	final static ArrayList<String> urlsVisited = new ArrayList<String>();


	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started
       

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		String targetUrl = "https://en.wikipedia.org/wiki/Philosophy";
		
		
		if(getToPhilosophy(url,urlsVisited)) {
			System.out.println("Game complete!");
		} else {
			System.out.println("You lose, chump");
		}

	
       
	}

	public static boolean getToPhilosophy(String url, ArrayList<String> urlsVisited) throws IOException {
			urlsVisited.add(url);
			System.out.println(url);
			Elements paragraphs = wf.fetchWikipedia(url);
			int counter = 0;
			while(counter < paragraphs.size()) {
				Element paragraph = paragraphs.get(counter);
				Iterable<Node> iter = new WikiNodeIterable(paragraph);
				for (Node node: iter) {
					if (node instanceof Element) {


						Element elt = (Element) node;
						Element parent = elt.parent();
						String link = node.attr("href");
						String nextLink = "https://en.wikipedia.org" + link;
						if(elt.tagName().equalsIgnoreCase("a") 
						&& !parent.tagName().equalsIgnoreCase("i") 
						&& !parent.tagName().equalsIgnoreCase("em") 
						&& !elt.hasClass("external") 
						&& !Character.isUpperCase(elt.text().charAt(0))
						&& !elt.attr("href").startsWith("#") 
						&& !elt.attr("href").startsWith("/wiki/Help:")
						&& !elt.absUrl("href").contains("redlink")) {

							if(link.equals("/wiki/Philosophy")) {
								System.out.println(nextLink);
								return true;
							
							} else {
								counter = paragraphs.size()+1;
								return getToPhilosophy(nextLink, urlsVisited);
							}
						}


					}
				}
				counter++;
			}
			return false;
		}

	// public void getToPhilosophy(String url) throws IOException {
	// 	String currentUrl = url;
	// 	Boolean gameOver = false;
	// 	System.out.println(currentUrl);

	// 	while (!gameOver) {

	// 		Elements paragraphs = wf.fetchWikipedia(""+currentUrl+"");
	// 		Element firstPara = paragraphs.get(0);

	// 		if (!urlsVisited.contains(currentUrl)) {
	// 			urlsVisited.add(currentUrl);
	// 		} else {
	// 			System.out.println("Entered a loop. Game cannot be finished.");
	// 			gameOver = true;
	// 			break;
	// 		}
	// 		System.out.println(currentUrl);
	// 		//Assert.assertEquals(url, currentUrl);
			
			
	// 		Iterable<Node> iter = new WikiNodeIterable(firstPara);
	// 		for (Node node: iter) {
	// 			if (node instanceof Element) {
	// 				Element link = null;
	// 				if (testLink((Element) node)) {
	// 					link = (Element) node;
	// 				}
	// 				if (link != null) {
	// 					currentUrl = link.absUrl("href");
	// 					break;
	// 				}
	// 			}
	// 		}
			
	// 		// If currentUrl is not updated then no valid links were found
	// 		if (currentUrl.equals(url)) {
	// 			System.out.println("There were no links on this page. GG");
	// 			gameOver = true;
	// 		}
	// 		// checks for win state
	// 		if (currentUrl.equals("https://en.wikipedia.org/wiki/Philosophy")) {
	// 			System.out.println("Ya won! Arrived at Philosophy");
	// 			gameOver = true;
	// 		}

	// 	}
			
			
	// }

		// checks a few conditions for links
		// public static boolean testLink (Element elt) {
		// 	Element parent = elt.parent();

		// 	return (!elt.tagName().equalsIgnoreCase("a") 
		// 		&& !parent.tagName().equalsIgnoreCase("i") 
		// 		&& !parent.tagName().equalsIgnoreCase("em") 
		// 		&& !elt.hasClass("external") 
		// 		&& !Character.isUpperCase(elt.text().charAt(0)));
		// }
}

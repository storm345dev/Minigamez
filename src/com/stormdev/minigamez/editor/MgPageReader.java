package com.stormdev.minigamez.editor;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class MgPageReader {

	public static String toHtml(String doc, JEditorPane pane){
		doc = doc.replaceAll(Pattern.quote("}"), "</div>");
    	doc = doc.replaceAll(Pattern.quote("{"), "<div class='content'>");
    	doc = "<style type='text/css'>body{font-family:Calibri,Veranda,Arial;font-size:16pt;background-image:url('https://dl.dropboxusercontent.com/u/50672767/minigamez/site/iron_block.png')}h1{font-size:30pt;}h2{font-size:20pt;}.content{background-image:url('https://dl.dropboxusercontent.com/u/50672767/minigamez/site/translucent.png')}a{color:#D4A20F;}</style>"+doc;
    	doc = doc.replaceAll("¬", "&nbsp;");
    	doc = doc.replaceAll(System.getProperty("line.separator"), "<br>");
    	doc = doc.replaceAll(Pattern.quote("\\"), "<br>");
    	doc = doc.replaceAll("<big>", "<h1>");
    	doc = doc.replaceAll("</big>", "</h1>");
    	doc = doc.replaceAll("<med>", "<h2>");
    	doc = doc.replaceAll("</med>", "</h2>");
    	doc = doc.replaceAll("<para>", "<p>");
    	doc = doc.replaceAll("</para>", "</p>");
    	doc = doc.replaceAll("<small>", "<h5>");
    	doc = doc.replaceAll("</small>", "</h5>");
    	doc = doc.replaceAll(Pattern.quote("/**"), "</strong>");
    	doc = doc.replaceAll(Pattern.quote("**"), "<strong>");
    	doc = doc.replaceAll(Pattern.quote("/--"), "</s>");
    	doc = doc.replaceAll(Pattern.quote("--"), "<s>");
    	doc = doc.replaceAll(Pattern.quote("/~~"), "</em>");
    	doc = doc.replaceAll(Pattern.quote("~~"), "<em>");
    	final Desktop desktop = Desktop.getDesktop();
    	pane.addHyperlinkListener(new HyperlinkListener() {

    	    public void hyperlinkUpdate(HyperlinkEvent hle) {
    	        if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
    	            try {
    	                System.out.println(hle.getURL());;
    	                try {
    	                    desktop.browse(new URI(hle.getURL().toString()));
    	                } catch (URISyntaxException ex) {
    	                    System.out.println("ERROR: Malformed URL");
    	                }
    	            } catch (IOException ex) {
    	                ex.printStackTrace();
    	            }

    	        }
    	    }
    	});
    	return doc;
	}

}

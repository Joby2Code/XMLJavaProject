package demo.xmlparsee;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;
import java.io.*;
public class JAXPSAXParser extends DefaultHandler{
	static String textToDisplay[] = new String[1000];
	static int nTextLines = 0;
	static String indent = "";
	public static void parseDocument(String filename) {
		JAXPSAXParser SAXEventHandler = new JAXPSAXParser();
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		XMLReader xmlReader = null;
		try {
			SAXParser saxParser = parserFactory.newSAXParser();
			xmlReader = saxParser.getXMLReader();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(1);
		}
		xmlReader.setContentHandler(SAXEventHandler);
		xmlReader.setErrorHandler(SAXEventHandler);
		try {
			String path = new File(filename).getAbsolutePath();
			xmlReader.parse("file:" + path);
		} catch (SAXException saxE) {
			System.err.println(saxE.getMessage());
			System.exit(1);
		} catch (IOException ioE) {
			System.err.println(ioE);
			System.exit(1);
		}
	}
	public void startDocument() {
		textToDisplay[nTextLines] = indent +"<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		nTextLines++;
	}
	public void startElement(String filename, String localName, String qualifiedName, Attributes attributes) {
		textToDisplay[nTextLines] = indent + "<" + qualifiedName;
		indent += " ";
		if (attributes != null) {
			int numberAttributes = attributes.getLength();
			for (int loopIndex = 0; loopIndex < numberAttributes;loopIndex++){
				textToDisplay[nTextLines] += " ";
				textToDisplay[nTextLines] +=attributes.getLocalName(loopIndex);
				textToDisplay[nTextLines] += "=\"";
				textToDisplay[nTextLines] +=attributes.getValue(loopIndex);
				textToDisplay[nTextLines] += "\"";
			}
		}
		textToDisplay[nTextLines] += ">";
		nTextLines++;
	}
	public void characters(char textChars[], int textStart, int textLength) {
		String textData = (new String(textChars, textStart,textLength));
		textData = textData.trim();
		if(textData.length() > 0 && textData.indexOf("\n") < 0) {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += textData;
			nTextLines++;
		}
	}
	public void endElement(String filename, String localName, String qualifiedName) {
		indent = indent.substring(0, indent.length() - 1);
		textToDisplay[nTextLines] = indent + "</"+ qualifiedName + ">";
		nTextLines++;
	}
	public void processingInstruction(String PIName, String PIData) {
		textToDisplay[nTextLines] = indent + "<?" + PIName;
		if (PIData != null && PIData.length() > 0) {
			textToDisplay[nTextLines] += " ";
			textToDisplay[nTextLines] += PIData;
		}
		textToDisplay[nTextLines] += "?>";
		nTextLines++;
	}
	public void warning(SAXParseException e) {
		System.err.println("SAX warning: " +e.getMessage());
	}
	public void error(SAXParseException e) {
		System.err.println("SAX error: " +e.getMessage());
		System.exit(1);
	}
	public void fatalError(SAXParseException e) {
		System.err.println("Fatal SAX error: " +e.getMessage());
		System.exit(1);
	}
	public static void main(String args[]) {
		parseDocument("customers.xml");
		for(int index = 0; index < nTextLines; index++){
			System.out.println(textToDisplay[index]);
		}
	}
}
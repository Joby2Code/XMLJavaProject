package demo.xmlparsee;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import org.apache.xerces.parsers.SAXParser;
public class XML4JSAXParser extends DefaultHandler{
	static String textToDisplay[] = new String[1000];
	static int nTextLines = 0;
	static String indent = "";
	public static void parseDocument(String filename){
		try {
		XML4JSAXParser SAXEventHandler = new XML4JSAXParser();
		SAXParser parser = new SAXParser();
		parser.setContentHandler(SAXEventHandler);
		parser.setErrorHandler(SAXEventHandler);
		parser.parse(filename);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	public void startDocument(){
		textToDisplay[nTextLines] = indent +"<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		nTextLines++;
	}
	public void startElement(String filename, String localName, String qualifiedName, Attributes attributes){
		textToDisplay[nTextLines] = indent + "<" + qualifiedName;
		indent += " ";
		if (attributes != null) {
			int numberAttributes = attributes.getLength();
			for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++){
				textToDisplay[nTextLines] += " ";
				textToDisplay[nTextLines] += attributes.getLocalName(loopIndex);
				textToDisplay[nTextLines] += "=\"";
				textToDisplay[nTextLines] +=attributes.getValue(loopIndex);
				textToDisplay[nTextLines] += "\"";
			}
		}
		textToDisplay[nTextLines] += ">";
		nTextLines++;
	}
	public void characters(char textChars[], int textStart, int textLength){
		String textData = (new String(textChars, textStart, textLength));
		textData = textData.trim();
		if(textData.length() > 0 && textData.indexOf("\n") < 0) {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += textData;
			nTextLines++;
		}
	}
	public void endElement(String filename, String localName, String qualifiedName){
		indent = indent.substring(0, indent.length() - 1);
		textToDisplay[nTextLines] = indent + "</"+ qualifiedName + ">";
		nTextLines++;
	}
	public void processingInstruction(String PIName, String PIData){
		textToDisplay[nTextLines] = indent + "<?" + PIName;
		if (PIData != null && PIData.length() > 0) {
			textToDisplay[nTextLines] += " ";
			textToDisplay[nTextLines] += PIData;
		}
		textToDisplay[nTextLines] += "?>";
		nTextLines++;
	}
	public void warning(SAXParseException e){
		System.err.println("SAX warning: " + e.getMessage());
	}
	public void error(SAXParseException e){
		System.err.println("SAX error: " + e.getMessage());
		System.exit(1);
	}
	public void fatalError(SAXParseException e){
		System.err.println("Fatal SAX error: " +e.getMessage());
		System.exit(1);
	}
	public static void main(String args[]){
		parseDocument(args[0]);
		for(int index = 0; index < nTextLines; index++){
			System.out.println(textToDisplay[index]);
		}
	}
}
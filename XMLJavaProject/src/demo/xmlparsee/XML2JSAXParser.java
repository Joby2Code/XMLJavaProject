package demo.xmlparsee;

import org.*;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XML2JSAXParser extends DefaultHandler {
	static String textToDisplay[] = new String[100];
	static int nTextLines = 0;
	static String indent = "";

	

	public static void parseDocument(String filename) {
		try {
			XML2JSAXParser SAXEventHandler=new XML2JSAXParser();/*The object is passed to SAX parser to know what objects call back methods to use.*/
			SAXParser parser=new SAXParser();
			parser.setContentHandler(SAXEventHandler);//Used to access the class methods when the parser encounters a event.
			parser.setErrorHandler(SAXEventHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}

	public void startDocument() {
		textToDisplay[nTextLines] = indent
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		nTextLines++;
	}
	public void startElement(String filename, String localName,
			String qualifiedName, Attributes attributes) {
		textToDisplay[nTextLines] = indent + "<" + qualifiedName;
		indent += " ";
		if (attributes != null) {
			int numberAttributes = attributes.getLength();
			for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++) {
				textToDisplay[nTextLines] += " ";
				textToDisplay[nTextLines] += attributes.getLocalName(loopIndex);
				textToDisplay[nTextLines] += "=\"";
				textToDisplay[nTextLines] += attributes.getValue(loopIndex);
				textToDisplay[nTextLines] += "\"";
			}
		}
		textToDisplay[nTextLines] += ">";
		nTextLines++;
	}
	public void characters(char textChars[], int textStart, int textLength) {//textChars[] has all the elements stored as character in the main method
		String textData = (new String(textChars, textStart, textLength));
		textData = textData.trim();
		if (textData.length() > 0 && textData.indexOf("\n") < 0) {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += textData;
			nTextLines++;
		}
	}
	public static void main(String args[]) {
		parseDocument("customers.xml");
		for (int index = 0; index < nTextLines; index++) {
			System.out.println(textToDisplay[index]);
		}
	}
}

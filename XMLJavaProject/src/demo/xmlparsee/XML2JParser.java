package demo.xmlparsee;

import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.XMLParser;

public class XML2JParser {
	static String textToDisplay[] = new String[1000];
	static int nTextLines = 0;

	public static void parseDocument(String filename) {
		try {
			DOMParser parser = new DOMParser();// Object to parse the document
			parser.parse(filename);// To parse the xml doc
			Document doc = parser.getDocument();// Used to get the elements from
												// the parsed doc
			createDisplay(doc, "");
			Element e = doc.getDocumentElement();
			System.out.println(e.toString());

			/*
			 * XMLParser xmlParser=new XMLParser(); xmlParser.
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createDisplay(Node doc, String indent) {
		// TODO Auto-generated method stub
		if (doc == null) {
			return;
		}
		int type = doc.getNodeType();
		System.out.println("Type: " + type);
		switch (type) {
		case Node.DOCUMENT_NODE: {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += "<?xml version=\"1.0\" encoding=\""
					+ "UTF-8" + "\"?>";
			nTextLines++;
			createDisplay(((Document) doc).getDocumentElement(), "");
			break;
		}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseDocument("customers.xml");
		for (int i = 0; i < nTextLines; i++) {
			System.out.println(textToDisplay[i]);
		}

	}

}

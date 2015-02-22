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
			// System.out.println("The element is" + e.toString());

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
		// System.out.println("Type: ");
		switch (type) {
		case Node.DOCUMENT_NODE: { // To get the document node.
			textToDisplay[nTextLines] = indent;// Used to adjust the space and
												// display the xml document
			// System.out.println("Indent "+textToDisplay[0]);
			textToDisplay[nTextLines] += "<?xml version=\"1.0\" encoding=\""
					+ "UTF-8" + "\"?>";
			nTextLines++;
			createDisplay(((Document) doc).getDocumentElement(), "");
			break;
		}
		case Node.ELEMENT_NODE: {// To get the element node and the attributes
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += "<";
			textToDisplay[nTextLines] += doc.getNodeName();
			int numberAttributes = 0;
			if (doc.getAttributes() != null)
				numberAttributes = doc.getAttributes().getLength();
			for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++) {
				Attr attribute = (Attr) doc.getAttributes().item(loopIndex);
				textToDisplay[nTextLines] += " ";
				textToDisplay[nTextLines] += attribute.getNodeName();
				textToDisplay[nTextLines] += "=\"";
				textToDisplay[nTextLines] += attribute.getNodeValue();
				textToDisplay[nTextLines] += "\"";
			}
			textToDisplay[nTextLines] += ">";
			nTextLines++;
			nTextLines++;
			NodeList childNodes = doc.getChildNodes();//
			if (childNodes != null) {
				int numberChildNodes = childNodes.getLength();
				indent += " ";
				for (int loopIndex = 0; loopIndex < numberChildNodes; loopIndex++) {
					createDisplay(childNodes.item(loopIndex), indent);
				}
			}
			break;
		}
		case Node.TEXT_NODE: {// To get the value of the element nodes.
			textToDisplay[nTextLines] = indent;
			String nodeText = doc.getNodeValue().trim();
			if (nodeText.indexOf("\n") < 0 && nodeText.length() > 0) {
				textToDisplay[nTextLines] += nodeText;
				nTextLines++;
			}
			break;

		}
		case Node.PROCESSING_INSTRUCTION_NODE: {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += "<?";
			textToDisplay[nTextLines] += doc.getNodeName();
			String PItext = doc.getNodeValue();
			if (PItext != null && PItext.length() > 0) {
				textToDisplay[nTextLines] += PItext;
			}
			textToDisplay[nTextLines] += "?>";
			nTextLines++;
			break;
		}
		}
		if (type == Node.ELEMENT_NODE) {//To add the closing element
			textToDisplay[nTextLines] = indent
					.substring(0, indent.length() - 1);
			textToDisplay[nTextLines] += "</";
			textToDisplay[nTextLines] += doc.getNodeName();
			textToDisplay[nTextLines] += ">";
			nTextLines++;
			indent += " ";
		}
	}

	// createDisplay(((Document) doc).getDocumentElement(), "");

	public static void main(String[] args) {// To display the xml document aftr
											// reading the document.
		// TODO Auto-generated method stub
		parseDocument("customers.xml");
		for (int i = 0; i < nTextLines; i++) {
			System.out.println(textToDisplay[i]);
		}

	}

}

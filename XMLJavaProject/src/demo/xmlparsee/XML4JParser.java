package demo.xmlparsee;

import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;

public class XML4JParser {
	static String textToDisplay[] = new String[1000];
	static int nTextLines = 0;

	public static void parseDocument(String filename) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(filename);
			Document document = parser.getDocument();
			createDisplay(document, "");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public static void createDisplay(Node node, String indent) {
		if (node == null) {
			return;
		}
		int type = node.getNodeType();
		switch (type) {

		case Node.DOCUMENT_NODE: {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += "<?xml version=\"1.0\" encoding=\""
					+ "UTF-8" + "\"?>";
			nTextLines++;
			createDisplay(((Document) node).getDocumentElement(), "");
			break;
		}
		case Node.ELEMENT_NODE: {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += "<";
			textToDisplay[nTextLines] += node.getNodeName();
			int numberAttributes = 0;
			if (node.getAttributes() != null)
				numberAttributes = node.getAttributes().getLength();
			for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++) {
				Attr attribute = (Attr) node.getAttributes().item(loopIndex);
				textToDisplay[nTextLines] += " ";
				textToDisplay[nTextLines] += attribute.getNodeName();
				textToDisplay[nTextLines] += "=\"";
				textToDisplay[nTextLines] += attribute.getNodeValue();
				textToDisplay[nTextLines] += "\"";
			}
			textToDisplay[nTextLines] += ">";
			nTextLines++;
			NodeList childNodes = node.getChildNodes();
			if (childNodes != null) {
				int numberChildNodes = childNodes.getLength();
				indent += " ";
				for (int loopIndex = 0; loopIndex < numberChildNodes; loopIndex++) {
					createDisplay(childNodes.item(loopIndex), indent);
				}
			}
			break;
		}
		case Node.TEXT_NODE: {
			textToDisplay[nTextLines] = indent;
			String nodeText = node.getNodeValue().trim();
			if (nodeText.indexOf("\n") < 0 && nodeText.length() > 0) {
				textToDisplay[nTextLines] += nodeText;
				nTextLines++;
			}
			break;
		}
		case Node.PROCESSING_INSTRUCTION_NODE: {
			textToDisplay[nTextLines] = indent;
			textToDisplay[nTextLines] += "<?";
			textToDisplay[nTextLines] += node.getNodeName();
			String PItext = node.getNodeValue();
			if (PItext != null && PItext.length() > 0) {
				textToDisplay[nTextLines] += PItext;
			}
			textToDisplay[nTextLines] += "?>";
			nTextLines++;
			break;
		}
		}
		if (type == Node.ELEMENT_NODE) {
			textToDisplay[nTextLines] = indent
					.substring(0, indent.length() - 1);
			textToDisplay[nTextLines] += "</";
			textToDisplay[nTextLines] += node.getNodeName();
			textToDisplay[nTextLines] += ">";
			nTextLines++;
			indent += " ";
		}
	}

	public static void main(String args[]) {
		parseDocument("customers.xml");
		for (int loopIndex = 0; loopIndex < nTextLines; loopIndex++) {
			System.out.println(textToDisplay[loopIndex]);
		}
	}
}

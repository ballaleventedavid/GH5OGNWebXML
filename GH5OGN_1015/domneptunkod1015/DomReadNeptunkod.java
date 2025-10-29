package domneptunkod1015;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomReadNeptunkod {
    public static void main(String[] args) {
        String xmlPath = "GH5OGN_orarend.xml";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setNamespaceAware(false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlPath));
            document.getDocumentElement().normalize();

            System.out.println("Blokk formájú kiírás: \n");
            DOMRead.printDocument(document);
        } catch (Exception e) {
            System.err.println("Hiba az XML beolvasásakor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class DOMRead {
    public static void printDocument(Document doc) {
        Node root = doc.getDocumentElement();
        printNode(root, 0);
    }

    private static void printNode(Node node, int indent) {
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                printIndent(indent);
                System.out.print("<" + node.getNodeName());

                // attribútumok
                NamedNodeMap attrs = node.getAttributes();
                if (attrs != null) {
                    for (int i = 0; i < attrs.getLength(); i++) {
                        Node a = attrs.item(i);
                        System.out.print(" " + a.getNodeName() + "=\"" + a.getNodeValue() + "\"");
                    }
                }
                System.out.println(">");

                // gyerekek és szöveg
                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        String text = child.getTextContent();
                        if (text != null) {
                            String trimmed = text.trim();
                            if (!trimmed.isEmpty()) {
                                printIndent(indent + 1);
                                System.out.println(trimmed);
                            }
                        }
                    } else {
                        printNode(child, indent + 1);
                    }
                }

                printIndent(indent);
                System.out.println("</" + node.getNodeName() + ">");
                break;

            case Node.DOCUMENT_NODE:
                // dokumentum gyökér továbbléptetése
                printNode(((Document) node).getDocumentElement(), indent);
                break;

            default:
                // egyéb node típusokat (CDATA, PROCESSING_INSTRUCTION stb.) opcionálisan kezelhetünk
                break;
        }
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("    ");
        }
    }
}



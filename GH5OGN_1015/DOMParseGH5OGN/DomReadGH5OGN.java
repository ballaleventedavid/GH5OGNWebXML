package domGH5OGN1015;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomParseGH5OGN {
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("GH5OGN_orarend.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("ora");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            System.out.println("\nAktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String oid = elem.getAttribute("id");
                String tipus = elem.getAttribute("tipus");

                String targy = getText(elem, "targy");
                Element idopont = (Element) elem.getElementsByTagName("idopont").item(0);
                String nap = getText(idopont, "nap");
                String tol = getText(idopont, "tol");
                String ig = getText(idopont, "ig");
                String helyszin = getText(elem, "helyszin");
                String oktato = getText(elem, "oktato");
                String szak = getText(elem, "szak");

                System.out.println("Óra id: " + oid);
                System.out.println("Típus: " + tipus);
                System.out.println("Tárgy: " + targy);
                System.out.println("Időpont: " + nap + " " + tol + "-" + ig);
                System.out.println("Helyszín: " + helyszin);
                System.out.println("Oktató: " + oktato);
                System.out.println("Szak: " + szak);
            }
        }
    }

    private static String getText(Element parent, String tagName) {
        Node node = parent.getElementsByTagName(tagName).item(0);
        return node != null ? node.getTextContent() : "";
    }
}



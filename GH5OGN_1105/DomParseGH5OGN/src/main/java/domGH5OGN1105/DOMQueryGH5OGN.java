package domgh5ogn1105;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMQueryGH5OGN {

    public static void main(String[] args) {

        try {
            // XML fájl beolvasása
            File inputFile = new File("GH5OGNhallgato.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Gyökér elem kiírása
            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            // 'hallgato' elemek listázása
            NodeList hallgatoNodeList = doc.getElementsByTagName("hallgato");

            // Végigiterálás az összes 'hallgato' elemen
            for (int i = 0; i < hallgatoNodeList.getLength(); i++) {
                Node hallgatoNode = hallgatoNodeList.item(i);

                // Aktuális elem kiírása
                System.out.println("\nAktuális elem: " + hallgatoNode.getNodeName());

                if (hallgatoNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element hallgatoElement = (Element) hallgatoNode;

                    // A 'vezeteknev' elem tartalmának lekérdezése és kiírása
                    String vezeteknev = hallgatoElement
                            .getElementsByTagName("vezeteknev")
                            .item(0)
                            .getTextContent();
                    
                    System.out.println("vezeteknev: " + vezeteknev);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
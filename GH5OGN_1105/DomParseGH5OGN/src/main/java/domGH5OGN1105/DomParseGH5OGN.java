package domGH5OGN1105;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;



import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DomParseGH5OGN {

    public static void main(String argv[]) {

        try {
            
            File inputFile = new File("GH5OGNhallgato.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(inputFile);

            // A 'hallgatok' gyökér elem
            Node hallgatok = doc.getFirstChild();

            // Az ELSŐ 'hallgato' elem lekérése (
            Node hallgat = doc.getElementsByTagName("hallgato").item(0);

            // hallgat attributumának lekérése
            NamedNodeMap attr = hallgat.getAttributes();
            Node nodeAttr = attr.getNamedItem("id");
            
            nodeAttr.setTextContent("01");

            // loop the hallgat child node
            NodeList list = hallgat.getChildNodes();

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;

                    // Keresztnév módosítása
                    if ("keresztnev".equals(eElement.getNodeName())) {
                        if ("Pál".equals(eElement.getTextContent())) {
                            eElement.setTextContent("Olivia");
                        }
                    }

                    // Vezetéknév módosítása
                    if ("vezeteknev".equals(eElement.getNodeName())) {
                        if ("Kiss".equals(eElement.getTextContent())) {
                            eElement.setTextContent("Radics");
                        }
                    }
                }
            }

            // Tartalom konzolra írása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Ez DOMSource tartalmazza a DOM fát. Egy bemeneti forrás létrehozása egy DOM csomóponttal.
            DOMSource source = new DOMSource(doc);

            System.out.println("---Módosított fájl---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
}

package xpathgh5ogn;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;

// Mentéshez szükséges importok
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;


public class xPathQueryGH5OGN {

    public static void main(String[] args) {
        String outputFileName = "orarendGH5OGN1.xml";
        
        try {
            // 1. Inicializálás
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("GH5OGN_orarend.xml");
            document.getDocumentElement().normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();

            String neptunkod;
            NodeList neptunKod;

            
            System.out.println("--- 1. Lekérdezés: Hétfői órák ---");
            neptunkod = "//ora[idopont/nap='Hétfő']";
            System.out.println("XPath: " + neptunkod);
            neptunKod = (NodeList) xPath.compile(neptunkod).evaluate(document, XPathConstants.NODESET);
            printNodeListDetails(neptunKod, "targy");
            
            
            System.out.println("\n--- 2. Lekérdezés: Web technológiák 1. oktatók ---");
            neptunkod = "//ora[targy='Web technológiák 1.']/oktato";
            System.out.println("XPath: " + neptunkod);
            neptunKod = (NodeList) xPath.compile(neptunkod).evaluate(document, XPathConstants.NODESET);
            printNodeListDetails(neptunKod, "textContent");

            
            System.out.println("\n--- 3. Lekérdezés: Gyakorlat órák ---");
            neptunkod = "//ora[@tipus='Gyakorlat']";
            System.out.println("XPath: " + neptunkod);
            neptunKod = (NodeList) xPath.compile(neptunkod).evaluate(document, XPathConstants.NODESET);
            printNodeListDetails(neptunKod, "targy");


            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            
            
            StreamResult fileResult = new StreamResult(new File(outputFileName));
            transformer.transform(source, fileResult);
            
            System.out.println("\n*** Fájl mentve: Az eredeti dokumentum elmentve ide: " + outputFileName + " ***");

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // A konzolos kiírás
    private static void printNodeListDetails(NodeList nodeList, String detailType) {
        System.out.println("  Találatok száma: " + nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.print("    > Elem: <" + element.getNodeName() + ">");
                
                if (detailType.equals("targy")) {
                    NodeList targy = element.getElementsByTagName("targy");
                    if (targy.getLength() > 0) {
                        System.out.print(" (Tárgy: " + targy.item(0).getTextContent() + ")");
                    }
                } else if (detailType.equals("textContent")) {
                    System.out.print(", Tartalom: " + element.getTextContent());
                }

                if (element.hasAttribute("id")) {
                    System.out.print(" [ID: " + element.getAttribute("id") + "]");
                }
                System.out.println();
            }
        }
    }
}
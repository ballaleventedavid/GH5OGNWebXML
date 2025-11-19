package xpathgh5ogn;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;

public class xPathModifyGH5OGN {

    public static void main(String[] args) {
        try {
            
            File xmlFile = new File("studentGH5OGN.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            
            XPath xPath = XPathFactory.newInstance().newXPath();

            
            String expression = "/class/student[@id='01']";
            Node studentNode = (Node) xPath.compile(expression).evaluate(doc, XPathConstants.NODE);

            if (studentNode != null && studentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) studentNode;

                
                Node idNode = studentElement.getElementsByTagName("id").item(0);
                
                if (idNode != null && idNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element idElement = (Element) idNode;
                    Node keresztnevNode = idElement.getElementsByTagName("keresztnev").item(0);

                    if (keresztnevNode != null) {
                        
                        System.out.println("Régi név: " + keresztnevNode.getTextContent());
                        keresztnevNode.setTextContent("Új Ember"); // Az új név
                    }
                }

               
                System.out.println("--- Módosított példány: ---");
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

                DOMSource source = new DOMSource(studentNode);
                StreamResult consoleResult = new StreamResult(System.out);
                transformer.transform(source, consoleResult);

            } else {
                System.out.println("Nem található 'student' elem id='01' attribútummal.");
                System.out.println("Ellenőrizd, hogy módosítottad-e az XML fájlt az attribútum hozzáadásához!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
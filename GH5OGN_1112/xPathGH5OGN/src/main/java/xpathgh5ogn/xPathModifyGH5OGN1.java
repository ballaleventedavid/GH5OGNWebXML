package xpathgh5ogn;

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
import java.io.File;
import java.io.IOException;

public class xPathModifyGH5OGN1 {

    public static void main(String[] args) {
        String xmlFileName = "GH5OGN_orarend.xml";
        String outputFileName = "orarendGH5OGN2.xml";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder(); 
            Document document = builder.parse(xmlFileName);
            document.getDocumentElement().normalize();

            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("1. Módosítás: Helyszín frissítése (Elektrotechnika-elektronika)...");
            String query1 = "//ora[targy='Elektrotechnika-elektronika']/helyszin";
            NodeList result1 = (NodeList) xPath.compile(query1).evaluate(document, XPathConstants.NODESET);
            if (result1.getLength() > 0) {
                for(int i = 0; i < result1.getLength(); i++) {
                    result1.item(i).setTextContent("1. előadó");
                }
                System.out.println("   -> Sikeres: Helyszín új értéke: 1.előadó. (" + result1.getLength() + " elem módosítva)");
            } else {
                System.out.println("   -> Nincs találat az Elektrotechnika tárgyra.");
            }

            System.out.println("\n2. Módosítás: Új attribútum hozzáadása (ora id='8')...");
            String query2 = "//ora[@id='8']";
            Node result2 = (Node) xPath.compile(query2).evaluate(document, XPathConstants.NODE);
            if (result2 != null && result2.getNodeType() == Node.ELEMENT_NODE) {
                Element oraElement = (Element) result2;
                oraElement.setAttribute("neptun", "GH5OGN");
                System.out.println("   -> Sikeres: hozzáadva a neptun=\"GH5OGN\" attribútum az ID 8-as órához.");
            } else {
                System.out.println("   -> Nincs találat a cél ID-val (8).");
            }
            
            System.out.println("\n3. Módosítás: Oktató nevének frissítése (ora id='15')...");
            String query3 = "//ora[@id='15']/oktato";
            Node result3 = (Node) xPath.compile(query3).evaluate(document, XPathConstants.NODE);
            if (result3 != null) {
                String originalName = result3.getTextContent();
                result3.setTextContent("Új Oktató Béla");
                System.out.println("   -> Sikeres: Oktató megváltoztatva " + originalName + " -> 'Új Oktató Béla'.");
            } else {
                System.out.println("   -> Nincs találat a cél ID-val (15).");
            }

            System.out.println("\n--- Fájlba mentés ---");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            DOMSource source = new DOMSource(document);
            
            // Fájlba mentés
            StreamResult fileResult = new StreamResult(new File(outputFileName));
            transformer.transform(source, fileResult);
            
            System.out.println("*** Sikeres mentés: A módosított tartalom elmentve ide: " + outputFileName + " ***");

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
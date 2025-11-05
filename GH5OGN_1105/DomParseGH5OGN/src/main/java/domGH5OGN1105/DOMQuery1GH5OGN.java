package domgh5ogn1105;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Importok a kiíráshoz (Transformer)
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DOMQuery1GH5OGN {

    public static void main(String[] args) {

        try {
            File inputFile = new File("GH5OGN_orarend.xml");
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Transformer objektum létrehozása a formázott kiíráshoz
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // XML fejrész elhagyása

            // 1. FELADAT: Kurzusok nevének listázása ---
            System.out.println("--- 1. Feladat: Kurzusnevek ---");
            NodeList targyList = doc.getElementsByTagName("targy");
            List<String> kurzusok = new ArrayList<>();

            for (int i = 0; i < targyList.getLength(); i++) {
                kurzusok.add(targyList.item(i).getTextContent());
            }

            System.out.print("Kurzusok: [");
            for (int i = 0; i < kurzusok.size(); i++) {
                System.out.print(kurzusok.get(i) + (i == kurzusok.size() - 1 ? "" : ", "));
            }
            System.out.println("]\n");

            
            // --- 2. FELADAT: Első példány kiírása ---
            System.out.println("--- 2. Feladat: Első óra kiírása ---");
            Node firstOraNode = doc.getElementsByTagName("ora").item(0);

            //Kiírás konzolra
            DOMSource source = new DOMSource(firstOraNode);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            //Kiírás fájlba
            File outputFile = new File("ora_elso_GH5OGN.xml");
            StreamResult fileResult = new StreamResult(outputFile);
            transformer.transform(source, fileResult);
            System.out.println("\n(Az első óra elmentve ide: " + outputFile.getName() + ")\n");


            // 3. FELADAT: Oktatók nevének listázása 
            System.out.println("--- 3. Feladat: Oktatók ---");
            NodeList oktatoList = doc.getElementsByTagName("oktato");
            List<String> oktatok = new ArrayList<>();

            for (int i = 0; i < oktatoList.getLength(); i++) {
                String oktatoNeve = oktatoList.item(i).getTextContent();
                // Opcionális: csak egyedi neveket gyűjtsünk
                if (!oktatok.contains(oktatoNeve)) {
                    oktatok.add(oktatoNeve);
                }
            }
            System.out.println("Oktatók: " + oktatok + "\n");


            // 4. FELADAT: Összetett lekérdezés
            System.out.println("--- 4. Feladat: Összetett lekérdezés ---");
            System.out.println("Minden 'Dr. Agárdi Anita' által tartott óra helyszíne:");

            NodeList oraNodeList = doc.getElementsByTagName("ora");
            for (int i = 0; i < oraNodeList.getLength(); i++) {
                Node oraNode = oraNodeList.item(i);
                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;

                    // Adatok kinyerése
                    String targy = oraElement.getElementsByTagName("targy").item(0).getTextContent();
                    String oktato = oraElement.getElementsByTagName("oktato").item(0).getTextContent();
                    String helyszin = oraElement.getElementsByTagName("helyszin").item(0).getTextContent();

                    // Feltétel ellenőrzése
                    if ("Dr. Agárdi Anita".equals(oktato)) {
                        System.out.println("  - Tárgy: " + targy + " | Helyszín: " + helyszin);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package domgh5ogn1105;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class DomModify1GH5OGN {

    public static void main(String[] args) {

        try {
            
            // A beolvasandó XML fájl
            File inputFile = new File("GH5OGN_orarend.xml");
            
            // A kimeneti fájl neve
            File outputFile = new File("orarendModify1GH5OGN.xml");

            // DOM fa létrehozása a fájlból
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            

            //összes 'ora' elem
            NodeList oraList = doc.getElementsByTagName("ora");
            
            // Vegyük az első 'ora' elemet
            Node firstOraNode = oraList.item(0);

            if (firstOraNode.getNodeType() == Node.ELEMENT_NODE) {
                Element firstOraElement = (Element) firstOraNode;

                // Új elem létrehozása
                Element oraado = doc.createElement("oraado");
                oraado.appendChild(doc.createTextNode("Dr. Példa János")); // Tartalommal feltöltés

                // Az új elem hozzáadása az első 'ora' elemhez
                firstOraElement.appendChild(oraado);
            }

            //. FELADAT: KIÍRÁS KONZOLRA ÉS FÁJLBA

            System.out.println("--- 1. Feladat: Módosítás <oraado> elemmel ---");

            // Transformer beállítása a szép kiíráshoz
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);

            // Kiírás a konzolra
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            //Kiírás a 'orarendModify1GH5OGN.xml' fájlba
            StreamResult fileResult = new StreamResult(outputFile);
            transformer.transform(source, fileResult);

            System.out.println("\n(A fenti tartalom elmentve ide: " + outputFile.getName() + ")");


            //  2. FELADAT: MINDEN 'GYAKORLAT' ÁTÍRÁSA 'ELŐADÁS'-RA

            System.out.println("\n\n--- 2. Feladat: 'Gyakorlat' -> 'Előadás' módosítás ---");

            // Az oraList végigjárása
            for (int i = 0; i < oraList.getLength(); i++) {
                Node oraNode = oraList.item(i);

                if (oraNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element oraElement = (Element) oraNode;

                    String tipus = oraElement.getAttribute("tipus");

                    if ("Gyakorlat".equals(tipus)) {
                        oraElement.setAttribute("tipus", "Előadás");
                    }
                }
            }

            // 2. FELADAT: KIÍRÁS KONZOLRA

            StreamResult finalConsoleResult = new StreamResult(System.out);
            transformer.transform(source, finalConsoleResult);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
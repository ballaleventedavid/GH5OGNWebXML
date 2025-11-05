package hu.domparse.gh5ogn;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

/**
 * Legalább 4 módosítás DOM-mal, majd mentés új fájlba és kiírás.
 */
public class GH5OGNDOMModify {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // XML beolvasása
        File xmlFile = new File("../GH5OGN_XML.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        System.out.println("Eredeti dokumentum gyökér: " + doc.getDocumentElement().getNodeName());

        //Új telefonszám hozzáadása a V002-höz
        Element vasarloV002 = findByAttr(doc, "Vasarlo", "vevo_id", "V002");
        if (vasarloV002 != null) {
            Element ujTel = doc.createElement("Telefonszam");
            ujTel.setTextContent("+36111222333");
            vasarloV002.appendChild(ujTel);
            System.out.println("Új telefonszám hozzáadva");
        }

        //E002 étterem nyitvatartás módosítása
        Element etteremE002 = findByAttr(doc, "Etterem", "etterem_id", "E002");
        if (etteremE002 != null) {
            Element ny = (Element) etteremE002.getElementsByTagName("Nyitvatartas").item(0);
            ny.setTextContent("H-V: 11:00-23:00");
            System.out.println("E002 nyitvatartása frissítve");
        }

        //ET004 étel egyik allergénjének törlése
        Element etelET004 = findByAttr(doc, "Etel", "etel_id", "ET004");
        if (etelET004 != null) {
            Node allergen = etelET004.getElementsByTagName("Allergenek").item(0);
            if (allergen != null) {
                etelET004.removeChild(allergen);
                System.out.println("ET004 egyik allergénje törölve.");
            }
        }

        //Új étel felvétele az E001 étteremhez
        Element etelek = (Element) doc.getElementsByTagName("Etelek").item(0);
        Element ujEtel = doc.createElement("Etel");
        ujEtel.setAttribute("etel_id", "ET005");
        ujEtel.setAttribute("etterem_idref", "E001");
        appendText(doc, ujEtel, "Nev", "Retro lángos");
        appendText(doc, ujEtel, "Ar", "1500");
        appendText(doc, ujEtel, "Leiras", "Fokhagyma, tejföl, sajt");
        etelek.appendChild(ujEtel);
        System.out.println("Új étel felvéve az E001-hez");

        // R001 rendelés státusz frissítése 'Kiszállítva'-ra
        Element r001 = findByAttr(doc, "Rendeles", "rendeles_id", "R001");
        if (r001 != null) {
            Element st = (Element) r001.getElementsByTagName("Statusz").item(0);
            st.setTextContent("Kiszállítva");
            System.out.println("R001 státusza frissült, kiszállitva");
        }

        // Mentés új fájlba
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        tr.transform(new DOMSource(doc), new StreamResult(new File("GH5OGN_XML_valtoztatva.xml")));
        System.out.println("Változtatások mentve a GH5OGN_XML_valtoztatva.xml fájlba");
    }

    private static Element findByAttr(Document doc, String tag, String attr, String value) {
        NodeList list = doc.getElementsByTagName(tag);
        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            if (value.equals(e.getAttribute(attr))) return e;
        }
        return null;
    }

    private static void appendText(Document doc, Element parent, String tag, String value) {
        Element e = doc.createElement(tag);
        e.setTextContent(value);
        parent.appendChild(e);
    }
}



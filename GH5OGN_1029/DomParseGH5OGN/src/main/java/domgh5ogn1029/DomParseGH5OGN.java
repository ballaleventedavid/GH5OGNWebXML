
package domgh5ogn1029;

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
    public static void main(String argv[]) throws SAXException,
            IOException, ParserConfigurationException {

        // XML file megnyitása
        File xmlFile = new File("GH5OGNhallgato.xml");

        // példányosítás a DocumentBuilderFactory osztályt a statikus newInstance() metódussal.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        // A DocumentBuilderFactory-ből megkapjuk a DocumentBuildert.
        // A DocumentBuilder tartalmazza az API-t a DOM-dokumentum példányok XML-dokumentumból való beszerzéséhez.

        // DOM fa előállítása
        Document neptunkod = dBuilder.parse(xmlFile);
        // A parse() metódus elemzi az XML fájlt a Document.

        neptunkod.getDocumentElement().normalize();
        // A dokumentum normalizálása segít a helyes eredmények elérésében.
        // eltávolítja az üres szövegcsomópontokat, és összekapcsolja a szomszédos szövegcsomópontokat.

        System.out.println("Gyökér elem: " + neptunkod.getDocumentElement().getNodeName());
        // Kiiratjuk a dokumentum gyökérelemét

        // a fa megadott névvel (hallgato) rendelkező csomópontjainak összegyűjtése listába.
        // A getElementsByTagName() metódus segítségével megkapjuk a hallgato elem Nodelistjét a dokumentumban.
        NodeList nList = neptunkod.getElementsByTagName("hallgato"); // gyerekelemek mentése listába

        for (int i = 0; i < nList.getLength(); i++) {
            // A listán for ciklussal megyünk végig.

            // lekérjük a lista aktuális elemét
            Node nNode = nList.item(i);

            System.out.println("\nAktuális elem: " + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                // Elementté konvertáljuk az aktuális elemet
                Element elem = (Element) nNode;

                // Lekérjük az aktuális elem attribútumának tartalmát
                String hid = elem.getAttribute("id");
                // Az elem attribútumot a getAttribute() segítségével kapjuk meg.

                // Lekérjük az aktuális elem gyerekelemeit és annak tartalmát
                Node node1 = elem.getElementsByTagName("keresztnev").item(0);
                String kname = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("vezeteknev").item(0);
                String vname = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("foglalkozas").item(0);
                String fname = node3.getTextContent();
                // Megkapjuk a hallgato elem három gyerekelemének tartalmát.

                // Formázva kiiratjuk a lekért információkat az adott elemről
                System.out.println("Hallgató id: " + hid);
                System.out.println("Keresztnév: " + kname);
                System.out.println("Vezetéknév: " + vname);
                System.out.println("Foglalkozás: " + fname);

            }
        }
    }
}

package domGH5OGN1029;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DomReadGH5OGN1 {

    public static void main(String[] args) {
        try {
            // XML file megnyitása
            File xmlFile = new File("GH5OGN_orarend.xml");

            // példányosítás a DocumentBuilderFactory osztályt a statikus newInstance() metódussal.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            // A DocumentBuilderFactory-ből megkapjuk a DocumentBuildert.
            // A DocumentBuilder tartalmazza az API-t a DOM-dokumentum példányok XML-dokumentumból való beszerzéséhez.

            // DOM fa előállítása
            Document orarend = dBuilder.parse(xmlFile);
            // A parse() metódus elemzi az XML fájlt a Document.

            orarend.getDocumentElement().normalize();
            // A dokumentum normalizálása segít a helyes eredmények elérésében.
            // eltávolítja az üres szövegcsomópontokat, és összekapcsolja a szomszédos szövegcsomópontokat.

            System.out.println("Gyökér elem: " + orarend.getDocumentElement().getNodeName());
            // Kiiratjuk a dokumentum gyökérelemét

            // a fa megadott névvel (ora) rendelkező csomópontjainak összegyűjtése listába.
            // A getElementsByTagName() metódus segítségével megkapjuk az ora elem Nodelistjét a dokumentumban.
            NodeList nList = orarend.getElementsByTagName("ora"); // gyerekelemek mentése listába

            for (int i = 0; i < nList.getLength(); i++) {
                // A listán for ciklussal megyünk végig.

                // lekérjük a lista aktuális elemét
                Node nNode = nList.item(i);

                System.out.println("\nAktuális elem: " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    // Elementté konvertáljuk az aktuális elemet
                    Element elem = (Element) nNode;

                    // Lekérjük az aktuális elem attribútumainak tartalmát
                    String oid = elem.getAttribute("id");
                    String tipus = elem.getAttribute("tipus");

                    // Lekérjük az aktuális elem gyerekelemeit és annak tartalmát
                    String targy = elem.getElementsByTagName("targy").item(0).getTextContent();

                    Element idopont = (Element) elem.getElementsByTagName("idopont").item(0);
                    String nap = idopont.getElementsByTagName("nap").item(0).getTextContent();
                    String tol = idopont.getElementsByTagName("tol").item(0).getTextContent();
                    String ig = idopont.getElementsByTagName("ig").item(0).getTextContent();

                    String helyszin = elem.getElementsByTagName("helyszin").item(0).getTextContent();
                    String oktato = elem.getElementsByTagName("oktato").item(0).getTextContent();
                    String szak = elem.getElementsByTagName("szak").item(0).getTextContent();

                    // Formázva kiiratjuk a lekért információkat az adott elemről
                    System.out.println("Óra id: " + oid);
                    System.out.println("Típus: " + tipus);
                    System.out.println("Tárgy: " + targy);
                    System.out.println("Nap: " + nap);
                    System.out.println("Tól: " + tol);
                    System.out.println("Ig: " + ig);
                    System.out.println("Helyszín: " + helyszin);
                    System.out.println("Oktató: " + oktato);
                    System.out.println("Szak: " + szak);

                }
            }

        } catch (Exception e) {
            System.err.println("Hiba történt: " + e.getMessage());
        }
    }

    // A feladatleírás szerinti elnevezés megőrzése érdekében külön metódus
    public static void DOMRead1() {
        main(new String[0]);
    }
}



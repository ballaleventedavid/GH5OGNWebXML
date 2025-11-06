package hu.domparse.gh5ogn;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import org.xml.sax.SAXException;

public class GH5OGNDomRead {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        // XML fájl beolvasása
        File xmlFile = new File("../GH5OGN_XML.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();

        // DOM fa létrehozása a fájlból
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        StringWriter buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);

        // Vásárlók kiírása
        NodeList vasarlok = doc.getElementsByTagName("Vasarlo");
        out.println("\n Vásárlók (" + vasarlok.getLength() + ") \n");
        for (int i = 0; i < vasarlok.getLength(); i++) {
            Element vasarlo = (Element) vasarlok.item(i);
            out.println("[Vásárló] vevo_id=" + vasarlo.getAttribute("vevo_id"));
            Element nev = (Element) vasarlo.getElementsByTagName("Nev").item(0);
            out.println("  Név: " + getText(nev, "Vezeteknev") + " " + getText(nev, "Keresztnev"));
            out.println("  Email: " + getText(vasarlo, "Email"));
            NodeList tels = vasarlo.getElementsByTagName("Telefonszam");
            for (int t = 0; t < tels.getLength(); t++) {
                out.println("  Telefonszám: " + tels.item(t).getTextContent());
            }
            Element mentett = (Element) vasarlo.getElementsByTagName("MentettCimek").item(0);
            if (mentett != null) {
                NodeList cimek = mentett.getElementsByTagName("Cim");
                for (int c = 0; c < cimek.getLength(); c++) {
                    Element cim = (Element) cimek.item(c);
                    out.println("  Cím #" + (c + 1) + ": " + formatCim(cim));
                }
            }
            out.println();
        }

        // Éttermek
        NodeList etteremList = doc.getElementsByTagName("Etterem");
        out.println(" Éttermek (" + etteremList.getLength() + ") \n");
        for (int i = 0; i < etteremList.getLength(); i++) {
            Element etterem = (Element) etteremList.item(i);
            out.println("[Étterm] etterem_id=" + etterem.getAttribute("etterem_id"));
            out.println("  Név: " + getText(etterem, "Nev"));
            Element cim = (Element) etterem.getElementsByTagName("Cim").item(0);
            out.println("  Cím: " + formatCim(cim));
            NodeList katList = etterem.getElementsByTagName("Kategoria");
            for (int k = 0; k < katList.getLength(); k++) {
                out.println("  Kategória: " + katList.item(k).getTextContent());
            }
            out.println("  Nyitvatartás: " + getText(etterem, "Nyitvatartas"));
            out.println();
        }

        // Futárok
        NodeList futarList = doc.getElementsByTagName("Futar");
        out.println("Futárok (" + futarList.getLength() + ")\n");
        for (int i = 0; i < futarList.getLength(); i++) {
            Element futar = (Element) futarList.item(i);
            out.println("[Futár] futar_id=" + futar.getAttribute("futar_id"));
            Element nev = (Element) futar.getElementsByTagName("Nev").item(0);
            out.println("  Név: " + getText(nev, "Vezeteknev") + " " + getText(nev, "Keresztnev"));
            out.println("  Telefonszám: " + getText(futar, "Telefonszam"));
            out.println("  Rendszám: " + getText(futar, "JarmuRendszam"));
            out.println("  Értékelés: " + getText(futar, "Ertekeles"));
            out.println();
        }

        // Ételek blokk
        NodeList etelList = doc.getElementsByTagName("Etel");
        out.println("==== Ételek (" + etelList.getLength() + ") ====\n");
        for (int i = 0; i < etelList.getLength(); i++) {
            Element etel = (Element) etelList.item(i);
            out.println("[Étel] etel_id=" + etel.getAttribute("etel_id") + ", etterem_idref=" + etel.getAttribute("etterem_idref"));
            out.println("  Név: " + getText(etel, "Nev"));
            out.println("  Ár: " + getText(etel, "Ar"));
            out.println("  Leírás: " + getText(etel, "Leiras"));
            NodeList allergenList = etel.getElementsByTagName("Allergenek");
            for (int a = 0; a < allergenList.getLength(); a++) {
                out.println("  Allergen: " + allergenList.item(a).getTextContent());
            }
            out.println();
        }

        // Rendelések
        NodeList rendelesek = doc.getElementsByTagName("Rendeles");
        out.println("Rendelések (" + rendelesek.getLength() + ") \n");
        for (int i = 0; i < rendelesek.getLength(); i++) {
            Element rendeles = (Element) rendelesek.item(i);
            out.println("[Rendelés] rendeles_id=" + rendeles.getAttribute("rendeles_id")
                    + ", vevo_idref=" + rendeles.getAttribute("vevo_idref")
                    + ", etterem_idref=" + rendeles.getAttribute("etterem_idref")
                    + ", futar_idref=" + rendeles.getAttribute("futar_idref"));
            out.println("  Időpont: " + getText(rendeles, "RendelesIdeje"));
            out.println("  Státusz: " + getText(rendeles, "Statusz"));
            Element szc = (Element) rendeles.getElementsByTagName("SzallitasiCim").item(0);
            out.println("  Szállítási cím: " + formatCim(szc));
            out.println("  Végösszeg: " + getText(rendeles, "Vegosszeg"));

            Element tetelek = (Element) rendeles.getElementsByTagName("RendelesTetelei").item(0);
            if (tetelek != null) {
                NodeList tetelList = tetelek.getElementsByTagName("Tetel");
                for (int t = 0; t < tetelList.getLength(); t++) {
                    Element tetel = (Element) tetelList.item(t);
                    out.println("    [Tétel] etel_idref=" + tetel.getAttribute("etel_idref"));
                    out.println("      Darabszám: " + getText(tetel, "Darabszam"));
                    out.println("      Vásárláskori ár: " + getText(tetel, "VasarlaskoriAr"));
                    String extra = getOptionalText(tetel, "ExtraKeresek");
                    if (extra != null) out.println("      Extra kérések: " + extra);
                }
            }
            out.println();
        }

        out.flush();
        System.out.print(buffer.toString());

        // Mentés fájlba
        try (FileWriter fw = new FileWriter(new File("GH5OGN_Read_mentett.txt"))) {
            fw.write(buffer.toString());
        }

    }

    private static String getText(Element parent, String tag) {
        return ((Element) parent.getElementsByTagName(tag).item(0)).getTextContent();
    }

    private static String getOptionalText(Element parent, String tag) {
        Node n = parent.getElementsByTagName(tag).item(0);
        return n == null ? null : n.getTextContent();
    }

    private static String formatCim(Element cim) {
        if (cim == null) return "-";
        String irsz = getText(cim, "Irsz");
        String varos = getText(cim, "Varos");
        String utca = getText(cim, "Utca");
        String hsz = getText(cim, "Hsz");
        return irsz + ", " + varos + ", " + utca + " " + hsz + ".";
    }
}



package hu.domparse.gh5ogn;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

public class GH5OGNDomQuery {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        // XML fájl beolvasása
        File xmlFile = new File("../GH5OGN_XML.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        //Összes vásárló neve és email címe
        System.out.println("\n Vásárlók neve és email címe:");
        NodeList vasarlok = doc.getElementsByTagName("Vasarlo");
        for (int i = 0; i < vasarlok.getLength(); i++) {
            Element v = (Element) vasarlok.item(i);
            Element nev = (Element) v.getElementsByTagName("Nev").item(0);
            String teljesNev = text(nev, "Vezeteknev") + " " + text(nev, "Keresztnev");
            System.out.println("- " + teljesNev + "  " + text(v, "Email"));
        }

        //E001 étteremhez tartozó ételek neve és ára
        System.out.println("\n E001 étterem ételeinek neve és ára:");
        NodeList etelek = doc.getElementsByTagName("Etel");
        for (int i = 0; i < etelek.getLength(); i++) {
            Element etel = (Element) etelek.item(i);
            if ("E001".equals(etel.getAttribute("etterem_idref"))) {
                System.out.println("- " + text(etel, "Nev") + " " + text(etel, "Ar") + " Ft");
            }
        }

        //4000 Ft feletti rendelések azonosítója és státusza
        System.out.println("\n 4000 Ft feletti rendelések:");
        NodeList rendelesek = doc.getElementsByTagName("Rendeles");
        for (int i = 0; i < rendelesek.getLength(); i++) {
            Element r = (Element) rendelesek.item(i);
            int vegosszeg = Integer.parseInt(text(r, "Vegosszeg"));
            if (vegosszeg > 4000) {
                System.out.println("- " + r.getAttribute("rendeles_id") + ": " + vegosszeg + " Ft  " + text(r, "Statusz"));
            }
        }

        //Futárok telefonszámai és értékelése
        System.out.println("\n Futárok telefonszáma és értékelése:");
        NodeList futarok = doc.getElementsByTagName("Futar");
        for (int i = 0; i < futarok.getLength(); i++) {
            Element f = (Element) futarok.item(i);
            double ertekeles = Double.parseDouble(text(f, "Ertekeles"));
            System.out.println("- " + text(f, "Telefonszam") + "  értékelés: " + ertekeles);
        }

        //Rendelések tételeinek darabszáma 
        System.out.println("\n Rendelések tételeinek darabszáma:");
        for (int i = 0; i < rendelesek.getLength(); i++) {
            Element r = (Element) rendelesek.item(i);
            Element tetelek = (Element) r.getElementsByTagName("RendelesTetelei").item(0);
            int sum = 0;
            if (tetelek != null) {
                NodeList tetelList = tetelek.getElementsByTagName("Tetel");
                for (int t = 0; t < tetelList.getLength(); t++) {
                    Element tetel = (Element) tetelList.item(t);
                    sum += Integer.parseInt(text(tetel, "Darabszam"));
                }
            }
            System.out.println("- " + r.getAttribute("rendeles_id") + ": darabszám összesen = " + sum);
        }
    }

    private static String text(Element parent, String tag) {
        return ((Element) parent.getElementsByTagName(tag).item(0)).getTextContent();
    }
}



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

public class GH5OGNDOMModify {

    public static void main(String[] args) {
        try {
            // XML beolvasása
            File xmlFile = new File("../GH5OGN_XML.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            System.out.println("--- Módosítások ---");

            // 1. MÓDOSÍTÁS: Új telefonszám hozzáadása a V002-höz
            NodeList vasarloList = doc.getElementsByTagName("Vasarlo");
            for (int i = 0; i < vasarloList.getLength(); i++) {
                Node nNode = vasarloList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element vasarlo = (Element) nNode;
                    if ("V002".equals(vasarlo.getAttribute("vevo_id"))) {
                        Element ujTel = doc.createElement("Telefonszam");
                        ujTel.setTextContent("+36111222333");
                        vasarlo.appendChild(ujTel);
                        System.out.println("V002 vásárló új telefonszáma hozzáadva.");
                        break;
                    }
                }
            }

            // 2. MÓDOSÍTÁS: E002 étterem nyitvatartás módosítása
            NodeList etteremList = doc.getElementsByTagName("Etterem");
            for (int i = 0; i < etteremList.getLength(); i++) {
                Node nNode = etteremList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element etterem = (Element) nNode;
                    if ("E002".equals(etterem.getAttribute("etterem_id"))) {
                        //Nyitvatartas elem megtalálása
                        NodeList eChildren = etterem.getChildNodes();
                        for (int j = 0; j < eChildren.getLength(); j++) {
                            Node eNode = eChildren.item(j);
                            if (eNode.getNodeType() == Node.ELEMENT_NODE) {
                                if ("Nyitvatartas".equals(eNode.getNodeName())) {
                                    eNode.setTextContent("H-V: 11:00-23:00");
                                    System.out.println("E002 nyitvatartása frissítve.");
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }

            // 3. MÓDOSÍTÁS: ET004 étel egyik allergénjének törlése
            NodeList etelList = doc.getElementsByTagName("Etel");
            for (int i = 0; i < etelList.getLength(); i++) {
                Node nNode = etelList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element etel = (Element) nNode;
                    if ("ET004".equals(etel.getAttribute("etel_id"))) {
                        // Megkeresi az első Allergenek elemet és törli
                        NodeList allergenList = etel.getElementsByTagName("Allergenek");
                        if (allergenList.getLength() > 0) {
                            Node allergen = allergenList.item(0);
                            etel.removeChild(allergen);
                            System.out.println("ET004 egyik allergénje törölve.");
                        }
                        break;
                    }
                }
            }

            // 4. MÓDOSÍTÁS: Új étel felvétele az E001 étteremhez
            Node etelek = doc.getElementsByTagName("Etelek").item(0);

            // Új Etel elem felépítése
            Element ujEtel = doc.createElement("Etel");
            ujEtel.setAttribute("etel_id", "ET005");
            ujEtel.setAttribute("etterem_idref", "E001");

            Element nev = doc.createElement("Nev");
            nev.setTextContent("Retro lángos");
            ujEtel.appendChild(nev);

            Element ar = doc.createElement("Ar");
            ar.setTextContent("1500");
            ujEtel.appendChild(ar);

            Element leiras = doc.createElement("Leiras");
            leiras.setTextContent("Fokhagyma, tejföl, sajt");
            ujEtel.appendChild(leiras);

            // elem hozzáadása a fához
            etelek.appendChild(ujEtel);
            System.out.println("Új étel (ET005) felvéve az E001-hez.");

            // 5. MÓDOSÍTÁS: R001 rendelés státusz frissítése Kiszállítva-ra
            NodeList rendelesList = doc.getElementsByTagName("Rendeles");
            for (int i = 0; i < rendelesList.getLength(); i++) {
                Node nNode = rendelesList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element rendeles = (Element) nNode;
                    if ("R001".equals(rendeles.getAttribute("rendeles_id"))) {
                        Node statusz = rendeles.getElementsByTagName("Statusz").item(0);
                        if (statusz != null) {
                            statusz.setTextContent("Kiszállítva");
                            System.out.println("R001 státusza frissítve.");
                        }
                        break;
                    }
                }
            }
            
            // Módosított XML kiírása a konzolra
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);

            System.out.println("\n--- Módosított dokumentum ---");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
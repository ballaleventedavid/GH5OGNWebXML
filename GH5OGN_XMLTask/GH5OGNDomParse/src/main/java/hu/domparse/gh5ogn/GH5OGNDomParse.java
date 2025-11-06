package hu.domparse.gh5ogn;

import java.util.Scanner;

public class GH5OGNDomParse {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1-es Adatolvasás GH5OGNDomRead");
            System.out.println("2-es Adatlekérdezés GH5OGNDomQuery");
            System.out.println("3-as Adatmódosítás GH5OGNDOMModify");
            System.out.println("0 Kilépés");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> GH5OGNDomRead.main(new String[]{});
                    case "2" -> GH5OGNDomQuery.main(new String[]{});
                    case "3" -> GH5OGNDOMModify.main(new String[]{});
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Ismeretlen opció.");
                }
            } catch (Exception e) {
                System.out.println("Hiba történt: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
    }
}



package cz.student_evidence.ui;

import cz.student_evidence.services.StudentService;

import java.util.Scanner;

public class ScannerTool {

    private final Scanner scanner = new Scanner(System.in);

    public void promptEnterKey() {
        System.out.println("\nStisknete \"ENTER\" pro pokracovani...\n");
        scanner.nextLine();
    }

    public int readIntegerFromConsole() {
        while (!scanner.hasNextInt()) scanner.next();

        int integer = -1;
        boolean isValidInteger;
        do {
            try {
                integer = Integer.parseInt(scanner.nextLine());
                isValidInteger = true;
            } catch (NumberFormatException e) {
                System.out.println("Spatny vstup - ocekava se cele cislo");
                isValidInteger = false;
            }
        } while (!isValidInteger);

        return integer;
    }


    public long readLongFromConsole() {
        while (!scanner.hasNextLong()) scanner.next();

        long number = -1L;
        boolean isValidLong;
        do {
            try {
                number = Long.parseLong(scanner.nextLine());
                isValidLong = true;
            } catch (NumberFormatException e) {
                System.out.println("Spatny vstup - ocekava se cele cislo");
                isValidLong = false;
            }
        } while (!isValidLong);

        return number;
    }


    public String readStringFromConsole() {
        return scanner.nextLine();
    }

}

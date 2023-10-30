package config;

import java.util.Scanner;

public class Input {
    private static Scanner input = new Scanner(System.in);
    private static Scanner inputString = new Scanner(System.in);

    public static int inputInteger() {
        int inputInt;
        while (true)
            try {
                System.out.println("Mời lựa chọn");
                inputInt = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.err.println("Sai định dạng vui lòng nhập số!");
            }
        return inputInt;
    }

    public static String inputString() {
        return inputString.nextLine();
    }

}

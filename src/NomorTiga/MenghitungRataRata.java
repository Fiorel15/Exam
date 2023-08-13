package NomorTiga;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenghitungRataRata {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int i = 1;
        float jum = 0, x, rata = 0;
        System.out.print("Masukkan Banyaknya Data: ");
        int n;
        
        try {
            n = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Data yang dimasukkan tidak valid. Program dihentikan.");
            return;
        }
        
        while (i <= n) {
            try {
                System.out.print("Data ke-" + i + ": ");
                x = scan.nextFloat();
                jum += x;
                i++;
            } catch (InputMismatchException e) {
                String input = scan.next();
                if (input.equalsIgnoreCase("Exit")) {
                    break;
                }
                System.out.println("Data yang dimasukkan tidak valid. Silakan masukkan angka "
                        + "\nuntuk menghitung atau tekan 'Exit' untuk mengakhiri.\n");
            }
        }

        if (i > 1) {
            rata = jum / (i - 1);
            System.out.println("Rata-rata dari bilangan yang dimasukkan adalah: " + rata);
        } else {
            System.out.println("Tidak ada bilangan yang dimasukkan.");
        }
    }
}
package lt.viko.eif;

import lt.viko.eif.algorithm.AesAlgorithm;
import lt.viko.eif.algorithm.CipherMode;

import java.util.Locale;
import java.util.Scanner;

@SuppressWarnings("all")
public class ConsoleApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter text: ");
        String text = scanner.nextLine();
        System.out.println("Enter secret key: ");
        String key = scanner.nextLine();
        System.out.println("Enter mode (enc/dec): ");
        String mode = scanner.nextLine();
        System.out.println("Enter cipher mode of operation (ECB, CBC, CFB): ");
        String cipherMode = scanner.nextLine();

        AesAlgorithm aes = new AesAlgorithm(CipherMode.valueOf(cipherMode.toUpperCase(Locale.ROOT)));

        if (mode.equals("dec")) {
            String decrypted = aes.decrypt(text, key);
            System.out.println("Decrypted: " + decrypted);
        } else {
            String encrypted = aes.encrypt(text, key);
            System.out.println("Encrypted: " + encrypted);
        }
    }
}

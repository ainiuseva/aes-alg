package lt.viko.eif;

import lt.viko.eif.algorithm.AesAlgorithm;
import lt.viko.eif.algorithm.CipherMode;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

@SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {
        String textPath = "src/main/resources/text.txt";
        String encryptedPath = "src/main/resources/encrypted.txt";
        String decryptedPath = "src/main/resources/decrypted.txt";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter mode (enc/dec): ");
        String mode = scanner.nextLine();

        System.out.println("Enter secret key: ");
        String key = scanner.nextLine();

        System.out.println("Enter cipher mode of operation (ECB, CBC, CFB): ");
        String cipherMode = scanner.nextLine();

        AesAlgorithm aes = new AesAlgorithm(CipherMode.valueOf(cipherMode.toUpperCase(Locale.ROOT)));

        if (mode.equals("dec")) {
            String decrypted = aes.decrypt(new File(encryptedPath), key, new File(decryptedPath));
            System.out.println("Decrypted: " + decrypted);
        } else {
            String encrypted = aes.encrypt(new File(textPath), key, new File(encryptedPath));
            System.out.println("Encrypted: " + encrypted);
        }

    }
}

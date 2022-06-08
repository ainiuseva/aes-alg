package lt.viko.eif;

import lt.viko.eif.algorithm.AesAlgorithm;
import lt.viko.eif.algorithm.CipherMode;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@SuppressWarnings("all")
public class AesAlgorithmMain {
    public static void main(String[] args) {
        String encryptedPath = "src/main/resources/encrypted.txt";
        String decryptedPath = "src/main/resources/decrypted.txt";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write the text: ");
        String text = scanner.nextLine();

        System.out.println("Write the key: ");
        String key = scanner.nextLine();

        if (key.length() != 16) {
            throw new RuntimeException("Length of secret key should be 128 bits key size");
        }

        System.out.println("Choose mode (enc/dec): ");
        String mode = scanner.nextLine();

        System.out.println("Your text that will be " + mode + "rypted: " + text);

        System.out.println("Choose block cipher mode (ECB, DBC, CFB)");
        CipherMode cipherMode = CipherMode.valueOf(scanner.nextLine());

        AesAlgorithm aes = new AesAlgorithm(cipherMode);

        if (mode.equals("enc")) {
            String encrypted = aes.encrypt(text, key);
            saveToFile(new File(encryptedPath), encrypted);
            aes.decrypt(new File(encryptedPath), key, new File(decryptedPath));
        } else if (mode.equals("dec")) {
            String decrypted = aes.decrypt(text, key);
            saveToFile(new File(decryptedPath), decrypted);
            aes.encrypt(new File(decryptedPath), key, new File(encryptedPath));
        }
    }

    private static String saveToFile(File saveFilePath, String text) {
        try {
            Files.write(Path.of(saveFilePath.getPath()), text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }
}

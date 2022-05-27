package lt.viko.eif.algorithm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@SuppressWarnings("all")
public class AesAlgorithm {

    private final CipherMode cipherMode;

    public AesAlgorithm(CipherMode cipherMode) {
        this.cipherMode = cipherMode;
    }

    public String encrypt(String text, String key) {
        try {
            SecretKeySpec secretKey = setKey(key);
            Cipher cipher = Cipher.getInstance("AES/" + cipherMode.toString() + "/PKCS5Padding");

            if (cipherMode == CipherMode.ECB) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            }
            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String text, String key) {
        try {
            SecretKeySpec secretKey = setKey(key);
            Cipher cipher = Cipher.getInstance("AES/" + cipherMode.toString() + "/PKCS5Padding");

            if (cipherMode == CipherMode.ECB) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            }
            return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(File textFile, String key, File encryptedTextFile) {
        String text = readText(textFile);

        String encrypted = encrypt(text, key);

        return saveToFile(encryptedTextFile, encrypted);
    }

    private String readText(File textFile) {
        String text = null;
        try {
            text = Files.readString(Path.of(textFile.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }


    public String decrypt(File textFile, String key, File decryptedTextFile) {
        String text = readText(textFile);

        String decrypted = decrypt(text, key);

        return saveToFile(decryptedTextFile, decrypted);
    }

    private String saveToFile(File saveFilePath, String text) {
        try {
            Files.write(Path.of(saveFilePath.getPath()), text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }

    private SecretKeySpec setKey(final String myKey) {
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

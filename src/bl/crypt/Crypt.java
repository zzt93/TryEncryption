package bl.crypt;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by zzt on 10/5/15.
 * <p>
 * Usage:
 */
public class Crypt {


    private static final String thisClass = Crypt.class.getCanonicalName();
    public static final String CRYPT_ALGO = "AES";
    public static final String MODE_PADDING = "/CBC/PKCS5Padding";
    public static int BLOCK_SIZE;
    /**
     * char not in base64 for android is + /
     */
    public static final char CHAR_NOT_BASE64 = ']';

    static {
        try {
            BLOCK_SIZE = Cipher.getInstance(CRYPT_ALGO + MODE_PADDING).getBlockSize();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            BLOCK_SIZE = 0;
        }
    }


    public static final int KEY_BYTES = BLOCK_SIZE;
    public static final int KEY_BITS = BLOCK_SIZE * 8;
    private final String algo;
    private final String password;


    public Crypt(String password)
            throws NoSuchPaddingException, NoSuchAlgorithmException, KeyStoreException {
        this(Crypt.CRYPT_ALGO, password);
    }

    /**
     * @param algo valid algorithm(only block cipher algorithm is valid): AES, DES, 3DES
     */
    private Crypt(String algo, String password) {
        this.algo = algo;
        this.password = password;
    }


    public String encrypt(String s) throws NoSuchAlgorithmException {
        try {
            byte[] text = s.getBytes(Default.ENCODING_UTF8);
            byte[] iv = DeriveKey.getRandomByte(KEY_BITS);

            Cipher cipher = Cipher.getInstance(algo + MODE_PADDING);
            CryptInfo cryptInfo = DeriveKey.deriveSecretKey(password);
            SecretKey secretKey = cryptInfo.getSecretKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            if (iv.length != BLOCK_SIZE) {
                throw new RuntimeException("iv.length != blockSize");
            }
            byte[] textEncrypted = cipher.doFinal(text);

            //            String s1 = new String(textEncrypted, Default.ENCODING_UTF8);
            //            System.out.println(s1);
            // can't use new String(bytes, encoding) for no encoding
            // will explain those bytes correctly
            return toBase64(cryptInfo.getSalt()) + CHAR_NOT_BASE64 +
                    toBase64(iv) + CHAR_NOT_BASE64 + toBase64(textEncrypted);

        } catch (BadPaddingException
                | UnsupportedEncodingException
                | IllegalBlockSizeException
                | InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decrypt(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException {

            String[] split = s.split("" + CHAR_NOT_BASE64);
            if (split.length != 3) {
                throw new IllegalArgumentException("decrypt string is broken");
            }
            byte[] salt = fromBase64(split[0]);
            byte[] iv = fromBase64(split[1]);
            byte[] encryptText = fromBase64(split[2]);

            Cipher cipher = Cipher.getInstance(algo + MODE_PADDING);
            SecretKey secretKey = DeriveKey.recoverSecretKey(password, salt);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] textDecrypted = cipher.doFinal(encryptText);
            return new String(textDecrypted, Default.ENCODING_UTF8);

    }

    public static void main(String[] args) {
        Crypt crypt;
        try {
            crypt = new Crypt("1234567890");
            String encrypt = crypt.encrypt("a message");
            System.out.println(crypt.decrypt(encrypt));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | KeyStoreException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String toHex(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        for (byte b : bytes) {
            buff.append(String.format("%02X", b));
        }

        return buff.toString();
    }

    public static String toBase64(byte[] raw) {
        return Base64.getEncoder().encodeToString(raw);
    }

    public static byte[] fromBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }


}

package data;

import bl.crypt.Crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class AccountInfo {

    private final String site;
    private String password;
    private final String account;

    public AccountInfo(String siteText, String accountText, String passwordText) {
        site = siteText;
        account = accountText;
        password = passwordText;
    }

    public String getSite() {
        return site;
    }

    public String getPassword() {
        return password;
    }

    public String getAccount() {
        return account;
    }

    public void encrypt(Crypt crypt) throws NoSuchAlgorithmException {
        password = crypt.encrypt(password);
    }

    public void decrypt(Crypt crypt) throws NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        password = crypt.decrypt(password);
    }

    class Key {
        private String key;

        public Key(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}

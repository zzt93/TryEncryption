package bl;

import bl.crypt.Crypt;
import data.AccountInfo;
import data.AccountMapper;

import javax.crypto.NoSuchPaddingException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class AccountBL {
    private static AccountBL ourInstance;


    private AccountMapper accountMapper;
    private Crypt crypt;

    public static AccountBL getInstance(String pw) {
        if (ourInstance == null) {
            ourInstance = new AccountBL(pw);
        }
        return ourInstance;
    }

    private AccountBL(String pw) {
        try {
            crypt = new Crypt(pw);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            accountMapper = new AccountMapper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean add(AccountInfo accountInfo) throws SQLException, NoSuchAlgorithmException {
        accountInfo.encrypt(crypt);
        return accountMapper.add(accountInfo);
    }

    public ArrayList<AccountInfo> showAll() throws SQLException, NoSuchAlgorithmException {
        ArrayList<AccountInfo> all = accountMapper.findAll();
        for (AccountInfo accountInfo : all) {
            accountInfo.decrypt(crypt);
        }
        return all;
    }
}

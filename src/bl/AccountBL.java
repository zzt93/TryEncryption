package bl;

import data.AccountInfo;

import java.util.ArrayList;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class AccountBL {
    private static AccountBL ourInstance = new AccountBL();

    public static AccountBL getInstance() {
        return ourInstance;
    }

    private AccountBL() {
    }

    public void add(AccountInfo accountInfo) {

    }

    public ArrayList<AccountInfo> showAll() {
        return null;
    }
}

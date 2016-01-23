package data;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class AccountInfo {

    private final String site;
    private final String password;
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
}

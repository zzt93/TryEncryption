package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Created by zzt on 1/23/16.
 * <p>
 * Usage:
 */
public class AccountMapper {

    Connection connection;

    public AccountMapper() throws SQLException {
        connection = ConnectionManager.getConnection();
    }

    public AccountInfo find(AccountInfo.Key key) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from account a where a.site = " + key.getKey());
        AccountInfo res;
        if (resultSet.next()) {
            res = new AccountInfo(resultSet.getString(1),
                    resultSet.getString(2), resultSet.getString(3));
        } else {
            throw new NoSuchElementException("no such account");
        }
        return res;
    }

    public ArrayList<AccountInfo> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM account");
        ArrayList<AccountInfo> accountInfos = new ArrayList<>();
        while (resultSet.next()) {
            accountInfos.add(new AccountInfo(resultSet.getString(1),
                    resultSet.getString(2), resultSet.getString(3)));
        }
        return accountInfos;
    }

    public boolean add(AccountInfo info) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.execute("INSERT INTO account VALUES ('" +
                info.getSite() + "', '" +
                info.getAccount() + "', '" +
                info.getPassword() + "');");
    }
}

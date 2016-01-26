package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by zzt on 1/24/16.
 * <p>
 * Usage:
 */
public class ConnectionManager {
    /**
     * The java.sql.Connection object represents a single logical database connection; It is important to note that a
     * Connection object is thread-safe and can be shared between threads without the need for additional
     * synchronization. On the other hand, a Statement object (created from a Connection object) is not thread-safe
     */
    private static Connection con;

    private static final String userName = "root";

    private static final String password = "";

    private static final String dbms = "mysql";

    private static final String serverName = "localhost";

    private static final String portNumber = "3306";

    public static final String ENCRYPTION = "encryption";

    static {
        Properties connectionProps = new Properties();
        connectionProps.put("user", ConnectionManager.userName);
        connectionProps.put("password", ConnectionManager.password);

        try {
            //            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:" + ConnectionManager.dbms + "://" +
                            ConnectionManager.serverName +
                            ":" + ConnectionManager.portNumber + "/" + ENCRYPTION + "?useUnicode=true&characterEncoding=UTF-8",
                    connectionProps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to database");
    }

    public static Connection getConnection() throws SQLException {
        if (con == null) {
            throw new SQLException("can't connect to server");
        }
        return con;
    }

}

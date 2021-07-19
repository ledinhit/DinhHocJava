import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    //jdbc:postgresql://<database_host>:<port>/<database_name>
    private static final String url = "jdbc:postgresql://192.168.12.24:5432/market_main_live_20210601";
    private static final String user = "sm";
    private static final String password = "WhzQamG5NUwNF45HGssKGkATZ";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}

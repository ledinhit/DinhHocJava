import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Buoi1 {
    public static void main(String[] args) {
        Connection conn = ConnectionDB.connect();
        try {
            // tạo đối tượng Statement
            Statement stmt = conn.createStatement();
            String sql = "select id, username, phone_number from \"user\" order by id desc limit 10";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("id") + "\t" +
                        rs.getString("username")  + "\t" +
                        rs.getString("phone_number"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

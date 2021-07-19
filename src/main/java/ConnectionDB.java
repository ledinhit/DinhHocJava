import java.sql.*;

public class ConnectionDB {

    //jdbc:postgresql://<database_host>:<port>/<database_name>
    //private static final String url = "jdbc:postgresql://192.168.12.24:5432/market_main_live_20210601";
    //Private static final String user = "sm";
    //private static final String password = "WhzQamG5NUwNF45HGssKGkATZ";

    private static final String user = "postgres";
    private static final String password = "123456";
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final Connection CONN = ConnectionDB.connect();

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

    public Product findProduct(int idInput) {
        try {
            // tạo đối tượng Statement
            Statement stmt = CONN.createStatement();
            String sql = String.format("select * from product where id = %s", idInput);
            //executeQuery() Phương thức này có công dụng thực thi một câu truy vấn bất kì và trả kết quả về thuộc kiểu ResultSet.
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product();
                int id = Integer.parseInt(rs.getString("id"));
                String name = rs.getString("name");
                double price = Double.parseDouble(rs.getString("price"));
                int inventory = Integer.parseInt(rs.getString("inventory"));
                product.setId(id);
                product.setName(name);
                product.setPrice(price);
                product.setInventory(inventory);
                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getProduct() {
        try {
            // tạo đối tượng Statement
            Statement stmt = CONN.createStatement();
            String sql = "select id, name, price, inventory from product";
            //executeQuery() Phương thức này có công dụng thực thi một câu truy vấn bất kì và trả kết quả về thuộc kiểu ResultSet.
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product();
                System.out.println(rs.getString("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("price") + "\t" +
                        rs.getString("inventory"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    int insertProduct() {

        String sqlInsertProduct = "insert into product(name,price,inventory) values(?,?,?)";

        // for insert a new candidate
        ResultSet rs = null;
        int productId = 0;

        try {
            PreparedStatement pstmt = CONN.prepareStatement(sqlInsertProduct, Statement.RETURN_GENERATED_KEYS);
            // set parameters for statement
            pstmt.setString(1, getName());
            pstmt.setDouble(2, getPrice());
            pstmt.setInt(3, getInventory());

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    productId = rs.getInt(1);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return productId;
    }
}

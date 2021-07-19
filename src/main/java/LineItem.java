import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LineItem {
    //khai báo các thuộc tính sản phẩm
    private int id;
    private int orderId;
    private String name;
    private double price;
    private int quantity;
    private int productId;
    Locale localeEN = new Locale("en", "EN");
    NumberFormat en = NumberFormat.getInstance(localeEN);
    private static final Connection CONN = ConnectionDB.connect();

    //------------------begin getter and setter----------------------
    //Trả về tên sản phẩm của đối tượng
    public String getName() {
        return name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    //Gán giá trị cho thuộc tính tên sản phẩm của đối tượng
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    //-----------------------end getter and setter--------------------

    // Phương thức hiển thị danh sách sản phẩm trong Order

    public void inLineItem() {
        System.out.printf("%10d %10d %10d %20s %20s  %20d \n", id, orderId, productId, name, en.format(price), quantity);
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quality=" + quantity +
                ", productId=" + productId +
                ", localeEN=" + localeEN +
                ", en=" + en +
                '}';
    }

    public int insertLineItem() {
        String sqlInsertProduct = "insert into order(orderId,productId,name,price,quantity) values(?,?,?,?,?)";

        // for insert a new candidate
        ResultSet rs = null;
        int itemId = 0;
//        Order order = new Order();

        try {
            PreparedStatement pstmt = CONN.prepareStatement(sqlInsertProduct, Statement.RETURN_GENERATED_KEYS);
            // set parameters for statement
            pstmt.setInt(1, getOrderId());
            pstmt.setInt(2, getProductId());
            pstmt.setString(3, getName());
            pstmt.setDouble(4, getPrice());
            pstmt.setInt(5, getQuantity());


            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    itemId = rs.getInt(1);

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

        return itemId;
    }

}
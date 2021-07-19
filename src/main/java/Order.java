import java.sql.*;

public class Order {
    private static final Connection CONN = ConnectionDB.connect();
    int id;
    double totalAmount;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public int insertOrder() {
        String sqlInsertOrder = "insert into order(totalAmount) values(?)";

        // for insert a new candidate
        ResultSet rs = null;
        int orderId = 0;

        try {
            PreparedStatement pstmt = CONN.prepareStatement(sqlInsertOrder, Statement.RETURN_GENERATED_KEYS);
            // set parameters for statement
            pstmt.setDouble(1, getTotalAmount());

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    orderId = rs.getInt(1);

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

        return orderId;
    }

}
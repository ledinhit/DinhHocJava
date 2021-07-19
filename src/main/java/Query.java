
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Query {
    static Scanner sc = new Scanner(System.in);

    static void addProduct() {
        System.out.println("Nhập Tên sản phẩm: ");
        sc.nextLine();
        prd.setName(sc.nextLine());
        System.out.println("Nhập Giá sản phẩm: ");
        prd.setPrice(sc.nextDouble());
        System.out.println("Nhập Số lượng sản phẩm: ");
        prd.setInventory(sc.nextInt());
        int id = prd.insertProduct();
    }

    static void selectProduct(List<LineItem> lineItems, int orderId) {

        // Nếu nhập id sản phẩm không tồn tại
        System.out.println("Nhập id sản phẩm muốn mua:");
        int idProdSelected = sc.nextInt();
        // Kiểm tra xem idProd có tồn tại trong bảng Product hay không
        Product product = prd.findProduct(idProdSelected);
        if (product == null) {
            System.out.println("Sản phầm bạn chọn không tồn tại trong danh sách sản phẩm của chúng tôi");
            selectProduct(lineItems, orderId);
        } else {
            int quantity = inputQuantity(product);
            LineItem lineItem = new LineItem();
            lineItem.setId(product.getId());
            lineItem.setName(product.getName());
            lineItem.setQuantity(quantity);
            lineItem.setOrderId(orderId);
            lineItem.setPrice(product.getPrice());
            lineItems.add(lineItem);
            System.out.println("Bạn muốn mua mặt hàng khác nữa hay không: 1. Có, 0. Không");
            int action = sc.nextInt();
            if (action == 1) {
                selectProduct(lineItems, orderId);
            }
        }
    }

    public static int inputQuantity(Product prd) {

        System.out.println("Nhập số lượng sản phẩm: ");
        int quantity = sc.nextInt();
        if (quantity <= prd.getInventory()) {
            prd.subtractInventory(quantity);
            return quantity;
        } else {
            System.out.println("Sản phầm không đủ để cung cấp");
            return inputQuantity(prd);
        }
    }

    public static void main(String[] args) {
        // thêm mới bản ghi
//        int n = 0;
//        System.out.println("Nhập số sản phẩm muốn thêm:");
//        n = sc.nextInt();
//        for (int i = 0; i < n; i++) {
//            System.out.println("Sản phẩm thứ " + (i +1) + ": ");
//            addProduct();
//        }
        //Hiển thị danh sách sản phẩm
        prd.getProduct();
        // B. Tạo đơn hàng
        double totalAmount = 0;
        System.out.println("\n================B. ĐẶT HÀNG================\n");
        List<LineItem> lineItems = new ArrayList<>();
        Order order = new Order();
        order.setId(order.insertOrder());
        selectProduct(lineItems, order.getId());
        System.out.println("\n================SẢN PHẨM TRONG ĐƠN HÀNG================\n");
        System.out.printf("%10s %10s %20s %20s %20s \n", "ID đơn hàng", "id Sản phẩm", "Tên sản phẩm", "Giá ", "Số lượng");
        for (LineItem lineItemInOrrder : lineItems) {
            lineItemInOrrder.inLineItem();
            totalAmount += (lineItemInOrrder.getQuantity() * lineItemInOrrder.getPrice());

        }

    }
}

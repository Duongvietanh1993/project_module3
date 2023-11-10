package models.orders;
import models.products.Products;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import static config.Color.*;

public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    private int productId;
    private int orderId;
    private String name;
    private double unitPrice;
    private int quantity;
    private Products product;

    public OrderDetail(int productId, int orderId, String name, double unitPrice, int quantity) {
        this.productId = productId;
        this.orderId = orderId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Products getProduct() {
        return product;
    }

    public String getFormattedUnitPrice() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(unitPrice);
    }

    DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Override
    public String toString() {
        return
                "Mã sản phẩm: " + productId +
                        " - Mã đơn hàng: " + orderId +
                        " - Tên sản phâm: " + name +
                        " - Giá tiền: " + unitPrice +
                        " - Số lượng: " + quantity;
    }

    public void display() {
        System.out.printf(BRIGHT_ORANGE_BOLD+"|  " + WHITE_BRIGHT + "%-3S" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-22s" + BRIGHT_ORANGE_BOLD + "|" + WHITE_BRIGHT + "%6s" + BRIGHT_ORANGE_BOLD + "    |" + WHITE_BRIGHT + "%22s" + BRIGHT_ORANGE_BOLD + "       |\n",
                productId, name, quantity, (formatter.format(unitPrice * quantity) + " VND"));
    }
}

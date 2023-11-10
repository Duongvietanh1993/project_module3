package models.carts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Carts implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private Map<Integer, Integer> products;
    private boolean status = false;

    public Carts(int cartId, int userId) {
        this.id = cartId;
        this.userId = userId;
        this.products = new HashMap<Integer, Integer>();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCartId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void addProduct(int product, int quantity) {
        products.put(product, quantity);
    }

    public void removeProduct(int product) {
        products.remove(product);
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public int getProductQuantity(int productId) {
        return products.getOrDefault(productId, 0);
    }

    public void setProductQuantity(int productId, int quantity) {
        products.put(productId, quantity);
    }


    @Override
    public String toString() {
        return
                "Mã giỏ hàng: " + id +
                        " - Mã người dùng: " + userId +
                        " - Danh sách giỏ hang: " + products+
                        " - Trạng thái: "+(status?"Đã Thanh Toán":"Chưa Thanh Toán");
    }
}

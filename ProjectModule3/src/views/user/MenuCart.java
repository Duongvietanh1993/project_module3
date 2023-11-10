package views.user;

import config.Config;
import config.Input;
import config.regex.ValidateRegex;
import models.carts.Carts;
import models.orders.OrderDetail;
import models.orders.OrderStatus;
import models.orders.Orders;
import models.products.Products;
import models.user.Users;
import service.cart.CartIMPL;
import service.cart.ICartService;
import service.order.OrderIMPL;
import service.product.IProductService;
import service.product.ProductIMPL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static config.Color.*;

import static config.Color.BRIGHT_ORANGE_BOLD;


public class MenuCart {
    static Config<Users> usersConfig = new Config();
    static Config<Carts> cartsConfig = new Config();
    OrderIMPL orderIMPL = new OrderIMPL();
    IProductService productService;
    ICartService cartService;
    ValidateRegex validateRegex = new ValidateRegex();

    private String formatCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,### VND");
        return formatter.format(amount);
    }

    int totalCart = 0;

    public void showCart() {
        this.productService = new ProductIMPL();
        this.cartService = new CartIMPL();
        int menuWidth = 30;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        if (cartService != null && productService != null) {
            System.out.println(BRIGHT_ORANGE_BOLD + ".-----------------------------" + WHITE_BOLD_BRIGHT + "GIỎ HÀNG" + BRIGHT_ORANGE_BOLD + "---------------------------------.");
            System.out.println("|    " + WHITE_BOLD_BRIGHT + "[0]. QUAY LẠI                           " + greeting + BRIGHT_ORANGE_BOLD + "|");
            System.out.println("|----------------------------------------------------------------------|" + RESET);

            List<Carts> cartList = cartService.findAll();
            if (cartList.isEmpty()) {
                System.out.println(BRIGHT_ORANGE_BOLD + "|                          " + WHITE_BOLD_BRIGHT + "Giỏ hàng trống!" + BRIGHT_ORANGE_BOLD + "                             |" + RESET);
            } else {
                System.out.println(BRIGHT_ORANGE_BOLD + "|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |       " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "         |   " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + "   |        " + BRIGHT_ORANGE_BOLD + "GIÁ" + BRIGHT_ORANGE_BOLD + "         |");
                System.out.println("|----------------------------------------------------------------------|" + RESET);


                for (Carts cart : cartList) {
                    Map<Integer, Integer> products = cart.getProducts();
                    for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                        int productId = entry.getKey();
                        int quantity = entry.getValue();

                        Products product = productService.findById(productId);
                        if (product != null) {
                            double totalPrice = product.getUnitPrice() * quantity;
                            totalCart += totalPrice;
                            String formattedPrice = formatCurrency(totalPrice);
                            System.out.printf(BRIGHT_ORANGE_BOLD + "|" + RESET + "%3d" + BRIGHT_ORANGE_BOLD + "  |   " + RESET + "%-24s" + BRIGHT_ORANGE_BOLD + " |" + RESET + "%8d" + BRIGHT_ORANGE_BOLD + "      |" + RESET + "%18s" + BRIGHT_ORANGE_BOLD + "  |\n" + RESET, product.getId(), product.getProductName(), quantity, formattedPrice);
                        }
                    }
                }
            }
            String formattedPriceCart = formatCurrency(totalCart);
            int menuTotal = 31;
            String totalAll = String.format("TỔNG GIÁ TRỊ ĐƠN HÀNG: %-" + (menuTotal - 14) + "s", formattedPriceCart);
            System.out.println(BRIGHT_ORANGE_BOLD+"|----------------------------------------------------------------------|");
            System.out.println("|                              " + WHITE_BOLD_BRIGHT + totalAll + BRIGHT_ORANGE_BOLD + "|");
            System.out.println("|----------------------------------------------------------------------|");
            System.out.println("|    " + ORANGE_BOLD + "1. Tăng số lượng sản phẩm" + BRIGHT_ORANGE_BOLD + "      |    " + ORANGE_BOLD + "2. Giảm số lượng sản phẩm" + BRIGHT_ORANGE_BOLD + "     |");
            System.out.println("|----------------------------------------------------------------------|");
            System.out.println("|    " + ORANGE_BOLD + "3. Xóa sản phẩm" + BRIGHT_ORANGE_BOLD + "                |    " + ORANGE_BOLD + "4. Thanh toán" + BRIGHT_ORANGE_BOLD + "                 |");
            System.out.println("'----------------------------------------------------------------------'" + RESET);

            System.out.print("Vui lòng chọn: ");
            int choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    increaseProductQuantity();
                    break;
                case 2:
                    decreaseProductQuantity();
                    break;
                case 3:
                    removeProductFromCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private void increaseProductQuantity() {
        System.out.print("Nhập mã sản phẩm muốn tăng số lượng: ");
        int productId = Input.inputInteger();
        System.out.print("Nhập số lượng muốn tăng: ");
        int quantity = Input.inputInteger();

        Products product = productService.findById(productId);
        if (product != null) {
            Carts cart = cartService.findByUserId(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
            if (cart != null) {
                int currentQuantity = cart.getProductQuantity(productId);
                int newQuantity = currentQuantity + quantity;
                if (newQuantity <= product.getStock()) {
                    cart.setProductQuantity(productId, newQuantity);
                    cartService.save(cart);
                    product.setStock(product.getStock() - quantity); // Trừ số lượng từ kho
                    productService.save(product); // Lưu lại số lượng tồn kho
                    System.out.println("Tăng số lượng sản phẩm thành công.");
                } else {
                    System.out.println("Không đủ số lượng sản phẩm trong kho.");
                }
            }
        }
    }

    private void decreaseProductQuantity() {
        System.out.print("Nhập mã sản phẩm muốn giảm số lượng: ");
        int productId = Input.inputInteger();
        System.out.print("Nhập số lượng muốn giảm: ");
        int quantity = Input.inputInteger();

        Products product = productService.findById(productId);
        if (product != null) {
            Carts cart = cartService.findByUserId(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
            if (cart != null) {
                int currentQuantity = cart.getProductQuantity(productId);
                int newQuantity = currentQuantity - quantity;
                if (newQuantity > 0) {
                    cart.setProductQuantity(productId, newQuantity);
                    cartService.save(cart);
                    product.setStock(product.getStock() + quantity); // Trả lại số lượng vào kho
                    productService.save(product); // Lưu lại số lượng tồn kho
                    System.out.println("Giảm số lượng sản phẩm thành công.");
                } else {
                    cart.removeProduct(productId);
                    cartService.save(cart);
                    product.setStock(product.getStock() + quantity); // Trả lại số lượng vào kho
                    productService.save(product); // Lưu lại số lượng tồn kho
                    System.out.println("Xóa sản phẩm khỏi giỏ hàng thành công.");
                }
            }
        }
    }

    private void removeProductFromCart() {
        System.out.print("Nhập mã sản phẩm muốn xóa: ");
        int productId = Input.inputInteger();

        Carts cart = cartService.findByUserId(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
        if (cart != null) {
            Products product = productService.findById(productId); // Lấy thông tin sản phẩm từ lớp dịch vụ sản phẩm
            if (product != null) {
                int quantity = cart.getProductQuantity(productId);
                cart.removeProduct(productId);
                cartService.save(cart);
                product.setStock(product.getStock() + quantity); // Trả lại số lượng vào kho
                productService.save(product); // Lưu lại số lượng tồn kho
                System.out.println("Xóa sản phẩm khỏi giỏ hàng thành công.");
            } else {
                System.out.println("Sản phẩm không tồn tại.");
            }
        } else {
            System.out.println("Giỏ hàng không tồn tại.");
        }
    }

    private void checkout() {
        Carts cart = cartService.findByUserId(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
        if (cart == null) {
            System.out.println("Giỏ hàng không tồn tại.");
            return;
        }

        // Kiểm tra số lượng tồn kho trước khi thanh toán
        Map<Integer, Integer> products = cart.getProducts();
        boolean hasEnoughStock = true;

        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            Products product = productService.findById(productId);
            if (product == null) {
                continue;
            }

            if (quantity > product.getStock()) {
                hasEnoughStock = false;
                System.out.printf("Sản phẩm '%s' không đủ số lượng trong kho.\n", product.getProductName());
            }
        }

        if (!hasEnoughStock) {
            return;
        }

        // Cập nhật số lượng tồn kho
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            Products product = productService.findById(productId);
            if (product == null) {
                continue;
            }

            int newStock = product.getStock() - quantity;
            product.setStock(newStock);
            productService.save(product);
        }

        // Tạo danh sách order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        int orderId = orderIMPL.getNewId();

        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            Products product = productService.findById(productId);
            if (product == null) {
                continue;
            }

            double unitPrice = product.getUnitPrice();
            String name = product.getProductName();
            OrderDetail orderDetail = new OrderDetail(productId, orderId, name, unitPrice, quantity);
            orderDetails.add(orderDetail);
        }

        // Nhập thông tin đơn hàng
        int userId = usersConfig.readFile(Config.URL_USER_LOGIN).getId();
        String name = usersConfig.readFile(Config.URL_USER_LOGIN).getFullName();
        System.out.println("Nhập vào số điện thoại nhận hàng: ");
        String phone = validateRegex.validatePhone();
        System.out.println("Nhập vào địa chỉ nhận hàng: ");
        String address = validateRegex.validateAddress();
        double total = totalCart;
        Date orderDate = new Date();
        OrderStatus orderStatus = OrderStatus.WAITING;
        Orders newOrder = new Orders(orderId, userId, name, phone, address, total, orderStatus, orderDetails, orderDate);

        // Lưu đơn hàng
        orderIMPL.save(newOrder);

        // Xóa giỏ hàng sau khi thanh toán
        cartsConfig.writeFile(Config.URL_CART, null);
        System.out.println(GREEN+"Thanh toán thành công. Cảm ơn bạn đã mua hàng!"+RESET);
    }
}

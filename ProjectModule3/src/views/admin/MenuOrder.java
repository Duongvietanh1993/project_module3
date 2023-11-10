package views.admin;
import config.Config;
import config.Input;
import models.orders.OrderDetail;
import models.orders.OrderStatus;
import models.orders.Orders;
import models.products.Products;
import models.user.Users;
import service.order.IOrderService;
import service.order.OrderIMPL;
import service.product.IProductService;
import service.product.ProductIMPL;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import static config.Color.*;


public class MenuOrder {
    static Config<Users> usersConfig = new Config();
    IOrderService orderService = new OrderIMPL();


    public void menuOrder() {
        int menuWidth = 22;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu =BRIGHT_ORANGE_BOLD+
                ".-----------------------------" + WHITE_BOLD_BRIGHT + "TRANG ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "----------------------------.\n" +
                        "|    " + WHITE_BOLD_BRIGHT + "[0]. Quay lại                                    " + greeting +BRIGHT_ORANGE_BOLD+ "|\n" +
                        "|-----------------------------------------------------------------------|\n" +
                        "|                     " + WHITE_BOLD_BRIGHT + "1. Hiển thị danh sách đơn hàng" + BRIGHT_ORANGE_BOLD + "                    |\n" +
                        "|                     " + WHITE_BOLD_BRIGHT + "2. Tìm kiếm đơn hàng" + BRIGHT_ORANGE_BOLD + "                              |\n" +
                        "|                     " + WHITE_BOLD_BRIGHT + "3. Sắp xếp theo giời gian đặt hàng" + BRIGHT_ORANGE_BOLD + "                |\n" +
                        "'-----------------------------------------------------------------------'"+RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.print("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    showOrder();
                    break;
                case 2:
                    searchOrderByCustomerName();
                    break;
                case 3:
                    sortByOrderPlacementTime();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void showOrder() {
        if (orderService != null) {
            System.out.println(BRIGHT_ORANGE_BOLD+".---------------------------------------------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "--------------------------------------------------------------------------.");
            System.out.println("|                                                                                                                                                                 |");
            System.out.println("|-" + WHITE_BOLD_BRIGHT + "MÃ ĐƠN" + BRIGHT_ORANGE_BOLD + "-|-----" + WHITE_BOLD_BRIGHT + "TÊN NGƯỜI ĐẶT" + BRIGHT_ORANGE_BOLD + "-----|----" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + BRIGHT_ORANGE_BOLD + "----|--------------" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ GIAO HÀNG" + BRIGHT_ORANGE_BOLD + "-------------|---" + WHITE_BOLD_BRIGHT + "GIỜI GIAN ĐẶT HÀNG" + BRIGHT_ORANGE_BOLD + "---|-----" + WHITE_BOLD_BRIGHT + "GIÁ TRỊ ĐƠN" + BRIGHT_ORANGE_BOLD + "-----|--" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "--|");

            if (orderService.findAll().isEmpty()) {
                System.out.println("|                                                                                                                                                           |");
                System.out.println("|                                                                Danh mục đang trống!                                                                       |"+RESET);
            } else {

                for (Orders orders : orderService.findAll()) {
                    orders.display();
                }
            }
        } else {
            System.err.println("Không tìm thấy danh sách đơn hàng!");
        }


        System.out.println(BRIGHT_ORANGE_BOLD+"|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|              " + WHITE_BOLD_BRIGHT + "1. XEM CHI TIẾT ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "               |               " + WHITE_BOLD_BRIGHT + "2. THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "               |                 " + WHITE_BOLD_BRIGHT + "0. QUAY LẠI" + BRIGHT_ORANGE_BOLD + "                 |");
        System.out.println("'-----------------------------------------------------------------------------------------------------------------------------------------------------------------'"+RESET);

        System.out.print("Mời nhập lựa chọn: ");
        int choice = Input.inputInteger();
        switch (choice) {
            case 1:
                orderDetailShow();
                break;
            case 2:
                changeOrderStatus();
                break;
            case 0:
                break;
            default:
                System.err.println("Lựa chọn không hợp lệ");
        }

    }

    public void orderDetailShow() {
        System.out.print("Nhập ID đơn hàng: ");
        int orderIdEdit = Input.inputInteger();
        Orders foundOrders = orderService.findById(orderIdEdit);
        if (foundOrders == null) {
            System.out.println("Không tìm thấy đơn hàng có ID: " + orderIdEdit);
        } else {
            System.out.println(BRIGHT_ORANGE_BOLD+".-----------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "------------------------.");
            System.out.println("|                                                                       |"+RESET);

            foundOrders.displayOrder();
            orderDetailProduct(foundOrders);

        }
    }


    public void changeOrderStatus() {
        System.out.print("Nhập ID đơn hàng: ");
        int orderId = Input.inputInteger();
        Orders order = orderService.findById(orderId);
        if (order == null) {
            System.out.println("Không tìm thấy đơn hàng với mã đơn " + orderId);
            return;
        }

        if (order.getOrderStatus() != OrderStatus.WAITING) {
            System.out.println("Không thể thay đổi trạng thái cho đơn hàng này.");
            return;
        }

        System.out.println("Chọn trạng thái mới:");
        System.out.println("1. XÁC NHÂN ĐƠN HÀNG");
        System.out.println("2. HỦY ĐƠN HÀNG");
        System.out.print("Nhập số tương ứng với trạng thái mới: ");
        int choice = Input.inputInteger();

        OrderStatus newStatus;
        switch (choice) {
            case 1:
                newStatus = OrderStatus.CONFIRM;
                break;
            case 2:
                newStatus = OrderStatus.CANCEL;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        order.setOrderStatus(newStatus);
        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                int productId = orderDetail.getProductId();
                int quantity = orderDetail.getQuantity();

                // Lấy thông tin sản phẩm từ service
                IProductService productService = new ProductIMPL();
                Products product = productService.findById(productId);
                if (product != null) {
                    int currentStock = product.getStock();
                    product.setStock(currentStock + quantity);
                    productService.save(product);
                    System.out.println("Đã trả lại " + quantity + " sản phẩm vào kho cho sản phẩm có ID: " + productId);
                } else {
                    System.out.println("Không tìm thấy thông tin sản phẩm có ID: " + productId);
                }
            }
            System.out.println("Đơn hàng đã được hủy và số lượng sản phẩm đã được trả lại!");
        } else {
            orderService.save(order);
            System.out.println("Đã cập nhật trạng thái của đơn hàng " + orderId + " thành: " + order.getOrderStatus());

            // Lập lịch chuyển trạng thái sau một khoảng thời gian
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (order.getOrderStatus() == OrderStatus.CONFIRM) {
                        order.setOrderStatus(OrderStatus.DELIVERY);
                        orderService.save(order);
                        System.out.println("Đã chuyển trạng thái của đơn hàng " + orderId + " thành: " + order.getOrderStatus());
                        // Lập lịch chuyển trạng thái thành công sau một khoảng thời gian
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (order.getOrderStatus() == OrderStatus.DELIVERY) {
                                    order.setOrderStatus(OrderStatus.SUCCESS);
                                    orderService.save(order);
                                    System.out.println("Đã chuyển trạng thái của đơn hàng " + orderId + " thành: " + order.getOrderStatus());
                                }
                            }
                        }, 6000); // Chuyển thành trạng thái SUCCESS sau 6s
                    }
                }
            }, 6000); // Chuyển thành trạng thái DELIVERY sau 6s
        }
    }

    public void searchOrderByCustomerName() {
        System.out.print("Nhập tên người đặt hàng: ");
        String customerName = Input.inputString();
        List<Orders> foundOrders = orderService.findByName(customerName);
        if (foundOrders.isEmpty()) {
            System.out.println("Không tìm thấy đơn hàng cho khách hàng: " + customerName);
        } else {
            System.out.println(BRIGHT_ORANGE_BOLD+".---------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "--------------------------.");
            System.out.println("|                                                                       |"+RESET);
            for (Orders order : foundOrders) {
                order.displayOrder();

                orderDetailProduct(order);
            }
        }
    }

    public void orderDetailProduct(Orders order) {

        System.out.println(BRIGHT_ORANGE_BOLD+"|----------------------------" + WHITE_BOLD_BRIGHT + "CHI TIẾT SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "--------------------------|");
        System.out.println("|                                                                       |");
        System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|------" + WHITE_BOLD_BRIGHT + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "------|-" + WHITE_BOLD_BRIGHT + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + "-|----------" + WHITE_BOLD_BRIGHT + "GIÁ TIỀN" + BRIGHT_ORANGE_BOLD + "-----------|");
        order.getOrderDetails().forEach(OrderDetail::display);
        System.out.println("'-----------------------------------------------------------------------'");
        System.out.println();
        System.out.println(".---------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "--------------------------.");
        System.out.println("|                                                                       |"+RESET);
    }

    public void sortByOrderPlacementTime() {
        List<Orders> allOrders = orderService.findAll();
        if (allOrders.isEmpty()) {
            System.out.println("Danh sách đơn hàng rỗng.");
            return;
        }

        Collections.sort(allOrders, Comparator.comparing(Orders::getOrderAt).reversed());

        System.out.println(BRIGHT_ORANGE_BOLD+".----------------------------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH ĐƠN HÀNG (SẮP XẾP THEO THỜI GIAN ĐẶT HÀNG)" + BRIGHT_ORANGE_BOLD + "---------------------------------------------------------.");
        System.out.println("|                                                                                                                                                                |");
        System.out.println("|-" + WHITE_BOLD_BRIGHT + "MÃ ĐƠN" + BRIGHT_ORANGE_BOLD + "-|-----" + WHITE_BOLD_BRIGHT + "TÊN NGƯỜI ĐẶT" + BRIGHT_ORANGE_BOLD + "-----|----" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + BRIGHT_ORANGE_BOLD + "----|--------------" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ GIAO HÀNG" + BRIGHT_ORANGE_BOLD + "-------------|---" + WHITE_BOLD_BRIGHT + "GIỜI GIAN ĐẶT HÀNG" + BRIGHT_ORANGE_BOLD + "---|-----" + WHITE_BOLD_BRIGHT + "GIÁ TRỊ ĐƠN" + BRIGHT_ORANGE_BOLD + "-----|--" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "--|");

        for (Orders order : allOrders) {
            order.display();
        }

        System.out.println("`-----------------------------------------------------------------------------------------------------------------------------------------------------------------'"+RESET);
    }
}

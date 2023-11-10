package models.orders;

import models.products.Products;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static config.Color.*;

public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    private int orderId;
    private int userId;
    private String name;
    private String phoneNumber;
    private String address;
    private double total;
    private OrderStatus orderStatus;
    private List<Products> products;
    private List<OrderDetail> orderDetails;
    private Date orderAt;
    private Date deliverAt;

    public Orders(int orderId, int userId, String name, String phoneNumber, String address, double total, OrderStatus orderStatus, List<OrderDetail> orderDetails, Date orderAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.total = total;
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
        this.orderAt = orderAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Date getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Date orderAt) {
        this.orderAt = orderAt;
    }

    public Date getDeliverAt() {
        return deliverAt;
    }

    public void setDeliverAt(Date deliverAt) {
        this.deliverAt = deliverAt;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    @Override
    public String toString() {
        return
                "Mã đơn hàng: " + orderId +
                        " - Mã người dùng: " + userId +
                        " - Tên người đặt: " + name +
                        " - Số điện thoại: " + phoneNumber +
                        " - Địa chỉ: " + address +
                        " - Tổng giá tiền: " + total +
                        " - Trạng thái đơn hàng: " + orderStatus +
                        " - Chi tiết đơn hàng: " + orderDetails +
                        " - Thời gian đặt hàng: " + dateFormat.format(orderAt);
    }


    public void display() {

        System.out.printf(BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-5S" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-20s" + BRIGHT_ORANGE_BOLD + "|    " + WHITE_BRIGHT + "%-17s" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-41s" + BRIGHT_ORANGE_BOLD + "|    " + WHITE_BRIGHT + "%-20s" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%17s" + BRIGHT_ORANGE_BOLD + "  |   " + WHITE_BRIGHT + "%-18s" + BRIGHT_ORANGE_BOLD + "|\n",
                orderId, name, phoneNumber, address, dateFormat.format(orderAt), (formatter.format(total) + " VND"),
                (orderStatus == OrderStatus.WAITING ? PURPLE_BOLD_BRIGHT + "Đang Đợi" : orderStatus == OrderStatus.CONFIRM ? CYAN_BOLD_BRIGHT + "Xác Nhận" : orderStatus == OrderStatus.DELIVERY ? YELLOW_BOLD_BRIGHT + "Đang Giao" : orderStatus == OrderStatus.CANCEL ? RED_BOLD_BRIGHT + "Hủy Đơn" : GREEN_BOLD_BRIGHT + "Đã Giao"));
    }

    public void displayOrder() {

        System.out.printf(BRIGHT_ORANGE_BOLD+"|  " + WHITE_BRIGHT + "Mã sản phẩm: %-20S   Trạng thái đơn: %-24s" + BRIGHT_ORANGE_BOLD + "|\n",
                orderId, (orderStatus == OrderStatus.WAITING ? PURPLE_BOLD_BRIGHT + "Đang Đợi" : orderStatus == OrderStatus.CONFIRM ? CYAN_BOLD_BRIGHT + "Xác Nhận" : orderStatus == OrderStatus.DELIVERY ? YELLOW_BOLD_BRIGHT + "Đang Giao" : orderStatus == OrderStatus.CANCEL ? RED_BOLD_BRIGHT + "Hủy Đơn" : GREEN_BOLD_BRIGHT + "Đã Giao"));
        System.out.printf(BRIGHT_ORANGE_BOLD+"|  " + WHITE_BRIGHT + "Tên người đặt: %-20S Số điện thoại: %-18s" + BRIGHT_ORANGE_BOLD + "|\n",
                name, phoneNumber);
        System.out.printf(BRIGHT_ORANGE_BOLD+"|  " + WHITE_BRIGHT + "Thời gian đặt: %-20S Tổng giá tiền: %-18s" + BRIGHT_ORANGE_BOLD + "|\n",
                dateFormat.format(orderAt), (formatter.format(total) + " VND"));
        System.out.printf(BRIGHT_ORANGE_BOLD+"|  " + WHITE_BRIGHT + "Địa chỉ: %-59s" + BRIGHT_ORANGE_BOLD + " |\n",
                address);
    }

}
package models.user;

import java.io.*;
import java.util.Scanner;

import static config.Color.*;


public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String userName;
    private String email;
    private String address;
    private String fullName;
    private String password;
    private String phone;
    private boolean status = true;
    private RoleLogin role = RoleLogin.USER;

    public Users() {

    }

    public Users(int id, String fullName, String userName, String password, String email, String phone, String address, boolean status, RoleLogin role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(Scanner scanner) {
        System.out.println(
                "Trạng thái: " + '\n' +
                        "1.true" + '\n' +
                        "2.false");
        this.status = Integer.parseInt(scanner.nextLine()) == 1;
    }

    public RoleLogin getRole() {
        return role;
    }

    public void setRole(RoleLogin role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Mã người dùng: " + id +
                " - Họ và tên: " + fullName +
                " - Tên đăng nhập: " + userName +
                " - Mật khẩu: " + password +
                " - Email: " + email +
                " - Số điện thoại: " + phone +
                " - Số địa chỉ: " + address +
                " - Trạng thái: " + (status ? "Mở" : "Khóa") +
                " - Phân quyền: " + role;
    }

    public void display() {
        System.out.printf(BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-3S" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-15s" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-18s" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-16s" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-22s" + BRIGHT_ORANGE_BOLD + "|      " + WHITE_BRIGHT + "%-11s" + BRIGHT_ORANGE_BOLD + "|     " + WHITE_BRIGHT + "%-14s" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-9s" + BRIGHT_ORANGE_BOLD + "|\n",
                id, fullName, email, phone, address, userName, (status ? GREEN_BOLD_BRIGHT + "Mở" : RED_BOLD_BRIGHT + "Khóa"), role);
    }
}

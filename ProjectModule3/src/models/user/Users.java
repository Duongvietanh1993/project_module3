package models.user;

import java.io.Serializable;
import java.nio.charset.Charset;

public class Users implements Serializable {
    private static int count = 1;
    private String id;
    private String userName;
    private String email;
    private String address;
    private String fullName;
    private String password;
    private String phone;
    private boolean status = true;
    private RoleLogin role = RoleLogin.USER;

    public Users() {
        this.id = generateUserId();
    }

    public Users(String fullName, String userName, String password, String email, String phone,String address, boolean status, RoleLogin role) {
        this.id = generateUserId();
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.role = role;
    }

    private String generateUserId() {
        String id = "US" + String.format("%03d", count++);
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
                " - Trạng thái: " + (status ? "UNLOCK" : "LOCK") +
                " - Phân quyền: " + role;
    }


}

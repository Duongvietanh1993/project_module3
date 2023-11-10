package views.user;

import config.Config;
import config.Input;
import config.regex.ValidateRegex;
import models.orders.OrderDetail;
import models.orders.Orders;
import models.user.Users;
import service.order.IOrderService;
import service.order.OrderIMPL;
import service.user.UserIMPL;

import java.util.List;

import static config.Color.*;

public class MenuInfor {
    static Config<Users> usersConfig = new Config();
    UserIMPL userIMPL = new UserIMPL();
    ValidateRegex validateRegex = new ValidateRegex();
    Users userLogin = usersConfig.readFile(Config.URL_USER_LOGIN);
    IOrderService orderService = new OrderIMPL();

    public void menuInfor() {
        int menuWidth = 30;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", userLogin.getFullName());
        String menu = BRIGHT_ORANGE_BOLD +
                ".----------------------" + WHITE_BOLD_BRIGHT + "TRANG THÔNG TIN NGƯỜI DÙNG" + BRIGHT_ORANGE_BOLD + "-----------------------.\n" +
                "|    " + WHITE_BOLD_BRIGHT + "[0]. Quay lại                            " + greeting + BRIGHT_ORANGE_BOLD + "|\n" +
                "|-----------------------------------------------------------------------|\n" +
                "|                       " + WHITE_BOLD_BRIGHT + "1. Thông tin cá nhân" + BRIGHT_ORANGE_BOLD + "                            |\n" +
                "|                       " + WHITE_BOLD_BRIGHT + "2. Sửa thông tin cá nhân" + BRIGHT_ORANGE_BOLD + "                        |\n" +
                "|                       " + WHITE_BOLD_BRIGHT + "3. Đổi mật khẩu" + BRIGHT_ORANGE_BOLD + "                                 |\n" +
                "|                       " + WHITE_BOLD_BRIGHT + "4. Lịch sử mua hàng" + BRIGHT_ORANGE_BOLD + "                             |\n" +
                "'-----------------------------------------------------------------------'" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    showUser();
                    break;
                case 2:
                    editUser();
                    break;
                case 3:
                    editPassword();
                    break;
                case 4:
                    historyCart();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void showUser() {
        int nameWidth = 50;
        String name = String.format("Họ và tên: %-" + (nameWidth - 14) + "s", userLogin.getFullName());
        int phoneWidth = 46;
        String phone = String.format("Số điên thoại: %-" + (phoneWidth - 14) + "s", userLogin.getPhone());
        int addressWidth = 52;
        String address = String.format("Địa chỉ: %-" + (addressWidth - 14) + "s", userLogin.getAddress());
        int emailWidth = 54;
        String email = String.format("Email: %-" + (emailWidth - 14) + "s", userLogin.getEmail());
        int userNameWidth = 46;
        String userName = String.format("Tên đăng nhập: %-" + (userNameWidth - 14) + "s", userLogin.getUserName());

        String userInfor = BRIGHT_ORANGE_BOLD +
                ".-----------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN CÁ NHÂN" + BRIGHT_ORANGE_BOLD + "------------------------.\n" +
                "|                                                                      |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + name + BRIGHT_ORANGE_BOLD + " |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + phone + BRIGHT_ORANGE_BOLD + " |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + address + BRIGHT_ORANGE_BOLD + " |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + email + BRIGHT_ORANGE_BOLD + " |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + userName + BRIGHT_ORANGE_BOLD + " |\n" +
                "|                                                                      |\n" +
                "'----------------------------------------------------------------------'" + RESET;

        System.out.println(userInfor);
    }

    public void editUser() {
        System.out.println(CYAN_BOLD_BRIGHT + "<------------------------"+WHITE_BOLD_BRIGHT+"SỬA THÔNG TIN CÁ NHÂN"+CYAN_BOLD_BRIGHT+"------------------------>" + RESET);


        System.out.println("Họ và tên: ");
        String fullName = Input.inputString();
        if (fullName.isEmpty()) {
            userLogin.getUserName();
        } else {
            userLogin.setFullName(fullName);
        }

        System.out.println("Số điện thoại: ");
        String phone = Input.inputString();
        if (phone.isEmpty()) {
            userLogin.getPhone();
        } else {
            userLogin.setPhone(phone);
        }

        System.out.println("Địa chỉ: ");
        String address = Input.inputString();
        if (address.isEmpty()) {
            userLogin.getAddress();
        } else {
            userLogin.setAddress(address);
        }

        System.out.println("Email: ");
        String email = Input.inputString();
        if (email.isEmpty()) {
            userLogin.getEmail();
        } else {
            userLogin.setEmail(email);
        }

        userIMPL.save(userLogin);

        System.out.println("Thông tin cá nhân đã được cập nhật thành công.");
    }


    public void editPassword() {
        System.out.println(CYAN_BOLD_BRIGHT + "<----------------------------"+WHITE_BOLD_BRIGHT+"ĐỔI MẬT KHẨU"+CYAN_BOLD_BRIGHT+"----------------------------->" + RESET);

        boolean success = false;
        do {
            System.out.println("Mật khẩu cũ: ");
            String oldPassword = Input.inputString();
            if (!oldPassword.equals(userLogin.getPassword())) {
                System.err.println("Mật khẩu cũ không chính xác. Vui lòng thử lại.");
                continue;
            }

            System.out.println("Mật khẩu mới: ");
            String newPassword = Input.inputString();
            if (!validateRegex.checkPassword(newPassword)) {
                System.err.println("Mật khẩu mới không hợp lệ. Vui lòng thử lại.");
                continue;
            }

            if (newPassword.equals(oldPassword)) {
                System.err.println("Mật khẩu mới phải khác mật khẩu cũ. Vui lòng thử lại.");
                continue;
            }

            System.out.println("Xác nhận mật khẩu mới: ");
            String confirmPassword = Input.inputString();
            if (!newPassword.equals(confirmPassword)) {
                System.err.println("Xác nhận mật khẩu mới không khớp. Vui lòng thử lại.");
                continue;
            }

            userLogin.setPassword(newPassword);
            userIMPL.save(userLogin);
            usersConfig.writeFile(Config.URL_USER_LOGIN, userLogin);
            System.out.println("Đổi mật khẩu thành công.");
            success = true;
        } while (!success);
    }

    public void historyCart() {
        String userIdHistory = userLogin.getFullName();
        List<Orders> foundOrders = orderService.findByName(userIdHistory);
        if (foundOrders.isEmpty()) {
            System.out.println("Không tìm thấy đơn hàng cho người dùng: " + userLogin.getUserName());
        } else {
            System.out.println(BRIGHT_ORANGE_BOLD + ".---------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "--------------------------.");
            System.out.println("|                                                                       |" + RESET);
            for (Orders order : foundOrders) {
                order.displayOrder();

                System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------" + WHITE_BOLD_BRIGHT + "CHI TIẾT SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "--------------------------|");
                System.out.println("|                                                                       |");
                System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|------" + WHITE_BOLD_BRIGHT + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "------|-" + WHITE_BOLD_BRIGHT + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + "-|----------" + WHITE_BOLD_BRIGHT + "GIÁ TIỀN" + BRIGHT_ORANGE_BOLD + "-----------|");
                for (OrderDetail orderDetail : order.getOrderDetails()) {
                    orderDetail.display();
                }
                System.out.println("'-----------------------------------------------------------------------'");
                System.out.println();
                System.out.println(".---------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN ĐƠN HÀNG" + BRIGHT_ORANGE_BOLD + "--------------------------.");
                System.out.println("|                                                                       |"+RESET);
            }
        }
    }
}
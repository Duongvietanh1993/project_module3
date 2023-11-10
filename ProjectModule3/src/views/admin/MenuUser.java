package views.admin;

import config.Config;
import config.Input;
import models.user.RoleLogin;
import models.user.Users;
import service.user.IUserService;
import service.user.UserIMPL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static config.Color.*;

public class MenuUser {
    static Config<Users> usersConfig = new Config();
    IUserService userService = new UserIMPL();

    public void menuUser() {
        int menuWidth = 23;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu = BRIGHT_ORANGE_BOLD +
                ".---------------------------" + WHITE_BOLD_BRIGHT + "QUẢN LÝ NGƯỜI DÙNG" + BRIGHT_ORANGE_BOLD + "--------------------------.\n" +
                "|    " + WHITE_BOLD_BRIGHT + "[0]. Quay lại                                   " + greeting + BRIGHT_ORANGE_BOLD + "|\n" +
                "|-----------------------------------------------------------------------|\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "1. Hiển thị danh sách người dùng" + BRIGHT_ORANGE_BOLD + "                  |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "2. Tìm kiếm người dùng" + BRIGHT_ORANGE_BOLD + "                            |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "3. Đổi quyền cho tài khoản" + BRIGHT_ORANGE_BOLD + "                        |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "4. Đổi trạng thái cho tài khoản" + BRIGHT_ORANGE_BOLD + "                   |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "5. Sắp xếp danh sách người dùng" + BRIGHT_ORANGE_BOLD + "                   |\n" +
                "'-----------------------------------------------------------------------'" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    showUsers();
                    break;
                case 2:
                    findUserByName();
                    break;
                case 3:
                    editRole();
                    break;
                case 4:
                    ediStatus();
                    break;
                case 5:
                    softUser();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void showUsers() {
        if (userService != null) {
            System.out.println(BRIGHT_ORANGE_BOLD + ".--------------------------------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH TÀI KHOẢN" + BRIGHT_ORANGE_BOLD + "---------------------------------------------------------.");
            System.out.println("|                                                                                                                                    |");
            System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|----" + WHITE_BOLD_BRIGHT + "HỌ VÀ TÊN" + BRIGHT_ORANGE_BOLD + "----|--------" + WHITE_BOLD_BRIGHT + "EMAIL" + BRIGHT_ORANGE_BOLD + "-------|---" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + BRIGHT_ORANGE_BOLD + "--|---------" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ" + BRIGHT_ORANGE_BOLD + "--------|--" + WHITE_BOLD_BRIGHT + "TÊN ĐĂNG NHẬP" + BRIGHT_ORANGE_BOLD + "--|-" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "-|-" + WHITE_BOLD_BRIGHT + "PHÂN QUYỀN" + BRIGHT_ORANGE_BOLD + "-|");
            if (userService.findAll().isEmpty()) {
                System.out.println("|                                                                                                                                                |");
                System.out.println("|                                                               " + RED + "Danh mục đang trống!" + BRIGHT_ORANGE_BOLD + "                                                             |");
            } else {
                for (Users users : userService.findAll()) {
                    users.display();
                }
            }
            System.out.println("`------------------------------------------------------------------------------------------------------------------------------------'" + RESET);
            System.out.println();
        } else {
            System.out.println("Không tìm thấy thông tin danh mục.");
        }
    }

    public void findUserByName() {
        System.out.println(CYAN_BOLD_BRIGHT + "<------------------------" + WHITE_BOLD_BRIGHT + "TÌM KIẾM DANH MỤC THEO TÊN" + CYAN_BOLD_BRIGHT + "--------------------->" + RESET);
        System.out.println("Nhập tên tài khoản cần tìm: ");
        String userName = Input.inputString();

        List<Users> foundUsers = userService.findByName(userName);

        if (foundUsers.isEmpty()) {
            System.out.println("Không tìm thấy người dùng có tên '" + userName + "'.");
        } else {
            System.out.println(BRIGHT_ORANGE_BOLD + ".---------------------------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH TÀI KHOẢN TÌM THẤY" + BRIGHT_ORANGE_BOLD + "-----------------------------------------------------.");
            System.out.println("|                                                                                                                                    |");
            System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|----" + WHITE_BOLD_BRIGHT + "HỌ VÀ TÊN" + BRIGHT_ORANGE_BOLD + "----|--------" + WHITE_BOLD_BRIGHT + "EMAIL" + BRIGHT_ORANGE_BOLD + "-------|---" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + BRIGHT_ORANGE_BOLD + "--|---------" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ" + BRIGHT_ORANGE_BOLD + "--------|--" + WHITE_BOLD_BRIGHT + "TÊN ĐĂNG NHẬP" + BRIGHT_ORANGE_BOLD + "--|-" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "-|-" + WHITE_BOLD_BRIGHT + "PHÂN QUYỀN" + BRIGHT_ORANGE_BOLD + "-|" + RESET);

            for (Users users : foundUsers) {
                users.display();
            }

            System.out.println(BRIGHT_ORANGE_BOLD + "`------------------------------------------------------------------------------------------------------------------------------------'" + RESET);
        }
    }

    public void editRole() {

        System.out.println(CYAN_BOLD_BRIGHT + "<-------------------" + WHITE_BOLD_BRIGHT + "ĐỔI QUYỀN HẠN TÀI KHOẢN NGƯỜI DÙNG" + CYAN_BOLD_BRIGHT + "------------------>" + RESET);
        System.out.println("Nhập ID của tài khoản cần chỉnh sửa: ");
        int userId = Input.inputInteger();

        Users userEdit = userService.findById(userId);
        if (userId == 0) {
            System.err.println("Tài khoản ADMIN gốc không được sửa đổi.");
            return;
        }

        if (userEdit == null) {
            System.out.println("Không tìm thấy người dùng có ID '" + userId + "'.");
        } else {
            System.out.println(
                    "Chọn quyền hạn mới: " + '\n' +
                            "1.USER" + '\n' +
                            "2.ADMIN");
            int choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    userEdit.setRole(RoleLogin.USER);
                    System.out.println("Đã chuyển đổi người dùng thành USER!");
                    break;
                case 2:
                    userEdit.setRole(RoleLogin.ADMIN);
                    System.out.println("Đã chuyển đổi người dùng thành ADMIN!");
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ!");
            }
            userService.save(userEdit);
        }
    }

    public void ediStatus() {

        System.out.println(CYAN_BOLD_BRIGHT + "<------------------" + WHITE_BOLD_BRIGHT + "ĐỔI TRẠNG THÁI TÀI KHOẢN NGƯỜI DÙNG" + CYAN_BOLD_BRIGHT + "----------------->" + RESET);
        System.out.println("Nhập ID của danh mục cần chỉnh sửa: ");
        int userId = Input.inputInteger();

        Users userEdit = userService.findById(userId);
        if (userId == 0) {
            System.err.println("Tài khoản ADMIN gốc không được sửa đổi.");
            return;
        }
        if (userEdit == null) {
            System.out.println("Không tìm thấy người dùng có ID '" + userId + "'.");
        } else {
            Scanner scanner = new Scanner(System.in);
            userEdit.setStatus(scanner);
            userService.save(userEdit);
            System.out.println("Tài khoản đã được chỉnh sửa thành công.");
        }
    }
    public void softUser(){
//        Collections.sort(userService.findAll(), new Comparator<Users>() {
//            @Override
//            public int compare(Users o1, Users o2) {
//                return o2.getId()-o1.getId();
//            }
//        });
        userService.findAll().sort( (e1, e2)->e2.getId()-e1.getId());
        System.out.println(BRIGHT_ORANGE_BOLD + ".--------------------------------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH TÀI KHOẢN" + BRIGHT_ORANGE_BOLD + "---------------------------------------------------------.");
        System.out.println("|                                                                                                                                    |");
        System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|----" + WHITE_BOLD_BRIGHT + "HỌ VÀ TÊN" + BRIGHT_ORANGE_BOLD + "----|--------" + WHITE_BOLD_BRIGHT + "EMAIL" + BRIGHT_ORANGE_BOLD + "-------|---" + WHITE_BOLD_BRIGHT + "SỐ ĐIỆN THOẠI" + BRIGHT_ORANGE_BOLD + "--|---------" + WHITE_BOLD_BRIGHT + "ĐỊA CHỈ" + BRIGHT_ORANGE_BOLD + "--------|--" + WHITE_BOLD_BRIGHT + "TÊN ĐĂNG NHẬP" + BRIGHT_ORANGE_BOLD + "--|-" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "-|-" + WHITE_BOLD_BRIGHT + "PHÂN QUYỀN" + BRIGHT_ORANGE_BOLD + "-|");
        if (userService.findAll().isEmpty()) {
            System.out.println("|                                                                                                                                                |");
            System.out.println("|                                                               " + RED + "Danh mục đang trống!" + BRIGHT_ORANGE_BOLD + "                                                             |");
        } else {
            for (Users users : userService.findAll()) {
                users.display();
            }
        }
        System.out.println("`------------------------------------------------------------------------------------------------------------------------------------'" + RESET);
        System.out.println();
    }
}

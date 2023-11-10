package views.home;

import config.Config;
import config.Input;
import config.regex.ValidateRegex;
import models.user.RoleLogin;
import models.user.Users;
import service.user.IUserService;
import service.user.UserIMPL;
import views.admin.MenuAdmin;
import views.user.MenuUser;

import static config.Color.*;

public class MenuLogin {
    IUserService userService = new UserIMPL();
    ValidateRegex validateRegex = new ValidateRegex();
    Config<Users> usersConfig = new Config<>();

    public void showLoginMenu() {
//        for (Users users : userService.findAll()) {
//            System.out.println(users);
//        }

        String menu =
                BRIGHT_ORANGE_BOLD + ".----------------------------" + WHITE_BOLD_BRIGHT + "TRANG ĐĂNG NHẬP" + BRIGHT_ORANGE_BOLD + "---------------------------.\n" +
                        "|                             " + WHITE_BOLD_BRIGHT + "1. Đăng nhập" + BRIGHT_ORANGE_BOLD + "                             |\n" +
                        "|                             " + WHITE_BOLD_BRIGHT + "2. Đăng ký" + BRIGHT_ORANGE_BOLD + "                               |\n" +
                        "|                             " + WHITE_BOLD_BRIGHT + "0. Thoát" + BRIGHT_ORANGE_BOLD + "                                 |\n" +
                        "'----------------------------------------------------------------------'" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời Nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    registerUser();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void registerUser() {

        System.out.println(CYAN_BOLD_BRIGHT + "<--------------------------------" + WHITE_BOLD_BRIGHT + "ĐĂNG KÝ" + CYAN_BOLD_BRIGHT + "------------------------------->" + RESET);
        Users users = new Users();

        users.setId(userService.getNewId());

        System.out.println("Họ và tên: ");
        users.setFullName(validateRegex.validateFullName());

        System.out.println("Tên đăng nhập: ");
        while (true) {
            String userName = validateRegex.validateUserName();
            if (userService.existUserName(userName)) {
                System.err.println("Tên đăng nhập đã tồn tại, nhập lại tên khác!");
            } else {
                users.setUserName(userName);
                break;
            }
        }

        System.out.println("Mật khẩu: ");
        users.setPassword(validateRegex.validatePassword());
        System.out.println("Nhập lại mật khẩu: ");
        while (true) {
            String repeatPass = validateRegex.validatePassword();
            if (repeatPass.equals(users.getPassword())) {
                break;
            } else {
                System.err.println("Mật khẩu không khớp, mời nhập lại!");
            }
        }

        System.out.println("Email: ");
        while (true) {
            String email = validateRegex.validateEmail();
            if (userService.existEmail(email)) {
                System.err.println("Email đã tồn tại, nhập lại Emai khác!");
            } else {
                users.setEmail(email);
                break;
            }
        }

        System.out.println("Phone: ");
        users.setPhone(validateRegex.validatePhone());

        System.out.println("Địa chỉ: ");
        users.setAddress(validateRegex.validateAddress());

        userService.save(users);
        System.out.println("Tạo tài khoản thành công");
    }

    public void loginUser() {
        boolean isLoggedIn = false; // Biến để kiểm tra trạng thái đăng nhập
        do {
            System.out.println(CYAN_BOLD_BRIGHT + "<------------------------------" + WHITE_BOLD_BRIGHT + "ĐĂNG NHẬP" + CYAN_BOLD_BRIGHT + "------------------------------>" + RESET);
            System.out.println("Tên đăng nhập: ");
            String userName = validateRegex.validateUserName();
            System.out.println("Mật khẩu: ");
            String password = validateRegex.validatePassword();

            Users users = userService.checkLogin(userName, password);
            if (users == null) {
                System.err.println("Sai tên đăng nhập hoặc mật khẩu!");
            } else {
                checkRoleLogin(users);
                isLoggedIn = true; // Đánh dấu đã đăng nhập thành công
            }
        } while (!isLoggedIn);
    }

    public void checkRoleLogin(Users users) {
        if (users.getRole().equals(RoleLogin.ADMIN)) {

            //ghi đối tượng user vào file
            usersConfig.writeFile(Config.URL_USER_LOGIN, users);

            System.out.println(GREEN_BOLD_BRIGHT + "Admin đăng nhập thành công!" + RESET);
            new MenuAdmin().menuAdmin();
        } else {
            if (users.isStatus()) {
                //ghi đối tượng user vào file
                usersConfig.writeFile(Config.URL_USER_LOGIN, users);

                System.out.println(GREEN_BOLD_BRIGHT + "User đăng nhâp thành công!" + RESET);
                new MenuUser().menuUser();
            } else {
                System.err.println("Tài khoản đã bị khóa!");
            }
        }
    }
}
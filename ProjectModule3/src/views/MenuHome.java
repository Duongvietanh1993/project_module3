package views;

import config.Config;
import config.Input;
import config.regex.UserRegex;
import models.user.RoleLogin;
import models.user.Users;
import sevice.user.IUserSevice;
import sevice.user.UserIMPL;
import views.admin.MenuAdmin;
import views.user.MenuUser;
import static config.Color.*;

public class MenuHome {
    IUserSevice userSevice = new UserIMPL();
    UserRegex userRegex = new UserRegex();
    Config<Users> config = new Config<>();

    public void showLoginMenu() {
        for (Users users: userSevice.findAll()) {
            System.out.println(users);
        }

        String menu =
                CYAN+".----------------------------------MENU--------------------------------.\n" +
                        "|                             1. Đăng nhập                             |\n" +
                        "|                             2. Đăng ký                               |\n" +
                        "|                             0. Thoát                                 |\n" +
                        "'----------------------------------------------------------------------'"+RESET;
        int choice;
        do {
            System.out.println(menu);
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
        String menuRegister =
                "-------ĐĂNG KÝ-------";
        System.out.println(menuRegister);
        Users users = new Users();

        System.out.println("Họ và tên: ");
        users.setFullName(userRegex.validateFullName());

        System.out.println("Tên đăng nhập: ");
        while (true) {
            String userName = userRegex.validateUserName();
            if (userSevice.existUserName(userName)) {
                System.err.println("Tên đăng nhập đã tồn tại, nhập lại tên khác!");
            } else {
                users.setUserName(userName);
                break;
            }
        }

        System.out.println("Mật khẩu: ");
        users.setPassword(userRegex.validatePassword());
        System.out.println("Nhập lại mật khẩu: ");
        while (true) {
            String repeatPass = userRegex.validatePassword();
            if (repeatPass.equals(users.getPassword())) {
                break;
            } else {
                System.err.println("Mật khẩu không khớp, mời nhập lại!");
            }
        }

        System.out.println("Email: ");
        while (true) {
            String email = userRegex.validateEmail();
            if (userSevice.existEmail(email)) {
                System.err.println("Email đã tồn tại, nhập lại Emai khác!");
            } else {
                users.setEmail(email);
                break;
            }
        }

        System.out.println("Phone: ");
        users.setPhone(userRegex.validatePhone());

        System.out.println("Địa chỉ: ");
        users.setAddress(userRegex.validateAddress());

        userSevice.save(users);
        System.out.println("Tạo tài khoản thành công");
    }

    public void loginUser() {
        String menuLogin =
                "-------ĐĂNG NHẬP------";
        System.out.println(menuLogin);
        System.out.println("Tên đăng nhập: ");
        String userName = userRegex.validateUserName();
        System.out.println("Mật khẩu: ");
        String password = userRegex.validatePassword();

        Users users = userSevice.checkLogin(userName, password);
        if (users == null) {
            System.err.println("Sai tên đăng nhập hoặc mật khẩu!");
        } else {
            checkRoleLogin(users);
        }
    }
    public void checkRoleLogin(Users users){
        if (users.getRole().equals(RoleLogin.ADMIN)) {

            //ghi đối tượng user vào file
            config.writeFile(Config.URL_USER_LOGIN, users);

            System.out.println("Admin đăng nhập thành công!");
            new MenuAdmin().menuAdmin();
        } else {
            if (users.isStatus()) {
                //ghi đối tượng user vào file
                config.writeFile(Config.URL_USER_LOGIN, users);

                System.out.println("User đăng nhâp thành công!");
                new MenuUser().menuUser();
            } else {
                System.err.println("Tài khoản đã bị khóa!");
            }
        }
    }
}
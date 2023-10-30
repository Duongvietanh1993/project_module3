package views.user;

import config.Config;
import config.Input;
import models.user.Users;
import views.MenuHome;

public class MenuUser {
    static Config<Users> config = new Config();

    public void menuUser() {
        int menuWidth = 27;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", config.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu =
                ".--------------------------------MENU USER-----------------------------.\n" +
                        "|                                             " + greeting + " |\n" +
                        "|                                                                      |\n" +
                        "|                      1. Thông tin cá nhân                            |\n" +
                        "|                      2. Hiển thị danh sách sản phẩm                  |\n" +
                        "|                      3. Tìm kiếm sản phẩm theo tên                   |\n" +
                        "|                      4. Giỏ hàng                                     |\n" +
                        "|                      0. Đăng xuất                                    |\n" +
                        "|                                                                      |\n" +
                        "'----------------------------------------------------------------------'";
        int choice;
        do {
            System.out.println(menu);
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    new MenuInfor().menuInfor();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    logOut();
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void logOut() {
        config.writeFile(Config.URL_USER_LOGIN, null);
        new MenuHome().showLoginMenu();
    }


}

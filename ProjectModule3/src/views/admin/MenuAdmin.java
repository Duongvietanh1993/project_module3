package views.admin;

import config.Config;
import config.Input;
import models.user.Users;
import views.MenuHome;

public class MenuAdmin {
    static Config<Users> config = new Config();
    public void menuAdmin(){
        int menuWidth = 20;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", config.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu =
                        ".--------------------------------MENU ADMIN----------------------------.\n" +
                        "|                                                    " + greeting + "  |\n" +
                        "|                                                                      |\n" +
                        "|                           1. Quản lý Người Dùng                      |\n" +
                        "|                           2. Quản lý Danh Mục                        |\n" +
                        "|                           3. Quản lý Sản Phẩm                        |\n" +
                        "|                           0. Đăng xuất                               |\n" +
                        "|                                                                      |\n" +
                        "'----------------------------------------------------------------------'";
        int choice;
        do {
            System.out.println(menu);
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 0:
                    logOut();
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }
    public void logOut(){
config.writeFile(Config.URL_USER_LOGIN,null);
        new MenuHome().showLoginMenu();
    }
}

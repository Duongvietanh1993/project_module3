package views.admin;

import config.Config;
import config.Input;
import models.user.Users;
import views.home.MenuLogin;
import static config.Color.*;


public class MenuAdmin {
    static Config<Users> config = new Config();

    public void menuAdmin() {
        int menuWidth = 21;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", config.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu =BRIGHT_ORANGE_BOLD+
                        ".------------------------------" + WHITE_BOLD_BRIGHT + "TRANG QUẢN LÝ" + BRIGHT_ORANGE_BOLD + "----------------------------.\n" +
                        "|    " + WHITE_BOLD_BRIGHT + "[0]. Đăng xuất                                  "+WHITE_BOLD_BRIGHT + greeting + BRIGHT_ORANGE_BOLD+"  |\n" +
                        "|-----------------------------------------------------------------------|\n" +
                        "|                           " + WHITE_BOLD_BRIGHT + "1. Quản lý Người Dùng" + BRIGHT_ORANGE_BOLD + "                       |\n" +
                        "|                           " + WHITE_BOLD_BRIGHT + "2. Quản lý Danh Mục" + BRIGHT_ORANGE_BOLD + "                         |\n" +
                        "|                           " + WHITE_BOLD_BRIGHT + "3. Quản lý Sản Phẩm" + BRIGHT_ORANGE_BOLD + "                         |\n" +
                        "|                           " + WHITE_BOLD_BRIGHT + "4. Quản lý Đơn Hàng" + BRIGHT_ORANGE_BOLD + "                         |\n" +
                        "'-----------------------------------------------------------------------'"+RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    new MenuUser().menuUser();
                    break;
                case 2:
                    new MenuCatalog().menuCatalog();
                    break;
                case 3:
                    new MenuProduct().menuProduct();
                    break;
                case 4:
                    new MenuOrder().menuOrder();
                    break;
                case 0:
                    confirmLogOut();
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }
    public void confirmLogOut() {
        System.out.println("Bạn có muốn đăng xuất? (Nhập 'Y' để đăng xuất, 'N' để tiếp tục): ");
        String confirmLogout = Input.inputString();
        if (confirmLogout.equalsIgnoreCase("Y")) {
            logOut();
        }else {
           menuAdmin();
        }
    }
    public void logOut() {
        config.writeFile(Config.URL_USER_LOGIN, null);
        System.out.println(GREEN_BOLD_BRIGHT+"Đăng xuất...THANK YOU!"+RESET);
        new MenuLogin().showLoginMenu();
    }
}

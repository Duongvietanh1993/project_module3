package views;

import config.Config;
import models.user.Users;

public class Main {
    static MenuHome menuHome = new MenuHome();

    public static void main(String[] args) {
        Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
        if (userLogin != null) {
            menuHome.checkRoleLogin(userLogin);
        } else {
            menuHome.showLoginMenu();
        }
    }
}
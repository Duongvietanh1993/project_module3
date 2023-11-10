package views;

import config.Config;
import models.user.Users;
import views.home.MenuLogin;

public class Main {
    static MenuLogin menuLogin = new MenuLogin();

    public static void main(String[] args) {
        Users userLogin = new Config<Users>().readFile(Config.URL_USER_LOGIN);
        if (userLogin != null) {
            menuLogin.checkRoleLogin(userLogin);
        } else {
            menuLogin.showLoginMenu();
        }
    }
}
package views.user;

import config.Config;
import config.Input;
import config.regex.UserRegex;
import models.user.Users;
import sevice.user.UserIMPL;

public class MenuInfor {
    static Config<Users> config = new Config();
    UserIMPL userIMPL = new UserIMPL();
    UserRegex userRegex = new UserRegex();
    Users userLogin = config.readFile(Config.URL_USER_LOGIN);

    public void menuInfor() {
        int menuWidth = 26;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", userLogin.getFullName());
        String menu =
                ".--------------------------------MENU INFOR----------------------------.\n" +
                        "|                                             " + greeting + " |\n" +
                        "|                                                                      |\n" +
                        "|                      1. Thông tin cá nhân                            |\n" +
                        "|                      2. Sửa thông tin cá nhân                        |\n" +
                        "|                      3. Đổi mật khẩu                                 |\n" +
                        "|                      0. Quay lại                                     |\n" +
                        "|                                                                      |\n" +
                        "'----------------------------------------------------------------------'";
        int choice;
        do {
            System.out.println(menu);
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    showUser();
                    break;
                case 2:
                    editUser();
                    break;
                case 3:
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
        int UserNameWidth = 46;
        String userName = String.format("Tên đăng nhập: %-" + (UserNameWidth - 14) + "s", userLogin.getUserName());

        String userInfor =
                ".-----------------------------THÔNG TIN CÁ NHÂN------------------------.\n" +
                "|                                                                      |\n" +
                "|                      " +  name +" |\n" +
                "|                      " + phone + " |\n" +
                "|                      " + address + " |\n" +
                "|                      " + email + " |\n" +
                "|                      " + userName + " |\n" +
                "|                                                                      |\n" +
                "'----------------------------------------------------------------------'";

        System.out.println(userInfor);
    }
    public void editUser(){
        System.out.println("------SỬA THÔNG TIN CÁ NHÂN------");

        System.out.println("Họ và tên: ");
        String fullName =Input.inputString();
        if (fullName.isEmpty()) {
            userLogin.getUserName();
        }else {
            userLogin.setFullName(fullName);
        }

        System.out.println("Số điện thoại: ");
        String phone = Input.inputString();
        if(phone.isEmpty()) {
            userLogin.getPhone();
        }else {
            userLogin.setPhone(phone);
        }

        System.out.println("Địa chỉ: ");
        String address = Input.inputString();
        if (address.isEmpty()) {
            userLogin.getAddress();
        }else {
            userLogin.setAddress(address);
        }

        System.out.println("Email: ");
        String email = Input.inputString();
        if (email.isEmpty()) {
            userLogin.getEmail();
        }else {
            userLogin.setEmail(email);
        }

        userIMPL.save(userLogin);
    }
}

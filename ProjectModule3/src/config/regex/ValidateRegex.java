package config.regex;

import config.Input;

import java.util.regex.Pattern;

public class ValidateRegex {
    private static final String EMAIN_REGEX = "[a-zA-Z0-9]+(\\.[a-zA-Z0-9]+)*@[a-z]+(\\.[a-z]+){1,2}";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).{7,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String PHONE_REGEX = "\\+84\\.\\d{9}";
    private static final String USERNAME_REGEX = "[a-z0-9]+";

    String fullName;
    String address;
    String phoneNumber;
    String userName;
    String password;
    String email;


    public String validateFullName() {
        while (true) {
            fullName = Input.inputString();
            if (fullName.isEmpty()) {
                System.err.println("Họ và tên không được để trống, mời nhập lại!");
            }
            break;
        }
        return fullName;
    }
    public String validateAddress() {
        while (true) {
            address = Input.inputString();
            if (address.isEmpty()) {
                System.err.println("Địa chỉ không đuược để trống, mời nhập lại!");
            }
            break;
        }
        return address;
    }

    public String validateUserName() {
        do {
            userName = Input.inputString();
            if (userName.isEmpty()) {
                System.err.println("Tên đăng nhập không được để trống, mời nhập lại!");
            }else
            if (userName.matches(USERNAME_REGEX)) {
                return userName;
            } else System.err.println("Sai định dạng Tên đăng nhập, mời nhập lại!");
        } while (true);
    }

    public String validatePassword() {
        do {
            password = Input.inputString();
            if (password.isEmpty()) {
                System.err.println("Mật khẩu không được để trống, mời nhập lại!");
            }else
            if (password.matches(PASSWORD_REGEX)) {
                return password;
            } else System.err.println("Mật khẩu nhiều hơn 6 ký tự, ít nhất một chữ cái và một số!");
        } while (true);
    }
    public boolean checkPassword(String password){
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }

    public String validatePhone() {
        do {
            phoneNumber = Input.inputString();
            if (phoneNumber.isEmpty()) {
                System.err.println("Số điện thoại không được để trống, mời nhập lại!");
            }else
            if (phoneNumber.matches(PHONE_REGEX)) {
                return phoneNumber;
            } else System.err.println("Yêu cầu số điện thoại nhập đúng đinh dạng +84.xxx xxx xxx!");
        } while (true);
    }

    public String validateEmail() {
        do {
            email = Input.inputString();
            if (email.isEmpty()) {
                System.err.println("Email không được để trống, mời nhập lại!");
            }else
            if (email.matches(EMAIN_REGEX)) {
                return email;
            } else System.err.println("Sai định dạng Email, mời nhập lại!");
        } while (true);
    }


}

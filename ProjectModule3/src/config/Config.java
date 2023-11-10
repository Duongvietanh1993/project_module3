package config;

import java.io.*;
import java.util.Scanner;

public class Config<T> {
    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    static File fileUser = new File("ProjectModule3/data/user.csv");
    public static final String URL_USERS = String.valueOf(fileUser);

    static File fileProduct = new File("ProjectModule3/data/product.csv");
    public static final String URL_PRODUCT = String.valueOf(fileProduct);

    static File fileCategories = new File("ProjectModule3/data/catalog.csv");
    public static final String URL_CATALOG = String.valueOf(fileCategories);

    static File fileUserLogin = new File("ProjectModule3/data/userLogin.csv");
    public static final String URL_USER_LOGIN = String.valueOf(fileUserLogin);

    static File fileCart=new File("ProjectModule3/data/cart.csv");
    public static final String URL_CART = String.valueOf(fileCart);

    static File fileOrder=new File("ProjectModule3/data/order.csv");
    public static final String URL_ORDER = String.valueOf(fileOrder);

    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.err.println("Lỗi ghi file!!!");
        }
    }

    public T readFile(String PATH_FILE) {
        File file = new File(PATH_FILE);
        T t = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null) {
                fis.close();
            }
            ois.close();
        } catch (FileNotFoundException f) {
            System.err.println("Không tìm thấy file!!!");
        } catch (IOException i) {
            System.err.println("File rỗng!!!");
        } catch (ClassNotFoundException e) {
            System.err.println("Lớp ngoại lệ! ");
        }
        return t;
    }
}
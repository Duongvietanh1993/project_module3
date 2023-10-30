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

    static File fileCategories = new File("ProjectModule3/data/categories.csv");
    public static final String URL_CATEGORY = String.valueOf(fileCategories);

    static File fileUserLogin=new File("ProjectModule3/data/userLogin.csv");
    public static final String URL_USER_LOGIN = String.valueOf(fileUserLogin);

    public void writeFile(String PATH_FILE, T t) {
        File file = new File(PATH_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            System.err.println("Không tim thấy!!!");
        } catch (IOException e) {
            System.err.println("Ghi file lỗi!!!");
        }
    }

    public T readFile(String PATH_FILE){
        File file = new File(PATH_FILE);
        T t = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            t = (T) ois.readObject();
            if (fis != null){
                fis.close();
            }
            if (ois != null){
                ois.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tim thấy!!!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Đọc file lỗi!!!");
        }
        return t;
    }
}
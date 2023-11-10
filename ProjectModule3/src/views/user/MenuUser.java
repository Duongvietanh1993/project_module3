package views.user;
import config.Config;
import config.Input;
import models.carts.Carts;
import models.products.Products;
import models.user.Users;
import service.cart.CartIMPL;
import service.cart.ICartService;
import service.product.IProductService;
import service.product.ProductIMPL;
import views.home.MenuLogin;
import java.util.List;
import static config.Color.*;

public class MenuUser {
    static Config<Users> usersConfig = new Config();
    IProductService productService;
    ICartService cartService;

    public void menuUser() {
        this.productService = new ProductIMPL();
        this.cartService = new CartIMPL();
        int menuWidth = 35;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 21) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu = BRIGHT_ORANGE_BOLD + ".-----------------------------" + WHITE_BOLD_BRIGHT + "TRANG CHỦ" + BRIGHT_ORANGE_BOLD + "--------------------------------.\n" +
                "|    " + WHITE_BOLD_BRIGHT + "[0]. Đăng xuất                           " + greeting + BRIGHT_ORANGE_BOLD + " |\n" +
                "|----------------------------------------------------------------------|\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "1. Thông tin cá nhân" + RESET + BRIGHT_ORANGE_BOLD + "                            |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "2. Hiển thị danh sách sản phẩm" + RESET + BRIGHT_ORANGE_BOLD + "                  |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "3. Tìm kiếm sản phẩm theo tên" + RESET + BRIGHT_ORANGE_BOLD + "                   |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "4. Tìm kiếm sản phẩm theo danh mục" + RESET + BRIGHT_ORANGE_BOLD + "              |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "5. Giỏ hàng" + RESET + BRIGHT_ORANGE_BOLD + "                                     |\n" +
                "'----------------------------------------------------------------------'" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    new MenuInfor().menuInfor();
                    break;
                case 2:
                    showProductList();
                    break;
                case 3:
                    searchProductByName();
                    break;
                case 4:
                    searchProductByCatalog();
                    break;
                case 5:
                    new MenuCart().showCart();
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
            menuUser();
        }
    }
    public void logOut() {
        usersConfig.writeFile(Config.URL_USER_LOGIN, null);
        System.out.println(GREEN_BOLD_BRIGHT+"Đăng xuất...THANK YOU!"+RESET);
        new MenuLogin().showLoginMenu();

    }

    public void showProductList() {
        int menuWidth = 30;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        if (productService != null && cartService != null) {
            System.out.println(BRIGHT_ORANGE_BOLD + ".-----------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH SẢN PHẨM ĐANG BÁN" + BRIGHT_ORANGE_BOLD + "--------------------------------------------.");
            System.out.println(BRIGHT_ORANGE_BOLD + "|    " + WHITE_BOLD_BRIGHT + "[0]. Đăng xuất                                                              " + greeting + BRIGHT_ORANGE_BOLD + "|");
            System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------------------------------------------------------------------------------------|" + RESET);
            System.out.println(BRIGHT_ORANGE_BOLD + "|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |      " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "        |     " + BRIGHT_ORANGE_BOLD + "GIÁ BÁN" + BRIGHT_ORANGE_BOLD + "      |  " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + " |              " + BRIGHT_ORANGE_BOLD + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "              |" + RESET);
            System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------------------------------------------------------------------------------------|" + RESET);

            if (productService.findAll().isEmpty()) {
                System.out.println("|                                                                                              |");
                System.out.println("|                                Danh mục đang trống!                                          |");
            } else {
                boolean continueAdding = true;
                while (continueAdding) {
                    for (Products product : productService.findAll()) {
                        if (product.getCatalogs().isStatus()) {
                            if (product.isStatus()) {
                                product.displayHome();
                            }
                        }
                    }

                    System.out.println(BRIGHT_ORANGE_BOLD +"`----------------------------------------------------------------------------------------------------------'"+ RESET);

                    addProductToCart();

                    System.out.print("Bạn có muốn tiếp tục thêm sản phẩm vào giỏ hàng? (Y/N): ");
                    String choice = Input.inputString();
                    continueAdding = choice.equalsIgnoreCase("Y");
                    if (choice.equalsIgnoreCase("Y")) {
                        System.out.println();
                        System.out.println(BRIGHT_ORANGE_BOLD+".----------------------------------------------------------------------------------------------------------.");
                        System.out.println(BRIGHT_ORANGE_BOLD+"|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |      " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "        |     " + BRIGHT_ORANGE_BOLD + "GIÁ BÁN" + BRIGHT_ORANGE_BOLD + "      |  " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + " |              " + BRIGHT_ORANGE_BOLD + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "              |");
                        System.out.println(BRIGHT_ORANGE_BOLD+"|----------------------------------------------------------------------------------------------------------|"+RESET);
                    } else System.out.println();
                }
            }
        }
    }

    private void addProductToCart() {
        System.out.print("Nhập mã sản phẩm muốn thêm vào giỏ hàng (nhập 0 để quay lại): ");
        int productId = Input.inputInteger();

        // Kiểm tra nếu mã sản phẩm là 0, thoát khỏi phương thức
        if (productId == 0) {
            menuUser();
        }

        Products productBuy = productService.findById(productId);
        if (productBuy != null) {
            if (!productBuy.isStatus()) {
                System.out.println("Sản phẩm không có sẵn để mua.");
                return;
            }
            if (!productBuy.getCatalogs().isStatus()){
                System.out.println("Sản phẩm không có sẵn để mua.");
                return;
            }

            System.out.print("Nhập số lượng sản phẩm muốn thêm: ");
            int quantity = Input.inputInteger();

            Carts cart = cartService.findByUserId(usersConfig.readFile(Config.URL_USER_LOGIN).getId());
            if (cart == null) {
                cart = new Carts(cartService.getNewId(), usersConfig.readFile(Config.URL_USER_LOGIN).getId());
            }
            cart.addProduct(productId, quantity);
            cartService.save(cart);
            System.out.println("Thêm vào giỏ hàng thành công.");
        } else {
            System.out.println("Sản phẩm không tồn tại.");
        }
    }

    public void searchProductByName() {
        System.out.print("Nhập tên sản phẩm cần tìm kiếm: ");
        String productName = Input.inputString();
        List<Products> foundProducts = productService.findByName(productName);
        if (!foundProducts.isEmpty()) {
            int menuWidth = 30;
            String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
            if (productService != null && cartService != null) {
                System.out.println(BRIGHT_ORANGE_BOLD + ".-----------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH SẢN PHẨM TÌM KIẾM" + BRIGHT_ORANGE_BOLD + "--------------------------------------------.");
                System.out.println(BRIGHT_ORANGE_BOLD + "|    " + WHITE_BOLD_BRIGHT + "[0]. Đăng xuất                                                              " + greeting + BRIGHT_ORANGE_BOLD + "|");
                System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------------------------------------------------------------------------------------|" + RESET);
                System.out.println(BRIGHT_ORANGE_BOLD + "|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |      " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "        |     " + BRIGHT_ORANGE_BOLD + "GIÁ BÁN" + BRIGHT_ORANGE_BOLD + "      |  " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + " |              " + BRIGHT_ORANGE_BOLD + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "              |" + RESET);
                System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------------------------------------------------------------------------------------|" + RESET);

                if (productService.findAll().isEmpty()) {
                    System.out.println("|                                                                                              |");
                    System.out.println("|                                Danh mục đang trống!                                          |");
                } else {
                    boolean continueAdding = true;
                    while (continueAdding) {
                        for (Products product : foundProducts) {
                            if (product.getCatalogs().isStatus()) {
                                if (product.isStatus()) {
                                    product.displayHome();
                                }
                            }
                        }

                        System.out.println(BRIGHT_ORANGE_BOLD+"`----------------------------------------------------------------------------------------------------------'"+RESET);

                        addProductToCart();

                        System.out.print("Bạn có muốn tiếp tục thêm sản phẩm vào giỏ hàng? (Y/N): ");
                        String choice = Input.inputString();
                        continueAdding = choice.equalsIgnoreCase("Y");
                        if (choice.equalsIgnoreCase("Y")) {
                            System.out.println();
                            System.out.println(BRIGHT_ORANGE_BOLD+".----------------------------------------------------------------------------------------------------------.");
                            System.out.println(BRIGHT_ORANGE_BOLD+"|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |      " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "        |     " + BRIGHT_ORANGE_BOLD + "GIÁ BÁN" + BRIGHT_ORANGE_BOLD + "      |  " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + " |              " + BRIGHT_ORANGE_BOLD + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "              |");
                            System.out.println(BRIGHT_ORANGE_BOLD+"|----------------------------------------------------------------------------------------------------------|"+RESET);
                        } else System.out.println();
                    }
                }
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm.");
        }
    }

    public void searchProductByCatalog(){
        System.out.print("Nhập tên danh mục cần tìm kiếm: ");
        String productName = Input.inputString();
        List<Products> foundProducts = productService.findByName(productName);
        if (!foundProducts.isEmpty()) {
            int menuWidth = 30;
            String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
            if (productService != null && cartService != null) {
                System.out.println(BRIGHT_ORANGE_BOLD + ".-----------------------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH SẢN PHẨM TÌM KIẾM" + BRIGHT_ORANGE_BOLD + "--------------------------------------------.");
                System.out.println(BRIGHT_ORANGE_BOLD + "|    " + WHITE_BOLD_BRIGHT + "[0]. Đăng xuất                                                              " + greeting + BRIGHT_ORANGE_BOLD + "|");
                System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------------------------------------------------------------------------------------|" + RESET);
                System.out.println(BRIGHT_ORANGE_BOLD + "|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |      " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "        |     " + BRIGHT_ORANGE_BOLD + "GIÁ BÁN" + BRIGHT_ORANGE_BOLD + "      |  " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + " |              " + BRIGHT_ORANGE_BOLD + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "              |" + RESET);
                System.out.println(BRIGHT_ORANGE_BOLD + "|----------------------------------------------------------------------------------------------------------|" + RESET);

                if (productService.findAll().isEmpty()) {
                    System.out.println("|                                                                                              |");
                    System.out.println("|                                Danh mục đang trống!                                          |");
                } else {
                    boolean continueAdding = true;
                    while (continueAdding) {
                        for (Products product : foundProducts) {
                                    product.displayHome();
                        }

                        System.out.println(BRIGHT_ORANGE_BOLD+"`----------------------------------------------------------------------------------------------------------'"+RESET);

                        addProductToCart();

                        System.out.print("Bạn có muốn tiếp tục thêm sản phẩm vào giỏ hàng? (Y/N): ");
                        String choice = Input.inputString();
                        continueAdding = choice.equalsIgnoreCase("Y");
                        if (choice.equalsIgnoreCase("Y")) {
                            System.out.println();
                            System.out.println(BRIGHT_ORANGE_BOLD+".----------------------------------------------------------------------------------------------------------.");
                            System.out.println(BRIGHT_ORANGE_BOLD+"|  " + BRIGHT_ORANGE_BOLD + "*" + BRIGHT_ORANGE_BOLD + "  |      " + BRIGHT_ORANGE_BOLD + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "        |     " + BRIGHT_ORANGE_BOLD + "GIÁ BÁN" + BRIGHT_ORANGE_BOLD + "      |  " + BRIGHT_ORANGE_BOLD + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + " |              " + BRIGHT_ORANGE_BOLD + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "              |");
                            System.out.println(BRIGHT_ORANGE_BOLD+"|----------------------------------------------------------------------------------------------------------|"+RESET);
                        } else System.out.println();
                    }
                }
            }
        } else {
            System.out.println("Không tìm thấy sản phẩm.");
        }
    }
}

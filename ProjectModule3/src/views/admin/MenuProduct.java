package views.admin;
import config.Config;
import config.Input;
import models.catalogs.Catalogs;
import models.products.Products;
import models.user.Users;
import service.catalog.CatalogIMPL;
import service.catalog.ICatalogService;
import service.product.IProductService;
import service.product.ProductIMPL;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import static config.Color.*;

public class MenuProduct implements Serializable {
    static Config<Products> productsConfig = new Config<>();
    static Config<Users> usersConfig = new Config();
    IProductService productService = new ProductIMPL();
    ICatalogService catalogService = new CatalogIMPL();

    public void menuProduct() {
        int menuWidth = 23;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu = BRIGHT_ORANGE_BOLD +
                ".----------------------------" + WHITE_BOLD_BRIGHT + "QUẢN LÝ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "---------------------------.\n" +
                "|    " + WHITE_BOLD_BRIGHT + "[0]. Quay lại                                   " + greeting + BRIGHT_ORANGE_BOLD + "|\n" +
                "|-----------------------------------------------------------------------|\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "1. Hiển thị danh sách sản phẩm" + BRIGHT_ORANGE_BOLD + "                    |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "2. Thêm mới sản phẩm" + BRIGHT_ORANGE_BOLD + "                              |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "3. Sửa thông tin sản phẩm" + BRIGHT_ORANGE_BOLD + "                         |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "4. Tìm kiếm sản phẩm" + BRIGHT_ORANGE_BOLD + "                              |\n" +
                "|                     " + WHITE_BOLD_BRIGHT + "5. Sắp xếp theo giá giao dịch" + BRIGHT_ORANGE_BOLD + "                     |\n" +
                "'-----------------------------------------------------------------------'" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    showProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    editProduct();
                    break;
                case 4:
                    findProductByName();
                    break;
                case 5:
                    sortProductByUnitPrice();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void showProducts() {
        if (productService != null) {
            System.out.println(BRIGHT_ORANGE_BOLD + ".----------------------------------------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN CÁC SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "----------------------------------------------------------.");
            System.out.println("|                                                                                                                                          |");
            System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|-------" + WHITE_BOLD_BRIGHT + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "-------|----" + WHITE_BOLD_BRIGHT + "DANH MỤC" + BRIGHT_ORANGE_BOLD + "----|---------------" + WHITE_BOLD_BRIGHT + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "--------------|------" + WHITE_BOLD_BRIGHT + "ĐƠN GIÁ" + BRIGHT_ORANGE_BOLD + "-----|-" + WHITE_BOLD_BRIGHT + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + "-|--" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "--|");

            if (productService.findAll().isEmpty()) {
                System.out.println("|                                                                                                                                          |");
                System.out.println("|                                                        Danh mục đang trống!                                                              |");
            } else {
                for (Products products : productService.findAll()) {
                    products.display();
                }
            }
            System.out.println("`------------------------------------------------------------------------------------------------------------------------------------------'" + RESET);
            System.out.println();
        } else {
            System.out.println("Không tìm thấy thông tin danh mục.");
        }
    }

    public void addProduct() {

        System.out.println(CYAN_BOLD_BRIGHT + "<--------------------------" + WHITE_BOLD_BRIGHT + "THÊM MỚI SẢN PHẨM" + CYAN_BOLD_BRIGHT + "---------------------------->" + RESET);
        System.out.println("Nhập số lượng sản phẩm bạn muốn thêm mới: ");
        int numProducts = Input.inputInteger();

        for (int i = 0; i < numProducts; i++) {
            System.out.println("Nhập thông tin cho sản phẩm #" + (i + 1));

            int productId = productService.getNewId();
            System.out.println("Tên sản phẩm: ");

            String productName = Input.inputString();
            if (productService.existProductName(productName)) {
                System.err.println("Tên sản phẩm đã tồn tại. Vui lòng chọn tên khác!");
                i--;
                continue;
            }

            System.out.println("Danh sách danh mục sản phẩm: ");
            showCatalog();

            System.out.println("Chọn mã danh mục sản phẩm: ");
            int catalogId = Input.inputInteger();
            Catalogs selectedCatalog = catalogService.findById(catalogId);

            if (selectedCatalog != null) {
                System.out.println("Mô tả sản phẩm: ");
                String description = Input.inputString();

                System.out.println("Đơn giá: ");
                double unitPrice = Input.inputInteger();

                System.out.println("Số lượng trong kho: ");
                int stock = Input.inputInteger();

                Products newProduct = new Products(productId, productName, selectedCatalog, description, unitPrice, stock);

                productService.save(newProduct);
                System.out.println("Sản phẩm đã được thêm mới thành công!");
            } else {
                System.err.println("Không tìm thấy danh mục với mã ID '" + catalogId + "'.");
                System.out.println("Hãy bổ sung thêm danh mục!");
                new MenuCatalog().addCatalog();
            }
        }
    }

    public void showCatalog() {
        for (Catalogs catalogs : catalogService.findAll()) {
            System.out.println(catalogs.getId() + ". " + catalogs.getCatalogName());
        }
    }

    public void editProduct() {

        System.out.println(CYAN_BOLD_BRIGHT + "<---------------------" + WHITE_BOLD_BRIGHT + "THAY ĐỔI THÔNG TIN SẢN PHẨM" + CYAN_BOLD_BRIGHT + "----------------------->" + RESET);
        System.out.println("Nhập ID của sản phẩm cần chỉnh sửa: ");
        int productId = Input.inputInteger();

        Products productEdit = productService.findById(productId);

        if (productEdit == null) {
            System.out.println("Không tìm thấy danh mục có ID '" + productId + "'");
        } else {
            System.out.println("Thông tin sản phẩm cần chỉnh sửa: ");
            System.out.println(productEdit);

            System.out.println("Nhập thông tin mới cho sản phẩm (nhấn Enter để bỏ qua):");
            System.out.println("Tên sản phẩm: ");
            String newName = Input.inputString();
            if (!newName.isEmpty()) {
                productEdit.setProductName(newName);
            }

            System.out.println("Danh sách danh mục sản phẩm: ");
            showCatalog();
            System.out.println("Chọn mã danh mục sản phẩm: ");
            int catalogId = Input.inputInteger();
            Catalogs selectedCatalog = catalogService.findById(catalogId);
            productEdit.setCatalogs(selectedCatalog);
            if (selectedCatalog != null) {
                System.out.println("Mô tả sản phẩm: ");
                String newDescription = Input.inputString();
                if (!newDescription.isEmpty()) {
                    productEdit.setDescription(newDescription);
                }

                System.out.println("Đơn giá: ");
                String newUnitPriceStr = Input.inputString();
                if (!newUnitPriceStr.isEmpty()) {
                    double newUnitPrice = Double.parseDouble(newUnitPriceStr);
                    productEdit.setUnitPrice(newUnitPrice);
                }

                System.out.println("Số lượng trong kho: ");
                String newStockStr = Input.inputString();
                if (!newStockStr.isEmpty()) {
                    int newStock = Integer.parseInt(newStockStr);
                    productEdit.setStock(newStock);
                }

                Scanner scanner = new Scanner(System.in);
                productEdit.setStatus(scanner);

                productService.save(productEdit);
            }
        }
    }

    public void findProductByName() {

        System.out.println(CYAN_BOLD_BRIGHT + "<----------------------" + WHITE_BOLD_BRIGHT + "TÌM KIẾM DANH MỤC THEO TÊN" + CYAN_BOLD_BRIGHT + "----------------------->" + RESET);
        System.out.print("Nhập tên danh mục: ");
        String productName = Input.inputString();

        List<Products> foundProducts = productService.findByName(productName);
        if (foundProducts.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào có tên '" + productName + "'.");
        } else {
            System.out.println(BRIGHT_ORANGE_BOLD + ".----------------------------------------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN CÁC SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "----------------------------------------------------------.");
            System.out.println("|                                                                                                                                          |");
            System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|-------" + WHITE_BOLD_BRIGHT + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "-------|----" + WHITE_BOLD_BRIGHT + "DANH MỤC" + BRIGHT_ORANGE_BOLD + "----|---------------" + WHITE_BOLD_BRIGHT + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "--------------|------" + WHITE_BOLD_BRIGHT + "ĐƠN GIÁ" + BRIGHT_ORANGE_BOLD + "-----|-" + WHITE_BOLD_BRIGHT + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + "-|--" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "--|");

            for (Products products : foundProducts) {
                products.display();
            }

            System.out.println("'------------------------------------------------------------------------------------------------------------------------------------------'"+RESET);
            System.out.println();
        }
    }

    public void sortProductByUnitPrice() {
        List<Products> productList = productService.findAll();
        Comparator<Products> priceComparator = Comparator.comparingDouble(Products::getUnitPrice);

        productList.sort(priceComparator.reversed());

        System.out.println(BRIGHT_ORANGE_BOLD + ".----------------------------------------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN CÁC SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "----------------------------------------------------------.");
        System.out.println("|                                                                                                                                          |");
        System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|-------" + WHITE_BOLD_BRIGHT + "TÊN SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "-------|----" + WHITE_BOLD_BRIGHT + "DANH MỤC" + BRIGHT_ORANGE_BOLD + "----|---------------" + WHITE_BOLD_BRIGHT + "MÔ TẢ SẢN PHẨM" + BRIGHT_ORANGE_BOLD + "--------------|------" + WHITE_BOLD_BRIGHT + "ĐƠN GIÁ" + BRIGHT_ORANGE_BOLD + "-----|-" + WHITE_BOLD_BRIGHT + "SỐ LƯỢNG" + BRIGHT_ORANGE_BOLD + "-|--" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "--|");

        for (Products product : productList) {
            product.display();
        }

        System.out.println("'------------------------------------------------------------------------------------------------------------------------------------------'"+RESET);
        System.out.println();
    }
}

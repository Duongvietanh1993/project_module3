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

import java.util.*;

import static config.Color.*;

public class MenuCatalog {
    static Config<Users> usersConfig = new Config();
    ICatalogService catalogService = new CatalogIMPL();
    IProductService productService = new ProductIMPL();


    public void menuCatalog() {
        int menuWidth = 23;
        String greeting = String.format("XIN CHÀO: %-" + (menuWidth - 14) + "s", usersConfig.readFile(Config.URL_USER_LOGIN).getFullName());
        String menu = BRIGHT_ORANGE_BOLD +
                ".-----------------------------" + WHITE_BOLD_BRIGHT + "TRANG DANH MỤC" + BRIGHT_ORANGE_BOLD + "----------------------------.\n" +
                "|    " + WHITE_BOLD_BRIGHT + "[0]. Quay lại                                   " + WHITE_BOLD_BRIGHT + greeting + BRIGHT_ORANGE_BOLD + "|\n" +
                "|-----------------------------------------------------------------------|\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "1. Hiển thị danh mục sản phẩm" + BRIGHT_ORANGE_BOLD + "                    |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "2. Thêm mới danh mục sản phẩm" + BRIGHT_ORANGE_BOLD + "                    |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "3. Tìm kiếm danh mục sản phẩm" + BRIGHT_ORANGE_BOLD + "                    |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "4. Chỉnh sửa danh mục sản phẩm" + BRIGHT_ORANGE_BOLD + "                   |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "5. Sắp xếp danh mục sản phẩm" + BRIGHT_ORANGE_BOLD + "                     |\n" +
                "|                      " + WHITE_BOLD_BRIGHT + "6. Xóa danh danh mục sản phẩm" + BRIGHT_ORANGE_BOLD + "                    |\n" +
                "'-----------------------------------------------------------------------'" + RESET;
        int choice;
        do {
            System.out.println(menu);
            System.out.println("Mời nhập lựa chọn: ");
            choice = Input.inputInteger();
            switch (choice) {
                case 1:
                    showCatalog();
                    break;
                case 2:
                    addCatalog();
                    break;
                case 3:
                    findCatalogByName();
                    break;
                case 4:
                    editCatalog();
                    break;
                case 5:
                    softCatalog();
                    break;
                    case 6:
                        deleteList();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
            }
        } while (choice != 0);
    }

    public void showCatalog() {
        if (catalogService != null) {
            System.out.println(BRIGHT_ORANGE_BOLD + ".--------------------------" + WHITE_BOLD_BRIGHT + "THÔNG TIN CÁC DANH MỤC" + BRIGHT_ORANGE_BOLD + "-----------------------.");
            System.out.println("|                                                                       |");
            System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|-----" + WHITE_BOLD_BRIGHT + "DANH MỤC" + BRIGHT_ORANGE_BOLD + "-----|-----------" + WHITE_BOLD_BRIGHT + "MÔ TẢ" + BRIGHT_ORANGE_BOLD + "-------------|---" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "---|");

            if (catalogService.findAll().isEmpty()) {
                System.out.println("|                                                                       |");
                System.out.println("|                             Danh mục đang trống!                      |");
            } else {
                for (Catalogs catalogs : catalogService.findAll()) {
                    catalogs.display();
                }
            }
            System.out.println("'-----------------------------------------------------------------------'" + RESET);
        } else {
            System.out.println("Không tìm thấy thông tin danh mục.");
        }
    }

    public void addCatalog() {
        System.out.println(CYAN_BOLD_BRIGHT + "<----------------------" + WHITE_BOLD_BRIGHT + "THÊM MỚI DANH MỤC SẢN PHẨM" + CYAN_BOLD_BRIGHT + "----------------------->" + RESET);
        System.out.println("Nhập số lượng danh mục bạn muốn thêm mới: ");
        int numCatalogs = Input.inputInteger();

        for (int i = 0; i < numCatalogs; i++) {
            System.out.println("Nhập thông tin cho danh mục #" + (i + 1));

            System.out.print("Tên danh mục: ");
            String catalogName = Input.inputString();

            if (catalogService.existCatalogName(catalogName)) {
                System.err.println("Danh mục đã tồn tại. Vui lòng chọn tên khác!");
                i--;
                continue;
            }

            System.out.print("Mô tả: ");
            String catalogDescription = Input.inputString();
            Catalogs newCatalog = new Catalogs(catalogService.getNewId(), catalogName, catalogDescription);

            catalogService.save(newCatalog);
        }
    }

    public void findCatalogByName() {
        System.out.println(CYAN_BOLD_BRIGHT + "<------------------------" + WHITE_BOLD_BRIGHT + "TÌM KIẾM DANH MỤC THEO TÊN" + CYAN_BOLD_BRIGHT + "--------------------->" + RESET);

        String catalogName;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Nhập tên danh mục: ");
            catalogName = Input.inputString();

            if (catalogName.isEmpty()) {
                System.err.println("Tên danh mục không được để trống.");
            } else {
                validInput = true;
                List<Catalogs> foundCatalogs = catalogService.findByName(catalogName);

                if (foundCatalogs.isEmpty()) {
                    System.out.println("Không tìm thấy danh mục nào có tên '" + catalogName + "'.");
                } else {
                    System.out.println(BRIGHT_ORANGE_BOLD + ".-----------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH DANH MỤC TÌM KIẾM" + BRIGHT_ORANGE_BOLD + "---------------------.");
                    System.out.println("|                                                                       |");
                    System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|-----" + WHITE_BOLD_BRIGHT + "DANH MỤC" + BRIGHT_ORANGE_BOLD + "-----|-----------" + WHITE_BOLD_BRIGHT + "MÔ TẢ" + BRIGHT_ORANGE_BOLD + "-------------|---" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "---|");

                    for (Catalogs catalog : foundCatalogs) {
                        catalog.display();
                    }

                    System.out.println("'-----------------------------------------------------------------------'");
                }
            }
        }
    }

    public void editCatalog() {

        System.out.println(CYAN_BOLD_BRIGHT + "<----------------------------" + WHITE_BOLD_BRIGHT + "CHỈNH SỬA DANH MỤC" + CYAN_BOLD_BRIGHT + "------------------------->" + RESET);
        System.out.print("Nhập ID của danh mục cần chỉnh sửa: ");
        int catalogId = Input.inputInteger();

        Catalogs catalogToEdit = catalogService.findById(catalogId);

        if (catalogToEdit == null) {
            System.out.println("Không tìm thấy danh mục có ID '" + catalogId + "'.");
        } else {
            System.out.println("Thông tin danh mục cần chỉnh sửa:");
            System.out.println(catalogToEdit);

            System.out.println("Nhập thông tin mới cho danh mục (nhấn Enter để bỏ qua):");

            System.out.print("Tên danh mục: ");
            String newName = Input.inputString();
            if (!newName.isEmpty()) {
                catalogToEdit.setCatalogName(newName);
            }

            System.out.print("Mô tả: ");
            String newDescription = Input.inputString();
            if (!newDescription.isEmpty()) {
                catalogToEdit.setDescription(newDescription);
            }

            Scanner scanner = new Scanner(System.in);
            catalogToEdit.setStatus(scanner);

            catalogService.save(catalogToEdit);
            updateProduct(catalogToEdit);

            System.out.println("Danh mục đã được chỉnh sửa thành công.");
        }
    }

    private void updateProduct(Catalogs catalogs) {
        for (Products product : productService.findAll()) {
            if (product.getCatalogs().getId() == catalogs.getId()) {
                product.setCatalogs(catalogs);
                productService.save(product);
            }
        }
    }

    public void softCatalog() {
        List<Catalogs> catalogs = catalogService.findAll();

        Collections.sort(catalogs, new Comparator<Catalogs>() {
            @Override
            public int compare(Catalogs catalog1, Catalogs catalog2) {
                return catalog1.getCatalogName().compareTo(catalog2.getCatalogName());
            }
        });

        System.out.println(BRIGHT_ORANGE_BOLD + ".-------------------" + WHITE_BOLD_BRIGHT + "DANH SÁCH DANH MỤC (ĐÃ SẮP XẾP)" + BRIGHT_ORANGE_BOLD + "------------------.");
        System.out.println("|                                                                       |");
        System.out.println("|--" + WHITE_BOLD_BRIGHT + "*" + BRIGHT_ORANGE_BOLD + "--|-----" + WHITE_BOLD_BRIGHT + "DANH MỤC" + BRIGHT_ORANGE_BOLD + "-----|-----------" + WHITE_BOLD_BRIGHT + "MÔ TẢ" + BRIGHT_ORANGE_BOLD + "-------------|---" + WHITE_BOLD_BRIGHT + "TRẠNG THÁI" + BRIGHT_ORANGE_BOLD + "---|");

        for (Catalogs catalog : catalogs) {
            catalog.display();
        }

        System.out.println("'-----------------------------------------------------------------------'" + RESET);
    }

    public void deleteList() {
        System.out.println("Nhập danh mục muốn xóa");
        int idCatalog = Input.inputInteger();

        Catalogs catalog = catalogService.findById(idCatalog);
        if (catalog != null) {
            Iterator<Products> iterator = productService.findAll().iterator();
            while (iterator.hasNext()) {
                Products product = iterator.next();
                if (product.getCatalogs().getId() == idCatalog) {
                    iterator.remove();
                }
            }


            catalogService.delete(idCatalog);
        } else {
            System.out.println("Không tìm thấy danh mục");
        }
    }
}
package service.product;

import config.Config;
import models.catalogs.Catalogs;
import models.products.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductIMPL implements IProductService {
    static Config<List<Products>> config = new Config<>();

    public static List<Products> productsList;


    static {
        productsList = config.readFile(Config.URL_PRODUCT);

        if (productsList == null) {
            productsList = new ArrayList<>();
        }
    }

    @Override
    public List<Products> findAll() {
        return productsList;
    }

    @Override
    public void save(Products products) {
        int index = findIndexById(products.getId());
        if (findById(products.getId()) == null) {
            productsList.add(products);
            updateData();
        } else {
            productsList.set(index, products);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        productsList.remove(findById(id));
        updateData();
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Products products : productsList) {
            if (products.getId() > idMax) {
                idMax = products.getId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_PRODUCT, productsList);
    }

    @Override
    public Products findById(int id) {
        for (Products products : productsList) {
            if (products.getId() == id) {
                return products;
            }
        }
        return null;
    }

    @Override
    public int findIndexById(int id) {
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Products> findByName(String name) {
        List<Products> foundProducts = new ArrayList<>();
        for (Products products : productsList) {
            if (products.getProductName().toLowerCase().trim().contains(name)) {
                foundProducts.add(products);
            }
        }
        return foundProducts;
    }

    @Override
    public boolean existProductName(String productName) {
        for (Products products : productsList) {
            if (products.getProductName().equals(productName)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void increaseProductQuantity(Products product, int quantity) {
        int currentQuantity = product.getStock(); // Lấy số lượng hiện tại của sản phẩm
        int newQuantity = currentQuantity + quantity; // Tính toán số lượng mới

        product.setStock(newQuantity); // Cập nhật số lượng mới của sản phẩm
        updateData();
    }
}

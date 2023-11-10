package service.product;

import models.catalogs.Catalogs;
import models.products.Products;
import service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Products> {
    boolean existProductName(String productName);
    List<Products> findByName(String name);
    public void increaseProductQuantity(Products product, int quantity);
}

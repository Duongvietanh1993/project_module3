package models.products;

import models.catalogs.Catalogs;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Scanner;

import static config.Color.*;

public class Products implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String productName;
    private Catalogs catalogs;
    private String description;
    private double unitPrice;
    private int stock;
    private boolean status = true;

    public Products() {
    }

    public Products(int id, String productName, Catalogs catalogs, String description, double unitPrice, int stock) {
        this.id = id;
        this.productName = productName;
        this.catalogs = catalogs;
        this.description = description;
        this.unitPrice = unitPrice;
        this.stock = stock;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Catalogs getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(Catalogs catalogs) {
        this.catalogs = catalogs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(Scanner scanner) {
        System.out.println(
                "Trạng thái sản phẩm: " + '\n' +
                        "1.true" + '\n' +
                        "2.false");
        this.status = Integer.parseInt(scanner.nextLine()) == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return id == products.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Override
    public String toString() {
        return "Mã sản phẩm " + id +
                " - Tên sản phẩm: " + productName +
                " - Danh mục sản phẩm: " + catalogs +
                " - Mô tả: " + description +
                " - Đơn giá: " + (formatter.format(unitPrice)) +
                " - Số lượng trong kho: " + stock +
                " - Trạng thái: " + (status ? "Đang Bán" : "Ngừng Bán");
    }

    public void display() {
        System.out.printf(BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-3S" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-23s" + BRIGHT_ORANGE_BOLD + "|    " + WHITE_BRIGHT + "%-12s" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-40s" + BRIGHT_ORANGE_BOLD + "| " + WHITE_BRIGHT + "%16s" + BRIGHT_ORANGE_BOLD + " |    " + WHITE_BRIGHT + "%-6s" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-18s" + BRIGHT_ORANGE_BOLD + "|\n",
                id, productName, catalogs.getCatalogName(), description, (formatter.format(unitPrice) + " VND"), stock, (status ? GREEN_BOLD_BRIGHT + "Đang Bán" : RED_BOLD_BRIGHT + "Ngừng Bán"));
    }

    public void displayHome() {
        System.out.printf(BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-3S" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-24s" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-16s" + BRIGHT_ORANGE_BOLD + "|    " + WHITE_BRIGHT + "%-7s" + BRIGHT_ORANGE_BOLD + "|  " + WHITE_BRIGHT + "%-40s" + BRIGHT_ORANGE_BOLD + "|\n" + RESET,
                id, productName, (formatter.format(unitPrice) + " VND"), stock, description);
    }
}



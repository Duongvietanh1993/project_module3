package models.catalogs;
import java.io.*;
import java.util.Scanner;
import static config.Color.*;


public class Catalogs implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String catalogName;
    private String description;
    private boolean status = true;

    public Catalogs() {

    }

    public Catalogs(int id, String catalogName, String description) {
        this.id = id;
        this.catalogName = catalogName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(Scanner scanner) {
        System.out.println(
                "Trạng thái: " + '\n' +
                        "1.true" + '\n' +
                        "2.false");
        this.status = Integer.parseInt(scanner.nextLine()) == 1;
    }

    @Override
    public String toString() {
        return "Mã danh mục: " + id +
                " - Tên danh mục: " + catalogName +
                " - Mô tả: " + description +
                " - Trạng thái: " + (status ? "Hiển Thị" : "Ẩn");
    }
    public void display() {
        System.out.printf(BRIGHT_ORANGE_BOLD+"|  " + WHITE_BRIGHT + "%-3S" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-15s" + BRIGHT_ORANGE_BOLD + "|   " + WHITE_BRIGHT + "%-26s" + BRIGHT_ORANGE_BOLD + "|    " + WHITE_BRIGHT + "%-19s" + BRIGHT_ORANGE_BOLD + "|\n",
                id,catalogName, description, (status ? GREEN_BOLD_BRIGHT +"Hiển Thị" : RED_BOLD_BRIGHT+"Ẩn"));
    }
}

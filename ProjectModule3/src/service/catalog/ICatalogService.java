package service.catalog;

import models.catalogs.Catalogs;
import service.IGenericService;

import java.util.List;

public interface ICatalogService extends IGenericService<Catalogs> {
    boolean existCatalogName(String catalogName);
    List<Catalogs> findByName(String name);
    void deleteByName(int name);
}

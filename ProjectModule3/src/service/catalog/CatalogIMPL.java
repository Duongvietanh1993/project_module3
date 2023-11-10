package service.catalog;

import config.Config;
import models.catalogs.Catalogs;

import java.util.ArrayList;
import java.util.List;

public class CatalogIMPL implements ICatalogService {
    static Config<List<Catalogs>> config = new Config<>();
    public static List<Catalogs> catalogList;

    static {
        catalogList = config.readFile(Config.URL_CATALOG);
        if (catalogList == null) {
            catalogList = new ArrayList<>();
        }
    }


    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATALOG, catalogList);
    }


    @Override
    public List<Catalogs> findAll() {
        return catalogList;
    }

    @Override
    public void save(Catalogs catalogs) {
        if (findById(catalogs.getId()) == null) {
            catalogList.add(catalogs);
            updateData();
        } else {
            catalogList.set(catalogList.indexOf(catalogs), catalogs);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        catalogList.remove(findById(id));
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Catalogs catalogs : catalogList) {
            if (catalogs.getId() > idMax) {
                idMax = catalogs.getId();
            }
        }
        return (idMax + 1);
    }


    @Override
    public Catalogs findById(int id) {
        for (Catalogs catalogs : catalogList) {
            if (catalogs.getId()==id) {
                return catalogs;
            }
        }
        return null;
    }


    @Override
    public int findIndexById(int id) {
        for (int i = 0; i < catalogList.size(); i++) {
            if (catalogList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List<Catalogs> findByName(String name) {
        List<Catalogs> foundCatalogs = new ArrayList<>();
        for (Catalogs catalog : catalogList) {
            if (catalog.getCatalogName().toLowerCase().trim().contains(name)) {
                foundCatalogs.add(catalog);
            }
        }
        return foundCatalogs;
    }

    @Override
    public void deleteByName(int id) {
        for (Catalogs catalogs : catalogList) {
            if (catalogs.getId()==id) {
                catalogList.remove(catalogs);
                updateData();
            }
        }

    }

    @Override
    public boolean existCatalogName(String catalogName) {
        for (Catalogs catalogs : catalogList) {
            if (catalogs.getCatalogName().equals(catalogName)) {
                return true;
            }
        }
        return false;
    }
}

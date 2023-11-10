package service;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();
    void save (T t); // add + update
    void delete(int id);
    int getNewId();
    void updateData();
    T findById(int id);
    int findIndexById(int id);

}

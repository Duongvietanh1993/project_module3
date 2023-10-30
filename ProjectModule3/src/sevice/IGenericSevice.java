package sevice;

import models.user.Users;

import java.util.List;

public interface IGenericSevice<T> {
    List<T>findAll();
    void save(T t);
    void delete(String id);
    int findIndexById(String id);
    Users findById(String id);
    Users findByName(String name);
}

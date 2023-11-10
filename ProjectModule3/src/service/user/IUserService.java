package service.user;

import models.user.Users;
import service.IGenericService;

import java.util.List;

public interface IUserService extends IGenericService<Users> {
    boolean existUserName(String userName);
    boolean existEmail(String email);
    Users checkLogin(String userName, String password);
    List<Users> findByName(String name);

}

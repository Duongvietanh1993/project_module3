package sevice.user;

import models.user.Users;
import sevice.IGenericSevice;

public interface IUserSevice extends IGenericSevice<Users> {
    boolean existUserName(String userName);
    boolean existEmail(String email);
    Users checkLogin(String userName, String password);
}

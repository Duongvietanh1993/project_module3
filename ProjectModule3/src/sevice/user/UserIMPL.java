package sevice.user;

import config.Config;
import models.user.RoleLogin;
import models.user.Users;

import java.util.ArrayList;
import java.util.List;

public class UserIMPL implements IUserSevice {
    static Config<List<Users>> config = new Config<>();
    public static List<Users> usersList;

    static {
        usersList = config.readFile(Config.URL_USERS);
        if (usersList == null) {
            usersList = new ArrayList<>();
            usersList.add(new Users("ADMIN", "admin", "tv14061993", "admin@gmail.com", "+84.xxxxxxxxx","Hà Nội", true, RoleLogin.ADMIN));
            new UserIMPL().updateData();
        }
    }

    public void updateData() {
        config.writeFile(Config.URL_USERS, usersList);
    }

    @Override
    public List<Users> findAll() {
        return usersList;
    }

    @Override
    public void save(Users users) {
        if (findById(users.getId()) == null) {
            usersList.add(users);
            updateData();
        } else {
            usersList.set(usersList.indexOf(users), users);
            updateData();
        }
    }

    @Override
    public void delete(String id) {
        usersList.remove(findIndexById(id));
    }

    @Override
    public int findIndexById(String id) {
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Users findById(String id) {
        for (Users users : usersList) {
            if (users.getId().equals(id)) {
                return users;
            }
        }
        return null;
    }

    @Override
    public Users findByName(String fullName) {
        for (Users user : usersList) {
            if (user.getFullName().contains(fullName)) {
                return user;
            }
        }
        return null;
    }


    @Override
    public boolean existUserName(String userName) {
        for (Users user : usersList) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existEmail(String email) {
        for (Users user : usersList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Users checkLogin(String userName, String password) {
        for (Users user : usersList) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}

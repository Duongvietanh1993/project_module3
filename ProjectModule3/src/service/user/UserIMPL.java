package service.user;

import config.Config;
import models.user.RoleLogin;
import models.user.Users;


import java.util.ArrayList;
import java.util.List;

public class UserIMPL implements IUserService {
    static Config<List<Users>> config = new Config<>();
    public static List<Users> userList;

    static {
        userList = config.readFile(Config.URL_USERS);
        if (userList == null) {
            userList = new ArrayList<>();
            userList.add(new Users(0, "ADMIN", "admin", "tv14061993", "admin@gmail.com", "+84.*********", "Hà Nội", true, RoleLogin.ADMIN));
            new UserIMPL().updateData();
        }

    }

    @Override
    public List<Users> findAll() {
        return userList;
    }

    @Override
    public void save(Users users) {
        int index = findIndexById(users.getId());
        if (findById(users.getId()) == null) {
            userList.add(users);
            updateData();
        } else {
            userList.set(index, users);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        userList.remove(findById(id));
        updateData();
    }

    @Override
    public Users findById(int id) {
        for (Users users : userList) {
            if (users.getId() == id) {
                return users;
            }
        }
        return null;
    }

    @Override
    public int findIndexById(int id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Users users : userList) {
            if (users.getId() > idMax) {
                idMax = users.getId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_USERS, userList);
    }


    public List<Users> findByName(String name) {
        List<Users> foundUser = new ArrayList<>();
        for (Users users : userList) {
            if (users.getFullName().toLowerCase().trim().contains(name)) {
                foundUser.add(users);
            }
        }
        return foundUser;
    }


    @Override
    public boolean existUserName(String userName) {
        for (Users users : userList) {
            if (users.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existEmail(String email) {
        for (Users users : userList) {
            if (users.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Users checkLogin(String username, String password) {
        for (Users users : userList) {
            if (users.getUserName().equals(username) && users.getPassword().equals(password)) {
                return users;
            }
        }
        return null;
    }
}

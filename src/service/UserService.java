package service;

import dao.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();
    public boolean authenticateUser(String username,String password) {
        User user = userDAO.getUserByUsername(username);
        if(user == null){
            return false;
        }
        return user.getPassword().equals(password);
    }

}

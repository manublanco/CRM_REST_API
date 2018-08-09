package com.theam.crmrestapi.services;

import com.theam.crmrestapi.dao.UserDao;
import com.theam.crmrestapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    /** The user dao. */
    @Autowired
    UserDao userDao;


    public List<User> findAllUsers() {

        return userDao.getAllUsers();
    }

    public User findUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public User findUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    /**
     * Creates the user.
     *
     * @param user
     *            the user
     */
    public void createUser(User user) {

        userDao.create(user);
    }


    public void updateUser(User user) {
        userDao.update(user);
    }


    public boolean deleteUserById(int id) {
        return userDao.delete(id);
    }
}

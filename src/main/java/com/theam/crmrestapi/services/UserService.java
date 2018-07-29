package com.theam.crmrestapi.services;

import com.theam.crmrestapi.dao.RoleDao;
import com.theam.crmrestapi.dao.UserDao;
import com.theam.crmrestapi.model.Role;
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


    /** The role dao. */
    @Autowired
    RoleDao roleDao;


    public List<User> findAllUsers() {

        return userDao.getAllUsers();
    }

    public User findUserById(Integer id) {
        return userDao.getUserById(id);
    }

    public User findUserByNameAndPassword(String name, String password) {
        return userDao.getUserByNameAndPassword(name, password);
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

    /**
     * Gets the role by name.
     *
     * @param roleName
     *            the role name
     * @return the role by name
     */
    public Role getRoleByName(String roleName) {
        return roleDao.getRoleByName(roleName);

    }

    /**
     * Gets the role id.
     *
     * @param id
     *            the id
     * @return the role id
     */
    public Role getRoleId(int id) {
        return roleDao.getRoleById(id);

    }

    public void updateUser(User user) {
        userDao.update(user);
    }


    public List<Role> findAllRoles() {

        return roleDao.getAllRoles();
    }



}

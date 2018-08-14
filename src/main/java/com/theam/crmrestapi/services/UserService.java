package com.theam.crmrestapi.services;

import com.theam.crmrestapi.dao.UserDao;
import com.theam.crmrestapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

@Service
@Transactional
public class UserService {

    /** The user dao. */
    @Autowired
    UserDao userDao;

    /** Find all Users **/
    public List<User> findAllUsers() {
        return userDao.getAllUsers();
    }

    /** Find user by Id **/
    public User findUserById(Integer id) {
        return userDao.getUserById(id);
    }

    /** Find user by username **/
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

    /**
     * Creates the user.
     *
     * @param user
     *            the user
     */
    public void updateUser(User user) {
        userDao.update(user);
    }

    /**
     * Creates the user.
     *
     * @param id
     *
     */
    public boolean deleteUserById(int id) {
        return userDao.delete(id);
    }

    /**
     * Find the authenticated user with the authorization header
     *
     * @param authorization the authorization header
     *
     */
    public User authenticatedUser(String authorization) {
        final String encodedUserPassword = authorization.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = new BASE64Decoder().decodeBuffer(encodedUserPassword);
            usernameAndPassword = new String(decodedBytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();

        return findUserByUsername(username);
    }
}

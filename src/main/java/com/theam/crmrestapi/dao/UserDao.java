package com.theam.crmrestapi.dao;

import com.theam.crmrestapi.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("userDao")
public class UserDao {

    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates the User
     *
     * @param user the user
     */
    public void create(User user) {
        entityManager.persist(user);
    }

    /**
     * Gets the user by id.
     *
     * @param id the id
     * @return the User by id
     */
    public User getUserById(Integer id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Gets the user by username.
     *
     * @param username the username
     * @return the User by username
     */
    public User getUserByUsername(String username) {
        try{
            Query query = entityManager.createNamedQuery("User.findByUsername", User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    /**
     * Gets all the users.
     *
     * @return all the users
     */
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query query = entityManager.createNamedQuery("User.findAll", User.class);
        return (List<User>) query.getResultList();
    }

    /**
     * Update the user
     *
     * @param   user
     */
    public void update (User user) {
        entityManager.merge(user);
    }

    /**
     * Delete the user
     *
     * @param   id
     */
    @Transactional
    public boolean delete(int id) {
        User user = getUserById(id);
        if (user != null) {
            entityManager.remove(user);
            return true;
        }
        return false;
    }
}
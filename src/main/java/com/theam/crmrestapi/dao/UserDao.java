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
     * Creates the.
     *
     * @param user the user
     */
    public void create(User user) {
        entityManager.persist(user);
    }

    /**
     * Gets the product by id.
     *
     * @param id the id
     * @return the product by id
     */
    public User getUserById(Integer id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Gets the all users.
     *
     * @return the all users
     */
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        return (List<User>) query.getResultList();
    }

    public void update (User user) {entityManager.merge(user); }

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
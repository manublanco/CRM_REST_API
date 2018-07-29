package com.theam.crmrestapi.dao;


import com.theam.crmrestapi.model.User;
import org.springframework.stereotype.Component;

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

    public User getUserByNameAndPassword(String name, String password) {

        User user = null;
        try{
            user = (User) entityManager.createQuery("SELECT u FROM U∫ser u where u.name = :name and u.password = :password")
                    .setParameter("name", name)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException nre){}

        return user;
    }

    public void update (User user) {entityManager.merge(user); }


}
package com.theam.crmrestapi.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.theam.crmrestapi.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * The Class RoleDao.
 */
@Component("roleDao")
public class RoleDao {

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;


    public List<Role> getAllRoles() {
        Query query = entityManager.createQuery("SELECT r FROM Role r");
        return (List<Role>) query.getResultList();
    }



    /**
     * Gets the role by name.
     *
     * @param name
     *            the name
     * @return the role by name
     */
    public Role getRoleByName(String name) {
        Role role = null;
        try{
            role = (Role) entityManager.createQuery("select r from Role r where r.name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre){}

        return role;
    }

    /**
     * Gets the role by id.
     *
     * @param id
     *            the id
     * @return the role by id
     */
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);

    }
}

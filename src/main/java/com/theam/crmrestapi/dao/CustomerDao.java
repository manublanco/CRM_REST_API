package com.theam.crmrestapi.dao;

import com.theam.crmrestapi.model.Customer;
import com.theam.crmrestapi.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("customerDao")
public class CustomerDao {
    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates the customer
     *
     * @param   customer
     */
    public void create(Customer customer) {
        entityManager.persist(customer);
    }

    /**
     * Gets the customer by id.
     *
     * @param id the id
     * @return the customer by id
     */
    public Customer getCustomerById(Integer id) {
        return entityManager.find(Customer.class, id);
    }

    /**
     * Gets all customers.
     *
     * @return  all customers
     */
    @SuppressWarnings("unchecked")
    public List<Customer> getAllCustomers() {
        Query query = entityManager.createQuery("SELECT c FROM Customer c");
        return (List<Customer>) query.getResultList();
    }


    public void update (Customer customer) {entityManager.merge(customer); }

    @Transactional
    public boolean delete(int id) {

        Customer customer = getCustomerById(id);
        if (customer != null) {
            entityManager.remove(customer);
            return true;
        }
        return false;
    }
}

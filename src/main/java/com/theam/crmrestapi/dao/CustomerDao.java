package com.theam.crmrestapi.dao;

import com.theam.crmrestapi.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
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
     * @param   customer the customer
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
        Query query = entityManager.createNamedQuery("Customer.findAll", Customer.class);
        return (List<Customer>) query.getResultList();
    }

    /**
     * Update the customer
     *
     * @param   customer the customer
     */
    public void update (Customer customer) {
        entityManager.merge(customer);
    }

    /**
     * Delete the customer
     *
     * @param   id
     */
    @Transactional
    public boolean delete(int id) {
        Customer customer = getCustomerById(id);
        if (null != customer) {
            entityManager.remove(customer);
            return true;
        }
        return false;
    }
}

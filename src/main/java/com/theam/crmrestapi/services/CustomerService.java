package com.theam.crmrestapi.services;

import com.theam.crmrestapi.dao.CustomerDao;
import com.theam.crmrestapi.dao.UserDao;
import com.theam.crmrestapi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    /** The user dao. */
    @Autowired
    UserDao userDao;

    /** The customer dao. */
    @Autowired
    CustomerDao customerDao;

    /** Find All Customers **/
    public List<Customer> findAllCustomers() {
        return customerDao.getAllCustomers();
    }

    /** Find Customer by Id **/
    public Customer findCustomerById(Integer id) {
        return customerDao.getCustomerById(id);
    }

    /**
     * Creates the customer.
     *
     * @param customer
     *            the customer
     */
    public void createCustomer(Customer customer) {
        customerDao.create(customer);
    }

    /**
     * Updates the customer.
     *
     * @param customer
     *            the customer
     */
    public void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    /**
     * Delete the customer.
     *
     * @param id the customer id
     *
     */
    public boolean deleteCustomerById(int id) {
        return customerDao.delete(id);
    }
}

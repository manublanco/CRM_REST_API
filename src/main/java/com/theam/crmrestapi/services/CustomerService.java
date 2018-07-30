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


    public List<Customer> findAllCustomers() {

        return customerDao.getAllCustomers();
    }

    public Customer findCustomerById(Integer id) {
        return customerDao.getCustomerById(id);
    }

    /**
     * Creates the user.
     *
     * @param customer
     *            the user
     */
    public void createCustomer(Customer customer) {

        customerDao.create(customer);
    }


    public void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    public boolean deleteCustomerById(int id) {
        return customerDao.delete(id);
    }
}

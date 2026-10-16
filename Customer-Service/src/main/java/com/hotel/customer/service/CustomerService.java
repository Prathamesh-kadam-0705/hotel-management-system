
package com.hotel.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.customer.entity.Customer;
import com.hotel.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer customer = getCustomerById(id);

        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
    

	public Customer patchCustomer(Long id, Customer updatedCustomer) {
	
	    Customer customer = getCustomerById(id);
	
	    if (updatedCustomer.getName() != null) {
	        customer.setName(updatedCustomer.getName());
	    }
	
	    if (updatedCustomer.getEmail() != null) {
	        customer.setEmail(updatedCustomer.getEmail());
	    }
	
	    if (updatedCustomer.getPhone() != null) {
	        customer.setPhone(updatedCustomer.getPhone());
	    }
	
	    return customerRepository.save(customer);
	}

}

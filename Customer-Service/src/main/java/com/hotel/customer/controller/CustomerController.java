
package com.hotel.customer.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.hotel.customer.entity.Customer;
import com.hotel.customer.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }
    

	@PatchMapping("/{id}")
	public Customer patchCustomer(
	        @PathVariable Long id,
	        @RequestBody Customer updatedCustomer) {
	
	    return customerService.patchCustomer(id, updatedCustomer);
	}

}

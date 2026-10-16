
package com.hotel.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}

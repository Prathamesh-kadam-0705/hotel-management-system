
package com.hotel.order.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.hotel.order.dto.Customer;

@Component
public class CustomerClient {

    private final RestClient customerRestClient;

    public CustomerClient(RestClient customerRestClient) {
        this.customerRestClient = customerRestClient;
    }

    public Customer getCustomerById(Long customerId) {

        return customerRestClient.get()
                .uri("/customers/{id}", customerId)
                .retrieve()
                .body(Customer.class);
    }
}


package com.hotel.notification.client;

import com.hotel.notification.dto.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class CustomerClient {

    private final RestClient restClient;


	public CustomerClient(
	        @Qualifier("loadBalancedRestClientBuilder")
	        RestClient.Builder builder) {
	
	    this.restClient = builder
	            .baseUrl("http://CUSTOMER-SERVICE")
	            .build();
	}


    public Customer getCustomerById(Long customerId) {
        return restClient.get()
                .uri("/customers/{id}", customerId)
                .retrieve()
                .body(Customer.class);
    }
}

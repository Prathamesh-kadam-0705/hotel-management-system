
package com.hotel.order.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.order.dto.OrderEvent;
import com.hotel.order.kafka.OrderEventProducer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.order.client.CustomerClient;
import com.hotel.order.dto.Customer;
import com.hotel.order.dto.OrderDetailsResponse;
import com.hotel.order.entity.Order;
import com.hotel.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    
    private final OrderEventProducer orderEventProducer;
    private final ObjectMapper objectMapper;

    public OrderService(
            OrderRepository orderRepository,
            CustomerClient customerClient,
            OrderEventProducer orderEventProducer,
            ObjectMapper objectMapper) {

        this.orderRepository = orderRepository;
        this.customerClient = customerClient;
        this.orderEventProducer = orderEventProducer;
        this.objectMapper = objectMapper;
    }

    public Order createOrder(Order order) {

        // Save order in the database
        Order savedOrder = orderRepository.save(order);

        // Create the event
        OrderEvent event = new OrderEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getItemName(),
                savedOrder.getQuantity(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount()
        );

        try {
            // Convert event to JSON
            String message = objectMapper.writeValueAsString(event);

            // Send JSON message to Kafka
            orderEventProducer.sendOrderEvent(message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert order event to JSON", e);
        }

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);

        existingOrder.setCustomerId(updatedOrder.getCustomerId());
        existingOrder.setItemName(updatedOrder.getItemName());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
        existingOrder.setStatus(updatedOrder.getStatus());

        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        Order existingOrder = getOrderById(id);
        orderRepository.delete(existingOrder);
    }
    
    public Order patchOrder(Long id, Order patchData) {

        Order existingOrder = getOrderById(id);

        if (patchData.getItemName() != null) {
            existingOrder.setItemName(patchData.getItemName());
        }

        if (patchData.getQuantity() != null) {
            existingOrder.setQuantity(patchData.getQuantity());
        }

        if (patchData.getTotalAmount() != null) {
            existingOrder.setTotalAmount(patchData.getTotalAmount());
        }

        if (patchData.getStatus() != null) {
            existingOrder.setStatus(patchData.getStatus());
        }

        return orderRepository.save(existingOrder);
    }
    
    public Customer getCustomerForOrder(Long orderId) {
        Order order = getOrderById(orderId);

        return customerClient.getCustomerById(
                order.getCustomerId()
        );
    }
    

	public OrderDetailsResponse getOrderDetails(Long orderId) {
	
	    // Get order from Order Service database
	    Order order = getOrderById(orderId);
	
	    // Get customer from Customer Service
	    Customer customer = customerClient.getCustomerById(
	            order.getCustomerId()
	    );
	
	    // Combine order and customer data
	    return new OrderDetailsResponse(
	            order.getId(),
	            order.getItemName(),
	            order.getQuantity(),
	            customer
	    );
	}

}

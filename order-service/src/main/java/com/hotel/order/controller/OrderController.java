
package com.hotel.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.hotel.order.dto.Customer;
import com.hotel.order.dto.OrderDetailsResponse;
import com.hotel.order.entity.Order;
import com.hotel.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public Order updateOrder(
            @PathVariable Long id,
            @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "Order deleted successfully";
    }
    
    @PatchMapping("/{id}")
    public Order patchOrder(
            @PathVariable Long id,
            @RequestBody Order patchData) {

        return orderService.patchOrder(id, patchData);
    }
    
    @GetMapping("/{orderId}/customer")
    public Customer getCustomerForOrder(
            @PathVariable Long orderId) {

        return orderService.getCustomerForOrder(orderId);
    }
    

	@GetMapping("/{orderId}/details")
	public OrderDetailsResponse getOrderDetails(
	        @PathVariable Long orderId) {
	
	    return orderService.getOrderDetails(orderId);
	}

}

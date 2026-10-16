
package com.hotel.order.dto;

public class OrderDetailsResponse {

    private Long orderId;
    private String itemName;
    private Integer quantity;
    private Customer customer;

    public OrderDetailsResponse() {
    }

    public OrderDetailsResponse(
            Long orderId,
            String itemName,
            Integer quantity,
            Customer customer) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.customer = customer;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

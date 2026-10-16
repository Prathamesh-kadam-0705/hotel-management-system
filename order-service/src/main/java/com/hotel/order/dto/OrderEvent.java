
package com.hotel.order.dto;

public class OrderEvent {

    private Long orderId;
    private Long customerId;
    private String itemName;
    private Integer quantity;
    private String status;
    private Double totalAmount;

    public OrderEvent() {
    }

    public OrderEvent(Long orderId, Long customerId,
                      String itemName, Integer quantity,
                      String status,Double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
        this.totalAmount = totalAmount;
    }
    
    

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

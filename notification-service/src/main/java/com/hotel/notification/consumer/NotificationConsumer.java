
package com.hotel.notification.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.notification.client.CustomerClient;
import com.hotel.notification.dto.Customer;
import com.hotel.notification.dto.OrderEvent;
import com.hotel.notification.service.EmailService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final ObjectMapper objectMapper;
    private final CustomerClient customerClient;
    private final EmailService emailService;

    public NotificationConsumer(
            ObjectMapper objectMapper,
            CustomerClient customerClient,
            EmailService emailService) {
        this.objectMapper = objectMapper;
        this.customerClient = customerClient;
        this.emailService = emailService;
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group"
    )
    public void consume(String message) throws Exception {

        System.out.println("Received order event: " + message);

        OrderEvent event =
                objectMapper.readValue(message, OrderEvent.class);

        String status = event.getStatus();

        if (status == null) {
            System.out.println("Email skipped: status is missing.");
            return;
        }

        if (!"CONFIRMED".equalsIgnoreCase(status)
                && !"CANCELLED".equalsIgnoreCase(status)
                && !"DELIVERED".equalsIgnoreCase(status)) {

            System.out.println("Email skipped. Order status: " + status);
            return;
        }

        Customer customer =
                customerClient.getCustomerById(event.getCustomerId());

        if (customer == null
                || customer.getEmail() == null
                || customer.getEmail().isBlank()) {

            throw new IllegalStateException(
                    "Customer email not found for customer ID: "
                            + event.getCustomerId()
            );
        }

        emailService.sendOrderStatusEmail(
                customer.getEmail(),
                event.getOrderId(),
                event.getItemName(),
                event.getQuantity(),
                event.getTotalAmount(),
                status
        );

        System.out.println(
                status + " email processed for order ID: "
                        + event.getOrderId()
        );
    }
}

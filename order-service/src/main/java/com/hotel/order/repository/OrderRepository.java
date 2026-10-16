
package com.hotel.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotel.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

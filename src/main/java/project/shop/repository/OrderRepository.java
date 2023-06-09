package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.Order;
import project.shop.entity.OrderItem;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

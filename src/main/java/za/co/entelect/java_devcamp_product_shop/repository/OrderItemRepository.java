package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.entelect.java_devcamp_product_shop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
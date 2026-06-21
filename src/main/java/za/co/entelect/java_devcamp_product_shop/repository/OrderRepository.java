package za.co.entelect.java_devcamp_product_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.entelect.java_devcamp_product_shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
package za.co.entelect.java_devcamp_product_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp_product_shop.entity.Order;
import za.co.entelect.java_devcamp_product_shop.entity.OrderItem;
import za.co.entelect.java_devcamp_product_shop.entity.Product;
import za.co.entelect.java_devcamp_product_shop.exception.ResourceNotFoundException;
import za.co.entelect.java_devcamp_product_shop.repository.OrderRepository;
import za.co.entelect.java_devcamp_product_shop.repository.ProductRepository;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createPendingOrder(Long customerId, Long productId) {

        log.info("Creating pending order. customerId={}, productId={}", customerId, productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setContractUrl(null);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        order.getOrderItems().add(orderItem);

        Order savedOrder = orderRepository.save(order);

        log.info(
                "Pending order created successfully. orderId={}, customerId={}, productId={}",
                savedOrder.getOrderId(),
                customerId,
                productId
        );

        return savedOrder;
    }
}
